package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MerchantPageVo {

    @ExcelIgnore
    private String id;

    @ExcelProperty(value = "客户名称")
    @ApiModelProperty("客户名称")
    private String name;

    @ExcelProperty(value = "手机号")
    @ApiModelProperty("手机号")
    private String phone;

    @ExcelProperty(value = "门店数")
    @ApiModelProperty("门店数")
    private String storeCount;

    @ExcelProperty(value = "是否代理")
    @ApiModelProperty("是否代理 1代理 2不是代理")
    private String isAgent;

    @ExcelProperty(value = "代理权数")
    @ApiModelProperty("代理权数")
    private String agentCount;

    @ExcelProperty(value = "身份证/统一信用代码")
    @ApiModelProperty("身份证/统一信用代码")
    private String idNumber;

    @ExcelProperty(value = "状态")
    @ApiModelProperty("状态：1：启用，2：禁用")
    private String status;
}
