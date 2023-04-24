package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PopupDto {

    private String id;

    @ApiModelProperty("1一审 2二审")
    private String type;
}
