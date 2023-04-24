package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class BaseInfoVO implements Serializable {
    @ApiModelProperty("代理商名称")
    private String merchantName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("是否代理 1代理 2不是代理")
    private String isAgent;

    @ApiModelProperty("区域经理")
    private String regionalName;

    @ApiModelProperty("管理费标准")
    private String managementExpense;

    @ApiModelProperty("区域经理ID")
    private String regionalNameId;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("到期时间")
    private String expireTime;

    @ApiModelProperty("代理费")
    private String agencyFees;

    @ApiModelProperty("履约保证金")
    private String depositFee;

    @ApiModelProperty("签约时间")
    private String signTime;

    @ApiModelProperty("是否建仓：1：是，2：否")
    private String isOpenPosition;

    @ApiModelProperty("代理区域")
    private String agentArea;

    @ApiModelProperty("服务到期时间")
    private String serviceExpireTime;

    @ApiModelProperty("代理权是否生效：1：是，2：否")
    private String isEffect;
}
