package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/29 20:02
 * @description:
 */
@Data
public class UpdateExtraFieldDTO implements Serializable {
    @ApiModelProperty("门店id")
    @NotBlank(message = "门店id不能为空")
    private String storeId;

    @ApiModelProperty("修改类型：1：餐位数、2：哗啦啦授权码、3：美团ID、4：饿了么ID、5：大众点评ID、6：备注")
    @NotBlank(message = "修改类型不能为空")
    private String type;

    @ApiModelProperty("修改的值")
    @NotBlank(message = "修改的值不能为空")
    private String value;
}
