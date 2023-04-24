package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.AgencyWork;
import com.yunzyq.yudbapp.dao.vo.IndexOrderVo;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AgencyWorkMapper extends MyBaseMapper<AgencyWork> {

    @Select("SELECT  " +
            "  COUNT( 1 )   " +
            "FROM  " +
            "  agency_work   " +
            "WHERE  " +
            "  REGIONAL_MANAGER_ID=#{vlaue}" +
            "  and STATUS = 1")
    Integer queryByRegionalManagerId(String vlaue);


    @Select("SELECT  " +
            "  pom.ID,  " +
            "  pt.`NAME`,  " +
            "  pom.MONEY   " +
            "FROM  " +
            "  agency_work aw  " +
            "  LEFT JOIN payment_order_master pom ON aw.WORK_ID = pom.ID  " +
            "  LEFT JOIN payment_type pt ON pt.ID = pom.PAYMENT_TYPE_ID   " +
            "WHERE  " +
            "  aw.`STATUS` = 1   " +
            "  AND aw.REGIONAL_MANAGER_ID = #{vlaue}   " +
            "ORDER BY  " +
            "  aw.CREATE_TIME DESC   " +
            "  LIMIT 1")
    IndexOrderVo queryByRegionalManagerIdInfo(String vlaue);


    @Select("SELECT  " +
            "  pom.ID,  " +
            "  pt.`NAME`,  " +
            "  pom.MONEY   " +
            "FROM  " +
            "  agency_work aw  " +
            "  LEFT JOIN payment_order_master pom ON aw.WORK_ID = pom.ID  " +
            "  LEFT JOIN payment_type pt ON pt.ID = pom.PAYMENT_TYPE_ID   " +
            "WHERE  " +
            "  aw.`STATUS` = 1   " +
            "  AND aw.MERCHANT_ID = #{vlaue}   " +
            "ORDER BY  " +
            "  aw.CREATE_TIME DESC   " +
            "  LIMIT 1")
    IndexOrderVo queryByMerchantIdInfo(String vlaue);

    @Select("SELECT  " +
            "  COUNT( 1 )   " +
            "FROM  " +
            "  agency_work   " +
            "WHERE  " +
            "  MERCHANT_ID=#{vlaue}" +
            "  and STATUS = 1")
    Integer queryByMerchantId(String vlaue);
}