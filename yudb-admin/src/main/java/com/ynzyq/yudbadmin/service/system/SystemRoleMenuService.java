package com.ynzyq.yudbadmin.service.system;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.model.SystemRoleMenu;

import java.util.List;

/**
 * 角色菜单关联Service定义
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
public interface SystemRoleMenuService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    Integer create(SystemRoleMenu systemRoleMenu);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @author Caesar Liu
     * @date 2021-03-30 15:25
     */
    void delete(SystemRoleMenu systemRoleMenu);

    /**
     * 批量主键删除
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void updateById(SystemRoleMenu systemRoleMenu);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void updateByIdInBatch(List<SystemRoleMenu> systemRoleMenus);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemRoleMenu findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemRoleMenu findOne(SystemRoleMenu systemRoleMenu);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    List<SystemRoleMenu> findList(SystemRoleMenu systemRoleMenu);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    PageData<SystemRoleMenu> findPage(PageWrap<SystemRoleMenu> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    long count(SystemRoleMenu systemRoleMenu);
}