package com.ynzyq.yudbadmin.biz.impl;

import com.ynzyq.yudbadmin.biz.SystemRoleBiz;
import com.ynzyq.yudbadmin.dao.system.dto.CreateRoleMenuDTO;
import com.ynzyq.yudbadmin.dao.system.dto.CreateRolePermissionDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemRoleMenu;
import com.ynzyq.yudbadmin.dao.system.model.SystemRolePermission;
import com.ynzyq.yudbadmin.service.system.SystemRoleMenuService;
import com.ynzyq.yudbadmin.service.system.SystemRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemRoleBizImpl implements SystemRoleBiz {

    @Autowired
    private SystemRolePermissionService systemRolePermissionService;

    @Autowired
    private SystemRoleMenuService systemRoleMenuService;

    @Override
    public void createRolePermission(CreateRolePermissionDTO dto) {
        // 删除关联权限
        SystemRolePermission deleteDto = new SystemRolePermission();
        deleteDto.setRoleId(dto.getRoleId());
        systemRolePermissionService.delete(deleteDto);
        // 新增新的权限
        for (Integer permissionId : dto.getPermissionIds()) {
            SystemRolePermission newRolePermission = new SystemRolePermission();
            newRolePermission.setRoleId(dto.getRoleId());
            newRolePermission.setCreateUser(dto.getCreateUser());
            newRolePermission.setPermissionId(permissionId);
            systemRolePermissionService.create(newRolePermission);
        }
    }

    @Override
    public void createRoleMenu(CreateRoleMenuDTO dto) {
        // 删除关联权限
        SystemRoleMenu deleteDto = new SystemRoleMenu();
        deleteDto.setRoleId(dto.getRoleId());
        systemRoleMenuService.delete(deleteDto);
        // 新增新的权限
        for (Integer menuId : dto.getMenuIds()) {
            SystemRoleMenu newRoleMenu = new SystemRoleMenu();
            newRoleMenu.setRoleId(dto.getRoleId());
            newRoleMenu.setCreateUser(dto.getCreateUser());
            newRoleMenu.setMenuId(menuId);
            systemRoleMenuService.create(newRoleMenu);
        }
    }
}
