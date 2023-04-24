package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.ListComplaintDTO;
import com.ynzyq.yudbadmin.dao.business.model.MerchantComplaint;

import com.ynzyq.yudbadmin.dao.business.vo.ListComplaintVO;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantComplaintMapper extends MyBaseMapper<MerchantComplaint> {
    @Select("<script>" +
            "SELECT " +
            " t1.ID, " +
            " t1.CREATE_TIME, " +
            " t3.`NAME` AS MERCHANT_NAME, " +
            " t2.PROVINCE, " +
            " t2.CITY, " +
            " t2.AREA,( " +
            " SELECT " +
            "  REGION  " +
            " FROM " +
            "  merchant_store_mapping_area  " +
            " WHERE " +
            "  PROVINCE = t2.PROVINCE  " +
            "  AND CITY = t2.CITY  " +
            "  AND AREA = t2.AREA  " +
            "  LIMIT 1  " +
            " ) REGION, " +
            " t3.MOBILE, " +
            " t1.CONTENT, " +
            " t1.STORE_UID, " +
            " t1.`STATUS`, " +
            " t1.RESULT  " +
            "FROM " +
            " merchant_complaint t1 " +
            " INNER JOIN merchant_store t2 ON t1.STORE_UID = t2.UID  " +
            " INNER JOIN merchant t3 ON t1.MERCHANT_ID = t3.ID  " +
            "<where>" +
            "<if test='startTime != null and endTime != null'> AND t1.CREATE_TIME BETWEEN #{startTime} AND #{endTime}</if>" +
            "<if test='merchantName != null'> AND t3.`NAME` LIKE concat('%',#{merchantName},'%')</if>" +
            "<if test='mobile != null'> AND t3.MOBILE LIKE concat('%',#{mobile},'%')</if>" +
            "<if test='status != null'> AND t1.`STATUS` = #{status}</if>" +
            "<if test='province != null'> AND t2.PROVINCE LIKE concat('%',#{province},'%')</if>" +
            "<if test='city != null'> AND t2.CITY LIKE concat('%',#{city},'%')</if>" +
            "<if test='area != null'> AND t2.AREA LIKE concat('%',#{area},'%')</if>" +
            "<if test='region != null'> AND ( SELECT REGION FROM merchant_store_mapping_area WHERE PROVINCE = t2.PROVINCE AND CITY = t2.CITY AND AREA = t2.AREA LIMIT 1 ) LIKE concat('%',#{region},'%')</if>" +
            "</where>" +
            "ORDER BY " +
            " t1.ID DESC" +
            "</script>")
    List<ListComplaintVO> listComplaint(ListComplaintDTO listComplaintDTO);
}