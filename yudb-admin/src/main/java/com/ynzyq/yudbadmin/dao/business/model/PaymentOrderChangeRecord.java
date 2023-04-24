package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class PaymentOrderChangeRecord {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer mappingAreaId;

    private Integer orderId;

    private Integer orderStatus;

    private Integer oldManagerId;

    private String oldManagerName;

    private Integer newManagerId;

    private String newManagerName;

    private Integer type;

    private Integer userId;

    private String userName;

    private Date createTime;
}