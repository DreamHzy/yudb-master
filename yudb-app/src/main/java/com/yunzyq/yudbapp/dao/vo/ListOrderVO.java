package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/2/25 14:37
 * @description:
 */
@Data
public class ListOrderVO implements Serializable {
    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("订单状态")
    private String status;

    @ApiModelProperty("订单金额")
    private String orderMoney;

    @ApiModelProperty("商品件数")
    private String amount;

    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("审核状态")
    private Integer auditStatus;

    @ApiModelProperty("是否配置")
    private Integer isConfig;

    @ApiModelProperty("授权号")
    private Integer uid;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("商品图片")
    private List<GoodsImage> images;
}
