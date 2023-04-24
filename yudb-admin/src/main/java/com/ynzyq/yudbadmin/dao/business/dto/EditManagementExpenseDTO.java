package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class EditManagementExpenseDTO implements Serializable {
    @ApiModelProperty("代理管理列表id")
    @NotNull(message = "id不能为空")
    private Integer id;

    @ApiModelProperty("管理费标准")
    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0", message = "金额必须大于0")
    private String money;
}
