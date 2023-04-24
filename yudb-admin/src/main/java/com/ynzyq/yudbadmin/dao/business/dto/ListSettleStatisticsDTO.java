package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/16 11:58
 * @description:
 */
@Data
public class ListSettleStatisticsDTO implements Serializable {
    @ApiModelProperty("账单日开始时间")
    private String statisticsStartDate;

    @ApiModelProperty("账单日结束时间")
    private String statisticsEndDate;

    @ApiModelProperty("门店授权号")
    private String storeUid;

    @ApiModelProperty("结算状态：1：已完成，2：未结算，3：结算异常")
    private String settleStatus;

    @ApiModelProperty("钱包id")
    private String walletId;

    @ApiModelProperty("是否开通钱包：1：已开通，2：未开通")
    private String isOpenWallet;

    @ApiModelProperty("结算开始时间")
    private String settleStartTime;

    @ApiModelProperty("结算结束时间")
    private String settleEndTime;

    public void setStatisticsStartDate(String statisticsStartDate) {
        if (StringUtils.isNotBlank(statisticsStartDate)) {
            this.statisticsStartDate = statisticsStartDate;
        }
    }

    public void setStatisticsEndDate(String statisticsEndDate) {
        if (StringUtils.isNotBlank(statisticsEndDate)) {
            this.statisticsEndDate = statisticsEndDate;
        }
    }

    public void setStoreUid(String storeUid) {
        if (StringUtils.isNotBlank(storeUid)) {
            this.storeUid = storeUid;
        }
    }

    public void setSettleStatus(String settleStatus) {
        if (StringUtils.isNotBlank(settleStatus)) {
            this.settleStatus = settleStatus;
        }
    }

    public void setWalletId(String walletId) {
        if (StringUtils.isNotBlank(walletId)) {
            this.walletId = walletId;
        }
    }

    public void setIsOpenWallet(String isOpenWallet) {
        if (StringUtils.isNotBlank(isOpenWallet)) {
            this.isOpenWallet = isOpenWallet;
        }
    }

    public void setSettleStartTime(String settleStartTime) {
        if (StringUtils.isNotBlank(settleStartTime)) {
            this.settleStartTime = settleStartTime + " 00:00:00";
        }
    }

    public void setSettleEndTime(String settleEndTime) {
        if (StringUtils.isNotBlank(settleEndTime)) {
            this.settleEndTime = settleEndTime + " 23:59:59";
        }
    }
}
