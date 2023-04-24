package com.yunzyq.yudbapp.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class MerchantLoginRecord {
    private Integer id;

    private Integer merchantId;

    private Date loginTime;

    private Date createTime;

}