package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.dto.ListOrderDTO;
import com.yunzyq.yudbapp.dao.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/2/25 14:47
 * @description:
 */
@Mapper
public interface AppOrderMapper {
    @Select("<script>" +
            "SELECT " +
            "  t1.ID, " +
            "  t1.CREATE_TIME, " +
            "  t1.`STATUS`, " +
            "  t1.ORDER_MONEY, " +
            "  ( SELECT SUM(AMOUNT) FROM yg_order_goods WHERE `STATUS` = 1 AND ORDER_ID = t1.ID ) amount, " +
            "  t1.TYPE, " +
            "  t1.AUDIT_STATUS, " +
            "  t1.IS_CONFIG, " +
            "  t2.UID, " +
            "  t2.ADDRESS " +
            "FROM " +
            "  yg_order t1 " +
            "  LEFT JOIN merchant_store t2 ON t1.STORE_UID = t2.UID " +
            "WHERE " +
            "  t2.ID IN (" +
            "       SELECT " +
            "           MERCHANT_STORE_ID  " +
            "       FROM " +
            "           merchant_store_regional_manager  " +
            "       WHERE " +
            "           REGIONAL_MANAGER_ID = #{managerId}   " +
            "           AND `STATUS` = 1 " +
            ") " +
            "  <if test='keyword != null'> AND t2.UID = #{keyword}  </if> " +
            "  AND t1.`STATUS` != 0 " +
            "  <if test='status != null and status != 2 and status != 5'> AND t1.`STATUS` = #{status}</if> " +
            "  <if test='status == 2'> AND t1.`STATUS` IN (2, 3) AND t1.AUDIT_STATUS = 1</if> " +
            "  <if test='status == 2 or status == 3 or status == 4'> AND t1.IS_CONFIG = 1</if> " +
            "  <if test='status == null or status == 1'> AND t1.AUDIT_STATUS = 1</if> " +
            "  <if test='status == 5'> AND ((t1.TYPE = 2 AND t1.`STATUS` = 1 AND t1.AUDIT_STATUS = 0) OR (t1.TYPE = 2 AND t1.`STATUS` = 2 AND t1.AUDIT_STATUS = 1 AND t1.IS_CONFIG = 2))</if> " +
            "        ORDER BY " +
            "  ID DESC" +
            "</script>")
    List<ListOrderVO> listOrder(ListOrderDTO listOrderDTO);

    @Select("<script>" +
            "SELECT " +
            "  t2.IMAGE AS url " +
            "FROM " +
            "  yg_order_goods t1 " +
            "  INNER JOIN yg_goods t2 ON t1.GOODS_CODE = t2.`CODE` " +
            "WHERE " +
            "  t1.ORDER_ID = #{orderId} " +
            "LIMIT 2 " +
            "</script>")
    List<GoodsImage> goodsImages(@Param("orderId") Integer orderId);

    @Select("SELECT " +
            " t1.ID, " +
            " ( SELECT `NAME` FROM merchant WHERE ID = t2.AGENT_ID LIMIT 1 ) merchantName, " +
            " t1.TYPE, " +
            " t1.PLACE_ORDER_TYPE, " +
            " t1.ORDER_NO, " +
            " t1.CREATE_TIME, " +
            " t1.`STATUS`, " +
            " t1.REMARK, " +
            " t1.RECEIVER, " +
            " t1.RECEIVER_MOBILE, " +
            " t1.DELIVERY_ADDRESS, " +
            " t2.SHOW_DELIVERY_ADDRESS, " +
            " t2.DELIVERY_PROVINCE, " +
            " t2.DELIVERY_CITY, " +
            " t2.DELIVERY_AREA, " +
            " t1.STORE_UID, " +
            " t2.MOBILE, " +
            " t1.GOODS_MONEY, " +
            " t1.REBATE_MONEY, " +
            " t1.FREIGHT, " +
            " t1.IS_DOUBLE_FREIGHT, " +
            " t1.PAY_MONEY, " +
            " t1.ORDER_MONEY, " +
            " t1.EXPIRE_TIME, " +
            " t1.USER_NAME, " +
            " (SELECT COUNT(*) FROM yg_order_sold WHERE SUPER_ORDER_ID = t1.ID AND `STATUS` IN (2,3)) hasAfterSold, " +
            " t1.DISTRIBUTION_TYPE, " +
            " t3.ADDRESS, " +
            " t3.CONTACT_PERSON, " +
            " t3.PICK_UP_TIME, " +
            " t3.USER_NAME AS CONTACT_USER_NAME, " +
            " t3.MOBILE AS CONTACT_MOBILE, " +
            " t3.ID_CARD, " +
            " t1.AUDIT_STATUS, " +
            " t1.IS_CONFIG, " +
            " ( SELECT SUM( REFUND_MONEY ) FROM yg_order_pay WHERE ORDER_ID = t1.ID ) REFUND_MONEY " +
            "FROM " +
            " yg_order t1 " +
            " LEFT JOIN merchant_store t2 ON t1.STORE_ID = t2.ID " +
            " LEFT JOIN yg_order_pickup t3 ON t1.ID = t3.ORDER_ID " +
            "WHERE " +
            " t1.ID = #{orderId} " +
            " AND t1.`STATUS` != 0")
    OrderDetailVO orderDetail(Integer orderId);

    @Select("SELECT " +
            "  PAY_WAY " +
            "FROM " +
            "  yg_order_pay " +
            "WHERE " +
            "  ORDER_ID = #{orderId} " +
            "  AND `STATUS` = 2 " +
            "GROUP BY " +
            "  PAY_WAY")
    List<Integer> getPayWay(Integer orderId);

    @Select("SELECT " +
            "  t3.ID, " +
            "  t3.`NAME` " +
            "FROM " +
            "  yg_order_goods t1 " +
            "  LEFT JOIN yg_goods t2 ON t1.GOODS_CODE = t2.`CODE` " +
            "  LEFT JOIN yg_category t3 ON t2.CATEGORY_ID = t3.ID " +
            "WHERE " +
            "  t1.ORDER_ID = #{orderId} " +
            "GROUP BY " +
            "  t3.ID")
    List<CategoryInfo> orderGoodsCategoryInfo(Integer orderId);

    @Select("SELECT  " +
            "   IFNULL( SUM( GOODS_MONEY ), 0 ) GOODS_MONEY  " +
            "FROM  " +
            "   yg_order_goods t1  " +
            "   INNER JOIN yg_goods t2 ON t1.GOODS_CODE = t2.`CODE`  " +
            "WHERE  " +
            "   ORDER_ID = #{orderId}  " +
            "   AND t2.CATEGORY_ID = #{categoryId}")
    BigDecimal orderDetailGoodsMoney(@Param("orderId") Integer orderId, @Param("categoryId") Integer categoryId);

    @Select("SELECT " +
            "  t2.IMAGE, " +
            "  t2.LABLE, " +
            "  t2.RETAILER_GOODS_NAME AS goodsName, " +
            "  ( SELECT SPECIFICATION_MSG FROM yg_goods_specification WHERE `STATUS` = 1 AND GOODS_CODE = t2.`CODE` LIMIT 1 ) SPECIFICATION, " +
            "  t2.UNIT, " +
            "  t1.AMOUNT, " +
            "  t1.PRICE, " +
            "  t1.GOODS_MONEY, " +
            "  t1.REBATE, " +
            "  t1.REBATE_MONEY " +
            "FROM " +
            "  yg_order_goods t1 " +
            "  INNER JOIN yg_goods t2 ON t1.GOODS_CODE = t2.`CODE` " +
            "WHERE " +
            "  ORDER_ID = #{orderId} " +
            "  AND t2.CATEGORY_ID = #{categoryId}")
    List<OrderDetailGoods> orderDetailGoods(@Param("orderId") Integer orderId, @Param("merchantId") Integer merchantId, @Param("categoryId") Integer categoryId);

}
