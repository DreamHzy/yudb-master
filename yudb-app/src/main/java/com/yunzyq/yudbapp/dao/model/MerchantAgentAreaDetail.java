package com.yunzyq.yudbapp.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class MerchantAgentAreaDetail {
    private Integer id;

    private String uid;

    private Integer agentAreaId;

    private String province;

    private String city;

    private String area;

    private Integer status;

    private Date createTime;

}