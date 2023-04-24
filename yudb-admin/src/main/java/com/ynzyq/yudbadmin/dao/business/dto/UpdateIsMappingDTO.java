package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/3/8 10:15
 * @description:
 */
@Data
public class UpdateIsMappingDTO implements Serializable {
    @ApiModelProperty("映射id")
    @NotEmpty(message = "映射id不能为空")
    private List<String> ids;

    @ApiModelProperty("是否映射：1：是，2：否")
    @NotBlank(message = "是否映射不能为空")
    private String isMapping;
}
