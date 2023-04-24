package com.ynzyq.yudbadmin.api.excel.mapper;

import com.ynzyq.yudbadmin.api.excel.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.MerchantVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Mapper
public interface ExcelMapper {
    /**
     * 获取所有代理商名字
     *
     * @return
     */
    @Select("SELECT `NAME` FROM merchant order by ID")
    List<String> getAllMerchantName();

    /**
     * 获取所有代理商手机号
     *
     * @return
     */
    @Select("SELECT MOBILE FROM merchant order by ID")
    List<String> getAllMerchantMobile();

    /**
     * 获取加盟商id
     *
     * @return
     */
    @Select("SELECT ID FROM merchant  order by ID")
    List<Integer> getAllMerchantId();

    /**
     * 获取区域经理id
     *
     * @return
     */
    @Select("SELECT ID FROM regional_manager order by ID")
    List<String> getRegionalManagerId();

    /**
     * 获取区域经理名字
     *
     * @return
     */
    @Select("SELECT `NAME` FROM regional_manager order by ID")
    List<String> getRegionalManagerName();

    @Select("SELECT * FROM merchant WHERE `NAME`= #{name} LIMIT 1")
    Merchant getMerchant(String name);

    @Select("SELECT * FROM merchant Order by id")
    List<Merchant> getAllMerchant();

    @Select("SELECT * FROM regional_manager Order by id")
    List<RegionalManager> getAllRegional();

    @Select("SELECT id FROM merchant_agent_area WHERE PROVINCE= #{p1} AND CITY = #{p2} AND AREA = #{p3}")
    Integer getMerchantAreaId(String p1, String p2, String p3);

    @Select("SELECT id,UID FROM merchant_store ORDER BY id")
    List<MerchantStore> getAllMerchantStore();

    @Select("SELECT * FROM merchant_agent_area ORDER BY id")
    List<MerchantAgentArea> getAllArea();

    @Select("SELECT * FROM merchant_agent_area_regional_manager ORDER BY id")
    List<MerchantAgentAreaRegionalManager> getAllAreaRegional();

    @Select("SELECT * FROM merchant_store_cloud_school ORDER BY id")
    List<MerchantStoreCloudSchool> getAllCloudSchool();

    @Select("SELECT PROVINCE AS value,PROVINCE AS label FROM merchant_store_mapping_area GROUP BY PROVINCE")
    List<ProvinceDTO> listProvince();

    @Select("SELECT CITY AS value,CITY AS label  FROM merchant_store_mapping_area WHERE PROVINCE = #{province} GROUP BY CITY")
    List<CityDTO> listCity(String province);

    @Select("SELECT AREA AS value,AREA AS label FROM merchant_store_mapping_area WHERE PROVINCE = #{province} AND CITY = #{city}")
    List<AreaDTO> listArea(String province,String city);

    @Update("UPDATE merchant_store SET PROVINCE = #{province},CITY = #{city},AREA = #{area} WHERE ID = #{id}")
    void updateStoreArea(String province, String city, String area, Integer id);

    @Select("SELECT MERCHANT_STORE_UID AS uid,OTHER_ID,COUNT(*) num FROM payment_order_master WHERE PAYMENT_TYPE_ID = 3 GROUP BY MERCHANT_STORE_UID,OTHER_ID HAVING num>1")
//    @Select("SELECT MERCHANT_STORE_UID AS uid,OTHER_ID FROM payment_order_master WHERE OTHER_ID < 266")
    List<SchoolDTO> listOrder();

    @Select("SELECT * FROM merchant_store_cloud_school WHERE MERCHANT_STORE_UID = #{uid} AND `STATUS` = 1")
    List<Integer> listSchool(String uid);

    @Update("UPDATE payment_order_master SET OTHER_ID = #{id} WHERE OTHER_ID= #{oldId} AND MERCHANT_STORE_UID = #{uid} LIMIT 1")
    void updateOrder(Integer oldId, Integer id, String uid);

    @Select("SELECT CHANNEL_ORDER_NO AS orderNo FROM agent_area_payment_order_pay WHERE `STATUS` = 3 AND IS_SETTLE = 0")
    List<OrderDTO> listAgentOrder();

    @Select("SELECT CHANNEL_ORDER_NO AS orderNo FROM payment_order_pay WHERE `STATUS` = 3 AND IS_SETTLE = 0")
    List<OrderDTO> listStoreOrder();

    @Update("UPDATE agent_area_payment_order_pay SET IS_SETTLE = 1 WHERE CHANNEL_ORDER_NO = #{orderNo} AND IS_SETTLE = 0")
    void updateAgentOrder(String orderNo);

    @Update("UPDATE payment_order_pay SET IS_SETTLE = 1 WHERE CHANNEL_ORDER_NO = #{orderNo} AND IS_SETTLE = 0")
    void updateStoreOrder(String orderNo);

    @Update("UPDATE merchant_store_cloud_school SET MERCHANT_STORE_ID=#{storeId},END_TIME=#{endTime},CREATE_DATE=#{startTime} WHERE MERCHANT_STORE_UID=#{uid} AND ACCOUNT =#{account} ")
    void updateCloudSchool(Integer storeId, String endTime, String startTime, String uid, String account);

    @Select("SELECT * FROM merchant_store_mapping_area ORDER BY ID ASC")
    List<MerchantStoreMappingArea> listMappingArea();

    @Update("  <script>  " +
            "    <foreach collection=\"updateList\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">" +
            "        update merchant_store_mapping_area " +
            "        <set>" +
            "            LEVEL = #{item.level}" +
            "        </set>" +
            "        where id = #{item.id}" +
            "    </foreach> " +
            "</script>")
    void updateMappingArea(@Param("updateList") List<MerchantStoreMappingArea> updateList);


    @Update("  <script>  " +
            "    <foreach collection=\"updateList\" item=\"item\" index=\"index\" open=\"\" close=\"\" separator=\";\">" +
            "        update merchant_store " +
            "        <set>" +
            "            FRANCHISE_FEE = #{item.franchiseFee},MANAGEMENT_EXPENSE = #{item.managementExpense},BOND_MONEY = #{item.bondMoney}" +
            "        </set>" +
            "        where id = #{item.id}" +
            "    </foreach> " +
            "</script>")
    void updateStore(@Param("updateList") List<MerchantStore> updateList);

    @Select("SELECT " +
            " t1.UID, " +
            " (SELECT t3.`NAME` FROM merchant_store_regional_manager t2 INNER JOIN regional_manager t3 ON t2.REGIONAL_MANAGER_ID = t3.ID WHERE t2.`STATUS` = 1 AND t2.MERCHANT_STORE_ID = t1.ID) `NAME`, " +
            " t1.PROVINCE, " +
            " t1.CITY, " +
            " t1.AREA, " +
            " t1.ADDRESS, " +
            " t1.MANAGEMENT_EXPENSE, " +
            " t1.START_TIME, " +
            " t1.EXPIRE_TIME " +
            "FROM " +
            " merchant_store t1 " +
            "WHERE " +
            " t1.`STATUS` = 2")
    List<StoreOrderDTO> listStore();

    @Select("SELECT ID,`STATUS` FROM merchant")
    List<Merchant> listMerchant();

    @Select("SELECT " +
            " ( SELECT COUNT( * ) FROM merchant_store ms WHERE ms.MERCHANT_ID = m.ID AND `STATUS` != 9 ) storeCount, " +
            " ( SELECT COUNT( * ) FROM merchant_agent_area ms WHERE ms.MERCHANT_ID = m.ID AND IS_EFFECT != 2 ) agentCount  " +
            "FROM " +
            " merchant m  " +
            "WHERE " +
            " ID = #{id}")
    MerchantVO getMerchantData(Integer id);

    @Select("SELECT * FROM agent_area_payment_order_master WHERE UID = #{uid}")
    List<AgentAreaPaymentOrderMaster> listAgentOrderByUid(String uid);

    @Select("SELECT * FROM agent_area_payment_order_examine WHERE PAYMENT_ORDER_MASTER_ID = #{orderId} AND UID = #{uid}")
    List<AgentAreaPaymentOrderExamine> listAgentExamine(Integer orderId, String uid);

    @Delete("DELETE FROM agent_area_payment_order_examine_deail WHERE PAYMENT_ORDER_EXAMINE_ID = #{examineId}")
    void deleteAgentExamineDetail(Integer examineId);

    @Delete("DELETE FROM agent_area_payment_order_examine WHERE PAYMENT_ORDER_MASTER_ID = #{orderId}")
    void deleteAgentExamine(Integer orderId);

    @Delete("DELETE FROM agent_area_payment_order_master WHERE UID = #{uid}")
    void deleteAgentOrder(String uid);

    @Select("SELECT * FROM payment_order_examine WHERE STEP IS NOT NULL AND DELETED = 0")
    List<PaymentOrderExamine> listStoreExamine();

    @Select("SELECT * FROM payment_order_examine_deail WHERE PAYMENT_ORDER_EXAMINE_ID = #{examineId} AND DELETED = 0")
    List<PaymentOrderExamineDeail> listStoreExamineDetail(Integer examineId);

    @Update("UPDATE payment_order_examine_deail SET APPROVE_NAME = #{approveName} WHERE ID = #{detailId}")
    void updateStoreExamineDetail(String approveName, Integer detailId);

    @Select("SELECT * FROM agent_area_payment_order_examine WHERE STEP IS NOT NULL AND DELETED = 0")
    List<AgentAreaPaymentOrderExamine> listAgentExamine1();

    @Select("SELECT * FROM agent_area_payment_order_examine_deail WHERE PAYMENT_ORDER_EXAMINE_ID = #{examineId} AND DELETED = 0")
    List<AgentAreaPaymentOrderExamineDeail> listAgentExamineDetail(Integer examineId);

    @Update("UPDATE agent_area_payment_order_examine_deail SET APPROVE_NAME = #{approveName} WHERE ID = #{detailId}")
    void updateAgentExamineDetail(String approveName, Integer detailId);

    @Update("UPDATE payment_order_master SET EXPIRE_TIME = #{expireTime},UPDATE_TIME = NOW() WHERE ID = #{id} ")
    void updateExpireTime(String expireTime, Integer id);

    @Update("<script>  " +
            "    <foreach collection='updateList' item='item' index='index' open='' close='' separator=';'>" +
            "        update merchant_store " +
            "        <set>" +
            "            COLLECTION_MONTH = #{item.collectionMonth}" +
            "        </set>" +
            "        where id = #{item.id}" +
            "    </foreach> " +
            "</script>")
    void updateStoreCollectionMonth(@Param("updateList") List<MerchantStore> updateList);
}
