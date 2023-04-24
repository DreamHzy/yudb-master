package com.ynzyq.yudbadmin.dao.business.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class AgentAreaPaymentOrderPay {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Integer paymentOrderMasterId;

    private String orderNo;

    private String channelOrderNo;

    private Integer payChannelId;

    private String payChannelName;

    private String payChannelRoute;

    private String transactionId;

    private BigDecimal totalMoney;

    private Integer payType;

    private BigDecimal fees;

    private Integer status;

    private String channelApiUrl;

    private String channelNotifyUrl;

    private String msg;

    private String remark;

    private Date payTime;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPaymentOrderMasterId() {
        return paymentOrderMasterId;
    }

    public void setPaymentOrderMasterId(Integer paymentOrderMasterId) {
        this.paymentOrderMasterId = paymentOrderMasterId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getChannelOrderNo() {
        return channelOrderNo;
    }

    public void setChannelOrderNo(String channelOrderNo) {
        this.channelOrderNo = channelOrderNo == null ? null : channelOrderNo.trim();
    }

    public Integer getPayChannelId() {
        return payChannelId;
    }

    public void setPayChannelId(Integer payChannelId) {
        this.payChannelId = payChannelId;
    }

    public String getPayChannelName() {
        return payChannelName;
    }

    public void setPayChannelName(String payChannelName) {
        this.payChannelName = payChannelName == null ? null : payChannelName.trim();
    }

    public String getPayChannelRoute() {
        return payChannelRoute;
    }

    public void setPayChannelRoute(String payChannelRoute) {
        this.payChannelRoute = payChannelRoute == null ? null : payChannelRoute.trim();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChannelApiUrl() {
        return channelApiUrl;
    }

    public void setChannelApiUrl(String channelApiUrl) {
        this.channelApiUrl = channelApiUrl == null ? null : channelApiUrl.trim();
    }

    public String getChannelNotifyUrl() {
        return channelNotifyUrl;
    }

    public void setChannelNotifyUrl(String channelNotifyUrl) {
        this.channelNotifyUrl = channelNotifyUrl == null ? null : channelNotifyUrl.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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