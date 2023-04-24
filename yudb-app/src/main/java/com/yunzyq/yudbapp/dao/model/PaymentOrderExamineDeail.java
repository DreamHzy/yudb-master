package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class PaymentOrderExamineDeail {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer paymentOrderExamineId;

    private Integer examine;

    private Integer step;

    private Integer type;

    private String approveName;

    private Integer status;

    private String remark;

    private Integer createUser;

    private String createName;

    private Date examineTime;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;

}