package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentType;

import com.ynzyq.yudbadmin.dao.business.model.PaymentType;
import com.ynzyq.yudbadmin.dao.business.vo.PayTypeListVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AgentAreaPaymentTypeMapper extends MyBaseMapper<AgentAreaPaymentType> {

    @Select("SELECT   " +
            "   ID,   " +
            "   `NAME`    " +
            "FROM   " +
            "   agent_area_payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1")
    List<PayTypeListVo> payTypeList();


    @Select("SELECT   " +
            "   `NAME`    " +
            "FROM   " +
            "   agent_area_payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1"
    )
    List<String> queryPayTypeName();

    @Select("SELECT   " +
            "   *    " +
            "FROM   " +
            "   agent_area_payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1"
    )
    List<PaymentType> queryList();

    @Select("SELECT   " +
            "   ID,   " +
            "   `NAME`    " +
            "FROM   " +
            "   agent_area_payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1"
    )
    List<PayTypeListVo> queryPayTypeListNoId();
}