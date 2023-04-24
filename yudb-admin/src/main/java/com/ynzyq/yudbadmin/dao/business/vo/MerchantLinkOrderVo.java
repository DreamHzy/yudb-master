package com.ynzyq.yudbadmin.dao.business.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantLinkOrderVo {
    private String merchantId;
    private String merchantName;
    private String merchantStoreId;
    private String merchantStoreName;
    private String merchantStoreUid;
    private String merchantStoreMobile;
    private String regionalManagerId;
    private String regionalManagerName;
    private String regionalManagerMobile;
    private BigDecimal money;
    private Integer status;
    private Date merchantLinkEndTime;
}
