package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/3/24 9:47
 * @description:
 */
@Data
public class UpdateExpireTimeDTO implements Serializable {
    @ApiModelProperty("缴费订单id")
    @NotBlank(message = "缴费订单id不能为空")
    private String id;

    @ApiModelProperty("缴费截止时间")
    @NotBlank(message = "缴费截止时间不能为空")
    private String expireTime;
}
