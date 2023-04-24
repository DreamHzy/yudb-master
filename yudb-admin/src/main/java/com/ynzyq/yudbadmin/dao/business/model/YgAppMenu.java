package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class YgAppMenu {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String name;

    private String path;

    private String icon;

    private Integer status;

    private Date createTime;
}