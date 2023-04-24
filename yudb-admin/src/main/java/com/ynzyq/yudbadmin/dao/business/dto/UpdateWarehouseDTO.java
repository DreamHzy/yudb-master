package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/2/14 14:22
 * @description:
 */
@Data
public class UpdateWarehouseDTO implements Serializable {
    @ApiModelProperty("映射id数组")
    @NotEmpty(message = "映射表id数组不能为空")
    private List<String> ids;

    @ApiModelProperty("类型：1：修改代仓，2：修改限单，3：修改匹配规则")
    private String type;

    @ApiModelProperty("根据type而定，type=1是代仓id，type=2是限单id，type=3是匹配规则id")
    private String valueId;
}
