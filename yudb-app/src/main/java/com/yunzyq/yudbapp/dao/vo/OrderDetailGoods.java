package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/25 17:34
 * @description:
 */
@Data
public class OrderDetailGoods implements Serializable {
    @ApiModelProperty("商品图片")
    private String image;

    @ApiModelProperty("温区标签：1：冷冻，2：常温")
    private String lable;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("商品单位")
    private String unit;

    @ApiModelProperty("商品数量")
    private String amount;

    @ApiModelProperty("商品单价")
    private String price;

    @ApiModelProperty("商品合计")
    private String goodsMoney;

    @ApiModelProperty("返利百分比")
    private String rebate;

    @ApiModelProperty("返利金额")
    private String rebateMoney;
}
