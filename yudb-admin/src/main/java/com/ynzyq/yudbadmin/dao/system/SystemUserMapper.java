package com.ynzyq.yudbadmin.dao.system;

import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemUserDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.dao.system.model.SystemUserExample;
import com.ynzyq.yudbadmin.dao.system.vo.SystemUserListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemUserMapper {

    /**
     * 查询用户列表
     * @author Caesar Liu
     * @date 2021-03-29 22:52
     */
    List<SystemUserListVO> selectList(QuerySystemUserDTO dto);

    int countByExample(SystemUserExample example);

    int deleteByExample(SystemUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemUser record);

    int insertSelective(SystemUser record);

    List<SystemUser> selectByExample(SystemUserExample example);

    SystemUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemUser record, @Param("example") SystemUserExample example);

    int updateByExample(@Param("record") SystemUser record, @Param("example") SystemUserExample example);

    int updateByPrimaryKeySelective(SystemUser record);

    int updateByPrimaryKey(SystemUser record);
}