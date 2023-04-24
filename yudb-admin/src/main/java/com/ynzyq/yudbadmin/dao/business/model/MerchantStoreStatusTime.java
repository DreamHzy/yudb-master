package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;

import java.util.Date;

@Data
public class MerchantStoreStatusTime {
    private Integer id;

    private Integer merchantStoreId;

    private Integer storeStatus;

    private Integer storeStatusTwo;

    private Integer status;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

}