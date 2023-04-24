package com.ynzyq.yudbadmin.dao.system;

import com.ynzyq.yudbadmin.dao.system.model.SystemRoleMenu;
import com.ynzyq.yudbadmin.dao.system.model.SystemRoleMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemRoleMenuMapper {
    int countByExample(SystemRoleMenuExample example);

    int deleteByExample(SystemRoleMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemRoleMenu record);

    int insertSelective(SystemRoleMenu record);

    List<SystemRoleMenu> selectByExample(SystemRoleMenuExample example);

    SystemRoleMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemRoleMenu record, @Param("example") SystemRoleMenuExample example);

    int updateByExample(@Param("record") SystemRoleMenu record, @Param("example") SystemRoleMenuExample example);

    int updateByPrimaryKeySelective(SystemRoleMenu record);

    int updateByPrimaryKey(SystemRoleMenu record);
}