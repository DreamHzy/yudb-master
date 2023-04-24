package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/1/25 14:41
 * @description:
 */
@Data
public class DealWithDTO implements Serializable {
    @ApiModelProperty("id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty("处理内容")
    @NotBlank(message = "处理内容不能为空")
    private String content;
}
