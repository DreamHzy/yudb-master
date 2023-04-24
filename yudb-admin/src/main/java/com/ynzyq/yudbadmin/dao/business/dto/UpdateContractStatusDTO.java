package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/9 14:11
 * @description:
 */
@Data
public class UpdateContractStatusDTO implements Serializable {
    @ApiModelProperty("门店id")
    @NotBlank(message = "门店id不能为空")
    private String id;

    @ApiModelProperty("合同状态")
    @NotBlank(message = "合同状态不能为空")
    private String contractStatus;
}
