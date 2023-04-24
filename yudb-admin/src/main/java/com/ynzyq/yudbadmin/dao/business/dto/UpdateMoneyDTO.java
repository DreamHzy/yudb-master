package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/9 14:44
 * @description:
 */
@Data
public class UpdateMoneyDTO implements Serializable {
    @ApiModelProperty("门店id")
    @NotBlank(message = "门店id不能为空")
    private String id;

    @ApiModelProperty("修改类型：1：加盟费，2：管理费，3：履约保证金")
    @NotBlank(message = "修改类型不能为空")
    private String type;

    @ApiModelProperty("金额")
    @NotBlank(message = "金额不能为空")
    private String Money;
}
