package com.ynzyq.yudbadmin.service.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.*;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewDetailDto;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewDto;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.PopupDto;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.AgentPayReviewService;
import com.ynzyq.yudbadmin.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AgentPayReviewServiceImpl implements AgentPayReviewService {
    @Resource
    AgentAreaPaymentOrderExamineMapper agentAreaPaymentOrderExamineMapper;
    @Resource
    AgentAreaPaymentOrderExamineDeailMapper agentAreaPaymentOrderExamineDeailMapper;
    @Resource
    AgentAreaPaymentOrderExamineDeailFileMapper agentAreaPaymentOrderExamineDeailFileMapper;
    @Value("${imageUrl}")
    private String imageurl;

    @Resource
    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;
    @Resource
    MerchantAgentAreaMapper merchantAgentAreaMapper;
    @Resource
    AgentAreaPaymentOrderExamineFileMapper agentAreaPaymentOrderExamineFileMapper;

    @Resource
    AgentAreaPaymentOrderPayMapper agentAreaPaymentOrderPayMapper;

    @Override
    public ApiRes<PageWrap<PayReviewPageVo>> findPage(PageWrap<PayReviewPageDto> pageWrap) {
        PayReviewPageDto payReviewPageDto = pageWrap.getModel();
        if (payReviewPageDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String paymentTypeId = payReviewPageDto.getPaymentTypeId();
        if (StringUtils.isEmpty(paymentTypeId)) {
            payReviewPageDto.setPaymentTypeId(null);
        }
        String condition = payReviewPageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            payReviewPageDto.setCondition(null);
        }
        if (StringUtils.isEmpty(payReviewPageDto.getEndTime())) {
            payReviewPageDto.setEndTime(null);
            payReviewPageDto.setStartTime(null);
        } else {
            String startTime = payReviewPageDto.getStartTime();
            String endTime = payReviewPageDto.getEndTime();
            startTime = startTime + " 00:00:00";
            endTime = endTime + " 23:59:59";
            payReviewPageDto.setEndTime(endTime);
            payReviewPageDto.setStartTime(startTime);
        }
        payReviewPageDto.setExamineOneStatus(StringUtils.isBlank(payReviewPageDto.getExamineOneStatus()) ? null : payReviewPageDto.getExamineOneStatus());
        payReviewPageDto.setExamineTwoStatus(StringUtils.isBlank(payReviewPageDto.getExamineTwoStatus()) ? null : payReviewPageDto.getExamineTwoStatus());
        payReviewPageDto.setExamineType(StringUtils.isBlank(payReviewPageDto.getExamineType()) ? null : payReviewPageDto.getExamineType());
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<PayReviewPageVo> payReviewPageVoList = agentAreaPaymentOrderExamineMapper.findPayReviewPageVo(payReviewPageDto);
        payReviewPageVoList.stream().forEach(
                payReviewPageVo -> {
                    switch (payReviewPageVo.getExamineType()) {
                        case "1":
                            payReviewPageVo.setExamineType("金额调整");
                            break;
                        case "2":
                            payReviewPageVo.setExamineType("延期支付");
                            break;
                        case "3":
                            payReviewPageVo.setExamineType("缴费内容审核");
                            break;
                        case "4":
                            payReviewPageVo.setExamineType("取消费用");
                            break;
                        default:
                            payReviewPageVo.setExamineType("线下已收款");

                    }

                    if (!StringUtils.isEmpty(payReviewPageVo.getExamineOneStatus())) {
                        switch (payReviewPageVo.getExamineOneStatus()) {
                            case "1":
                                payReviewPageVo.setExamineOneStatus("待审核");
                                payReviewPageVo.setExamineTwoStatus("");
                                break;
                            case "2":
                                payReviewPageVo.setExamineOneStatus("审核通过");
                                break;
                            case "3":
                                payReviewPageVo.setExamineOneStatus("拒绝");
                                break;
                            default:
                                payReviewPageVo.setExamineOneStatus("");
                        }
                    } else {
                        payReviewPageVo.setExamineOneStatus("");
                    }
                    if (!StringUtils.isEmpty(payReviewPageVo.getExamineTwoStatus())) {
                        switch (payReviewPageVo.getExamineTwoStatus()) {
                            case "1":
                                payReviewPageVo.setExamineTwoStatus("待审核");
                                break;
                            case "2":
                                payReviewPageVo.setExamineTwoStatus("审核通过");
                                break;
                            default:
                                payReviewPageVo.setExamineTwoStatus("拒绝");
                        }
                    } else {
                        payReviewPageVo.setExamineTwoStatus("");
                    }
                }
        );
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(payReviewPageVoList)));
    }


    @Override
    public ApiRes<PopupVo> popup(PopupDto popupDto) {
        if (popupDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = popupDto.getId();
        AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine = agentAreaPaymentOrderExamineMapper.selectByPrimaryKey(id);
        if (agentAreaPaymentOrderExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核订单不存在", "");
        }
        String type = popupDto.getType();
        if (!"1".equals(type) && !"2".equals(type)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        PopupVo popupVo = new PopupVo();
        popupVo.setReviewMsg(agentAreaPaymentOrderExamine.getMsg());
        switch (agentAreaPaymentOrderExamine.getExamineType()) {
            case 1:
                popupVo.setExamineType("金额调整");
                break;
            case 2:
                popupVo.setExamineType("延期支付");
                break;
            case 3:
                popupVo.setExamineType("缴费内容审核");
                break;
            case 4:
                popupVo.setExamineType("取消费用");
                break;
            default:
                popupVo.setExamineType("线下已收款");
        }
        popupVo.setReviewMsg(agentAreaPaymentOrderExamine.getMsg());
        if ("2".equals(type) && agentAreaPaymentOrderExamine.getExamine() == 2) {
            //查询一审内容
            AgentAreaPaymentOrderExamineDeail paymentOrderExamineDeail = agentAreaPaymentOrderExamineDeailMapper.queryByOne(agentAreaPaymentOrderExamine);
            popupVo.setExamineOneMsg(paymentOrderExamineDeail.getRemark());
            popupVo.setExamineOneName(paymentOrderExamineDeail.getCreateName());
            //查询一审的审批照片
            List<String> images = agentAreaPaymentOrderExamineDeailFileMapper.queryUrlByPaymentOrderExamineDeailId(paymentOrderExamineDeail.getId(), imageurl);
            popupVo.setImages(images);
            popupVo.setUrl(imageurl);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", popupVo);
    }


    @Override
    public ApiRes one(PayReviewDto payReviewDto) {
        if (payReviewDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = payReviewDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String status = payReviewDto.getStatus();
        if (!"2".equals(status) && !"3".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");

        }
        String msg = payReviewDto.getMsg();
        AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine = agentAreaPaymentOrderExamineMapper.selectByPrimaryKey(id);
        if (agentAreaPaymentOrderExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核订单不存在", "");
        }
        if (agentAreaPaymentOrderExamine.getExamine() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");

        }
        //查询一审内容
        AgentAreaPaymentOrderExamineDeail agentAreaPaymentOrderExamineDeail = agentAreaPaymentOrderExamineDeailMapper.queryByOne(agentAreaPaymentOrderExamine);
        if (agentAreaPaymentOrderExamineDeail == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }
        if (agentAreaPaymentOrderExamineDeail.getStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核状态错误", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(agentAreaPaymentOrderExamine.getPaymentOrderMasterId());
        if (agentAreaPaymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "系统内部错误", "");
        }
        List<AgentAreaPaymentOrderExamineDeailFile> agentAreaPaymentOrderExamineDeailFiles = new ArrayList<>();
        List<String> images = payReviewDto.getImages();
        if (images.size() > 0) {
            images.stream().forEach(
                    s -> {
                        AgentAreaPaymentOrderExamineDeailFile agentAreaPaymentOrderExamineDeailFile = new AgentAreaPaymentOrderExamineDeailFile();
                        agentAreaPaymentOrderExamineDeailFile.setAgentAreaPaymentOrderExamineDeailId(agentAreaPaymentOrderExamineDeail.getId());
                        agentAreaPaymentOrderExamineDeailFile.setType(4);
                        agentAreaPaymentOrderExamineDeailFile.setUrl(s);
                        agentAreaPaymentOrderExamineDeailFile.setStatus(1);
                        agentAreaPaymentOrderExamineDeailFile.setCreateTime(new Date());
                        agentAreaPaymentOrderExamineDeailFile.setDeleted(0);
                        agentAreaPaymentOrderExamineDeailFiles.add(agentAreaPaymentOrderExamineDeailFile);
                    }
            );
        }
        agentAreaPaymentOrderExamineDeail.setStatus(Integer.valueOf(status));
        agentAreaPaymentOrderExamineDeail.setRemark(msg);
        agentAreaPaymentOrderExamineDeail.setExamineTime(new Date());
        agentAreaPaymentOrderExamineDeail.setUpdateTime(new Date());
        agentAreaPaymentOrderExamineDeail.setCreateUser(loginUserInfo.getId());
        agentAreaPaymentOrderExamineDeail.setCreateName(loginUserInfo.getRealname());
        //如果是审核通过的生成二审订单明细
        if ("2".equals(status)) {//生成二审订单
            AgentAreaPaymentOrderExamineDeail paymentOrderExamineDeailTwo = new AgentAreaPaymentOrderExamineDeail();
            paymentOrderExamineDeailTwo.setPaymentOrderExamineId(agentAreaPaymentOrderExamine.getId());
            paymentOrderExamineDeailTwo.setExamine(2);
            paymentOrderExamineDeailTwo.setStatus(1);
            paymentOrderExamineDeailTwo.setCreateTime(new Date());
            agentAreaPaymentOrderExamineDeailMapper.insertSelective(paymentOrderExamineDeailTwo);

            //将主订单修改为二审
            agentAreaPaymentOrderExamine.setExamine(2);
            if (agentAreaPaymentOrderMaster.getPaymentTypeId().equals(1) && agentAreaPaymentOrderMaster.getType().equals(1)
                    && agentAreaPaymentOrderMaster.getAdjustmentCount().equals(0)  &&agentAreaPaymentOrderExamine.getExamineType().equals(3)) {
                paymentOrderExamineDeailTwo.setStatus(2);
                agentAreaPaymentOrderExamine.setExamine(0);
                agentAreaPaymentOrderExamineDeailMapper.updateByPrimaryKeySelective(paymentOrderExamineDeailTwo);
            }

        } else {//修改主订单的状态
            //将主订单修改为审核完成
            agentAreaPaymentOrderExamine.setExamine(0);
            agentAreaPaymentOrderExamine.setRefuse(msg);
            agentAreaPaymentOrderExamine.setStatus(3);
            agentAreaPaymentOrderExamine.setCompleteTime(new Date());
            //将缴费修改为未审核状态
            agentAreaPaymentOrderMaster.setExamine(1);
            if (agentAreaPaymentOrderMaster.getType() == 2 && agentAreaPaymentOrderExamine.getExamineType() == 3) {
                //如果这笔订单是区域经理自己生成的还需要将这笔订单的订单状态改为审核拒绝
                agentAreaPaymentOrderMaster.setStatus(4);
            }

            if (agentAreaPaymentOrderMaster.getPaymentTypeId().equals(1) && agentAreaPaymentOrderMaster.getType().equals(1) && agentAreaPaymentOrderMaster.getAdjustmentCount().equals(0)) {
                agentAreaPaymentOrderMaster.setStatus(4);
            }

            if (agentAreaPaymentOrderMaster.getAdjustmentCount() > 0) {
                if (StringUtils.isEmpty(agentAreaPaymentOrderMaster.getAdjustmentMsg())) {
                    agentAreaPaymentOrderMaster.setAdjustmentMsg(agentAreaPaymentOrderExamine.getMsg() + "(拒绝)");
                } else {
                    agentAreaPaymentOrderMaster.setAdjustmentMsg(agentAreaPaymentOrderMaster.getAdjustmentMsg() + ";" + agentAreaPaymentOrderExamine.getMsg() + "(拒绝)");
                }
            }
            agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderMaster);
        }
        agentAreaPaymentOrderExamineDeailMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderExamineDeail);
        agentAreaPaymentOrderExamineMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderExamine);
        if (agentAreaPaymentOrderExamineDeailFiles.size() > 0) {
            agentAreaPaymentOrderExamineDeailFileMapper.insertList(agentAreaPaymentOrderExamineDeailFiles);
        }



        if (agentAreaPaymentOrderMaster.getPaymentTypeId().equals(1) &&
                agentAreaPaymentOrderMaster.getType().equals(1)
                && agentAreaPaymentOrderMaster.getAdjustmentCount().equals(0)
                &&agentAreaPaymentOrderExamine.getExamineType().equals(3)
         && "2".equals(status)) {

            agentAreaPaymentOrderMaster.setExamine(1);
            agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderMaster);
        }




        return ApiRes.response(CommonConstant.SUCCESS_CODE, "审核成功", "");
    }

    @Override
    public ApiRes two(PayReviewDto payReviewDto) {
        if (payReviewDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = payReviewDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String status = payReviewDto.getStatus();
        if (!"2".equals(status) && !"3".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");

        }
        String msg = payReviewDto.getMsg();
        AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine = agentAreaPaymentOrderExamineMapper.selectByPrimaryKey(id);
        if (agentAreaPaymentOrderExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核订单不存在", "");
        }
        if (agentAreaPaymentOrderExamine.getExamine() != 2) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }

        AgentAreaPaymentOrderExamineDeail agentAreaPaymentOrderExamineDeail = agentAreaPaymentOrderExamineDeailMapper.queryByTwo(agentAreaPaymentOrderExamine);
        if (agentAreaPaymentOrderExamineDeail == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }
        if (agentAreaPaymentOrderExamineDeail.getStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核状态错误", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(agentAreaPaymentOrderExamine.getPaymentOrderMasterId());
        if (agentAreaPaymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "系统内部错误", "");
        }

        agentAreaPaymentOrderExamineDeail.setStatus(Integer.valueOf(status));
        agentAreaPaymentOrderExamineDeail.setRemark(msg);
        agentAreaPaymentOrderExamineDeail.setExamineTime(new Date());
        agentAreaPaymentOrderExamineDeail.setUpdateTime(new Date());
        agentAreaPaymentOrderExamineDeail.setCreateUser(loginUserInfo.getId());
        agentAreaPaymentOrderExamineDeail.setCreateName(loginUserInfo.getRealname());
        List<AgentAreaPaymentOrderExamineDeailFile> paymentOrderExamineDeailFiles = new ArrayList<>();
        List<String> images = payReviewDto.getImages();
        if (images.size() > 0) {
            images.stream().forEach(
                    s -> {
                        AgentAreaPaymentOrderExamineDeailFile paymentOrderExamineDeailFile = new AgentAreaPaymentOrderExamineDeailFile();
                        paymentOrderExamineDeailFile.setAgentAreaPaymentOrderExamineDeailId(agentAreaPaymentOrderExamineDeail.getId());
                        paymentOrderExamineDeailFile.setType(4);
                        paymentOrderExamineDeailFile.setUrl(s);
                        paymentOrderExamineDeailFile.setStatus(1);
                        paymentOrderExamineDeailFile.setCreateTime(new Date());
                        paymentOrderExamineDeailFile.setDeleted(0);
                        paymentOrderExamineDeailFiles.add(paymentOrderExamineDeailFile);
                    }
            );
        }

        if ("2".equals(status)) {//通过该后要对缴费订单进行操作
            switch (agentAreaPaymentOrderExamine.getExamineType()) {
                case 1:
                    agentAreaPaymentOrderMaster.setMoney(agentAreaPaymentOrderExamine.getNewMoney());
                    break;
                case 2:
                    agentAreaPaymentOrderMaster.setExpireTime(agentAreaPaymentOrderExamine.getNewTime());
                    break;
                case 4:
                    agentAreaPaymentOrderMaster.setStatus(3);
                    break;
                default:
            }
            agentAreaPaymentOrderExamine.setStatus(2);
            if (agentAreaPaymentOrderExamine.getExamineType() == 4) {//取消费用
                agentAreaPaymentOrderMaster.setStatus(3);
            }


            if (agentAreaPaymentOrderExamine.getExamineType() == 5) {//线下支付逻辑处理

                agentAreaPaymentOrderMaster.setPayType(2);
                agentAreaPaymentOrderMaster.setPayType(2);
                agentAreaPaymentOrderMaster.setStatus(2);

                MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.queryByUid(agentAreaPaymentOrderMaster.getUid());
                Date newTime = DateUtil.endTime("1年后", merchantAgentArea.getExpireTime());
                merchantAgentArea.setAlreadyManagementExpense(agentAreaPaymentOrderMaster.getMoney());
                merchantAgentArea.setServiceExpireTime(newTime);
                //对支付订单进行操作
                agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderMaster);
                merchantAgentAreaMapper.updateByPrimaryKeySelective(merchantAgentArea);

            }

            if (agentAreaPaymentOrderMaster.getAdjustmentCount() > 0) {
                if (StringUtils.isEmpty(agentAreaPaymentOrderMaster.getAdjustmentMsg())) {
                    agentAreaPaymentOrderMaster.setAdjustmentMsg(agentAreaPaymentOrderExamine.getMsg() + "(通过)");
                } else {
                    agentAreaPaymentOrderMaster.setAdjustmentMsg(agentAreaPaymentOrderMaster.getAdjustmentMsg() + ";" + agentAreaPaymentOrderExamine.getMsg() + "(通过)");
                }
            }

            if (agentAreaPaymentOrderMaster.getPaymentTypeId() == 1 && agentAreaPaymentOrderExamine.getExamineType() == 1) {//如果是管理费，且是金额调整，需要修改门店的管理费需缴纳金额字段
                MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.queryByUid(agentAreaPaymentOrderMaster.getUid());
                merchantAgentArea.setNeedManagementExpense(agentAreaPaymentOrderMaster.getMoney());
                merchantAgentAreaMapper.updateByPrimaryKeySelective(merchantAgentArea);
            }

        } else {
            agentAreaPaymentOrderExamine.setRefuse(msg);
            agentAreaPaymentOrderExamine.setStatus(3);
            if (agentAreaPaymentOrderMaster.getType() == 2 && agentAreaPaymentOrderExamine.getExamineType() == 3) {
                //如果这笔订单是区域经理自己生成的还需要将这笔订单的订单状态改为审核拒绝
                agentAreaPaymentOrderMaster.setStatus(4);
            }

            if (agentAreaPaymentOrderMaster.getAdjustmentCount() > 0) {
                if (StringUtils.isEmpty(agentAreaPaymentOrderMaster.getAdjustmentMsg())) {
                    agentAreaPaymentOrderMaster.setAdjustmentMsg(agentAreaPaymentOrderExamine.getMsg() + "(拒绝)");
                } else {
                    agentAreaPaymentOrderMaster.setAdjustmentMsg(agentAreaPaymentOrderMaster.getAdjustmentMsg() + ";" + agentAreaPaymentOrderExamine.getMsg() + "(拒绝)");
                }
            }
        }
        agentAreaPaymentOrderExamine.setCompleteTime(new Date());
        //将主订单改为审核完成
        agentAreaPaymentOrderExamine.setExamine(0);
        //将缴费订单修改为未审核状态
        agentAreaPaymentOrderMaster.setExamine(1);
        agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderMaster);
        agentAreaPaymentOrderExamineDeailMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderExamineDeail);
        agentAreaPaymentOrderExamineMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderExamine);
        if (paymentOrderExamineDeailFiles.size() > 0) {
            agentAreaPaymentOrderExamineDeailFileMapper.insertList(paymentOrderExamineDeailFiles);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "审核成功", "");
    }

    @Override
    public ApiRes<PayReviewDetailVo> detail(PayReviewDetailDto payReviewDetailDto) {
        if (payReviewDetailDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = payReviewDetailDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine = agentAreaPaymentOrderExamineMapper.selectByPrimaryKey(id);
        if (agentAreaPaymentOrderExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }

        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(agentAreaPaymentOrderExamine.getPaymentOrderMasterId());
        if (agentAreaPaymentOrderMaster == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        //审核明细
        List<AgentAreaPaymentOrderExamineDeail> agentAreaPaymentOrderExamineDeailList = agentAreaPaymentOrderExamineDeailMapper.queryByPaymentOrderExamineId(agentAreaPaymentOrderExamine.getId());
        //文件明细
        List<AgentAreaPaymentOrderExamineFile> agentAreaPaymentOrderExamineFileList = agentAreaPaymentOrderExamineFileMapper.queryByPaymentOrderExamineId(agentAreaPaymentOrderExamine.getId());

        //数据组装
        PayReviewDetailVo payReviewDetailVo = new PayReviewDetailVo();
        payReviewDetailVo.setExpireTime(agentAreaPaymentOrderMaster.getExpireTime());
        payReviewDetailVo.setUid(agentAreaPaymentOrderMaster.getUid());
        payReviewDetailVo.setMerchantName(agentAreaPaymentOrderMaster.getMerchantName());
        payReviewDetailVo.setMoney(agentAreaPaymentOrderMaster.getMoney() + "");
        payReviewDetailVo.setApplicant(agentAreaPaymentOrderExamine.getApplyName());
        payReviewDetailVo.setCreateTime(agentAreaPaymentOrderExamine.getCreateTime());
        payReviewDetailVo.setReviewMsg(agentAreaPaymentOrderExamine.getMsg());
        payReviewDetailVo.setRemark(agentAreaPaymentOrderExamine.getRemark());
        payReviewDetailVo.setPayTypeName(agentAreaPaymentOrderExamine.getPaymentTypeName());
        switch (agentAreaPaymentOrderExamine.getExamineType()) {
            case 1:
                payReviewDetailVo.setExamineType("金额调整");
                break;
            case 2:
                payReviewDetailVo.setExamineType("延期支付");
                break;
            case 3:
                payReviewDetailVo.setExamineType("缴费内容审核");
                break;
            case 4:
                payReviewDetailVo.setExamineType("取消费用");
                break;
            default:
                payReviewDetailVo.setExamineType("线下已收款");
        }
        List<ExamineDetailVo> examineDetailVoList = new ArrayList<>();
        agentAreaPaymentOrderExamineDeailList.stream().forEach(
                paymentOrderExamineDeail -> {
                    ExamineDetailVo examineDetailVo = new ExamineDetailVo();
                    if (paymentOrderExamineDeail.getExamine() == 1) {
                        examineDetailVo.setName("一审");
                    } else {
                        examineDetailVo.setName("二审");
                    }

                    switch (paymentOrderExamineDeail.getStatus()) {
                        case 1:
                            examineDetailVo.setExamineStatus("待审核");
                            break;
                        case 2:
                            examineDetailVo.setExamineStatus("审核通过");
                            break;
                        default:
                            examineDetailVo.setExamineStatus("拒绝");
                    }
                    examineDetailVo.setExamineMsg(paymentOrderExamineDeail.getRemark());
                    examineDetailVo.setExamineName(paymentOrderExamineDeail.getCreateName());


                    //查询一审的审批照片
                    List<String> images = agentAreaPaymentOrderExamineDeailFileMapper.queryUrlByPaymentOrderExamineDeailId(paymentOrderExamineDeail.getId(), imageurl);
                    examineDetailVo.setImages(images);
                    examineDetailVo.setUrl(imageurl);
                    examineDetailVoList.add(examineDetailVo);
                }
        );
        payReviewDetailVo.setExamineDetailVoList(examineDetailVoList);


        List<ExamineFileVo> examineFileVoList = new ArrayList<>();

        List<String> video = new ArrayList<>();
        List<String> image = new ArrayList<>();
        agentAreaPaymentOrderExamineFileList.stream().forEach(
                paymentOrderExamineFile -> {
                    if (paymentOrderExamineFile.getType() == 4) {
                        image.add(imageurl + paymentOrderExamineFile.getUrl());
                    }
                    if (paymentOrderExamineFile.getType() == 3) {
                        video.add(imageurl + paymentOrderExamineFile.getUrl());

                    }
                }
        );

        ExamineFileVo examineFileVoImage = new ExamineFileVo();
        examineFileVoImage.setName("图片");
        examineFileVoImage.setFileS(image);
        examineFileVoList.add(examineFileVoImage);

        ExamineFileVo examineFileVoVideo = new ExamineFileVo();
        examineFileVoVideo.setName("视频");
        examineFileVoVideo.setFileS(video);
        examineFileVoList.add(examineFileVoVideo);
        payReviewDetailVo.setExamineFileVoList(examineFileVoList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", payReviewDetailVo);
    }

}
