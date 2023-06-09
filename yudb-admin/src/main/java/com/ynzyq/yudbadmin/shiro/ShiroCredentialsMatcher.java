package com.ynzyq.yudbadmin.shiro;

import com.ynzyq.yudbadmin.core.utils.SecureUtil;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.service.system.SystemUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Shiro密码比对处理
 *
 * @author Caesar Liu
 * @date 2021-03-31 18:03
 */
@Component
public class ShiroCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private SystemUserService systemUserService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String userName = usernamePasswordToken.getUsername();
        String arr[] = userName.split(",");
        if (arr.length != 2) {
            SystemUser queryUserDto = new SystemUser();
            queryUserDto.setUsername(usernamePasswordToken.getUsername());
            queryUserDto.setDeleted(Boolean.FALSE);
            SystemUser systemUser = systemUserService.findOne(queryUserDto);
            if (systemUser == null) {
                return Boolean.FALSE;
            }
            String pwd = SecureUtil.encryptPassword(new String(usernamePasswordToken.getPassword()), systemUser.getSalt());
            return this.equals(pwd, systemUser.getPassword());
        }
        return true;
    }
}
