package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderExamineDeail;

import java.util.List;

import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface AgentAreaPaymentOrderExamineDeailMapper extends MyBaseMapper<AgentAreaPaymentOrderExamineDeail> {
    @Update("UPDATE agent_area_payment_order_examine_deail  " +
            "SET DELETED = 1  " +
            "WHERE " +
            "  PAYMENT_ORDER_EXAMINE_ID =#{id} ")
    void updateDeletedByPaymentOrderExamineId(Integer id);
}