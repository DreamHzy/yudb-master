package com.yunzyq.yudbapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunzyq.yudbapp.constant.CallBackConstant;
import com.yunzyq.yudbapp.core.*;
import com.yunzyq.yudbapp.dao.AgentAreaPaymentOrderMasterMapper;
import com.yunzyq.yudbapp.dao.AgentAreaPaymentOrderPayMapper;
import com.yunzyq.yudbapp.dao.PayChannelMapper;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderMaster;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.dos.PayChannelDO;
import com.yunzyq.yudbapp.enums.ChannelPayTypeEnum;
import com.yunzyq.yudbapp.enums.OrderMasterExamineEnum;
import com.yunzyq.yudbapp.enums.OrderMasterStatusEnum;
import com.yunzyq.yudbapp.po.AgentOrderPayPO;
import com.yunzyq.yudbapp.service.IMerchantOrderV2Service;
import com.yunzyq.yudbapp.service.pay.PayContext;
import com.yunzyq.yudbapp.service.pay.PayService;
import com.yunzyq.yudbapp.util.PlatformCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @Author wj
 * @Date 2021/10/25
 */
@Service
public class MerchantOrderV2ServiceImpl implements IMerchantOrderV2Service {

    @Resource
    AgentAreaPaymentOrderPayMapper agentAreaPaymentOrderPayMapper;

    @Resource
    PayContext payContext;

    @Resource
    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;

    @Resource
    PayChannelMapper payChannelMapper;

    @Override
    public ApiRes<MerchantPlatformAgentVO> merchantPlatformAgentPageVo(String merchantId, int page, int capacity) {
        PageHelper.startPage(page, capacity);
        List<MerchantPlatformAgentVO> merchantPlatformAgentVOS = agentAreaPaymentOrderMasterMapper.queryMerchantPlatformAgent(merchantId);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(merchantPlatformAgentVOS)));
    }

    @Override
    public ApiRes<PaymentOnBehalfVo> paymentOnAgentBehalf(AgentOrderPayDTO agentOrderPayDto, String ip) {
        // 参数校验
        ApiRes paymentOnBehalfVoApiRes = validateParam(agentOrderPayDto.getId(), ChannelPayTypeEnum.AGGREGATE_PAYMENT.getType(), null);
        if (!paymentOnBehalfVoApiRes.getCode().equals(CommonConstant.SUCCESS_CODE)) {
            return paymentOnBehalfVoApiRes;
        }
        AgentOrderDataDTO agentOrderDataDTO = (AgentOrderDataDTO) paymentOnBehalfVoApiRes.getData();
        // 主订单对象
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentOrderDataDTO.getAgentAreaPaymentOrderMaster();
        // 通道对象
        PayChannelDO payChannelDO = agentOrderDataDTO.getPayChannelDO();
        // 路由到对应的支付方法中去
        PayService payService = payContext.pay(payChannelDO.getChannelRoute());
        // 新增支付订单
        AgentOrderPayPO agentOrderPayPO = new AgentOrderPayPO(payChannelDO, agentAreaPaymentOrderMaster);
        agentOrderPayPO.setFees(PlatformCodeUtil.rate(agentAreaPaymentOrderMaster.getMoney(), payChannelDO.getRate()));
        agentAreaPaymentOrderPayMapper.insertSelective(agentOrderPayPO);
        PayServiceDto payServiceDto = new PayServiceDto();
        payServiceDto.setIp(ip);
        // 组装通道请求参数
        PayDto payDto = setPayDto(payChannelDO, agentOrderPayPO, payServiceDto, agentAreaPaymentOrderMaster.getChannelId());
        // 请求通道
        ApiRes<PayChennleVo> payChannelVo = payService.pay(payDto);

        // 更新订单支付表状态
        PayChennleVo payChennleVo = payChannelVo.getData();
        AgentOrderPayPO updateAgentOrderPayPO = new AgentOrderPayPO(agentOrderPayPO.getId(), payChennleVo);
        agentAreaPaymentOrderPayMapper.updateByPrimaryKeySelective(updateAgentOrderPayPO);

        if (!ResponseStatus.DATA_EMPTY.getCode().toString().equals(payChennleVo.getCode())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请求失败", "");
        }
        PaymentOnBehalfVo paymentOnBehalfVo = new PaymentOnBehalfVo();
        paymentOnBehalfVo.setQrCodeBase64(payChennleVo.getQrCodeBase64());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "下单成功", paymentOnBehalfVo);
    }

    private PayDto setPayDto(PayChannelDO payChannelDO, AgentOrderPayPO agentOrderPayPO, PayServiceDto payServiceDto, Integer channelId) {
        PayDto payDto = new PayDto();
        payDto.setUrl(payChannelDO.getChannelApiUrl());
        payDto.setShopNo(payChannelDO.getChannelNo());
        payDto.setOrderNo(agentOrderPayPO.getOrderNo());
        payDto.setMoney(agentOrderPayPO.getTotalMoney().multiply(new BigDecimal("100")).intValue());
        payDto.setAuthCode(payServiceDto.getOpenId());
        payDto.setIp(payServiceDto.getIp());
        payDto.setOpenId(payServiceDto.getOpenId());
        if (StringUtils.equals("wx", payChannelDO.getChannelRoute())) {
            payDto.setCallbackUrl(payChannelDO.getChannelNotifyUrl() + CallBackConstant.WX_PAYMENT_ON_AGENT_BEHALF_CALL_BACK);
        } else if (StringUtils.equals("jlw", payChannelDO.getChannelRoute())) {
            payDto.setCallbackUrl(payChannelDO.getChannelNotifyUrl() + CallBackConstant.PAYMENT_ON_AGENT_BEHALF_CALL_BACK);
        } else if (StringUtils.equals("sd", payChannelDO.getChannelRoute())) {
            payDto.setCallbackUrl(payChannelDO.getChannelNotifyUrl() + CallBackConstant.SD_PAYMENT_ON_AGENT_BEHALF_CALL_BACK);
        } else if (StringUtils.equals("yl", payChannelDO.getChannelRoute())) {
            payDto.setCallbackUrl(payChannelDO.getChannelNotifyUrl() + CallBackConstant.YL_PAYMENT_ON_AGENT_BEHALF_CALL_BACK);
        }
        payDto.setOrderName(agentOrderPayPO.getRemark());
        payDto.setSign(payChannelDO.getChannelKey());
        return payDto;
    }

    private ApiRes validateParam(String id, Integer type, String money) {
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(id);
        if (Objects.isNull(agentAreaPaymentOrderMaster)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        if (StringUtils.isNotBlank(money)) {
            BigDecimal remain = agentAreaPaymentOrderMaster.getMoney().subtract(agentAreaPaymentOrderMaster.getPayMoney());
            BigDecimal standard = new BigDecimal("5000");
            if (remain.compareTo(standard) >= 0 && new BigDecimal(money).compareTo(standard) < 0) {
                return ApiRes.failed("缴费金额必须大于5000");
            } else if (remain.compareTo(standard) < 0 && new BigDecimal(money).compareTo(remain) != 0) {
                return ApiRes.failed("缴费金额必须全额支付");
            } else if (new BigDecimal(money).compareTo(remain) > 0) {
                return ApiRes.failed("支付金额不能大于需缴费金额");
            }
        }
        // 判断是否是 未审核且待支付的订单
        if (!OrderMasterExamineEnum.UNREVIEWED.getStatus().equals(agentAreaPaymentOrderMaster.getExamine()) && !OrderMasterStatusEnum.TO_BE_PAID.getStatus().equals(agentAreaPaymentOrderMaster.getStatus())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");
        }
        // 获取支付通道，聚合支付无需判断金额区间
        PayChannelDO payChannelDO;
        if (agentAreaPaymentOrderMaster.getChannelId() != null) {
            payChannelDO = payChannelMapper.getChannelById2(agentAreaPaymentOrderMaster.getChannelId());
        } else if (ChannelPayTypeEnum.AGGREGATE_PAYMENT.getType().equals(type)) {
            payChannelDO = payChannelMapper.queryChannelByStatus(type, null);
        } else {
            payChannelDO = payChannelMapper.queryChannelByStatus(type, agentAreaPaymentOrderMaster.getMoney());
        }
        if (Objects.isNull(payChannelDO)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "支付通道未开启", "");
        }
        AgentOrderDataDTO agentOrderDataDTO = new AgentOrderDataDTO();
        agentOrderDataDTO.setAgentAreaPaymentOrderMaster(agentAreaPaymentOrderMaster);
        agentOrderDataDTO.setPayChannelDO(payChannelDO);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "订单不存在", agentOrderDataDTO);
    }

    @Override
    public ApiRes<MerchantOrderPayVo> agentPay(AgentOrderPayDTO agentOrderPayDto, String ip) {
        // 参数校验
        ApiRes paymentOnBehalfVoApiRes = validateParam(agentOrderPayDto.getId(), ChannelPayTypeEnum.WECHAT_MINI_PROGRAM.getType(), null);
        if (!paymentOnBehalfVoApiRes.getCode().equals(CommonConstant.SUCCESS_CODE)) {
            return paymentOnBehalfVoApiRes;
        }
        AgentOrderDataDTO agentOrderDataDTO = (AgentOrderDataDTO) paymentOnBehalfVoApiRes.getData();
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentOrderDataDTO.getAgentAreaPaymentOrderMaster();
        PayChannelDO payChannelDO = agentOrderDataDTO.getPayChannelDO();
        //路由到对应的支付方法中取
        PayService payService = payContext.pay(payChannelDO.getChannelRoute());
        // 新增支付
        AgentOrderPayPO agentOrderPayPO = new AgentOrderPayPO(payChannelDO, agentAreaPaymentOrderMaster);
        agentOrderPayPO.setFees(PlatformCodeUtil.rate(agentAreaPaymentOrderMaster.getMoney(), payChannelDO.getRate()));
        agentAreaPaymentOrderPayMapper.insertSelective(agentOrderPayPO);

        PayServiceDto payServiceDto = new PayServiceDto();
        payServiceDto.setIp(ip);
        payServiceDto.setOpenId(agentOrderPayDto.getOpenId());
        // 组装通道请求参数
        PayDto payDto = setPayDto(payChannelDO, agentOrderPayPO, payServiceDto, agentAreaPaymentOrderMaster.getChannelId());
        // 请求通道
        ApiRes<PayChennleVo> payChannelVo = payService.pay(payDto);

        // 更新订单支付表状态
        PayChennleVo payChennleVo = payChannelVo.getData();
        AgentOrderPayPO updateAgentOrderPayPO = new AgentOrderPayPO(agentOrderPayPO.getId(), payChennleVo);
        agentAreaPaymentOrderPayMapper.updateByPrimaryKeySelective(updateAgentOrderPayPO);
        if (!ResponseStatus.DATA_EMPTY.getCode().toString().equals(payChennleVo.getCode())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请求失败", "");
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "下单成功", payChennleVo);
    }

    @Override
    public ApiRes<MerchantOrderPayVo> agentPaySeparately(AgentOrderPaySeparatelyDTO agentOrderPaySeparatelyDTO, String ip) {
        // 参数校验
        ApiRes paymentOnBehalfVoApiRes = validateParam(agentOrderPaySeparatelyDTO.getId(), ChannelPayTypeEnum.WECHAT_MINI_PROGRAM.getType(), agentOrderPaySeparatelyDTO.getMoney());
        if (!paymentOnBehalfVoApiRes.getCode().equals(CommonConstant.SUCCESS_CODE)) {
            return paymentOnBehalfVoApiRes;
        }
        AgentOrderDataDTO agentOrderDataDTO = (AgentOrderDataDTO) paymentOnBehalfVoApiRes.getData();
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentOrderDataDTO.getAgentAreaPaymentOrderMaster();
        PayChannelDO payChannelDO = agentOrderDataDTO.getPayChannelDO();
        //路由到对应的支付方法中取
        PayService payService = payContext.pay(payChannelDO.getChannelRoute());
        // 新增支付
        AgentOrderPayPO agentOrderPayPO = new AgentOrderPayPO(payChannelDO, agentAreaPaymentOrderMaster);
        agentOrderPayPO.setTotalMoney(new BigDecimal(agentOrderPaySeparatelyDTO.getMoney()));
        agentOrderPayPO.setFees(PlatformCodeUtil.rate(agentOrderPayPO.getTotalMoney(), payChannelDO.getRate()));
        agentAreaPaymentOrderPayMapper.insertSelective(agentOrderPayPO);

        PayServiceDto payServiceDto = new PayServiceDto();
        payServiceDto.setIp(ip);
        payServiceDto.setOpenId(agentOrderPaySeparatelyDTO.getOpenId());
        // 组装通道请求参数
        PayDto payDto = setPayDto(payChannelDO, agentOrderPayPO, payServiceDto, agentAreaPaymentOrderMaster.getChannelId());
        // 请求通道
        ApiRes<PayChennleVo> payChannelVo = payService.pay(payDto);

        // 更新订单支付表状态
        PayChennleVo payChennleVo = payChannelVo.getData();
        AgentOrderPayPO updateAgentOrderPayPO = new AgentOrderPayPO(agentOrderPayPO.getId(), payChennleVo);
        agentAreaPaymentOrderPayMapper.updateByPrimaryKeySelective(updateAgentOrderPayPO);
        if (!ResponseStatus.DATA_EMPTY.getCode().toString().equals(payChennleVo.getCode())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请求失败", "");
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "下单成功", payChennleVo);
    }

    @Override
    public ApiRes<PageWrap<PaymentRecordMerchantVo>> agentPaymentRecord(String merchantId, int page, int capacity) {
        PageHelper.startPage(page, capacity);
        List<PaymentRecordMerchantVo> list = agentAreaPaymentOrderMasterMapper.agentPaymentRecord(merchantId);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes<PageWrap<MerchantPlatformPageVo>> agentMyBacklog(String merchantId, AgentMyBacklogDTO agentMyBacklogDTO) {
        PageHelper.startPage(agentMyBacklogDTO.getPage(), agentMyBacklogDTO.getCapacity());
        List<MerchantPlatformPageVo> merchantPlatformPageVos = agentAreaPaymentOrderMasterMapper.agentMyBacklog(Integer.parseInt(merchantId), agentMyBacklogDTO.getType());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(merchantPlatformPageVos)));
    }

    @Override
    public ApiRes<MerchantPaymentRecordDetailVo> paymentRecordDetail(PlatformPageDetatilDto platformPageDetatilDto) {
        if (Objects.isNull(platformPageDetatilDto)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }

        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(platformPageDetatilDto.getId());
        if (Objects.isNull(agentAreaPaymentOrderMaster)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费详情不存在", "");
        }
        MerchantPaymentRecordDetailVo platformPageDetatilVo = new MerchantPaymentRecordDetailVo();
        platformPageDetatilVo.setMoney(agentAreaPaymentOrderMaster.getMoney() + "");
        platformPageDetatilVo.setCreateTime(agentAreaPaymentOrderMaster.getCreateTime());
        platformPageDetatilVo.setMaerchantName(agentAreaPaymentOrderMaster.getMerchantName());
        platformPageDetatilVo.setPayTime(agentAreaPaymentOrderMaster.getPayTime());
        platformPageDetatilVo.setStatus(agentAreaPaymentOrderMaster.getStatus() + "");
        platformPageDetatilVo.setUid(agentAreaPaymentOrderMaster.getUid());
        platformPageDetatilVo.setId(PlatformCodeUtil.orderNo("13GT"));
        platformPageDetatilVo.setPayTypeName(agentAreaPaymentOrderMaster.getPaymentTypeName());
        platformPageDetatilVo.setRemarkOne(agentAreaPaymentOrderMaster.getPaymentTypeName() + ":" + agentAreaPaymentOrderMaster.getMoney() + "元");
        platformPageDetatilVo.setRemarkTwo(agentAreaPaymentOrderMaster.getRemark());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", platformPageDetatilVo);
    }

}
