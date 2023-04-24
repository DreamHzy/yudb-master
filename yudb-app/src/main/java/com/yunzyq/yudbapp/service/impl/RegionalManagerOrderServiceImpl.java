package com.yunzyq.yudbapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.core.PageData;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.*;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.model.*;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.enums.AdjustmentTypeEnum;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.RegionalManagerOrderService;
import com.yunzyq.yudbapp.service.pay.PayContext;
import com.yunzyq.yudbapp.service.pay.PayService;
import com.yunzyq.yudbapp.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionalManagerOrderServiceImpl implements RegionalManagerOrderService {

    @Resource
    RedisUtils redisUtils;
    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;
    @Resource
    RegionalManagerMapper regionalManagerMapper;
    @Resource
    PaymentTypeMapper paymentTypeMapper;
    @Resource
    PaymentOrderExamineMapper paymentOrderExamineMapper;
    @Resource
    PaymentOrderExamineDeailMapper paymentOrderExamineDeailMapper;
    @Resource
    PaymentOrderExamineFileMapper paymentOrderExamineFileMapper;
    @Resource
    PayChannelMapper payChannelMapper;
    @Resource
    PaymentOrderPayMapper paymentOrderPayMapper;
    @Resource
    MerchantStoreMapper merchantStoreMapper;
    @Resource
    MerchantMapper merchantMapper;

    @Resource
    PayContext payContext;
    @Resource
    MerchantStoreCloudSchoolMapper merchantStoreCloudSchoolMapper;

    @Value("${imageUrl}")
    private String imageurl;


    @Value("${AppID}")
    private String AppID;
    @Value("${AppSecret}")
    private String AppSecret;


    @Override
    public ApiRes<PageWrap<RegionalManagerPlatformPageVo>> platformPageNoSh(HttpServletRequest httpServletRequest, PageWrap pageWrap) {
        String vlaue = redisUtils.token(httpServletRequest);
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<RegionalManagerPlatformPageVo> regionalManagerPlatformPageVoList = paymentOrderMasterMapper.platformPageNoSh(vlaue);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        regionalManagerPlatformPageVoList.stream().forEach(regionalManagerPlatformPageVo -> {
            if (regionalManagerPlatformPageVo.getExamineStatus() != null) {
                regionalManagerPlatformPageVo.setDisplay("1");
                try {
                    if (regionalManagerPlatformPageVo.getSend().equals("未推送")) {
                        regionalManagerPlatformPageVo.setMerchantDisplay("未显示");
                    } else {
                        if (DateUtil.getDaySub(new Date(), sdf.parse(regionalManagerPlatformPageVo.getExpireTime())) <= 30) {
                            regionalManagerPlatformPageVo.setMerchantDisplay("显示");
                        } else {
                            regionalManagerPlatformPageVo.setMerchantDisplay("未显示");

                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });


        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(regionalManagerPlatformPageVoList)));
    }

    @Override
    public ApiRes<PageWrap<RegionalManagerPlatformPageVo>> findPage(HttpServletRequest httpServletRequest, PlatformPageDTO platformPageDTO) {
        String vlaue = redisUtils.token(httpServletRequest);
        PageHelper.startPage(platformPageDTO.getPage(), platformPageDTO.getCapacity());
        List<RegionalManagerPlatformPageVo> regionalManagerPlatformPageVoList;
        if (StringUtils.equals("2", platformPageDTO.getType().toString())) {
            regionalManagerPlatformPageVoList = paymentOrderMasterMapper.findPage2(vlaue, platformPageDTO);
            regionalManagerPlatformPageVoList.forEach(regionalManagerPlatformPageVo -> {
                if (StringUtils.equals("1", regionalManagerPlatformPageVo.getDeleted())) {
                    regionalManagerPlatformPageVo.setExamineStatus("4");
                } else {
                    List<ExamineDetailDTO> examineDetailDTOS = paymentOrderExamineMapper.listExamineDetail(Integer.parseInt(regionalManagerPlatformPageVo.getId()));
                    examineDetailDTOS.forEach(examineDetailDTO -> {
                        examineDetailDTO.setExamineTime(StringUtils.isBlank(examineDetailDTO.getExamineTime()) ? "" : examineDetailDTO.getExamineTime());
                        examineDetailDTO.setRemark(StringUtils.isBlank(examineDetailDTO.getRemark()) ? "" : examineDetailDTO.getRemark());
                    });
                    regionalManagerPlatformPageVo.setExamineDetailList(examineDetailDTOS);
                }
            });
        } else {
            regionalManagerPlatformPageVoList = paymentOrderMasterMapper.findPage(vlaue, platformPageDTO);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            regionalManagerPlatformPageVoList.stream().forEach(regionalManagerPlatformPageVo -> {
                if (StringUtils.isBlank(regionalManagerPlatformPageVo.getApproveName())) {
                    List<String> approveNames = paymentOrderMasterMapper.getApproveName(Integer.parseInt(regionalManagerPlatformPageVo.getId()));
                    regionalManagerPlatformPageVo.setApproveName(approveNames.stream().collect(Collectors.joining(",")));
                }

                if (regionalManagerPlatformPageVo.getExamineStatus() != null) {
                    regionalManagerPlatformPageVo.setDisplay("1");
                    try {
                        if (regionalManagerPlatformPageVo.getSend().equals("未推送")) {
                            regionalManagerPlatformPageVo.setMerchantDisplay("未显示");
                        } else {
                            if (DateUtil.getDaySub(new Date(), sdf.parse(regionalManagerPlatformPageVo.getExpireTime())) <= 30) {
                                regionalManagerPlatformPageVo.setMerchantDisplay("显示");
                            } else {
                                regionalManagerPlatformPageVo.setMerchantDisplay("未显示");

                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(regionalManagerPlatformPageVoList)));
    }

    @Override
    public ApiRes<ExamineFileVO> examineFile(AuditRecordDTO auditRecordDTO) {
        List<String> photos = paymentOrderExamineFileMapper.examineFile(Integer.parseInt(auditRecordDTO.getId()), 4);
        if (CollectionUtils.isEmpty(photos) || photos.size() == 0) {
            photos = Lists.newArrayList();
        }
        List<String> videos = paymentOrderExamineFileMapper.examineFile(Integer.parseInt(auditRecordDTO.getId()), 3);
        if (CollectionUtils.isEmpty(videos) || videos.size() == 0) {
            videos = Lists.newArrayList();
        }
        ExamineFileVO examineFileVO = new ExamineFileVO();
        examineFileVO.setImageUrl(imageurl);
        examineFileVO.setPhotos(photos);
        examineFileVO.setVideos(videos);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", examineFileVO);
    }

    @Override
    public ApiRes<AuditRecordVO> auditRecord(AuditRecordDTO auditRecordDTO) {
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(auditRecordDTO.getId());
        if (paymentOrderMaster == null) {
            return ApiRes.failed("订单不存在");
        }
        List<ExamineDTO> examineDTOS = paymentOrderExamineMapper.listExamine(paymentOrderMaster.getId());
        examineDTOS.forEach(examineDTO -> {
            List<ExamineDetailDTO> examineDetailDTOS = paymentOrderExamineMapper.listExamineDetail(Integer.parseInt(examineDTO.getId()));
            examineDetailDTOS.forEach(examineDetailDTO -> {
                examineDetailDTO.setExamineTime(StringUtils.isBlank(examineDetailDTO.getExamineTime()) ? "" : examineDetailDTO.getExamineTime());
                examineDetailDTO.setRemark(StringUtils.isBlank(examineDetailDTO.getRemark()) ? "" : examineDetailDTO.getRemark());
            });
            examineDTO.setExamineDetailList(examineDetailDTOS);
        });
        AuditRecordVO auditRecordVO = new AuditRecordVO();
        auditRecordVO.setExamineList(examineDTOS);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", auditRecordVO);
    }

    @Override
    public ApiRes<PlatformPageDetatilVo> deatil(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto) {
        if (pageDetatilDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PlatformPageDetatilVo platformPageDetatilVo = new PlatformPageDetatilVo();


        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(pageDetatilDto.getId());

        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        platformPageDetatilVo.setMoney(paymentOrderMaster.getMoney() + "");
        platformPageDetatilVo.setCreateTime(paymentOrderMaster.getCreateTime());
        platformPageDetatilVo.setPayTime(paymentOrderMaster.getPayTime());
        platformPageDetatilVo.setMerchantName(paymentOrderMaster.getMerchantName());
        platformPageDetatilVo.setStatus(paymentOrderMaster.getStatus() + "");
        platformPageDetatilVo.setUid(paymentOrderMaster.getMerchantStoreUid());
        platformPageDetatilVo.setId(paymentOrderMaster.getOrderNo());
        platformPageDetatilVo.setPayTypeName(paymentOrderMaster.getPaymentTypeName());
        platformPageDetatilVo.setRemarkOne(paymentOrderMaster.getPaymentTypeName() + ":" + paymentOrderMaster.getMoney() + "元");
        platformPageDetatilVo.setRemarkTwo(paymentOrderMaster.getRemark());

        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", platformPageDetatilVo);
    }

    @Override
    public ApiRes<PageWrap<PlatformRecordPageVo>> platformRecordPage(HttpServletRequest httpServletRequest, PageWrap pageWrap) {
        String vlaue = redisUtils.token(httpServletRequest);
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<PlatformRecordPageVo> regionalManagerPlatformPageVoList = paymentOrderMasterMapper.findPlatformRecordPageVo(vlaue);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(regionalManagerPlatformPageVoList)));
    }

    @Override
    public ApiRes applyForAdjustment(HttpServletRequest httpServletRequest, ApplyForAdjustmentDto applyForAdjustmentDto) {
        if (applyForAdjustmentDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }

        String id = applyForAdjustmentDto.getId();
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(id);
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        if (paymentOrderMaster.getStatus() != 1 || paymentOrderMaster.getExamine() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态错误", "");
        }
        //查询订单是否存在
        String payTypeId = applyForAdjustmentDto.getPayTypeId();
        if (!"1".equals(payTypeId) && !"2".equals(payTypeId) && !"4".equals(payTypeId) && !"5".equals(payTypeId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "类型错误", "");
        }
        String money = applyForAdjustmentDto.getMoney();
        String time = applyForAdjustmentDto.getTime();

        if ("1".equals(payTypeId)) {
            if (StringUtils.isEmpty(money)) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "请输入调整金额", "");
            } else if (BigDecimal.ZERO.compareTo(paymentOrderMaster.getPayMoney()) < 0) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "该订单已支付金额，无法申请调整", "");
            }
        }
        if ("2".equals(payTypeId)) {
            if (StringUtils.isEmpty(time)) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "请输入调整时间", "");
            }

        }
        String vlaue = redisUtils.token(httpServletRequest);
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "内部错误", "");
        }

        PaymentOrderExamine paymentOrderExamine = new PaymentOrderExamine();
        paymentOrderExamine.setPaymentOrderMasterId(paymentOrderMaster.getId());
        paymentOrderExamine.setMerchantStoreId(paymentOrderMaster.getMerchantStoreId());
        paymentOrderExamine.setMerchantStoreUid(paymentOrderMaster.getMerchantStoreUid());
        paymentOrderExamine.setPaymentTypeId(Integer.valueOf(payTypeId));
        paymentOrderExamine.setPaymentTypeName(paymentOrderMaster.getPaymentTypeName());
        paymentOrderExamine.setExamine(1);
        paymentOrderExamine.setStatus(1);
        paymentOrderExamine.setRemark(applyForAdjustmentDto.getMsg());
        paymentOrderExamine.setExamineType(Integer.valueOf(payTypeId));
        if ("1".equals(payTypeId)) {
            paymentOrderExamine.setOldMoney(paymentOrderMaster.getMoney());
            paymentOrderExamine.setNewMoney(new BigDecimal(money));
            paymentOrderExamine.setMsg("支付金额" + paymentOrderMaster.getMoney() + "调整到" + money);
        }
        if ("2".equals(payTypeId)) {
            paymentOrderExamine.setNewMoney(paymentOrderMaster.getMoney());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            paymentOrderExamine.setOldTime(paymentOrderMaster.getExpireTime());
            try {
                paymentOrderExamine.setNewTime(sdf.parse(time));
                paymentOrderExamine.setMsg("支付时间" + sdf.format(paymentOrderMaster.getExpireTime()) + "延期到" + time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if ("4".equals(payTypeId)) {
            paymentOrderExamine.setNewMoney(paymentOrderMaster.getMoney());
            paymentOrderExamine.setMsg("缴费取消");
        }
        if ("5".equals(payTypeId)) {
            paymentOrderExamine.setMsg("线下缴费");
        }

        paymentOrderMaster.setAdjustmentCount(paymentOrderMaster.getAdjustmentCount() + 1);

        paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);

        paymentOrderExamine.setCreateTime(new Date());
        paymentOrderExamine.setApplyId(regionalManager.getId());
        paymentOrderExamine.setApplyName(regionalManager.getName());
        paymentOrderExamine.setStep(1);

        paymentOrderExamineMapper.insertSelective(paymentOrderExamine);

        PaymentOrderExamineDeail paymentOrderExamineDeail = new PaymentOrderExamineDeail();
        paymentOrderExamineDeail.setPaymentOrderExamineId(paymentOrderExamine.getId());
        paymentOrderExamineDeail.setExamine(1);
        paymentOrderExamineDeail.setStatus(1);
        paymentOrderExamineDeail.setCreateTime(new Date());
        paymentOrderExamineDeail.setStep(1);
        paymentOrderExamineDeail.setType(1);
        // 查询该阶段的审核人
        List<String> approveNames = paymentOrderExamineDeailMapper.getApproveName(1, paymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), paymentOrderExamineDeail.getType());
        String approveName = approveNames.stream().collect(Collectors.joining(","));
        paymentOrderExamineDeail.setApproveName(approveName);
        paymentOrderExamineDeailMapper.insertSelective(paymentOrderExamineDeail);


        paymentOrderMaster.setExamine(2);
        paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);

        List<String> url = applyForAdjustmentDto.getUrl();
        List<PaymentOrderExamineFile> paymentOrderExamineFileList = new ArrayList<>();
        url.stream().forEach(s -> {
            PaymentOrderExamineFile paymentOrderExamineFile = new PaymentOrderExamineFile();
            paymentOrderExamineFile.setPaymentOrderExamineId(paymentOrderExamine.getId());
            paymentOrderExamineFile.setType(4);
            paymentOrderExamineFile.setUrl(s);
            paymentOrderExamineFile.setStatus(1);
            paymentOrderExamineFile.setDeleted(false);
            paymentOrderExamineFile.setCreateTime(new Date());
            paymentOrderExamineFileList.add(paymentOrderExamineFile);
        });
        if (paymentOrderExamineFileList.size() > 0) {
            paymentOrderExamineFileMapper.insertList(paymentOrderExamineFileList);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", "");
    }

    @Override
    public ApiRes delete(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto) {
        if (pageDetatilDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(pageDetatilDto.getId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        if (paymentOrderMaster.getStatus() != 4 && paymentOrderMaster.getStatus() != 3) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");

        }
        paymentOrderMaster.setDeleted(true);
        paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
        //查询正在审核的订单
        PaymentOrderExamine paymentOrderExamine = paymentOrderExamineMapper.queryByMasterIdAndStatus(paymentOrderMaster.getId(), 1);
        //删除审核详情/审核文件
        paymentOrderExamineDeailMapper.updateDeletedByPaymentOrderExamineId(paymentOrderExamine.getId());
        paymentOrderExamineFileMapper.updateDeletedByPaymentOrderExamineId(paymentOrderExamine.getId());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "删除成功", "");
    }

    @Override
    public ApiRes withdrawal(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto) {

        if (pageDetatilDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(pageDetatilDto.getId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        if (paymentOrderMaster.getExamine() != 2) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");

        }
        PaymentOrderExamine paymentOrderExamine = null;

        //如果是业务经理自己生成的订单需要做判断，判断是第一次提交审核还是已经提交过审核的
        if (paymentOrderMaster.getType() == 2) {
            //查询前面是否有审核成功的
            paymentOrderExamine = paymentOrderExamineMapper.queryByMasterIdAndStatus(paymentOrderMaster.getId(), 2);
            if (paymentOrderExamine == null) {//说明是首次审核
                paymentOrderMaster.setDeleted(true);
            }
            paymentOrderMaster.setExamine(1);
            //查询最新的审核中的列表
            paymentOrderExamine = paymentOrderExamineMapper.queryByMasterIdAndStatus(paymentOrderMaster.getId(), 1);
            //并且删除审核记录
            paymentOrderExamine.setDeleted(true);

        } else {
            //查询最新的审核中的列表
            paymentOrderExamine = paymentOrderExamineMapper.queryByMasterIdAndStatus(paymentOrderMaster.getId(), 1);
            if (paymentOrderExamine == null) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");
            }
            paymentOrderMaster.setExamine(1);
            //并且删除审核记录
            paymentOrderExamine.setDeleted(true);
        }
        //修改住订单状态
        paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
        //修改审核主订单状态
        paymentOrderExamineMapper.updateByPrimaryKeySelective(paymentOrderExamine);
        //删除审核详情/审核文件
        paymentOrderExamineDeailMapper.updateDeletedByPaymentOrderExamineId(paymentOrderExamine.getId());
        paymentOrderExamineFileMapper.updateDeletedByPaymentOrderExamineId(paymentOrderExamine.getId());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "撤销成功", "");
    }

    @Override
    public ApiRes paymentCode(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto) {

        String ip = IpUtil.getIpAddr(httpServletRequest);

        if (pageDetatilDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(pageDetatilDto.getId());
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
        paymentOrderPay.setFees(PlatformCodeUtil.rate(paymentOrderMaster.getMoney(), payChannel.getRate()));
        paymentOrderPay.setStatus(1);
        paymentOrderPay.setChannelApiUrl(payChannel.getChannelApiUrl());
        paymentOrderPay.setChannelNotifyUrl(payChannel.getChannelNotifyUrl());
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
        } else if (StringUtils.equals("jlw", payChannel.getChannelRoute())
        ||StringUtils.equals("jl", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl(payChannel.getChannelNotifyUrl() + "/common/jlCallBack");
        } else if (StringUtils.equals("sd", payChannel.getChannelRoute())) {
            payDto.setCallbackUrl(payChannel.getChannelNotifyUrl() + "/common/sdCallBack");
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
            payChennleVo.setMoney(paymentOrderMaster.getMoney() + "");
            payChennleVo.setUid(paymentOrderMaster.getMerchantStoreUid());
            payChennleVo.setMerchantName(paymentOrderMaster.getMerchantName());
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "下单成功", payChennleVo);
        }
        return ApiRes.response(CommonConstant.FAIL_CODE, "请求失败", "");
    }

    @Override
    public String callBack(PaymentOrderPay paymentOrderPay) {
        //查询订单是否存在
        PaymentOrderPay paymentOrderPayNew = paymentOrderPayMapper.queryByOrderNo(paymentOrderPay);
        if (paymentOrderPayNew != null && paymentOrderPayNew.getStatus() == 2) {//只有订单存在切是未支付的状态才进行下一步操作
            paymentOrderPayNew.setTransactionId(paymentOrderPay.getTransactionId());
            PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.queryByOrderNO(paymentOrderPayNew.getPaymentOrderMasterId() + "");
            if (paymentOrderMaster != null) {
                MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(paymentOrderMaster.getMerchantStoreId());
                //如果是管理费要对门店管理字段进行操作
                if (paymentOrderMaster.getPaymentTypeId() == 1) {
                    merchantStore.setAlreadyManagementExpense(paymentOrderMaster.getMoney());
                    Date newTime = DateUtil.endTime("1年后", merchantStore.getExpireTime());
                    merchantStore.setExpireTime(newTime);
                    merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
                    //如果管理费大缴纳标准2w
                    if (merchantStore.getManagementExpense().compareTo(new BigDecimal(20000)) > -1) {//说明用户的管理费金额包含云学堂的
                        //查询这个用户总共有多少个云学堂账户
                        List<MerchantStoreCloudSchool> merchantStoreCloudSchoolList = merchantStoreCloudSchoolMapper.queryByMerChantStoreId(merchantStore.getId());
                        if (merchantStoreCloudSchoolList.size() > 0) {
                            merchantStoreCloudSchoolList.stream().forEach(merchantStoreCloudSchool -> {
                                Date newTime1 = DateUtil.endTime("1年后", merchantStoreCloudSchool.getEndTime());
                                merchantStoreCloudSchool.setEndTime(newTime1);
                                merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
                            });
                        }
                    }
                }
//                if (paymentOrderMaster.getPaymentTypeId() == 2) {//修改商户通时间
//                    Date newTime = null;
//                    if (merchantStore.getMerchantLink() == 1) {
//                        newTime = DateUtil.endTime("1年后", merchantStore.getMerchantLinkEndTime());
//                    } else {
//                        newTime = DateUtil.endTime("1年后", new Date());
//                        merchantStore.setMerchantLink(1);
//                    }
//                    merchantStore.setMerchantLinkEndTime(newTime);
//                    merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
//                }
//                if (paymentOrderMaster.getPaymentTypeId() == 3) {//修改云学堂时间
//                    //查询这个用户的云学堂账号信息
//                    Integer id = paymentOrderMaster.getOtherId();
//                    MerchantStoreCloudSchool merchantStoreCloudSchool = merchantStoreCloudSchoolMapper.queryById(id);
//                    Date newTime = DateUtil.endTime("1年后", merchantStoreCloudSchool.getEndTime());
//                    merchantStoreCloudSchool.setEndTime(newTime);
//                    merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
//                }
                paymentOrderMaster.setPayTime(new Date());
                paymentOrderMaster.setStatus(2);
                paymentOrderPayNew.setStatus(3);
                paymentOrderPayNew.setUpdateTime(new Date());
                paymentOrderPayNew.setPayTime(new Date());
                //对支付订单进行操作
                paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
                paymentOrderPayMapper.updateByPrimaryKeySelective(paymentOrderPayNew);
            }


        }
        return null;
    }

    @Override
    public ApiRes send(PlatformPageDetatilDto platformPageDetatilDto) {
        if (platformPageDetatilDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(platformPageDetatilDto.getId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费详情不存在", "");
        }
        if (paymentOrderMaster.getSend() != 2) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "状态不对", "");
        }
        paymentOrderMaster.setSend(1);
        paymentOrderMaster.setSendTime(new Date());
        paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
        String access_token = (String) redisUtils.get("access_token");
        Merchant merchant = merchantMapper.selectByPrimaryKey(paymentOrderMaster.getMerchantId());
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_05);

//        public static String sendSubscriptionMsg(String token, String money, String msg, String time, String openId) {


        String result = WxUtil.sendSubscriptionMsg(access_token, paymentOrderMaster.getMoney() + "", paymentOrderMaster.getPaymentTypeName(), sdf.format(paymentOrderMaster.getExpireTime()), merchant.getOpenId());
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject.getString("errcode").equals("0")) {//说明订阅通知成功，清除缓存
            redisUtils.remove(merchant.getMobile() + CommonConstant.AUTH);
        }

        return ApiRes.response(CommonConstant.SUCCESS_CODE, "推送成功", "");
    }

    @Override
    public ApiRes sendBack(PlatformPageDetatilDto platformPageDetatilDto) {
        if (platformPageDetatilDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(platformPageDetatilDto.getId());
        if (paymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费详情不存在", "");
        }
        if (paymentOrderMaster.getSend() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "状态不对", "");
        }
        paymentOrderMaster.setSend(2);
        paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "推送成功撤回", "");
    }

}
