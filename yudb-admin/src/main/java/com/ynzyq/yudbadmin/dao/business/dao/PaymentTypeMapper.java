package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.PaymentType;
import com.ynzyq.yudbadmin.dao.business.vo.PayTypeListVo;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PaymentTypeMapper extends MyBaseMapper<PaymentType> {

    @Select("SELECT   " +
            "   *  " +
            "FROM   " +
            "   payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1" +
            "   AND ID = #{id}")
    PaymentType queryPayTypeList(String id);

    @Select("SELECT   " +
            "   ID,   " +
            "   `NAME`    " +
            "FROM   " +
            "   payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1"
    )
    List<PayTypeListVo> queryPayTypeListNoId();


    @Select("SELECT   " +
            "   `NAME`    " +
            "FROM   " +
            "   payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1"
    )
    List<String> queryPayTypeName();


    @Select("SELECT   " +
            "   *    " +
            "FROM   " +
            "   payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1"
    )
    List<PaymentType> queryList();
}