package com.ynzyq.yudbadmin.dao.system;

import com.ynzyq.yudbadmin.dao.system.model.SystemMenu;
import com.ynzyq.yudbadmin.dao.system.model.SystemMenuExample;
import com.ynzyq.yudbadmin.dao.system.vo.SystemMenuListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemMenuMapper {

    /**
     * 查询列表
     * @author Caesar Liu
     * @date 2021-03-30 20:16
     */
    List<SystemMenuListVO> selectList();

    /**
     * 根据用户ID查询
     * @author Caesar Liu
     * @date 2021-03-30 16:22
     */
    List<SystemMenu> selectByUserId(Integer userId);

    /**
     * 根据角色ID查询
     * @author Caesar Liu
     * @date 2021-03-31 20:40
     */
    List<SystemMenu> selectByRoleId(Integer roleId);

    int countByExample(SystemMenuExample example);

    int deleteByExample(SystemMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemMenu record);

    int insertSelective(SystemMenu record);

    List<SystemMenu> selectByExample(SystemMenuExample example);

    SystemMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemMenu record, @Param("example") SystemMenuExample example);

    int updateByExample(@Param("record") SystemMenu record, @Param("example") SystemMenuExample example);

    int updateByPrimaryKeySelective(SystemMenu record);

    int updateByPrimaryKey(SystemMenu record);
}