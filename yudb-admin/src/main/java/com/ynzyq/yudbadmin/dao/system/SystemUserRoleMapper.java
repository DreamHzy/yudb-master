package com.ynzyq.yudbadmin.dao.system;

import com.ynzyq.yudbadmin.dao.system.model.SystemUserRole;
import com.ynzyq.yudbadmin.dao.system.model.SystemUserRoleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemUserRoleMapper {
    int countByExample(SystemUserRoleExample example);

    int deleteByExample(SystemUserRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemUserRole record);

    int insertSelective(SystemUserRole record);

    List<SystemUserRole> selectByExample(SystemUserRoleExample example);

    SystemUserRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemUserRole record, @Param("example") SystemUserRoleExample example);

    int updateByExample(@Param("record") SystemUserRole record, @Param("example") SystemUserRoleExample example);

    int updateByPrimaryKeySelective(SystemUserRole record);

    int updateByPrimaryKey(SystemUserRole record);
}