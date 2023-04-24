package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.PaymentOrderExamine;
import com.ynzyq.yudbadmin.dao.business.model.PaymentOrderExamineDeail;

import java.util.List;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

public interface PaymentOrderExamineDeailMapper extends MyBaseMapper<PaymentOrderExamineDeail> {

    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    payment_order_examine_deail    " +
            "WHERE   " +
            "    PAYMENT_ORDER_EXAMINE_ID =#{id}" +
            "   AND EXAMINE= 1" +
            "   AND DELETED= 0" +
            " ")
    PaymentOrderExamineDeail queryByOne(PaymentOrderExamine paymentOrderExamine);

    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    payment_order_examine_deail    " +
            "WHERE   " +
            "    PAYMENT_ORDER_EXAMINE_ID =#{id}" +
            "   AND DELETED= 0" +
            "   AND EXAMINE= 2 ")
    PaymentOrderExamineDeail queryByTwo(PaymentOrderExamine paymentOrderExamine);


    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    payment_order_examine_deail    " +
            "WHERE   " +
            "    PAYMENT_ORDER_EXAMINE_ID =#{id}" +
            "   AND DELETED= 0" +
            "   ")
    List<PaymentOrderExamineDeail> queryByPaymentOrderExamineId(Integer id);

    @Select("SELECT MAX(STEP_ID) STEP_ID FROM payment_order_examine_deail WHERE PAYMENT_ORDER_EXAMINE_ID = #{examineId} AND STEP_ID IS NOT NULL")
    Integer getExamineDetailMaxStepId(Integer examineId);

    @Select("SELECT ID FROM  approve_process_step WHERE `STATUS` = 1 AND USER_ID = #{userId} AND ID > #{stepId} ORDER BY ID LIMIT 1")
    Integer getUserNextStepId(Integer userId, Integer stepId);
}