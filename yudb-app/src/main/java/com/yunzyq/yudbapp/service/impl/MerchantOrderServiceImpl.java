package com.yunzyq.yudbapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.core.PageData;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.*;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.model.PayChannel;
import com.yunzyq.yudbapp.dao.model.PaymentOrderMaster;
import com.yunzyq.yudbapp.dao.model.PaymentOrderPay;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.MerchantOrderService;
import com.yunzyq.yudbapp.service.pay.PayContext;
import com.yunzyq.yudbapp.service.pay.PayService;
import com.yunzyq.yudbapp.util.IpUtil;
import com.yunzyq.yudbapp.util.PlatformCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class MerchantOrderServiceImpl implements MerchantOrderService {

    @Resource
    RedisUtils redisUtils;
    @Resource
    MerchantStoreMapper merchantStoreMapper;
    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;
    @Resource
    PayContext payContext;
    @Resource
    PayChannelMapper payChannelMapper;
    @Resource
    PaymentOrderPayMapper paymentOrderPayMapper;

    @Override
    public ApiRes<SoreListVo> storeList(HttpServletRequest httpServletRequest) {
        String vlaue = redisUtils.token(httpServletRequest);
        List<SoreListVo> list = merchantStoreMapper.queryListByMerchantId(vlaue);
        return new ApiRes(CommonConstant.SUCCESS_CODE, "查询成功", list);
    }

    @Override
    public ApiRes<MerchantPlatformPageVo> merchantPlatformPageVo(PageWrap<StroeIdDto> pageWrap, HttpServletRequest httpServletRequest) {
        StroeIdDto stroeIdDto = pageWrap.getModel();
        if (stroeIdDto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = stroeIdDto.getStoreId();
        if (StringUtils.isEmpty(id)) {
            stroeIdDto.setStoreId(null);
        }
        String vlaue = redisUtils.token(httpServletRequest);
        stroeIdDto.setMerchantId(vlaue);
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<MerchantPlatformPageVo> list = paymentOrderMasterMapper.queryMerchantPlatformPageVo(stroeIdDto);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes<MerchantOrderPayVo> pay(MerchantOrderPayDto merchantOrderPayDto, HttpServletRequest httpServletRequest) {
        String ip = IpUtil.getIpAddr(httpServletRequest);
        if (merchantOrderPayDto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(merchantOrderPayDto.getId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        if (paymentOrderMaster.getExamine() != 1 && paymentOrderMaster.getStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");
        }

        PayChannel payChannel;
        if (paymentOrderMaster.getChannelId() != null) {
            payChannel = payChannelMapper.getChannelById(paymentOrderMaster.getChannelId());
        } else {
            payChannel = payChannelMapper.queryByStatusWx(paymentOrderMaster.getMoney());
        }
        if (payChannel == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "支付通道未开启", "");
        }
        //路由到对应的支付方法中取
        PayService payService = payContext.pay(payChannel.getChannelRoute());
        PaymentOrderPay paymentOrderPay = new PaymentOrderPay();
        paymentOrderPay.setPaymentOrderMasterId(paymentOrderMaster.getId());
        paymentOrderPay.setOrderNo(PlatformCodeUtil.orderNo("13GT"));
        paymentOrderPay.setPayChannelId(payChannel.getId());
        paymentOrderPay.setPayChannelName(payChannel.getChannelName());
        paymentOrderPay.setTotalMoney(paymentOrderMaster.getMoney());
        paymentOrderPay.setPayType(payChannel.getPayType());
        paymentOrderPay.setPayChannelRoute(payChannel.getChannelRoute());
        paymentOrderPay.setStatus(1);
        paymentOrderPay.setFees(PlatformCodeUtil.rate(paymentOrderMaster.getMoney(), payChannel.getRate()));
        paymentOrderPay.setChannelApiUrl(payChannel.getChannelApiUrl());
        if (StringUtils.equals("wx", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl((payChannel.getChannelNotifyUrl() + "/common/wxjlCallBack"));
        } else if (StringUtils.equals("sd", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl((payChannel.getChannelNotifyUrl() + "/common/sdCallBack"));
        } else if (StringUtils.equals("jlw", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl((payChannel.getChannelNotifyUrl() + "/common/jlCallBack"));
        }else if (StringUtils.equals("yl", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl((payChannel.getChannelNotifyUrl() + "/common/ylCallBack"));
        } else {
            return ApiRes.failed("渠道路由不正确");
        }
        paymentOrderPay.setRemark("授权号:" + paymentOrderMaster.getMerchantStoreUid() + "," + paymentOrderMaster.getPaymentTypeName());
        paymentOrderPay.setCreateTime(new Date());
        paymentOrderPayMapper.insertSelective(paymentOrderPay);
        PayServiceDto payServiceDto = new PayServiceDto();
        payServiceDto.setIp(ip);
        payServiceDto.setOpenId(merchantOrderPayDto.getOpenId());
        Integer total_fee = paymentOrderPay.getTotalMoney().multiply(new BigDecimal(100)).intValue();
        PayDto payDto = new PayDto();
        payDto.setUrl(payChannel.getChannelApiUrl());
        payDto.setShopNo(payChannel.getChannelNo());
        payDto.setOrderNo(paymentOrderPay.getOrderNo());
        payDto.setMoney(total_fee);
        payDto.setOpenId(payServiceDto.getOpenId());
        payDto.setAuthCode(payServiceDto.getOpenId());
        payDto.setIp(payServiceDto.getIp());
        if (StringUtils.equals("wx", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl((payChannel.getChannelNotifyUrl() + "/common/wxjlCallBack"));
        } else if (StringUtils.equals("sd", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl(payChannel.getChannelNotifyUrl() + "/common/sdCallBack");
        } else if (StringUtils.equals("jlw", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl((payChannel.getChannelNotifyUrl() + "/common/jlCallBack"));
        } else if (StringUtils.equals("yl", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl((payChannel.getChannelNotifyUrl() + "/common/ylCallBack"));
        } else {
            return ApiRes.failed("回调地址不正确");
        }

        payDto.setOrderName(paymentOrderPay.getRemark());
        payDto.setSign(payChannel.getChannelKey());
        ApiRes<PayChennleVo> merchantPayChennleResApiRes = payService.pay(payDto);
        PayChennleVo payChennleVo = merchantPayChennleResApiRes.getData();
        paymentOrderPay.setStatus(payChennleVo.getStatus());
        paymentOrderPay.setMsg(payChennleVo.getMsg());
        paymentOrderPayMapper.updateByPrimaryKeySelective(paymentOrderPay);
        if ("200".equals(payChennleVo.getCode())) {
            MerchantOrderPayVo merchantOrderPayVo = new MerchantOrderPayVo();
            merchantOrderPayVo.setPayInfo(payChennleVo.getPayInfo());
            payChennleVo.setId(paymentOrderMaster.getId() + "");
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "下单成功", payChennleVo);
        }
        return ApiRes.response(CommonConstant.FAIL_CODE, "请求失败", "");
    }

    @Override
    public ApiRes<MerchantOrderPayVo> paySeparately(MerchantOrderPaySeparatelyDto merchantOrderPaySeparatelyDto, HttpServletRequest httpServletRequest) {
        String ip = IpUtil.getIpAddr(httpServletRequest);
        if (merchantOrderPaySeparatelyDto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(merchantOrderPaySeparatelyDto.getId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        BigDecimal remain = paymentOrderMaster.getMoney().subtract(paymentOrderMaster.getPayMoney());
        BigDecimal standard = new BigDecimal("5000");
        if (remain.compareTo(standard) >= 0 && new BigDecimal(merchantOrderPaySeparatelyDto.getMoney()).compareTo(standard) < 0) {
            return ApiRes.failed("缴费金额必须大于5000");
        } else if (remain.compareTo(standard) < 0 && new BigDecimal(merchantOrderPaySeparatelyDto.getMoney()).compareTo(remain) != 0) {
            return ApiRes.failed("缴费金额必须全额支付");
        } else if (new BigDecimal(merchantOrderPaySeparatelyDto.getMoney()).compareTo(remain) > 0) {
            return ApiRes.failed("支付金额不能大于需缴费金额");
        }
        if (paymentOrderMaster.getExamine() != 1 && paymentOrderMaster.getStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");
        }

        PayChannel payChannel;
        if (paymentOrderMaster.getChannelId() != null) {
            payChannel = payChannelMapper.getChannelById(paymentOrderMaster.getChannelId());
        } else {
            payChannel = payChannelMapper.queryByStatusWx(paymentOrderMaster.getMoney());
        }
        if (payChannel == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "支付通道未开启", "");
        }
        //路由到对应的支付方法中取
        PayService payService = payContext.pay(payChannel.getChannelRoute());
        PaymentOrderPay paymentOrderPay = new PaymentOrderPay();
        paymentOrderPay.setPaymentOrderMasterId(paymentOrderMaster.getId());
        paymentOrderPay.setOrderNo(PlatformCodeUtil.orderNo("13GT"));
        paymentOrderPay.setPayChannelId(payChannel.getId());
        paymentOrderPay.setPayChannelName(payChannel.getChannelName());
        paymentOrderPay.setTotalMoney(new BigDecimal(merchantOrderPaySeparatelyDto.getMoney()));
        paymentOrderPay.setPayType(payChannel.getPayType());
        paymentOrderPay.setPayChannelRoute(payChannel.getChannelRoute());
        paymentOrderPay.setStatus(1);
        paymentOrderPay.setFees(PlatformCodeUtil.rate(paymentOrderPay.getTotalMoney(), payChannel.getRate()));
        paymentOrderPay.setChannelApiUrl(payChannel.getChannelApiUrl());
        if (StringUtils.equals("wx", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl((payChannel.getChannelNotifyUrl() + "/common/wxjlCallBack"));
        } else if (StringUtils.equals("sd", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl((payChannel.getChannelNotifyUrl() + "/common/sdCallBack"));
        } else if (StringUtils.equals("jlw", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl((payChannel.getChannelNotifyUrl() + "/common/jlCallBack"));
        } else if (StringUtils.equals("yl", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl((payChannel.getChannelNotifyUrl() + "/common/ylCallBack"));
        } else {
            return ApiRes.failed("渠道路由不正确");
        }
        paymentOrderPay.setRemark("授权号:" + paymentOrderMaster.getMerchantStoreUid() + "," + paymentOrderMaster.getPaymentTypeName());
        paymentOrderPay.setCreateTime(new Date());
        paymentOrderPayMapper.insertSelective(paymentOrderPay);
        PayServiceDto payServiceDto = new PayServiceDto();
        payServiceDto.setIp(ip);
        payServiceDto.setOpenId(merchantOrderPaySeparatelyDto.getOpenId());
        Integer total_fee = paymentOrderPay.getTotalMoney().multiply(new BigDecimal(100)).intValue();
        PayDto payDto = new PayDto();
        payDto.setUrl(payChannel.getChannelApiUrl());
        payDto.setShopNo(payChannel.getChannelNo());
        payDto.setOrderNo(paymentOrderPay.getOrderNo());
        payDto.setMoney(total_fee);
        payDto.setOpenId(payServiceDto.getOpenId());
        payDto.setAuthCode(payServiceDto.getOpenId());
        payDto.setIp(payServiceDto.getIp());
        if (StringUtils.equals("wx", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl((payChannel.getChannelNotifyUrl() + "/common/wxjlCallBack"));
        } else if (StringUtils.equals("sd", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl(payChannel.getChannelNotifyUrl() + "/common/sdCallBack");
        } else if (StringUtils.equals("jlw", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl((payChannel.getChannelNotifyUrl() + "/common/jlCallBack"));
        } else if (StringUtils.equals("yl", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl((payChannel.getChannelNotifyUrl() + "/common/ylCallBack"));
        } else {
            return ApiRes.failed("回调地址不正确");
        }
        payDto.setOrderName(paymentOrderPay.getRemark());
        payDto.setSign(payChannel.getChannelKey());
        ApiRes<PayChennleVo> merchantPayChennleResApiRes = payService.pay(payDto);
        PayChennleVo payChennleVo = merchantPayChennleResApiRes.getData();
        paymentOrderPay.setStatus(payChennleVo.getStatus());
        paymentOrderPay.setMsg(payChennleVo.getMsg());
        paymentOrderPayMapper.updateByPrimaryKeySelective(paymentOrderPay);
        if ("200".equals(payChennleVo.getCode())) {
            MerchantOrderPayVo merchantOrderPayVo = new MerchantOrderPayVo();
            merchantOrderPayVo.setPayInfo(payChennleVo.getPayInfo());
            payChennleVo.setId(paymentOrderMaster.getId() + "");
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "下单成功", payChennleVo);
        }
        return ApiRes.response(CommonConstant.FAIL_CODE, "请求失败", "");
    }

    @Override
    public ApiRes<PaymentOnBehalfVo> paymentOnBehalf(MerchantOrderPayDto merchantOrderPayDto, HttpServletRequest httpServletRequest) {
        String ip = IpUtil.getIpAddr(httpServletRequest);
        if (merchantOrderPayDto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        System.out.println("merchantOrderPayDto.getId():" + merchantOrderPayDto.getId());
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(merchantOrderPayDto.getId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        if (paymentOrderMaster.getExamine() != 1 && paymentOrderMaster.getStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");
        }

        PayChannel payChannel;
        if (paymentOrderMaster.getChannelId() != null) {
            payChannel = payChannelMapper.getChannelById(paymentOrderMaster.getChannelId());
        } else {
            payChannel = payChannelMapper.queryByStatus();
        }
        if (payChannel == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "支付通道未开启", "");
        }
        //路由到对应的支付方法中取
        PayService payService = payContext.pay(payChannel.getChannelRoute());
        PaymentOrderPay paymentOrderPay = new PaymentOrderPay();
        paymentOrderPay.setPaymentOrderMasterId(paymentOrderMaster.getId());
        paymentOrderPay.setOrderNo(PlatformCodeUtil.orderNo("13GT"));
        paymentOrderPay.setPayChannelId(payChannel.getId());
        paymentOrderPay.setPayChannelName(payChannel.getChannelName());
        paymentOrderPay.setTotalMoney(paymentOrderMaster.getMoney());
        paymentOrderPay.setPayType(payChannel.getPayType());
        paymentOrderPay.setPayChannelRoute(payChannel.getChannelRoute());
        paymentOrderPay.setStatus(1);
        paymentOrderPay.setFees(PlatformCodeUtil.rate(paymentOrderMaster.getMoney(), payChannel.getRate()));
        paymentOrderPay.setChannelApiUrl(payChannel.getChannelApiUrl());
        if (StringUtils.equals("wx", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl(payChannel.getChannelNotifyUrl() + "/common/wxjlCallBack");
        } else if (StringUtils.equals("sd", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl(payChannel.getChannelNotifyUrl() + "/common/sdCallBack");
        } else if (StringUtils.equals("jlw", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl(payChannel.getChannelNotifyUrl() + "/common/jlCallBack");
        } else if (StringUtils.equals("yl", payChannel.getChannelRoute())) {
            paymentOrderPay.setChannelNotifyUrl((payChannel.getChannelNotifyUrl() + "/common/ylCallBack"));
        } else {
            return ApiRes.failed("渠道路由不正确");
        }
        paymentOrderPay.setRemark("授权号:" + paymentOrderMaster.getMerchantStoreUid() + "," + paymentOrderMaster.getPaymentTypeName());
        paymentOrderPay.setCreateTime(new Date());
        paymentOrderPayMapper.insertSelective(paymentOrderPay);

        PayServiceDto payServiceDto = new PayServiceDto();
        payServiceDto.setIp(ip);

        Integer total_fee = paymentOrderPay.getTotalMoney().multiply(new BigDecimal(100)).intValue();
        PayDto payDto = new PayDto();
        payDto.setUrl(payChannel.getChannelApiUrl());
        payDto.setShopNo(payChannel.getChannelNo());
        payDto.setOrderNo(paymentOrderPay.getOrderNo());
        payDto.setMoney(total_fee);
        payDto.setAuthCode(payServiceDto.getOpenId());
        payDto.setIp(payServiceDto.getIp());
        if (StringUtils.equals("wx", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl(payChannel.getChannelNotifyUrl() + "/common/wxjlCallBack");
        } else if (StringUtils.equals("sd", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl(payChannel.getChannelNotifyUrl() + "/common/sdCallBack");
        } else if (StringUtils.equals("jlw", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl(payChannel.getChannelNotifyUrl() + "/common/jlCallBack");
        } else if (StringUtils.equals("yl", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl(payChannel.getChannelNotifyUrl() + "/common/ylCallBack");
        } else {
            return ApiRes.failed("回调地址不正确");
        }
        payDto.setOrderName(paymentOrderPay.getRemark());
        payDto.setSign(payChannel.getChannelKey());
        ApiRes<PayChennleVo> merchantPayChennleResApiRes = payService.pay(payDto);
        PayChennleVo payChennleVo = merchantPayChennleResApiRes.getData();
        paymentOrderPay.setStatus(payChennleVo.getStatus());
        paymentOrderPay.setMsg(payChennleVo.getMsg());
        paymentOrderPayMapper.updateByPrimaryKeySelective(paymentOrderPay);
        if ("200".equals(payChennleVo.getCode())) {
            PaymentOnBehalfVo paymentOnBehalfVo = new PaymentOnBehalfVo();
            paymentOnBehalfVo.setQrCodeBase64(payChennleVo.getQrCodeBase64());
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "下单成功", paymentOnBehalfVo);
        }
        return ApiRes.response(CommonConstant.FAIL_CODE, "请求失败", "");
    }

    @Override
    public ApiRes<PageWrap<PaymentRecordMerchantVo>> paymentRecord(HttpServletRequest httpServletRequest, PageWrap pageWrap) {
        String vlaue = redisUtils.token(httpServletRequest);
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<PaymentRecordMerchantVo> list = paymentOrderMasterMapper.paymentRecord(vlaue);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes paymentRecordDetail(PlatformPageDetatilDto platformPageDetatilDto) {
        if (platformPageDetatilDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }

        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(platformPageDetatilDto.getId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费详情不存在", "");
        }
        MerchantPaymentRecordDetailVo platformPageDetatilVo = new MerchantPaymentRecordDetailVo();
        platformPageDetatilVo.setMoney(paymentOrderMaster.getMoney() + "");
        platformPageDetatilVo.setCreateTime(paymentOrderMaster.getCreateTime());
        platformPageDetatilVo.setMaerchantName(paymentOrderMaster.getMerchantName());
        platformPageDetatilVo.setPayTime(paymentOrderMaster.getPayTime());
        platformPageDetatilVo.setStatus(paymentOrderMaster.getStatus() + "");
        platformPageDetatilVo.setUid(paymentOrderMaster.getMerchantStoreUid());
        platformPageDetatilVo.setId(PlatformCodeUtil.orderNo("13GT"));
        platformPageDetatilVo.setPayTypeName(paymentOrderMaster.getPaymentTypeName());
        platformPageDetatilVo.setRemarkOne(paymentOrderMaster.getPaymentTypeName() + ":" + paymentOrderMaster.getMoney() + "元");
        platformPageDetatilVo.setRemarkTwo(paymentOrderMaster.getRemark());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", platformPageDetatilVo);
    }


}
