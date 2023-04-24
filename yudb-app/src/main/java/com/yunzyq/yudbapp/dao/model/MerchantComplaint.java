package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class MerchantComplaint {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String complaintNo;

    private String storeUid;

    private Integer merchantId;

    private String content;

    private Integer status;

    private String result;

    private Date handleTime;

    private Date createTime;
}