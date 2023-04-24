package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/3/27 20:26
 * @description:
 */
@Data
public class UpdatePublishStatusDTO implements Serializable {
    @ApiModelProperty("缴费订单id")
    @NotBlank(message = "缴费订单id不能为空")
    private String id;
}
