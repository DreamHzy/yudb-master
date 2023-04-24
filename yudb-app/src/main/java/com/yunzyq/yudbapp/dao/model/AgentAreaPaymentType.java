package com.yunzyq.yudbapp.dao.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class AgentAreaPaymentType {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String name;

    private Integer status;

    private Integer manual;

    private BigDecimal money;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getManual() {
        return manual;
    }

    public void setManual(Integer manual) {
        this.manual = manual;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}