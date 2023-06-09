package com.ynzyq.yudbadmin.biz;

import com.ynzyq.yudbadmin.dao.system.dto.CreateUserRoleDTO;
import com.ynzyq.yudbadmin.dao.system.dto.ResetSystemUserPwdDTO;
import com.ynzyq.yudbadmin.dao.system.dto.UpdatePwdDto;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;

/**
 * 系统用户业务处理
 * @author Caesar Liu
 * @date 2021-03-29 22:24
 */
public interface SystemUserBiz {

    /**
     * 修改密码
     * @author Caesar Liu
     * @date 2021-03-31 14:14
     */
    void updatePwd(UpdatePwdDto dto);

    /**
     * 重置密码
     * @author Caesar Liu
     * @date 2021-03-31 20:01
     */
    void resetPwd (ResetSystemUserPwdDTO dto);

    /**
     * 创建用户
     * @author Caesar Liu
     * @date 2021-03-31 17:03
     */
    void create(SystemUser systemUser);

    /**
     * 修改用户信息
     * @author Caesar Liu
     * @date 2021-03-31 22:14
     */
    void updateById(SystemUser systemUser);

    /**
     * 创建用户角色
     * @author Caesar Liu
     * @date 2021-03-29 22:24
     */
    void createUserRole(CreateUserRoleDTO dto);
}
