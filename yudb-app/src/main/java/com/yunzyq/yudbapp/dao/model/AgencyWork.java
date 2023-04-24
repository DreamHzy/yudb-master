package com.yunzyq.yudbapp.dao.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

public class AgencyWork {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer type;

    private Integer merchantId;

    private Integer regionalManagerId;

    private Integer workId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getRegionalManagerId() {
        return regionalManagerId;
    }

    public void setRegionalManagerId(Integer regionalManagerId) {
        this.regionalManagerId = regionalManagerId;
    }

    public Integer getWorkId() {
        return workId;
    }

    public void setWorkId(Integer workId) {
        this.workId = workId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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