package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class MonitorUserPageVo {

    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("1启用2停用'")
    private String status;
    @ApiModelProperty("创建者")
    private String createUserName;
    @ApiModelProperty("创建时间")
    private String createTime;
}
