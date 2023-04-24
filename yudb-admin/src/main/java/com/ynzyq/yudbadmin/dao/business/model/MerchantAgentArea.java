package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantAgentArea {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String uid;

    private Integer merchantId;

    private String merchantName;

    private String signatory;

    private String mobile;

    private Date signTime;

    private String idNumber;

    private Date startTime;

    private Date expireTime;

    private Date serviceExpireTime;

    private String province;

    private String city;

    private String area;

    private Integer contractStatus;

    private BigDecimal managementExpense;

    private BigDecimal needManagementExpense;

    private BigDecimal alreadyManagementExpense;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    private BigDecimal  agencyFees;

    private BigDecimal  depositFee;

    private Integer isOpenPosition;

    private Integer isEffect;

    private String accountName;

    private String publicAccount;

    private String bank;

    private String receiver;

    private String deliveryPhone;

    private String receiptProvince;

    private String receiptCity;

    private String receiptArea;

    private String receiptDetailedAddress;

    private Integer systemFactor;
}