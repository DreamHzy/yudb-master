package com.ynzyq.yudbadmin.dao.system.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuerySystemLogDTO {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("手机号")
    private String mobile;
}
