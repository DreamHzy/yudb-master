package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StorePage2Statistics {

    @ApiModelProperty("一级状态")
    private String status;

    @ApiModelProperty("一级状态文本说明")
    private String statusDesc;

    @ApiModelProperty("数量")
    private String count;

}
