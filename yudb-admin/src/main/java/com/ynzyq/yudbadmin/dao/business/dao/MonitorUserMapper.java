package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.MonitorUser;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.vo.MonitorUserPageVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MonitorUserMapper extends MyBaseMapper<MonitorUser> {

    @Select("SELECT*FROM monitor_user WHERE `STATUS` = #{status}")
    List<MonitorUser> queryByStatus(int status);

    @Select("SELECT " +
            " m.ID, " +
            " m.`NAME`, " +
            " m.PHONE, " +
            " m.`STATUS`, " +
            " m.CREATE_TIME, " +
            " su.REALNAME createUserName " +
            "FROM " +
            " monitor_user m LEFT JOIN system_user su ON m.CREATE_USER = su.ID "
    )
    List<MonitorUserPageVo> findPage();
}