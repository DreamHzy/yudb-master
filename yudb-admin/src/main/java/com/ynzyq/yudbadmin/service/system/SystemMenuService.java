package com.ynzyq.yudbadmin.service.system;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.model.SystemMenu;
import com.ynzyq.yudbadmin.dao.system.vo.SystemMenuListVO;

import java.util.List;

/**
 * 系统菜单Service定义
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
public interface SystemMenuService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    Integer create(SystemMenu systemMenu);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void deleteById(Integer id);

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
    void updateById(SystemMenu systemMenu);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void updateByIdInBatch(List<SystemMenu> systemMenus);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemMenu findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemMenu findOne(SystemMenu systemMenu);

    /**
     * 查询列表
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    List<SystemMenu> findList(SystemMenu systemMenu);

    /**
     * 查询列表
     * @author Caesar Liu
     * @date 2021-03-30 20:08
     */
    List<SystemMenuListVO> findList();

    /**
     * 查询一级菜单列表
     * @author Caesar Liu
     * @date 2021-03-30 21:51
     */
    List<SystemMenu> findRootList();

    /**
     * 查询用户ID查询
     * @author Caesar Liu
     * @date 2021-03-30 16:13
     */
    List<SystemMenu> findByUserId(Integer userId);

    /**
     * 根据角色ID查询
     * @author Caesar Liu
     * @date 2021-03-31 20:34
     */
    List<SystemMenu> findByRoleId(Integer roleId);

    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    PageData<SystemMenu> findPage(PageWrap<SystemMenu> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    long count(SystemMenu systemMenu);
}