package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.FinancePageDto;
import com.ynzyq.yudbadmin.dao.business.dto.ListFinanceOrderDTO;
import com.ynzyq.yudbadmin.dao.business.dto.MoneyDTO;
import com.ynzyq.yudbadmin.dao.business.model.PaymentOrderMaster;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import oshi.jna.platform.mac.SystemB;

public interface PaymentOrderMasterMapper extends MyBaseMapper<PaymentOrderMaster> {


    @Select("" +
            "<script>" +
            "SELECT  " +
            "   pom.ID,  " +
            "   pom.ORDER_NO,  " +
            "   pom.MERCHANT_NAME,  " +
            "   pom.MERCHANT_STORE_UID,  " +
            "   pom.PAYMENT_TYPE_NAME,  " +
            "   pom.SEND,  " +
            "   pom.MONEY,  " +
            "   pom.PAY_MONEY,  " +
            "   (pom.MONEY - pom.PAY_MONEY) remain,  " +
            "   ( SELECT REGION FROM merchant_store_mapping_area WHERE MANAGER_ID = pom.REGIONAL_MANAGER_ID AND `STATUS` = 1 LIMIT 1 ) REGION, " +
            "   IF(   pom.`STATUS`=1,'待支付','已支付') 'status', " +
            "  (SELECT GROUP_CONCAT(pop.CHANNEL_ORDER_NO)  FROM payment_order_pay pop WHERE pop.PAYMENT_ORDER_MASTER_ID =pom.ID AND pop.`STATUS`= 3) channelOrderNo, " +
            "   pom.REGIONAL_MANAGER_NAME,  " +
            "   pom.EXPIRE_TIME,  " +
            "   pom.FEES,  " +
            "   pom.PAY_TIME ," +
            "   pom.CREATE_TIME ," +
            "   pom.PROVINCE ," +
            "   pom.CITY ," +
            "   pom.AREA ," +
            "   pom.ADDRESS ," +
            "   pom.IS_REPORT ," +
            "   pom.SERVICE_START_TIME ," +
            "   pom.SERVICE_END_TIME ," +
            "   IF(   pom.PAY_TYPE=1,'线上支付','线下支付') PAY_TYPE, " +
            "  pom.ADJUSTMENT_MSG, " +
            "  IFNULL((SELECT IS_SETTLE FROM payment_order_pay WHERE PAYMENT_ORDER_MASTER_ID = pom.ID AND `STATUS` = 3 LIMIT 1 ),0) IS_SETTLE, " +
            "  pom.REMARK," +
            "  pom.IS_PUBLISH AS EXAMINE," +
            "  DATEDIFF( DATE_FORMAT( NOW(), '%y-%m-%d' ), pom.EXPIRE_TIME ) overdueDay," +
            " ms.COLLECTION_MONTH, " +
            " ms.`STATUS` AS primaryStatus, " +
            " ms.SIGN_TIME, " +
            " ms.SERVICE_EXPIRE_TIME, " +
            " ms.OPEN_TIME  " +
            " FROM  " +
            "   payment_order_master pom " +
            "   LEFT JOIN merchant_store ms ON pom.MERCHANT_STORE_ID = ms.ID " +
            "   WHERE pom.`STATUS` NOT IN(3,4)  " +
//            "   AND pom.EXAMINE = 1 " +
            "   AND pom.DELETED = 0  " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  pom.MERCHANT_STORE_UID = #{condition} " +
            "  OR pom.REGIONAL_MANAGER_NAME like concat('%',#{condition},'%') " +
            " )  " +
            " </if>" +
            " <if test='paymentTypeId !=null'>" +
            " AND pom.PAYMENT_TYPE_ID=#{paymentTypeId}" +
            " </if>" +
            " <if test='payStatus !=null'>" +
            " AND pom.STATUS=#{payStatus}" +
            " </if>" +
            " <if test='startexpireTime !=null'>" +
            " AND pom.EXPIRE_TIME BETWEEN #{startexpireTime} AND #{endexpireTime} " +
            " </if>" +
            " <if test='startPayTime !=null'>" +
            " AND pom.PAY_TIME BETWEEN #{startPayTime} AND #{endPayTime} " +
            " </if>" +
            " <if test='startCreateTime !=null'> AND pom.CREATE_TIME BETWEEN #{startCreateTime} AND #{endCreateTime}  </if>" +
            " <if test='payType !=null'>" +
            " AND pom.PAY_TYPE = #{payType} " +
            " </if>" +
            " <if test='send !=null'>" +
            " AND pom.SEND = #{send} " +
            " </if>" +
            "<if test='typeId != null and managerId != null'> AND pom.PAYMENT_TYPE_ID = #{typeId} AND pom.REGIONAL_MANAGER_ID = #{managerId} </if>" +
            "<if test='orderId != null'> AND pom.ID = #{orderId} </if>" +
            "<if test='examine != null'> AND pom.IS_PUBLISH = #{examine} </if>" +
            "<if test='state != null'> AND ms.`STATUS` = #{state} </if>" +
            "<if test='month != null'> AND ms.COLLECTION_MONTH = #{month} </if>" +
            " ORDER BY " +
            " pom.PAY_TIME DESC, " +
            " pom.CREATE_TIME DESC" +
            " </script>")
    List<FinancePageVo> financePageVoList(FinancePageDto financePageDto);

    @Select("" +
            "<script>" +
            "SELECT  " +
            "   pom.ID,  " +
            "   pom.MERCHANT_NAME,  " +
            "   pom.MERCHANT_STORE_UID,  " +
            "   pom.PAYMENT_TYPE_NAME,  " +
            "   pom.SEND,  " +
            "   pom.MONEY,  " +
            "   pom.PAY_MONEY,  " +
            "   (pom.MONEY - pom.PAY_MONEY) remain,  " +
            "   ( SELECT REGION FROM merchant_store_mapping_area WHERE MANAGER_ID = pom.REGIONAL_MANAGER_ID AND `STATUS` = 1 LIMIT 1 ) REGION, " +
            "   IF(   pom.`STATUS`=1,'待支付','已支付') 'status', " +
            "  (SELECT GROUP_CONCAT(pop.CHANNEL_ORDER_NO)  FROM payment_order_pay pop WHERE pop.PAYMENT_ORDER_MASTER_ID =pom.ID AND pop.`STATUS`= 3) channelOrderNo, " +
            "   pom.REGIONAL_MANAGER_NAME,  " +
            "   pom.EXPIRE_TIME,  " +
            "   pom.FEES,  " +
            "   pom.PAY_TIME ," +
            "   pom.CREATE_TIME ," +
            "   pom.PROVINCE ," +
            "   pom.CITY ," +
            "   pom.AREA ," +
            "   pom.ADDRESS ," +
            "   pom.IS_REPORT ," +
            "   IF(   pom.PAY_TYPE=1,'线上支付','线下支付') PAY_TYPE, " +
            "  pom.ADJUSTMENT_MSG, " +
            "  IFNULL((SELECT IS_SETTLE FROM payment_order_pay WHERE PAYMENT_ORDER_MASTER_ID = pom.ID AND `STATUS` = 3 LIMIT 1 ),0) IS_SETTLE, " +
            "  pom.REMARK," +
            " ms.COLLECTION_MONTH, " +
            " ms.`STATUS` AS primaryStatus, " +
            " ms.SIGN_TIME, " +
            " ms.SERVICE_EXPIRE_TIME, " +
            " ms.OPEN_TIME  " +
            " FROM  " +
            "   payment_order_master pom " +
            "   LEFT JOIN merchant_store ms ON pom.MERCHANT_STORE_ID = ms.ID " +
            "   WHERE pom.`STATUS` NOT IN(3,4)  " +
//            "   AND pom.EXAMINE = 1 " +
            "   AND pom.DELETED = 0  " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  pom.MERCHANT_STORE_UID  like concat('%',#{condition},'%') " +
            "  OR pom.REGIONAL_MANAGER_NAME like concat('%',#{condition},'%') " +
            " )  " +
            " </if>" +
            " <if test='paymentTypeId !=null'>" +
            " AND pom.PAYMENT_TYPE_ID=#{paymentTypeId}" +
            " </if>" +
            " <if test='payStatus !=null'>" +
            " AND pom.STATUS=#{payStatus}" +
            " </if>" +
            " <if test='startexpireTime !=null'>" +
            " AND pom.EXPIRE_TIME BETWEEN #{startexpireTime} AND #{endexpireTime} " +
            " </if>" +
            " <if test='startPayTime !=null'>" +
            " AND pom.PAY_TIME BETWEEN #{startPayTime} AND #{endPayTime} " +
            " </if>" +
            " <if test='payType !=null'>" +
            " AND pom.PAY_TYPE = #{payType} " +
            " </if>" +
            " <if test='send !=null'>" +
            " AND pom.SEND = #{send} " +
            " </if>" +
            "<if test='typeId != null and managerId != null'> AND pom.PAYMENT_TYPE_ID = #{typeId} AND pom.REGIONAL_MANAGER_ID = #{managerId} </if>" +
            "<if test='orderId != null'> AND pom.ID = #{orderId} </if>" +
            "<if test='type == 1'> AND pom.EXAMINE = 2 </if>" +
            "<if test='type == 2'> AND pom.EXAMINE = 1 </if>" +
            " ORDER BY " +
            " pom.PAY_TIME DESC, " +
            " pom.CREATE_TIME DESC" +
            " </script>")
    List<FinancePageVo> listFinanceOrder(ListFinanceOrderDTO financeOrderDTO);

    @Select("" +
            "<script>" +
            "SELECT  " +
            "  COUNT(*) count," +
            "  IFNULL( SUM( pom.MONEY ), 0 ) money " +
            " FROM  " +
            "   payment_order_master pom " +
            "   WHERE pom.`STATUS` = #{status} " +
//            "   AND pom.EXAMINE = 1 " +
            "   AND pom.DELETED = 0  " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  pom.MERCHANT_STORE_UID  like concat('%',#{condition},'%') " +
            "  OR pom.REGIONAL_MANAGER_NAME like concat('%',#{condition},'%') " +
            " )  " +
            " </if>" +
            " <if test='paymentTypeId !=null'>" +
            " AND pom.PAYMENT_TYPE_ID=#{paymentTypeId}" +
            " </if>" +
            " <if test='payStatus !=null'>" +
            " AND pom.STATUS=#{payStatus}" +
            " </if>" +
            " <if test='startexpireTime !=null'>" +
            " AND pom.EXPIRE_TIME BETWEEN #{startexpireTime} AND #{endexpireTime} " +
            " </if>" +
            " <if test='startPayTime !=null'>" +
            " AND pom.PAY_TIME BETWEEN #{startPayTime} AND #{endPayTime} " +
            " </if>" +
            " <if test='payType !=null'>" +
            " AND pom.PAY_TYPE = #{payType} " +
            " </if>" +
            " </script>")
    MoneyDTO getMoneySUM(FinancePageDto financePageDto);


    @Select("SELECT   " +
            "   ID,   " +
            "   `NAME`    " +
            "FROM   " +
            "   payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1")
    List<PayTypeListVo> payTypeList();


    @Select(
            "<script>" +
                    "SELECT   " +
                    "   MERCHANT_STORE_ID id," +
                    "   COALESCE(sum(MONEY),0) money     " +
                    "FROM   " +
                    "   payment_order_master   " +
                    "   WHERE 1=1  " +
                    "   AND `STATUS` = 1 " +
                    "   AND PAYMENT_TYPE_ID = 1 " +

                    "   AND MERCHANT_STORE_ID in " +
                    "   <foreach collection=\"list\" item=\"managementOrderExpireTimeVo\" index=\"index\"     " +
                    "            open=\"(\" close=\")\" separator=\",\">     " +
                    "            #{managementOrderExpireTimeVo.merchantStoreId}        " +
                    "        </foreach> " +
                    "  GROUP BY MERCHANT_STORE_ID" +
                    "</script>")
    List<ManagementOrderJoinVo> queryManagementOrderJoinVoList(@Param("list") List<ManagementOrderExpireTimeVo> merchantStores);

    @Select("SELECT " +
            " COUNT(*)  " +
            "FROM " +
            " payment_order_master  " +
            "WHERE " +
            " `STATUS` = 1  " +
            " AND PAYMENT_TYPE_ID = 1  " +
            " AND MERCHANT_STORE_ID = #{storeId} ")
    int queryExistOrderCount(Integer storeId);

    @Select("SELECT " +
            " t1.ID  " +
            "FROM " +
            " payment_order_master t1 " +
            " INNER JOIN merchant_store t2 ON t1.MERCHANT_STORE_ID = t2.ID  " +
            "WHERE " +
            " t1.`STATUS` = 1  " +
            " AND t1.PAYMENT_TYPE_ID = 1  " +
            " AND t1.TYPE = 1  " +
            " AND t1.EXAMINE = 2  " +
            " AND t1.IS_PUSH = 1  " +
            " AND t1.IS_PUBLISH = 2  " +
            " AND t2.COLLECTION_MONTH = #{month}")
    List<Integer> queryNeedPushOrder(Integer month);

    @Select("SELECT " +
            " t1.ID  " +
            "FROM " +
            " payment_order_master t1  " +
            "WHERE " +
            " t1.`STATUS` = 1  " +
            " AND t1.PAYMENT_TYPE_ID IN ( 2, 3 )  " +
            " AND t1.TYPE = 3  " +
            " AND t1.EXAMINE = 2  " +
            " AND t1.IS_PUSH = 1  " +
            " AND t1.IS_PUBLISH = 2  " +
            " AND t1.EXPIRE_TIME BETWEEN #{startTime} AND #{endTime}")
    List<Integer> queryNeedPushOrder2(String startTime, String endTime);

    @Select("SELECT     " +
            "     PAYMENT_TYPE_NAME payTypeName,     " +
            "     MONEY,     " +
            "     PAY_TIME     " +
            "FROM     " +
            "     payment_order_master      " +
            "WHERE     " +
            "     `STATUS` = 2      " +
            "     AND MERCHANT_STORE_ID=#{id}")
    List<MerchantStoreOrderVo> queryStoreId(String id);

    @Select("SELECT     " +
            "     *    " +
            "FROM     " +
            "     payment_order_master      " +
            "WHERE     " +
            "     `STATUS` = 2      " +
            "     AND MERCHANT_STORE_ID=#{id}" +
            "  ORDER BY  PAYMENT_TYPE_ID ")
    List<PaymentOrderMaster> queryOrderInfoByStoreId(String id);


    @Select(
            "<script>" +
                    "SELECT   " +
                    "   MERCHANT_STORE_ID id," +
                    "   COALESCE(sum(MONEY),0) money     " +
                    "FROM   " +
                    "   payment_order_master   " +
                    "   WHERE 1=1  " +
                    "   AND `STATUS` = 1 " +
                    "   AND PAYMENT_TYPE_ID = 1 " +

                    "   AND MERCHANT_STORE_ID in " +
                    "   <foreach collection=\"list\" item=\"managementOrderExpireTimeVo\" index=\"index\"     " +
                    "            open=\"(\" close=\")\" separator=\",\">     " +
                    "            #{managementOrderExpireTimeVo.merchantStoreId}        " +
                    "        </foreach> " +
                    "  GROUP BY MERCHANT_STORE_ID" +
                    "</script>")
    List<MerchantStoreCloudSchoolVo> merchantStoreJoinList(List<MerchantStoreCloudSchoolVo> merchantStoreList);

    @Select(" SELECT     " +
            "    *    " +
            " FROM     " +
            "     payment_order_master      " +
            " WHERE     " +
            "     `STATUS` = #{status}      " +
            "     AND PAYMENT_TYPE_ID=#{payTypeId}")
    List<PaymentOrderMaster> queryByPayTypeIdAndStatus(@Param("payTypeId") int payTypeId, @Param("status") int status);

    @Select(" SELECT      " +
            "      *       " +
            "  FROM      " +
            "      payment_order_master      " +
            "      WHERE MERCHANT_STORE_ID =#{streoId}     " +
            "      AND PAYMENT_TYPE_ID =#{payTypeId}     " +
            "      AND `STATUS` = 1      " +
            "      AND DELETED=0 " +
            " LIMIT 1")
    PaymentOrderMaster queryByStoreIdAndPayTypeId(@Param("streoId") Integer streoId, @Param("payTypeId") Integer payTypeId);

    @Update("UPDATE payment_order_master SET IS_REPORT = #{isReport} WHERE ID = #{id} ")
    void updateIsReport(Integer isReport, Integer id);
}