package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.PaymentOrderExamineFile;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PaymentOrderExamineFileMapper extends MyBaseMapper<PaymentOrderExamineFile> {

    @Update("UPDATE payment_order_examine_file  " +
            "SET DELETED = 1  " +
            "WHERE " +
            "  PAYMENT_ORDER_EXAMINE_ID =#{id} ")
    void updateDeletedByPaymentOrderExamineId(Integer id);

    @Select("SELECT URL FROM payment_order_examine_file WHERE PAYMENT_ORDER_EXAMINE_ID = #{id} AND TYPE = #{type} AND `STATUS` = 1")
    List<String> examineFile(Integer id, Integer type);
}