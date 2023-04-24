package com.ynzyq.yudbadmin.dao.business.model;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class AgentAreaPaymentOrderMaster {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String uid;

    private String orderNo;

    private Integer merchantId;

    private String merchantName;

    private String merchantMobile;

    private Integer regionalManagerId;

    private String regionalManagerName;

    private String regionalManagerMobile;

    private String province;

    private String city;

    private String area;

    private Integer paymentTypeId;

    private String paymentTypeName;

    private Integer type;

    private Integer status;

    private Integer examine;

    private BigDecimal fees;

    private Date expireTime;

    private Date payTime;

    private Integer send;

    private Integer examineNum;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer createUser;

    private Integer updateUser;

    private Date naturalYearStart;

    private Date naturalYearEnd;

    private Boolean deleted;

    private BigDecimal paymentStandardMoney;

    private BigDecimal money;

    private Integer adjustmentCount;

    private String adjustmentMsg;

    private String cycle;

    private Integer payType;

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getMerchantMobile() {
        return merchantMobile;
    }

    public void setMerchantMobile(String merchantMobile) {
        this.merchantMobile = merchantMobile == null ? null : merchantMobile.trim();
    }

    public Integer getRegionalManagerId() {
        return regionalManagerId;
    }

    public void setRegionalManagerId(Integer regionalManagerId) {
        this.regionalManagerId = regionalManagerId;
    }

    public String getRegionalManagerName() {
        return regionalManagerName;
    }

    public void setRegionalManagerName(String regionalManagerName) {
        this.regionalManagerName = regionalManagerName == null ? null : regionalManagerName.trim();
    }

    public String getRegionalManagerMobile() {
        return regionalManagerMobile;
    }

    public void setRegionalManagerMobile(String regionalManagerMobile) {
        this.regionalManagerMobile = regionalManagerMobile == null ? null : regionalManagerMobile.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public void setPaymentTypeName(String paymentTypeName) {
        this.paymentTypeName = paymentTypeName == null ? null : paymentTypeName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getExamine() {
        return examine;
    }

    public void setExamine(Integer examine) {
        this.examine = examine;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public void setFees(BigDecimal fees) {
        this.fees = fees;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getSend() {
        return send;
    }

    public void setSend(Integer send) {
        this.send = send;
    }

    public Integer getExamineNum() {
        return examineNum;
    }

    public void setExamineNum(Integer examineNum) {
        this.examineNum = examineNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Date getNaturalYearStart() {
        return naturalYearStart;
    }

    public void setNaturalYearStart(Date naturalYearStart) {
        this.naturalYearStart = naturalYearStart;
    }

    public Date getNaturalYearEnd() {
        return naturalYearEnd;
    }

    public void setNaturalYearEnd(Date naturalYearEnd) {
        this.naturalYearEnd = naturalYearEnd;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public BigDecimal getPaymentStandardMoney() {
        return paymentStandardMoney;
    }

    public void setPaymentStandardMoney(BigDecimal paymentStandardMoney) {
        this.paymentStandardMoney = paymentStandardMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getAdjustmentCount() {
        return adjustmentCount;
    }

    public void setAdjustmentCount(Integer adjustmentCount) {
        this.adjustmentCount = adjustmentCount;
    }

    public String getAdjustmentMsg() {
        return adjustmentMsg;
    }

    public void setAdjustmentMsg(String adjustmentMsg) {
        this.adjustmentMsg = adjustmentMsg == null ? null : adjustmentMsg.trim();
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle == null ? null : cycle.trim();
    }
}