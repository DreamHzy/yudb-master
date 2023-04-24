package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;
import com.ynzyq.yudbadmin.dao.business.model.RegionalManagerDepartment;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RegionalManagerDepartmentMapper extends MyBaseMapper<RegionalManagerDepartment> {
    @Update("UPDATE regional_manager_department    " +
            "SET `STATUS` = 2    " +
            "WHERE   " +
            "   REGIONAL_MANAGER_ID = #{id}")
    void updateStatusByRegionalManagerId(Integer id);

    @Select("SELECT " +
            " rm.* " +
            "FROM " +
            " regional_manager rm " +
            " LEFT JOIN merchant_store_regional_manager rmd ON rm.ID = rmd.REGIONAL_MANAGER_ID  " +
            "WHERE " +
            " rmd.`STATUS` = 1  " +
            " AND rmd.MERCHANT_STORE_ID=#{id}")
    RegionalManager queryStoreId(Integer id);
}