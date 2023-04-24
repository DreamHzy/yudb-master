package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/6/1 17:49
 * @description:
 */
@Data
public class ChooseAppMenuDTO implements Serializable {
    @ApiModelProperty("加盟商id")
    @NotBlank(message = "加盟商id不能为空")
    private String merchantId;

    @ApiModelProperty("菜单id数组")
    @NotEmpty(message = "菜单id数组不能为空")
    private List<String> menuIds;
}
