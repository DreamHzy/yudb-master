package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/15 17:42
 * @description:
 */
@Data
public class PayShareInfoDTO implements Serializable {
    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("类型：1：门店，2：代理")
    private String type;
}
