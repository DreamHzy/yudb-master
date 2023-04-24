package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/1 16:55
 * @description:
 */
@Data
public class ListAppMenuVO implements Serializable {
    @ApiModelProperty("菜单id")
    private String menuId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("是否选中：1：是，2：否")
    private String isChoose;
}
