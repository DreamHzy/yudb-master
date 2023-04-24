package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.RegionalManager;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegionalManagerMapper extends MyBaseMapper<RegionalManager> {


    @Select(" SELECT " +
            " *  " +
            " FROM " +
            " regional_manager  " +
            " WHERE " +
            " MOBILE = #{phone}" +
            " AND STATUS = 1" +
            " ")
    RegionalManager queryByPhone(String phone);
}