package com.ynzyq.yudbadmin.service.system;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.model.SystemRolePermission;

import java.util.List;

/**
 * 角色权限关联Service定义
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
public interface SystemRolePermissionService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    Integer create(SystemRolePermission systemRolePermission);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void deleteById(Integer id);

    /**
     * 删除
     * @author Caesar Liu
     * @date 2021-03-30 10:53
     */
    void delete(SystemRolePermission systemRolePermission);

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
    void updateById(SystemRolePermission systemRolePermission);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void updateByIdInBatch(List<SystemRolePermission> systemRolePermissions);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemRolePermission findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemRolePermission findOne(SystemRolePermission systemRolePermission);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    List<SystemRolePermission> findList(SystemRolePermission systemRolePermission);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    PageData<SystemRolePermission> findPage(PageWrap<SystemRolePermission> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    long count(SystemRolePermission systemRolePermission);
}