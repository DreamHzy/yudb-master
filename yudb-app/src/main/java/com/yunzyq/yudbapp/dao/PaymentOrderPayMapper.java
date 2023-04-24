package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.PaymentOrderPay;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

public interface PaymentOrderPayMapper extends MyBaseMapper<PaymentOrderPay> {

    @Select("SELECT*FROM payment_order_pay WHERE ORDER_NO  =#{orderNo} ")
    PaymentOrderPay queryByOrderNo(PaymentOrderPay paymentOrderPay);

}