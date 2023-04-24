package com.ynzyq.yudbadmin.dao.business.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AgentOrderExpireTimeVo {
    private String merchantId;
    private String merchantName;
    private String uid;
    private String mobile;
    private String regionalManagerId;
    private String regionalManagerName;
    private String regionalManagerMobile;
    private BigDecimal money;
    private Integer status;
    private String province;
    private String city;
    private String area;
    private Date serviceExpireTime;
    private Integer isEffect;
}
