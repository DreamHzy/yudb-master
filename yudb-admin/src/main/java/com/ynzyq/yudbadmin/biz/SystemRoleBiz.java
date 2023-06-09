package com.ynzyq.yudbadmin.biz;

import com.ynzyq.yudbadmin.dao.system.dto.CreateRoleMenuDTO;
import com.ynzyq.yudbadmin.dao.system.dto.CreateRolePermissionDTO;

/**
 * 角色权限业务处理
 * @author Caesar Liu
 * @date 2021-03-30 10:51
 */
public interface SystemRoleBiz {

    /**
     * 创建角色权限
     * @author Caesar Liu
     * @date 2021-03-30 10:51
     */
    void createRolePermission(CreateRolePermissionDTO dto);

    /**
     * 创建角色菜单
     * @author Caesar Liu
     * @date 2021-03-30 10:51
     */
    void createRoleMenu(CreateRoleMenuDTO dto);
}
