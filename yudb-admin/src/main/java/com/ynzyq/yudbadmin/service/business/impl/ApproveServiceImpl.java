package com.ynzyq.yudbadmin.service.business.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.*;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.dao.business.vo.ApproveProcessStepDTO;
import com.ynzyq.yudbadmin.enums.AdjustmentTypeEnum;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import com.ynzyq.yudbadmin.exception.BaseException;
import com.ynzyq.yudbadmin.service.business.IApproveService;
import com.ynzyq.yudbadmin.util.DateUtil;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xinchen
 * @date 2021/12/24 15:31
 * @description:
 */
@Slf4j
@Service
public class ApproveServiceImpl implements IApproveService {

    @Resource
    ApproveProcessMapper approveProcessMapper;

    @Resource
    ApproveProcessStepMapper approveProcessStepMapper;

    @Resource
    PaymentOrderExamineMapper paymentOrderExamineMapper;

    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;

    @Resource
    PaymentOrderExamineDeailMapper paymentOrderExamineDeailMapper;

    @Resource
    PaymentOrderExamineDeailFileMapper paymentOrderExamineDeailFileMapper;

    @Resource
    MerchantStoreMapper merchantStoreMapper;

    @Resource
    MerchantStoreCloudSchoolMapper merchantStoreCloudSchoolMapper;

    @Value("${imageUrl}")
    private String imageUrl;

    @Override
    public ApiRes<ListApproveProcessVO> listApproveProcess(PageWrap<ListApproveProcessDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<ListApproveProcessVO> listApproveProcessVOS = approveProcessMapper.listApproveProcess(pageWrap.getModel());
        listApproveProcessVOS.forEach(listApproveProcessVO -> {
            listApproveProcessVO.setExamineType(AdjustmentTypeEnum.getName(listApproveProcessVO.getExamineType()));
        });
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(listApproveProcessVOS)));
    }

    @Override
    public ApiRes<ApproveProcessDetailVO> approveProcessDetail(ApproveProcessDetailDTO approveProcessDetailDTO) {
        ApproveProcess approveProcess = approveProcessMapper.selectByPrimaryKey(approveProcessDetailDTO.getId());
        if (approveProcess == null) {
            return ApiRes.failResponse("审批流不存在");
        }
        // 查询该审批流下的所有审批步骤
        List<ApproveProcessStepDTO> approveProcessSteps = approveProcessMapper.listApproveProcessStep(approveProcess.getId());
        ApproveProcessDetailVO approveProcessDetailVO = new ApproveProcessDetailVO();
        approveProcessDetailVO.setId(approveProcess.getId().toString());
        approveProcessDetailVO.setType(approveProcess.getType().toString());
        approveProcessDetailVO.setExamineType(approveProcess.getExamineType().toString());
        List<AddApproveProcessStepDTO> approveProcessStepDTOS = Lists.newArrayList();
        // 根据步骤对集合进行分组
        Map<String, List<ApproveProcessStepDTO>> collect = approveProcessSteps.stream().collect(Collectors.groupingBy(approveProcessStepDTO -> approveProcessStepDTO.getStep() + "_" + approveProcessStepDTO.getName()));
        // 对分组后的数据进行筛选
        collect.forEach((key, approveProcessStepList) -> {
            String[] temp = key.split("_");
            String step = temp[0];
            String name = temp[1];
            AddApproveProcessStepDTO addApproveProcessStepDTO = new AddApproveProcessStepDTO();
            addApproveProcessStepDTO.setStep(step);
            addApproveProcessStepDTO.setName(name);
            // 审批人数组
            List<String> approveUserIds = Lists.newArrayList();
            // 签约人数组
            List<String> signUserIds = Lists.newArrayList();
            approveProcessStepList.forEach(approveProcessStepDTO -> {
                // 根据不同类型把人员划分到审批人和签约人中去
                if (approveProcessStepDTO.getType().equals("1")) {
                    approveUserIds.add(approveProcessStepDTO.getUserId());
                } else if (approveProcessStepDTO.getType().equals("2")) {
                    signUserIds.add(approveProcessStepDTO.getUserId());
                }
                addApproveProcessStepDTO.setApproveUserIds(approveUserIds);
                addApproveProcessStepDTO.setSignUserIds(signUserIds);
            });
            approveProcessStepDTOS.add(addApproveProcessStepDTO);
        });
        approveProcessStepDTOS.sort(Comparator.comparing(AddApproveProcessStepDTO::getStep));
        approveProcessDetailVO.setApproveProcessSteps(approveProcessStepDTOS);
        return ApiRes.successResponseData(approveProcessDetailVO);
    }

    @Override
    public ApiRes<SystemUserVO> userSelectBox() {
        // 查询审批人/会签人
        List<SystemUserVO> systemUserVOS = approveProcessMapper.listUser();
        if (systemUserVOS.size() == 0) {
            systemUserVOS = Lists.newArrayList();
        }
        return ApiRes.successResponseData(systemUserVOS);
    }

    @Override
    public ApiRes<SystemUserVO> examineTypeSelectBox() {
        List<ExamineTypeVO> examineTypeVOS = approveProcessMapper.listExamineType();
        if (examineTypeVOS.size() == 0) {
            examineTypeVOS = Lists.newArrayList();
        }
        examineTypeVOS.forEach(examineTypeVO -> {
            examineTypeVO.setExamineTypeName(AdjustmentTypeEnum.getName(examineTypeVO.getExamineType()));
        });
        return ApiRes.successResponseData(examineTypeVOS);
    }

    @Override
    public ApiRes addApproveProcess(AddApproveProcessDTO addApproveProcessDTO) {
        Example example = new Example(ApproveProcess.class);
        example.createCriteria().andEqualTo("examineType", addApproveProcessDTO.getExamineType())
                .andEqualTo("type", addApproveProcessDTO.getType());
        if (approveProcessMapper.selectCountByExample(example) > 0) {
            return ApiRes.failResponse("已存在相同审核类型的审核流，无法添加");
        }
        ApproveProcess approveProcess = new ApproveProcess();
        approveProcess.setType(Integer.parseInt(addApproveProcessDTO.getType()));
        approveProcess.setStatus(StatusEnum.ENABLE.getStatus());
        approveProcess.setExamineType(Integer.parseInt(addApproveProcessDTO.getExamineType()));
        approveProcess.setCreateTime(new Date());
        approveProcessMapper.insertSelective(approveProcess);
        // 查询审批人/会签人
        List<SystemUserVO> systemUserVOS = approveProcessMapper.listUser();
        Map<String, String> userMap = Maps.newHashMap();
        systemUserVOS.forEach(systemUserVO -> {
            userMap.put(systemUserVO.getUserId(), systemUserVO.getUserName());
        });
        List<ApproveProcessStep> addList = Lists.newArrayList();
        addApproveProcessDTO.getApproveProcessSteps().forEach(approveProcessStepDTO -> {
            approveProcessStepDTO.getApproveUserIds().forEach(userId -> {
                ApproveProcessStep approveProcessStep = new ApproveProcessStep();
                approveProcessStep.setApproveId(approveProcess.getId());
                approveProcessStep.setStep(Integer.parseInt(approveProcessStepDTO.getStep()));
                approveProcessStep.setName(approveProcessStepDTO.getName());
                approveProcessStep.setType(1);
                approveProcessStep.setUserId(Integer.parseInt(userId));
                if (userMap.keySet().contains(userId)) {
                    approveProcessStep.setUserName(userMap.get(userId));
                }
                approveProcessStep.setStatus(StatusEnum.ENABLE.getStatus());
                approveProcessStep.setCreateTime(new Date());
                addList.add(approveProcessStep);
            });
            approveProcessStepDTO.getSignUserIds().forEach(userId -> {
                ApproveProcessStep approveProcessStep = new ApproveProcessStep();
                approveProcessStep.setApproveId(approveProcess.getId());
                approveProcessStep.setStep(Integer.parseInt(approveProcessStepDTO.getStep()));
                approveProcessStep.setName(approveProcessStepDTO.getName());
                approveProcessStep.setType(2);
                approveProcessStep.setUserId(Integer.parseInt(userId));
                if (userMap.keySet().contains(userId)) {
                    approveProcessStep.setUserName(userMap.get(userId));
                }
                approveProcessStep.setStatus(StatusEnum.ENABLE.getStatus());
                approveProcessStep.setCreateTime(new Date());
                addList.add(approveProcessStep);
            });
        });
        approveProcessStepMapper.insertList(addList);
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes updateApproveProcess(UpdateApproveProcessDTO updateApproveProcessDTO) {
        ApproveProcess approveProcess = approveProcessMapper.selectByPrimaryKey(updateApproveProcessDTO.getId());
        if (approveProcess == null) {
            return ApiRes.failResponse("审批流不存在");
        }
        // 查询是否有审核的流程，有就无法修改
//        int usedCount;
//        if (approveProcess.getType().equals(1)) {
//            usedCount = approveProcessMapper.getUsedCount(approveProcess.getExamineType());
//        } else {
//            usedCount = approveProcessMapper.getUsedCount2(approveProcess.getExamineType());
//        }
//        if (usedCount > 0) {
//            return ApiRes.failResponse("该审核流在使用中，无法修改");
//        }
        // 修改审批流
        ApproveProcess updateApproveProcess = new ApproveProcess();
        updateApproveProcess.setId(approveProcess.getId());
        updateApproveProcess.setType(Integer.parseInt(updateApproveProcessDTO.getType()));
        updateApproveProcess.setExamineType(Integer.parseInt(updateApproveProcessDTO.getExamineType()));
        updateApproveProcess.setUpdateTime(new Date());
        if (approveProcessMapper.updateByPrimaryKeySelective(updateApproveProcess) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        // 查询审批人/会签人
        List<SystemUserVO> systemUserVOS = approveProcessMapper.listUser();
        Map<String, String> userMap = Maps.newHashMap();
        systemUserVOS.forEach(systemUserVO -> {
            userMap.put(systemUserVO.getUserId(), systemUserVO.getUserName());
        });
        // 先将原审批流步骤改为不可用
        ApproveProcessStep updateApproveProcessStep = new ApproveProcessStep();
        updateApproveProcessStep.setStatus(StatusEnum.DISABLE.getStatus());
        Example example = new Example(ApproveProcessStep.class);
        example.createCriteria().andEqualTo("approveId", approveProcess.getId());
        approveProcessStepMapper.updateByExampleSelective(updateApproveProcessStep, example);
        List<ApproveProcessStep> addList = Lists.newArrayList();
        updateApproveProcessDTO.getApproveProcessSteps().forEach(approveProcessStepDTO -> {
            approveProcessStepDTO.getApproveUserIds().forEach(userId -> {
                ApproveProcessStep approveProcessStep = new ApproveProcessStep();
                approveProcessStep.setApproveId(approveProcess.getId());
                approveProcessStep.setStep(Integer.parseInt(approveProcessStepDTO.getStep()));
                approveProcessStep.setName(approveProcessStepDTO.getName());
                approveProcessStep.setType(1);
                approveProcessStep.setUserId(Integer.parseInt(userId));
                if (userMap.keySet().contains(userId)) {
                    approveProcessStep.setUserName(userMap.get(userId));
                }
                approveProcessStep.setStatus(StatusEnum.ENABLE.getStatus());
                approveProcessStep.setCreateTime(new Date());
                addList.add(approveProcessStep);
            });
            approveProcessStepDTO.getSignUserIds().forEach(userId -> {
                ApproveProcessStep approveProcessStep = new ApproveProcessStep();
                approveProcessStep.setApproveId(approveProcess.getId());
                approveProcessStep.setStep(Integer.parseInt(approveProcessStepDTO.getStep()));
                approveProcessStep.setName(approveProcessStepDTO.getName());
                approveProcessStep.setType(2);
                approveProcessStep.setUserId(Integer.parseInt(userId));
                if (userMap.keySet().contains(userId)) {
                    approveProcessStep.setUserName(userMap.get(userId));
                }
                approveProcessStep.setStatus(StatusEnum.ENABLE.getStatus());
                approveProcessStep.setCreateTime(new Date());
                addList.add(approveProcessStep);
            });
        });
        approveProcessStepMapper.insertList(addList);
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<ShowStoreExamineVO> showStoreExamine(PageWrap<ShowStoreExamineDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<ShowStoreExamineVO> showStoreExamineVOS = paymentOrderExamineMapper.showStoreExamine(pageWrap.getModel());
        showStoreExamineVOS.forEach(showStoreExamineVO -> {
            if (StringUtils.isBlank(showStoreExamineVO.getApproveName())) {
                List<String> approveNames = paymentOrderExamineMapper.getApproveName(Integer.parseInt(showStoreExamineVO.getId()));
                showStoreExamineVO.setApproveName(approveNames.stream().collect(Collectors.joining(",")));
            }
            showStoreExamineVO.setExamineType(AdjustmentTypeEnum.getName(showStoreExamineVO.getExamineType()));
        });
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(showStoreExamineVOS)));
    }

    @Override
    public void exportShowStoreExamine(ShowStoreExamineDTO showStoreExamineDTO, HttpServletResponse httpServletResponse) {
        List<ShowStoreExamineVO> showStoreExamineVOS = paymentOrderExamineMapper.showStoreExamine(showStoreExamineDTO);
        showStoreExamineVOS.forEach(showStoreExamineVO -> {
            if (StringUtils.isBlank(showStoreExamineVO.getApproveName())) {
                List<String> approveNames = paymentOrderExamineMapper.getApproveName(Integer.parseInt(showStoreExamineVO.getId()));
                showStoreExamineVO.setApproveName(approveNames.stream().collect(Collectors.joining(",")));
            }
            showStoreExamineVO.setExamineType(AdjustmentTypeEnum.getName(showStoreExamineVO.getExamineType()));
            if (StringUtils.equals("1", showStoreExamineVO.getStatus())) {
                showStoreExamineVO.setStatus("审核中");
            } else if (StringUtils.equals("2", showStoreExamineVO.getStatus())) {
                showStoreExamineVO.setStatus("通过");
            } else if (StringUtils.equals("3", showStoreExamineVO.getStatus())) {
                showStoreExamineVO.setStatus("拒绝");
            }
        });
//        ExcelUtils.writeExcel(httpServletResponse, showStoreExamineVOS, ShowStoreExamineVO.class, "门店审核查看.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("门店审核查看", httpServletResponse), ShowStoreExamineVO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(showStoreExamineVOS);
    }

    @Override
    public ApiRes<ShowStoreExamineVO> listStoreExamine(PageWrap<ShowStoreExamineDTO> pageWrap) {
        // 当前登录人信息
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        ShowStoreExamineDTO showStoreExamineDTO = pageWrap.getModel();
        showStoreExamineDTO.setUserId(loginUserInfo.getId());
        // 该订单列表组成由 第一步需审订单+已审订单+待审订单
        List<ShowStoreExamineVO> showStoreExamineVOS = paymentOrderExamineMapper.listStoreExamine(showStoreExamineDTO);
        showStoreExamineVOS.forEach(showStoreExamineVO -> {
            if (StringUtils.isBlank(showStoreExamineVO.getApproveName())) {
                List<String> approveNames = paymentOrderExamineMapper.getApproveName(Integer.parseInt(showStoreExamineVO.getId()));
                showStoreExamineVO.setApproveName(approveNames.stream().collect(Collectors.joining(",")));
            }
            showStoreExamineVO.setExamineType(AdjustmentTypeEnum.getName(showStoreExamineVO.getExamineType()));
        });
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(showStoreExamineVOS)));
    }

    @Override
    public void exportListStoreExamine(ShowStoreExamineDTO showStoreExamineDTO, HttpServletResponse httpServletResponse) {
        // 当前登录人信息
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        showStoreExamineDTO.setUserId(loginUserInfo.getId());
        // 该订单列表组成由 第一步需审订单+已审订单+待审订单
        List<ShowStoreExamineVO> showStoreExamineVOS = paymentOrderExamineMapper.listStoreExamine(showStoreExamineDTO);
        showStoreExamineVOS.forEach(showStoreExamineVO -> {
            if (StringUtils.isBlank(showStoreExamineVO.getApproveName())) {
                List<String> approveNames = paymentOrderExamineMapper.getApproveName(Integer.parseInt(showStoreExamineVO.getId()));
                showStoreExamineVO.setApproveName(approveNames.stream().collect(Collectors.joining(",")));
            }
            showStoreExamineVO.setExamineType(AdjustmentTypeEnum.getName(showStoreExamineVO.getExamineType()));
            if (StringUtils.equals("1", showStoreExamineVO.getStatus())) {
                showStoreExamineVO.setStatus("审核中");
            } else if (StringUtils.equals("2", showStoreExamineVO.getStatus())) {
                showStoreExamineVO.setStatus("通过");
            } else if (StringUtils.equals("3", showStoreExamineVO.getStatus())) {
                showStoreExamineVO.setStatus("拒绝");
            }
        });
//        ExcelUtils.writeExcel(httpServletResponse, showStoreExamineVOS, ShowStoreExamineVO.class, "门店审核管理.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("门店审核管理", httpServletResponse), ShowStoreExamineVO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(showStoreExamineVOS);
    }

    @Override
    public ApiRes<StoreExamineDetailVO> storeExamineDetail(StoreExamineDetailDTO storeExamineDetailDTO) {
        StoreExamineDetail storeExamineDetail = paymentOrderExamineMapper.storeExamineDetail(Integer.parseInt(storeExamineDetailDTO.getId()));
        List<ApproveStepDetail> approveStepDetails = paymentOrderExamineMapper.listApproveStepDetail(Integer.parseInt(storeExamineDetailDTO.getId()));
        if (CollectionUtils.isEmpty(approveStepDetails) || approveStepDetails.size() == 0) {
            approveStepDetails = Lists.newArrayList();
        }
        approveStepDetails.forEach(approveStepDetail -> {
            // 审核记录图片
            List<ExamineFileDetail> detailPhotos = paymentOrderExamineMapper.listExamineDetailFiles(Integer.parseInt(approveStepDetail.getId()), 4);
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
        List<ExamineFileDetail> videos = paymentOrderExamineMapper.listExamineFiles(Integer.parseInt(storeExamineDetailDTO.getId()), 3);
        if (CollectionUtils.isEmpty(videos) || videos.size() == 0) {
            videos = Lists.newArrayList();
        } else {
            videos.forEach(examineFileDetail -> {
                examineFileDetail.setUrl(imageUrl + examineFileDetail.getUrl());
            });
        }
        // 审核单图片
        List<ExamineFileDetail> photos = paymentOrderExamineMapper.listExamineFiles(Integer.parseInt(storeExamineDetailDTO.getId()), 4);
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
        PaymentOrderExamine paymentOrderExamine = paymentOrderExamineMapper.selectByPrimaryKey(examineDTO.getId());
        if (paymentOrderExamine == null) {
            return ApiRes.failResponse("审批单不存在");
        }
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(paymentOrderExamine.getPaymentOrderMasterId());
        if (paymentOrderMaster == null) {
            return ApiRes.failResponse("主订单不存在");
        }
        // 当前登录人信息
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        // 查询在待审批状态的审核记录，该记录在某一环节(非终审)审核完以后会生成
        Example example = new Example(PaymentOrderExamineDeail.class);
        example.createCriteria()
                .andEqualTo("paymentOrderExamineId", paymentOrderExamine.getId())
                .andEqualTo("status", 1)
                .andEqualTo("deleted", 0);
        PaymentOrderExamineDeail paymentOrderExamineDeail = paymentOrderExamineDeailMapper.selectOneByExample(example);
        Integer userStepId;
        // 如果是空，则说明是初审
        if (paymentOrderExamineDeail == null) {
            // 判断是否有审核权限
            userStepId = approveProcessStepMapper.getUserStepId(paymentOrderExamine.getExamineType(), 1, loginUserInfo.getId(), 1);
            if (userStepId == null) {
                return ApiRes.failResponse("未配置该审核类型的审核流或你在该步骤没有审核权限");
            }
            // 添加待审核状态审批记录
            PaymentOrderExamineDeail addPaymentOrderExamineDeail = addPaymentOrderExamineDeail(paymentOrderExamine, userStepId);
            paymentOrderExamineDeail = addPaymentOrderExamineDeail;
        } else {
            userStepId = approveProcessStepMapper.getUserStepId(paymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), loginUserInfo.getId(), 1);
            if (userStepId == null) {
                return ApiRes.failResponse("未配置该审核类型的审核流或你在该步骤没有审核权限");
            }
        }
        // 修改审批记录为拒绝
        updateExamineDetail(paymentOrderExamineDeail, examineDTO, loginUserInfo, userStepId, 3);

        // 添加审批图片信息
        addExamineDetailFile(examineDTO, paymentOrderExamineDeail);

        // 修改审批单审批信息
        updateExamine(examineDTO, paymentOrderExamine, paymentOrderExamineDeail.getStep());

        // 修改主订单审批信息
        updateOrderMaster(paymentOrderExamine, paymentOrderMaster);

        return ApiRes.successResponse();
    }

    private PaymentOrderExamineDeail addPaymentOrderExamineDeail(PaymentOrderExamine paymentOrderExamine, Integer userStepId) {
        PaymentOrderExamineDeail addPaymentOrderExamineDeail = new PaymentOrderExamineDeail();
        addPaymentOrderExamineDeail.setPaymentOrderExamineId(paymentOrderExamine.getId());
        addPaymentOrderExamineDeail.setStepId(userStepId);
        addPaymentOrderExamineDeail.setStep(1);
        addPaymentOrderExamineDeail.setType(1);
        // 查询该阶段的审核人
        List<String> approveNames = approveProcessMapper.getApproveName(1, paymentOrderExamine.getExamineType(), addPaymentOrderExamineDeail.getStep(), addPaymentOrderExamineDeail.getType());
        String approveName = approveNames.stream().collect(Collectors.joining(","));
        addPaymentOrderExamineDeail.setApproveName(approveName);
        addPaymentOrderExamineDeail.setStatus(1);
        addPaymentOrderExamineDeail.setCreateTime(new Date());
        if (paymentOrderExamineDeailMapper.insertSelective(addPaymentOrderExamineDeail) == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "添加待审核状态审批记录失败");
        }
        return addPaymentOrderExamineDeail;
    }

    private void updateOrderMaster(PaymentOrderExamine paymentOrderExamine, PaymentOrderMaster paymentOrderMaster) {
        PaymentOrderMaster updatePaymentOrderMaster = new PaymentOrderMaster();
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
        if (paymentOrderMasterMapper.updateByPrimaryKeySelective(updatePaymentOrderMaster) == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "主订单审批信息修改失败");
        }
    }

    private void updateExamine(ExamineDTO examineDTO, PaymentOrderExamine paymentOrderExamine, Integer step) {
        PaymentOrderExamine updatePaymentOrderExamine = new PaymentOrderExamine();
        updatePaymentOrderExamine.setId(paymentOrderExamine.getId());
        updatePaymentOrderExamine.setExamine(0);
        updatePaymentOrderExamine.setRefuse(examineDTO.getMsg());
        updatePaymentOrderExamine.setStatus(3);
        updatePaymentOrderExamine.setCompleteTime(new Date());
        updatePaymentOrderExamine.setStep(step);
        if (paymentOrderExamineMapper.updateByPrimaryKeySelective(updatePaymentOrderExamine) == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "审批单信息修改失败");
        }
    }

    private void addExamineDetailFile(ExamineDTO examineDTO, PaymentOrderExamineDeail paymentOrderExamineDeail) {
        if (examineDTO.getImages().size() > 0) {
            List<PaymentOrderExamineDeailFile> list = Lists.newArrayList();
            examineDTO.getImages().forEach(
                    url -> {
                        PaymentOrderExamineDeailFile paymentOrderExamineDeailFile = new PaymentOrderExamineDeailFile();
                        paymentOrderExamineDeailFile.setPaymentOrderExamineDeailId(paymentOrderExamineDeail.getId());
                        paymentOrderExamineDeailFile.setType(4);
                        paymentOrderExamineDeailFile.setUrl(url);
                        paymentOrderExamineDeailFile.setStatus(1);
                        paymentOrderExamineDeailFile.setCreateTime(new Date());
                        paymentOrderExamineDeailFile.setDeleted(false);
                        list.add(paymentOrderExamineDeailFile);
                    }
            );
            if (paymentOrderExamineDeailFileMapper.insertList(list) == 0) {
                throw new BaseException(CommonConstant.FAIL_CODE, "添加审批流程图片失败");
            }
        }
    }

    private void updateExamineDetail(PaymentOrderExamineDeail paymentOrderExamineDeail, ExamineDTO examineDTO, LoginUserInfo loginUserInfo, Integer stepId, Integer status) {
        PaymentOrderExamineDeail udpatePaymentOrderExamineDeail = new PaymentOrderExamineDeail();
        udpatePaymentOrderExamineDeail.setId(paymentOrderExamineDeail.getId());
        udpatePaymentOrderExamineDeail.setStepId(stepId);
        udpatePaymentOrderExamineDeail.setStatus(status);
        udpatePaymentOrderExamineDeail.setRemark(examineDTO.getMsg());
        udpatePaymentOrderExamineDeail.setCreateUser(loginUserInfo.getId());
        udpatePaymentOrderExamineDeail.setCreateName(loginUserInfo.getRealname());
        udpatePaymentOrderExamineDeail.setExamineTime(new Date());
        udpatePaymentOrderExamineDeail.setUpdateTime(new Date());
        if (paymentOrderExamineDeailMapper.updateByPrimaryKeySelective(udpatePaymentOrderExamineDeail) == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "修改审批记录失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiRes examineAgree(ExamineDTO examineDTO) {
        PaymentOrderExamine paymentOrderExamine = paymentOrderExamineMapper.selectByPrimaryKey(examineDTO.getId());
        if (paymentOrderExamine == null) {
            return ApiRes.failResponse("审批单不存在");
        }
        PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectByPrimaryKey(paymentOrderExamine.getPaymentOrderMasterId());
        if (paymentOrderMaster == null) {
            return ApiRes.failResponse("主订单不存在");
        }

        // 当前登录人信息
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        // 查询在待审批状态的审核记录，该记录在某一环节(非终审)审核完以后会生成
        Example example = new Example(PaymentOrderExamineDeail.class);
        example.createCriteria()
                .andEqualTo("paymentOrderExamineId", paymentOrderExamine.getId())
                .andEqualTo("status", 1)
                .andEqualTo("deleted", 0);
        PaymentOrderExamineDeail paymentOrderExamineDeail = paymentOrderExamineDeailMapper.selectOneByExample(example);
        Integer userStepId;
        // 如果是空，则说明是初审
        if (paymentOrderExamineDeail == null) {
            // 判断是否有审核权限
            userStepId = approveProcessStepMapper.getUserStepId(paymentOrderExamine.getExamineType(), 1, loginUserInfo.getId(), 1);
            if (userStepId == null) {
                return ApiRes.failResponse("未配置该审核类型的审核流或你在该步骤没有审核权限");
            }
            // 添加待审核状态审批记录
            PaymentOrderExamineDeail addPaymentOrderExamineDeail = addPaymentOrderExamineDeail(paymentOrderExamine, userStepId);
            paymentOrderExamineDeail = addPaymentOrderExamineDeail;
        } else {
            userStepId = approveProcessStepMapper.getUserStepId(paymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), loginUserInfo.getId(), 1);
            if (userStepId == null) {
                return ApiRes.failResponse("未配置该审核类型的审核流或你在该步骤没有审核权限");
            }
        }
        // 修改审批记录为成功
        updateExamineDetail(paymentOrderExamineDeail, examineDTO, loginUserInfo, userStepId, 2);
        // 添加审批图片信息
        addExamineDetailFile(examineDTO, paymentOrderExamineDeail);

        Integer maxStep = approveProcessStepMapper.getMaxStep(paymentOrderExamine.getExamineType(), 1);

        // 修改审批记录后重新查询信息
        PaymentOrderExamineDeail newPaymentOrderExamineDeail = paymentOrderExamineDeailMapper.selectByPrimaryKey(paymentOrderExamineDeail.getId());
        // 获取当前审核步骤信息
        ApproveProcessStep approveProcessStep = approveProcessStepMapper.selectByPrimaryKey(newPaymentOrderExamineDeail.getStepId());
        Integer stepId = null;
        // 判断当前步骤是否有会签人
        if (approveProcessStep.getType().equals(1)) {
            stepId = approveProcessStepMapper.getUserNextStepId(approveProcessStep.getStep(), approveProcessStep.getApproveId(), 1);
        }
        // 判断该审核是否为终审，相同即且无会签人则为终审
        if (paymentOrderExamineDeail.getStep().equals(maxStep) && stepId == null) {
            // 不同的审核类型修改不同的字段
            // 审核类型：1、金额调整 2、延期支付 3、新订单审核 4、取消费用，5：线下支付
            PaymentOrderMaster updatePaymentOrderMaster = new PaymentOrderMaster();
            updatePaymentOrderMaster.setId(paymentOrderMaster.getId());
            if (paymentOrderExamine.getExamineType().equals(1)) {
                updatePaymentOrderMaster.setMoney(paymentOrderExamine.getNewMoney());
            } else if (paymentOrderExamine.getExamineType().equals(2)) {
                updatePaymentOrderMaster.setExpireTime(paymentOrderExamine.getNewTime());
            } else if (paymentOrderExamine.getExamineType().equals(4)) {
                updatePaymentOrderMaster.setStatus(3);
            } else if (paymentOrderExamine.getExamineType().equals(5)) {
                updatePaymentOrderMaster.setStatus(2);
                updatePaymentOrderMaster.setPayType(2);
                updatePaymentOrderMaster.setPayMoney(paymentOrderMaster.getMoney());
                updatePaymentOrderMaster.setPayTime(new Date());
            }

            if (paymentOrderMaster.getAdjustmentCount() > 0) {
                if (StringUtils.isEmpty(paymentOrderMaster.getAdjustmentMsg())) {
                    updatePaymentOrderMaster.setAdjustmentMsg(paymentOrderExamine.getMsg() + "(通过)");
                } else {
                    updatePaymentOrderMaster.setAdjustmentMsg(paymentOrderMaster.getAdjustmentMsg() + ";" + paymentOrderExamine.getMsg() + "(通过)");
                }
            }
            // 支援费和罚款审核完成即为已发布
            if (paymentOrderExamine.getPaymentTypeId().equals(4)
                    || paymentOrderExamine.getPaymentTypeId().equals(5)) {
                updatePaymentOrderMaster.setIsPublish(1);
            }
            updatePaymentOrderMaster.setExamine(1);
            updatePaymentOrderMaster.setUpdateTime(new Date());
            paymentOrderMasterMapper.updateByPrimaryKeySelective(updatePaymentOrderMaster);

            PaymentOrderExamine updatePaymentOrderExamine = new PaymentOrderExamine();
            updatePaymentOrderExamine.setId(paymentOrderExamine.getId());
            updatePaymentOrderExamine.setExamine(0);
            updatePaymentOrderExamine.setStatus(2);
            updatePaymentOrderExamine.setStep(newPaymentOrderExamineDeail.getStep());
            paymentOrderExamineMapper.updateByPrimaryKeySelective(updatePaymentOrderExamine);

            MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(paymentOrderMaster.getMerchantStoreId());
            // 如果是管理费，且是金额调整，需要修改门店的管理费需缴纳金额字段
            if (paymentOrderMaster.getPaymentTypeId() == 1 && paymentOrderExamine.getExamineType() == 1) {
                MerchantStore updateMerchantStore = new MerchantStore();
                updateMerchantStore.setId(merchantStore.getId());
                updateMerchantStore.setNeedManagementExpense(paymentOrderMaster.getMoney());
                merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore);
            }

            // 线下支付通过需要直接修改数据
            if (paymentOrderExamine.getExamineType().equals(5)) {
                // 管理费
                if (paymentOrderMaster.getPaymentTypeId() == 1) {
                    merchantStore.setAlreadyManagementExpense(paymentOrderMaster.getMoney());
                    Date newTime = DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
                    merchantStore.setServiceExpireTime(newTime);
                    merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
                    //如果管理费大缴纳标准2w
                    if (merchantStore.getManagementExpense().compareTo(new BigDecimal(20000)) > -1) {//说明用户的管理费金额包含云学堂的
                        //查询这个用户总共有多少个云学堂账户
                        List<MerchantStoreCloudSchool> merchantStoreCloudSchoolList = merchantStoreCloudSchoolMapper.queryByMerChantStoreId(merchantStore.getId());
                        if (merchantStoreCloudSchoolList.size() > 0) {
                            merchantStoreCloudSchoolList.stream().forEach(
                                    merchantStoreCloudSchool -> {
                                        Date newTime1 = DateUtil.endTime("1年后", merchantStoreCloudSchool.getEndTime());
                                        merchantStoreCloudSchool.setEndTime(newTime1);
                                        merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
                                    }
                            );
                        }
                    }
                }
//                // 云学堂
//                if (paymentOrderMaster.getPaymentTypeId().equals(3)) {
//                    //查询这个用户的云学堂账号信息
//                    Integer otherId = paymentOrderMaster.getOtherId();
//                    MerchantStoreCloudSchool merchantStoreCloudSchool = merchantStoreCloudSchoolMapper.queryById(otherId);
//                    Date newTime = DateUtil.endTime("1年后", merchantStoreCloudSchool.getEndTime());
//                    merchantStoreCloudSchool.setEndTime(newTime);
//                    merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
//                }
            } else if (paymentOrderExamine.getExamineType().equals(4)) {
                // 取消费用，服务到期时间延期一年
                MerchantStore updateMerchantStore = new MerchantStore();
                updateMerchantStore.setId(merchantStore.getId());
                Date newTime = DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
                updateMerchantStore.setServiceExpireTime(newTime);
                updateMerchantStore.setUpdateTime(new Date());
                merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore);
            }
        } else {
            // 新增下一人的待审核记录
            PaymentOrderExamineDeail addPaymentOrderExamineDeail = new PaymentOrderExamineDeail();
            addPaymentOrderExamineDeail.setPaymentOrderExamineId(paymentOrderExamine.getId());
            if (stepId != null) {
                addPaymentOrderExamineDeail.setStep(paymentOrderExamineDeail.getStep());
                addPaymentOrderExamineDeail.setType(2);
            } else {
                addPaymentOrderExamineDeail.setStep(paymentOrderExamineDeail.getStep() + 1);
                addPaymentOrderExamineDeail.setType(1);
            }
            addPaymentOrderExamineDeail.setStatus(1);
            // 查询该阶段的审核人
            List<String> approveNames = approveProcessMapper.getApproveName(1, paymentOrderExamine.getExamineType(), addPaymentOrderExamineDeail.getStep(), addPaymentOrderExamineDeail.getType());
            String approveName = approveNames.stream().collect(Collectors.joining(","));
            addPaymentOrderExamineDeail.setApproveName(approveName);
            addPaymentOrderExamineDeail.setCreateTime(new Date());
            paymentOrderExamineDeailMapper.insertSelective(addPaymentOrderExamineDeail);
            // 修改审批单信息
            PaymentOrderExamine updatePaymentOrderExamine = new PaymentOrderExamine();
            updatePaymentOrderExamine.setId(paymentOrderExamine.getId());
            updatePaymentOrderExamine.setExamine(2);
//            updatePaymentOrderExamine.setStatus(2);
            if (stepId != null) {
                updatePaymentOrderExamine.setStep(paymentOrderExamineDeail.getStep());
            } else {
                updatePaymentOrderExamine.setStep(paymentOrderExamineDeail.getStep() + 1);
            }
            if (paymentOrderExamineMapper.updateByPrimaryKeySelective(updatePaymentOrderExamine) == 0) {
                throw new BaseException(CommonConstant.FAIL_CODE, "审批单信息修改失败");
            }
        }
        return ApiRes.successResponse();
    }
}
