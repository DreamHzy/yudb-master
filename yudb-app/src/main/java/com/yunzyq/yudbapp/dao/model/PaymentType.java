package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentType {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String name;

    private Integer status;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    private Integer manual;

    private BigDecimal money;

}