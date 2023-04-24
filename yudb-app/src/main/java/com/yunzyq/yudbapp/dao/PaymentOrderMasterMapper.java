package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.model.PaymentOrderMaster;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PaymentOrderMasterMapper extends MyBaseMapper<PaymentOrderMaster> {


    @Select("<script>" +
            "SELECT " +
            " pom.id, " +
            " pom.ORDER_NO, " +
            " pom.MERCHANT_NAME, " +
            " pom.MERCHANT_STORE_MOBILE mobile, " +
            " pom.MERCHANT_STORE_UID uid, " +
            " pom.PAYMENT_TYPE_NAME payTypeName, " +
            " pom.MONEY, " +
            " pom.PAY_MONEY, " +
            " ( pom.MONEY - pom.PAY_MONEY ) remain, " +
            " pom.`STATUS`, " +
            " pom.ADJUSTMENT_COUNT, " +
            "IF " +
            " ( pom.SEND = 1, '已推送', '未推送' ) send, " +
            " pom.EXPIRE_TIME, " +
            " pom.EXAMINE examineStatus, " +
            " pom.NATURAL_YEAR_END, " +
            " ms.PROVINCE, " +
            " ms.CITY, " +
            " ms.AREA, " +
            " ms.ADDRESS, " +
            " ( SELECT poe.refuse FROM payment_order_examine poe WHERE pom.ID = poe.PAYMENT_ORDER_MASTER_ID AND poe.DELETED = 0 ORDER BY CREATE_TIME DESC LIMIT 1 ) refuse," +
            " ( SELECT poe.MSG FROM payment_order_examine poe   WHERE pom.ID = poe.PAYMENT_ORDER_MASTER_ID AND poe.DELETED = 0 ORDER BY CREATE_TIME DESC LIMIT 1  ) adjustment, " +
            " (SELECT CREATE_NAME FROM payment_order_examine_deail t1 INNER JOIN payment_order_examine t2 ON t1.PAYMENT_ORDER_EXAMINE_ID = t2.ID WHERE PAYMENT_ORDER_MASTER_ID = pom.ID ORDER BY t1.ID DESC LIMIT 1) approveName " +
            "FROM " +
            " payment_order_master pom " +
            " LEFT JOIN merchant_store ms ON pom.MERCHANT_STORE_ID = ms.ID  " +
            "WHERE " +
            " pom.REGIONAL_MANAGER_ID = #{vlaue}  " +
            " AND pom.DELETED = 0" +
            " AND pom.IS_PUBLISH = 1 " +
//            " <if test='platformPageDTO.type==\"1\"'>AND  pom.`STATUS` NOT IN(2,3,4)" +
//            "   AND ( pom.TYPE, pom.EXAMINE ) NOT IN ( ( 3, 2 ) ) </if> " +
//            " <if test='platformPageDTO.type==\"2\"'>AND  pom.`STATUS` NOT IN(2,3,4) " +
//            "   AND pom.EXAMINE = 2 </if>" +
//            " <if test='platformPageDTO.type==\"3\"'>AND  pom.`STATUS` NOT IN(2,3,4)" +
//            "   AND ( pom.TYPE, pom.EXAMINE ) NOT IN ( ( 3, 2 ) ) " +
//            "   AND pom.ADJUSTMENT_COUNT &gt; 0</if> " +
//            " <if test='platformPageDTO.type==\"4\"'> </if>" +
            " <if test='platformPageDTO.type==0'>AND  pom.`STATUS` = 1 AND ( pom.TYPE, pom.EXAMINE, pom.ADJUSTMENT_COUNT ) NOT IN ( ( 3, 2 ,0),( 1, 2 ,0) ) </if> " +
            " <if test='platformPageDTO.type==1'>AND  pom.`STATUS` = 1 AND  pom.ADJUSTMENT_COUNT = 0 AND  pom.EXAMINE = 1</if>" +
            " <if test='platformPageDTO.type==2'>AND  pom.`STATUS` = 1 AND  pom.ADJUSTMENT_COUNT &gt; 0 AND  pom.EXAMINE = 1</if> " +
            " <if test='platformPageDTO.type==3'>AND  pom.`STATUS` = 1 AND ( pom.TYPE, pom.EXAMINE, pom.ADJUSTMENT_COUNT ) NOT IN ( ( 3, 2 ,0),( 1, 2 ,0) )  AND  pom.EXAMINE = 2</if>" +
            " <if test='platformPageDTO.conditions != null'> AND ( pom.MERCHANT_STORE_UID = #{platformPageDTO.conditions} OR  pom.MERCHANT_ID = #{platformPageDTO.conditions} OR  pom.PAYMENT_TYPE_NAME = #{platformPageDTO.conditions}) </if>" +
//            " <if test='platformPageDTO.merchantStoreUid != null'> AND pom.MERCHANT_STORE_UID = #{platformPageDTO.merchantStoreUid} </if>" +
//            " <if test='platformPageDTO.merchantId != null'> AND pom.MERCHANT_ID = #{platformPageDTO.merchantId} </if>" +
//            " <if test='platformPageDTO.paymentTypeId != null'> AND pom.PAYMENT_TYPE_ID = #{platformPageDTO.paymentTypeId} </if>" +
            "   ORDER BY  pom.EXPIRE_TIME </script> ")
    List<RegionalManagerPlatformPageVo> findPage(String vlaue, @RequestParam("platformPageDTO") PlatformPageDTO platformPageDTO);

    @Select("<script>" +
            "SELECT " +
            " t2.id, " +
            " t1.ORDER_NO, " +
            " t1.MERCHANT_NAME, " +
            " t1.MERCHANT_STORE_MOBILE mobile, " +
            " t1.MERCHANT_STORE_UID uid, " +
            " t1.PAYMENT_TYPE_NAME payTypeName, " +
            " t2.`STATUS` examineStatus, " +
            " t1.MONEY, " +
            " t1.PAY_MONEY, " +
            " ( t1.MONEY - t1.PAY_MONEY ) remain, " +
            " t1.ADJUSTMENT_COUNT, " +
            " t1.EXPIRE_TIME, " +
            " t3.PROVINCE, " +
            " t3.CITY, " +
            " t3.AREA, " +
            " t3.ADDRESS, " +
            " t2.`STATUS`, " +
            " t2.CREATE_TIME, " +
            " t2.EXAMINE_TYPE, " +
            " t2.MSG, " +
            " t2.REMARK, " +
            " t2.DELETED  " +
            "FROM " +
            " payment_order_master t1 " +
            " LEFT JOIN payment_order_examine t2 ON t1.ID = t2.PAYMENT_ORDER_MASTER_ID  " +
            " LEFT JOIN merchant_store t3 ON t1.MERCHANT_STORE_ID = t3.ID  " +
            "WHERE " +
            " t1.REGIONAL_MANAGER_ID = #{vlaue}  " +
            " AND t1.DELETED = 0  " +
//            " AND t2.DELETED = 0  " +
            " AND t1.`STATUS` = 1  " +
            " AND t1.IS_PUBLISH = 1  " +
            " AND t2.APPLY_ID = #{vlaue} " +
//            " AND t2.EXAMINE_TYPE != 3  " +
//            " AND t1.ADJUSTMENT_COUNT &gt; 0  " +
//            " AND t1.EXAMINE = 1  " +
            " <if test='platformPageDTO.conditions != null'> AND ( t1.MERCHANT_STORE_UID = #{platformPageDTO.conditions} OR  t1.MERCHANT_ID = #{platformPageDTO.conditions} OR  t1.PAYMENT_TYPE_NAME = #{platformPageDTO.conditions}) </if>" +
            "ORDER BY " +
            " t2.ID DESC" +
            "</script>")
    List<RegionalManagerPlatformPageVo> findPage2(String vlaue, @RequestParam("platformPageDTO") PlatformPageDTO platformPageDTO);

    @Select("SELECT " +
            " t2.USER_NAME  " +
            "FROM " +
            " payment_order_examine t1 " +
            " INNER JOIN approve_process_step t2 ON t1.STEP = t2.STEP " +
            " INNER JOIN approve_process t3 ON t1.EXAMINE_TYPE = t3.EXAMINE_TYPE  " +
            " AND t2.APPROVE_ID = t3.ID  " +
            " INNER JOIN payment_order_master t4 ON t1.PAYMENT_ORDER_MASTER_ID = t4.ID " +
            "WHERE " +
            " t3.TYPE = 1  " +
            " AND t1.PAYMENT_ORDER_MASTER_ID = #{id} " +
            " AND t2.`STATUS` = 1 " +
            " AND t3.`STATUS` = 1" +
            " AND t1.DELETED = 0" +
            " AND t2.TYPE = ( " +
            " SELECT " +
            "  TYPE  " +
            " FROM " +
            "  payment_order_examine_deail  " +
            " WHERE " +
            "  PAYMENT_ORDER_EXAMINE_ID = t1.ID  " +
            " ORDER BY " +
            "  ID DESC  " +
            " LIMIT 1  " +
            " )" +
            " ORDER BY t1.ID DESC LIMIT 1")
    List<String> getApproveName(Integer id);

    @Select("SELECT   " +
            "   pom.id,   " +
            "   pom.MERCHANT_NAME ,   " +
            "   pom.MERCHANT_STORE_MOBILE mobile ,   " +
            "   pom.MERCHANT_STORE_UID uid,   " +
            "   pom.PAYMENT_TYPE_NAME payTypeName,   " +
            "   pom.MONEY,   " +
            "   pom.`STATUS`,   " +
            "   IF(   pom.SEND=1,'已推送','未推送') agentSend,   " +
            "   pom.EXPIRE_TIME,   " +
            "   pom.EXAMINE examineStatus,   " +
            " ( SELECT poe.refuse FROM payment_order_examine poe WHERE pom.ID = poe.PAYMENT_ORDER_MASTER_ID AND poe.DELETED = 0 ORDER BY CREATE_TIME DESC LIMIT 1 ) refuse," +
            " ( SELECT poe.MSG FROM payment_order_examine poe   WHERE pom.ID = poe.PAYMENT_ORDER_MASTER_ID AND poe.DELETED = 0 ORDER BY CREATE_TIME DESC LIMIT 1  ) adjustment " +
            "FROM   " +
            "   payment_order_master pom  " +
            "   WHERE pom.REGIONAL_MANAGER_ID = #{vlaue}   " +
            "  AND pom.EXAMINE = 1" +
            "   AND pom.DELETED = 0   " +
            "   AND  pom.`STATUS` NOT IN(2,3,4) " +
            "   ORDER BY  pom.EXPIRE_TIME  ")
    List<RegionalManagerPlatformPageVo> platformPageNoSh(String vlaue);

    @Select("SELECT   " +
            "   pom.id,   " +
            "   pom.MERCHANT_NAME ,   " +
            "   pom.MERCHANT_STORE_UID uid,   " +
            "   pom.PAYMENT_TYPE_NAME payTypeName,   " +
            "   pom.MONEY,   " +
            "   pom.`STATUS`,   " +
            "   pom.CREATE_TIME,   " +
            "   pom.PAY_TIME  " +
            "FROM   " +
            "   payment_order_master  pom " +
            "   WHERE " +
            "    pom.REGIONAL_MANAGER_ID = #{vlaue}   " +
            "    AND pom.DELETED = 0   " +
            "   AND  pom.`STATUS`= 2" +
            "    ORDER BY CREATE_TIME DESC ")
    List<PlatformRecordPageVo> findPlatformRecordPageVo(String vlaue);

    @Select("SELECT*FROM payment_order_master WHERE id  =#{id} ")
    PaymentOrderMaster queryByOrderNO(String id);


    @Select("SELECT count(*) FROM payment_order_master WHERE REGIONAL_MANAGER_ID  =#{vlaue}  AND STATUS=1 AND EXAMINE= 1 AND DELETED=0 ")
    Integer queryByRegionalManagerIdWorkCount(String vlaue);


    @Select("SELECT ID, PAYMENT_TYPE_NAME 'name',MONEY  FROM payment_order_master WHERE REGIONAL_MANAGER_ID  =#{vlaue}  AND STATUS=1 AND EXAMINE= 1 AND DELETED=0 ORDER BY CREATE_TIME DESC  LIMIT 1")
    IndexOrderVo queryByRegionalManagerIdInfoWorkInfo(String vlaue);

    @Select("<script>" +
            "SELECT " +
            "  COUNT(1) " +
            "FROM " +
            "  payment_order_master  " +
            "WHERE " +
            "  MERCHANT_ID = #{vlaue}  " +
            "  AND STATUS = 1  " +
            "  AND EXAMINE= 1 " +
            "  AND SEND = 1 " +
            "  AND DELETED=0" +
            "  AND DATE_SUB( EXPIRE_TIME, INTERVAL 30 DAY ) <![CDATA[ <= ]]>  date( " +
            "  NOW())" +
            " </script>")
    Integer queryByMerchantIdWorkCount(String vlaue);

    @Select("<script>" +
            " SELECT " +
            "   ID, " +
            " PAYMENT_TYPE_NAME 'name'," +
            " MONEY  " +
            " FROM " +
            "  payment_order_master  " +
            " WHERE " +
            "  MERCHANT_ID = #{vlaue}  " +
            "  AND STATUS = 1  " +
            "  AND EXAMINE= 1 " +
            "  AND SEND = 1 " +
            "  AND DELETED=0" +
            "  AND DATE_SUB(EXPIRE_TIME, INTERVAL 30 DAY ) <![CDATA[ <= ]]> date( " +
            "  NOW())" +
            "  ORDER BY CREATE_TIME DESC  LIMIT 1 " +
            " </script>")
    IndexOrderVo queryByMerchantIdWorkInfo(String vlaue);


    @Select("<script>" +
            "  SELECT   " +
            "   pom.ID,   " +
            "   pom.MERCHANT_STORE_UID uid ,   " +
            "   pom.PROVINCE, " +
            "   pom.CITY, " +
            "   pom.AREA, " +
            "   ms.ADDRESS,   " +
            "   pom.PAYMENT_TYPE_NAME payTypeName,   " +
            "   pom.MONEY,   " +
            "   pom.PAY_MONEY,   " +
            "   ( pom.MONEY - pom.PAY_MONEY ) remain, " +
            "   pom.REGIONAL_MANAGER_MOBILE mobile," +
            "   pom.EXPIRE_TIME,   " +
            "   pom.`STATUS`   " +
            " FROM   " +
            "   payment_order_master pom   " +
            "   LEFT JOIN merchant_store ms ON pom.MERCHANT_STORE_ID = ms.ID   " +
            "   WHERE   " +
            "   pom.`STATUS`=1   " +
            "   AND pom.EXAMINE = 1   " +
            "   AND pom.SEND = 1   " +
            "   AND pom.IS_PUBLISH = 1   " +
            "   AND pom.MERCHANT_ID=#{merchantId}   " +
//            "   AND  DATE_SUB(pom.EXPIRE_TIME, INTERVAL 30 DAY ) <![CDATA[ <= ]]>  date(NOW()) " +
            " <if test='storeId !=null'>" +
            " AND pom.MERCHANT_STORE_ID=#{storeId} " +
            " </if>" +
            "  ORDER BY pom.EXPIRE_TIME   " +
            " </script>")
    List<MerchantPlatformPageVo> queryMerchantPlatformPageVo(StroeIdDto stroeIdDto);

    @Select(" SELECT " +
            " MERCHANT_NAME, " +
            " MERCHANT_STORE_UID uid, " +
            " MONEY, " +
            " PAYMENT_TYPE_NAME payTypeName, " +
            " CREATE_TIME " +
            " FROM " +
            " payment_order_master " +
            " WHERE MERCHANT_STORE_ID =#{id} " +
            " AND REGIONAL_MANAGER_ID =#{regionalManagerId}" +
            " AND `STATUS`=2")
    List<HistoryOrderVo> historyOrder(HistoryOrderDto historyOrderDto);

    @Select("SELECT " +
            " ID, " +
            " MERCHANT_STORE_UID uid," +
            " MERCHANT_NAME," +
            " MERCHANT_STORE_UID uid, " +
            " MONEY money, " +
            " PAYMENT_TYPE_NAME payTypeName, " +
            " CREATE_TIME, " +
            " STATUS, " +
            " PAY_TIME  " +
            "FROM " +
            " payment_order_master  " +
            "WHERE " +
            "  MERCHANT_ID = #{vlaue}  " +
            " AND `STATUS` = 2")
    List<PaymentRecordMerchantVo> paymentRecord(String vlaue);

    @Select("  SELECT " +
            "  MERCHANT_NAME, " +
            "  MERCHANT_STORE_UID uid, " +
            "  PAYMENT_TYPE_NAME 'payTypeName'," +
            "  MONEY  ," +
            "  STATUS  ," +
            "  MERCHANT_STORE_MOBILE mobile" +
            "  FROM payment_order_master WHERE REGIONAL_MANAGER_ID  =#{id} " +
            "  AND STATUS=#{type} " +
            "  AND EXAMINE= 1" +
            "  AND DELETED=0 " +
            " ")
    List<RegionalManagerDaibVo> daib(RegionalManagerDaibDto regionalManagerDaibDto);


    @Select("<script>" +
            " SELECT " +
            " pom.ID, " +
            " ms.ADDRESS address, " +
            " pom.MERCHANT_STORE_UID uid, " +
            " pom.PAYMENT_TYPE_NAME 'payTypeName'," +
            " pom.MONEY  ," +
            " pom.STATUS  ," +
            " pom.MERCHANT_STORE_MOBILE mobile" +
            " FROM payment_order_master  pom  LEFT JOIN merchant_store ms ON ms.ID = pom.MERCHANT_STORE_ID" +
            "  WHERE pom.MERCHANT_ID  =#{id} " +
            "  AND pom.STATUS=#{type} " +
            "  AND pom.DELETED=0" +
            "  AND pom.EXAMINE= 1 " +
            "  AND  DATE_SUB( pom.EXPIRE_TIME, INTERVAL 30 DAY ) <![CDATA[ <= ]]>  date(NOW()) " +
            "  AND pom.SEND = 1   " +
            " </script>")
    List<MerchantDaibVo> merchantDaib(MerchantDaibDto merchantDaibDto);


    @Select(" SELECT      " +
            "      *       " +
            "  FROM      " +
            "      payment_order_master      " +
            "      WHERE MERCHANT_STORE_ID =#{streoId}     " +
            "      AND PAYMENT_TYPE_ID =#{payTypeId}     " +
            "      AND `STATUS` = 1      " +
            "      AND DELETED=0")
    PaymentOrderMaster queryByStoreIdAndPayTypeId(@Param("streoId") Integer streoId, @Param("payTypeId") Integer payTypeId);

    @Select("SELECT " +
            " COUNT(*)  " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  pom.ID " +
//            "  pom.MERCHANT_STORE_UID uid, " +
//            "  pom.PROVINCE, " +
//            "  pom.CITY, " +
//            "  pom.AREA, " +
//            "  ms.ADDRESS, " +
//            "  pom.PAYMENT_TYPE_NAME payTypeName, " +
//            "  pom.MONEY, " +
//            "  pom.REGIONAL_MANAGER_MOBILE mobile, " +
//            "  pom.EXPIRE_TIME, " +
//            "  pom.`STATUS`  " +
            " FROM " +
            "  payment_order_master pom " +
            "  LEFT JOIN merchant_store ms ON pom.MERCHANT_STORE_ID = ms.ID  " +
            " WHERE " +
            "  pom.`STATUS` = 1  " +
            "  AND pom.EXAMINE = 1  " +
            "  AND pom.SEND = 1  " +
            "  AND pom.MERCHANT_ID = #{merchantId} UNION ALL " +
            " SELECT " +
            "  ID " +
//            "  UID, " +
//            "  PAYMENT_TYPE_NAME AS payTypeName, " +
//            "  PROVINCE, " +
//            "  CITY, " +
//            "  AREA, " +
//            "  \"\" AS address, " +
//            "  MONEY, " +
//            "  REGIONAL_MANAGER_MOBILE mobile, " +
//            "  EXPIRE_TIME, " +
//            "  `STATUS`  " +
            " FROM " +
            "  agent_area_payment_order_master  " +
            " WHERE " +
            "  `STATUS` = 1  " +
            "  AND EXAMINE = 1  " +
            "  AND SEND = 1  " +
            "  AND DELETED = 0  " +
            "  AND MERCHANT_ID = #{merchantId}  " +
            " ) p  ")
    Integer queryWorkCountByMerchantId(String merchantId);

    @Select("SELECT " +
            " *  " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  pom.ID, " +
//            "  pom.MERCHANT_STORE_UID uid, " +
//            "  pom.PROVINCE, " +
//            "  pom.CITY, " +
//            "  pom.AREA, " +
//            "  ms.ADDRESS, " +
            "  pom.PAYMENT_TYPE_NAME AS name, " +
            "  pom.MONEY, " +
//            "  pom.REGIONAL_MANAGER_MOBILE mobile, " +
            "  pom.EXPIRE_TIME " +
//            "  pom.`STATUS`  " +
            " FROM " +
            "  payment_order_master pom " +
            "  LEFT JOIN merchant_store ms ON pom.MERCHANT_STORE_ID = ms.ID  " +
            " WHERE " +
            "  pom.`STATUS` = 1  " +
            "  AND pom.EXAMINE = 1  " +
            "  AND pom.SEND = 1  " +
            "  AND pom.MERCHANT_ID = #{merchantId} UNION ALL " +
            " SELECT " +
            "  ID, " +
//            "  UID, " +
            "  PAYMENT_TYPE_NAME  AS name, " +
//            "  PROVINCE, " +
//            "  CITY, " +
//            "  AREA, " +
//            "  \"\" AS address, " +
            "  MONEY, " +
//            "  REGIONAL_MANAGER_MOBILE mobile, " +
            "  EXPIRE_TIME " +
//            "  `STATUS`  " +
            " FROM " +
            "  agent_area_payment_order_master " +
            " WHERE " +
            "  `STATUS` = 1  " +
            "  AND EXAMINE = 1  " +
            "  AND SEND = 1  " +
            "  AND DELETED = 0  " +
            "  AND MERCHANT_ID = #{merchantId}  " +
            " ) p  " +
            "ORDER BY " +
            " p.EXPIRE_TIME DESC " +
            "LIMIT 1")
    IndexOrderVo queryNewestWorkInfoByMerchantId(String merchantId);


    @Select("<script>" +
            "SELECT " +
            " COUNT(*)  " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  pom.ID " +
            " FROM " +
            "  payment_order_master pom  " +
            " WHERE " +
            "  pom.REGIONAL_MANAGER_ID = #{managerId} " +
            "  AND pom.DELETED = 0 " +
            "  AND pom.`STATUS` = 1 " +
            "  AND pom.EXAMINE = 1 " +
            "  AND pom.IS_PUBLISH = 1 " +
            "  AND DATEDIFF( pom.EXPIRE_TIME, DATE_FORMAT( NOW(), '%y-%m-%d' ) ) <![CDATA[ <= ]]> 180  " +
            " ) p" +
            "</script>")
    Integer queryWorkCountByManagerId(String managerId, Integer month);

    @Select("<script>" +
            "SELECT " +
            " *  " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  pom.id, " +
            "  pom.PAYMENT_TYPE_NAME AS name, " +
            "  pom.MONEY, " +
            "  pom.EXPIRE_TIME " +
            " FROM " +
            "  payment_order_master pom  " +
            " WHERE " +
            "  pom.REGIONAL_MANAGER_ID = #{managerId} " +
            "  AND pom.DELETED = 0 " +
            "  AND pom.`STATUS` = 1 " +
            "  AND pom.EXAMINE = 1 " +
            "  AND pom.IS_PUBLISH = 1 " +
            "  AND DATEDIFF( pom.EXPIRE_TIME, DATE_FORMAT( NOW(), '%y-%m-%d' ) ) <![CDATA[ <= ]]> 180  " +
//            "UNION ALL " +
//            " SELECT " +
//            "  ID, " +
//            "  PAYMENT_TYPE_NAME AS name, " +
//            "  MONEY, " +
//            "  EXPIRE_TIME " +
//            " FROM " +
//            "  agent_area_payment_order_master t1  " +
//            " WHERE " +
//            "  t1.REGIONAL_MANAGER_ID = #{managerId}  " +
//            "  AND t1.DELETED = 0  " +
//            "  AND t1.`STATUS` = 1 " +
//            "  AND t1.EXAMINE = 1 " +
            " ) p" +
            " ORDER BY p.EXPIRE_TIME " +
            " LIMIT 1" +
            "</script>")
    IndexOrderVo queryNewestWorkInfoByManagerId(String managerId);

    @Select("SELECT " +
            " ID, " +
            " MERCHANT_STORE_UID uid, " +
            " MERCHANT_NAME, " +
            " PAYMENT_TYPE_NAME paymentType, " +
            " MONEY, " +
            " PAY_MONEY, " +
            " ( MONEY -  PAY_MONEY ) remain, " +
            " `STATUS` payStatus " +
            " FROM " +
            " payment_order_master t1 " +
            " WHERE " +
            " ID = #{id}")
    PayShareInfoVO getMerchantOrderById(Integer id);

    @Select("SELECT " +
            " ID, " +
            " UID, " +
            " MERCHANT_NAME, " +
            " PAYMENT_TYPE_NAME paymentType, " +
            " MONEY, " +
            " PAY_MONEY, " +
            " ( MONEY -  PAY_MONEY ) remain, " +
            " `STATUS` payStatus  " +
            "FROM " +
            " agent_area_payment_order_master t1  " +
            "WHERE " +
            " ID = #{id}")
    PayShareInfoVO getAgentOrderById(Integer id);
}