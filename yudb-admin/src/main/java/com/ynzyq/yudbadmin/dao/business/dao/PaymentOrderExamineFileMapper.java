package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.PaymentOrderExamineFile;

import java.util.List;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PaymentOrderExamineFileMapper extends MyBaseMapper<PaymentOrderExamineFile> {


    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    payment_order_examine_file    " +
            "WHERE   " +
            "    PAYMENT_ORDER_EXAMINE_ID =#{id}" +
            "   AND DELETED= 0" +
            "   ")
    List<PaymentOrderExamineFile> queryByPaymentOrderExamineId(Integer id);
}