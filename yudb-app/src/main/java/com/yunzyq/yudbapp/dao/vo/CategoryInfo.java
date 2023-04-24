package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/3/13 16:00
 * @description:
 */
@Data
public class CategoryInfo implements Serializable {
    @ApiModelProperty("类目id")
    private String id;

    @ApiModelProperty("类目名称")
    private String name;
}
