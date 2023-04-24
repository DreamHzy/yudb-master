package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.dto.ListSettleStatisticsDTO;
import com.ynzyq.yudbadmin.dao.business.dto.SettleStatisticsDetailDTO;
import com.ynzyq.yudbadmin.dao.business.dto.SettleStatusStatistics;
import com.ynzyq.yudbadmin.dao.business.dto.WalletOrderStatisticsDTO;
import com.ynzyq.yudbadmin.dao.business.vo.ListSettleStatisticsVO;
import com.ynzyq.yudbadmin.dao.business.vo.SettleStatisticsDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author xinchen
 * @date 2022/6/16 14:21
 * @description:
 */
@Mapper
public interface WalletMapper {
    @Select("<script>" +
            "SELECT " +
            " t1.ID AS statisticsId, " +
            " t1.STATISTICS_DATE, " +
            " t1.STORE_UID, " +
            " t1.SETTLE_STATUS, " +
            " t1.WALLET_ID, " +
            " t1.ORDER_MONEY, " +
            " t1.ORDER_FEE, " +
            " t1.SETTLE_MONEY, " +
            " t1.SETTLE_TIME, " +
            " t1.OPERATOR  " +
            "FROM " +
            " wallet_order_statistics t1 " +
            " INNER JOIN merchant_store t2 ON t1.STORE_UID = t2.UID  " +
            "<where> " +
            " <if test='statisticsStartDate != null and statisticsEndDate != null'> AND t1.STATISTICS_DATE BETWEEN #{statisticsStartDate} AND #{statisticsEndDate}</if> " +
            " <if test='storeUid != null'> AND t1.STORE_UID = #{storeUid}</if> " +
            " <if test='settleStatus != null'> AND t1.SETTLE_STATUS = #{settleStatus}</if> " +
            " <if test='walletId != null'> AND t1.WALLET_ID = #{walletId}</if> " +
            " <if test='isOpenWallet != null'> AND t2.IS_OPEN_WALLET = #{isOpenWallet}</if> " +
            " <if test='settleStartTime != null and settleEndTime != null'> AND t1.SETTLE_TIME BETWEEN #{settleStartTime} AND #{settleEndTime}</if> " +
            "</where> " +
            "ORDER BY " +
            " t1.ID DESC" +
            "</script>")
    List<ListSettleStatisticsVO> listSettleStatistics(ListSettleStatisticsDTO listSettleStatisticsDTO);

    @Select("<script>" +
            "SELECT " +
            " ID, " +
            " ORDER_NO, " +
            " ORDER_TIME, " +
            " ORDER_MONEY, " +
            " ORDER_FEE, " +
            " ACTUAL_MONEY, " +
            " SETTLE_STATUS  " +
            "FROM " +
            " wallet_order  " +
            "WHERE " +
            " STATISTICS_ID = #{statisticsId} " +
            " <if test='orderNo != null'> AND ORDER_NO LIKE CONCAT('%',#{orderNo},'%')</if> " +
            " <if test='settleStatusList != null and settleStatusList.size > 0 '> " +
            " AND SETTLE_STATUS IN " +
            "<foreach collection=\"settleStatusList\" item=\"settleStatus\" open=\"(\" close=\")\" separator=\",\">" +
            " #{settleStatus}" +
            "</foreach>" +
            "</if> " +
            "</script>")
    List<SettleStatisticsDetailVO> listSettleOrder(SettleStatisticsDetailDTO settleStatisticsDetailDTO);

    @Select("SELECT " +
            " ORDER_DATE, " +
            " STORE_UID, " +
            " SUM( ORDER_MONEY ) ORDER_MONEY, " +
            " SUM( ORDER_FEE ) ORDER_FEE, " +
            " ANY_VALUE (( SELECT WALLET_ID FROM merchant_store WHERE UID = t1.STORE_UID )) WALLET_ID " +
            "FROM " +
            " wallet_order t1 " +
            "WHERE " +
            " SETTLE_STATUS > 1  " +
            "GROUP BY " +
            " STORE_UID, " +
            " ORDER_DATE")
    List<WalletOrderStatisticsDTO> walletOrderStatistics();

    @Select("SELECT " +
            " COUNT(*) count, " +
            " COUNT( CASE WHEN SETTLE_STATUS = 1 THEN ID END ) settleCount, " +
            " COUNT( CASE WHEN SETTLE_STATUS = 2 THEN ID END ) notSettleCount " +
            "FROM " +
            " wallet_order  " +
            "WHERE " +
            " STATISTICS_ID = #{statisticsId}")
    SettleStatusStatistics settleStatusStatistics(Integer statisticsId);
}
