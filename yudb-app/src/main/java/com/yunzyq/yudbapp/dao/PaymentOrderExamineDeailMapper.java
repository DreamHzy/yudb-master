package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.PaymentOrderExamineDeail;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PaymentOrderExamineDeailMapper extends MyBaseMapper<PaymentOrderExamineDeail> {


    @Update("UPDATE payment_order_examine_deail  " +
            "SET DELETED = 1  " +
            "WHERE " +
            "  PAYMENT_ORDER_EXAMINE_ID =#{id} ")
    void updateDeletedByPaymentOrderExamineId(Integer id);

    @Select("SELECT " +
            " t2.USER_NAME  " +
            "FROM " +
            " approve_process t1 " +
            " INNER JOIN approve_process_step t2 ON t1.ID = t2.APPROVE_ID  " +
            "WHERE " +
            " t1.`STATUS` = 1  " +
            " AND t2.`STATUS` = 1  " +
            " AND t1.TYPE = #{userType}  " +
            " AND t1.EXAMINE_TYPE = #{examineType} " +
            " AND t2.STEP = #{step}  " +
            " AND t2.TYPE = #{approveType} ")
    List<String> getApproveName(Integer userType, Integer examineType, Integer step, Integer approveType);

}