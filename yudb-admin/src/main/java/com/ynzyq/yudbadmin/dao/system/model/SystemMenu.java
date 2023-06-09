package com.ynzyq.yudbadmin.dao.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 系统菜单
 *
 * @author Caesar Liu
 * @date 2021/03/30 22:29
 */
@Data
@ApiModel("系统菜单")
public class SystemMenu {

    @ApiModelProperty(value = "主键", example = "1")
    private Integer id;

    @ApiModelProperty(value = "上一级菜单", example = "1")
    private Integer parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单访问路径")
    private String path;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disabled;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建者ID", example = "1")
    private Integer createUser;

    @ApiModelProperty(value = "更新者ID", example = "1")
    private Integer updateUser;

    @ApiModelProperty(value = "是否已删除")
    private Boolean deleted;

    @ApiModelProperty(value = "是否已删除")
    private Integer isSystem;

}