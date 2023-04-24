package com.ynzyq.yudbadmin.dao.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询系统角色参数
 * @author Caesar Liu
 * @date 2021-03-30 11:28
 */
@Data
public class QuerySystemRoleDTO {

    @ApiModelProperty("角色CODE")
    private String code;

    @ApiModelProperty("角色名称")
    private String name;
}
