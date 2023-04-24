package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.dto.StroeIdDto;
import com.yunzyq.yudbapp.dao.dto.StroePage2Dto;
import com.yunzyq.yudbapp.dao.dto.StroePageDto;
import com.yunzyq.yudbapp.dao.model.MerchantStore;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantStoreMapper extends MyBaseMapper<MerchantStore> {


    @Select("<script>" +
            " SELECT   " +
            "   ms.ID,   " +
            "   ms.UID,   " +
            "   m.IS_AGENT,   " +
            "   m.`NAME` 'merchantName',   " +
            "   ms.EXPIRE_TIME ,   " +
            "   ms.ADDRESS ,   " +
            "   ms.CONTRACT_STATUS ,   " +
            "   ms.MANAGEMENT_EXPENSE ,   " +
            "   ms.ALREADY_MANAGEMENT_EXPENSE ,   " +
            "   m.MOBILE ,   " +
            "   ms.STATUS ,   " +
            "   CONCAT( ms.PROVINCE, ms.CITY )  area   " +
            " FROM   " +
            "   merchant_store ms   " +
            "   LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
            "   LEFT JOIN merchant_store_regional_manager msrm ON ms.ID = msrm.MERCHANT_STORE_ID    " +
            "   WHERE msrm.`STATUS`=1   " +
            "   AND msrm.REGIONAL_MANAGER_ID = #{id}" +
            " <if test='condition !=null'>" +
            " AND ( " +
            "   m.`NAME`  like concat('%',#{condition},'%') " +
            "  OR  ms.UID like concat('%',#{condition},'%') )  " +
            " </if>" +
            " ORDER BY EXPIRE_TIME" +
            " </script>")
    List<StroePageVo> findPage(StroePageDto stroePageDto);

    @Select("<script>" +
            " SELECT   " +
            "   ms.ID,   " +
            "   ms.UID,   " +
            "   m.IS_AGENT,   " +
            "   m.`NAME` 'merchantName',   " +
            "   ms.EXPIRE_TIME ,   " +
            "   ms.ADDRESS ,   " +
            "   ms.CONTRACT_STATUS ,   " +
            "   ms.MANAGEMENT_EXPENSE ,   " +
            "   ms.ALREADY_MANAGEMENT_EXPENSE ,   " +
            "   ms.STATUS ,   " +
            "   m.MOBILE ,   " +
            "   CONCAT( ms.PROVINCE, ms.CITY )  area   " +
            " FROM   " +
            "   merchant_store ms   " +
            "   LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
            "   LEFT JOIN merchant_store_regional_manager msrm ON ms.ID = msrm.MERCHANT_STORE_ID    " +
            "   WHERE msrm.`STATUS`=1   " +
            "   AND msrm.REGIONAL_MANAGER_ID = #{id}" +
            " <if test='condition !=null'>" +
            " AND ( " +
            "   m.`NAME`  like concat('%',#{condition},'%') " +
            "  OR  ms.UID like concat('%',#{condition},'%') )  " +
            " </if>" +
            "<if test='status != null'> AND ms.STATUS = #{status} </if>" +
            " ORDER BY EXPIRE_TIME" +
            " </script>")
    List<StorePage2> findPage2(StroePage2Dto stroePage2Dto);

    @Select("SELECT ID,ADDRESS FROM merchant_store WHERE MERCHANT_ID=#{vlaue} ")
    List<SoreListVo> queryListByMerchantId(String vlaue);


    @Select("SELECT SIGN_TIME FROM merchant_store WHERE MERCHANT_ID=#{vlaue} ORDER BY SIGN_TIME LIMIT 1")
    String queryTime(String vlaue);
    
    @Select("<script>" +
            "SELECT " +
            " t1.`STATUS`, " +
            " IFNULL( t2.count, 0 ) count  " +
            "FROM " +
            " ( SELECT int_val AS `STATUS` FROM system_dict WHERE `code` = 'status' AND `status` = 1 ) t1 " +
            " LEFT JOIN ( " +
            " SELECT " +
            "  ms.`STATUS`, " +
            "  COUNT(*) count  " +
            " FROM " +
            "  merchant_store ms " +
            "  LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID " +
            "  LEFT JOIN merchant_store_regional_manager msrm ON ms.ID = msrm.MERCHANT_STORE_ID  " +
            " WHERE " +
            "  msrm.`STATUS` = 1  " +
            "  AND msrm.REGIONAL_MANAGER_ID = #{id}  " +
            "  AND ms.`STATUS` IN ( 2, 3, 9, 10, 11 )  " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "   m.`NAME`  like concat('%',#{condition},'%') " +
            "  OR  ms.UID like concat('%',#{condition},'%') )  " +
            " </if>" +
            " GROUP BY " +
            " ms.`STATUS`  " +
            " ) t2 ON t1.`STATUS` = t2.`STATUS`" +
            "</script>")
    List<StorePage2Statistics> storePage2Statistics(StroePage2Dto stroePage2Dto);
}