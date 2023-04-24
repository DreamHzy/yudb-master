package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.vo.HistoryOrderVo;
import com.yunzyq.yudbapp.dao.vo.ListAgentPaymentTypeVO;
import com.yunzyq.yudbapp.dao.vo.ListMyAgentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author wj
 * @Date 2021/10/25
 */
@Mapper
public interface MyAgentMapper {
    /**
     * 代理商列表
     *
     * @param managerId
     * @param conditions
     * @return
     */
    @Select("<script>" +
            "SELECT " +
            " t2.ID, " +
            " t2.UID, " +
            " t2.MERCHANT_NAME, " +
            " t2.PROVINCE, " +
            " t2.CITY, " +
            " t2.AREA, " +
            " t2.EXPIRE_TIME," +
//            " t2.MOBILE " +
            " (SELECT MOBILE FROM merchant WHERE ID = t2.MERCHANT_ID) MOBILE " +
            "FROM " +
            " merchant_agent_area_regional_manager t1 " +
            " INNER JOIN merchant_agent_area t2 ON t1.MERCHANT_AGENT_AREA_ID = t2.ID  " +
            "WHERE " +
            " t1.REGIONAL_MANAGER_ID = #{managerId}  " +
            " AND t1.`STATUS` = 1 " +
            "<if test='conditions != null'>AND (t2.MERCHANT_NAME = #{conditions} OR t2.UID = #{conditions} OR t2.MOBILE = #{conditions})</if>" +
            "</script>")
    List<ListMyAgentVO> listMyAgent(String managerId, String conditions);

    /**
     * 缴费类型
     *
     * @return
     */
    @Select("SELECT " +
            " id, " +
            " `NAME`  " +
            "FROM " +
            " agent_area_payment_type  " +
            "WHERE " +
            " `STATUS` = 1")
    List<ListAgentPaymentTypeVO> listAgentPaymentType();

    /**
     * 获取未缴费订单
     *
     * @param merchantId
     * @return
     */
    @Select("SELECT " +
            " COUNT(*)  " +
            "FROM " +
            " agent_area_payment_order_master t1  " +
            "WHERE " +
            " t1.`STATUS` = 1  " +
            " AND t1.DELETED = 0  " +
            " AND t1.MERCHANT_ID = #{merchantId}")
    int getPaidOrderCount(Integer merchantId);

    /**
     * 历史开单
     *
     * @param managerId
     * @return
     */
    @Select("SELECT " +
            " MERCHANT_NAME, " +
            " UID, " +
            " MONEY, " +
            " PAYMENT_TYPE_NAME, " +
            " CREATE_TIME  " +
            "FROM " +
            " agent_area_payment_order_master  " +
            "WHERE " +
            " REGIONAL_MANAGER_ID = #{managerId}  " +
            " AND `STATUS` = 2 " +
            " AND DELETED = 0")
    List<HistoryOrderVo> historyBill(String managerId);
}
