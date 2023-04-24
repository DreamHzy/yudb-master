package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class MerchantComplaintFile {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer complaintId;

    private String url;

    private Date createTime;

}