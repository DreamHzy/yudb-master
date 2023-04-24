package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class ApproveProcessStep {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer approveId;

    private Integer step;

    private String name;

    private Integer type;

    private Integer userId;

    private String userName;

    private Integer status;

    private Date createTime;
}