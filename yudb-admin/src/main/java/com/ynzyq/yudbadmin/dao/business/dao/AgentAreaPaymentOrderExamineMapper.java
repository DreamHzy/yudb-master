package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.PayReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.ShowStoreExamineDTO;
import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderExamine;
import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderExamineExample;

import java.util.List;

import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AgentAreaPaymentOrderExamineMapper extends MyBaseMapper<AgentAreaPaymentOrderExamine> {

    @Select("" +
            "<script>" +
            "SELECT " +
            "  p.ID, " +
            "  p.APPLY_NAME 'applicant', " +
            "  p.UID 'uid', " +
            "  p.EXAMINE_TYPE, " +
            "  p.NEW_MONEY 'money', " +
            "  p.PAYMENT_TYPE_NAME 'payTypeName', " +
            "  p.CREATE_TIME, " +
            "  ( SELECT pd.EXPIRE_TIME FROM agent_area_payment_order_master pd WHERE pd.ID = p.PAYMENT_ORDER_MASTER_ID ) expireTime, " +
            "  p.MSG 'reviewMsg', " +
//            "  ( SELECT pd.`STATUS` FROM agent_area_payment_order_examine_deail pd WHERE pd.PAYMENT_ORDER_EXAMINE_ID = p.id AND EXAMINE = 1 and pd.DELETED=0) EXAMINE_ONE_STATUS, " +
//            "  ( SELECT pd.`STATUS` FROM agent_area_payment_order_examine_deail pd WHERE pd.PAYMENT_ORDER_EXAMINE_ID = p.id AND EXAMINE = 2 and pd.DELETED=0 ) EXAMINE_TWO_STATUS  " +
            "  t1.`STATUS` EXAMINE_ONE_STATUS, " +
            "  t2.`STATUS` EXAMINE_TWO_STATUS " +
            "FROM " +
            "  agent_area_payment_order_examine p  " +
            "  LEFT JOIN ( SELECT pd.PAYMENT_ORDER_EXAMINE_ID, pd.`STATUS` FROM agent_area_payment_order_examine_deail pd WHERE EXAMINE = 1 AND pd.DELETED = 0 ) t1 ON t1.PAYMENT_ORDER_EXAMINE_ID = p.id " +
            "  LEFT JOIN ( SELECT pd.PAYMENT_ORDER_EXAMINE_ID, pd.`STATUS` FROM agent_area_payment_order_examine_deail pd WHERE EXAMINE = 2 AND pd.DELETED = 0 ) t2 ON t2.PAYMENT_ORDER_EXAMINE_ID = p.id " +
            "WHERE " +
            " 1=1 " +
            "  AND p.EXAMINE != - 1  " +
            "  AND p.DELETED = 0  " +
            " <if test='examineType !=null'>AND p.EXAMINE_TYPE = #{examineType}</if>" +
            " <if test='examineOneStatus !=null'>AND t1.`STATUS` = #{examineOneStatus}</if>" +
            " <if test='examineTwoStatus !=null'>AND t2.`STATUS` = #{examineTwoStatus}</if>" +
            " <if test='condition !=null'>" +
            " AND ( " +
            "  p.APPLY_NAME  like concat('%',#{condition},'%') " +
            "  OR p.UID like concat('%',#{condition},'%') " +
            " )  " +
            " </if>" +
            " <if test='paymentTypeId !=null'>" +
            " AND p.PAYMENT_TYPE_ID =#{paymentTypeId} " +
            " </if>" +
            " <if test='startTime !=null'>" +
            " AND p.CREATE_TIME BETWEEN #{startTime} AND #{endTime} " +
            " </if>" +
            "ORDER BY " +
            "  p.CREATE_TIME DESC" +
            "</script>")
    List<PayReviewPageVo> findPayReviewPageVo(PayReviewPageDto payReviewPageDto);

    @Select("<script>" +
            "SELECT " +
            " t1.ID, " +
            " t1.APPLY_NAME, " +
            " t2.UID AS MERCHANT_STORE_UID, " +
//            " t2.ADDRESS, " +
            " t1.EXAMINE_TYPE, " +
            " t1.PAYMENT_TYPE_ID, " +
            " t1.PAYMENT_TYPE_NAME, " +
            " t2.MONEY, " +
            " t1.`STATUS`, " +
            " t1.CREATE_TIME, " +
            " t2.EXPIRE_TIME, " +
            " p3.APPROVE_NAME  " +
            "FROM " +
            " agent_area_payment_order_examine t1 " +
            " LEFT JOIN agent_area_payment_order_master t2 ON t1.PAYMENT_ORDER_MASTER_ID = t2.ID " +
            " LEFT JOIN ( " +
            " SELECT " +
            "  p1.PAYMENT_ORDER_EXAMINE_ID, " +
            "  p1.APPROVE_NAME  " +
            " FROM " +
            "  agent_area_payment_order_examine_deail p1 " +
            "  INNER JOIN ( SELECT PAYMENT_ORDER_EXAMINE_ID, MAX( ID ) ID FROM agent_area_payment_order_examine_deail WHERE DELETED = 0 GROUP BY PAYMENT_ORDER_EXAMINE_ID ) p2  " +
            " WHERE " +
            "  p1.PAYMENT_ORDER_EXAMINE_ID = p2.PAYMENT_ORDER_EXAMINE_ID  " +
            "  AND p1.ID = p2.ID  " +
            " ) p3 ON t1.ID = p3.PAYMENT_ORDER_EXAMINE_ID " +
            "<where>" +
            " t1.DELETED = 0 " +
            "<if test='applyName != null'> AND t1.APPLY_NAME LIKE CONCAT('%',#{applyName},'%') </if>" +
            "<if test='merchantStoreUid != null'> AND t2.UID LIKE CONCAT('%',#{merchantStoreUid},'%') </if>" +
            "<if test='paymentTypeId != null'> AND t1.PAYMENT_TYPE_ID = #{paymentTypeId} </if>" +
            "<if test='examineType != null'> AND t1.EXAMINE_TYPE = #{examineType} </if>" +
            "<if test='status != null'> AND t1.`STATUS` = #{status} </if>" +
            "<if test='approveName != null'> AND p3.APPROVE_NAME LIKE CONCAT('%',#{approveName},'%') </if>" +
            "<if test='startTime != null and endTime != null'> AND t2.EXPIRE_TIME BETWEEN #{startTime} AND #{endTime} </if>" +
            "</where>" +
            " ORDER BY t1.CREATE_TIME DESC" +
            "</script>")
    List<ShowStoreExamineVO> showStoreExamine(ShowStoreExamineDTO showStoreExamineDTO);

    @Select("<script>" +
            "SELECT " +
            " COUNT(*)  " +
            "FROM " +
            " agent_area_payment_order_examine t1 " +
            " LEFT JOIN agent_area_payment_order_master t2 ON t1.PAYMENT_ORDER_MASTER_ID = t2.ID" +
            "<where>" +
            " t1.DELETED = 0 " +
            "<if test='applyName != null'> AND t1.APPLY_NAME LIKE CONCAT('%',#{applyName},'%') </if>" +
            "<if test='merchantStoreUid != null'> AND t2.UID LIKE CONCAT('%',#{merchantStoreUid},'%') </if>" +
            "<if test='paymentTypeId != null'> AND t1.PAYMENT_TYPE_ID = #{paymentTypeId} </if>" +
            "<if test='examineType != null'> AND t1.EXAMINE_TYPE = #{examineType} </if>" +
            "<if test='status != null'> AND t1.`STATUS` = #{status} </if>" +
            "<if test='startTime != null and endTime != null'> AND t2.EXPIRE_TIME BETWEEN #{startTime} AND #{endTime} </if>" +
            "</where>" +
            "</script>")
    int showStoreExamineCount(ShowStoreExamineDTO showStoreExamineDTO);

    @Select("SELECT " +
            " t2.USER_NAME  " +
            "FROM " +
            " agent_area_payment_order_examine t1 " +
            " INNER JOIN approve_process_step t2 ON t1.STEP = t2.STEP " +
            " INNER JOIN approve_process t3 ON t1.EXAMINE_TYPE = t3.EXAMINE_TYPE  " +
            " AND t2.APPROVE_ID = t3.ID  " +
            "WHERE " +
            " t3.TYPE = 2  " +
            " AND t1.ID = #{id}  " +
            " AND t2.`STATUS` = 1 " +
            " AND t3.`STATUS` = 1" +
            " AND t2.TYPE = ( " +
            " SELECT " +
            "  TYPE  " +
            " FROM " +
            "  agent_area_payment_order_examine_deail  " +
            " WHERE " +
            "  PAYMENT_ORDER_EXAMINE_ID = t1.ID  " +
            " ORDER BY ID DESC LIMIT 1  " +
            " )")
    String getApproveName(Integer id);

    @Select("<script>" +
            "SELECT " +
            " *  " +
            "FROM " +
            " (" +
//            "SELECT " +
//            " t1.ID, " +
//            " null AS approveName, " +
//            " t1.APPLY_NAME, " +
//            " t2.UID AS merchantStoreUid, " +
////            " t2.ADDRESS, " +
//            " t1.EXAMINE_TYPE, " +
//            " t1.PAYMENT_TYPE_ID, " +
//            " t1.PAYMENT_TYPE_NAME, " +
//            " t2.MONEY, " +
//            " t1.`STATUS`, " +
//            " t1.CREATE_TIME, " +
//            " t2.EXPIRE_TIME  " +
//            "FROM " +
//            " agent_area_payment_order_examine t1 " +
//            " LEFT JOIN agent_area_payment_order_master t2 ON t1.PAYMENT_ORDER_MASTER_ID = t2.ID " +
//            " INNER JOIN ( " +
//            " SELECT " +
//            "  t2.EXAMINE_TYPE, " +
//            "  t1.STEP  " +
//            " FROM " +
//            "  approve_process_step t1 " +
//            "  INNER JOIN approve_process t2 ON t1.APPROVE_ID = t2.ID  " +
//            " WHERE " +
//            "  t1.`STATUS` = 1  " +
//            "  AND t2.`STATUS` = 1  " +
//            "  AND t2.TYPE = 2  " +
//            "  AND t1.STEP = 1  " +
//            "  AND t1.TYPE = 1  " +
//            "  AND t1.USER_ID = #{userId}  " +
//            " ) p ON t1.STEP = p.STEP  " +
//            " AND t1.EXAMINE_TYPE = p.EXAMINE_TYPE " +
//            " WHERE " +
//            "  t1.EXAMINE = 1 UNION ALL " +
            "SELECT " +
            " t1.ID, " +
            " null AS approveName, " +
            " t1.APPLY_NAME, " +
            " t2.UID AS merchantStoreUid, " +
//            " t2.ADDRESS, " +
            " t1.EXAMINE_TYPE, " +
            " t1.PAYMENT_TYPE_ID, " +
            " t1.PAYMENT_TYPE_NAME, " +
            " t2.MONEY, " +
            " t3.`STATUS`, " +
            " t1.CREATE_TIME, " +
            " t2.EXPIRE_TIME  " +
            "FROM " +
            " agent_area_payment_order_examine t1 " +
            " LEFT JOIN agent_area_payment_order_master t2 ON t1.PAYMENT_ORDER_MASTER_ID = t2.ID " +
            " INNER JOIN agent_area_payment_order_examine_deail t3 ON t1.ID = t3.PAYMENT_ORDER_EXAMINE_ID  " +
            "WHERE " +
            " t3.CREATE_USER = #{userId} " +
            " AND t3.DELETED = 0 UNION ALL " +
            " SELECT " +
            "  t3.ID, " +
            "  t1.USER_NAME AS approveName, " +
            "  t3.APPLY_NAME, " +
            "  t5.UID AS merchantStoreUid, " +
//            "  t5.ADDRESS, " +
            "  t3.EXAMINE_TYPE, " +
            "  t3.PAYMENT_TYPE_ID, " +
            "  t3.PAYMENT_TYPE_NAME, " +
            "  t5.MONEY, " +
            "  t4.`STATUS`, " +
            "  t3.CREATE_TIME, " +
            "  t5.EXPIRE_TIME  " +
            " FROM " +
            "  approve_process_step t1 " +
            "  INNER JOIN approve_process t2 ON t1.APPROVE_ID = t2.ID " +
            "  INNER JOIN agent_area_payment_order_examine t3 ON t2.EXAMINE_TYPE = t3.EXAMINE_TYPE " +
            "  INNER JOIN agent_area_payment_order_examine_deail t4 ON t3.ID = t4.PAYMENT_ORDER_EXAMINE_ID  " +
            "  AND t1.STEP = t4.STEP  " +
            "  AND t1.TYPE = t4.TYPE " +
            "  INNER JOIN agent_area_payment_order_master t5 ON t3.PAYMENT_ORDER_MASTER_ID = t5.ID  " +
            " WHERE " +
            "  t1.`STATUS` = 1  " +
            "  AND t2.`STATUS` = 1  " +
            "  AND t2.TYPE = 2  " +
            "  AND t4.`STATUS` = 1 " +
            "  AND t1.USER_ID = #{userId} " +
            "  AND t4.DELETED = 0 " +
            " ) p " +
            "<where>" +
            "<if test='applyName != null'> AND p.APPLY_NAME LIKE CONCAT('%',#{applyName},'%') </if>" +
            "<if test='merchantStoreUid != null'> AND p.merchantStoreUid LIKE CONCAT('%',#{merchantStoreUid},'%') </if>" +
            "<if test='paymentTypeId != null'> AND p.PAYMENT_TYPE_ID = #{paymentTypeId} </if>" +
            "<if test='examineType != null'> AND p.EXAMINE_TYPE = #{examineType} </if>" +
            "<if test='status != null'> AND p.`STATUS` = #{status} </if>" +
            "<if test='startTime != null and endTime != null'> AND p.EXPIRE_TIME BETWEEN #{startTime} AND #{endTime} </if>" +
            "</where>" +
            " ORDER BY p.CREATE_TIME DESC" +
            "</script>")
    List<ShowStoreExamineVO> listStoreExamine(ShowStoreExamineDTO showStoreExamineDTO);

    @Select("<script>" +
            "SELECT " +
            " t1.ID, " +
            " t1.APPLY_NAME, " +
            " t2.UID AS MERCHANT_STORE_UID, " +
            " t2.MERCHANT_NAME, " +
//            " t2.ADDRESS, " +
            " t1.EXAMINE_TYPE, " +
//            " t1.PAYMENT_TYPE_ID, " +
            " t1.PAYMENT_TYPE_NAME, " +
            " t2.MONEY, " +
//            " t1.`STATUS`, " +
            " t1.MSG, " +
            " t1.REMARK, " +
            " t1.CREATE_TIME, " +
            " t2.EXPIRE_TIME  " +
            "FROM " +
            " agent_area_payment_order_examine t1 " +
            " LEFT JOIN agent_area_payment_order_master t2 ON t1.PAYMENT_ORDER_MASTER_ID = t2.ID " +
            "WHERE t1.ID = #{id}" +
            "</script>")
    StoreExamineDetail storeExamineDetail(Integer id);

    @Select("SELECT " +
            " t1.ID, " +
            " t2.STEP, " +
            " t2.`NAME`, " +
            " t2.TYPE, " +
            " t2.USER_NAME, " +
            " t1.`STATUS`, " +
            " t1.REMARK, " +
            " t1.EXAMINE_TIME  " +
            "FROM " +
            " agent_area_payment_order_examine_deail t1 " +
            " INNER JOIN approve_process_step t2 ON t1.STEP_ID = t2.ID  " +
            "WHERE " +
            " t1.PAYMENT_ORDER_EXAMINE_ID = #{id}")
    List<ApproveStepDetail> listApproveStepDetail(Integer id);

    @Select("SELECT " +
            " ID, " +
            " URL  " +
            "FROM " +
            " agent_area_payment_order_examine_deail_file  " +
            "WHERE " +
            " AGENT_AREA_PAYMENT_ORDER_EXAMINE_DEAIL_ID = #{id}  " +
            " AND TYPE = #{type}  " +
            " AND `STATUS` = 1  " +
            " AND DELETED = 0")
    List<ExamineFileDetail> listExamineDetailFiles(Integer id, Integer type);

    @Select("SELECT " +
            " ID, " +
            " URL  " +
            "FROM " +
            " agent_area_payment_order_examine_file  " +
            "WHERE " +
            " PAYMENT_ORDER_EXAMINE_ID = #{id}  " +
            " AND TYPE = #{type}  " +
            " AND `STATUS` = 1  " +
            " AND DELETED = 0")
    List<ExamineFileDetail> listExamineFiles(Integer id, Integer type);


}