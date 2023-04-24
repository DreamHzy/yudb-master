package com.ynzyq.yudbadmin.third.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/15 20:15
 * @description:
 */
@Data
public class QuerySettleStatusDTO implements Serializable {

    @ApiModelProperty("订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty("时间戳毫秒数")
    @NotBlank(message = "时间戳毫秒数不能为空")
    private String timestamp;

    @ApiModelProperty("签名")
    @NotBlank(message = "签名不能为空")
    private String sign;
}
