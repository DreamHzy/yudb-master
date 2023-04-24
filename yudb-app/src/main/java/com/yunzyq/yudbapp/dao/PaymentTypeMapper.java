package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.PaymentType;
import com.yunzyq.yudbapp.dao.vo.PayTypeListVo;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PaymentTypeMapper extends MyBaseMapper<PaymentType> {

    @Select("SELECT   " +
            "   ID,   " +
            "   `NAME`    " +
            "FROM   " +
            "   payment_type    " +
            "WHERE   " +
            "   `STATUS` = 1" +
            "   AND MANUAL = 2")
    List<PayTypeListVo> queryPayTypeList();
}