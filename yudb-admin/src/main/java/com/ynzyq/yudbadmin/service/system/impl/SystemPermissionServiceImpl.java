package com.ynzyq.yudbadmin.service.system.impl;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.core.utils.ExampleBuilder;
import com.ynzyq.yudbadmin.dao.system.SystemPermissionMapper;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemPermissionDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemPermission;
import com.ynzyq.yudbadmin.dao.system.model.SystemPermissionExample;
import com.ynzyq.yudbadmin.dao.system.vo.SystemPermissionListVO;
import com.ynzyq.yudbadmin.service.system.SystemPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 系统权限Service实现
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
@Service
public class SystemPermissionServiceImpl implements SystemPermissionService {

    @Autowired
    private SystemPermissionMapper systemPermissionMapper;

    @Override
    public Integer create(SystemPermission systemPermission) {
        systemPermission.setCreateTime(new Date());
        systemPermissionMapper.insertSelective(systemPermission);
        return systemPermission.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemPermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id: ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(SystemPermission systemPermission) {
        systemPermission.setUpdateTime(new Date());
        systemPermissionMapper.updateByPrimaryKeySelective(systemPermission);
    }

    @Override
    public void updateByIdInBatch(List<SystemPermission> systemPermissions) {
        if (CollectionUtils.isEmpty(systemPermissions)) return;
        for (SystemPermission systemPermission: systemPermissions) {
            this.updateById(systemPermission);
        }
    }

    @Override
    public SystemPermission findById(Integer id) {
        return systemPermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemPermission findOne(SystemPermission systemPermission) {
        ExampleBuilder<SystemPermissionExample, SystemPermissionExample.Criteria> builder = ExampleBuilder.create(SystemPermissionExample.class, SystemPermissionExample.Criteria.class);
        List<SystemPermission> systemPermissions = systemPermissionMapper.selectByExample(builder.buildExamplePack(systemPermission).getExample());
        if (systemPermissions.size() > 0) {
            return systemPermissions.get(0);
        }
        return null;
    }

    @Override
    public List<SystemPermission> findList(SystemPermission systemPermission) {
        ExampleBuilder<SystemPermissionExample, SystemPermissionExample.Criteria> builder = ExampleBuilder.create(SystemPermissionExample.class, SystemPermissionExample.Criteria.class);
        return systemPermissionMapper.selectByExample(builder.buildExamplePack(systemPermission).getExample());
    }
  
    @Override
    public PageData<SystemPermissionListVO> findPage(PageWrap<QuerySystemPermissionDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        return PageData.from(new PageInfo<>(systemPermissionMapper.selectList(pageWrap.getModel())));
    }

    @Override
    public List<SystemPermission> findByUserId(Integer userId) {
        return systemPermissionMapper.selectByUserId(userId);
    }

    @Override
    public List<SystemPermission> findByRoleId(Integer roleId) {
        return systemPermissionMapper.selectByRoleId(roleId);
    }

    @Override
    public long count(SystemPermission systemPermission) {
        ExampleBuilder<SystemPermissionExample, SystemPermissionExample.Criteria> builder = ExampleBuilder.create(SystemPermissionExample.class, SystemPermissionExample.Criteria.class);
        return systemPermissionMapper.countByExample(builder.buildExamplePack(systemPermission).getExample());
    }
}