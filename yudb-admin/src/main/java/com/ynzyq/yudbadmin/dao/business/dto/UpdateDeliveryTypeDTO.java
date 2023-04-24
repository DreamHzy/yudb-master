package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/4/8 10:01
 * @description:
 */
@Data
public class UpdateDeliveryTypeDTO implements Serializable {
    @ApiModelProperty("门店id")
    @NotBlank(message = "门店id不能为空")
    private String storeId;
}
