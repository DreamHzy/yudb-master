package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderExamineDeailFile;

import java.util.List;

import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AgentAreaPaymentOrderExamineDeailFileMapper extends MyBaseMapper<AgentAreaPaymentOrderExamineDeailFile> {

    @Select("SELECT CONCAT(#{imageurl},URL) FROM agent_area_payment_order_examine_deail_file WHERE AGENT_AREA_PAYMENT_ORDER_EXAMINE_DEAIL_ID =#{id} ")
    List<String> queryUrlByPaymentOrderExamineDeailId(@Param("id") Integer id, @Param("imageurl") String imageurl);
}