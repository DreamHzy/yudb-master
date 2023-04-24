package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.PaymentOrderExamineDeailFile;

import java.util.List;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PaymentOrderExamineDeailFileMapper extends MyBaseMapper<PaymentOrderExamineDeailFile> {
    @Select("SELECT CONCAT(#{imageurl},URL)  FROM payment_order_examine_deail_file WHERE PAYMENT_ORDER_EXAMINE_DEAIL_ID =#{id} ")
    List<String> queryUrlByPaymentOrderExamineDeailId(@Param("id") Integer id, @Param("imageurl") String imageurl);
}