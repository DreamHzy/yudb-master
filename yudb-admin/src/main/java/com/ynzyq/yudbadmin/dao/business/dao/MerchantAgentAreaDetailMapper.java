package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentAreaDetail;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Update;

public interface MerchantAgentAreaDetailMapper extends MyBaseMapper<MerchantAgentAreaDetail> {

    /**
     * 代理区域修改为不可用
     *
     * @param areaId
     */
    @Update("UPDATE merchant_agent_area_detail SET `STATUS` = 2 WHERE AGENT_AREA_ID = #{areaId} AND `STATUS` = 1")
    void updateDetailStatus(Integer areaId);

    /**
     * 代理区域修改为不可用
     *
     * @param managerId
     * @param areaId
     */
    @Update("UPDATE merchant_agent_area_regional_manager SET `STATUS` = 2 WHERE REGIONAL_MANAGER_ID = #{managerId} AND MERCHANT_AGENT_AREA_ID = #{areaId} AND `STATUS` = 1")
    void updateManagerStatus(Integer managerId, Integer areaId);
}