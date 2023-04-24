package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/25
 */
@Data
public class ListAgentPaymentTypeVO implements Serializable {

    /**
     * 缴费类型id
     */
    @ApiModelProperty("缴费类型id")
    private String id;

    /**
     * 缴费名称
     */
    @ApiModelProperty("缴费名称")
    private String name;
}
