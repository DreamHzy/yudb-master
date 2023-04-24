package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/14 15:38
 * @description:
 */
@Data
public class OrderGoodsSelectBoxDTO implements Serializable {
    @ApiModelProperty("类型：1：代仓下拉框，2：限单下拉框，3：匹配规则下拉框")
    @NotBlank(message = "类型不能为空")
    private String type;
}
