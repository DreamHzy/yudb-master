package com.ynzyq.yudbadmin.dao.system.vo;

import com.ynzyq.yudbadmin.dao.system.model.SystemMenu;
import com.ynzyq.yudbadmin.dao.system.model.SystemPermission;
import com.ynzyq.yudbadmin.dao.system.model.SystemRole;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import lombok.Data;

import java.util.List;

/**
 * 系统角色列表视图对象
 * @author Caesar Liu
 * @date 2021-03-30 11:35
 */
@Data
public class SystemRoleListVO extends SystemRole {

    private List<SystemPermission> permissions;

    private List<SystemMenu> menus;

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;
}
