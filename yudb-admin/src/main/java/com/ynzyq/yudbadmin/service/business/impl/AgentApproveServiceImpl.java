package com.ynzyq.yudbadmin.service.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.*;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.enums.AdjustmentTypeEnum;
import com.ynzyq.yudbadmin.exception.BaseException;
import com.ynzyq.yudbadmin.service.business.IAgentApproveService;
import com.ynzyq.yudbadmin.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xinchen
 * @date 2021/12/24 15:31
 * @description:
 */
@Slf4j
@Service
public class AgentApproveServiceImpl implements IAgentApproveService {

    @Resource
    ApproveProcessStepMapper approveProcessStepMapper;

    @Resource
    AgentAreaPaymentOrderExamineMapper agentAreaPaymentOrderExamineMapper;

    @Resource
    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;

    @Resource
    AgentAreaPaymentOrderExamineDeailMapper agentAreaPaymentOrderExamineDeailMapper;

    @Resource
    AgentAreaPaymentOrderExamineDeailFileMapper agentAreaPaymentOrderExamineDeailFileMapper;

    @Resource
    MerchantAgentAreaMapper merchantAgentAreaMapper;

    @Resource
    ApproveProcessMapper approveProcessMapper;

    @Value("${imageUrl}")
    private String imageUrl;

    @Override
    public ApiRes<ShowStoreExamineVO> showStoreExamine(PageWrap<ShowStoreExamineDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<ShowStoreExamineVO> showStoreExamineVOS = agentAreaPaymentOrderExamineMapper.showStoreExamine(pageWrap.getModel());
        showStoreExamineVOS.forEach(showStoreExamineVO -> {
            if (StringUtils.isBlank(showStoreExamineVO.getApproveName())) {
                String approveName = agentAreaPaymentOrderExamineMapper.getApproveName(Integer.parseInt(showStoreExamineVO.getId()));
                showStoreExamineVO.setApproveName(approveName);
            }
            showStoreExamineVO.setExamineType(AdjustmentTypeEnum.getName(showStoreExamineVO.getExamineType()));
        });
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(showStoreExamineVOS)));
    }

    @Override
    public ApiRes<ShowStoreExamineVO> listStoreExamine(PageWrap<ShowStoreExamineDTO> pageWrap) {
        // 当前登录人信息
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        ShowStoreExamineDTO showStoreExamineDTO = pageWrap.getModel();
        showStoreExamineDTO.setUserId(loginUserInfo.getId());
        // 该订单列表组成由 已审订单+待审订单
        List<ShowStoreExamineVO> showStoreExamineVOS = agentAreaPaymentOrderExamineMapper.listStoreExamine(showStoreExamineDTO);
        showStoreExamineVOS.forEach(showStoreExamineVO -> {
            if (StringUtils.isBlank(showStoreExamineVO.getApproveName())) {
                String approveName = agentAreaPaymentOrderExamineMapper.getApproveName(Integer.parseInt(showStoreExamineVO.getId()));
                showStoreExamineVO.setApproveName(approveName);
            }
            showStoreExamineVO.setExamineType(AdjustmentTypeEnum.getName(showStoreExamineVO.getExamineType()));
        });
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(showStoreExamineVOS)));
    }

    @Override
    public ApiRes<StoreExamineDetailVO> storeExamineDetail(StoreExamineDetailDTO storeExamineDetailDTO) {
        StoreExamineDetail storeExamineDetail = agentAreaPaymentOrderExamineMapper.storeExamineDetail(Integer.parseInt(storeExamineDetailDTO.getId()));
        List<ApproveStepDetail> approveStepDetails = agentAreaPaymentOrderExamineMapper.listApproveStepDetail(Integer.parseInt(storeExamineDetailDTO.getId()));
        if (CollectionUtils.isEmpty(approveStepDetails) || approveStepDetails.size() == 0) {
            approveStepDetails = Lists.newArrayList();
        }
        approveStepDetails.forEach(approveStepDetail -> {
            // 审核记录图片
            List<ExamineFileDetail> detailPhotos = agentAreaPaymentOrderExamineMapper.listExamineDetailFiles(Integer.parseInt(approveStepDetail.getId()), 4);
            if (CollectionUtils.isEmpty(detailPhotos) || detailPhotos.size() == 0) {
                detailPhotos = Lists.newArrayList();
            } else {
                detailPhotos.forEach(examineFileDetail -> {
                    examineFileDetail.setUrl(imageUrl + examineFileDetail.getUrl());
                });
            }
//            approveStepDetail.setVideos(detailVideos);
            approveStepDetail.setPhotos(detailPhotos);
        });
        // 审核单视频
        List<ExamineFileDetail> videos = agentAreaPaymentOrderExamineMapper.listExamineFiles(Integer.parseInt(storeExamineDetailDTO.getId()), 3);
        if (CollectionUtils.isEmpty(videos) || videos.size() == 0) {
            videos = Lists.newArrayList();
        } else {
            videos.forEach(examineFileDetail -> {
                examineFileDetail.setUrl(imageUrl + examineFileDetail.getUrl());
            });
        }
        // 审核单图片
        List<ExamineFileDetail> photos = agentAreaPaymentOrderExamineMapper.listExamineFiles(Integer.parseInt(storeExamineDetailDTO.getId()), 4);
        if (CollectionUtils.isEmpty(photos) || photos.size() == 0) {
            photos = Lists.newArrayList();
        } else {
            photos.forEach(examineFileDetail -> {
                examineFileDetail.setUrl(imageUrl + examineFileDetail.getUrl());
            });
        }
        StoreExamineDetailVO storeExamineDetailVO = new StoreExamineDetailVO();
        storeExamineDetailVO.setStoreExamineDetail(storeExamineDetail);
        storeExamineDetailVO.setApproveStepDetails(approveStepDetails);
        storeExamineDetailVO.setVideos(videos);
        storeExamineDetailVO.setPhotos(photos);
        return ApiRes.successResponseData(storeExamineDetailVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiRes examineRefuse(ExamineDTO examineDTO) {
        AgentAreaPaymentOrderExamine paymentOrderExamine = agentAreaPaymentOrderExamineMapper.selectByPrimaryKey(examineDTO.getId());
        if (paymentOrderExamine == null) {
            return ApiRes.failResponse("审批单不存在");
        }
        AgentAreaPaymentOrderMaster paymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(paymentOrderExamine.getPaymentOrderMasterId());
        if (paymentOrderMaster == null) {
            return ApiRes.failResponse("主订单不存在");
        }
        // 当前登录人信息
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        // 查询在待审批状态的审核记录，该记录在某一环节(非终审)审核完以后会生成
        Example example = new Example(AgentAreaPaymentOrderExamineDeail.class);
        example.createCriteria()
                .andEqualTo("paymentOrderExamineId", paymentOrderExamine.getId())
                .andEqualTo("status", 1)
                .andEqualTo("deleted", 0);
        AgentAreaPaymentOrderExamineDeail paymentOrderExamineDeail = agentAreaPaymentOrderExamineDeailMapper.selectOneByExample(example);
        Integer userStepId;
        // 如果是空，则说明是初审
        if (paymentOrderExamineDeail == null) {
            // 判断是否有审核权限
            userStepId = approveProcessStepMapper.getUserStepId(paymentOrderExamine.getExamineType(), 1, loginUserInfo.getId(), 2);
            if (userStepId == null) {
                return ApiRes.failResponse("未配置该审核类型的审核流或你在该步骤没有审核权限");
            }
            // 添加待审核状态审批记录
            AgentAreaPaymentOrderExamineDeail addPaymentOrderExamineDeail = addPaymentOrderExamineDeail(paymentOrderExamine, userStepId);
            paymentOrderExamineDeail = addPaymentOrderExamineDeail;
        } else {
            userStepId = approveProcessStepMapper.getUserStepId(paymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), loginUserInfo.getId(), 2);
            if (userStepId == null) {
                return ApiRes.failResponse("未配置该审核类型的审核流或你在该步骤没有审核权限");
            }
        }
        // 修改审批记录为拒绝失败
        updateExamineDetail(paymentOrderExamineDeail, examineDTO, loginUserInfo, userStepId, 3);

        // 添加审批图片信息
        addExamineDetailFile(examineDTO, paymentOrderExamineDeail);

        // 修改审批单审批信息
        updateExamine(examineDTO, paymentOrderExamine, paymentOrderExamineDeail.getStep());

        // 修改主订单审批信息
        updateOrderMaster(paymentOrderExamine, paymentOrderMaster);

        return ApiRes.successResponse();
    }

    private AgentAreaPaymentOrderExamineDeail addPaymentOrderExamineDeail(AgentAreaPaymentOrderExamine paymentOrderExamine, Integer userStepId) {
        AgentAreaPaymentOrderExamineDeail addPaymentOrderExamineDeail = new AgentAreaPaymentOrderExamineDeail();
        addPaymentOrderExamineDeail.setPaymentOrderExamineId(paymentOrderExamine.getId());
        addPaymentOrderExamineDeail.setStepId(userStepId);
        addPaymentOrderExamineDeail.setStep(1);
        addPaymentOrderExamineDeail.setType(1);
        // 查询该阶段的审核人
        List<String> approveNames = approveProcessMapper.getApproveName(2, paymentOrderExamine.getExamineType(), addPaymentOrderExamineDeail.getStep(), addPaymentOrderExamineDeail.getType());
        String approveName = approveNames.stream().collect(Collectors.joining(","));
        addPaymentOrderExamineDeail.setApproveName(approveName);
        addPaymentOrderExamineDeail.setStatus(1);
        addPaymentOrderExamineDeail.setCreateTime(new Date());
        if (agentAreaPaymentOrderExamineDeailMapper.insertSelective(addPaymentOrderExamineDeail) == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "添加待审核状态审批记录失败");
        }
        return addPaymentOrderExamineDeail;
    }

    private void updateOrderMaster(AgentAreaPaymentOrderExamine paymentOrderExamine, AgentAreaPaymentOrderMaster paymentOrderMaster) {
        AgentAreaPaymentOrderMaster updatePaymentOrderMaster = new AgentAreaPaymentOrderMaster();
        updatePaymentOrderMaster.setId(paymentOrderMaster.getId());
        // 将缴费修改为未审核状态
        updatePaymentOrderMaster.setExamine(1);
        // 根据调整次数进行判断
        if (paymentOrderMaster.getAdjustmentCount().equals(0)) {
            // 如果这笔订单是区域经理自己生成的还需要将这笔订单的订单状态改为审核拒绝
            if (paymentOrderExamine.getExamineType() == 3 || (paymentOrderMaster.getPaymentTypeId().equals(1) && paymentOrderMaster.getType().equals(1))) {
                updatePaymentOrderMaster.setStatus(4);
            }
        } else {
            if (StringUtils.isBlank(paymentOrderMaster.getAdjustmentMsg())) {
                updatePaymentOrderMaster.setAdjustmentMsg(paymentOrderExamine.getMsg() + "(拒绝)");
            } else {
                updatePaymentOrderMaster.setAdjustmentMsg(paymentOrderMaster.getAdjustmentMsg() + ";" + paymentOrderExamine.getMsg() + "(拒绝)");
            }
        }
        if (agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(updatePaymentOrderMaster) == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "主订单审批信息修改失败");
        }
    }

    private void updateExamine(ExamineDTO examineDTO, AgentAreaPaymentOrderExamine paymentOrderExamine, Integer step) {
        AgentAreaPaymentOrderExamine updatePaymentOrderExamine = new AgentAreaPaymentOrderExamine();
        updatePaymentOrderExamine.setId(paymentOrderExamine.getId());
        updatePaymentOrderExamine.setExamine(0);
        updatePaymentOrderExamine.setRefuse(examineDTO.getMsg());
        updatePaymentOrderExamine.setStatus(3);
        updatePaymentOrderExamine.setCompleteTime(new Date());
        updatePaymentOrderExamine.setStep(step);
        if (agentAreaPaymentOrderExamineMapper.updateByPrimaryKeySelective(updatePaymentOrderExamine) == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "审批单信息修改失败");
        }
    }

    private void addExamineDetailFile(ExamineDTO examineDTO, AgentAreaPaymentOrderExamineDeail paymentOrderExamineDeail) {
        if (examineDTO.getImages().size() > 0) {
            List<AgentAreaPaymentOrderExamineDeailFile> list = Lists.newArrayList();
            examineDTO.getImages().forEach(
                    url -> {
                        AgentAreaPaymentOrderExamineDeailFile paymentOrderExamineDeailFile = new AgentAreaPaymentOrderExamineDeailFile();
                        paymentOrderExamineDeailFile.setAgentAreaPaymentOrderExamineDeailId(paymentOrderExamineDeail.getId());
                        paymentOrderExamineDeailFile.setType(4);
                        paymentOrderExamineDeailFile.setUrl(url);
                        paymentOrderExamineDeailFile.setStatus(1);
                        paymentOrderExamineDeailFile.setCreateTime(new Date());
                        paymentOrderExamineDeailFile.setDeleted(0);
                        list.add(paymentOrderExamineDeailFile);
                    }
            );
            if (agentAreaPaymentOrderExamineDeailFileMapper.insertList(list) == 0) {
                throw new BaseException(CommonConstant.FAIL_CODE, "添加审批流程图片失败");
            }
        }
    }

    private void updateExamineDetail(AgentAreaPaymentOrderExamineDeail paymentOrderExamineDeail, ExamineDTO examineDTO, LoginUserInfo loginUserInfo, Integer stepId, Integer status) {
        AgentAreaPaymentOrderExamineDeail udpatePaymentOrderExamineDeail = new AgentAreaPaymentOrderExamineDeail();
        udpatePaymentOrderExamineDeail.setId(paymentOrderExamineDeail.getId());
        udpatePaymentOrderExamineDeail.setStepId(stepId);
        udpatePaymentOrderExamineDeail.setStatus(status);
        udpatePaymentOrderExamineDeail.setRemark(examineDTO.getMsg());
        udpatePaymentOrderExamineDeail.setCreateUser(loginUserInfo.getId());
        udpatePaymentOrderExamineDeail.setCreateName(loginUserInfo.getRealname());
        udpatePaymentOrderExamineDeail.setExamineTime(new Date());
        udpatePaymentOrderExamineDeail.setUpdateTime(new Date());
        if (agentAreaPaymentOrderExamineDeailMapper.updateByPrimaryKeySelective(udpatePaymentOrderExamineDeail) == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "修改审批记录为拒绝失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiRes examineAgree(ExamineDTO examineDTO) {
        AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine = agentAreaPaymentOrderExamineMapper.selectByPrimaryKey(examineDTO.getId());
        if (agentAreaPaymentOrderExamine == null) {
            return ApiRes.failResponse("审批单不存在");
        }
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.selectByPrimaryKey(agentAreaPaymentOrderExamine.getPaymentOrderMasterId());
        if (agentAreaPaymentOrderMaster == null) {
            return ApiRes.failResponse("主订单不存在");
        }

        // 当前登录人信息
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        // 查询在待审批状态的审核记录，该记录在某一环节(非终审)审核完以后会生成
        Example example = new Example(PaymentOrderExamineDeail.class);
        example.createCriteria()
                .andEqualTo("paymentOrderExamineId", agentAreaPaymentOrderExamine.getId())
                .andEqualTo("status", 1)
                .andEqualTo("deleted", 0);
        AgentAreaPaymentOrderExamineDeail paymentOrderExamineDeail = agentAreaPaymentOrderExamineDeailMapper.selectOneByExample(example);
        Integer userStepId;
        // 如果是空，则说明是初审
        if (paymentOrderExamineDeail == null) {
            // 判断是否有审核权限
            userStepId = approveProcessStepMapper.getUserStepId(agentAreaPaymentOrderExamine.getExamineType(), 1, loginUserInfo.getId(), 2);
            if (userStepId == null) {
                return ApiRes.failResponse("未配置该审核类型的审核流或你在该步骤没有审核权限");
            }
            // 添加待审核状态审批记录
            AgentAreaPaymentOrderExamineDeail addPaymentOrderExamineDeail = addPaymentOrderExamineDeail(agentAreaPaymentOrderExamine, userStepId);
            paymentOrderExamineDeail = addPaymentOrderExamineDeail;
        } else {
            userStepId = approveProcessStepMapper.getUserStepId(agentAreaPaymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), loginUserInfo.getId(), 2);
            if (userStepId == null) {
                return ApiRes.failResponse("未配置该审核类型的审核流或你在该步骤没有审核权限");
            }
        }
        // 添加同意状态的审批记录
        updateExamineDetail(paymentOrderExamineDeail, examineDTO, loginUserInfo, userStepId, 2);
        // 添加审批图片信息
        addExamineDetailFile(examineDTO, paymentOrderExamineDeail);

        Integer maxStep = approveProcessStepMapper.getMaxStep(agentAreaPaymentOrderExamine.getExamineType(), 2);

        // 修改审批记录后重新查询信息
        AgentAreaPaymentOrderExamineDeail newPaymentOrderExamineDeail = agentAreaPaymentOrderExamineDeailMapper.selectByPrimaryKey(paymentOrderExamineDeail.getId());
        // 获取当前审核步骤信息
        ApproveProcessStep approveProcessStep = approveProcessStepMapper.selectByPrimaryKey(newPaymentOrderExamineDeail.getStepId());
        Integer stepId = null;
        // 判断当前步骤是否有会签人
        if (approveProcessStep.getType().equals(1)) {
            stepId = approveProcessStepMapper.getUserNextStepId(approveProcessStep.getStep(), approveProcessStep.getApproveId(), 2);
        }
        // 判断该审核是否为终审，相同即且无会签人则为终审
        if (paymentOrderExamineDeail.getStep().equals(maxStep) && stepId == null) {
            // 不同的审核类型修改不同的字段
            // 审核类型：1、金额调整 2、延期支付 3、新订单审核 4、取消费用，5：线下支付
            AgentAreaPaymentOrderMaster updateAgentAreaPaymentOrderMaster = new AgentAreaPaymentOrderMaster();
            updateAgentAreaPaymentOrderMaster.setId(agentAreaPaymentOrderMaster.getId());
            if (agentAreaPaymentOrderExamine.getExamineType().equals(1)) {
                updateAgentAreaPaymentOrderMaster.setMoney(agentAreaPaymentOrderExamine.getNewMoney());
            } else if (agentAreaPaymentOrderExamine.getExamineType().equals(2)) {
                updateAgentAreaPaymentOrderMaster.setExpireTime(agentAreaPaymentOrderExamine.getNewTime());
            } else if (agentAreaPaymentOrderExamine.getExamineType().equals(4)) {
                updateAgentAreaPaymentOrderMaster.setStatus(3);
            }
            updateAgentAreaPaymentOrderMaster.setExamine(1);
            if (agentAreaPaymentOrderExamine.getExamineType() == 4) {//取消费用
                MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.queryByUid(agentAreaPaymentOrderMaster.getUid());
                Date newTime = DateUtil.endTime("1年后", merchantAgentArea.getServiceExpireTime());
                merchantAgentArea.setAlreadyManagementExpense(agentAreaPaymentOrderMaster.getMoney());
                merchantAgentArea.setServiceExpireTime(newTime);
                merchantAgentAreaMapper.updateByPrimaryKeySelective(merchantAgentArea);
            }
            if (agentAreaPaymentOrderExamine.getExamineType() == 5) {//线下支付逻辑处理
                updateAgentAreaPaymentOrderMaster.setPayTime(new Date());
                updateAgentAreaPaymentOrderMaster.setPayType(2);
                updateAgentAreaPaymentOrderMaster.setStatus(2);
                MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.queryByUid(agentAreaPaymentOrderMaster.getUid());
                Date newTime = DateUtil.endTime("1年后", merchantAgentArea.getServiceExpireTime());
                merchantAgentArea.setAlreadyManagementExpense(agentAreaPaymentOrderMaster.getMoney());
                merchantAgentArea.setServiceExpireTime(newTime);
                merchantAgentAreaMapper.updateByPrimaryKeySelective(merchantAgentArea);
            }
            if (agentAreaPaymentOrderMaster.getAdjustmentCount() > 0) {
                if (StringUtils.isEmpty(agentAreaPaymentOrderMaster.getAdjustmentMsg())) {
                    updateAgentAreaPaymentOrderMaster.setAdjustmentMsg(agentAreaPaymentOrderExamine.getMsg() + "(通过)");
                } else {
                    updateAgentAreaPaymentOrderMaster.setAdjustmentMsg(agentAreaPaymentOrderMaster.getAdjustmentMsg() + ";" + agentAreaPaymentOrderExamine.getMsg() + "(通过)");
                }
            }
            agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(updateAgentAreaPaymentOrderMaster);

            //如果是管理费，且是金额调整，需要修改管理费需缴纳金额字段
            if (agentAreaPaymentOrderMaster.getPaymentTypeId() == 1 && agentAreaPaymentOrderExamine.getExamineType() == 1) {
                MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.queryByUid(agentAreaPaymentOrderMaster.getUid());
                merchantAgentArea.setNeedManagementExpense(agentAreaPaymentOrderMaster.getMoney());
                merchantAgentAreaMapper.updateByPrimaryKeySelective(merchantAgentArea);
            }

            AgentAreaPaymentOrderExamine updateAgentAreaPaymentOrderExamine = new AgentAreaPaymentOrderExamine();
            updateAgentAreaPaymentOrderExamine.setId(agentAreaPaymentOrderExamine.getId());
            updateAgentAreaPaymentOrderExamine.setExamine(0);
            updateAgentAreaPaymentOrderExamine.setStatus(2);
            updateAgentAreaPaymentOrderExamine.setStep(newPaymentOrderExamineDeail.getStep());
            agentAreaPaymentOrderExamineMapper.updateByPrimaryKeySelective(updateAgentAreaPaymentOrderExamine);
        } else {
            // 新增下一人的待审核记录
            AgentAreaPaymentOrderExamineDeail addPaymentOrderExamineDeail = new AgentAreaPaymentOrderExamineDeail();
            addPaymentOrderExamineDeail.setPaymentOrderExamineId(agentAreaPaymentOrderExamine.getId());
            if (stepId != null) {
                addPaymentOrderExamineDeail.setStep(paymentOrderExamineDeail.getStep());
                addPaymentOrderExamineDeail.setType(2);
            } else {
                addPaymentOrderExamineDeail.setStep(paymentOrderExamineDeail.getStep() + 1);
                addPaymentOrderExamineDeail.setType(1);
            }
            // 查询该阶段的审核人
            List<String> approveNames = approveProcessMapper.getApproveName(2, agentAreaPaymentOrderExamine.getExamineType(), addPaymentOrderExamineDeail.getStep(), addPaymentOrderExamineDeail.getType());
            String approveName = approveNames.stream().collect(Collectors.joining(","));
            addPaymentOrderExamineDeail.setApproveName(approveName);
            addPaymentOrderExamineDeail.setStatus(1);
            addPaymentOrderExamineDeail.setCreateTime(new Date());
            agentAreaPaymentOrderExamineDeailMapper.insertSelective(addPaymentOrderExamineDeail);
            // 修改审批单信息
            AgentAreaPaymentOrderExamine updatePaymentOrderExamine = new AgentAreaPaymentOrderExamine();
            updatePaymentOrderExamine.setId(agentAreaPaymentOrderExamine.getId());
            updatePaymentOrderExamine.setExamine(2);
//            updatePaymentOrderExamine.setStatus(2);
            if (stepId != null) {
                updatePaymentOrderExamine.setStep(paymentOrderExamineDeail.getStep());
            } else {
                updatePaymentOrderExamine.setStep(paymentOrderExamineDeail.getStep() + 1);
            }
            if (agentAreaPaymentOrderExamineMapper.updateByPrimaryKeySelective(updatePaymentOrderExamine) == 0) {
                throw new BaseException(CommonConstant.FAIL_CODE, "审批单信息修改失败");
            }
        }
        return ApiRes.successResponse();
    }
}
