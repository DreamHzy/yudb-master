package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/5/20 10:50
 * @description:
 */
@Data
public class UpdateApplyAdjustDTO implements Serializable {
    @ApiModelProperty("门店id")
    @NotEmpty(message = "门店id不能为空")
    private List<String> storeIds;

    @ApiModelProperty("是否开启门店调货：1：开，2：关")
    @NotEmpty(message = "是否开启门店调货不能为空")
    private String isApply;
}
