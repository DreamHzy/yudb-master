package com.ynzyq.yudbadmin.service.system;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.model.SystemUserRole;

import java.util.List;

/**
 * 用户角色关联Service定义
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
public interface SystemUserRoleService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    Integer create(SystemUserRole systemUserRole);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @author Caesar Liu
     * @date 2021-03-29 22:26
     */
    void delete(SystemUserRole systemUserRole);

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
    void updateById(SystemUserRole systemUserRole);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void updateByIdInBatch(List<SystemUserRole> systemUserRoles);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemUserRole findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemUserRole findOne(SystemUserRole systemUserRole);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    List<SystemUserRole> findList(SystemUserRole systemUserRole);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    PageData<SystemUserRole> findPage(PageWrap<SystemUserRole> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    long count(SystemUserRole systemUserRole);
}