package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderExamineFile;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AgentAreaPaymentOrderExamineFileMapper extends MyBaseMapper<AgentAreaPaymentOrderExamineFile> {
    @Select("SELECT   " +
            "    *    " +
            "FROM   " +
            "    agent_area_payment_order_examine_file    " +
            "WHERE   " +
            "    PAYMENT_ORDER_EXAMINE_ID =#{id}" +
            "   AND DELETED= 0" +
            "   ")
    List<AgentAreaPaymentOrderExamineFile> queryByPaymentOrderExamineId(Integer id);
}