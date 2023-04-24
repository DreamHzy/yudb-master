package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderExamine;
import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderExamineDeail;

import java.util.List;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

public interface AgentAreaPaymentOrderExamineDeailMapper extends MyBaseMapper<AgentAreaPaymentOrderExamineDeail> {

    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    agent_area_payment_order_examine_deail    " +
            "WHERE   " +
            "    PAYMENT_ORDER_EXAMINE_ID =#{id}" +
            "   AND EXAMINE= 1" +
            "   AND DELETED= 0" +
            " ")
    AgentAreaPaymentOrderExamineDeail queryByOne(AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine);


    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    agent_area_payment_order_examine_deail    " +
            "WHERE   " +
            "    PAYMENT_ORDER_EXAMINE_ID =#{id}" +
            "   AND DELETED= 0" +
            "   AND EXAMINE= 2 ")
    AgentAreaPaymentOrderExamineDeail queryByTwo(AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine);

    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    agent_area_payment_order_examine_deail    " +
            "WHERE   " +
            "    PAYMENT_ORDER_EXAMINE_ID =#{id}" +
            "   AND DELETED= 0" +
            "   ")
    List<AgentAreaPaymentOrderExamineDeail> queryByPaymentOrderExamineId(Integer id);
}