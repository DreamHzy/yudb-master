package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/8 14:58
 * @description:
 */
@Data
public class StatementIdDTO implements Serializable {
    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;
}
