package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.PaymentOrderDTO;
import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentAreaRegionalManager;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MerchantAgentAreaRegionalManagerMapper extends MyBaseMapper<MerchantAgentAreaRegionalManager> {


    @Select("SELECT  " +
            "  *   " +
            "FROM  " +
            "  merchant_agent_area_regional_manager   " +
            "WHERE  " +
            "  MERCHANT_AGENT_AREA_ID = #{id}  " +
            "  AND `STATUS` = 1")
    MerchantAgentAreaRegionalManager queryByMerchantAgentAreaId(Integer id);

    @Update("UPDATE merchant_agent_area_regional_manager SET `STATUS` = 2 WHERE MERCHANT_AGENT_AREA_ID=#{id}")
    void updateById(Integer id);

    /**
     * 查询原区域经理
     *
     * @param areaId
     * @return
     */
    @Select("SELECT " +
            " t3.ID, " +
            " t3.`NAME`, " +
            " t3.MOBILE  " +
            "FROM " +
            " merchant_agent_area t1 " +
            " LEFT JOIN merchant_agent_area_regional_manager t2 ON t1.ID = t2.MERCHANT_AGENT_AREA_ID " +
            " LEFT JOIN regional_manager t3 ON t2.REGIONAL_MANAGER_ID = t3.ID  " +
            "WHERE " +
            " t3.`STATUS` = 1  " +
            " AND t2.`STATUS` = 1  " +
            " AND t1.ID = #{areaId}  " +
            " LIMIT 1")
    RegionalManager getRegionalManager(Integer areaId);

    /**
     * 获取代理权订单
     *
     * @param uid
     * @return
     */
    @Select("SELECT ID,`STATUS` FROM agent_area_payment_order_master WHERE UID = #{uid}")
    List<PaymentOrderDTO> listAgentOrder(String uid);

    /**
     * 更新代理缴费表区域经理
     *
     * @param managerId
     * @param managerName
     * @param mobile
     * @param orderId
     */
    @Update("UPDATE agent_area_payment_order_master SET REGIONAL_MANAGER_ID = #{managerId} ,REGIONAL_MANAGER_NAME = #{managerName},REGIONAL_MANAGER_MOBILE = #{mobile} WHERE ID = #{orderId}")
    void updateOrderManager(Integer managerId, String managerName, String mobile, Integer orderId);

    /**
     * 区域经理关联修改为不可用
     *
     * @param managerId
     * @param areaId
     */
    @Update("UPDATE merchant_agent_area_regional_manager SET `STATUS` = 2 WHERE REGIONAL_MANAGER_ID = #{managerId} AND MERCHANT_AGENT_AREA_ID = #{areaId} AND `STATUS` = 1")
    void updateDetailStatus(Integer managerId, Integer areaId);
}