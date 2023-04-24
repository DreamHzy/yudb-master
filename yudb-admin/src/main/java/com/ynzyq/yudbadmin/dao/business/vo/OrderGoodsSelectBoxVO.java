package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/14 15:41
 * @description:
 */
@Data
public class OrderGoodsSelectBoxVO implements Serializable {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;
}
