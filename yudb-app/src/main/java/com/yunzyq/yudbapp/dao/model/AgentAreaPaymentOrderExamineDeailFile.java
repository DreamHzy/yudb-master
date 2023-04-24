package com.yunzyq.yudbapp.dao.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

public class AgentAreaPaymentOrderExamineDeailFile {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer agentAreaPaymentOrderExamineDeailId;

    private Integer type;

    private String name;

    private String url;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Byte deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgentAreaPaymentOrderExamineDeailId() {
        return agentAreaPaymentOrderExamineDeailId;
    }

    public void setAgentAreaPaymentOrderExamineDeailId(Integer agentAreaPaymentOrderExamineDeailId) {
        this.agentAreaPaymentOrderExamineDeailId = agentAreaPaymentOrderExamineDeailId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }
}