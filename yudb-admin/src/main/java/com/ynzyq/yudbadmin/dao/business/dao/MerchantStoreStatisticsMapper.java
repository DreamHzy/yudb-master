package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreStatistics;

import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantStoreStatisticsMapper extends MyBaseMapper<MerchantStoreStatistics> {

    /**
     * 查询日期
     *
     * @return
     */
    @Select("SELECT ID,STATISTICS_DATE FROM merchant_store_statistics WHERE `STATUS` = 1 ORDER BY ID DESC")
    List<ListStatementVO> listStatement();

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @Select("SELECT " +
            " ID, " +
            " STATISTICS_DATE, " +
            " STORE_COUNT, " +
            " AGENT_AREA_COUNT, " +
            " MANAGE_COUNT, " +
            " BUSINESS_COUNT, " +
            " NOT_OPEN_COUNT, " +
            " RELOCATION_COUNT, " +
            " CLOSE_COUNT, " +
            " PAUSE_COUNT, " +
            " NORTH_CHINA_COUNT, " +
            " EAST_CHINA_COUNT, " +
            " SOUTH_CHINA_COUNT, " +
            " WEST_CHINA_COUNT, " +
            " CENTER_CHINA_COUNT, " +
            " NEW_FIRST_TIER_COUNT, " +
            " FIRST_TIER_COUNT, " +
            " SECOND_TIER_COUNT, " +
            " THIRD_TIER_COUNT, " +
            " FOUR_TIER_COUNT, " +
            " FIVE_TIER_COUNT, " +
            " COUNTY_COUNT, " +
            " HKMT_COUNT, " +
            " CUSTOMER_COUNT, " +
            " ADD_CUSTOMER_COUNT, " +
            " LOGIN_CUSTOMER_COUNT, " +
            " AGENT_VALID, " +
            " AGENT_INVALID, " +
            " ACCOUNT_RECEIVABLE_COUNT, " +
            " ACCOUNT_RECEIVABLE_MONEY, " +
            " ACTUAL_RECEIVABLE_COUNT, " +
            " ACTUAL_RECEIVABLE_MONEY, " +
            " NOT_RECEIVABLE_COUNT, " +
            " NOT_RECEIVABLE_MONEY, " +
            " SEND_COUNT, " +
            " SEND_MONEY, " +
            " NOT_SEND_COUNT, " +
            " NOT_SEND_MONEY, " +
            " STATUS, " +
            " CREATE_TIME  " +
            "FROM " +
            " MERCHANT_STORE_STATISTICS  " +
            "WHERE " +
            " ID = #{id} " +
            " AND `STATUS` = 1")
    StatementDetailVO statementDetail(Integer id);

    /**
     * 门店数量
     *
     * @return
     */
    @Select("SELECT " +
            " COUNT(*) STORE_COUNT, " +
            " COUNT(CASE WHEN `STATUS` IN(2,11) THEN ID END) MANAGE_COUNT " +
            "FROM " +
            " merchant_store")
    StoreCountDTO storeCount();

    /**
     * 代理权数
     *
     * @return
     */
    @Select("SELECT " +
            " COUNT(*)  " +
            "FROM " +
            " merchant_agent_area")
    int agentCount();

    /**
     * 门店各状态数量
     *
     * @return
     */
    @Select("SELECT " +
            " COUNT( CASE WHEN `STATUS` = 2 THEN ID END ) BUSINESS_COUNT, " +
            " COUNT( CASE WHEN `STATUS` = 3 THEN ID END ) NOT_OPEN_COUNT, " +
            " COUNT( CASE WHEN `STATUS` = 10 THEN ID END ) RELOCATION_COUNT, " +
            " COUNT( CASE WHEN `STATUS` = 9 THEN ID END ) CLOSE_COUNT, " +
            " COUNT( CASE WHEN `STATUS` = 11 THEN ID END ) PAUSE_COUNT  " +
            "FROM " +
            " merchant_store")
    StoreStatusCountDTO storeStatusCount();

    /**
     * 区域名店数
     *
     * @return
     */
    @Select("SELECT " +
            " t2.REGION, " +
            " COUNT(*) count  " +
            "FROM " +
            " merchant_store t1 " +
            " LEFT JOIN merchant_store_mapping_area t2 ON t2.PROVINCE = t1.PROVINCE  " +
            " AND t2.CITY = t1.CITY  " +
            " AND t2.AREA = t1.AREA  " +
            "GROUP BY " +
            " t2.REGION")
    List<RegionStoreCountDTO> regionStoreCount();

    /**
     * 线级城市门店数
     *
     * @return
     */
    @Select("SELECT " +
            " t2.`LEVEL`, " +
            " COUNT(*) count  " +
            "FROM " +
            " merchant_store t1 " +
            " LEFT JOIN merchant_store_mapping_area t2 ON t2.PROVINCE = t1.PROVINCE  " +
            " AND t2.CITY = t1.CITY  " +
            " AND t2.AREA = t1.AREA  " +
            "GROUP BY " +
            " t2.`LEVEL`")
    List<LevelStoreCountDTO> levelStoreCount();

    /**
     * 门店和代理账单
     *
     * @return
     */
    @Select("SELECT " +
            " SUM( ACCOUNT_RECEIVABLE_COUNT ) ACCOUNT_RECEIVABLE_COUNT, " +
            " SUM( ACCOUNT_RECEIVABLE_MONEY ) ACCOUNT_RECEIVABLE_MONEY, " +
            " SUM( ACTUAL_RECEIVABLE_COUNT ) ACTUAL_RECEIVABLE_COUNT, " +
            " SUM( ACTUAL_RECEIVABLE_MONEY ) ACTUAL_RECEIVABLE_MONEY, " +
            " SUM( SEND_COUNT ) SEND_COUNT, " +
            " SUM( SEND_MONEY ) SEND_MONEY, " +
            " SUM( NOT_SEND_COUNT ) NOT_SEND_COUNT, " +
            " SUM( NOT_SEND_MONEY ) NOT_SEND_MONEY  " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  COUNT( CASE WHEN `STATUS` != 3 THEN ID END ) ACCOUNT_RECEIVABLE_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` != 3 THEN MONEY END ), 0 ) ACCOUNT_RECEIVABLE_MONEY, " +
            "  COUNT( CASE WHEN `STATUS` = 2 THEN ID END ) ACTUAL_RECEIVABLE_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` = 2 THEN MONEY END ), 0 ) ACTUAL_RECEIVABLE_MONEY, " +
            "  COUNT( CASE WHEN `STATUS` != 3 AND SEND = 1 THEN ID END ) SEND_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` != 3 AND SEND = 1 THEN MONEY END ), 0 ) SEND_MONEY, " +
            "  COUNT( CASE WHEN `STATUS` != 3 AND SEND = 2 THEN ID END ) NOT_SEND_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` != 3 AND SEND = 2 THEN MONEY END ), 0 ) NOT_SEND_MONEY  " +
            " FROM " +
            "  payment_order_master " +
            " WHERE " +
            "  EXAMINE = 1  " +
            "  AND `STATUS` NOT IN ( 3, 4 )  " +
            "  AND DELETED = 0" +
            " UNION ALL " +
            " SELECT " +
            "  COUNT( CASE WHEN `STATUS` != 3 THEN ID END ) ACCOUNT_RECEIVABLE_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` != 3 THEN MONEY END ), 0 ) ACCOUNT_RECEIVABLE_MONEY, " +
            "  COUNT( CASE WHEN `STATUS` = 2 THEN ID END ) ACTUAL_RECEIVABLE_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` = 2 THEN MONEY END ), 0 ) ACTUAL_RECEIVABLE_MONEY, " +
            "  COUNT( CASE WHEN `STATUS` != 3 AND SEND = 1 THEN ID END ) SEND_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` != 3 AND SEND = 1 THEN MONEY END ), 0 ) SEND_MONEY, " +
            "  COUNT( CASE WHEN `STATUS` != 3 AND SEND = 2 THEN ID END ) NOT_SEND_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` != 3 AND SEND = 2 THEN MONEY END ), 0 ) NOT_SEND_MONEY  " +
            " FROM " +
            "  agent_area_payment_order_master  " +
            " WHERE " +
            "  EXAMINE = 1  " +
            "  AND `STATUS` NOT IN ( 3, 4 )  " +
            "  AND DELETED = 0" +
            " ) p")
    StoreBillDTO storeBill();

    /**
     * 门店和代理已支付账单
     *
     * @return
     */
    @Select("SELECT " +
            " SUM( ACTUAL_RECEIVABLE_COUNT ) ACTUAL_RECEIVABLE_COUNT, " +
            " SUM( ACTUAL_RECEIVABLE_MONEY ) ACTUAL_RECEIVABLE_MONEY  " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  COUNT( CASE WHEN `STATUS` = 3 THEN ID END ) ACTUAL_RECEIVABLE_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` = 3 THEN TOTAL_MONEY END ), 0 ) ACTUAL_RECEIVABLE_MONEY  " +
            " FROM " +
            "  payment_order_pay UNION ALL " +
            " SELECT " +
            "  COUNT( CASE WHEN `STATUS` = 3 THEN ID END ) ACTUAL_RECEIVABLE_COUNT, " +
            "  IFNULL( SUM( CASE WHEN `STATUS` = 3 THEN TOTAL_MONEY END ), 0 ) ACTUAL_RECEIVABLE_MONEY  " +
            " FROM " +
            " agent_area_payment_order_pay  " +
            " ) p")
    StorePayDTO storePay();

    /**
     * 客户情况
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("SELECT " +
            " COUNT(*) CUSTOMER_COUNT, " +
            " COUNT(CASE WHEN CREATE_TIME BETWEEN #{startTime} AND #{endTime} THEN ID END) ADD_CUSTOMER_COUNT " +
            "FROM " +
            " merchant")
    CustomerDTO customer(String startTime, String endTime);

    /**
     * 登录数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("SELECT " +
            " IFNULL( SUM( IF ( num > 1, 1, num )), 0 ) num " +
            "FROM " +
            " ( SELECT COUNT(*) num FROM merchant_login_record WHERE LOGIN_TIME BETWEEN #{startTime} AND #{endTime} GROUP BY MERCHANT_ID )p")
    int loginCount(String startTime, String endTime);

    /**
     * 代理权分析
     *
     * @return
     */
    @Select("SELECT " +
            " COUNT(*) AGENT_AREA_COUNT," +
            " COUNT( CASE WHEN IS_EFFECT = 1 THEN ID END ) AGENT_VALID, " +
            " COUNT( CASE WHEN IS_EFFECT = 2 THEN ID END ) AGENT_INVALID  " +
            "FROM " +
            " merchant_agent_area")
    AgencyDTO agency();

    /**
     * 门店数和代理权数
     *
     * @return
     */
    @Select("SELECT " +
            " ID, " +
            " ( SELECT COUNT( * ) FROM merchant_store ms WHERE ms.MERCHANT_ID = m.ID AND `STATUS` != 9 ) storeCount, " +
            " ( SELECT COUNT( * ) FROM merchant_agent_area ms WHERE ms.MERCHANT_ID = m.ID AND IS_EFFECT != 2 ) agentCount  " +
            "FROM " +
            " merchant m ")
    List<MerchantVO> merchantData();

    @Select("<script>" +
            "SELECT " +
            " p.TYPE, " +
            " p.REGION, " +
            " p.MANAGER_ID, " +
            " p.MANAGER_NAME, " +
            " p.PAYMENT_TYPE_ID, " +
            " p.PAYMENT_TYPE_NAME, " +
            " p.count, " +
            " p.payCount, " +
            " p.amount, " +
            " p.payAmount " +
            "FROM " +
            " (" +
            "SELECT " +
            " '门店' AS TYPE, " +
            " t2.REGION, " +
            " t1.REGIONAL_MANAGER_ID MANAGER_ID, " +
            " t1.REGIONAL_MANAGER_NAME MANAGER_NAME, " +
            " t1.PAYMENT_TYPE_ID, " +
            " t1.PAYMENT_TYPE_NAME, " +
            " COUNT( t1.ID ) count, " +
            " COUNT( CASE WHEN t3.`STATUS` = 3 THEN t3.ID END ) payCount, " +
            " IFNULL( SUM( t1.MONEY ), 0 ) amount, " +
            " IFNULL( SUM( CASE WHEN t3.`STATUS` = 3 THEN t3.TOTAL_MONEY END ), 0 ) payAmount  " +
            "FROM " +
            " payment_order_master t1 " +
            " INNER JOIN merchant_store_mapping_area t2 ON t1.PROVINCE = t2.PROVINCE AND t1.CITY = t2.CITY AND t1.AREA = t2.AREA " +
            " LEFT JOIN payment_order_pay t3 ON t1.ID = t3.PAYMENT_ORDER_MASTER_ID AND t3.`STATUS` = 3 " +
            "WHERE " +
            " t1.EXAMINE = 1 " +
            " AND t1.`STATUS` NOT IN ( 3, 4 ) " +
            " AND t1.DELETED = 0 " +
            "GROUP BY " +
            " t2.REGION, " +
            " t1.REGIONAL_MANAGER_ID, " +
            " t1.REGIONAL_MANAGER_NAME, " +
            " t1.PAYMENT_TYPE_ID, " +
            " t1.PAYMENT_TYPE_NAME UNION ALL " +
            "SELECT " +
            " '代理' AS TYPE, " +
            " ( SELECT REGION FROM merchant_store_mapping_area WHERE MANAGER_ID = t1.REGIONAL_MANAGER_ID LIMIT 1 ) REGION, " +
            " t1.REGIONAL_MANAGER_ID AS MANAGER_ID, " +
            " t1.REGIONAL_MANAGER_NAME AS MANAGER_NAME, " +
            " t1.PAYMENT_TYPE_ID, " +
            " t1.PAYMENT_TYPE_NAME, " +
            " COUNT( t1.ID ) count, " +
            " COUNT( CASE WHEN t3.`STATUS` = 3 THEN t3.ID END ) payCount, " +
            " IFNULL( SUM( t1.MONEY ), 0 ) amount, " +
            " IFNULL( SUM( CASE WHEN t3.`STATUS` = 3 THEN t3.TOTAL_MONEY END ), 0 ) payAmount  " +
            "FROM " +
            " agent_area_payment_order_master t1 " +
            " LEFT JOIN agent_area_payment_order_pay t3 ON t1.ID = t3.PAYMENT_ORDER_MASTER_ID AND t3.`STATUS` = 3  " +
            "WHERE " +
            " t1.EXAMINE = 1 " +
            " AND t1.`STATUS` NOT IN ( 3, 4 ) " +
            " AND t1.DELETED = 0 " +
            "GROUP BY " +
            " REGION, " +
            " MANAGER_ID, " +
            " MANAGER_NAME, " +
            " t1.PAYMENT_TYPE_ID, " +
            " t1.PAYMENT_TYPE_NAME" +
            " ) p " +
            "<where>" +
            "<if test='condition != null'> AND (p.REGION LIKE concat('%',#{condition},'%') OR p.MANAGER_NAME LIKE concat('%',#{condition},'%'))</if>" +
            "<if test='typeId != null'> AND p.PAYMENT_TYPE_ID = #{typeId} </if>" +
            "<if test='type != null'> AND p.TYPE = #{type} </if>" +
            "</where>" +
            "</script>")
    List<ManageStatementVO> listManageStatement(ManageStatementDTO manageStatementDTO);

    @Select("<script>" +
            "SELECT " +
            " p.id, " +
            " p.TYPE, " +
            " p.REGION, " +
            " p.MANAGER_NAME, " +
            " p.PAYMENT_TYPE_NAME, " +
            " p.EXPIRE_TIME, " +
            " p.MONEY  " +
            "FROM " +
            " ( " +
            " SELECT " +
            "  t1.id, " +
            "  '门店' AS TYPE, " +
            "  t2.REGION, " +
            "  t1.REGIONAL_MANAGER_NAME AS MANAGER_NAME, " +
            "  t1.PAYMENT_TYPE_ID, " +
            "  t1.PAYMENT_TYPE_NAME, " +
            "  t1.EXPIRE_TIME, " +
            "  t1.MONEY  " +
            " FROM " +
            "  payment_order_master t1 " +
            "  INNER JOIN merchant_store_mapping_area t2 ON t1.PROVINCE = t2.PROVINCE  " +
            "  AND t1.CITY = t2.CITY  " +
            "  AND t1.AREA = t2.AREA  " +
            " WHERE " +
            "  t1.`STATUS` = 1  " +
            "  AND t1.EXAMINE = 1 " +
            "  AND t1.EXPIRE_TIME &lt; #{today} UNION ALL " +
            " SELECT " +
            "  t1.id, " +
            "  '代理' AS TYPE, " +
            "  ( SELECT REGION FROM merchant_store_mapping_area WHERE MANAGER_ID = t1.REGIONAL_MANAGER_ID LIMIT 1 ) REGION, " +
            "  t1.REGIONAL_MANAGER_NAME AS MANAGER_NAME, " +
            "  t1.PAYMENT_TYPE_ID, " +
            "  t1.PAYMENT_TYPE_NAME, " +
            "  t1.EXPIRE_TIME, " +
            "  t1.MONEY  " +
            " FROM " +
            "  agent_area_payment_order_master t1  " +
            " WHERE " +
            "  t1.`STATUS` = 1  " +
            "  AND t1.EXAMINE = 1 " +
            " AND t1.EXPIRE_TIME &lt; #{today}  " +
            " ) p" +
            "<where>" +
            "<if test='condition != null'> AND (p.REGION LIKE concat('%',#{condition},'%') OR p.MANAGER_NAME LIKE concat('%',#{condition},'%')) </if>" +
            "<if test='startTime != null and endTime != null'> AND p.EXPIRE_TIME BETWEEN #{startTime} AND #{endTime} </if>" +
            "<if test='typeId != null'> AND p.PAYMENT_TYPE_ID = #{typeId} </if>" +
            "<if test='type != null'> AND p.TYPE = #{type} </if>" +
            "</where>" +
            "</script>")
    List<ListOverdueOrderVO> listOverdueOrder(ListOverdueOrderDTO listOverdueOrderDTO);
}