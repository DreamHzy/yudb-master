package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/4/6 15:41
 * @description:
 */
@Data
public class NextStepVO implements Serializable {
    @ApiModelProperty("客户id")
    private String id;

    @ApiModelProperty("客户名称")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("门店数")
    private String storeCount;

    @ApiModelProperty("是否代理 1代理 2不是代理")
    private String isAgent;

    @ApiModelProperty("代理权数")
    private String agentCount;

    @ApiModelProperty("身份证/统一信用代码")
    private String idNumber;

    @ApiModelProperty("状态：1：启用，2：禁用")
    private String status;
}
