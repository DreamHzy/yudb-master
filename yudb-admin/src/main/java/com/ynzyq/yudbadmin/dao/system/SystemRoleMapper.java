package com.ynzyq.yudbadmin.dao.system;

import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemRoleDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemRole;
import com.ynzyq.yudbadmin.dao.system.model.SystemRoleExample;
import com.ynzyq.yudbadmin.dao.system.vo.SystemRoleListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemRoleMapper {

    /**
     * 查询角色列表
     * @author Caesar Liu
     * @date 2021-03-30 11:56
     */
    List<SystemRoleListVO> selectList(QuerySystemRoleDTO dto);

    /**
     * 根据用户ID查询
     * @author Caesar Liu
     * @date 2021-03-31 21:04
     */
    List<SystemRole> selectByUserId(Integer userId);

    int countByExample(SystemRoleExample example);

    int deleteByExample(SystemRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemRole record);

    int insertSelective(SystemRole record);

    List<SystemRole> selectByExample(SystemRoleExample example);

    SystemRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemRole record, @Param("example") SystemRoleExample example);

    int updateByExample(@Param("record") SystemRole record, @Param("example") SystemRoleExample example);

    int updateByPrimaryKeySelective(SystemRole record);

    int updateByPrimaryKey(SystemRole record);
}