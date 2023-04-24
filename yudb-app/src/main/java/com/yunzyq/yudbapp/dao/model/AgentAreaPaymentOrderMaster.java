package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AgentAreaPaymentOrderMaster {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String uid;

    private String orderNo;

    private Integer merchantId;

    private String merchantName;

    private String merchantMobile;

    private Integer regionalManagerId;

    private String regionalManagerName;

    private String regionalManagerMobile;

    private String province;

    private String city;

    private String area;

    private Integer paymentTypeId;

    private String paymentTypeName;

    private Integer type;

    private Integer status;

    private Integer examine;

    private BigDecimal fees;

    private Date expireTime;

    private Date payTime;

    private Integer send;

    private Integer examineNum;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer createUser;

    private Integer updateUser;

    private Date naturalYearStart;

    private Date naturalYearEnd;

    private Boolean deleted;

    private BigDecimal paymentStandardMoney;

    private BigDecimal money;

    private BigDecimal payMoney;

    private Integer adjustmentCount;

    private String adjustmentMsg;

    private String cycle;

    private Date sendTime;

    private Integer channelId;

    private Integer isReport;

}