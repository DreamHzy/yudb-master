package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentOrderPay {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Integer paymentOrderMasterId;

    private String orderNo;

    private Integer payChannelId;

    private String payChannelName;

    private String transactionId;

    private BigDecimal totalMoney;

    private Integer payType;

    private Integer status;

    private String channelApiUrl;

    private String channelNotifyUrl;

    private String msg;

    private Date payTime;

    private Date createTime;

    private Date updateTime;

    private String channelOrderNo;

    private String remark;

    private String payChannelRoute;

    private BigDecimal fees;
}