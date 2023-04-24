package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.dto.ExamineDTO;
import com.yunzyq.yudbapp.dao.dto.ExamineDetailDTO;
import com.yunzyq.yudbapp.dao.model.PaymentOrderExamine;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PaymentOrderExamineMapper extends MyBaseMapper<PaymentOrderExamine> {


    @Select("SELECT*FROM payment_order_examine WHERE PAYMENT_ORDER_MASTER_ID = #{id} AND STATUS=#{status} AND DELETED = 0 ORDER BY ID DESC LIMIT 1")
    PaymentOrderExamine queryByMasterIdAndStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Select("SELECT ID,CREATE_TIME,EXAMINE_TYPE FROM payment_order_examine WHERE PAYMENT_ORDER_MASTER_ID = #{id} AND EXAMINE_TYPE != 3")
    List<ExamineDTO> listExamine(Integer id);

    @Select("SELECT IFNULL(CREATE_NAME,APPROVE_NAME) CREATE_NAME,EXAMINE_TIME,`STATUS`,REMARK FROM payment_order_examine_deail WHERE PAYMENT_ORDER_EXAMINE_ID = #{id} AND IFNULL(CREATE_NAME,APPROVE_NAME) IS NOT NULL")
    List<ExamineDetailDTO> listExamineDetail(Integer id);

}