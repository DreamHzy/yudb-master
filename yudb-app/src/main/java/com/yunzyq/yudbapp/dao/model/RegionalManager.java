package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class RegionalManager {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String name;

    private String mobile;

    private Integer status;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    private String password;


    private String openId;


}