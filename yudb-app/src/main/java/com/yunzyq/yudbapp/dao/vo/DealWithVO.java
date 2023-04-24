package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/1/25 15:32
 * @description:
 */
@Data
public class DealWithVO implements Serializable {
    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("时间")
    private String handleTime;

    @ApiModelProperty("处理内容")
    private String result;
}
