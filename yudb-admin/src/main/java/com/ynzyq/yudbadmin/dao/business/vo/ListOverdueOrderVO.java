package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/14 16:58
 * @description:
 */
@Data
public class ListOverdueOrderVO implements Serializable {
    @ExcelIgnore
    @ApiModelProperty("订单id")
    private String id;

    @ExcelProperty(value = "订单类型")
    @ApiModelProperty("订单类型")
    private String type;

    @ExcelProperty(value = "区域")
    @ApiModelProperty("区域")
    private String region;

    @ExcelProperty(value = "区域经理")
    @ApiModelProperty("区域经理")
    private String managerName;

    @ExcelProperty(value = "缴费类型")
    @ApiModelProperty("缴费类型")
    private String paymentTypeName;

    @ExcelProperty(value = "逾期时间")
    @ApiModelProperty("逾期时间")
    private String expireTime;

    @ExcelProperty(value = "逾期金额")
    @ApiModelProperty("逾期金额")
    private String money;

}
