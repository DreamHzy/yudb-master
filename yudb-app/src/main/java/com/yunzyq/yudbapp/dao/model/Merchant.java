package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class Merchant {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String openId;

    private String name;

    private String mobile;

    private String password;

    private Integer status;

    private String province;

    private String city;

    private String address;

    private Date createTime;

    private Date updateTime;

    private Integer createUser;

    private Integer updateUser;


    private String idNumber;

    private Integer isAgent;

    private Date loginTime;
}