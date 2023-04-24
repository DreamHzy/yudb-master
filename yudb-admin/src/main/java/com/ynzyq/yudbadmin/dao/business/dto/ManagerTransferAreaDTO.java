package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/11/18 15:42
 * @description:
 */
@Data
public class ManagerTransferAreaDTO implements Serializable {
    @ApiModelProperty("映射id")
    @NotEmpty(message = "映射id不能为空")
    private List<String> id;

    @ApiModelProperty("区域经理id")
    @NotBlank(message = "区域经理id不能为空")
    private String managerId;
}
