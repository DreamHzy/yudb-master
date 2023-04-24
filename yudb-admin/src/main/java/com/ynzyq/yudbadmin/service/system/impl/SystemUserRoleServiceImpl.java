package com.ynzyq.yudbadmin.service.system.impl;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.core.utils.ExampleBuilder;
import com.ynzyq.yudbadmin.dao.system.SystemUserRoleMapper;
import com.ynzyq.yudbadmin.dao.system.model.SystemUserRole;
import com.ynzyq.yudbadmin.dao.system.model.SystemUserRoleExample;
import com.ynzyq.yudbadmin.service.system.SystemUserRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 用户角色关联Service实现
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
@Service
public class SystemUserRoleServiceImpl implements SystemUserRoleService {

    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    @Override
    public Integer create(SystemUserRole systemUserRole) {
        systemUserRole.setCreateTime(new Date());
        systemUserRoleMapper.insertSelective(systemUserRole);
        return systemUserRole.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemUserRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(SystemUserRole systemUserRole) {
        ExampleBuilder<SystemUserRoleExample, SystemUserRoleExample.Criteria> builder = ExampleBuilder.create(SystemUserRoleExample.class, SystemUserRoleExample.Criteria.class);
        SystemUserRole newUserRole = new SystemUserRole();
        newUserRole.setDeleted(Boolean.TRUE);
        systemUserRoleMapper.updateByExampleSelective(newUserRole, builder.buildExamplePack(systemUserRole).getExample());
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id: ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(SystemUserRole systemUserRole) {
        systemUserRoleMapper.updateByPrimaryKeySelective(systemUserRole);
    }

    @Override
    public void updateByIdInBatch(List<SystemUserRole> systemUserRoles) {
        if (CollectionUtils.isEmpty(systemUserRoles)) return;
        for (SystemUserRole systemUserRole: systemUserRoles) {
            this.updateById(systemUserRole);
        }
    }

    @Override
    public SystemUserRole findById(Integer id) {
        return systemUserRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemUserRole findOne(SystemUserRole systemUserRole) {
        ExampleBuilder<SystemUserRoleExample, SystemUserRoleExample.Criteria> builder = ExampleBuilder.create(SystemUserRoleExample.class, SystemUserRoleExample.Criteria.class);
        List<SystemUserRole> systemUserRoles = systemUserRoleMapper.selectByExample(builder.buildExamplePack(systemUserRole).getExample());
        if (systemUserRoles.size() > 0) {
            return systemUserRoles.get(0);
        }
        return null;
    }

    @Override
    public List<SystemUserRole> findList(SystemUserRole systemUserRole) {
        ExampleBuilder<SystemUserRoleExample, SystemUserRoleExample.Criteria> builder = ExampleBuilder.create(SystemUserRoleExample.class, SystemUserRoleExample.Criteria.class);
        return systemUserRoleMapper.selectByExample(builder.buildExamplePack(systemUserRole).getExample());
    }
  
    @Override
    public PageData<SystemUserRole> findPage(PageWrap pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        ExampleBuilder<SystemUserRoleExample, SystemUserRoleExample.Criteria> builder = ExampleBuilder.create(SystemUserRoleExample.class, SystemUserRoleExample.Criteria.class);
        ExampleBuilder.ExamplePack<SystemUserRoleExample, SystemUserRoleExample.Criteria> pack = builder.buildExamplePack(pageWrap.getModel());
        pack.getExample().setOrderByClause(pageWrap.getOrderByClause());
        return PageData.from(new PageInfo<>(systemUserRoleMapper.selectByExample(pack.getExample())));
    }

    @Override
    public long count(SystemUserRole systemUserRole) {
        ExampleBuilder<SystemUserRoleExample, SystemUserRoleExample.Criteria> builder = ExampleBuilder.create(SystemUserRoleExample.class, SystemUserRoleExample.Criteria.class);
        return systemUserRoleMapper.countByExample(builder.buildExamplePack(systemUserRole).getExample());
    }
}