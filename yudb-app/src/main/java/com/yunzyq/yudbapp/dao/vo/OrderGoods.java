package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/3/13 15:31
 * @description:
 */
@Data
public class OrderGoods implements Serializable {
    @ApiModelProperty("类目名称")
    private String category;

    @ApiModelProperty("合计")
    private String money;

    @ApiModelProperty("商品信息")
    private List<OrderDetailGoods> orderGoodsInfos;
}
