package com.ynzyq.yudbadmin.service.system.impl;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.core.utils.ExampleBuilder;
import com.ynzyq.yudbadmin.dao.system.SystemMenuMapper;
import com.ynzyq.yudbadmin.dao.system.model.SystemMenu;
import com.ynzyq.yudbadmin.dao.system.model.SystemMenuExample;
import com.ynzyq.yudbadmin.dao.system.vo.SystemMenuListVO;
import com.ynzyq.yudbadmin.service.system.SystemMenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 系统菜单Service实现
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
@Service
public class SystemMenuServiceImpl implements SystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Override
    public Integer create(SystemMenu systemMenu) {
        systemMenu.setCreateTime(new Date());
        //查询上级菜单所属系统
        SystemMenu systemMenuOld = systemMenuMapper.selectByPrimaryKey(systemMenu.getParentId());
        systemMenu.setIsSystem(systemMenuOld.getIsSystem());
        systemMenuMapper.insertSelective(systemMenu);
        return systemMenu.getId();
    }

    @Override
    public void deleteById(Integer id) {
        systemMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id: ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(SystemMenu systemMenu) {
        systemMenu.setUpdateTime(new Date());
        systemMenuMapper.updateByPrimaryKeySelective(systemMenu);
    }

    @Override
    public void updateByIdInBatch(List<SystemMenu> systemMenus) {
        if (CollectionUtils.isEmpty(systemMenus)) return;
        for (SystemMenu systemMenu: systemMenus) {
            this.updateById(systemMenu);
        }
    }

    @Override
    public SystemMenu findById(Integer id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemMenu findOne(SystemMenu systemMenu) {
        ExampleBuilder<SystemMenuExample, SystemMenuExample.Criteria> builder = ExampleBuilder.create(SystemMenuExample.class, SystemMenuExample.Criteria.class);
        List<SystemMenu> systemMenus = systemMenuMapper.selectByExample(builder.buildExamplePack(systemMenu).getExample());
        if (systemMenus.size() > 0) {
            return systemMenus.get(0);
        }
        return null;
    }

    @Override
    public List<SystemMenu> findList(SystemMenu systemMenu) {
        systemMenu.setDeleted(Boolean.FALSE);
        ExampleBuilder<SystemMenuExample, SystemMenuExample.Criteria> builder = ExampleBuilder.create(SystemMenuExample.class, SystemMenuExample.Criteria.class);
        ExampleBuilder.ExamplePack<SystemMenuExample, SystemMenuExample.Criteria> pack = builder.buildExamplePack(systemMenu);
        pack.getExample().setOrderByClause("SORT");
        return systemMenuMapper.selectByExample(pack.getExample());
    }

    @Override
    public List<SystemMenuListVO> findList() {
        return systemMenuMapper.selectList();
    }

    @Override
    public List<SystemMenu> findRootList() {
        SystemMenuExample example = new SystemMenuExample();
        example.createCriteria().andParentIdIsNull()
                .andDeletedEqualTo(Boolean.FALSE);
        example.setOrderByClause("SORT");
        return systemMenuMapper.selectByExample(example);
    }

    @Override
    public List<SystemMenu> findByUserId(Integer userId) {
        return systemMenuMapper.selectByUserId(userId);
    }

    @Override
    public List<SystemMenu> findByRoleId(Integer roleId) {
        return systemMenuMapper.selectByRoleId(roleId);
    }

    @Override
    public PageData<SystemMenu> findPage(PageWrap pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        ExampleBuilder<SystemMenuExample, SystemMenuExample.Criteria> builder = ExampleBuilder.create(SystemMenuExample.class, SystemMenuExample.Criteria.class);
        ExampleBuilder.ExamplePack<SystemMenuExample, SystemMenuExample.Criteria> pack = builder.buildExamplePack(pageWrap.getModel());
        pack.getExample().setOrderByClause(pageWrap.getOrderByClause());
        return PageData.from(new PageInfo<>(systemMenuMapper.selectByExample(pack.getExample())));
    }

    @Override
    public long count(SystemMenu systemMenu) {
        ExampleBuilder<SystemMenuExample, SystemMenuExample.Criteria> builder = ExampleBuilder.create(SystemMenuExample.class, SystemMenuExample.Criteria.class);
        return systemMenuMapper.countByExample(builder.buildExamplePack(systemMenu).getExample());
    }
}