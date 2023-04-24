package com.ynzyq.yudbadmin.dao.business.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class Department {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String name;

    private Integer status;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;


    private Integer parentId;


}