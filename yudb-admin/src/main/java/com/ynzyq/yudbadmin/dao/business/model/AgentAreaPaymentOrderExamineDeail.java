package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class AgentAreaPaymentOrderExamineDeail {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer paymentOrderExamineId;

    private Integer stepId;

    private Integer step;

    private String approveName;

    private Integer type;

    private Integer examine;

    private Integer status;

    private String remark;

    private Integer createUser;

    private String createName;

    private Date examineTime;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;


}