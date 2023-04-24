package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class WalletOrder {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String orderNo;

    private String storeUid;

    private BigDecimal orderFee;

    private BigDecimal orderMoney;

    private BigDecimal actualMoney;

    private Integer settleStatus;

    private String mctOrderNo;

    private Date orderDate;

    private Date orderTime;

    private Date createTime;

    private Date updateTime;

    private Integer statisticsId;
}