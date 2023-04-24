package com.ynzyq.yudbadmin.service.system;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemUserDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.dao.system.vo.SystemUserListVO;

import java.util.List;

/**
 * 系统用户Service定义
 * @author Caesar Liu
 * @date 2021/03/28 09:30
 */
public interface SystemUserService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    Integer create(SystemUser systemUser);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    void updateById(SystemUser systemUser);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    void updateByIdInBatch(List<SystemUser> systemUsers);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    SystemUser findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    SystemUser findOne(SystemUser systemUser);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    List<SystemUser> findList(SystemUser systemUser);
  
    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    PageData<SystemUserListVO> findPage(PageWrap<QuerySystemUserDTO> pageWrap);

    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    long count(SystemUser systemUser);
}