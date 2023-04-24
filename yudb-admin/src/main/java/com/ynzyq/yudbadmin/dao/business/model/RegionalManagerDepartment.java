package com.ynzyq.yudbadmin.dao.business.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

public class RegionalManagerDepartment {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer regionalManagerId;

    private Integer departmentId;

    private Integer status;

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

    public Integer getRegionalManagerId() {
        return regionalManagerId;
    }

    public void setRegionalManagerId(Integer regionalManagerId) {
        this.regionalManagerId = regionalManagerId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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