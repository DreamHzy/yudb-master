package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/25 15:54
 * @description:
 */
@Data
public class OrderDetailDTO implements Serializable {
    @ApiModelProperty("订单id")
    @NotBlank(message = "订单id不能为空")
    private String id;
}
