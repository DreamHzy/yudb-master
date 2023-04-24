package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletOrderStatistics {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Date statisticsDate;

    private String storeUid;

    private Integer settleStatus;

    private String walletId;

    private BigDecimal orderMoney;

    private BigDecimal orderFee;

    private BigDecimal settleMoney;

    private Date settleTime;

    private Date createTime;

    private Date updateTime;

    private Integer operatorId;

    private String operator;
}