package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class AgentAreaPaymentOrderExamineDeailFile {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer agentAreaPaymentOrderExamineDeailId;

    private Integer type;

    private String name;

    private String url;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;
}