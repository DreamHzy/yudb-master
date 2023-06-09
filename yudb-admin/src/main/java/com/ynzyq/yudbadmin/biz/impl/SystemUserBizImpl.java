package com.ynzyq.yudbadmin.biz.impl;

import com.ynzyq.yudbadmin.biz.SystemUserBiz;
import com.ynzyq.yudbadmin.core.model.BusinessException;
import com.ynzyq.yudbadmin.core.model.ResponseStatus;
import com.ynzyq.yudbadmin.core.utils.SecureUtil;
import com.ynzyq.yudbadmin.dao.system.dto.CreateUserRoleDTO;
import com.ynzyq.yudbadmin.dao.system.dto.ResetSystemUserPwdDTO;
import com.ynzyq.yudbadmin.dao.system.dto.UpdatePwdDto;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.dao.system.model.SystemUserRole;
import com.ynzyq.yudbadmin.service.system.SystemUserRoleService;
import com.ynzyq.yudbadmin.service.system.SystemUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SystemUserBizImpl implements SystemUserBiz {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @Override
    public void updatePwd(UpdatePwdDto dto) {
        Assert.notNull(dto.getUserId(), "缺少参数");
        Assert.notNull(dto.getOldPwd(), "缺少参数");
        Assert.notNull(dto.getNewPwd(), "缺少参数");
        SystemUser user = systemUserService.findById(dto.getUserId());
        if (user.getDeleted()) {
            throw new BusinessException(ResponseStatus.DATA_EMPTY.getCode(), "用户已经删除");
        }
        // 验证旧密码
        if (!user.getPassword().equals(SecureUtil.encryptPassword(dto.getOldPwd(), user.getSalt()))) {
            throw new BusinessException(ResponseStatus.PWD_INCORRECT.getCode(), "旧密码不正确");
        }
        // 修改密码
        SystemUser newUser = new SystemUser();
        newUser.setId(dto.getUserId());
        newUser.setPassword(SecureUtil.encryptPassword(dto.getNewPwd(), user.getSalt()));
        newUser.setUpdateUser(dto.getUserId());
        systemUserService.updateById(newUser);
    }

    @Override
    public void resetPwd(ResetSystemUserPwdDTO dto) {
        Assert.notNull(dto.getId(), "缺少参数");
        Assert.notNull(dto.getPassword(), "缺少密码");
        Assert.notNull(dto.getOperaUserId(), "缺少参数");
        // 查询用户
        SystemUser systemUser = systemUserService.findById(dto.getId());
        if (systemUser == null || systemUser.getDeleted()) {
            throw new BusinessException(ResponseStatus.DATA_EMPTY.getCode(), "用户不存在或已被删除");
        }
        // 修改密码
        SystemUser updateUserDto = new SystemUser();
        updateUserDto.setId(dto.getId());
        updateUserDto.setPassword(SecureUtil.encryptPassword(dto.getPassword(), systemUser.getSalt()));
        updateUserDto.setUpdateUser(dto.getOperaUserId());
        systemUserService.updateById(updateUserDto);
    }

    @Override
    public void create(SystemUser systemUser) {
        Assert.notNull(systemUser.getUsername(), "缺少参数");
        Assert.notNull(systemUser.getRealname(), "缺少参数");
        Assert.notNull(systemUser.getCreateUser(), "缺少参数");
        Assert.notNull(systemUser.getPassword(), "缺少参数");
        // 验证用户名
        SystemUser queryUserDto = new SystemUser();
        queryUserDto.setDeleted(Boolean.FALSE);
        queryUserDto.setUsername(systemUser.getUsername());
        SystemUser user = systemUserService.findOne(queryUserDto);
        if (user != null) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "用户名已被占用");
        }
        // 验证工号
        if (StringUtils.isNotBlank(systemUser.getEmpNo())) {
            queryUserDto = new SystemUser();
            queryUserDto.setDeleted(Boolean.FALSE);
            queryUserDto.setEmpNo(systemUser.getEmpNo());
            user = systemUserService.findOne(queryUserDto);
            if (user != null) {
                throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "工号已被占用");
            }
        }
        // 生成密码盐
        String salt = RandomStringUtils.randomAlphabetic(6);
        // 创建用户
        systemUser.setPassword(SecureUtil.encryptPassword(systemUser.getPassword(), salt));
        systemUser.setSalt(salt);
        systemUserService.create(systemUser);
    }

    @Override
    public void updateById(SystemUser systemUser) {
        Assert.notNull(systemUser.getUsername(), "缺少参数");
        Assert.notNull(systemUser.getRealname(), "缺少参数");
        Assert.notNull(systemUser.getUpdateUser(), "缺少参数");
        // 验证用户名
        SystemUser queryUserDto = new SystemUser();
        queryUserDto.setDeleted(Boolean.FALSE);
        queryUserDto.setUsername(systemUser.getUsername());
        SystemUser user = systemUserService.findOne(queryUserDto);
        if (user != null && !user.getId().equals(systemUser.getId())) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "用户名已被占用");
        }
        // 验证工号
        if (StringUtils.isNotBlank(systemUser.getEmpNo())) {
            queryUserDto = new SystemUser();
            queryUserDto.setDeleted(Boolean.FALSE);
            queryUserDto.setEmpNo(systemUser.getEmpNo());
            user = systemUserService.findOne(queryUserDto);
            if (user != null && !user.getId().equals(systemUser.getId())) {
                throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "工号已被占用");
            }
        }
        // 修改用户
        systemUserService.updateById(systemUser);
    }

    @Override
    public void createUserRole(CreateUserRoleDTO dto) {
        // 删除关联角色
        SystemUserRole deleteDto = new SystemUserRole();
        deleteDto.setUserId(dto.getUserId());
        systemUserRoleService.delete(deleteDto);
        // 新增新的角色
        for (Integer roleId : dto.getRoleIds()) {
            SystemUserRole newUserRole = new SystemUserRole();
            newUserRole.setUserId(dto.getUserId());
            newUserRole.setCreateUser(dto.getCreateUser());
            newUserRole.setRoleId(roleId);
            systemUserRoleService.create(newUserRole);
        }
    }
}
