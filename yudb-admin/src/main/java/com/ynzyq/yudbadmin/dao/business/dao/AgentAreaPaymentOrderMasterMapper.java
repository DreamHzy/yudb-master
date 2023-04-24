package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.FinancePageDto;
import com.ynzyq.yudbadmin.dao.business.dto.MoneyDTO;
import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderMaster;

import com.ynzyq.yudbadmin.dao.business.vo.AgentFinancePageVo;
import com.ynzyq.yudbadmin.dao.business.vo.AgentOrderExpireTimeVo;
import com.ynzyq.yudbadmin.dao.business.vo.AgentOrderJoinVo;
import com.ynzyq.yudbadmin.dao.business.vo.ManagementOrderExpireTimeVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

public interface AgentAreaPaymentOrderMasterMapper extends MyBaseMapper<AgentAreaPaymentOrderMaster> {


    @Select("" +
            "<script>" +
            "SELECT  " +
            "   pom.ID,  " +
            "   pom.MERCHANT_NAME,  " +
            "   pom.UID merchantStoreUid,  " +
            "   pom.PAYMENT_TYPE_NAME,  " +
            "   pom.MONEY,  " +
            "   pom.PAY_MONEY,  " +
            "   (pom.MONEY - pom.PAY_MONEY) remain,  " +
            "   pom.ADJUSTMENT_MSG,  " +
            "   pom.SEND,  " +
            "   IF(   pom.`STATUS`=1,'待支付','已支付') 'status', " +
            "  (SELECT GROUP_CONCAT(pop.CHANNEL_ORDER_NO)  FROM agent_area_payment_order_pay pop WHERE pop.PAYMENT_ORDER_MASTER_ID =pom.ID AND pop.`STATUS`=3) channelOrderNo, " +
            "   pom.REGIONAL_MANAGER_NAME,  " +
            "  (SELECT d.`NAME` FROM department d LEFT JOIN regional_manager_department rmd ON d.ID = rmd.DEPARTMENT_ID WHERE rmd.`STATUS`=1 AND rmd.REGIONAL_MANAGER_ID=pom.REGIONAL_MANAGER_ID ) departmentName, " +
            "   pom.EXPIRE_TIME,  " +
            "   pom.FEES,  " +
            "   pom.PAY_TIME ," +
            "   pom.CREATE_TIME ," +
            "   pom.PROVINCE ," +
            "   pom.CITY ," +
            "   pom.AREA ," +
            "   pom.IS_REPORT ," +
            "   IF(   pom.PAY_TYPE=1,'线上支付','线下支付') PAY_TYPE, " +
            "  IFNULL((SELECT IS_SETTLE FROM agent_area_payment_order_pay WHERE PAYMENT_ORDER_MASTER_ID = pom.ID AND `STATUS` = 3 LIMIT 1),0) IS_SETTLE, " +
            "  pom.REMARK " +
            " FROM  " +
            "   agent_area_payment_order_master pom " +
            "   WHERE pom.`STATUS` NOT IN(3,4)  " +
            "   AND pom.EXAMINE = 1 " +
            "   AND pom.DELETED = 0  " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  pom.UID  like concat('%',#{condition},'%') " +
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
            "  ORDER BY " +
            "  pom.PAY_TIME DESC, " +
            "  pom.CREATE_TIME DESC" +
            " </script>")
    List<AgentFinancePageVo> financePageVoList(FinancePageDto financePageDto);

    @Select("" +
            "<script>" +
            "SELECT  " +
            "   COUNT(*) count, " +
            "   IFNULL( SUM( pom.MONEY ), 0 ) money  " +
            " FROM  " +
            "   agent_area_payment_order_master pom " +
            "   WHERE pom.`STATUS`  = #{status} " +
            "   AND pom.EXAMINE = 1 " +
            "   AND pom.DELETED = 0  " +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  pom.UID  like concat('%',#{condition},'%') " +
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


    @Select("<script>" +
            " SELECT    " +
            "    ms.MERCHANT_ID merchantId,    " +
            "    ms.MERCHANT_NAME merchantName,    " +
            "    ms.UID ,    " +
            "    ms.MOBILE ,    " +
            "    ms.PROVINCE ,    " +
            "    ms.CITY ,    " +
            "    ms.AREA ,    " +
//            "    msrm.REGIONAL_MANAGER_ID regionalManagerId,    " +
//            "    rm.`NAME` regionalManagerName,    " +
//            "    rm.MOBILE regionalManagerMobile , " +
            "     (SELECT msrm.REGIONAL_MANAGER_ID FROM  merchant_agent_area_regional_manager msrm   " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID WHERE msrm.MERCHANT_AGENT_AREA_ID = ms.ID AND msrm.`STATUS` = 1 LIMIT 1 ) regionalManagerId,  " +
            "    (SELECT rm.`NAME` FROM  merchant_agent_area_regional_manager msrm   " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID WHERE msrm.MERCHANT_AGENT_AREA_ID = ms.ID AND msrm.`STATUS` = 1 LIMIT 1 ) regionalManagerName,  " +
            "    (SELECT rm.MOBILE FROM  merchant_agent_area_regional_manager msrm   " +
            "  LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID WHERE msrm.MERCHANT_AGENT_AREA_ID = ms.ID AND msrm.`STATUS` = 1 LIMIT 1 ) regionalManagerMobile, " +
            "    ms.MANAGEMENT_EXPENSE  money," +
            "    ms.IS_EFFECT," +
            "    ms.SERVICE_EXPIRE_TIME  " +
            " FROM    " +
            "    merchant_agent_area ms    " +
            "    LEFT JOIN merchant m ON ms.MERCHANT_ID = m.ID    " +
//            "    LEFT JOIN merchant_agent_area_regional_manager msrm ON ms.ID = msrm.MERCHANT_AGENT_AREA_ID    " +
//            "    LEFT JOIN regional_manager rm ON rm.ID = msrm.REGIONAL_MANAGER_ID     " +
            " WHERE    " +
            "      TIMESTAMPDIFF(DAY ,NOW(),ms.EXPIRE_TIME) <![CDATA[ <= ]]> 30" +
            " </script>")
    List<AgentOrderExpireTimeVo> queryByExpireTime();


    @Select(
            "<script>" +
                    "SELECT   " +
                    "   UID id, " +
                    "   COALESCE(sum(MONEY),0) money     " +
                    "FROM   " +
                    "   agent_area_payment_order_master   " +
                    "   WHERE 1=1  " +
                    "   AND `STATUS` = 1 " +
                    "   AND PAYMENT_TYPE_ID = 1 " +
                    "   AND UID in " +
                    "   <foreach collection=\"list\" item=\"agentOrderExpireTimeVo\" index=\"index\"     " +
                    "            open=\"(\" close=\")\" separator=\",\">     " +
                    "            #{agentOrderExpireTimeVo.uid}        " +
                    "        </foreach> " +
                    "  GROUP BY UID" +
                    "</script>")
    List<AgentOrderJoinVo> queryManagementOrderJoinVoList(@Param("list") List<AgentOrderExpireTimeVo> agentOrderExpireTimeVoList);

    @Update("UPDATE agent_area_payment_order_master SET IS_REPORT = #{isReport} WHERE ID = #{id} ")
    void updateIsReport(Integer isReport, Integer id);
}