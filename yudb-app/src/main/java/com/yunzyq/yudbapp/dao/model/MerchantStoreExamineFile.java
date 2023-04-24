package com.yunzyq.yudbapp.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class MerchantStoreExamineFile {
    private Integer id;

    private Integer merchantStoreExamineId;

    private Integer type;

    private String url;

    private String name;

    private Integer status;

    private Date createTime;

    private Date updateTime;
    private Boolean deleted;

}