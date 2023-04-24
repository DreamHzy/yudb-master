package com.ynzyq.yudbadmin.service.system;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemRoleDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemRole;
import com.ynzyq.yudbadmin.dao.system.vo.SystemRoleListVO;

import java.util.List;

/**
 * 系统角色Service定义
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
public interface SystemRoleService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    Integer create(SystemRole systemRole);

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
    void updateById(SystemRole systemRole);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    void updateByIdInBatch(List<SystemRole> systemRoles);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemRole findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    SystemRole findOne(SystemRole systemRole);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    List<SystemRole> findList(SystemRole systemRole);

    /**
     * 根据用户ID查询
     * @author Caesar Liu
     * @date 2021-03-31 21:01
     */
    List<SystemRole> findByUserId(Integer userId);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    PageData<SystemRoleListVO> findPage(PageWrap<QuerySystemRoleDTO> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/03/27 22:34
     */
    long count(SystemRole systemRole);
}