package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentOrderMaster {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String orderNo;

    private Integer merchantId;

    private String merchantName;

    private Integer merchantStoreId;

    private String merchantStoreName;

    private String merchantStoreMobile;

    private Integer regionalManagerId;

    private String regionalManagerName;

    private String regionalManagerMobile;

    private Integer paymentTypeId;

    private String paymentTypeName;

    private Integer type;

    private Integer status;

    private Integer examine;

    private BigDecimal money;

    private BigDecimal payMoney;

    private Date expireTime;

    private Date payTime;

    private Integer send;

    private Integer examineNum;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer createUser;

    private Integer updateUser;

    private String merchantStoreUid;

    private Date naturalYearStart;

    private Date naturalYearEnd;

    private Boolean deleted;

    private Integer otherId;

    private BigDecimal fees;

    private BigDecimal paymentStandardMoney;

    private Integer adjustmentCount;

    private String adjustmentMsg;

    private String cycle;

    private String province;

    private String city;

    private String area;

    private String address;

    private Date sendTime;

    private Integer channelId;

    private Integer isReport;

    private Integer isChange;

    private Integer isPublish;

}