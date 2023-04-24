package com.ynzyq.yudbadmin.dao.system.vo;

import com.ynzyq.yudbadmin.dao.system.model.SystemPermission;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import lombok.Data;

/**
 * 系统权限列表视图对象
 * @author Caesar Liu
 * @date 2021-03-31 15:09
 */
@Data
public class SystemPermissionListVO extends SystemPermission {

    private SystemUser createUserInfo;

    private SystemUser updateUserInfo;
}
