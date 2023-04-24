package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class MerchantStoreCloudSchool {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer merchantStoreId;

    private String merchantStoreUid;

    private String account;

    private Integer status;

    private Date endTime;

    private Date createDate;

    private Date createTime;


}