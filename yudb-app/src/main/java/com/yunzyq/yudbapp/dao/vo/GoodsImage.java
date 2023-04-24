package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/25 14:40
 * @description:
 */
@Data
public class GoodsImage implements Serializable {
    @ApiModelProperty("商品图片")
    private String url;
}
