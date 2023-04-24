package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderPay;

import java.util.List;

import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AgentAreaPaymentOrderPayMapper extends MyBaseMapper<AgentAreaPaymentOrderPay> {

    @Select("SELECT*FROM agent_area_payment_order_pay WHERE ORDER_NO  =#{orderNo} ")
    AgentAreaPaymentOrderPay queryByOrderNo(AgentAreaPaymentOrderPay paymentOrderPay);
}