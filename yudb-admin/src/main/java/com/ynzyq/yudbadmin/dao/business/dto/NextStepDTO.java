package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/4/6 15:51
 * @description:
 */
@Data
public class NextStepDTO implements Serializable {
    @ApiModelProperty("联系方式")
    @NotBlank(message = "联系方式不能为空")
    private String mobile;

    @ApiModelProperty("签约人")
    @NotBlank(message = "签约人不能为空")
    private String signatory;

    @ApiModelProperty("是否为代理：1：是，2：否")
    @NotBlank(message = "是否为代理不能为空")
    private String isAgent;
}
