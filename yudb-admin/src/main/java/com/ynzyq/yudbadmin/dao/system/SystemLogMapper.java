package com.ynzyq.yudbadmin.dao.system;

import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemLogDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemLog;
import com.ynzyq.yudbadmin.dao.system.model.SystemLogExample;
import com.ynzyq.yudbadmin.dao.system.vo.SystemLogListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemLogMapper {
    int countByExample(SystemLogExample example);

    int deleteByExample(SystemLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SystemLog record);

    int insertSelective(SystemLog record);

    List<SystemLog> selectByExample(SystemLogExample example);

    SystemLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SystemLog record, @Param("example") SystemLogExample example);

    int updateByExample(@Param("record") SystemLog record, @Param("example") SystemLogExample example);

    int updateByPrimaryKeySelective(SystemLog record);

    int updateByPrimaryKey(SystemLog record);

    List<SystemLogListVO> selectList(QuerySystemLogDTO model);
}