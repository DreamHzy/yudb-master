package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;

import java.util.Date;

@Data
public class MerchantStoreExamineFile {
    private Integer id;

    private Integer merchantStoreExamineId;

    private Integer type;

    private String name;

    private String url;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}