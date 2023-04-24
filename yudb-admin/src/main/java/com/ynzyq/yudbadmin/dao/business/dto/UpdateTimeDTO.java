package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Sir
 */
@Data
public class UpdateTimeDTO implements Serializable {
    @ApiModelProperty("门店id")
    @NotBlank(message = "门店id不能为空")
    private String storeId;

    @ApiModelProperty("修改类型：1：修改开业时间，2：修改闭店时间，3：修改迁址时间，4：修改暂停营业时间，5：服务到期时间，6：合同到期时间，7：重开业时间,8预计开业时间")
    @NotBlank(message = "修改类型不能为空")
    private String type;

    @ApiModelProperty("时间")
    @NotBlank(message = "时间不能为空")
    private String time;
}
