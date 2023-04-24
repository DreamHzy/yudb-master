package com.ynzyq.yudbadmin.dao.system;

import com.ynzyq.yudbadmin.dao.system.model.SystemRolePermission;
import com.ynzyq.yudbadmin.dao.system.model.SystemRolePermissionExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemRolePermissionMapper {
    int countByExample(SystemRolePermissionExample example);

    int deleteByExample(SystemRolePermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemRolePermission record);

    int insertSelective(SystemRolePermission record);

    List<SystemRolePermission> selectByExample(SystemRolePermissionExample example);

    SystemRolePermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemRolePermission record, @Param("example") SystemRolePermissionExample example);

    int updateByExample(@Param("record") SystemRolePermission record, @Param("example") SystemRolePermissionExample example);

    int updateByPrimaryKeySelective(SystemRolePermission record);

    int updateByPrimaryKey(SystemRolePermission record);
}