package com.ynzyq.yudbadmin.dao.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 角色菜单关联
 * @author Caesar Liu
 * @date 2021/03/27 22:34
 */
@Data
@ApiModel("角色菜单关联")
public class SystemRoleMenu {

    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "角色ID", example = "1")
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID", example = "1")
    private Integer menuId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    @ApiModelProperty(value = "创建者ID", example = "1")
    private Integer createUser;

    @ApiModelProperty(value = "更新者ID", example = "1")
    private Integer updateUser;

    @ApiModelProperty(value = "是否已删除")
    private Boolean deleted;

}