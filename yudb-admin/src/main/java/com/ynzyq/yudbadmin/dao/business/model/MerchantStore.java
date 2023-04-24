package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantStore {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String type;

    private String uid;

    private String name;

    private Integer merchantId;

    private String signatory;

    private String mobile;

    private Date signTime;

    private Date startTime;

    private Date expireTime;

    private Date serviceExpireTime;

    private Integer collectionMonth;

    private String delayedOpen;

    private String province;

    private String city;

    private String address;

    private Integer status;

    private Integer statusTwo;

    private BigDecimal managementExpense;

    private BigDecimal alreadyManagementExpense;

    private BigDecimal needManagementExpense;

    private Integer merchantLink;

    private Date merchantLinkTime;

    private Date merchantLinkEndTime;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    private String area;

    private String idNumber;

    private Integer contractStatus;

    private BigDecimal merchantMoney;

    private BigDecimal cloudSchoolMoney;

    private BigDecimal franchiseFee;

    private BigDecimal bondMoney;

    private Date openTime;

    private Date openAgainTime;

    private Date closeTime;

    private Date estimateTime;

    private Date relocationTime;

    private Date pauseTime;

    private Integer agentId;

    private String agentName;

    private Integer isPreferential;

    private Integer seatCount;
    private String hllCode;
    private String mtId;
    private String elmId;
    private String dzdpId;
    private String remark;
    private String deliveryAddress;
    private String distributionLimit;
    private String receiver;
    private String receiverMobile;
    private Integer showDeliveryAddress;
    private String deliveryProvince;
    private String deliveryCity;
    private String deliveryArea;
    private Integer isOpenWallet;
    private String walletId;
    private String walletName;
    private Integer agentAreaId;
}