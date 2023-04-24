package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.api.excel.dto.MerchantMoneyDto;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStore;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MerchantStoreMapper extends MyBaseMapper<MerchantStore> {

    @Update("<script>" +
            "UPDATE merchant_store " +
            "SET " +
            "<if test='type == 1'>OPEN_TIME = NULL </if>" +
            "<if test='type == 2'>CLOSE_TIME = NULL </if>" +
            "<if test='type == 3'>RELOCATION_TIME = NULL </if>" +
            "<if test='type == 4'>PAUSE_TIME = NULL </if>" +
            "<if test='type == 5'>SERVICE_EXPIRE_TIME = NULL </if>" +
            "<if test='type == 6'>EXPIRE_TIME = NULL </if>" +
            "<if test='type == 7'>OPEN_AGAIN_TIME = NULL </if>" +
            "<if test='type == 8'>ESTIMATE_TIME = NULL </if>" +
            "WHERE " +
            " ID = #{id}" +
            "</script>")
    int updateTime(Integer type, Integer id);

    @Update("<script>" +
            "UPDATE merchant_store " +
            "SET " +
            "<if test='type == 1'>SEAT_COUNT = NULL </if>" +
            "<if test='type == 2'>HLL_CODE = NULL </if>" +
            "<if test='type == 3'>MT_ID = NULL </if>" +
            "<if test='type == 4'>ELM_ID = NULL </if>" +
            "<if test='type == 5'>DZDP_ID = NULL </if>" +
            "<if test='type == 6'>REMARK = NULL </if>" +
            "WHERE " +
            " ID = #{id}" +
            "</script>")
    int updateField(Integer type, Integer id);

    @Select("" +
            "<script>" +
            " SELECT " +
            " m.ID, " +
            " m.UID, " +
            " m.TYPE, " +
            " m.PROVINCE, " +
            " m.CITY, " +
            " m.AREA, " +
            " m.MERCHANT_ID, " +
            " m.STATUS_TWO, " +
            " m.FRANCHISE_FEE, " +
            " m.BOND_MONEY, " +
            " m.MANAGEMENT_EXPENSE, " +
            " m.OPEN_TIME, " +
            " m.OPEN_AGAIN_TIME, " +
            " m.CLOSE_TIME, " +
            " m.RELOCATION_TIME, " +
            " m.PAUSE_TIME, " +
            " m.ESTIMATE_TIME, " +
            " m.SEAT_COUNT, " +
            " m.HLL_CODE, " +
            " m.MT_ID, " +
            " m.ELM_ID, " +
            " m.DZDP_ID, " +
            " m.REMARK, " +
            " m.COLLECTION_MONTH, " +
            " m.DELAYED_OPEN, " +
            " m.AGENT_ID, " +
            " ( SELECT REALNAME FROM system_user WHERE ID = m.CREATE_USER ) CREATE_USER, " +
            " m.CREATE_TIME, " +
            " ( SELECT `NAME` FROM merchant WHERE ID = m.AGENT_ID LIMIT 1 ) AGENT_NAME, " +
            " m.IS_PREFERENTIAL, " +
            " t.`NAME` merchantName, " +
            " t.MOBILE merchantMobile, " +
            " m.ADDRESS, " +
            " m.CONTRACT_STATUS, " +
            " t2.regionalManagerId, " +
            " t2.regionalManagerName, " +
            " t2.departmentName, " +
            " m.SIGN_TIME, " +
            " m.EXPIRE_TIME, " +
            " m.SERVICE_EXPIRE_TIME, " +
            " m.DELIVERY_ADDRESS, " +
            " m.DISTRIBUTION_LIMIT, " +
            " m.RECEIVER, " +
            " m.RECEIVER_MOBILE, " +
            " m.SHOW_DELIVERY_ADDRESS, " +
            " m.DELIVERY_PROVINCE, " +
            " m.DELIVERY_CITY, " +
            " m.DELIVERY_AREA, " +
            " m.`STATUS` statusNum, " +
            " m4.UID AS belongAgency, " +
            " m.`STATUS`,  " +
            " m.IS_APPLY  " +
            "FROM " +
            " merchant_store m " +
            " INNER JOIN merchant t ON m.MERCHANT_ID = t.ID " +
            " LEFT JOIN ( " +
            " SELECT " +
            "  msrm.ID, " +
            "  rm.ID regionalManagerId, " +
            "  rm.`NAME` regionalManagerName, " +
            "  d.`NAME` departmentName, " +
            "  msrm.MERCHANT_STORE_ID  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID " +
            "  LEFT JOIN regional_manager_department rmd ON rmd.REGIONAL_MANAGER_ID = rm.ID " +
            "  LEFT JOIN department d ON d.ID = rmd.DEPARTMENT_ID  " +
            " WHERE " +
            "  msrm.`STATUS` = 1  " +
            " AND rmd.`STATUS` = 1  " +
            " ) t2 ON m.ID = t2.MERCHANT_STORE_ID " +
            " LEFT JOIN merchant_agent_area m4 ON m.AGENT_AREA_ID=m4.ID " +
            "<where>" +
            " <if test='condition !=null'>AND ( m.UID = #{condition} " +
            "OR t.`NAME` like concat('%',#{condition},'%') " +
            "OR t2.regionalManagerName LIKE concat('%',#{condition},'%') " +
            ") </if>" +
            " <if test='startSignTime !=null'>AND m.SIGN_TIME BETWEEN #{startSignTime} AND #{endSignTime} </if>" +
            " <if test='startExpireTime !=null'>AND m.EXPIRE_TIME BETWEEN #{startExpireTime} AND #{endExpireTime}  </if>" +
            " <if test='merchantId !=null'>AND m.MERCHANT_ID = #{merchantId}  </if>" +
            " <if test='month !=null'>AND m.COLLECTION_MONTH = #{month}  </if>" +
            " <if test='status !=null'>AND m.`STATUS` = #{status}  </if>" +
            " <if test='delayedOpen !=null'>AND m.DELAYED_OPEN = #{delayedOpen}  </if>" +
            " <if test='province !=null'>AND m.PROVINCE LIKE concat('%',#{province},'%') </if>" +
            " <if test='city !=null'>AND m.CITY LIKE concat('%',#{city},'%')  </if>" +
            " <if test='area !=null'>AND m.AREA LIKE concat('%',#{area},'%')  </if>" +
            " <if test='region !=null'>AND (SELECT REGION FROM merchant_store_mapping_area WHERE PROVINCE = m.PROVINCE AND CITY = m.CITY AND AREA = m.AREA LIMIT 1) = #{region}  </if>" +
            " <if test='startServiceExpireTime !=null and endServiceExpireTime != null'>AND m.SERVICE_EXPIRE_TIME BETWEEN #{startServiceExpireTime}  AND #{endServiceExpireTime}  </if>" +
            "</where>" +
            " ORDER BY m.CREATE_TIME DESC  " +
            " </script>")
    List<StroePageVo> findPage(StroePageDto stroePageDto);

    @Select("" +
            "<script>" +
            " SELECT " +
            " m.ID, " +
            " m.UID, " +
            " m.TYPE, " +
            " m.PROVINCE, " +
            " m.CITY, " +
            " m.AREA, " +
            " m.MERCHANT_ID, " +
            " m.STATUS_TWO, " +
            " m.FRANCHISE_FEE, " +
            " m.BOND_MONEY, " +
            " m.MANAGEMENT_EXPENSE, " +
            " m.OPEN_TIME, " +
            " m.OPEN_AGAIN_TIME, " +
            " m.CLOSE_TIME, " +
            " m.RELOCATION_TIME, " +
            " m.PAUSE_TIME, " +
            " m.ESTIMATE_TIME, " +
            " m.SEAT_COUNT, " +
            " m.HLL_CODE, " +
            " m.MT_ID, " +
            " m.ELM_ID, " +
            " m.DZDP_ID, " +
            " m.REMARK, " +
            " m.COLLECTION_MONTH, " +
            " m.DELAYED_OPEN, " +
            " ( SELECT REALNAME FROM system_user WHERE ID = m.CREATE_USER ) CREATE_USER, " +
            " m.CREATE_TIME, " +
//            " (SELECT ID FROM merchant WHERE `NAME` = m.AGENT_NAME LIMIT 1) AGENT_ID, " +
//            " m.AGENT_NAME, " +
            " m.AGENT_ID, " +
            " ( SELECT `NAME` FROM merchant WHERE ID = m.AGENT_ID LIMIT 1 ) AGENT_NAME, " +
            " m.IS_PREFERENTIAL, " +
            " t.`NAME` merchantName, " +
            " t.MOBILE merchantMobile, " +
            " m.ADDRESS, " +
            " m.CONTRACT_STATUS, " +
            " t2.regionalManagerId, " +
            " t2.regionalManagerName, " +
            " t2.departmentName, " +
            " m.SIGN_TIME, " +
            " m.EXPIRE_TIME, " +
            " m.SERVICE_EXPIRE_TIME, " +
            " m.DELIVERY_ADDRESS, " +
            " m.DISTRIBUTION_LIMIT, " +
            " m.RECEIVER, " +
            " m.RECEIVER_MOBILE, " +
            " m.SHOW_DELIVERY_ADDRESS, " +
            " m.DELIVERY_PROVINCE, " +
            " m.DELIVERY_CITY, " +
            " m.DELIVERY_AREA, " +
            " m.`STATUS` statusNum, " +
            " m.`STATUS`,  " +
            " m3.UID AS belongAgency, " +
            " m.IS_APPLY " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  msrm.ID, " +
            "  rm.ID regionalManagerId, " +
            "  rm.`NAME` regionalManagerName, " +
            "  d.`NAME` departmentName, " +
            "  msrm.MERCHANT_STORE_ID  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID " +
            "  LEFT JOIN regional_manager_department rmd ON rmd.REGIONAL_MANAGER_ID = rm.ID " +
            "  LEFT JOIN department d ON d.ID = rmd.DEPARTMENT_ID  " +
            " WHERE " +
            "  msrm.`STATUS` = 1  " +
            " AND rmd.`STATUS` = 1  " +
//            " <if test='condition !=null'>AND rm.`NAME` like concat('%',#{condition},'%') </if>" +
            " <if test='areaId !=null'>AND msrm.REGIONAL_MANAGER_ID = #{areaId} </if>" +
            " ) t2 " +
            " INNER JOIN merchant_store m ON m.ID = t2.MERCHANT_STORE_ID  " +
            " INNER JOIN merchant t ON m.MERCHANT_ID = t.ID " +
            " INNER JOIN merchant_agent_area m3 ON m.AGENT_AREA_ID = m3.ID " +
            "<where>" +
            " <if test='condition !=null'>AND ( m.UID = #{condition}  " +
            "OR t.`NAME` like concat('%',#{condition},'%') " +
            "OR t2.regionalManagerName LIKE concat('%',#{condition},'%') " +
            ") </if>" +
            " <if test='startSignTime !=null'>AND m.SIGN_TIME BETWEEN #{startSignTime} AND #{endSignTime} </if>" +
            " <if test='startExpireTime !=null'>AND m.EXPIRE_TIME BETWEEN #{startExpireTime} AND #{endExpireTime}  </if>" +
            " <if test='merchantId !=null'>AND m.MERCHANT_ID = #{merchantId}  </if>" +
            " <if test='month !=null'>AND m.COLLECTION_MONTH = #{month}  </if>" +
            " <if test='status !=null'>AND m.`STATUS` = #{status}  </if>" +
            " <if test='delayedOpen !=null'>AND m.DELAYED_OPEN = #{delayedOpen}  </if>" +
            " <if test='province !=null'>AND m.PROVINCE LIKE concat('%',#{province},'%') </if>" +
            " <if test='city !=null'>AND m.CITY LIKE concat('%',#{city},'%')  </if>" +
            " <if test='area !=null'>AND m.AREA LIKE concat('%',#{area},'%')  </if>" +
            " <if test='region !=null'>AND (SELECT REGION FROM merchant_store_mapping_area WHERE PROVINCE = m.PROVINCE AND CITY = m.CITY AND AREA = m.AREA LIMIT 1) = #{region}  </if>" +
            " <if test='startServiceExpireTime !=null and endServiceExpireTime != null'>AND m.SERVICE_EXPIRE_TIME BETWEEN #{startServiceExpireTime}  AND #{endServiceExpireTime}  </if>" +
            "</where>" +
            " ORDER BY m.CREATE_TIME DESC  " +
            " </script>")
    List<StroePageVo> findPageByAreaId(StroePageDto stroePageDto);


    @Select("SELECT " +
            "  m.ID, " +
            "  m.UID, " +
            "  t.`NAME` merchantName, " +
            "  t.MOBILE 'merchantMobile', " +
            "  m.PROVINCE, " +
            "  M.CITY, " +
            "  M.AREA, " +
            "  m.MOBILE, " +
            "  m.ADDRESS, " +
            "  m.`STATUS`, " +
            "  rm.`NAME` regionalManagerName, " +
            "  d.`NAME` departmentName, " +
            "  m.SIGNATORY, " +
            "  m.MOBILE, " +
            "  m.SIGN_TIME, " +
            "  m.ID_NUMBER, " +
            "  m.EXPIRE_TIME, " +
            "  IF(m.MERCHANT_LINK=1,'是','否') 'merchantLink', " +
            "  m.MERCHANT_MONEY, " +
            "  m.MANAGEMENT_EXPENSE ," +
            "  m.CLOUD_SCHOOL_MONEY " +
            "FROM " +
            "  merchant_store m " +
            "  LEFT JOIN merchant_store_regional_manager msrm ON m.ID = msrm.MERCHANT_STORE_ID " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID " +
            "  LEFT JOIN regional_manager_department rmd ON rmd.REGIONAL_MANAGER_ID = rm.ID " +
            "  LEFT JOIN department d ON d.ID = rmd.DEPARTMENT_ID " +
            "  LEFT JOIN merchant t ON m.MERCHANT_ID = t.ID  " +
            "WHERE " +
            "  msrm.`STATUS` = 1  " +
            "  AND rmd.`STATUS` = 1  " +
            "  AND m.ID = #{id}")
    MerchantStoreDetailVo queryDetail(String id);

    @Select("SELECT " +
            " m.ID, " +
            " m.UID, " +
            " m.PROVINCE, " +
            " m.CITY, " +
            " m.AREA, " +
            " m.SIGNATORY, " +
            " m.MERCHANT_ID, " +
            " m.STATUS_TWO, " +
            " m.FRANCHISE_FEE, " +
            " m.BOND_MONEY, " +
            " m.MANAGEMENT_EXPENSE, " +
            " m.OPEN_TIME, " +
            " m.CLOSE_TIME, " +
            " m.RELOCATION_TIME, " +
            " m.PAUSE_TIME, " +
            " m.SEAT_COUNT, " +
            " m.HLL_CODE, " +
            " m.MT_ID, " +
            " m.ELM_ID, " +
            " m.DZDP_ID, " +
            " m.REMARK, " +
            " m.AGENT_ID, " +
            " ( SELECT `NAME` FROM merchant WHERE ID = m.AGENT_ID LIMIT 1 ) AGENT_NAME, " +
            " m.IS_PREFERENTIAL, " +
            " t.`NAME` merchantName, " +
            " t.MOBILE merchantMobile, " +
            " m.ADDRESS, " +
            " m.CONTRACT_STATUS, " +
            " t2.regionalManagerId, " +
            " t2.regionalManagerName, " +
            " t2.departmentName, " +
            " m.SIGN_TIME, " +
            " m.EXPIRE_TIME, " +
            " m.SERVICE_EXPIRE_TIME, " +
            " m.`STATUS` statusNum, " +
            " m.`STATUS`  " +
            "FROM " +
            " merchant_store m " +
            " INNER JOIN merchant t ON m.MERCHANT_ID = t.ID " +
            " LEFT JOIN ( " +
            " SELECT " +
            "  msrm.ID, " +
            "  rm.ID regionalManagerId, " +
            "  rm.`NAME` regionalManagerName, " +
            "  d.`NAME` departmentName, " +
            "  msrm.MERCHANT_STORE_ID  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID " +
            "  LEFT JOIN regional_manager_department rmd ON rmd.REGIONAL_MANAGER_ID = rm.ID " +
            "  LEFT JOIN department d ON d.ID = rmd.DEPARTMENT_ID  " +
            " WHERE " +
            "  msrm.`STATUS` = 1  " +
            "  AND rmd.`STATUS` = 1  " +
            " ) t2 ON m.ID = t2.MERCHANT_STORE_ID  " +
            "WHERE " +
            " m.ID = #{id}")
    MerchantStoreDetailVo singleMerchantStore(Integer id);

    @Select("SELECT " +
            " m.ID, " +
            " m.UID, " +
            " m.TYPE, " +
            " m.PROVINCE, " +
            " M.CITY, " +
            " M.AREA, " +
            " m.MOBILE, " +
            " m.ADDRESS, " +
            " m.`STATUS`, " +
            " m.`STATUS_TWO`, " +
            " m.SIGNATORY, " +
            " m.MOBILE, " +
            " m.SIGN_TIME, " +
            " m.ID_NUMBER, " +
            " m.EXPIRE_TIME, " +
            " m.SERVICE_EXPIRE_TIME, " +
            "  m.OPEN_TIME, " +
            "  m.CLOSE_TIME, " +
            "  m.RELOCATION_TIME, " +
            "  m.PAUSE_TIME, " +
            " m.SEAT_COUNT, " +
            " m.HLL_CODE, " +
            " m.MT_ID, " +
            " m.ELM_ID, " +
            " m.DZDP_ID, " +
            " m.REMARK, " +
            "IF " +
            " ( m.MERCHANT_LINK = 1, '是', '否' ) merchantLink, " +
            " m.MERCHANT_MONEY, " +
            " m.MANAGEMENT_EXPENSE, " +
            " m.NEED_MANAGEMENT_EXPENSE alreadyManagementExpense, " +
            " m.FRANCHISE_FEE, " +
            " m.BOND_MONEY, " +
            " m.CLOUD_SCHOOL_MONEY  " +
            "FROM " +
            " merchant_store m  " +
            "WHERE " +
            " m.ID = #{id}")
    MerchantStoreDetailVo queryDetail2(String id);

    @Select("SELECT*FROM merchant_store WHERE UID =#{uid}")
    MerchantStore queryByUid(String uid);


    @Select("SELECT " +
            "  m.ID, " +
            "  m.UID, " +
            "  m.TYPE, " +
            "  t.`NAME` merchantName, " +
            "  t.ID merchantId, " +
            "  m.PROVINCE, " +
            "  M.CITY, " +
            "  M.AREA, " +
            "  m.MOBILE, " +
            "  m.ADDRESS, " +
            "  m.`STATUS`, " +
            "  m.STATUS_TWO, " +
            " m.SEAT_COUNT, " +
            " m.HLL_CODE, " +
            " m.MT_ID, " +
            " m.ELM_ID, " +
            " m.DZDP_ID, " +
            " m.REMARK, " +
            "  (SELECT rm.`NAME` FROM merchant_store_regional_manager msrm INNER JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID WHERE msrm.MERCHANT_STORE_ID=m.ID AND msrm.`STATUS` = 1 LIMIT 1) regionalManagerName, " +
            "  (SELECT rm.ID FROM merchant_store_regional_manager msrm INNER JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID WHERE msrm.MERCHANT_STORE_ID=m.ID AND msrm.`STATUS` = 1 LIMIT 1) regionalManagerId, " +
            "  m.SIGNATORY, " +
            "  m.ID_NUMBER, " +
            "  m.MOBILE, " +
            "  m.SIGN_TIME, " +
            "  m.EXPIRE_TIME, " +
            "  m.SERVICE_EXPIRE_TIME, " +
            "  m.OPEN_TIME, " +
            "  m.CLOSE_TIME, " +
            "  m.RELOCATION_TIME, " +
            "  m.PAUSE_TIME, " +
            "  IF(m.MERCHANT_LINK=1,'开通','未开通') 'merchantLink', " +
            "  m.MERCHANT_LINK_TIME , " +
            "  m.NEED_MANAGEMENT_EXPENSE alreadyManagementExpense, " +
            "  m.FRANCHISE_FEE, " +
            "  m.BOND_MONEY, " +
            "  m.MANAGEMENT_EXPENSE " +
            "FROM " +
            "  merchant_store m " +
            "  LEFT JOIN merchant t ON m.MERCHANT_ID = t.ID  " +
            "WHERE " +
            "   m.ID = #{ id}")
    EditMerchantStoreQueryVo editMerchantStoreQuery(String id);


    @Select("<script>" +
            " SELECT    " +
            "    ms.MERCHANT_ID merchantId,    " +
            "    m.`NAME` merchantName,    " +
            "    ms.ID merchantStoreId,    " +
            "    ms.IS_PREFERENTIAL,    " +
            "    ( ms.SIGN_TIME &lt;= '2019-01-01' ) size,    " +

            "    ms.PROVINCE ,    " +
            "    ms.CITY ,    " +
            "    ms.AREA ,    " +
            "    ms.ADDRESS ,    " +

            "    ms.`NAME` merchantStoreName,    " +
            "    ms.UID merchantStoreUid,    " +
            "    ms.MOBILE merchantStoreMobile,    " +
//            "    msrm.REGIONAL_MANAGER_ID regionalManagerId,    " +
//            "    rm.`NAME` regionalManagerName,    " +
//            "    rm.MOBILE regionalManagerMobile , " +
            "   (SELECT msrm.REGIONAL_MANAGER_ID FROM merchant_store_regional_manager msrm " +
            "LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID WHERE msrm.MERCHANT_STORE_ID= ms.ID AND msrm.`STATUS` = 1 LIMIT 1 ) regionalManagerId, " +
            "(SELECT rm.`NAME` FROM merchant_store_regional_manager msrm " +
            "LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID WHERE msrm.MERCHANT_STORE_ID= ms.ID AND msrm.`STATUS` = 1 LIMIT 1 ) regionalManagerName, " +
            "(SELECT rm.MOBILE FROM merchant_store_regional_manager msrm " +
            "LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID WHERE msrm.MERCHANT_STORE_ID= ms.ID AND msrm.`STATUS` = 1 LIMIT 1 )  regionalManagerMobile, " +
            "   ms.MANAGEMENT_EXPENSE  money," +
            "    ms.SERVICE_EXPIRE_TIME  " +
            " FROM    " +
            "    merchant_store ms    " +
            "    LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
//            "    LEFT JOIN merchant_store_regional_manager msrm ON ms.ID = msrm.MERCHANT_STORE_ID    " +
//            "    LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID     " +
            " WHERE    " +
            "  ms.`STATUS`  NOT IN (3,9) " +
//            "  AND msrm.`STATUS` != 2" +
            "  AND TIMESTAMPDIFF(DAY ,NOW(),SERVICE_EXPIRE_TIME) <![CDATA[ <= ]]> 30" +
//            " AND ms.IS_PREFERENTIAL = 2 " +
//            " AND ms.SIGN_TIME &gt;= '2019-01-01'" +
//            "    AND ms.CONTRACT_STATUS =2 " +
            " </script>")
    List<ManagementOrderExpireTimeVo> queryByExpireTime();


    @Select("<script>" +
            "SELECT " +
            " ms.MERCHANT_ID merchantId, " +
            " m.`NAME` merchantName, " +
            " ms.ID merchantStoreId, " +
            " ms.IS_PREFERENTIAL, " +
            " ms.PROVINCE, " +
            " ms.CITY, " +
            " ms.AREA, " +
            " ms.ADDRESS, " +
            " ms.`NAME` merchantStoreName, " +
            " ms.UID merchantStoreUid, " +
            " ms.MOBILE merchantStoreMobile, " +
            " ms.STATUS, " +
            " ( " +
            " SELECT " +
            "  msrm.REGIONAL_MANAGER_ID  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID  " +
            " WHERE " +
            "  msrm.MERCHANT_STORE_ID = ms.ID  " +
            "  AND msrm.`STATUS` = 1  " +
            "  LIMIT 1  " +
            " ) regionalManagerId, " +
            " ( " +
            " SELECT " +
            "  rm.`NAME`  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID  " +
            " WHERE " +
            "  msrm.MERCHANT_STORE_ID = ms.ID  " +
            "  AND msrm.`STATUS` = 1  " +
            "  LIMIT 1  " +
            " ) regionalManagerName, " +
            " ( " +
            " SELECT " +
            "  rm.MOBILE  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID  " +
            " WHERE " +
            "  msrm.MERCHANT_STORE_ID = ms.ID  " +
            "  AND msrm.`STATUS` = 1  " +
            "  LIMIT 1  " +
            " ) regionalManagerMobile, " +
            " ms.MANAGEMENT_EXPENSE money, " +
            " ms.SERVICE_EXPIRE_TIME  " +
            "FROM " +
            " merchant_store ms " +
            " LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID  " +
            "WHERE " +
            " ms.`STATUS` = 2  " +
            " AND ms.MANAGEMENT_EXPENSE != 0  " +
            " AND ms.SERVICE_EXPIRE_TIME IS NOT NULL  " +
            " AND DATE_SUB( ms.SERVICE_EXPIRE_TIME, INTERVAL 180 DAY ) <![CDATA[ <= ]]> DATE_FORMAT( NOW(), '%y-%m-%d' )" +
            "</script>")
    List<ManagementOrderExpireTimeVo> queryStoreByCollectionMonth();

    @Select("SELECT " +
            " t2.*  " +
            "FROM " +
            " merchant_store_regional_manager t1 " +
            " INNER JOIN regional_manager t2 ON t1.REGIONAL_MANAGER_ID = t2.ID  " +
            "WHERE " +
            " t1.`STATUS` = 1  " +
            " AND t1.MERCHANT_STORE_ID = #{storeId} ")
    RegionalManager getRegionalManagerByStoreId(Integer storeId);

    @Select("<script>" +
            " SELECT    " +
            "    ms.MERCHANT_ID merchantId,    " +
            "    m.`NAME` merchantName,    " +
            "    ms.ID merchantStoreId,    " +
            "    ms.`NAME` merchantStoreName,    " +
            "    ms.UID merchantStoreUid,    " +
            "    ms.MOBILE merchantStoreMobile,    " +
            "    msrm.REGIONAL_MANAGER_ID regionalManagerId,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.MOBILE regionalManagerMobile , " +
            "    ms.MANAGEMENT_EXPENSE-ms.ALREADY_MANAGEMENT_EXPENSE  money," +
            "    ms.MERCHANT_LINK_END_TIME  " +
            " FROM    " +
            "    merchant_store ms    " +
            "    LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
            "    LEFT JOIN merchant_store_regional_manager msrm ON ms.ID = msrm.MERCHANT_STORE_ID    " +
            "    LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID     " +
            " WHERE    " +
            "    msrm.`STATUS` = 1" +
            "     AND TIMESTAMPDIFF(DAY ,NOW(),ms.MERCHANT_LINK_END_TIME) <![CDATA[ <= ]]> 30" +
            "    AND ms.MANAGEMENT_EXPENSE <![CDATA[ < ]]>  20000" +
            " </script>")
    List<MerchantLinkOrderVo> merchantLinkOrderVo();


    @Select("<script>" +
            " SELECT    " +
            "    mscs.ID ,    " +
            "    ms.MERCHANT_ID merchantId,    " +
            "    m.`NAME` merchantName,    " +
            "    ms.ID merchantStoreId,    " +
            "    ms.`NAME` merchantStoreName,    " +
            "    ms.UID merchantStoreUid,    " +

            "    ms.PROVINCE ,    " +
            "    ms.CITY ,    " +
            "    ms.AREA ,    " +
            "    ms.ADDRESS ,    " +

            "    ms.MOBILE merchantStoreMobile,    " +
            "    msrm.REGIONAL_MANAGER_ID regionalManagerId,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.MOBILE regionalManagerMobile , " +
            "    mscs.END_TIME   " +
            " FROM    " +
            "    merchant_store_cloud_school mscs " +
            "    LEFT JOIN merchant_store ms ON mscs.MERCHANT_STORE_ID = ms.ID    " +
            "    LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
            "    LEFT JOIN merchant_store_regional_manager msrm ON ms.ID = msrm.MERCHANT_STORE_ID    " +
            "    LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID     " +
            " WHERE    " +
            "     mscs.`STATUS` = 1" +
            "  AND  ms.`STATUS` NOT IN(3,9) " +
            "     AND msrm.`STATUS` = 1" +
            "     AND TIMESTAMPDIFF(DAY ,NOW(),mscs.END_TIME) <![CDATA[ <= ]]> 30" +
            "     AND ms.MANAGEMENT_EXPENSE <![CDATA[ < ]]>  20000" +
            " </script>")
    List<MerchantStoreCloudSchoolVo> merchantStoreCloudSchoolVo();

    @Select("SELECT  " +
            "  UID  " +
            "FROM  " +
            "  merchant_store")
    List<String> queryUids();

    @Select("SELECT  " +
            "  ms.ID merchantStoreId,  " +
            "  ms.UID merchantStoreUid,  " +
            "  ms.`NAME` merchantStoreName,  " +


            "  ms.PROVINCE ,  " +
            "  ms.CITY ,  " +
            "  ms.AREA ,  " +
            "  ms.ADDRESS ,  " +


            "  ms.MOBILE merchantStoreMobile,  " +
            "  m.ID merchantId,  " +
            "  m.`NAME` maechantName,  " +
            "  rm.ID regionalManagerId,  " +
            "  rm.`NAME` regionalManagerName,  " +
            "  rm.MOBILE regionalManagerMobile  " +
            "FROM  " +
            "  merchant_store ms LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID  " +
            "  LEFT JOIN merchant_store_regional_manager msrm ON msrm.MERCHANT_STORE_ID = ms.ID  " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID  " +
            "  WHERE msrm.`STATUS`=1  " +
            "  ")
    List<ExcelSubmitForReviewVo> ExcelSubmitForReviewVo();

    /**
     * 查询云学堂所有账号信息
     *
     * @return
     */
    @Select("SELECT " +
            " ID, " +
            " MERCHANT_STORE_UID, " +
            " ACCOUNT  " +
            "FROM " +
            " merchant_store_cloud_school  " +
            "WHERE " +
            " `STATUS` = 1")
    List<ListCloudSchoolDTO> listCloudSchool();

    /**
     * 查询所有门店
     *
     * @return
     */
    @Select("SELECT " +
            " * " +
            "FROM " +
            " merchant_store  ")
    List<MerchantStore> listMerchantStore();

    @Select("<script>" +
            " SELECT    " +
            "    ms.MERCHANT_ID merchantId,    " +
            "    m.`NAME` merchantName,    " +
            "    ms.ID merchantStoreId,    " +
            "    ms.IS_PREFERENTIAL,    " +
            "    ( ms.SIGN_TIME &lt;= '2019-01-01' ) size,    " +

            "    ms.PROVINCE ,    " +
            "    ms.CITY ,    " +
            "    ms.AREA ,    " +
            "    ms.ADDRESS ,    " +

            "    ms.`NAME` merchantStoreName,    " +
            "    ms.UID merchantStoreUid,    " +
            "    ms.MOBILE merchantStoreMobile,    " +
            "    msrm.REGIONAL_MANAGER_ID regionalManagerId,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.MOBILE regionalManagerMobile , " +
            "    ms.MANAGEMENT_EXPENSE  money," +
            "    ms.EXPIRE_TIME  " +
            " FROM    " +
            "    merchant_store ms    " +
            "    LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
            "    LEFT JOIN merchant_store_regional_manager msrm ON ms.ID = msrm.MERCHANT_STORE_ID    " +
            "    LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID     " +
            " WHERE    " +
            "    ms.UID in " +
            "   <foreach collection=\"list\" item=\"merchantMoneyDto\" index=\"index\"     " +
            "            open=\"(\" close=\")\" separator=\",\">     " +
            "            #{merchantMoneyDto.code}        " +
            "        </foreach> " +
            " </script>")
    List<ManagementOrderExpireTimeVo> queryCodes(List<MerchantMoneyDto> list);


    @Select("<script>" +
            " SELECT    " +
            "    ms.MERCHANT_ID merchantId,    " +
            "    m.`NAME` merchantName,    " +
            "    ms.ID merchantStoreId,    " +
            "    ms.`NAME` merchantStoreName,    " +
            "    ms.UID merchantStoreUid,    " +
            "    ms.MOBILE merchantStoreMobile,    " +
            "    msrm.REGIONAL_MANAGER_ID regionalManagerId,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.MOBILE regionalManagerMobile , " +
            "    ms.MANAGEMENT_EXPENSE-ms.ALREADY_MANAGEMENT_EXPENSE  money," +
            "    ms.MERCHANT_LINK_END_TIME  " +
            " FROM    " +
            "    merchant_store ms    " +
            "    LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
            "    LEFT JOIN merchant_store_regional_manager msrm ON ms.ID = msrm.MERCHANT_STORE_ID    " +
            "    LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID     " +
            " WHERE    " +
            "    ms.UID in " +
            "   <foreach collection=\"list\" item=\"merchantMoneyDto\" index=\"index\"     " +
            "            open=\"(\" close=\")\" separator=\",\">     " +
            "            #{merchantMoneyDto.code}        " +
            "        </foreach> " +
            " </script>")
    List<MerchantLinkOrderVo> queryCodesLink(List<MerchantMoneyDto> list);


    @Select("<script>" +
            " SELECT    " +
            "    mscs.ID ,    " +
            "    ms.MERCHANT_ID merchantId,    " +
            "    m.`NAME` merchantName,    " +
            "    ms.ID merchantStoreId,    " +
            "    ms.`NAME` merchantStoreName,    " +
            "    ms.UID merchantStoreUid,    " +

            "    ms.PROVINCE ,    " +
            "    ms.CITY ,    " +
            "    ms.AREA ,    " +
            "    ms.ADDRESS ,    " +

            "    ms.MOBILE merchantStoreMobile,    " +
            "    msrm.REGIONAL_MANAGER_ID regionalManagerId,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.`NAME` regionalManagerName,    " +
            "    rm.MOBILE regionalManagerMobile , " +
            "    mscs.END_TIME   " +
            " FROM    " +
            "    merchant_store_cloud_school mscs " +
            "    LEFT JOIN merchant_store ms ON mscs.MERCHANT_STORE_ID = ms.ID    " +
            "    LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
            "    LEFT JOIN merchant_store_regional_manager msrm ON ms.ID = msrm.MERCHANT_STORE_ID    " +
            "    LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID     " +
            " WHERE    " +
            "    ms.UID in " +
            "   <foreach collection=\"list\" item=\"merchantMoneyDto\" index=\"index\"     " +
            "            open=\"(\" close=\")\" separator=\",\">     " +
            "            #{merchantMoneyDto.code}        " +
            "        </foreach> " +
            " </script>")
    List<MerchantStoreCloudSchoolVo> merchantStoreCloudSchoolVoList(List<MerchantMoneyDto> list);


    @Select("<script>" +
            " SELECT    " +
            " * " +
            " FROM    " +
            "    merchant_store   " +
            " WHERE    " +
            "   UID in " +
            "   <foreach collection=\"list\" item=\"merchantMoneyDto\" index=\"index\"     " +
            "            open=\"(\" close=\")\" separator=\",\">     " +
            "            #{merchantMoneyDto.code}        " +
            "        </foreach> " +
            " </script>")
    List<MerchantStore> queryStoreCodes(List<MerchantMoneyDto> list);

    /**
     * 查询所有加盟商
     *
     * @return
     */
    @Select("SELECT ID,`NAME` FROM merchant WHERE `STATUS` = 1 AND IS_AGENT = 1")
    List<MerchantSelectBoxVO> listMerchantSelectBox();

    /**
     * 根据省市区获取代理和区域经理
     *
     * @param province
     * @param city
     * @param area
     * @return
     */
    @Select("SELECT " +
            " ID, " +
            " AGENT_ID, " +
            " AGENT_NAME, " +
            " MANAGER_ID, " +
            " MANAGER_NAME, " +
            " IS_MAPPING, " +
            " ( SELECT ID FROM merchant_agent_area WHERE UID = t1.AGENT_UID LIMIT 1 ) AGENT_AREA_ID " +
            "FROM " +
            " merchant_store_mapping_area t1  " +
            "WHERE " +
            " t1.PROVINCE LIKE concat('%',#{province},'%') " +
            " AND t1.CITY LIKE concat('%',#{city},'%') " +
            " AND t1.AREA LIKE concat('%',#{area},'%') " +
            "LIMIT 1")
    AgentManagerDTO getAgentManager(String province, String city, String area);


    /**
     * 获取原区域经理信息
     *
     * @param storeId
     * @return
     */
    @Select("SELECT " +
            " t3.ID AS managerId, " +
            " t3.`NAME`  " +
            "FROM " +
            " merchant_store_regional_manager t2 " +
            " INNER JOIN regional_manager t3 ON t2.REGIONAL_MANAGER_ID = t3.id  " +
            "WHERE " +
            " t2.`STATUS` = 1  " +
            " AND t3.`STATUS` = 1  " +
            " AND t2.MERCHANT_STORE_ID = #{storeId} " +
            "LIMIT 1")
    MerchantStoreRegionalManagerDTO getOldManager(Integer storeId);

    /**
     * 门店映射
     *
     * @return
     */
    @Select("SELECT PROVINCE,CITY,AREA,REGION,LEVEL FROM merchant_store_mapping_area WHERE `STATUS` = 1")
    List<MappingAreaDTO> listMappingArea();

    @Select("<script>" +
            "SELECT " +
            " m.ID, " +
            " m.TYPE, " +
            " m.UID, " +
            " m.PROVINCE, " +
            " m.CITY, " +
            " m.AREA, " +
            " m.MOBILE, " +
            " m.SIGNATORY, " +
            " m.ID_NUMBER, " +
            " m.MERCHANT_ID, " +
            " m.STATUS_TWO, " +
            " m.FRANCHISE_FEE, " +
            " m.BOND_MONEY, " +
            " m.MANAGEMENT_EXPENSE, " +
            " m.SEAT_COUNT, " +
            " m.HLL_CODE, " +
            " m.MT_ID, " +
            " m.ELM_ID, " +
            " m.DZDP_ID, " +
            " m.REMARK, " +
            " m.DELIVERY_ADDRESS, " +
            " m.DISTRIBUTION_LIMIT, " +
            " m.RECEIVER, " +
            " m.RECEIVER_MOBILE, " +
            " m.SHOW_DELIVERY_ADDRESS, " +
            " 1500 AS chargingStandard, " +
            " 150 AS cloudSchool, " +
            " ( SELECT COUNT(*) FROM merchant_store_cloud_school WHERE MERCHANT_STORE_ID = m.ID AND `STATUS` = 1 ) openNum, " +
            " m.OPEN_TIME, " +
            " m.OPEN_AGAIN_TIME, " +
            " m.CLOSE_TIME, " +
            " m.RELOCATION_TIME, " +
            " m.PAUSE_TIME, " +
            " m.ESTIMATE_TIME, " +
            " ( SELECT `NAME` FROM merchant WHERE ID = m.AGENT_ID LIMIT 1 ) AGENT_NAME, " +
            " m.IS_PREFERENTIAL, " +
            " t.`NAME` merchantName, " +
            " t.MOBILE merchantMobile, " +
            " m.ADDRESS, " +
            " m.CONTRACT_STATUS, " +
            " t2.regionalManagerName, " +
            " t2.departmentName, " +
            " m.SIGN_TIME, " +
            " m.EXPIRE_TIME, " +
            " m.SERVICE_EXPIRE_TIME, " +
            " m.COLLECTION_MONTH, " +
            " m.DELAYED_OPEN, " +
            " m.`STATUS`,  " +
            " m.IS_APPLY, " +
            " m4.UID AS belongAgency " +
            "FROM " +
            " merchant_store m " +
            " INNER JOIN merchant t ON m.MERCHANT_ID = t.ID " +
            " LEFT JOIN ( " +
            " SELECT " +
            "  msrm.ID, " +
            "  rm.ID regionalManagerId, " +
            "  rm.`NAME` regionalManagerName, " +
            "  d.`NAME` departmentName, " +
            "  msrm.MERCHANT_STORE_ID  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID " +
            "  LEFT JOIN regional_manager_department rmd ON rmd.REGIONAL_MANAGER_ID = rm.ID " +
            "  LEFT JOIN department d ON d.ID = rmd.DEPARTMENT_ID  " +
            " WHERE " +
            "  msrm.`STATUS` = 1  " +
            " AND rmd.`STATUS` = 1  " +
            " ) t2 ON m.ID = t2.MERCHANT_STORE_ID" +
            " LEFT JOIN merchant_agent_area m4 ON m.AGENT_AREA_ID = m4.ID " +
            "<where>" +
            " <if test='condition !=null'>AND ( m.UID  like concat('%',#{condition},'%') " +
            "OR t.`NAME` like concat('%',#{condition},'%') " +
            "OR t2.regionalManagerName LIKE concat('%',#{condition},'%') " +
            "OR m.PROVINCE LIKE concat('%',#{condition},'%') " +
            "OR m.CITY LIKE concat('%',#{condition},'%') " +
            "OR m.AREA LIKE concat('%',#{condition},'%') " +
            ") </if>" +
            " <if test='startSignTime !=null'>AND m.SIGN_TIME BETWEEN #{startSignTime} AND #{endSignTime} </if>" +
            " <if test='startExpireTime !=null'>AND m.EXPIRE_TIME BETWEEN #{startExpireTime} AND #{endExpireTime}  </if>" +
            " <if test='merchantId !=null'>AND m.MERCHANT_ID = #{merchantId}  </if>" +
            " <if test='month !=null'>AND m.COLLECTION_MONTH = #{month}  </if>" +
            " <if test='status !=null'>AND m.`STATUS` = #{status}  </if>" +
            " <if test='delayedOpen !=null'>AND m.DELAYED_OPEN = #{delayedOpen}  </if>" +
            "</where>" +
            "</script>")
    List<ExportMerchantStoreDetailVO> exportStoreDetail(StroePageDto stroePageDto);

    @Update("  <script>  " +
            "    <foreach collection=\"updateList\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">" +
            "        update merchant_store " +
            "       <set><if test='item.idNumber != null'>" +
            "            ID_NUMBER = #{item.idNumber}" +
            "        </if>" +
            "        <if test='item.seatCount != null'>," +
            "            SEAT_COUNT = #{item.seatCount}" +
            "        </if>" +
            "        <if test='item.hllCode != null'>," +
            "            HLL_CODE = #{item.hllCode}" +
            "        </if>" +
            "        <if test='item.mtId != null'>," +
            "            MT_ID = #{item.mtId}" +
            "        </if>" +
            "        <if test='item.elmId != null'>," +
            "            ELM_ID = #{item.elmId}" +
            "        </if>" +
            "        <if test='item.dzdpId != null'>," +
            "            DZDP_ID = #{item.dzdpId}" +
            "       </if></set>" +
            "        where UID = #{item.uid}" +
            "    </foreach> " +
            "</script>")
    void updateExtraField(@Param("updateList") List<MerchantStore> updateList);

    @Select("SELECT " +
            " m2.RECEIPT_DETAILED_ADDRESS  AS address ," +
            " m2.RECEIPT_PROVINCE AS receiptProvince," +
            " m2.RECEIPT_CITY AS receiptCity," +
            " m2.RECEIPT_AREA AS receiptArea," +
            " m2.RECEIVER AS receiver ," +
            " m2.DELIVERY_PHONE AS phone " +
            " FROM " +
            " merchant_store m1 " +
            " INNER JOIN merchant_agent_area m2 ON m1.AGENT_AREA_ID = m2.ID" +
            " WHERE m1.ID=#{id}")
    ReturnDataVO selectActing(ReturnDataDTO returnDataaDTO);

    @Select("SELECT " +
            " m1.ID AS id ," +
            " m1.UID ," +
            " m1.MERCHANT_NAME AS merchantName" +
            " FROM " +
            " merchant_agent_area m1 " +
            " WHERE MERCHANT_ID = #{agentId} ")
    List<BelongAgencyVO> belongAgencyMapper(BelongAgencyDTO belongAgencyDTO);

    @Update(" UPDATE merchant_store m1 " +
            " SET " +
            " m1.AGENT_AREA_ID=#{proxyId} " +
            " WHERE " +
            " m1.ID = #{storeId}")
    boolean updateBelongAgencyMapper(UpdateBelongAgencyDTO updateBelongAgencyDTO);

    @Select("SELECT " +
//            详细地址
            " m1.DELIVERY_ADDRESS  AS deliveryAddress ," +
//            省
            " m1.DELIVERY_PROVINCE AS province," +
//            市
            " m1.DELIVERY_CITY AS city," +
//            区
            " m1.DELIVERY_AREA AS area," +
//            收货人
            " m1.RECEIVER AS receiver ," +
//            收货人电话
            " m1.RECEIVER_MOBILE AS receiverMobile " +
            " FROM " +
            " merchant_store m1 " +
            " WHERE " +
            " m1.ID = #{id}")
    DeliveryVO deliveryToStoreServiceMapper(ReturnDataDTO returnDataDTO);

    @Update("<script>" +
            "update merchant_store SET IS_APPLY = #{isApply} " +
            "where id IN " +
            "<foreach collection='storeIds' item='storeId' index='index' open='(' close=')' separator=','>" +
            "   #{storeId}" +
            "</foreach> " +
            "</script>")
    int updateApplyAdjust(UpdateApplyAdjustDTO updateApplyAdjustDTO);

    @Select("SELECT " +
            " ms.MERCHANT_ID merchantId, " +
            " m.`NAME` merchantName, " +
            " ms.ID merchantStoreId, " +
            " ms.IS_PREFERENTIAL, " +
            " ms.PROVINCE, " +
            " ms.CITY, " +
            " ms.AREA, " +
            " ms.ADDRESS, " +
            " ms.`NAME` merchantStoreName, " +
            " ms.UID merchantStoreUid, " +
            " ms.MOBILE merchantStoreMobile, " +
            " ms.STATUS, " +
            " ( " +
            " SELECT " +
            "  msrm.REGIONAL_MANAGER_ID  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID  " +
            " WHERE " +
            "  msrm.MERCHANT_STORE_ID = ms.ID  " +
            "  AND msrm.`STATUS` = 1  " +
            "  LIMIT 1  " +
            " ) regionalManagerId, " +
            " ( " +
            " SELECT " +
            "  rm.`NAME`  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID  " +
            " WHERE " +
            "  msrm.MERCHANT_STORE_ID = ms.ID  " +
            "  AND msrm.`STATUS` = 1  " +
            "  LIMIT 1  " +
            " ) regionalManagerName, " +
            " ( " +
            " SELECT " +
            "  rm.MOBILE  " +
            " FROM " +
            "  merchant_store_regional_manager msrm " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID  " +
            " WHERE " +
            "  msrm.MERCHANT_STORE_ID = ms.ID  " +
            "  AND msrm.`STATUS` = 1  " +
            "  LIMIT 1  " +
            " ) regionalManagerMobile, " +
            " ms.MANAGEMENT_EXPENSE money, " +
            " ms.SERVICE_EXPIRE_TIME  " +
            "FROM " +
            " merchant_store ms " +
            " LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID  " +
            "WHERE " +
            " ms.UID = #{uid}")
    ManagementOrderExpireTimeVo queryStoreInfoByUid(String uid);

}