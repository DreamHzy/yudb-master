package com.yunzyq.yudbapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yunzyq.yudbapp.constant.CallBackConstant;
import com.yunzyq.yudbapp.core.*;
import com.yunzyq.yudbapp.dao.*;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.model.*;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.dos.PayChannelDO;
import com.yunzyq.yudbapp.enums.*;
import com.yunzyq.yudbapp.exception.BaseException;
import com.yunzyq.yudbapp.po.*;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.IRegionalManagerOrderV2Service;
import com.yunzyq.yudbapp.service.pay.PayContext;
import com.yunzyq.yudbapp.service.pay.PayService;
import com.yunzyq.yudbapp.util.PlatformCodeUtil;
import com.yunzyq.yudbapp.util.WxUtil;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WJ
 */
@Service
public class RegionalManagerOrderV2ServiceImpl implements IRegionalManagerOrderV2Service {

    @Resource
    RedisUtils redisUtils;
    @Resource
    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;
    @Resource
    RegionalManagerMapper regionalManagerMapper;
    @Resource
    AgentAreaPaymentOrderExamineMapper agentAreaPaymentOrderExamineMapper;
    @Resource
    AgentAreaPaymentOrderExamineDeailMapper agentAreaPaymentOrderExamineDeailMapper;
    @Resource
    AgentAreaPaymentOrderExamineFileMapper agentAreaPaymentOrderExamineFileMapper;
    @Resource
    PayChannelMapper payChannelMapper;
    @Resource
    AgentAreaPaymentOrderPayMapper agentAreaPaymentOrderPayMapper;
    @Resource
    MerchantMapper merchantMapper;

    @Resource
    PayContext payContext;

    @Resource
    DictMapper dictMapper;

    @Resource
    PaymentOrderExamineDeailMapper paymentOrderExamineDeailMapper;

    @Value("${imageUrl}")
    private String imageurl;


    @Override
    public ApiRes<PageWrap<RegionalManagerAgentPlatformPageVO>> agentPlatformPageV2(String managerId, PlatformPageDTO platformPageDTO) {
        PageHelper.startPage(platformPageDTO.getPage(), platformPageDTO.getCapacity());
        List<RegionalManagerAgentPlatformPageVO> list;
        if (StringUtils.equals("2", platformPageDTO.getType().toString())) {
            list = agentAreaPaymentOrderMasterMapper.agentPlatformPage2(managerId, platformPageDTO);
            list.forEach(regionalManagerPlatformPageVo -> {
                if (StringUtils.equals("1", regionalManagerPlatformPageVo.getDeleted())) {
                    regionalManagerPlatformPageVo.setExamineStatus("4");
                } else {
                    List<ExamineDetailDTO> examineDetailDTOS = agentAreaPaymentOrderExamineMapper.listExamineDetail(Integer.parseInt(regionalManagerPlatformPageVo.getId()));
                    examineDetailDTOS.forEach(examineDetailDTO -> {
                        examineDetailDTO.setExamineTime(StringUtils.isBlank(examineDetailDTO.getExamineTime()) ? "" : examineDetailDTO.getExamineTime());
                        examineDetailDTO.setRemark(StringUtils.isBlank(examineDetailDTO.getRemark()) ? "" : examineDetailDTO.getRemark());
                    });
                    regionalManagerPlatformPageVo.setExamineDetailList(examineDetailDTOS);
                }
            });
        } else {
            list = agentAreaPaymentOrderMasterMapper.agentPlatformPage(managerId, platformPageDTO);
            list.forEach(regionalManagerAgentPlatformPageVO -> {
                if (StringUtils.isBlank(regionalManagerAgentPlatformPageVO.getApproveName())) {
                    List<String> approveNames = agentAreaPaymentOrderMasterMapper.getApproveName(Integer.parseInt(regionalManagerAgentPlatformPageVO.getId()));
                    regionalManagerAgentPlatformPageVO.setApproveName(approveNames.stream().collect(Collectors.joining(",")));
                }
            });
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes<ExamineFileVO> examineFile(AuditRecordDTO auditRecordDTO) {
        List<String> photos = agentAreaPaymentOrderExamineFileMapper.examineFile(Integer.parseInt(auditRecordDTO.getId()), 4);
        if (CollectionUtils.isEmpty(photos) || photos.size() == 0) {
            photos = Lists.newArrayList();
        }
        List<String> videos = agentAreaPaymentOrderExamineFileMapper.examineFile(Integer.parseInt(auditRecordDTO.getId()), 3);
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
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(auditRecordDTO.getId());
        if (agentAreaPaymentOrderMaster == null) {
            return ApiRes.failed("订单不存在");
        }
        List<ExamineDTO> examineDTOS = agentAreaPaymentOrderExamineMapper.listExamine(agentAreaPaymentOrderMaster.getId());
        examineDTOS.forEach(examineDTO -> {
            List<ExamineDetailDTO> examineDetailDTOS = agentAreaPaymentOrderExamineMapper.listExamineDetail(Integer.parseInt(examineDTO.getId()));
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
    @Transactional(rollbackFor = Exception.class)
    public ApiRes agentApplyForAdjustment(String managerId, ApplyForAdjustmentDto applyForAdjustmentDto) {
        // 请求参数校验
        if (Objects.isNull(applyForAdjustmentDto)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = applyForAdjustmentDto.getId();
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(id);
        if (Objects.isNull(agentAreaPaymentOrderMaster)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        if (!agentAreaPaymentOrderMaster.getStatus().equals(OrderPayStatusEnum.ORDER_NOW.getStatus()) || !agentAreaPaymentOrderMaster.getExamine().equals(OrderMasterExamineEnum.UNREVIEWED.getStatus())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态错误", "");
        }
        // 金额调整时判断金额是否为空
        if (AdjustmentTypeEnum.AMOUNT_ADJUSTMENT.getType().equals(applyForAdjustmentDto.getPayTypeId()) && StringUtils.isBlank(applyForAdjustmentDto.getMoney())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入调整金额", "");
        } else if (AdjustmentTypeEnum.AMOUNT_ADJUSTMENT.getType().equals(applyForAdjustmentDto.getPayTypeId()) && BigDecimal.ZERO.compareTo(agentAreaPaymentOrderMaster.getPayMoney()) < 0) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "该订单已支付金额，无法申请调整", "");
        }
        // 延期支付时判断时间是否为空
        if (AdjustmentTypeEnum.DEFERRED_PAYMENT.getType().equals(applyForAdjustmentDto.getPayTypeId()) && StringUtils.isBlank(applyForAdjustmentDto.getTime())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入调整时间", "");
        }
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(managerId);
        if (Objects.isNull(regionalManager)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "内部错误", "");
        }
        agentAreaPaymentOrderMaster.setAdjustmentCount(agentAreaPaymentOrderMaster.getAdjustmentCount() + 1);
        agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderMaster);

        // 添加申请记录
        AgentOrderExaminePO agentOrderExaminePO = new AgentOrderExaminePO(agentAreaPaymentOrderMaster, applyForAdjustmentDto, regionalManager.getId(), regionalManager.getName());
        agentAreaPaymentOrderExamineMapper.insertSelective(agentOrderExaminePO);

        // 添加审核记录
        AgentOrderExamineDetailPO agentOrderExamineDetailPO = new AgentOrderExamineDetailPO(agentOrderExaminePO.getId());
        // 查询该阶段的审核人
        List<String> approveNames = paymentOrderExamineDeailMapper.getApproveName(2, agentOrderExaminePO.getExamineType(), agentOrderExamineDetailPO.getStep(), agentOrderExamineDetailPO.getType());
        String approveName = approveNames.stream().collect(Collectors.joining(","));
        agentOrderExamineDetailPO.setApproveName(approveName);
        agentAreaPaymentOrderExamineDeailMapper.insertSelective(agentOrderExamineDetailPO);

        // 主订单审核状态变更
        agentAreaPaymentOrderMaster.setExamine(OrderMasterExamineEnum.IN_REVIEW.getStatus());
        agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderMaster);

        List<String> urlList = applyForAdjustmentDto.getUrl();
        List<AgentAreaPaymentOrderExamineFile> paymentOrderExamineFileList = new ArrayList<>();
        urlList.stream().forEach(url -> {
            AgentOrderExamineFilePO agentOrderExamineFilePO = new AgentOrderExamineFilePO(agentOrderExaminePO.getId(), url, ExamineFileTypeEnum.PHOTO);
            paymentOrderExamineFileList.add(agentOrderExamineFilePO);
        });
        if (paymentOrderExamineFileList.size() > 0) {
            agentAreaPaymentOrderExamineFileMapper.insertList(paymentOrderExamineFileList);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", "");
    }


    @Override
    public ApiRes agentWithdrawal(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto) {

        if (pageDetatilDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(pageDetatilDto.getId());
        if (agentAreaPaymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        if (!OrderMasterStatusEnum.PAYMENT_SUCCESSFUL.getStatus().equals(agentAreaPaymentOrderMaster.getExamine())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");
        }
        AgentAreaPaymentOrderExamine paymentOrderExamine = null;
        //如果是业务经理自己生成的订单需要做判断，判断是第一次提交审核还是已经提交过审核的
        if (OrderMasterTypeEnum.REGIONAL_MANAGER_GENERATION.getType().equals(agentAreaPaymentOrderMaster.getType())) {
            //|| OrderMasterTypeEnum.SYSTEM_BACKGROUND_PERSONNEL_GENERATION.getType().equals(agentAreaPaymentOrderMaster.getType())
            //查询前面是否有审核成功的
            paymentOrderExamine = agentAreaPaymentOrderExamineMapper.queryByMasterIdAndStatus(agentAreaPaymentOrderMaster.getId(), 2);
            if (paymentOrderExamine == null) {//说明是首次审核
                agentAreaPaymentOrderMaster.setDeleted(true);
            }
            agentAreaPaymentOrderMaster.setExamine(OrderMasterExamineEnum.UNREVIEWED.getStatus());
            //查询最新的审核中的列表
            paymentOrderExamine = agentAreaPaymentOrderExamineMapper.queryByMasterIdAndStatus(agentAreaPaymentOrderMaster.getId(), 1);
            //并且删除审核记录
            paymentOrderExamine.setDeleted(IsDeleteEnum.DELETE.getIsDelete());

        } else {
            //查询最新的审核中的列表
            paymentOrderExamine = agentAreaPaymentOrderExamineMapper.queryByMasterIdAndStatus(agentAreaPaymentOrderMaster.getId(), 1);
            if (paymentOrderExamine == null) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");
            }
            agentAreaPaymentOrderMaster.setExamine(OrderMasterExamineEnum.UNREVIEWED.getStatus());
            //并且删除审核记录
            paymentOrderExamine.setDeleted(IsDeleteEnum.DELETE.getIsDelete());
        }
        //修改住订单状态
        agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderMaster);
        //修改审核主订单状态
        agentAreaPaymentOrderExamineMapper.updateByPrimaryKeySelective(paymentOrderExamine);
        //删除审核详情/审核文件
        agentAreaPaymentOrderExamineDeailMapper.updateDeletedByPaymentOrderExamineId(paymentOrderExamine.getId());
        agentAreaPaymentOrderExamineFileMapper.updateDeletedByPaymentOrderExamineId(paymentOrderExamine.getId());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "撤销成功", "");
    }

    @Override
    public ApiRes agentPaymentCode(String ip, PlatformPageDetatilDto pageDetailDto) {
        // 校验请求参数
        if (Objects.isNull(pageDetailDto)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(pageDetailDto.getId());
        if (Objects.isNull(agentAreaPaymentOrderMaster)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        if (!agentAreaPaymentOrderMaster.getExamine().equals(OrderMasterExamineEnum.UNREVIEWED.getStatus()) && !agentAreaPaymentOrderMaster.getStatus().equals(OrderMasterStatusEnum.TO_BE_PAID.getStatus())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单状态不正确", "");
        }
        // 获取请求通道
        PayChannelDO payChannelDO;
        if (agentAreaPaymentOrderMaster.getChannelId() != null) {
            payChannelDO = payChannelMapper.getChannelById2(agentAreaPaymentOrderMaster.getChannelId());
        } else {
            payChannelDO = payChannelMapper.queryChannelByStatus(ChannelPayTypeEnum.AGGREGATE_PAYMENT.getType(), null);
        }
        if (Objects.isNull(payChannelDO)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "支付通道未开启", "");
        }
        // 路由到对应的支付方法中去
        PayService payService = payContext.pay(payChannelDO.getChannelRoute());

        // 新增主订单
        AgentOrderPayPO agentOrderPayPO = new AgentOrderPayPO(payChannelDO, agentAreaPaymentOrderMaster);
        agentOrderPayPO.setFees(PlatformCodeUtil.rate(agentAreaPaymentOrderMaster.getMoney(), payChannelDO.getRate()));
        agentAreaPaymentOrderPayMapper.insertSelective(agentOrderPayPO);

        // 组装通道请求参数
        PayServiceDto payServiceDto = new PayServiceDto();
        payServiceDto.setIp(ip);
        PayDto payDto = setPayDto(payChannelDO, agentOrderPayPO, payServiceDto, agentAreaPaymentOrderMaster.getChannelId());
        // 请求通道
        ApiRes<PayChennleVo> merchantPayChennleResApiRes = payService.pay(payDto);
        // 更新支付订单状态
        PayChennleVo payChennleVo = merchantPayChennleResApiRes.getData();
        AgentOrderPayPO updateAgentOrderPayPO = new AgentOrderPayPO(agentOrderPayPO.getId(), payChennleVo);
        agentAreaPaymentOrderPayMapper.updateByPrimaryKeySelective(updateAgentOrderPayPO);
        if (!ResponseStatus.DATA_EMPTY.getCode().toString().equals(payChennleVo.getCode())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请求失败", "");
        }
        payChennleVo.setMoney(agentAreaPaymentOrderMaster.getMoney() + "");
        payChennleVo.setUid(agentAreaPaymentOrderMaster.getUid());
        payChennleVo.setMerchantName(agentAreaPaymentOrderMaster.getMerchantName());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "下单成功", payChennleVo);
    }

    private PayDto setPayDto(PayChannelDO payChannelDO, AgentOrderPayPO agentOrderPayPO, PayServiceDto payServiceDto, Integer channelId) {
        PayDto payDto = new PayDto();
        payDto.setUrl(payChannelDO.getChannelApiUrl());
        payDto.setShopNo(payChannelDO.getChannelNo());
        payDto.setOrderNo(agentOrderPayPO.getOrderNo());
        payDto.setMoney(agentOrderPayPO.getTotalMoney().multiply(new BigDecimal("100")).intValue());
        payDto.setAuthCode(payServiceDto.getOpenId());
        payDto.setIp(payServiceDto.getIp());
        if (StringUtils.equals("wx", payChannelDO.getChannelRoute())) {
            payDto.setCallbackUrl(payChannelDO.getChannelNotifyUrl() + CallBackConstant.WX_PAYMENT_ON_AGENT_BEHALF_CALL_BACK);
        } else if (StringUtils.equals("jlw", payChannelDO.getChannelRoute())) {
            payDto.setCallbackUrl(payChannelDO.getChannelNotifyUrl() + CallBackConstant.PAYMENT_ON_AGENT_BEHALF_CALL_BACK);
        } else if (StringUtils.equals("sd", payChannelDO.getChannelRoute())) {
            payDto.setCallbackUrl(payChannelDO.getChannelNotifyUrl() + CallBackConstant.SD_PAYMENT_ON_AGENT_BEHALF_CALL_BACK);
        }
        payDto.setOrderName(agentOrderPayPO.getRemark());
        payDto.setSign(payChannelDO.getChannelKey());
        return payDto;
    }


    @Override
    public ApiRes agentSend(PlatformPageDetatilDto platformPageDetatilDto) {
        // 参数校验
        if (Objects.isNull(platformPageDetatilDto)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        AgentAreaPaymentOrderMaster paymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(platformPageDetatilDto.getId());
        if (Objects.isNull(paymentOrderMaster)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费详情不存在", "");
        }
        if (!IsSendEnum.NOT_PUSHED.getIsSend().equals(paymentOrderMaster.getSend())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "状态不对", "");
        }
        // 更新订单推送状态
        AgentOrderMasterPO agentOrderMasterPO = new AgentOrderMasterPO(paymentOrderMaster.getId(), IsSendEnum.PUSHED);
        int ret = 0;
        ret = agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentOrderMasterPO);
        if (ret == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "更新订单推送状态失败");
        }
        String access_token = (String) redisUtils.get("access_token");
        Merchant merchant = merchantMapper.selectByPrimaryKey(paymentOrderMaster.getMerchantId());
        String result = WxUtil.sendSubscriptionMsg(access_token, paymentOrderMaster.getMoney() + "", paymentOrderMaster.getPaymentTypeName(), DateUtil.formatDate(paymentOrderMaster.getExpireTime(), "yyyy年MM月dd日"), merchant.getOpenId());
        JSONObject jsonObject = JSONObject.parseObject(result);
        // 说明订阅通知成功，清除缓存
        if ("0".equals(jsonObject.getString("errcode"))) {
            redisUtils.remove(merchant.getMobile() + CommonConstant.AUTH);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "推送成功", "");
    }

    @Override
    public ApiRes agentSendBack(PlatformPageDetatilDto platformPageDetatilDto) {
        // 参数校验
        if (Objects.isNull(platformPageDetatilDto)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        AgentAreaPaymentOrderMaster paymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(platformPageDetatilDto.getId());
        if (Objects.isNull(paymentOrderMaster)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费详情不存在", "");
        }
        if (!IsSendEnum.PUSHED.getIsSend().equals(paymentOrderMaster.getSend())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "状态不对", "");
        }
        // 更新推送状态
        AgentOrderMasterPO agentOrderMasterPO = new AgentOrderMasterPO(paymentOrderMaster.getId(), IsSendEnum.NOT_PUSHED);
        int ret = agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentOrderMasterPO);
        if (ret == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "更新主订单推送状态失败");
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "推送成功撤回", "");
    }

    @Override
    public ApiRes<RegionalManagerPlatformPageVo> regionalMyBacklog(String managerId, AgentMyBacklogDTO agentMyBacklogDTO) {
        PageHelper.startPage(agentMyBacklogDTO.getPage(), agentMyBacklogDTO.getCapacity());
        List<RegionalManagerPlatformPageVo> regionalManagerPlatformPageVoList = agentAreaPaymentOrderMasterMapper.regionalMyBacklog(managerId, agentMyBacklogDTO.getType());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(regionalManagerPlatformPageVoList)));
    }

    @Override
    public ApiRes<FutureBacklogVO> futureBacklog(Integer managerId) {
        String month = DateFormatUtils.format(new Date(), "MM");
        List<FutureBacklogVO> futureBacklogVOS = agentAreaPaymentOrderMasterMapper.listFutureBacklog(managerId, null, Integer.parseInt(month));
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", futureBacklogVOS);
    }

    @Override
    public ApiRes<FutureBacklogDetailVO> futureBacklogDetail(Integer managerId, FutureBacklogDetailDTO futureBacklogDetailDTO) {
        if (futureBacklogDetailDTO == null || StringUtils.isBlank(futureBacklogDetailDTO.getCollectionMonth())) {
            return ApiRes.failed("请求参数不能为空");
        }
        String month = DateFormatUtils.format(new Date(), "MM");
        List<FutureBacklogVO> futureBacklogVOS = agentAreaPaymentOrderMasterMapper.listFutureBacklog(managerId, Integer.parseInt(futureBacklogDetailDTO.getCollectionMonth()), Integer.parseInt(month));
        FutureBacklogDetailVO futureBacklogDetailVO = new FutureBacklogDetailVO();
        futureBacklogDetailVO.setCollectionMonth(futureBacklogVOS.get(0).getCollectionMonth());
        futureBacklogDetailVO.setCount(futureBacklogVOS.get(0).getCount());
        List<RegionalManagerPlatformPageVo> regionalManagerPlatformPageVos = agentAreaPaymentOrderMasterMapper.listFutureBacklogOrder(managerId, Integer.parseInt(futureBacklogDetailDTO.getCollectionMonth()));
        futureBacklogDetailVO.setRegionalManagerPlatformPageVos(regionalManagerPlatformPageVos);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", futureBacklogDetailVO);
    }

    @Override
    public ApiRes<PageWrap<PaymentRecordMerchantVo>> agentPaymentRecord(String managerId, int page, int capacity) {
        PageHelper.startPage(page, capacity);
        List<PaymentRecordMerchantVo> list = agentAreaPaymentOrderMasterMapper.regionalPaymentRecord(managerId);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes<IsShowQrcodeVO> isShowQrcode() {
        IsShowQrcodeVO isShowQrcodeVO = dictMapper.getIntVal();
        if (Objects.isNull(isShowQrcodeVO)) {
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", "");
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", isShowQrcodeVO);
    }

}
