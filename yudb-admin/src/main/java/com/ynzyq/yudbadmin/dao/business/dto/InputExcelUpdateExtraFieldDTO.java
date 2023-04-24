package com.ynzyq.yudbadmin.dao.business.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/30 10:57
 * @description:
 */
@Data
public class InputExcelUpdateExtraFieldDTO implements Serializable {
    @ExcelColumn(value = "授权号", col = 0)
    private String uid;

    @ExcelColumn(value = "身份证号", col = 1)
    @ApiModelProperty("身份证号")
    private String idNumber;

    @ExcelColumn(value = "餐位数", col = 1)
    @ApiModelProperty("餐位数")
    private String seatCount;

    @ExcelColumn(value = "哗啦啦授权码", col = 2)
    @ApiModelProperty("哗啦啦授权码")
    private String hllCode;

    @ExcelColumn(value = "美团ID", col = 27)
    @ApiModelProperty("美团ID")
    private String mtId;

    @ExcelColumn(value = "饿了么ID", col = 28)
    @ApiModelProperty("饿了么ID")
    private String elmId;

    @ExcelColumn(value = "大众点评ID", col = 29)
    @ApiModelProperty("大众点评ID")
    private String dzdpId;

    @ExcelColumn(value = "收货人", col = 30)
    @ApiModelProperty("收货人")
    private String receiver;

    @ExcelColumn(value = "收货电话", col = 31)
    @ApiModelProperty("收货电话")
    private String receiverMobile;

    @ExcelColumn(value = "收货地址", col = 32)
    @ApiModelProperty("收货地址")
    private String deliveryAddress;

    @ApiModelProperty("运费省份")
    @NotBlank(message = "运费省份不能为空")
    private String deliveryProvince;

    @ApiModelProperty("运费市区")
    @NotBlank(message = "运费市区不能为空")
    private String deliveryCity;

    @ApiModelProperty("运费区县")
    @NotBlank(message = "运费区县不能为空")
    private String deliveryArea;
}
