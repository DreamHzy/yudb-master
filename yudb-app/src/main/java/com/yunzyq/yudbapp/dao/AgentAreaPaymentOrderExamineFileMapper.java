package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderExamineFile;

import java.util.List;

import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AgentAreaPaymentOrderExamineFileMapper extends MyBaseMapper<AgentAreaPaymentOrderExamineFile> {
    @Update("UPDATE agent_area_payment_order_examine_file  " +
            "SET DELETED = 1  " +
            "WHERE " +
            "  PAYMENT_ORDER_EXAMINE_ID =#{id} ")
    void updateDeletedByPaymentOrderExamineId(Integer id);

    @Select("SELECT URL FROM agent_area_payment_order_examine_file WHERE PAYMENT_ORDER_EXAMINE_ID = #{id} AND TYPE = #{type} AND `STATUS` = 1")
    List<String> examineFile(Integer id, Integer type);
}