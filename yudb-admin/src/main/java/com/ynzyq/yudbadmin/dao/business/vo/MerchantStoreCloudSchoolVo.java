package com.ynzyq.yudbadmin.dao.business.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantStoreCloudSchoolVo {
    private String id;
    private String merchantId;
    private String merchantName;
    private String merchantStoreId;
    private String merchantStoreName;
    private String merchantStoreUid;
    private String merchantStoreMobile;
    private String regionalManagerId;
    private String regionalManagerName;
    private String regionalManagerMobile;
    private Date endTime;


    private String province;
    private String city;
    private String area;
    private String address;

    private Integer status;
}
