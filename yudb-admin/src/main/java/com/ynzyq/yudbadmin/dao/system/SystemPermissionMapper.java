package com.ynzyq.yudbadmin.dao.system;

import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemPermissionDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemPermission;
import com.ynzyq.yudbadmin.dao.system.model.SystemPermissionExample;
import com.ynzyq.yudbadmin.dao.system.vo.SystemPermissionListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemPermissionMapper {

    /**
     * 根据用户ID查询
     * @author Caesar Liu
     * @date 2021-03-30 23:19
     */
    List<SystemPermission> selectByUserId(Integer userId);

    /**
     * 根据角色ID查询
     * @author Caesar Liu
     * @date 2021-03-31 20:43
     */
    List<SystemPermission> selectByRoleId(Integer roleId);

    /**
     * 查询列表
     * @author Caesar Liu
     * @date 2021-03-31 15:15
     */
    List<SystemPermissionListVO> selectList(QuerySystemPermissionDTO dto);

    int countByExample(SystemPermissionExample example);

    int deleteByExample(SystemPermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemPermission record);

    int insertSelective(SystemPermission record);

    List<SystemPermission> selectByExample(SystemPermissionExample example);

    SystemPermission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemPermission record, @Param("example") SystemPermissionExample example);

    int updateByExample(@Param("record") SystemPermission record, @Param("example") SystemPermissionExample example);

    int updateByPrimaryKeySelective(SystemPermission record);

    int updateByPrimaryKey(SystemPermission record);
}