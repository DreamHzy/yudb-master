package com.ynzyq.yudbadmin.service.evaluate.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.SystemUserEvaluationMapper;
import com.ynzyq.yudbadmin.dao.business.dao.YgEmployeeBankMapper;
import com.ynzyq.yudbadmin.dao.business.model.YgEmployeeBank;
import com.ynzyq.yudbadmin.dao.business.vo.AgentFinancePageVo;
import com.ynzyq.yudbadmin.dao.business.vo.ListOverdueOrderVO;
import com.ynzyq.yudbadmin.dao.evaluate.dto.*;
import com.ynzyq.yudbadmin.dao.evaluate.mapper.EvaluateMapper;
import com.ynzyq.yudbadmin.dao.evaluate.po.SystemUserEvaluationPO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.EmployeeVO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.EvaluationVO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.ListEvaluateItemVO;
import com.ynzyq.yudbadmin.dao.system.SystemUserMapper;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.service.evaluate.IEvaluateService;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xinchen
 * @date 2021/12/1 11:51
 * @description:
 */
@Service
public class EvaluateServiceImpl implements IEvaluateService {

    @Resource
    EvaluateMapper evaluateMapper;

    @Resource
    SystemUserEvaluationMapper systemUserEvaluationMapper;

    @Resource
    SystemUserMapper systemUserMapper;

    @Resource
    YgEmployeeBankMapper employeeMapper;

    @Value("${imageUrl}")
    private String imageUrl;

    private static Map<String, String> mobileMap = Maps.newHashMap();

    static {
        // 陈建伟
        mobileMap.put("13311500508", "华东");
        // 董旭钊
        mobileMap.put("18010135932", "华北");
        // 梁飞龙
        mobileMap.put("13311505138", "华中");
        // 刘明
        mobileMap.put("18601153542", "华西");
        // 陶山山
        mobileMap.put("18519740575", "华南");
    }

    @Override
    public ApiRes<ListEvaluateItemVO> listEvaluateItem() {
        List<ListEvaluateItemVO> firstLevelEvaluateItem = evaluateMapper.listFirstLevelEvaluateItem();
        firstLevelEvaluateItem.forEach(listEvaluateItemVO -> {
            List<ListEvaluateItemVO> secondLevelEvaluateItem = evaluateMapper.listSecondLevelEvaluateItem(Integer.parseInt(listEvaluateItemVO.getId()));
            if (CollectionUtils.isEmpty(secondLevelEvaluateItem) || secondLevelEvaluateItem.size() == 0) {
                secondLevelEvaluateItem = Lists.newArrayList();
            }
            listEvaluateItemVO.setSonListEvaluateItemVO(secondLevelEvaluateItem);
        });
        return ApiRes.successResponseData(firstLevelEvaluateItem);
    }

    @Override
    public ApiRes addEvaluate(EvaluationDTO evaluationDTO) {
        if (StringUtils.isBlank(evaluationDTO.getApplicant())) {
            return ApiRes.failResponse("申请人不能为空");
        } else if (StringUtils.isBlank(evaluationDTO.getBirth())) {
            return ApiRes.failResponse("出生日期不能为空");
        } else if (StringUtils.isBlank(evaluationDTO.getContactPhone())) {
            return ApiRes.failResponse("联系电话不能为空");
        } else if (StringUtils.isBlank(evaluationDTO.getMarriage())) {
            return ApiRes.failResponse("婚姻状况不能为空");
        } else if (StringUtils.isBlank(evaluationDTO.getProvince())) {
            return ApiRes.failResponse("请选择省份");
        } else if (StringUtils.isBlank(evaluationDTO.getCity())) {
            return ApiRes.failResponse("请选择城市");
        } else if (StringUtils.isBlank(evaluationDTO.getArea())) {
            return ApiRes.failResponse("请选择区县");
        } else if (StringUtils.isBlank(evaluationDTO.getOpenStoreArea())) {
            return ApiRes.failResponse("计划开店区域不能为空");
        }
        SystemUserEvaluationPO systemUserEvaluationPO = new SystemUserEvaluationPO(evaluationDTO);
        if (systemUserEvaluationMapper.insert(systemUserEvaluationPO) == 0) {
            return ApiRes.failResponse("提交失败");
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<EvaluationVO> listEvaluate(PageWrap<ListEvaluateDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        SystemUser systemUser = systemUserMapper.selectByPrimaryKey(loginUserInfo.getId());
        String region = null;
        if (mobileMap.keySet().contains(systemUser.getUsername())) {
            region = mobileMap.get(systemUser.getUsername());
        }
        List<EvaluationVO> listEvaluate = evaluateMapper.listEvaluate(pageWrap.getModel().getCondition(), region);
        if (CollectionUtils.isEmpty(listEvaluate) || listEvaluate.size() == 0) {
            listEvaluate = Lists.newArrayList();
        } else {
            listEvaluate.forEach(evaluationVO -> {
                evaluationVO.setIdCard(imageUrl + evaluationVO.getIdCard());
            });
        }
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(listEvaluate)));
    }

    @Override
    public void exportEvaluate(ListEvaluateDTO listEvaluateDTO, HttpServletResponse response) {
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        SystemUser systemUser = systemUserMapper.selectByPrimaryKey(loginUserInfo.getId());
        String region = null;
        if (mobileMap.keySet().contains(systemUser.getUsername())) {
            region = mobileMap.get(systemUser.getUsername());
        }
        List<EvaluationVO> listEvaluate = evaluateMapper.listEvaluate(listEvaluateDTO.getCondition(), region);
        if (CollectionUtils.isEmpty(listEvaluate) || listEvaluate.size() == 0) {
            listEvaluate = Lists.newArrayList();
        } else {
            listEvaluate.forEach(evaluationVO -> {
                evaluationVO.setIdCard(imageUrl + evaluationVO.getIdCard());
            });
        }
//        ExcelUtils.writeExcel(response, listEvaluate, EvaluationVO.class, "意向客户申请表.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("意向客户申请表", response), EvaluationVO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(listEvaluate);
    }

    @Override
    public void employeeInformationService(EmployeeDTO employeeDTO, HttpServletResponse response) {
        List<YgEmployeeBank> ygEmployeeBank = employeeMapper.selectEmployee(employeeDTO);
//        ExcelUtils.writeExcel(response, ygEmployeeBank, YgEmployeeBank.class, "员工信息收集.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("员工信息收集", response), YgEmployeeBank.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(ygEmployeeBank);
    }

    @Override
    public ApiRes<EmployeeVO> employeeInformationQueryService(PageWrap<EmployeeKeysDTO> pageWrap) {
        EmployeeKeysDTO model = pageWrap.getModel();
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<EmployeeVO> ygEmployeeBanks = employeeMapper.selectEmployees(model);
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(ygEmployeeBanks)));
    }

    @Override
    public ApiRes EmployeeInformationStorageService(EmployeeStorageDTO employeeDTO) {
        if (employeeDTO == null) {
            return ApiRes.failResponse("无效参数");
        }
        YgEmployeeBank ygEmployeeBank = employeeMapper.selectEmployeeQueryId(employeeDTO.getEmployeeId());
        if (ygEmployeeBank == null) {
            ygEmployeeBank = new YgEmployeeBank();
        }
        ygEmployeeBank.setEmployeeId(employeeDTO.getEmployeeId());
        ygEmployeeBank.setEmployeeName(employeeDTO.getEmployeeName());
        ygEmployeeBank.setBankAccount(employeeDTO.getBankAccount());
        ygEmployeeBank.setAccountName(employeeDTO.getAccountName());
        ygEmployeeBank.setBankBranch(employeeDTO.getBankBranch());
        ygEmployeeBank.setDepositaryBank(employeeDTO.getDepositaryBank());
        ygEmployeeBank.setAccountOpeningAddress(employeeDTO.getAccountOpeningAddress());
        ygEmployeeBank.setUnionpayNumber(employeeDTO.getUnionpayNumber());
        ygEmployeeBank.setRemark(employeeDTO.getRemark());
        if (ygEmployeeBank == null) {
            ygEmployeeBank.setCreateTime(new Date());
            employeeMapper.insert(ygEmployeeBank);
        } else {
            employeeMapper.updateByPrimaryKeySelective(ygEmployeeBank);
        }
        return ApiRes.successResponseData("保存成功");
    }
}
