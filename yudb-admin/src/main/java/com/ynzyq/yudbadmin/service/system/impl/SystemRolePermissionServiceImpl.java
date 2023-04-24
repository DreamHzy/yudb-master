package com.ynzyq.yudbadmin.service.system.impl;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.core.utils.ExampleBuilder;
import com.ynzyq.yudbadmin.dao.system.SystemRolePermissionMapper;
import com.ynzyq.yudbadmin.dao.system.model.SystemRolePermission;
import com.ynzyq.yudbadmin.dao.system.model.SystemRolePermissionExample;
import com.ynzyq.yudbadmin.service.system.SystemRolePermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 角色权限关联Service实现
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
@Service
public class SystemRolePermissionServiceImpl implements SystemRolePermissionService {

    @Autowired
    private SystemRolePermissionMapper systemRolePermissionMapper;

    @Override
    public Integer create(SystemRolePermission systemRolePermission) {
        systemRolePermission.setCreateTime(new Date());
        systemRolePermissionMapper.insertSelective(systemRolePermission);
        return systemRolePermission.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemRolePermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(SystemRolePermission systemRolePermission) {
        ExampleBuilder<SystemRolePermissionExample, SystemRolePermissionExample.Criteria> builder = ExampleBuilder.create(SystemRolePermissionExample.class, SystemRolePermissionExample.Criteria.class);
        SystemRolePermission newPermission = new SystemRolePermission();
        newPermission.setDeleted(Boolean.TRUE);
        systemRolePermissionMapper.updateByExampleSelective(newPermission, builder.buildExamplePack(systemRolePermission).getExample());
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id: ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(SystemRolePermission systemRolePermission) {
        systemRolePermission.setUpdateTime(new Date());
        systemRolePermissionMapper.updateByPrimaryKeySelective(systemRolePermission);
    }

    @Override
    public void updateByIdInBatch(List<SystemRolePermission> systemRolePermissions) {
        if (CollectionUtils.isEmpty(systemRolePermissions)) return;
        for (SystemRolePermission systemRolePermission: systemRolePermissions) {
            this.updateById(systemRolePermission);
        }
    }

    @Override
    public SystemRolePermission findById(Integer id) {
        return systemRolePermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemRolePermission findOne(SystemRolePermission systemRolePermission) {
        ExampleBuilder<SystemRolePermissionExample, SystemRolePermissionExample.Criteria> builder = ExampleBuilder.create(SystemRolePermissionExample.class, SystemRolePermissionExample.Criteria.class);
        List<SystemRolePermission> systemRolePermissions = systemRolePermissionMapper.selectByExample(builder.buildExamplePack(systemRolePermission).getExample());
        if (systemRolePermissions.size() > 0) {
            return systemRolePermissions.get(0);
        }
        return null;
    }

    @Override
    public List<SystemRolePermission> findList(SystemRolePermission systemRolePermission) {
        ExampleBuilder<SystemRolePermissionExample, SystemRolePermissionExample.Criteria> builder = ExampleBuilder.create(SystemRolePermissionExample.class, SystemRolePermissionExample.Criteria.class);
        return systemRolePermissionMapper.selectByExample(builder.buildExamplePack(systemRolePermission).getExample());
    }
  
    @Override
    public PageData<SystemRolePermission> findPage(PageWrap pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        ExampleBuilder<SystemRolePermissionExample, SystemRolePermissionExample.Criteria> builder = ExampleBuilder.create(SystemRolePermissionExample.class, SystemRolePermissionExample.Criteria.class);
        ExampleBuilder.ExamplePack<SystemRolePermissionExample, SystemRolePermissionExample.Criteria> pack = builder.buildExamplePack(pageWrap.getModel());
        pack.getExample().setOrderByClause(pageWrap.getOrderByClause());
        return PageData.from(new PageInfo<>(systemRolePermissionMapper.selectByExample(pack.getExample())));
    }

    @Override
    public long count(SystemRolePermission systemRolePermission) {
        ExampleBuilder<SystemRolePermissionExample, SystemRolePermissionExample.Criteria> builder = ExampleBuilder.create(SystemRolePermissionExample.class, SystemRolePermissionExample.Criteria.class);
        return systemRolePermissionMapper.countByExample(builder.buildExamplePack(systemRolePermission).getExample());
    }
}