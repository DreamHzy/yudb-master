package com.ynzyq.yudbadmin.service.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.DepartmentMapper;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantMapper;
import com.ynzyq.yudbadmin.dao.business.dao.RegionalManagerDepartmentMapper;
import com.ynzyq.yudbadmin.dao.business.dao.RegionalManagerMapper;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.Merchant;
import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;
import com.ynzyq.yudbadmin.dao.business.model.RegionalManagerDepartment;
import com.ynzyq.yudbadmin.dao.business.vo.DepartmentListVo;
import com.ynzyq.yudbadmin.dao.business.vo.RegionalManagerPageVo;
import com.ynzyq.yudbadmin.service.business.RegionalManagerService;
import com.ynzyq.yudbadmin.util.Encrypt;
import com.ynzyq.yudbadmin.util.PhoneFormatCheckUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegionalManagerServiceImpl implements RegionalManagerService {

    @Resource
    RegionalManagerMapper regionalManagerMapper;
    @Resource
    DepartmentMapper departmentMapper;
    @Resource
    MerchantMapper merchantMapper;
    @Resource
    RegionalManagerDepartmentMapper regionalManagerDepartmentMapper;

    @Override
    public ApiRes<PageWrap<RegionalManagerPageVo>> findPage(PageWrap<RegionalManagerPageDto> pageWrap) {
        RegionalManagerPageDto regionalManagerPageDto = pageWrap.getModel();
        if (regionalManagerPageDto == null) {
            regionalManagerPageDto.setCondition(null);
        }
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<RegionalManagerPageVo> regionalManagerPageVoList = regionalManagerMapper.findPage(regionalManagerPageDto);
//        regionalManagerPageVoList.forEach(regionalManagerPageVo -> {
//            List<AgentAreaDTO> agentAreaList = merchantMapper.agentAreaList(Integer.parseInt(regionalManagerPageVo.getId()));
//            String agentArea = agentAreaList.stream().map(agentAreaDTO -> agentAreaDTO.getProvince() + agentAreaDTO.getCity() + agentAreaDTO.getArea()).collect(Collectors.joining(";"));
//            regionalManagerPageVo.setAgentArea(agentArea);
//        });
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(regionalManagerPageVoList)));
    }

    @Override
    public ApiRes updateStatus(RegionalManagerStatusDto regionalManagerStatusDto) {
        if (regionalManagerStatusDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = regionalManagerStatusDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "餐所错误", "");
        }
        String status = regionalManagerStatusDto.getStatus();
        if (!"1".equals(status) && !"2".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择正确的状态", "");
        }
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(id);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "内部错误", "");
        }
        if ("2".equals(status)) {//s说明要停用查询这个用户名下是否还有门店，如果有则不能停用
            Integer count = regionalManagerMapper.selectStroeCountById(id);
            if (count > 0) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "该区域经理名下还有门店，请先移除门店在停用", "");
            }
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        regionalManager.setUpdateUser(loginUserInfo.getId());
        regionalManager.setStatus(Integer.valueOf(status));
        regionalManager.setUpdateTime(new Date());
        regionalManagerMapper.updateByPrimaryKeySelective(regionalManager);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", "");
    }

    @Override
    public ApiRes<DepartmentListVo> departmentList() {
        List<DepartmentListVo> list = departmentMapper.quallDepartmentList();
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", list);
    }

    @Override
    public ApiRes add(RegionalManagerAddDto regionalManagerAddDto) {
        if (regionalManagerAddDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String name = regionalManagerAddDto.getName();
        if (StringUtils.isEmpty(name)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入姓名", "");
        }
        String phone = regionalManagerAddDto.getPhone();
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(phone)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入正确手机号", "");
        }
        String departmentId = regionalManagerAddDto.getDepartmentId();
        if (StringUtils.isEmpty(departmentId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择部门", "");
        }

        if (StringUtils.isBlank(regionalManagerAddDto.getUid())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入员工编号", "");
        }
        //先查询是否存在这个手机号(区域经理和加盟商)
        //加盟商
        Merchant merchant = merchantMapper.queryByPhone(phone);
        if (merchant != null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "该手机号已存在", "");
        }
        //业务经理
        RegionalManager regionalManager = regionalManagerMapper.queryByPhone(phone);
        if (regionalManager != null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "该手机号已存在", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        regionalManager = new RegionalManager();
        try {
            regionalManager.setUid(regionalManagerAddDto.getUid());
            regionalManager.setPassword(Encrypt.AESencrypt("123456"));
            regionalManager.setStatus(1);
            regionalManager.setCreateTime(new Date());
            regionalManager.setName(name);
            regionalManager.setCreateUser(loginUserInfo.getId());
            regionalManager.setMobile(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RegionalManagerDepartment regionalManagerDepartment = new RegionalManagerDepartment();
        regionalManagerDepartment.setDepartmentId(Integer.valueOf(departmentId));
        regionalManagerDepartment.setStatus(1);
        regionalManagerDepartment.setCreateUser(loginUserInfo.getId());
        regionalManagerDepartment.setCreateTime(new Date());
        regionalManagerMapper.insertSelective(regionalManager);
        regionalManagerDepartment.setRegionalManagerId(regionalManager.getId());
        regionalManagerDepartmentMapper.insertSelective(regionalManagerDepartment);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "添加成功", "");
    }

    @Override
    public ApiRes edit(RegionalManagerEditDto regionalManagerEditDto) {


        if (regionalManagerEditDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String name = regionalManagerEditDto.getName();
        if (StringUtils.isEmpty(name)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入姓名", "");
        }
        String departmentId = regionalManagerEditDto.getDepartmentId();
        if (StringUtils.isEmpty(departmentId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择部门", "");
        }
        if (StringUtils.isBlank(regionalManagerEditDto.getPhone())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入手机号", "");
        }
//        if (StringUtils.isBlank(regionalManagerEditDto.getUid())) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入员工编号", "");
//        }
        String id = regionalManagerEditDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择部门", "");
        }
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(id);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理不存在", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        regionalManager.setUpdateTime(new Date());
        regionalManager.setName(name);
        regionalManager.setMobile(regionalManagerEditDto.getPhone());
        regionalManager.setUpdateUser(loginUserInfo.getId());

        RegionalManagerDepartment regionalManagerDepartment = new RegionalManagerDepartment();
        regionalManagerDepartment.setDepartmentId(Integer.valueOf(departmentId));
        regionalManagerDepartment.setStatus(1);
        regionalManagerDepartment.setCreateUser(loginUserInfo.getId());
        regionalManagerDepartment.setCreateTime(new Date());
        regionalManagerDepartment.setRegionalManagerId(regionalManager.getId());


        regionalManagerMapper.updateByPrimaryKeySelective(regionalManager);
        //先将区域经理和部门关联变设置为无效
        regionalManagerDepartmentMapper.updateStatusByRegionalManagerId(regionalManager.getId());
        //添加
        regionalManagerDepartmentMapper.insertSelective(regionalManagerDepartment);

        return ApiRes.response(CommonConstant.SUCCESS_CODE, "编辑成功", "");

    }


}
