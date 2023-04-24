package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/11/6
 */
@Data
public class IsShowQrcodeVO implements Serializable {
    @ApiModelProperty("是否显示二维码：1：显示，2：不显示")
    private String isShow;
}
