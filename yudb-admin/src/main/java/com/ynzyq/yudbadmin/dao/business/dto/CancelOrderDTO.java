package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/3/1 14:42
 * @description:
 */
@Data
public class CancelOrderDTO implements Serializable {
    @ApiModelProperty("缴费订单id")
    @NotEmpty(message = "缴费订单id不能为空")
    private List<String> ids;
}
