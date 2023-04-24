package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class ApproveProcess {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer type;

    private Integer examineType;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}