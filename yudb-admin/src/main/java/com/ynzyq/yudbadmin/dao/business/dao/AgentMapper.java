package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.ListAgentDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ModifyDataDTO;
import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;
import com.ynzyq.yudbadmin.dao.business.vo.AgencyAreaVO;
import com.ynzyq.yudbadmin.dao.business.vo.BaseInfoVO;
import com.ynzyq.yudbadmin.dao.business.vo.ListAgentVO;
import com.ynzyq.yudbadmin.dao.business.vo.PaymentRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Mapper
public interface AgentMapper {

    /**
     * 代理管理列表
     *
     * @param listAgentDTO
     * @return
     */
    @Select("<script>SELECT " +
            " t1.ID, " +
            " t2.`NAME` AS MERCHANT_NAME, " +
            " t2.MOBILE, " +
            " t1.UID, " +
            " t1.AGENT_AREA," +
            " t2.IS_AGENT," +
            " t1.MANAGEMENT_EXPENSE," +
            " t1.START_TIME, " +
            " t1.EXPIRE_TIME," +
            " t1.AGENCY_FEES, " +
            " t1.DEPOSIT_FEE, " +
            " t1.SIGN_TIME , " +
            " t1.SERVICE_EXPIRE_TIME, " +
            " t4.ID AS regionalNameId, " +
            " t4.`NAME` AS regionalName, " +
            " t1.IS_OPEN_POSITION, " +
            " t1.ACCOUNT_NAME AS accountName ," +
            " t1.PUBLIC_ACCOUNT AS publicAccount ," +
            " t1.BANK AS bank ," +
            " t1.IS_EFFECT ," +
//            "t1.RECEIVER AS receiver," +
//            "t1.DELIVERY_PHONE AS phone," +
//            "t1.RECEIPT_PROVINCE AS receiptProvince," +
//            "t1.RECEIPT_CITY AS receiptCity," +
//            "t1.RECEIPT_AREA AS receiptArea," +
//            "t1.RECEIPT_DETAILED_ADDRESS AS address," +
            "t1.SYSTEM_FACTOR " +
            " FROM " +
            " merchant_agent_area t1 " +
            " INNER JOIN merchant t2 ON t1.MERCHANT_ID = t2.ID " +
            " LEFT JOIN merchant_agent_area_regional_manager t3 ON t1.ID = t3.MERCHANT_AGENT_AREA_ID " +
            " LEFT JOIN regional_manager t4 ON t3.REGIONAL_MANAGER_ID = t4.ID " +
            " WHERE " +
            " t3.`STATUS` = 1 " +
            "<if test='condition != null'>AND ( t2.`NAME` LIKE concat('%',#{condition},'%') OR t1.MOBILE LIKE concat('%',#{condition},'%') OR t1.UID LIKE concat('%',#{condition},'%') OR t1.AGENT_AREA LIKE concat('%',#{condition},'%') OR t4.`NAME` LIKE concat('%',#{condition},'%') )</if>" +
            "<if test='startTime != null and endTime != null '>AND t1.CREATE_TIME BETWEEN #{startTime} AND #{endTime}</if>" +
            "<if test='merchantId != null'>AND t1.MERCHANT_ID = #{merchantId} </if>" +
            "<if test='areaId != null'>AND t3.REGIONAL_MANAGER_ID = #{areaId} </if>" +
            "</script>")
    List<ListAgentVO> listAgent(ListAgentDTO listAgentDTO);

    @Select("SELECT " +
            " t1.ID, " +
            " t2.`NAME` AS MERCHANT_NAME, " +
            " t1.UID, " +
            " t1.AGENT_AREA, " +
            " t2.IS_AGENT, " +
            " t1.MANAGEMENT_EXPENSE, " +
            " t1.START_TIME, " +
            " t1.EXPIRE_TIME, " +
            " t1.AGENCY_FEES, " +
            " t1.DEPOSIT_FEE, " +
            " t1.SIGN_TIME, " +
            " t1.SERVICE_EXPIRE_TIME, " +
            " t4.ID AS regionalNameId, " +
            " t4.`NAME` AS regionalName, " +
            " t1.IS_OPEN_POSITION, " +
            " t1.IS_EFFECT  " +
            "FROM " +
            " merchant_agent_area t1 " +
            " INNER JOIN merchant t2 ON t1.MERCHANT_ID = t2.ID " +
            " LEFT JOIN merchant_agent_area_regional_manager t3 ON t1.ID = t3.MERCHANT_AGENT_AREA_ID " +
            " LEFT JOIN regional_manager t4 ON t3.REGIONAL_MANAGER_ID = t4.ID  " +
            "WHERE " +
            " t3.`STATUS` = 1 " +
            " AND t1.ID = #{id}")
    BaseInfoVO singleStore(Integer id);

    /**
     * 查询区域经理名称
     *
     * @param regionalId
     * @return
     */
    @Select("SELECT " +
            " `NAME`  " +
            "FROM " +
            " regional_manager  " +
            "WHERE " +
            " ID = ( " +
            " SELECT " +
            "  REGIONAL_MANAGER_ID  " +
            " FROM " +
            "  merchant_agent_area_regional_manager  " +
            "WHERE " +
            " MERCHANT_AGENT_AREA_ID = #{managerId}" +
            " AND `STATUS` = 1)")
    String getRegionalName(Integer regionalId);

    /**
     * 代理商代理区域
     *
     * @param areaId
     * @return
     */
    @Select("SELECT UID,PROVINCE,CITY,AREA FROM merchant_agent_area_detail WHERE AGENT_AREA_ID = #{areaId} AND `STATUS` = 1")
    List<AgencyAreaVO> areaList(Integer areaId);

    /**
     * 查询缴费记录
     *
     * @param uid
     * @return
     */
    @Select("SELECT " +
            " t1.PAYMENT_TYPE_NAME, " +
            " t1.CYCLE, " +
            " t1.PAYMENT_STANDARD_MONEY, " +
            " IF( t1.ADJUSTMENT_COUNT > 0, \"是\", \"否\" ) AS isAdjust, " +
            " t1.MONEY, " +
            " t1.MONEY AS paymentAmount, " +
            " t2.PAY_TIME, " +
            " t1.ADJUSTMENT_MSG  " +
            "FROM " +
            " agent_area_payment_order_master t1 " +
            " INNER JOIN agent_area_payment_order_pay t2 ON t1.id = t2.PAYMENT_ORDER_MASTER_ID  " +
            "WHERE " +
            " t1.UID = #{uid} " +
            " AND t1.`STATUS` = 2  " +
            " AND t2.`STATUS` = 3")
    List<PaymentRecordVO> listPaymentRecordVO(String uid);

    /**
     * 校验区域经理
     *
     * @param id
     * @return
     */
    @Select("SELECT " +
            " *  " +
            "FROM " +
            " regional_manager  " +
            "WHERE " +
            " ID = #{id}")
    RegionalManager validateRegionalCount(Integer id);

    @Select("SELECT " +
            "  REGIONAL_MANAGER_ID  " +
            " FROM " +
            "  merchant_agent_area_regional_manager  " +
            "WHERE " +
            " MERCHANT_AGENT_AREA_ID = #{managerId}" +
            " AND `STATUS` = 1 ")
    String getRegionalNameId(Integer id);


    @Update("UPDATE merchant_agent_area m1 SET m1.RECEIVER = #{receipt} , m1.DELIVERY_PHONE=#{receiptPhone},m1.RECEIPT_PROVINCE=#{province},m1.RECEIPT_CITY=#{city},m1.RECEIPT_AREA=#{area},m1.RECEIPT_DETAILED_ADDRESS=#{receiptAddress} WHERE m1.ID=#{id}")
    boolean modifyDataMapper(ModifyDataDTO modifyDataDTO);
}
