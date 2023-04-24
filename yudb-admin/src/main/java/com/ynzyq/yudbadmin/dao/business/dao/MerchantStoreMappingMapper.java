package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.MerchantStoreRegionalManagerDTO;
import com.ynzyq.yudbadmin.dao.business.dto.PaymentOrderDTO;
import com.ynzyq.yudbadmin.dao.business.dto.StoreMappingDTO;
import com.ynzyq.yudbadmin.dao.business.vo.MerchantSelectBoxVO;
import com.ynzyq.yudbadmin.dao.business.vo.StoreMappingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author xinchen
 * @date 2021/11/18 11:14
 * @description:
 */
@Mapper
public interface MerchantStoreMappingMapper {
    /**
     * 查询门店基础信息映射
     *
     * @param storeMappingDTO
     * @return
     */
    @Select("<script>" +
            "SELECT  " +
            "  t1.ID,  " +
            "  t1.UID,  " +
            "  t1.AGENT_UID,  " +
            "  t1.REGION,  " +
            "  t1.PROVINCE,  " +
            "  t1.CITY,  " +
            "  t1.AREA,  " +
            "  t1.AGENT_ID,  " +
            "  t1.LEVEL ,  " +
            "  t2.`NAME` AS AGENT_NAME, " +
            "  t1.MANAGER_ID,  " +
            "  t3.`NAME` AS REGIONAL_NAME, " +
            "  t1.IS_MAPPING " +
//            "  t1.WAREHOUSE_ID, " +
//            "  ( SELECT `NAME` FROM yg_warehouse WHERE ID = t1.WAREHOUSE_ID AND `STATUS` = 1 ) warehouseName, " +
//            "  t1.LIMIT_CONFIG_ID, " +
//            "  ( SELECT `NAME` FROM yg_limit_config WHERE ID = t1.LIMIT_CONFIG_ID AND `STATUS` = 1 ) limitConfigName, " +
//            "  t1.RULE_ID, " +
//            "  ( SELECT `NAME` FROM yg_supplier_rule WHERE ID = t1.RULE_ID AND `STATUS` = 1 ) ruleName " +
            " FROM  " +
            "  merchant_store_mapping_area t1 " +
            "  LEFT JOIN merchant t2 ON t1.AGENT_ID = t2.ID " +
            "  LEFT JOIN regional_manager t3 ON t1.MANAGER_ID = t3.ID " +
            " <where>" +
            " <if test='condition != null'>( t1.PROVINCE LIKE concat('%',#{condition},'%') OR t1.CITY LIKE concat('%',#{condition},'%') OR t1.AREA LIKE concat('%',#{condition},'%') OR t1.MANAGER_NAME LIKE concat('%',#{condition},'%') )</if>" +
            "</where>" +
            "</script>")
    List<StoreMappingVO> listStoreMapping(StoreMappingDTO storeMappingDTO);


    /**
     * 查询所有代理商
     *
     * @return
     */
    @Select("SELECT ID,`NAME` FROM merchant WHERE `STATUS` = 1")
    List<MerchantSelectBoxVO> agentSelectBox();

    /**
     * 获取加盟店id
     *
     * @param province
     * @param city
     * @param area
     * @return
     */
    @Select("SELECT ID FROM merchant_store WHERE PROVINCE LIKE concat('%',#{province},'%') AND CITY LIKE concat('%',#{city},'%') AND AREA LIKE concat('%',#{area},'%')")
    List<Integer> getStoreId(String province, String city, String area);

    /**
     * 更新商户所属代理
     *
     * @param merchantId
     * @param agentName
     * @param id
     */
    @Select("UPDATE merchant_store SET AGENT_ID = #{merchantId},AGENT_NAME = #{agentName} WHERE ID = #{id}")
    void updateStoreAgent(Integer merchantId, String agentName, Integer id);

    /**
     * 更新商户原所属区域经理为不可用
     *
     * @param storeId
     */
    @Select("UPDATE merchant_store_regional_manager SET `STATUS` = 2 WHERE MERCHANT_STORE_ID = #{storeId}")
    void updateStoreManager(Integer storeId);

    /**
     * 查询所有区域经理
     *
     * @return
     */
    @Select("SELECT ID,`NAME` FROM regional_manager WHERE `STATUS` = 1")
    List<MerchantSelectBoxVO> managerSelectBox();

    /**
     * 查询关联的区域经理
     *
     * @param province
     * @param city
     * @param area
     * @return
     */
    @Select("SELECT " +
            " t1.ID AS storeId, " +
//            " t2.ID, " +
            " t3.ID AS managerId, " +
            " t3.`NAME`  " +
            "FROM " +
            " merchant_store t1 " +
            " LEFT JOIN merchant_store_regional_manager t2 ON t1.ID = t2.MERCHANT_STORE_ID " +
            " LEFT JOIN regional_manager t3 ON t2.REGIONAL_MANAGER_ID = t3.id  " +
            "WHERE " +
            " t2.`STATUS` = 1  " +
            " AND t3.`STATUS` = 1 " +
            " AND t1.PROVINCE LIKE concat('%',#{province},'%') " +
            " AND t1.CITY LIKE concat('%',#{city},'%') " +
            " AND t1.AREA LIKE concat('%',#{area},'%') ")
    List<MerchantStoreRegionalManagerDTO> getManagerId(String province, String city, String area);

    /**
     * 获取门店订单
     *
     * @param storeId
     * @return
     */
    @Select("SELECT ID,`STATUS` FROM payment_order_master WHERE MERCHANT_STORE_ID = #{storeId}")
    List<PaymentOrderDTO> listStoreOrder(Integer storeId);

    /**
     * 更新门店缴费表区域经理
     *
     * @param managerId
     * @param managerName
     * @param mobile
     * @param orderId
     */
    @Update("UPDATE payment_order_master SET REGIONAL_MANAGER_ID = #{managerId} ,REGIONAL_MANAGER_NAME = #{managerName},REGIONAL_MANAGER_MOBILE = #{mobile} WHERE ID = #{orderId}")
    void updateOrderManager(Integer managerId, String managerName, String mobile, Integer orderId);

//    /**
//     * 更新代理缴费表区域经理
//     *
//     * @param managerId
//     * @param managerName
//     * @param mobile
//     * @param orderId
//     */
//    @Update("UPDATE agent_area_payment_order_master SET REGIONAL_MANAGER_ID = #{managerId} ,REGIONAL_MANAGER_NAME = #{managerName},REGIONAL_MANAGER_MOBILE = #{mobile} WHERE ID = #{orderId}")
//    void updateAgentOrderManager(Integer managerId, String managerName, String mobile, Integer orderId);
}
