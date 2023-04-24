package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayChannel {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String channelName;

    private String channelNo;

    private String channelKey;

    private String channelApiUrl;

    private String channelNotifyUrl;

    private Integer channelStatus;

    private String channelRoute;

    private String reservedOne;

    private String reservedTwo;

    private Date createTime;

    private Date updateTime;

    private Integer payType;

    private BigDecimal rate;


}