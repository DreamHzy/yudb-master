package com.ynzyq.yudbadmin.service.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.DepartmentMapper;
import com.ynzyq.yudbadmin.dao.business.dto.DepartmentAdd;
import com.ynzyq.yudbadmin.dao.business.dto.DepartmentPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.DepartmentUpdateDto;
import com.ynzyq.yudbadmin.dao.business.model.Department;
import com.ynzyq.yudbadmin.dao.business.vo.DepartmentPageVo;
import com.ynzyq.yudbadmin.dao.system.model.SystemMenu;
import com.ynzyq.yudbadmin.dao.system.vo.SystemLogListVO;
import com.ynzyq.yudbadmin.dao.system.vo.SystemMenuListVO;
import com.ynzyq.yudbadmin.service.business.DepartmentService;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentMapper departmentMapper;

    @Override
    public ApiRes<DepartmentPageVo> findPage() {
        List<DepartmentPageVo> menus = departmentMapper.selectList();

        List<DepartmentPageVo> rootMenus = new ArrayList<>();
        // 添加根菜单
        for (DepartmentPageVo menu : menus) {
            if (menu.getParentId() == null) {
                DepartmentPageVo rootMenu = new DepartmentPageVo();
                BeanUtils.copyProperties(menu, rootMenu, "children");
                rootMenu.setChildren(new ArrayList<>());
                rootMenus.add(rootMenu);
            }
        }
        menus.removeIf(menu -> menu.getParentId() == null);
        for (DepartmentPageVo child : rootMenus) {
            this.fillChildren(child, menus);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", rootMenus);
    }


    /**
     * 填充子部门
     *
     * @param parentMenu
     * @param menus
     */
    private void fillChildren(DepartmentPageVo parentMenu, List<DepartmentPageVo> menus) {
        if (menus.size() == 0) {
            return;
        }
        List<Integer> handledIds = new ArrayList<>();
        for (DepartmentPageVo menu : menus) {
            if (menu.getParentId().equals(parentMenu.getId())) {
                DepartmentPageVo child = new DepartmentPageVo();
                BeanUtils.copyProperties(menu, child, "children");
                child.setChildren(new ArrayList<>());
                parentMenu.getChildren().add(child);
                handledIds.add(menu.getId());
            }
        }
        menus.removeIf(menu -> handledIds.contains(menu.getId()));
        parentMenu.setHasChildren(Boolean.TRUE);
        if (parentMenu.getChildren().size() > 0) {
            parentMenu.setHasChildren(Boolean.FALSE);
            for (DepartmentPageVo child : parentMenu.getChildren()) {
                this.fillChildren(child, menus);
            }
        }
    }

    @Override
    public ApiRes add(DepartmentAdd departmentAdd) {
        if (departmentAdd == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", null);
        }
        String name = departmentAdd.getName();
        if (StringUtils.isEmpty(name)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", null);
        }
        //先查询部门是否存在
        Department department = departmentMapper.queryByName(name);
        if (department != null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "部门已存在", null);
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        department = new Department();
        department.setParentId(departmentAdd.getParentId());
        department.setName(name);
        department.setStatus(1);
        department.setCreateUser(loginUserInfo.getId());
        department.setCreateTime(new Date());
        departmentMapper.insertSelective(department);
        return new ApiRes(CommonConstant.SUCCESS_CODE, "部门创建成功", null);

    }

    @Override
    public ApiRes update(DepartmentUpdateDto departmentUpdateDto) {
        if (departmentUpdateDto == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", null);
        }
        String name = departmentUpdateDto.getName();
        if (StringUtils.isEmpty(name)) {
            return new ApiRes(CommonConstant.FAIL_CODE, "参数错误", null);
        }
        //先查询部门是否存在
        Department department = departmentMapper.selectByPrimaryKey(departmentUpdateDto.getId());
        if (department == null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "部门不存在", null);
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        //查询是否存在该名字部门
        Department departmentOld = departmentMapper.queryByNameAndNotInId(department);
        if (departmentOld != null) {
            return new ApiRes(CommonConstant.FAIL_CODE, "已有相同名称的部门", null);
        }
        department.setName(name);
        department.setUpdateTime(new Date());
        department.setUpdateUser(loginUserInfo.getId());
        departmentMapper.updateByPrimaryKeySelective(department);
        return new ApiRes(CommonConstant.SUCCESS_CODE, "修改成功", null);
    }
}
