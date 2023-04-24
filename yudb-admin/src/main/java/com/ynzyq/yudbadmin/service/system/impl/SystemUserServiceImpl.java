package com.ynzyq.yudbadmin.service.system.impl;

import com.ynzyq.yudbadmin.core.model.LoginUserInfo;
import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.core.utils.ExampleBuilder;
import com.ynzyq.yudbadmin.dao.system.SystemUserMapper;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemUserDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.dao.system.model.SystemUserExample;
import com.ynzyq.yudbadmin.dao.system.vo.SystemUserListVO;
import com.ynzyq.yudbadmin.service.system.SystemRoleService;
import com.ynzyq.yudbadmin.service.system.SystemUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 系统用户Service实现
 * @author Caesar Liu
 * @date 2021/03/28 09:30
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private SystemRoleService systemRoleService;

    @Override
    public Integer create(SystemUser systemUser) {
        systemUser.setCreateTime(new Date());
        systemUserMapper.insertSelective(systemUser);
        return systemUser.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id: ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(SystemUser systemUser) {
        systemUser.setUpdateTime(new Date());
        systemUserMapper.updateByPrimaryKeySelective(systemUser);
    }

    @Override
    public void updateByIdInBatch(List<SystemUser> systemUsers) {
        if (CollectionUtils.isEmpty(systemUsers)) return;
        for (SystemUser systemUser: systemUsers) {
            this.updateById(systemUser);
        }
    }

    @Override
    public SystemUser findById(Integer id) {
        return systemUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemUser findOne(SystemUser systemUser) {
        ExampleBuilder<SystemUserExample, SystemUserExample.Criteria> builder = ExampleBuilder.create(SystemUserExample.class, SystemUserExample.Criteria.class);
        List<SystemUser> systemUsers = systemUserMapper.selectByExample(builder.buildExamplePack(systemUser).getExample());
        if (systemUsers.size() > 0) {
            return systemUsers.get(0);
        }
        return null;
    }

    @Override
    public List<SystemUser> findList(SystemUser systemUser) {
        ExampleBuilder<SystemUserExample, SystemUserExample.Criteria> builder = ExampleBuilder.create(SystemUserExample.class, SystemUserExample.Criteria.class);
        return systemUserMapper.selectByExample(builder.buildExamplePack(systemUser).getExample());
    }
  
    @Override
    public PageData<SystemUserListVO> findPage(PageWrap<QuerySystemUserDTO> pageWrap) {

        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        pageWrap.getModel().setUserId(loginUserInfo.getId());
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<SystemUserListVO> userList = systemUserMapper.selectList(pageWrap.getModel());

        for (SystemUserListVO user : userList) {
            user.setRoles(systemRoleService.findByUserId(user.getId()));
        }
        return PageData.from(new PageInfo<>(userList));
    }

    @Override
    public long count(SystemUser systemUser) {
        ExampleBuilder<SystemUserExample, SystemUserExample.Criteria> builder = ExampleBuilder.create(SystemUserExample.class, SystemUserExample.Criteria.class);
        return systemUserMapper.countByExample(builder.buildExamplePack(systemUser).getExample());
    }
}