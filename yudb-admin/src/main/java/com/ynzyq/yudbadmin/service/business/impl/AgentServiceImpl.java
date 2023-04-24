package com.ynzyq.yudbadmin.service.business.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ynzyq.yudbadmin.constant.DataPoolConstant;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.*;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.enums.SystemFactorEnum;
import com.ynzyq.yudbadmin.po.AgentAreaDetailPO;
import com.ynzyq.yudbadmin.po.AgentAreaPO;
import com.ynzyq.yudbadmin.po.PaymentOrderChangeRecordPO;
import com.ynzyq.yudbadmin.service.business.IAgentService;
import com.ynzyq.yudbadmin.third.dto.UserInfoDTO;
import com.ynzyq.yudbadmin.third.service.IForeignService;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import com.ynzyq.yudbadmin.util.PlatformCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Service
@Slf4j
public class AgentServiceImpl implements IAgentService {

    @Resource
    AgentMapper agentMapper;

    @Resource
    MerchantAgentAreaMapper merchantAgentAreaMapper;

    @Resource
    MerchantAgentAreaRegionalManagerMapper merchantAgentAreaRegionalManagerMapper;
    @Resource
    AgentAreaPaymentTypeMapper agentAreaPaymentTypeMapper;
    @Resource
    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;
    @Resource
    AgentAreaPaymentOrderExamineMapper agentAreaPaymentOrderExamineMapper;
    @Resource
    AgentAreaPaymentOrderExamineDeailMapper agentAreaPaymentOrderExamineDeailMapper;
    @Resource
    MerchantMapper merchantMapper;

    @Resource
    RegionalManagerMapper regionalManagerMapper;
    @Resource
    AgentAreaPaymentOrderExamineFileMapper agentAreaPaymentOrderExamineFileMapper;

    @Resource
    MerchantAgentAreaDetailMapper merchantAgentAreaDetailMapper;

    @Resource
    MerchantAgentChangeRecordMapper merchantAgentChangeRecordMapper;

    @Resource
    PaymentOrderChangeRecordMapper paymentOrderChangeRecordMapper;

    @Resource
    ApproveProcessMapper approveProcessMapper;

    @Resource
    IForeignService iForeignService;

    @Resource
    DictMapper dictMapper;

    @Override
    public ApiRes<ListAgentVO> listAgent(PageWrap<ListAgentDTO> pageWrap) {
        ListAgentDTO listAgentDTO = pageWrap.getModel();
        listAgentDTO.setCondition(StringUtils.isBlank(listAgentDTO.getCondition()) ? null : listAgentDTO.getCondition());
        listAgentDTO.setStartTime(StringUtils.isBlank(listAgentDTO.getStartTime()) ? null : String.join("", listAgentDTO.getStartTime(), DataPoolConstant.START_TIME_SUFFIX));
        listAgentDTO.setEndTime(StringUtils.isBlank(listAgentDTO.getEndTime()) ? null : String.join("", listAgentDTO.getEndTime(), DataPoolConstant.END_TIME_SUFFIX));
        listAgentDTO.setMerchantId(StringUtils.isBlank(listAgentDTO.getMerchantId()) ? null : listAgentDTO.getMerchantId());
        listAgentDTO.setAreaId(StringUtils.isBlank(listAgentDTO.getAreaId()) ? null : listAgentDTO.getAreaId());
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<ListAgentVO> listAgentVOS = agentMapper.listAgent(pageWrap.getModel());
        for (ListAgentVO listAgentVO : listAgentVOS) {
            if (StringUtils.equals("1", listAgentVO.getSystemFactor())) {
                listAgentVO.setSystemFactorDesc("1.0");
            } else if (StringUtils.equals("2", listAgentVO.getSystemFactor())) {
                listAgentVO.setSystemFactorDesc("2.0");
            } else if (StringUtils.equals("3", listAgentVO.getSystemFactor())) {
                listAgentVO.setSystemFactorDesc("3.0");
            }
//            listAgentVO.setSystemFactorDesc(SystemFactorEnum.getStatusDesc(Integer.getInteger(listAgentVO.getSystemFactor())));
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", PageData.from(new PageInfo<>(listAgentVOS)));
    }

    /**
     * 编辑收款账户
     *
     * @param merchantIdDTO
     * @return
     */
    @Override
    public ApiRes editReceivingAccount(MerchantIdDTO merchantIdDTO) {
        // 检验参数
        if (merchantIdDTO.getMerchantId() == null) {
            return ApiRes.failResponse("代理商id不能是空的");
        }
        // 修改收款账户
        MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.selectByPrimaryKey(merchantIdDTO.getMerchantId());
        merchantAgentArea.setAccountName(merchantIdDTO.getAccountName());
        merchantAgentArea.setPublicAccount(merchantIdDTO.getPublicAccount());
        merchantAgentArea.setBank(merchantIdDTO.getBank());
        if (merchantAgentAreaMapper.updateByPrimaryKeySelective(merchantAgentArea) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<SingleAgentDetailVO> singleAgentDetail(SingleAgentDetailDTO singleAgentDetailDTO) {
        Integer id = singleAgentDetailDTO.getId();
        // 检验参数
        MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.selectByPrimaryKey(id);
        if (Objects.isNull(merchantAgentArea)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, DataPoolConstant.REQUEST_PARAMETER_ERROR, "");
        }
        // 获取区域经理
//        String regionalName = agentMapper.getRegionalName(id);
//        String regionalNameId = agentMapper.getRegionalNameId(id);
//        BaseInfoVO baseInfoVO = new BaseInfoVO(merchantAgentArea.getMerchantName(), merchantAgentArea.getMobile(), merchantAgentArea.getUid());
        BaseInfoVO baseInfoVO = agentMapper.singleStore(id);
        baseInfoVO.setMobile(StringUtils.isBlank(baseInfoVO.getMobile()) ? "" : baseInfoVO.getMobile());
        // 代理区域

        List<AgencyAreaVO> agencyAreaVOList = agentMapper.areaList(merchantAgentArea.getId());
//        AgencyAreaVO agencyAreaVO = new AgencyAreaVO(regionalName, merchantAgentArea);
//        agencyAreaVO.setRegionalManagerId(regionalNameId);
//        List<AgencyAreaVO> agencyAreaVOList = new ArrayList<>();
//        agencyAreaVOList.add(agencyAreaVO);
        // 获取缴费记录
        List<PaymentRecordVO> paymentRecordVOS = agentMapper.listPaymentRecordVO(merchantAgentArea.getUid());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", new SingleAgentDetailVO(baseInfoVO, agencyAreaVOList, paymentRecordVOS));
    }

    @Override
    public ApiRes editRegional(EditRegionalDTO editRegionalDTO) {
        List<Integer> ids = editRegionalDTO.getId();
        for (Integer id : ids) {
            MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.selectByPrimaryKey(id);
            if (merchantAgentArea == null) {
                return ApiRes.failResponse("代理权不存在");
            }
            RegionalManager regionalManager = agentMapper.validateRegionalCount(editRegionalDTO.getRegionalId());
            if (regionalManager == null) {
                return ApiRes.failResponse("区域经理不存在");
            }
            Integer oldManagerId = null;
            String oldManagerName = null;
            // 查询原区域经理信息
            RegionalManager oldRegionalManager = merchantAgentAreaRegionalManagerMapper.getRegionalManager(id);
            if (oldRegionalManager != null) {
                oldManagerId = oldRegionalManager.getId();
                oldManagerName = oldRegionalManager.getName();
            }
            //将原来的区域经理修改为无效
            merchantAgentAreaRegionalManagerMapper.updateById(id);
            // 当前登录人信息
            LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
            //新建数据
            MerchantAgentAreaRegionalManager merchantAgentAreaRegionalManager = new MerchantAgentAreaRegionalManager();
            merchantAgentAreaRegionalManager.setMerchantAgentAreaId(id);
            merchantAgentAreaRegionalManager.setRegionalManagerId(editRegionalDTO.getRegionalId());
            merchantAgentAreaRegionalManager.setStatus(1);
            merchantAgentAreaRegionalManager.setCreateTime(new Date());
            merchantAgentAreaRegionalManager.setCreateUser(loginUserInfo.getId());
            merchantAgentAreaRegionalManagerMapper.insertSelective(merchantAgentAreaRegionalManager);

            MerchantAgentChangeRecord merchantAgentChangeRecord = new MerchantAgentChangeRecord();
            merchantAgentChangeRecord.setMerchantAgentAreaId(id);
            merchantAgentChangeRecord.setOldManagerId(oldManagerId);
            merchantAgentChangeRecord.setOldManagerName(oldManagerName);
            merchantAgentChangeRecord.setNewManagerId(regionalManager.getId());
            merchantAgentChangeRecord.setNewManagerName(regionalManager.getName());
            merchantAgentChangeRecord.setUserId(loginUserInfo.getId());
            merchantAgentChangeRecord.setUserName(loginUserInfo.getRealname());
            merchantAgentChangeRecord.setCreateTime(new Date());
            merchantAgentChangeRecordMapper.insert(merchantAgentChangeRecord);

            List<PaymentOrderChangeRecord> paymentOrderChangeRecordList = new ArrayList<>();
            List<PaymentOrderDTO> orderDTOList = merchantAgentAreaRegionalManagerMapper.listAgentOrder(merchantAgentArea.getUid());
            for (PaymentOrderDTO paymentOrderDTO : orderDTOList) {
                PaymentOrderChangeRecordPO paymentOrderChangeRecord = new PaymentOrderChangeRecordPO(id.toString(), paymentOrderDTO, oldManagerId, oldManagerName, regionalManager, 2, loginUserInfo);
                paymentOrderChangeRecordList.add(paymentOrderChangeRecord);
                merchantAgentAreaRegionalManagerMapper.updateOrderManager(regionalManager.getId(), regionalManager.getName(), regionalManager.getMobile(), paymentOrderDTO.getId());
            }
            if (paymentOrderChangeRecordList.size() > 0) {
                paymentOrderChangeRecordMapper.insertList(paymentOrderChangeRecordList);
            }
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", "");
    }

    @Override
    public ApiRes editManagementExpense(EditManagementExpenseDTO editManagementExpenseDTO, Integer userId) {
        Integer id = editManagementExpenseDTO.getId();
        MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.selectByPrimaryKey(id);
        // 参数校验
        if (Objects.isNull(merchantAgentArea)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, DataPoolConstant.REQUEST_PARAMETER_ERROR, "");
        }
        AgentAreaPO agentAreaPO = new AgentAreaPO(editManagementExpenseDTO, userId);
        int ret = merchantAgentAreaMapper.updateByPrimaryKeySelective(agentAreaPO);
        if (ret == 0) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "调整失败", "");
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", "");
    }

    @Override
    public ApiRes excelSubmitForReview(MultipartFile file, HttpServletRequest httpServletRequest) {
        List<AgentOrderExcelDto> list = ExcelUtils.readExcel("", AgentOrderExcelDto.class, file);
        List<AgentOrderExcelDto> listYes = new ArrayList<>();
        List<AgentOrderExcelDto> listNo = new ArrayList<>();
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        if (list.size() > 0) {
            //查收授权号是否都存在
            List<String> merchantAgentAreaUids = merchantAgentAreaMapper.queryUids();

            List<String> payTypeNames = agentAreaPaymentTypeMapper.queryPayTypeName();
            List<AgentExcelSubmitForReviewVo> excelSubmitForReviewVoList = merchantAgentAreaMapper.ExcelSubmitForReviewVo();
            List<PaymentType> payTypeNameList = agentAreaPaymentTypeMapper.queryList();
            list.stream().forEach(
                    orderExcelDto -> {
                        if (merchantAgentAreaUids.contains(orderExcelDto.getUid()) && payTypeNames.contains(orderExcelDto.getPayTypeName())) {
                            listYes.add(orderExcelDto);
                        } else {
                            listNo.add(orderExcelDto);
                        }
                    }
            );
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            listYes.stream().forEach(
                    orderExcelDto -> {
                        excelSubmitForReviewVoList.stream().forEach(
                                excelSubmitForReviewVo -> {
                                    if (orderExcelDto.getUid().equals(excelSubmitForReviewVo.getUid())) {
                                        payTypeNameList.stream().forEach(
                                                paymentType -> {
                                                    if (paymentType.getName().equals(orderExcelDto.getPayTypeName())) {
                                                        //缴费主订单审核
                                                        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = new AgentAreaPaymentOrderMaster();
                                                        agentAreaPaymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));
                                                        agentAreaPaymentOrderMaster.setMerchantId(Integer.valueOf(excelSubmitForReviewVo.getMerchantId()));
                                                        agentAreaPaymentOrderMaster.setMerchantName(excelSubmitForReviewVo.getMaechantName());
                                                        agentAreaPaymentOrderMaster.setUid(excelSubmitForReviewVo.getUid());
                                                        agentAreaPaymentOrderMaster.setMerchantMobile(excelSubmitForReviewVo.getMerchantMobile());
                                                        agentAreaPaymentOrderMaster.setRegionalManagerId(Integer.valueOf(excelSubmitForReviewVo.getRegionalManagerId()));
                                                        agentAreaPaymentOrderMaster.setRegionalManagerName(excelSubmitForReviewVo.getRegionalManagerName());
                                                        agentAreaPaymentOrderMaster.setRegionalManagerMobile(excelSubmitForReviewVo.getRegionalManagerMobile());
                                                        agentAreaPaymentOrderMaster.setPaymentTypeId(paymentType.getId());
                                                        agentAreaPaymentOrderMaster.setPaymentTypeName(paymentType.getName());
                                                        agentAreaPaymentOrderMaster.setType(3);
                                                        agentAreaPaymentOrderMaster.setStatus(1);
                                                        agentAreaPaymentOrderMaster.setExamine(2);
                                                        agentAreaPaymentOrderMaster.setSend(2);
                                                        agentAreaPaymentOrderMaster.setDeleted(false);
                                                        agentAreaPaymentOrderMaster.setExamineNum(2);
                                                        agentAreaPaymentOrderMaster.setMoney(new BigDecimal(orderExcelDto.getMoney()));
                                                        try {
                                                            agentAreaPaymentOrderMaster.setExpireTime(sdf.parse(orderExcelDto.getExpireTime()));
                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }
                                                        agentAreaPaymentOrderMaster.setSend(2);
                                                        agentAreaPaymentOrderMaster.setExamine(2);
                                                        agentAreaPaymentOrderMaster.setRemark(orderExcelDto.getRemark());
                                                        agentAreaPaymentOrderMaster.setCreateTime(new Date());
                                                        agentAreaPaymentOrderMaster.setCreateUser(loginUserInfo.getId());
                                                        agentAreaPaymentOrderMaster.setDeleted(false);
                                                        agentAreaPaymentOrderMasterMapper.insertSelective(agentAreaPaymentOrderMaster);

                                                        //缴费审核订单
                                                        AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine = new AgentAreaPaymentOrderExamine();
                                                        agentAreaPaymentOrderExamine.setPaymentTypeId(paymentType.getId());
                                                        agentAreaPaymentOrderExamine.setPaymentOrderMasterId(agentAreaPaymentOrderMaster.getId());
                                                        agentAreaPaymentOrderExamine.setUid(excelSubmitForReviewVo.getUid());
                                                        agentAreaPaymentOrderExamine.setPaymentTypeName(paymentType.getName());
                                                        agentAreaPaymentOrderExamine.setNewMoney(agentAreaPaymentOrderMaster.getMoney());
                                                        agentAreaPaymentOrderExamine.setExamineType(3);
                                                        agentAreaPaymentOrderExamine.setExamine(1);
                                                        agentAreaPaymentOrderExamine.setStatus(1);
                                                        agentAreaPaymentOrderExamine.setApplyName(loginUserInfo.getRealname());
                                                        agentAreaPaymentOrderExamine.setMsg("新订单审核:" + paymentType.getName() + orderExcelDto.getMoney() + "元");
                                                        agentAreaPaymentOrderExamine.setRemark(agentAreaPaymentOrderMaster.getRemark());
                                                        agentAreaPaymentOrderExamine.setDeleted(false);
                                                        agentAreaPaymentOrderExamine.setApplyId(loginUserInfo.getId());
                                                        agentAreaPaymentOrderExamine.setCreateTime(new Date());
                                                        agentAreaPaymentOrderExamineMapper.insertSelective(agentAreaPaymentOrderExamine);

                                                        //缴费审核订单明细
                                                        AgentAreaPaymentOrderExamineDeail agentAreaPaymentOrderExamineDeail = new AgentAreaPaymentOrderExamineDeail();
                                                        agentAreaPaymentOrderExamineDeail.setPaymentOrderExamineId(agentAreaPaymentOrderExamine.getId());
                                                        agentAreaPaymentOrderExamineDeail.setExamine(1);
                                                        agentAreaPaymentOrderExamineDeail.setStatus(1);
                                                        agentAreaPaymentOrderExamineDeail.setCreateTime(new Date());
                                                        agentAreaPaymentOrderExamineDeail.setDeleted(0);
                                                        agentAreaPaymentOrderExamineDeail.setStep(1);
                                                        agentAreaPaymentOrderExamineDeail.setType(1);
                                                        // 查询该阶段的审核人
                                                        List<String> approveNames = approveProcessMapper.getApproveName(2, agentAreaPaymentOrderExamine.getExamineType(), agentAreaPaymentOrderExamineDeail.getStep(), agentAreaPaymentOrderExamineDeail.getType());
                                                        String approveName = approveNames.stream().collect(Collectors.joining(","));
                                                        agentAreaPaymentOrderExamineDeail.setApproveName(approveName);
                                                        agentAreaPaymentOrderExamineDeailMapper.insertSelective(agentAreaPaymentOrderExamineDeail);
                                                    }
                                                }
                                        );
                                    }
                                }
                        );
                    }
            );
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", "");
        }
        return ApiRes.response(CommonConstant.FAIL_CODE, "上传数据为空", "");
    }

    @Override
    public ApiRes<PayTypeListVo> payTypeList() {
        List<PayTypeListVo> list = agentAreaPaymentTypeMapper.queryPayTypeListNoId();
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", list);
    }

    @Override
    public ApiRes submitForReview(SubmitForReviewDto submitForReviewDto, HttpServletRequest httpServletRequest) {
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        if (submitForReviewDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String payTypeId = submitForReviewDto.getPayTypeId();
        if (StringUtils.isEmpty(payTypeId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String money = submitForReviewDto.getMoney();
        if (StringUtils.isEmpty(money)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        if (new BigDecimal("0.1").compareTo(new BigDecimal(money)) == 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "最小金额为0.1", "");
        }

        String time = submitForReviewDto.getTime();
        if (StringUtils.isEmpty(time)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = submitForReviewDto.getId();
        MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.selectByPrimaryKey(id);
        if (merchantAgentArea == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        Merchant merchant = merchantMapper.selectByPrimaryKey(merchantAgentArea.getMerchantId());
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商信息错误", "");
        }
        MerchantAgentAreaRegionalManager merchantAgentAreaRegionalManager = merchantAgentAreaRegionalManagerMapper.queryByMerchantAgentAreaId(merchantAgentArea.getId());
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(merchantAgentAreaRegionalManager.getRegionalManagerId());
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
        }
        AgentAreaPaymentType agentAreaPaymentType = agentAreaPaymentTypeMapper.selectByPrimaryKey(payTypeId);
        if (agentAreaPaymentType == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费类型错误", "");
        }
        String remark = submitForReviewDto.getRemark();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //缴费主订单审核
        AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = new AgentAreaPaymentOrderMaster();
        agentAreaPaymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));
        agentAreaPaymentOrderMaster.setMerchantId(merchant.getId());
        agentAreaPaymentOrderMaster.setMerchantName(merchant.getName());
        agentAreaPaymentOrderMaster.setMerchantMobile(merchant.getMobile());
        agentAreaPaymentOrderMaster.setUid(merchantAgentArea.getUid());
        agentAreaPaymentOrderMaster.setRegionalManagerId(regionalManager.getId());
        agentAreaPaymentOrderMaster.setRegionalManagerName(regionalManager.getName());
        agentAreaPaymentOrderMaster.setRegionalManagerMobile(regionalManager.getMobile());
        agentAreaPaymentOrderMaster.setPaymentTypeId(agentAreaPaymentType.getId());
        agentAreaPaymentOrderMaster.setPaymentTypeName(agentAreaPaymentType.getName());
        agentAreaPaymentOrderMaster.setType(3);
        agentAreaPaymentOrderMaster.setStatus(1);
        agentAreaPaymentOrderMaster.setExamine(2);
        agentAreaPaymentOrderMaster.setSend(2);
        agentAreaPaymentOrderMaster.setDeleted(false);
        agentAreaPaymentOrderMaster.setExamineNum(2);
        agentAreaPaymentOrderMaster.setMoney(new BigDecimal(money));
        try {
            agentAreaPaymentOrderMaster.setExpireTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        agentAreaPaymentOrderMaster.setSend(2);
        agentAreaPaymentOrderMaster.setExamine(2);
        agentAreaPaymentOrderMaster.setRemark(remark);
        agentAreaPaymentOrderMaster.setCreateTime(new Date());
        agentAreaPaymentOrderMaster.setCreateUser(loginUserInfo.getId());
        agentAreaPaymentOrderMaster.setDeleted(false);
        agentAreaPaymentOrderMasterMapper.insertSelective(agentAreaPaymentOrderMaster);


        //缴费审核订单
        AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine = new AgentAreaPaymentOrderExamine();
        agentAreaPaymentOrderExamine.setPaymentTypeId(agentAreaPaymentType.getId());
        agentAreaPaymentOrderExamine.setPaymentOrderMasterId(agentAreaPaymentOrderMaster.getId());
        agentAreaPaymentOrderExamine.setUid(agentAreaPaymentOrderMaster.getUid());
        agentAreaPaymentOrderExamine.setPaymentTypeName(agentAreaPaymentType.getName());
        agentAreaPaymentOrderExamine.setNewMoney(agentAreaPaymentOrderMaster.getMoney());
        agentAreaPaymentOrderExamine.setExamineType(3);
        agentAreaPaymentOrderExamine.setExamine(1);
        agentAreaPaymentOrderExamine.setStatus(1);
        agentAreaPaymentOrderExamine.setApplyName(loginUserInfo.getRealname());
        agentAreaPaymentOrderExamine.setMsg("新订单审核:" + agentAreaPaymentType.getName() + money + "元");
        agentAreaPaymentOrderExamine.setRemark(remark);
        agentAreaPaymentOrderExamine.setDeleted(false);
        agentAreaPaymentOrderExamine.setApplyId(loginUserInfo.getId());
        agentAreaPaymentOrderExamine.setCreateTime(new Date());
        agentAreaPaymentOrderExamine.setStep(1);
        agentAreaPaymentOrderExamineMapper.insertSelective(agentAreaPaymentOrderExamine);
        //缴费审核订单明细
        AgentAreaPaymentOrderExamineDeail agentAreaPaymentOrderExamineDeail = new AgentAreaPaymentOrderExamineDeail();
        agentAreaPaymentOrderExamineDeail.setPaymentOrderExamineId(agentAreaPaymentOrderExamine.getId());
        agentAreaPaymentOrderExamineDeail.setExamine(1);
        agentAreaPaymentOrderExamineDeail.setStatus(1);
        agentAreaPaymentOrderExamineDeail.setCreateTime(new Date());
        agentAreaPaymentOrderExamineDeail.setDeleted(0);
        agentAreaPaymentOrderExamineDeail.setStep(1);
        agentAreaPaymentOrderExamineDeail.setType(1);
        // 查询该阶段的审核人
        List<String> approveNames = approveProcessMapper.getApproveName(2, agentAreaPaymentOrderExamine.getExamineType(), agentAreaPaymentOrderExamineDeail.getStep(), agentAreaPaymentOrderExamineDeail.getType());
        String approveName = approveNames.stream().collect(Collectors.joining(","));
        agentAreaPaymentOrderExamineDeail.setApproveName(approveName);
        agentAreaPaymentOrderExamineDeailMapper.insertSelective(agentAreaPaymentOrderExamineDeail);

        //文件信息存储
        List<AgentAreaPaymentOrderExamineFile> agentAreaPaymentOrderExamineFileList = new ArrayList<>();
        List<String> photos = submitForReviewDto.getPhotos();
        photos.stream().forEach(
                s -> {
                    log.info("photos={}", s.length());
                    AgentAreaPaymentOrderExamineFile agentAreaPaymentOrderExamineFile = new AgentAreaPaymentOrderExamineFile();
                    agentAreaPaymentOrderExamineFile.setPaymentOrderExamineId(agentAreaPaymentOrderExamine.getId());
                    agentAreaPaymentOrderExamineFile.setType(4);
                    agentAreaPaymentOrderExamineFile.setUrl(s);
                    agentAreaPaymentOrderExamineFile.setDeleted(false);
                    agentAreaPaymentOrderExamineFile.setStatus(1);
                    agentAreaPaymentOrderExamineFile.setCreateTime(new Date());
                    agentAreaPaymentOrderExamineFileList.add(agentAreaPaymentOrderExamineFile);
                }
        );
        List<String> video = submitForReviewDto.getVideo();
        video.stream().forEach(
                s -> {
                    log.info("video={}", s.length());
                    AgentAreaPaymentOrderExamineFile agentAreaPaymentOrderExamineFile = new AgentAreaPaymentOrderExamineFile();
                    agentAreaPaymentOrderExamineFile.setPaymentOrderExamineId(agentAreaPaymentOrderExamine.getId());
                    agentAreaPaymentOrderExamineFile.setType(3);
                    agentAreaPaymentOrderExamineFile.setUrl(s);
                    agentAreaPaymentOrderExamineFile.setDeleted(false);
                    agentAreaPaymentOrderExamineFile.setStatus(1);
                    agentAreaPaymentOrderExamineFile.setCreateTime(new Date());
                    agentAreaPaymentOrderExamineFileList.add(agentAreaPaymentOrderExamineFile);
                }
        );

        if (agentAreaPaymentOrderExamineFileList.size() > 0) {
            agentAreaPaymentOrderExamineFileMapper.insertList(agentAreaPaymentOrderExamineFileList);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "缴费提交成功", "");

    }

    @Override
    public ApiRes addAgent(AddAgentDto addAgentDto) {

        if (addAgentDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String uid = addAgentDto.getUid();
        //先查询该授权号是否有用户存在
        MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.queryByUid(uid);
        if (merchantAgentArea != null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "授权号已经存在", "");
        }
        merchantAgentArea = new MerchantAgentArea();
        merchantAgentArea.setUid(uid);
        String merchantId = addAgentDto.getMerchantId();
        if (StringUtils.isEmpty(merchantId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择加盟商", "");
        }
        //查询加盟商是否存在
        Merchant merchant = merchantMapper.selectByPrimaryKey(merchantId);
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商不存在", "");
        }
        merchantAgentArea.setMerchantId(Integer.valueOf(merchantId));
//        String province = addAgentDto.getProvince();
//        if (StringUtils.isEmpty(province)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择省份", "");
//        }
        if (CollectionUtils.isEmpty(addAgentDto.getAreaList())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "选择省市区", "");
        }
        merchantAgentArea.setMerchantName(merchant.getName());
        merchantAgentArea.setSignatory(merchant.getName());
        merchantAgentArea.setMobile(merchant.getMobile());

//        merchantAgentArea.setProvince(province);
//        String city = addAgentDto.getCity();
//        if (StringUtils.isEmpty(city)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择城市", "");
//        }
//        merchantAgentArea.setCity(city);
//        String area = addAgentDto.getArea();
//        merchantAgentArea.setArea(area);
        String regionalManagerId = addAgentDto.getRegionalManagerId();
        if (StringUtils.isEmpty(regionalManagerId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择区域经理", "");
        }
        String signTime = addAgentDto.getSignTime();
        if (StringUtils.isEmpty(signTime)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择签约时间", "");
        }
        String expireTime = addAgentDto.getExpireTime();
        if (StringUtils.isEmpty(expireTime)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择到期时间", "");
        }
        String serviceExpireTime = addAgentDto.getServiceExpireTime();
        if (StringUtils.isEmpty(serviceExpireTime)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择服务到期时间", "");
        }
        try {
            merchantAgentArea.setSignTime(sdf.parse(signTime));
            merchantAgentArea.setStartTime(sdf.parse(signTime));
            merchantAgentArea.setExpireTime(sdf.parse(expireTime));
            merchantAgentArea.setServiceExpireTime(sdf.parse(serviceExpireTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String managementExpense = addAgentDto.getManagementExpense();
        if (StringUtils.isEmpty(managementExpense)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入管理费额度", "");
        }
        merchantAgentArea.setContractStatus(1);
        merchantAgentArea.setManagementExpense(new BigDecimal(managementExpense));
        merchantAgentArea.setNeedManagementExpense(new BigDecimal(addAgentDto.getAlreadyManagementExpense()));
        merchantAgentArea.setAlreadyManagementExpense(BigDecimal.ZERO);

        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        merchantAgentArea.setCreateUser(loginUserInfo.getId());
        merchantAgentArea.setCreateTime(new Date());
        merchantAgentArea.setAgencyFees(new BigDecimal(addAgentDto.getAgencyFees()));
        merchantAgentArea.setDepositFee(new BigDecimal(addAgentDto.getDepositFee()));
        merchantAgentArea.setIsOpenPosition(Integer.parseInt(addAgentDto.getIsOpenPosition()));
        merchantAgentAreaMapper.insertSelective(merchantAgentArea);

        if (CollectionUtils.isNotEmpty(addAgentDto.getAreaList()) && addAgentDto.getAreaList().size() > 0) {
            List<MerchantAgentAreaDetail> areaDetailList = new ArrayList<>();
            for (AgentAreaDTO agentAreaDTO : addAgentDto.getAreaList()) {
                AgentAreaDetailPO agentAreaDetailPO = new AgentAreaDetailPO(merchantAgentArea.getId(), agentAreaDTO);
                areaDetailList.add(agentAreaDetailPO);
            }
            merchantAgentAreaDetailMapper.insertList(areaDetailList);
        }

        MerchantAgentAreaRegionalManager merchantAgentAreaRegionalManager = new MerchantAgentAreaRegionalManager();
        merchantAgentAreaRegionalManager.setMerchantAgentAreaId(merchantAgentArea.getId());
        merchantAgentAreaRegionalManager.setRegionalManagerId(Integer.valueOf(regionalManagerId));
        merchantAgentAreaRegionalManager.setStatus(1);
        merchantAgentAreaRegionalManager.setCreateUser(loginUserInfo.getId());
        merchantAgentAreaRegionalManager.setCreateTime(new Date());
        merchantAgentAreaRegionalManagerMapper.insertSelective(merchantAgentAreaRegionalManager);
        merchant.setIsAgent(1);
        merchantMapper.updateByPrimaryKeySelective(merchant);

        UserInfoDTO userInfoDTO = new UserInfoDTO("2", uid, "1");
        String msg = iForeignService.userInfo(userInfoDTO);
        if (!StringUtils.equals("success", msg)) {
            ApiRes.failResponse("鱼店宝添加代理权成功，代理权数据同步金蝶失败，原因：" + msg);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "添加成功", "");

    }

    @Override
    public void exportAgent(ListAgentDTO listAgentDTO, HttpServletResponse response) {
        listAgentDTO.setCondition(StringUtils.isBlank(listAgentDTO.getCondition()) ? null : listAgentDTO.getCondition());
        listAgentDTO.setStartTime(StringUtils.isBlank(listAgentDTO.getStartTime()) ? null : String.join("", listAgentDTO.getStartTime(), DataPoolConstant.START_TIME_SUFFIX));
        listAgentDTO.setEndTime(StringUtils.isBlank(listAgentDTO.getEndTime()) ? null : String.join("", listAgentDTO.getEndTime(), DataPoolConstant.END_TIME_SUFFIX));
        listAgentDTO.setMerchantId(StringUtils.isBlank(listAgentDTO.getMerchantId()) ? null : listAgentDTO.getMerchantId());
        listAgentDTO.setAreaId(StringUtils.isBlank(listAgentDTO.getAreaId()) ? null : listAgentDTO.getAreaId());
        List<ListAgentVO> listAgentVOS = agentMapper.listAgent(listAgentDTO);
        listAgentVOS.forEach(listAgentVO -> {
            String regionalName = agentMapper.getRegionalName(Integer.parseInt(listAgentVO.getId()));
            String regionalNameId = agentMapper.getRegionalNameId(Integer.parseInt(listAgentVO.getId()));
            listAgentVO.setRegionalName(regionalName);
            listAgentVO.setRegionalNameId(regionalNameId);
            if (listAgentVO.getIsAgent().equals("1")) {
                listAgentVO.setIsAgent("是");
            } else {
                listAgentVO.setIsAgent("否");
            }
            if (listAgentVO.getIsOpenPosition().equals("1")) {
                listAgentVO.setIsOpenPosition("是");
            } else {
                listAgentVO.setIsOpenPosition("否");
            }
            if (listAgentVO.getIsEffect().equals("1")) {
                listAgentVO.setIsEffect("是");
            } else {
                listAgentVO.setIsEffect("否");
            }
            if (StringUtils.equals("1", listAgentVO.getSystemFactor())) {
                listAgentVO.setSystemFactorDesc("1.0");
            } else if (StringUtils.equals("2", listAgentVO.getSystemFactor())) {
                listAgentVO.setSystemFactorDesc("2.0");
            } else if (StringUtils.equals("3", listAgentVO.getSystemFactor())) {
                listAgentVO.setSystemFactorDesc("3.0");
            }
//            listAgentVO.setSystemFactorDesc(SystemFactorEnum.getStatusDesc(Integer.getInteger(listAgentVO.getSystemFactor())));
        });
//        ExcelUtils.writeExcel(response, listAgentVOS, ListAgentVO.class, "代理权.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("代理权", response), ListAgentVO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(listAgentVOS);
//        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", "");
    }

    @Override
    public ApiRes<SingleAgentVO> singleAgent(AgentIdDTO agentIdDTO) {
        // 查询代理权
        MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.selectByPrimaryKey(agentIdDTO.getId());
        if (merchantAgentArea == null) {
            return ApiRes.failResponse("代理权不存在");
        }
        SingleAgentVO singleAgentVO = new SingleAgentVO(merchantAgentArea);
        // 查询区域经理
        ManagerDTO managerInfo = merchantAgentAreaMapper.getManagerInfo(Integer.parseInt(agentIdDTO.getId()));
        singleAgentVO.setRegionalManagerId(managerInfo.getId());
        singleAgentVO.setRegionalManagerName(managerInfo.getName());

        // 查询代理权下的代理区域
        Example example = new Example(MerchantAgentAreaDetail.class);
        example.createCriteria().andEqualTo("agentAreaId", agentIdDTO.getId()).andEqualTo("status", 1);
        List<MerchantAgentAreaDetail> agentAreaDetails = merchantAgentAreaDetailMapper.selectByExample(example);
        List<AgentAreaDTO> areaList = new ArrayList<>();
        agentAreaDetails.forEach(merchantAgentAreaDetail -> {
            AgentAreaDTO agentAreaDTO = new AgentAreaDTO();
            agentAreaDTO.setUid(merchantAgentAreaDetail.getUid());
            agentAreaDTO.setProvince(merchantAgentAreaDetail.getProvince());
            agentAreaDTO.setCity(merchantAgentAreaDetail.getCity());
            agentAreaDTO.setArea(merchantAgentAreaDetail.getArea());
            areaList.add(agentAreaDTO);
        });
        singleAgentVO.setAreaList(areaList);
        return ApiRes.successResponseData(singleAgentVO);
    }

    @Override
    public ApiRes editAgent(EditAgentDTO editAgentDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //先查询该授权号是否有用户存在
        MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.selectByPrimaryKey(editAgentDTO.getId());
        if (merchantAgentArea == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "代理权不存在", "");
        }
//        merchantAgentArea.setUid(editAgentDTO.getUid());
        //查询加盟商是否存在
        Merchant merchant = merchantMapper.selectByPrimaryKey(editAgentDTO.getMerchantId());
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商不存在", "");
        }
//        merchantAgentArea.setMerchantName(merchant.getName());
//        merchantAgentArea.setSignatory(merchant.getName());
//        merchantAgentArea.setMobile(merchant.getMobile());
        try {
            merchantAgentArea.setSignTime(sdf.parse(editAgentDTO.getSignTime()));
            merchantAgentArea.setStartTime(sdf.parse(editAgentDTO.getSignTime()));
            merchantAgentArea.setExpireTime(sdf.parse(editAgentDTO.getExpireTime()));
            merchantAgentArea.setServiceExpireTime(sdf.parse(editAgentDTO.getServiceExpireTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        merchantAgentArea.setContractStatus(1);
        merchantAgentArea.setManagementExpense(new BigDecimal(editAgentDTO.getManagementExpense()));
        merchantAgentArea.setNeedManagementExpense(new BigDecimal(editAgentDTO.getAlreadyManagementExpense()));

        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        merchantAgentArea.setCreateUser(loginUserInfo.getId());
        merchantAgentArea.setCreateTime(new Date());
        merchantAgentArea.setAgencyFees(new BigDecimal(editAgentDTO.getAgencyFees()));
        merchantAgentArea.setDepositFee(new BigDecimal(editAgentDTO.getDepositFee()));
        merchantAgentArea.setIsOpenPosition(Integer.parseInt(editAgentDTO.getIsOpenPosition()));
        merchantAgentAreaMapper.updateByPrimaryKeySelective(merchantAgentArea);

        // 先把代理权的代理区域改为不可用，再添加新的代理权
        if (CollectionUtils.isNotEmpty(editAgentDTO.getAreaList()) && editAgentDTO.getAreaList().size() > 0) {
            merchantAgentAreaDetailMapper.updateDetailStatus(merchantAgentArea.getId());
            List<MerchantAgentAreaDetail> areaDetailList = new ArrayList<>();
            for (AgentAreaDTO agentAreaDTO : editAgentDTO.getAreaList()) {
                AgentAreaDetailPO agentAreaDetailPO = new AgentAreaDetailPO(merchantAgentArea.getId(), agentAreaDTO);
                areaDetailList.add(agentAreaDetailPO);
            }
            merchantAgentAreaDetailMapper.insertList(areaDetailList);
        }

        // 修改代理权关联的区域经理
        Example example = new Example(MerchantAgentAreaRegionalManager.class);
        example.createCriteria().andEqualTo("merchantAgentAreaId", merchantAgentArea.getId()).andEqualTo("status", 1);
        MerchantAgentAreaRegionalManager areaRegionalManager = merchantAgentAreaRegionalManagerMapper.selectOneByExample(example);
        if (areaRegionalManager == null) {
            addAgentAreaRegionalManager(editAgentDTO, merchantAgentArea, loginUserInfo);
        } else if (!StringUtils.equals(areaRegionalManager.getRegionalManagerId().toString(), editAgentDTO.getRegionalManagerId())) {
            // 先把原区域经理改为不可用
            merchantAgentAreaDetailMapper.updateManagerStatus(areaRegionalManager.getRegionalManagerId(), merchantAgentArea.getId());
            addAgentAreaRegionalManager(editAgentDTO, merchantAgentArea, loginUserInfo);
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO("2", merchantAgentArea.getUid(), "2");
        String msg = iForeignService.userInfo(userInfoDTO);
        if (!StringUtils.equals("success", msg)) {
            ApiRes.failResponse("鱼店宝修改代理权成功，代理权数据同步金蝶失败，原因：" + msg);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", "");
    }

    private void addAgentAreaRegionalManager(EditAgentDTO editAgentDTO, MerchantAgentArea merchantAgentArea, LoginUserInfo loginUserInfo) {
        MerchantAgentAreaRegionalManager merchantAgentAreaRegionalManager = new MerchantAgentAreaRegionalManager();
        merchantAgentAreaRegionalManager.setMerchantAgentAreaId(merchantAgentArea.getId());
        merchantAgentAreaRegionalManager.setRegionalManagerId(Integer.valueOf(editAgentDTO.getRegionalManagerId()));
        merchantAgentAreaRegionalManager.setStatus(1);
        merchantAgentAreaRegionalManager.setCreateUser(loginUserInfo.getId());
        merchantAgentAreaRegionalManager.setCreateTime(new Date());
        merchantAgentAreaRegionalManagerMapper.insertSelective(merchantAgentAreaRegionalManager);
    }

    @Override
    public ApiRes editEffect(EditEffectDTO editEffectDTO) {
        // 检验参数
        MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.selectByPrimaryKey(editEffectDTO.getId());
        if (Objects.isNull(merchantAgentArea)) {
            return ApiRes.failResponse("代理权不存在");
        }
        MerchantAgentArea updateMerchantAgentArea = new MerchantAgentArea();
        updateMerchantAgentArea.setId(merchantAgentArea.getId());
        updateMerchantAgentArea.setIsEffect(Integer.parseInt(editEffectDTO.getIsEffect()));
        if (merchantAgentAreaMapper.updateByPrimaryKeySelective(updateMerchantAgentArea) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        return ApiRes.successResponse();
    }

    /**
     * 修改订货配送信息
     */
    @Override
    public ApiRes modifyDataService(ModifyDataDTO modifyDataDTO) {
        //检查参数
        if (modifyDataDTO.getId() == null || modifyDataDTO.getId().equals("")) {
            return ApiRes.failResponse("请输入id");
        }
        //逻辑处理
        if (!agentMapper.modifyDataMapper(modifyDataDTO)) {
            return ApiRes.failResponse("修改失败");
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<OneLevelStatusVO> systemFactorSelectBox() {
        List<OneLevelStatusVO> systemFactorList = dictMapper.getDict("SYSTEM_FACTOR");
        return ApiRes.successResponseData(systemFactorList);
    }

    @Override
    public ApiRes editSystemFactor(EditSystemFactorDTO editSystemFactorDTO) {
        for (String id : editSystemFactorDTO.getIdList()) {
            MerchantAgentArea updateMerchantAgentArea = new MerchantAgentArea();
            updateMerchantAgentArea.setId(Integer.parseInt(id));
            updateMerchantAgentArea.setSystemFactor(Integer.parseInt(editSystemFactorDTO.getSystemFactor()));
            updateMerchantAgentArea.setUpdateTime(new Date());
            if (merchantAgentAreaMapper.updateByPrimaryKeySelective(updateMerchantAgentArea) == 0) {
                return ApiRes.failResponse("修改失败");
            }
        }
        return ApiRes.successResponse();
    }

}
