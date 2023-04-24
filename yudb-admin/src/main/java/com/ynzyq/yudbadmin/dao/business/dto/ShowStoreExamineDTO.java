package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/27 1:05
 * @description:
 */
@Data
public class ShowStoreExamineDTO implements Serializable {
    @ApiModelProperty("申请人")
    private String applyName;

    @ApiModelProperty("授权号")
    private String merchantStoreUid;

    @ApiModelProperty("费用类型id")
    private String paymentTypeId;

    @ApiModelProperty("审核类型 1、金额调整 2、延期支付 3、新订单审核 4、取消费用")
    private String examineType;

    @ApiModelProperty("缴费开始时间")
    private String startTime;

    @ApiModelProperty("缴费截止时间")
    private String endTime;

    @ApiModelProperty("状态：1、审核中 2、通过 3、拒绝")
    private String status;

    @ApiModelProperty("审核人")
    private String approveName;

    @ApiModelProperty("区域")
    private String region;

    private Integer userId;

    public void setApplyName(String applyName) {
        if (StringUtils.isNotBlank(applyName)) {
            this.applyName = applyName;
        }
    }

    public void setMerchantStoreUid(String merchantStoreUid) {
        if (StringUtils.isNotBlank(merchantStoreUid)) {
            this.merchantStoreUid = merchantStoreUid;
        }
    }

    public void setPaymentTypeId(String paymentTypeId) {
        if (StringUtils.isNotBlank(paymentTypeId)) {
            this.paymentTypeId = paymentTypeId;
        }
    }

    public void setExamineType(String examineType) {
        if (StringUtils.isNotBlank(examineType)) {
            this.examineType = examineType;
        }
    }

    public void setStartTime(String startTime) {
        if (StringUtils.isNotBlank(startTime)) {
            this.startTime = startTime;
        }
    }

    public void setEndTime(String endTime) {
        if (StringUtils.isNotBlank(endTime)) {
            this.endTime = endTime;
        }
    }

    public void setStatus(String status) {
        if (StringUtils.isNotBlank(status)) {
            this.status = status;
        }
    }

    public void setRegion(String region) {
        if (StringUtils.isNotBlank(region)) {
            this.region = region;
        }
    }
}
