package com.ynzyq.yudbadmin.service.system.impl;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.core.utils.ExampleBuilder;
import com.ynzyq.yudbadmin.dao.system.SystemRoleMenuMapper;
import com.ynzyq.yudbadmin.dao.system.model.SystemRoleMenu;
import com.ynzyq.yudbadmin.dao.system.model.SystemRoleMenuExample;
import com.ynzyq.yudbadmin.service.system.SystemRoleMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 角色菜单关联Service实现
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
@Service
public class SystemRoleMenuServiceImpl implements SystemRoleMenuService {

    @Autowired
    private SystemRoleMenuMapper systemRoleMenuMapper;

    @Override
    public Integer create(SystemRoleMenu systemRoleMenu) {
        systemRoleMenu.setCreateTime(new Date());
        systemRoleMenuMapper.insertSelective(systemRoleMenu);
        return systemRoleMenu.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemRoleMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delete(SystemRoleMenu systemRoleMenu) {
        ExampleBuilder<SystemRoleMenuExample, SystemRoleMenuExample.Criteria> builder =
                ExampleBuilder.create(SystemRoleMenuExample.class, SystemRoleMenuExample.Criteria.class);
        SystemRoleMenu newRoleMenu = new SystemRoleMenu();
        newRoleMenu.setDeleted(Boolean.TRUE);
        newRoleMenu.setUpdateUser(systemRoleMenu.getUpdateUser());
        newRoleMenu.setUpdateTime(new Date());
        systemRoleMenuMapper.updateByExampleSelective(newRoleMenu, builder.buildExamplePack(systemRoleMenu).getExample());
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id: ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(SystemRoleMenu systemRoleMenu) {
        systemRoleMenu.setUpdateTime(new Date());
        systemRoleMenuMapper.updateByPrimaryKeySelective(systemRoleMenu);
    }

    @Override
    public void updateByIdInBatch(List<SystemRoleMenu> systemRoleMenus) {
        if (CollectionUtils.isEmpty(systemRoleMenus)) return;
        for (SystemRoleMenu systemRoleMenu: systemRoleMenus) {
            this.updateById(systemRoleMenu);
        }
    }

    @Override
    public SystemRoleMenu findById(Integer id) {
        return systemRoleMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemRoleMenu findOne(SystemRoleMenu systemRoleMenu) {
        ExampleBuilder<SystemRoleMenuExample, SystemRoleMenuExample.Criteria> builder = ExampleBuilder.create(SystemRoleMenuExample.class, SystemRoleMenuExample.Criteria.class);
        List<SystemRoleMenu> systemRoleMenus = systemRoleMenuMapper.selectByExample(builder.buildExamplePack(systemRoleMenu).getExample());
        if (systemRoleMenus.size() > 0) {
            return systemRoleMenus.get(0);
        }
        return null;
    }

    @Override
    public List<SystemRoleMenu> findList(SystemRoleMenu systemRoleMenu) {
        ExampleBuilder<SystemRoleMenuExample, SystemRoleMenuExample.Criteria> builder = ExampleBuilder.create(SystemRoleMenuExample.class, SystemRoleMenuExample.Criteria.class);
        return systemRoleMenuMapper.selectByExample(builder.buildExamplePack(systemRoleMenu).getExample());
    }
  
    @Override
    public PageData<SystemRoleMenu> findPage(PageWrap pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        ExampleBuilder<SystemRoleMenuExample, SystemRoleMenuExample.Criteria> builder = ExampleBuilder.create(SystemRoleMenuExample.class, SystemRoleMenuExample.Criteria.class);
        ExampleBuilder.ExamplePack<SystemRoleMenuExample, SystemRoleMenuExample.Criteria> pack = builder.buildExamplePack(pageWrap.getModel());
        pack.getExample().setOrderByClause(pageWrap.getOrderByClause());
        return PageData.from(new PageInfo<>(systemRoleMenuMapper.selectByExample(pack.getExample())));
    }

    @Override
    public long count(SystemRoleMenu systemRoleMenu) {
        ExampleBuilder<SystemRoleMenuExample, SystemRoleMenuExample.Criteria> builder = ExampleBuilder.create(SystemRoleMenuExample.class, SystemRoleMenuExample.Criteria.class);
        return systemRoleMenuMapper.countByExample(builder.buildExamplePack(systemRoleMenu).getExample());
    }
}