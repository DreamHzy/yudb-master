package com.ynzyq.yudbadmin.service.system.impl;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.core.utils.ExampleBuilder;
import com.ynzyq.yudbadmin.dao.system.SystemRoleMapper;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemRoleDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemRole;
import com.ynzyq.yudbadmin.dao.system.model.SystemRoleExample;
import com.ynzyq.yudbadmin.dao.system.vo.SystemRoleListVO;
import com.ynzyq.yudbadmin.service.system.SystemMenuService;
import com.ynzyq.yudbadmin.service.system.SystemPermissionService;
import com.ynzyq.yudbadmin.service.system.SystemRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 系统角色Service实现
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private SystemPermissionService systemPermissionService;

    @Override
    public Integer create(SystemRole systemRole) {
        systemRole.setCreateTime(new Date());
        systemRoleMapper.insertSelective(systemRole);
        return systemRole.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id: ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(SystemRole systemRole) {
        systemRole.setUpdateTime(new Date());
        systemRoleMapper.updateByPrimaryKeySelective(systemRole);
    }

    @Override
    public void updateByIdInBatch(List<SystemRole> systemRoles) {
        if (CollectionUtils.isEmpty(systemRoles)) return;
        for (SystemRole systemRole: systemRoles) {
            this.updateById(systemRole);
        }
    }

    @Override
    public SystemRole findById(Integer id) {
        return systemRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemRole findOne(SystemRole systemRole) {
        ExampleBuilder<SystemRoleExample, SystemRoleExample.Criteria> builder = ExampleBuilder.create(SystemRoleExample.class, SystemRoleExample.Criteria.class);
        List<SystemRole> systemRoles = systemRoleMapper.selectByExample(builder.buildExamplePack(systemRole).getExample());
        if (systemRoles.size() > 0) {
            return systemRoles.get(0);
        }
        return null;
    }

    @Override
    public List<SystemRole> findList(SystemRole systemRole) {
        systemRole.setDeleted(Boolean.FALSE);
        ExampleBuilder<SystemRoleExample, SystemRoleExample.Criteria> builder = ExampleBuilder.create(SystemRoleExample.class, SystemRoleExample.Criteria.class);
        return systemRoleMapper.selectByExample(builder.buildExamplePack(systemRole).getExample());
    }

    @Override
    public List<SystemRole> findByUserId(Integer userId) {
        return systemRoleMapper.selectByUserId(userId);
    }

    @Override
    public PageData<SystemRoleListVO> findPage(PageWrap<QuerySystemRoleDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<SystemRoleListVO> roleList = systemRoleMapper.selectList(pageWrap.getModel());
        for (SystemRoleListVO role : roleList) {
            role.setMenus(systemMenuService.findByRoleId(role.getId()));
            role.setPermissions(systemPermissionService.findByRoleId(role.getId()));
        }
        return PageData.from(new PageInfo<>(roleList));
    }

    @Override
    public long count(SystemRole systemRole) {
        ExampleBuilder<SystemRoleExample, SystemRoleExample.Criteria> builder = ExampleBuilder.create(SystemRoleExample.class, SystemRoleExample.Criteria.class);
        return systemRoleMapper.countByExample(builder.buildExamplePack(systemRole).getExample());
    }
}