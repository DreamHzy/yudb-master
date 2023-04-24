package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.dto.PlatformPageDTO;
import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderMaster;

import java.util.List;

import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

public interface AgentAreaPaymentOrderMasterMapper extends MyBaseMapper<AgentAreaPaymentOrderMaster> {
    /**
     * 缴费记录
     *
     * @param merchantId
     * @return
     */
    @Select("SELECT " +
            "  ID, " +
            "  UID, " +
            "  PAYMENT_TYPE_NAME AS payTypeName, " +
            "  PROVINCE, " +
            "  CITY, " +
            "  AREA," +
            "  MONEY, " +
            "  PAY_MONEY, " +
            "  ( MONEY - PAY_MONEY ) remain, " +
            "  REGIONAL_MANAGER_MOBILE mobile, " +
            "  EXPIRE_TIME, " +
            "  `STATUS`  " +
            "FROM " +
            "  agent_area_payment_order_master  " +
            "WHERE " +
            "  `STATUS` = 1  " +
            "  AND EXAMINE = 1  " +
            "  AND SEND = 1  " +
            "  AND DELETED = 0" +
            "  AND MERCHANT_ID = #{merchantId} " +
            "ORDER BY " +
            "  ID DESC  ")
    List<MerchantPlatformAgentVO> queryMerchantPlatformAgent(String merchantId);

    /**
     * 已缴费记录
     *
     * @param merchantId
     * @return
     */
    @Select("SELECT " +
            " ID, " +
            " UID, " +
            " MERCHANT_NAME, " +
            " CITY, " +
            " AREA," +
            " MONEY, " +
            " MONEY, " +
            " PAYMENT_TYPE_NAME payTypeName , " +
            " CREATE_TIME, " +
            " STATUS, " +
            " PAY_TIME  " +
            "FROM " +
            " agent_area_payment_order_master  " +
            "WHERE " +
            " MERCHANT_ID = #{merchantId} " +
            " AND `STATUS` = 2 " +
            " AND DELETED = 0")
    List<PaymentRecordMerchantVo> agentPaymentRecord(String merchantId);

    /**
     * 代理缴费记录
     *
     * @param merchantId
     * @param platformPageDTO
     * @return
     */
    @Select("<script>" +
            "SELECT " +
            " t1.ID, " +
            " t1.ORDER_NO, " +
            " t1.UID, " +
            " t1.MERCHANT_NAME, " +
            " t1.MERCHANT_MOBILE mobile, " +
            " t1.PAYMENT_TYPE_NAME payTypeName, " +
            " t1.MONEY, " +
            " t1.PAY_MONEY, " +
            " ( t1.MONEY - t1.PAY_MONEY ) remain, " +
            " t1.CITY, " +
            " t1.AREA, " +
            " t1.`STATUS`, " +
            " t1.ADJUSTMENT_COUNT, " +
            "IF " +
            " ( t1.SEND = 1, '已推送', '未推送' ) send, " +
            " t1.EXPIRE_TIME, " +
            " t1.EXAMINE AS examineStatus, " +
            " ( SELECT poe.refuse FROM agent_area_payment_order_examine poe WHERE t1.ID = poe.PAYMENT_ORDER_MASTER_ID AND poe.DELETED = 0 ORDER BY CREATE_TIME DESC LIMIT 1 ) refuse," +
            " ( SELECT poe.MSG FROM agent_area_payment_order_examine poe   WHERE t1.ID = poe.PAYMENT_ORDER_MASTER_ID AND poe.DELETED = 0 ORDER BY CREATE_TIME DESC LIMIT 1  ) adjustment, " +
            " (SELECT CREATE_NAME FROM agent_area_payment_order_examine_deail t3 INNER JOIN agent_area_payment_order_examine t2 ON t3.PAYMENT_ORDER_EXAMINE_ID = t2.ID WHERE PAYMENT_ORDER_MASTER_ID = t1.ID  AND t3.DELETED = 0 AND t2.DELETED = 0 ORDER BY t3.ID DESC LIMIT 1) approveName " +
            "FROM " +
            " agent_area_payment_order_master t1 " +
            "WHERE " +
            " t1.REGIONAL_MANAGER_ID = #{merchantId} " +
            " AND t1.DELETED = 0  " +
            " <if test='platformPageDTO.type==0'>AND t1.`STATUS` = 1 AND ( t1.TYPE, t1.EXAMINE, t1.ADJUSTMENT_COUNT ) NOT IN ( ( 3, 2 ,0 ) , ( 1, 2 ,0 ) ) </if> " +
            " <if test='platformPageDTO.type==1'>AND t1.`STATUS` = 1 AND t1.ADJUSTMENT_COUNT = 0 AND t1.EXAMINE = 1</if>" +
            " <if test='platformPageDTO.type==2'>AND t1.`STATUS` = 1 AND t1.ADJUSTMENT_COUNT &gt; 0 AND t1.EXAMINE = 1</if> " +
            " <if test='platformPageDTO.type==3'>AND t1.`STATUS` = 1 AND ( t1.TYPE, t1.EXAMINE, t1.ADJUSTMENT_COUNT ) NOT IN ( ( 3, 2 ,0 ) , ( 1, 2 ,0 ) ) AND t1.EXAMINE = 2</if>" +
            " <if test='platformPageDTO.conditions != null'> AND (t1.UID = #{platformPageDTO.conditions} OR t1.MERCHANT_ID = #{platformPageDTO.conditions} OR t1.PAYMENT_TYPE_NAME = #{platformPageDTO.conditions}) </if>" +
            "   ORDER BY t1.EXPIRE_TIME DESC</script> ")
    List<RegionalManagerAgentPlatformPageVO> agentPlatformPage(String merchantId, @RequestParam("platformPageDTO") PlatformPageDTO platformPageDTO);

    /**
     * 代理缴费记录
     *
     * @param merchantId
     * @param platformPageDTO
     * @return
     */
    @Select("<script>" +
            "SELECT " +
            " t2.id, " +
            " t1.ORDER_NO, " +
            " t1.MERCHANT_NAME, " +
            " t1.MERCHANT_MOBILE mobile, " +
            " t1.UID, " +
            " t1.PAYMENT_TYPE_NAME payTypeName, " +
            " t1.MONEY, " +
            " t1.PAY_MONEY, " +
            " ( t1.MONEY - t1.PAY_MONEY ) remain, " +
            " t1.ADJUSTMENT_COUNT, " +
            " t2.`STATUS`  examineStatus, " +
            " t1.EXPIRE_TIME, " +
            " t1.PROVINCE, " +
            " t1.CITY, " +
            " t1.AREA, " +
            " t2.`STATUS`, " +
            " t2.CREATE_TIME, " +
            " t2.EXAMINE_TYPE, " +
            " t2.MSG, " +
            " t2.REMARK,  " +
            " t2.DELETED  " +
            "FROM " +
            " agent_area_payment_order_master t1 " +
            " LEFT JOIN agent_area_payment_order_examine t2 ON t1.ID = t2.PAYMENT_ORDER_MASTER_ID  " +
            "WHERE " +
            " t1.REGIONAL_MANAGER_ID = #{merchantId}  " +
            " AND t1.DELETED = 0  " +
//            " AND t2.DELETED = 0  " +
            " AND t1.`STATUS` = 1  " +
            " AND t2.EXAMINE_TYPE != 3  " +
            " AND t1.ADJUSTMENT_COUNT &gt; 0  " +
//            " AND t1.EXAMINE = 1  " +
            " <if test='platformPageDTO.conditions != null'> AND (t1.UID = #{platformPageDTO.conditions} OR t1.MERCHANT_ID = #{platformPageDTO.conditions} OR t1.PAYMENT_TYPE_NAME = #{platformPageDTO.conditions}) </if>" +
            "ORDER BY " +
            " t2.ID DESC</script> ")
    List<RegionalManagerAgentPlatformPageVO> agentPlatformPage2(String merchantId, @RequestParam("platformPageDTO") PlatformPageDTO platformPageDTO);

    @Select("SELECT " +
            " t2.USER_NAME  " +
            "FROM " +
            " agent_area_payment_order_examine t1 " +
            " INNER JOIN approve_process_step t2 ON t1.STEP = t2.STEP " +
            " INNER JOIN approve_process t3 ON t1.EXAMINE_TYPE = t3.EXAMINE_TYPE  " +
            " AND t2.APPROVE_ID = t3.ID  " +
            " INNER JOIN agent_area_payment_order_master t4 ON t1.PAYMENT_ORDER_MASTER_ID = t4.ID " +
            "WHERE " +
            " t3.TYPE = 2  " +
            " AND t1.PAYMENT_ORDER_MASTER_ID = #{id} " +
            " AND t2.`STATUS` = 1 " +
            " AND t3.`STATUS` = 1" +
            " AND t1.DELETED = 0" +
            " AND t2.TYPE = ( " +
            " SELECT " +
            "  TYPE  " +
            " FROM " +
            "  agent_area_payment_order_examine_deail  " +
            " WHERE " +
            "  PAYMENT_ORDER_EXAMINE_ID = t1.ID  " +
            " ORDER BY " +
            "  ID DESC  " +
            " LIMIT 1  " +
            " )" +
            " ORDER BY t1.ID DESC LIMIT 1")
    List<String> getApproveName(Integer id);


    @Select("SELECT*FROM agent_area_payment_order_master WHERE id  =#{id} ")
    AgentAreaPaymentOrderMaster queryByOrderNO(String s);

    /**
     * 区域经理名店和代理待办
     *
     * @param managerId
     * @param type
     * @return
     */
    @Select("<script>" +
            "SELECT " +
            " *  " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  pom.id, " +
            "  pom.ORDER_NO, " +
            "  pom.MERCHANT_NAME, " +
            "  pom.MERCHANT_STORE_UID uid, " +
            "  pom.PAYMENT_TYPE_NAME payTypeName, " +
            "  pom.MONEY, " +
            "  pom.PAY_MONEY, " +
            "  ( pom.MONEY - pom.PAY_MONEY ) remain, " +
            "  pom.MERCHANT_STORE_MOBILE mobile, " +
            "  pom.`STATUS`, " +
            "  IF( pom.SEND = 1, '已推送', '未推送' ) send, " +
            "  pom.EXPIRE_TIME, " +
            "  DATEDIFF( DATE_FORMAT( NOW(), '%y-%m-%d' ), pom.EXPIRE_TIME ) overdueDay, " +
            "  pom.EXAMINE examineStatus, " +
            "  pom.NATURAL_YEAR_END, " +
            "  ms.PROVINCE, " +
            "  ms.CITY, " +
            "  ms.AREA, " +
            "  ms.ADDRESS, " +
            "  \"门店\" AS roleTypeName, " +
            "  ( " +
            "  SELECT " +
            "   poe.refuse  " +
            "  FROM " +
            "   payment_order_examine poe  " +
            "  WHERE " +
            "   pom.ID = poe.PAYMENT_ORDER_MASTER_ID  " +
            "   AND poe.DELETED = 0  " +
            "  ORDER BY " +
            "   CREATE_TIME DESC  " +
            "   LIMIT 1  " +
            "   ) refuse,( " +
            "  SELECT " +
            "   poe.MSG  " +
            "  FROM " +
            "   payment_order_examine poe  " +
            "  WHERE " +
            "   pom.ID = poe.PAYMENT_ORDER_MASTER_ID  " +
            "   AND poe.DELETED = 0  " +
            "  ORDER BY " +
            "   CREATE_TIME DESC  " +
            "   LIMIT 1  " +
            "  ) adjustment  " +
            " FROM " +
            "  payment_order_master pom  " +
            "  LEFT JOIN merchant_store ms ON pom.MERCHANT_STORE_ID = ms.ID  " +
            " WHERE " +
            "  pom.REGIONAL_MANAGER_ID = #{managerId} " +
            "  AND pom.DELETED = 0 " +
            "  AND pom.IS_PUBLISH = 1 " +
            "  AND pom.EXAMINE = 1 " +
            " <if test='type == 1'> AND pom.`STATUS` = 1 AND DATEDIFF( DATE_FORMAT( NOW(), '%y-%m-%d' ), pom.EXPIRE_TIME ) <![CDATA[ > ]]> 0 </if>" +
            " <if test='type == 2'> AND pom.`STATUS` = 1 AND DATEDIFF( pom.EXPIRE_TIME, DATE_FORMAT( NOW(), '%y-%m-%d' ) ) <![CDATA[ >= ]]> 0 AND DATEDIFF( pom.EXPIRE_TIME, DATE_FORMAT( NOW(), '%y-%m-%d' ) ) <![CDATA[ <= ]]> 30 </if>" +
            " <if test='type == 3'> AND pom.`STATUS` = 2 </if>" +
//            "UNION ALL " +
//            " SELECT " +
//            "  ID, " +
//            "  ORDER_NO, " +
//            "  MERCHANT_NAME, " +
//            "  UID, " +
//            "  PAYMENT_TYPE_NAME payTypeName, " +
//            "  MONEY, " +
//            "  PAY_MONEY, " +
//            "  ( MONEY - PAY_MONEY ) remain, " +
//            "  MERCHANT_MOBILE AS mobile, " +
//            "  `STATUS`, " +
//            " IF( SEND = 1, '已推送', '未推送' ) send, " +
//            "  EXPIRE_TIME, " +
//            "  DATEDIFF( DATE_FORMAT( NOW(), '%y-%m-%d' ), t1.EXPIRE_TIME ) overdueDay, " +
//            "  EXAMINE AS examineStatus, " +
//            "  NATURAL_YEAR_END, " +
//            "  PROVINCE, " +
//            "  CITY, " +
//            "  AREA, " +
//            "  \"\" AS addresss, " +
//            "  \"代理\" AS roleTypeName, " +
//            "  ( " +
//            "  SELECT " +
//            "   poe.refuse  " +
//            "  FROM " +
//            "   agent_area_payment_order_examine poe  " +
//            "  WHERE " +
//            "   t1.ID = poe.PAYMENT_ORDER_MASTER_ID  " +
//            "   AND poe.DELETED = 0  " +
//            "  ORDER BY " +
//            "   CREATE_TIME DESC  " +
//            "   LIMIT 1  " +
//            "  ) refuse, " +
//            "  ( " +
//            "  SELECT " +
//            "   poe.MSG  " +
//            "  FROM " +
//            "   agent_area_payment_order_examine poe  " +
//            "  WHERE " +
//            "   t1.ID = poe.PAYMENT_ORDER_MASTER_ID  " +
//            "   AND poe.DELETED = 0  " +
//            "  ORDER BY " +
//            "   CREATE_TIME DESC  " +
//            "   LIMIT 1  " +
//            "  ) adjustment  " +
//            " FROM " +
//            "  agent_area_payment_order_master t1  " +
//            " WHERE " +
//            "  t1.REGIONAL_MANAGER_ID = #{managerId}  " +
//            "  AND t1.DELETED = 0  " +
//            "  AND t1.EXAMINE = 1 " +
//            " <if test='type == 1'> AND t1.`STATUS` = 1 AND DATEDIFF( DATE_FORMAT( NOW(), '%y-%m-%d' ), t1.EXPIRE_TIME ) <![CDATA[ > ]]> 0 </if>" +
//            " <if test='type == 2'> AND t1.`STATUS` = 1 AND DATEDIFF( t1.EXPIRE_TIME, DATE_FORMAT( NOW(), '%y-%m-%d' ) ) <![CDATA[ >= ]]> 0 AND DATEDIFF( t1.EXPIRE_TIME, DATE_FORMAT( NOW(), '%y-%m-%d' ) ) <![CDATA[ <= ]]> 30 </if>" +
//            " <if test='type == 3'> AND t1.`STATUS` = 2 </if>" +
            " ) p" +
            " ORDER BY p.EXPIRE_TIME ASC</script>")
    List<RegionalManagerPlatformPageVo> regionalMyBacklog(String managerId, Integer type);

    @Select("<script>" +
            "SELECT " +
            " DATE_FORMAT( t1.EXPIRE_TIME, '%m' ) COLLECTION_MONTH, " +
            " COUNT(*) COUNT  " +
            "FROM " +
            " payment_order_master t1 " +
            " LEFT JOIN merchant_store t2 ON t1.MERCHANT_STORE_UID = t2.UID  " +
            "WHERE " +
            " REGIONAL_MANAGER_ID = #{managerId}  " +
            " AND t1.DELETED = 0  " +
            " AND t1.IS_PUBLISH = 1  " +
            " AND t1.`STATUS` = 1 " +
            " AND t1.EXAMINE = 1 " +
            " AND DATE_FORMAT( t1.EXPIRE_TIME, '%m' ) != #{thisMonth} " +
            " AND DATEDIFF( t1.EXPIRE_TIME, DATE_FORMAT( NOW(), '%y-%m-%d' ) ) <![CDATA[ >= ]]> 0  " +
            " AND DATEDIFF( t1.EXPIRE_TIME, DATE_FORMAT( NOW(), '%y-%m-%d' ) ) <![CDATA[ <= ]]> 180  " +
            " <if test='month != null'> AND DATE_FORMAT( t1.EXPIRE_TIME, '%m' ) = #{month} </if>" +
            "GROUP BY " +
            " DATE_FORMAT( t1.EXPIRE_TIME, '%m' )  " +
            "ORDER BY " +
            " DATE_FORMAT( t1.EXPIRE_TIME, '%m' ) ASC" +
            "</script>")
    List<FutureBacklogVO> listFutureBacklog(Integer managerId, Integer month, Integer thisMonth);

    @Select("<script>" +
            "SELECT " +
            " pom.id, " +
            " pom.ORDER_NO, " +
            " pom.MERCHANT_NAME, " +
            " pom.MERCHANT_STORE_UID uid, " +
            " pom.PAYMENT_TYPE_NAME payTypeName, " +
            " pom.MONEY, " +
            " pom.PAY_MONEY, " +
            " ( pom.MONEY - pom.PAY_MONEY ) remain, " +
            " pom.MERCHANT_STORE_MOBILE mobile, " +
            " pom.`STATUS`, " +
            "IF " +
            " ( pom.SEND = 1, '已推送', '未推送' ) send, " +
            " pom.EXPIRE_TIME, " +
            " DATEDIFF( DATE_FORMAT( NOW(), '%y-%m-%d' ), pom.EXPIRE_TIME ) overdueDay, " +
            " pom.EXAMINE examineStatus, " +
            " pom.NATURAL_YEAR_END, " +
            " ms.PROVINCE, " +
            " ms.CITY, " +
            " ms.AREA, " +
            " ms.ADDRESS, " +
            " '门店' AS roleTypeName, " +
            " ( " +
            " SELECT " +
            "  poe.refuse  " +
            " FROM " +
            "  payment_order_examine poe  " +
            " WHERE " +
            "  pom.ID = poe.PAYMENT_ORDER_MASTER_ID  " +
            "  AND poe.DELETED = 0  " +
            " ORDER BY " +
            "  CREATE_TIME DESC  " +
            "  LIMIT 1  " +
            "  ) refuse,( " +
            " SELECT " +
            "  poe.MSG  " +
            " FROM " +
            "  payment_order_examine poe  " +
            " WHERE " +
            "  pom.ID = poe.PAYMENT_ORDER_MASTER_ID  " +
            "  AND poe.DELETED = 0  " +
            " ORDER BY " +
            "  CREATE_TIME DESC  " +
            "  LIMIT 1  " +
            " ) adjustment  " +
            "FROM " +
            " payment_order_master pom " +
            " LEFT JOIN merchant_store ms ON pom.MERCHANT_STORE_ID = ms.ID  " +
            "WHERE " +
            " pom.REGIONAL_MANAGER_ID = #{managerId}  " +
            " AND pom.DELETED = 0  " +
            " AND pom.IS_PUBLISH = 1  " +
            " AND DATE_FORMAT( pom.EXPIRE_TIME, '%m' ) = #{month} " +
            " AND pom.`STATUS` = 1 " +
            " AND pom.EXAMINE = 1 " +
            " AND DATEDIFF( pom.EXPIRE_TIME, DATE_FORMAT( NOW(), '%y-%m-%d' ) ) <![CDATA[ >= ]]> 0  " +
            "</script>")
    List<RegionalManagerPlatformPageVo> listFutureBacklogOrder(Integer managerId, Integer month);

    /**
     * 代理我的待办
     *
     * @param merchantId
     * @return
     */
    @Select("<script>SELECT " +
            " *  " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  pom.ID, " +
            "  pom.MERCHANT_NAME, " +
            "  pom.MERCHANT_STORE_UID uid, " +
            "  pom.PROVINCE, " +
            "  pom.CITY, " +
            "  pom.AREA, " +
            "  ms.ADDRESS, " +
            "  \"门店\" roleTypeName, " +
            "  pom.PAYMENT_TYPE_NAME payTypeName, " +
            "  pom.MONEY, " +
            "  pom.PAY_MONEY, " +
            "  ( pom.MONEY - pom.PAY_MONEY ) remain, " +
            "  pom.REGIONAL_MANAGER_MOBILE mobile, " +
            "  pom.EXPIRE_TIME, " +
            "  pom.ADJUSTMENT_COUNT, " +
            "  pom.`STATUS`  " +
            " FROM " +
            "  payment_order_master pom " +
            "  LEFT JOIN merchant_store ms ON pom.MERCHANT_STORE_ID = ms.ID  " +
            " WHERE " +
            " <if test='type==1'> pom.`STATUS` = 1  " +
            "  AND pom.EXAMINE = 1  " +
            "  AND pom.SEND = 1 </if> " +
            " <if test='type==2'> pom.`STATUS` = 2 </if>" +
            "  AND pom.MERCHANT_ID = #{merchantId} UNION ALL " +
            " SELECT " +
            "  ID, " +
            "  MERCHANT_NAME, " +
            "  UID, " +
            "  PROVINCE, " +
            "  CITY, " +
            "  AREA, " +
            "  \"\" AS address, " +
            "  \"代理\" roleTypeName, " +
            "  PAYMENT_TYPE_NAME AS payTypeName, " +
            "  MONEY, " +
            "  PAY_MONEY, " +
            "  ( MONEY - PAY_MONEY ) remain, " +
            "  REGIONAL_MANAGER_MOBILE mobile, " +
            "  EXPIRE_TIME, " +
            "  ADJUSTMENT_COUNT, " +
            "  `STATUS`  " +
            " FROM " +
            "  agent_area_payment_order_master  " +
            " WHERE " +
            " <if test='type==1'> `STATUS` = 1  " +
            "  AND EXAMINE = 1  " +
            "  AND SEND = 1 </if> " +
            " <if test='type==2'> `STATUS` = 2 </if>" +
            "  AND DELETED = 0 " +
            "  AND MERCHANT_ID = #{merchantId}  " +
            " ) p  " +
            "ORDER BY " +
            " p.EXPIRE_TIME DESC</script>")
    List<MerchantPlatformPageVo> agentMyBacklog(Integer merchantId, Integer type);

    /**
     * 区域经理缴费记录
     *
     * @param managerId
     * @return
     */
    @Select("SELECT " +
            " ID, " +
            " UID, " +
            " MERCHANT_NAME, " +
            " CITY, " +
            " AREA," +
            " MONEY, " +
            " MONEY, " +
            " PAYMENT_TYPE_NAME AS payTypeName, " +
            " CREATE_TIME, " +
            " STATUS, " +
            " PAY_TIME " +
            "FROM " +
            " agent_area_payment_order_master  " +
            "WHERE " +
            " REGIONAL_MANAGER_ID = #{managerId} " +
            " AND `STATUS` = 2 " +
            " AND DELETED = 0")
    List<PaymentRecordMerchantVo> regionalPaymentRecord(String managerId);
}