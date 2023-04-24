package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class AgentAreaPaymentOrderExamineFile {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer paymentOrderExamineId;

    private Integer type;

    private String name;

    private String url;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;


}