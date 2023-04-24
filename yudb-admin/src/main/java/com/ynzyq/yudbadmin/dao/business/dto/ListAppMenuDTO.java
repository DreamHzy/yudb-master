package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/1 17:49
 * @description:
 */
@Data
public class ListAppMenuDTO implements Serializable {
    @ApiModelProperty("加盟商id")
    @NotBlank(message = "加盟商id不能为空")
    private String merchantId;

    private Integer menuId;
}
