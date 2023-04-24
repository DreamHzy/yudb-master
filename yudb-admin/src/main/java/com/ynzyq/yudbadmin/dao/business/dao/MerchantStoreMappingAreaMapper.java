package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreMappingArea;

import com.ynzyq.yudbadmin.dao.business.vo.LevelVO;
import com.ynzyq.yudbadmin.dao.business.vo.OrderGoodsSelectBoxVO;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MerchantStoreMappingAreaMapper extends MyBaseMapper<MerchantStoreMappingArea> {

    @Select("SELECT " +
            " `LEVEL`  " +
            "FROM " +
            " merchant_store_mapping_area  " +
            "GROUP BY " +
            " `LEVEL`")
    List<LevelVO> levelSelectBox();

    @Select("SELECT UID FROM  merchant_agent_area WHERE MERCHANT_ID = #{merchantId}")
    String getMerchantUid(Integer merchantId);

    @Select("SELECT " +
            " t1.UID  " +
            "FROM " +
            " merchant_agent_area t1 " +
            " INNER JOIN merchant t2 ON t1.MERCHANT_ID = t2.ID " +
            " INNER JOIN merchant_agent_area_detail t3 ON t1.ID = t3.AGENT_AREA_ID  " +
            "WHERE " +
            " MERCHANT_ID = #{merchantId}  " +
            " AND t3.`STATUS` = 1  " +
            " AND t3.PROVINCE = #{province} " +
            " AND t3.CITY = #{city} " +
            " AND t3.AREA = #{area} " +
            " LIMIT 1 ")
    String getMerchantUid2(Integer merchantId, String province, String city, String area);

    @Update("<script>" +
            "UPDATE merchant_store_mapping_area  " +
            "SET " +
            "<if test='updateMerchantStoreMappingArea.warehouseId != null'> WAREHOUSE_ID = #{updateMerchantStoreMappingArea.warehouseId} </if> " +
            "<if test='updateMerchantStoreMappingArea.limitConfigId != null'> LIMIT_CONFIG_ID = #{updateMerchantStoreMappingArea.limitConfigId} </if>" +
            "<if test='updateMerchantStoreMappingArea.ruleId != null'> RULE_ID = #{updateMerchantStoreMappingArea.ruleId} </if>" +
            "WHERE " +
            " ID IN" +
            "<foreach collection='ids' item='id' index='index' open='(' close=')' separator=','>" +
            " #{id}" +
            "</foreach>" +
            "</script>")
    int updateOrderGoods(@Param("updateMerchantStoreMappingArea") MerchantStoreMappingArea updateMerchantStoreMappingArea,@Param("ids") List<String> ids);

    @Select("SELECT ID,`NAME` FROM yg_warehouse WHERE `STATUS` = 1 ")
    List<OrderGoodsSelectBoxVO> warehouseSelectBox();

    @Select("SELECT ID,`NAME` FROM yg_limit_config WHERE `STATUS` = 1 ")
    List<OrderGoodsSelectBoxVO> limitConfigSelectBox();

    @Select("SELECT ID,`NAME` FROM yg_supplier_rule WHERE `STATUS` = 1 ")
    List<OrderGoodsSelectBoxVO> supplierRuleSelectBox();
}