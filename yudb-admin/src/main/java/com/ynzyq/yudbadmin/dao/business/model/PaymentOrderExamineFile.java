package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentOrderExamineFile {
    private Integer id;

    private Integer paymentOrderExamineId;

    private Integer type;

    private String url;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;


}