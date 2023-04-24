package com.ynzyq.yudbadmin.dao.business.vo;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WJ
 */
@Data
public class ListAgentExportVO implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ExcelColumn(value = "代理商名称", col = 1)
    @ApiModelProperty("代理商名称")
    private String merchantName;

    @ExcelColumn(value = "代理权代码", col = 2)
    @ApiModelProperty("代理权代码")
    private String uid;

    @ExcelColumn(value = "是否代理", col = 3)
    @ApiModelProperty("是否代理 1代理 2不是代理")
    private String isAgent;

    @ExcelColumn(value = "区域经理", col = 4)
    @ApiModelProperty("区域经理")
    private String regionalName;

    @ExcelColumn(value = "管理费标准", col = 5)
    @ApiModelProperty("管理费标准")
    private String managementExpense;

    @ExcelColumn(value = "开始时间", col = 6)
    @ApiModelProperty("开始时间")
    private String startTime;

    @ExcelColumn(value = "到期时间", col = 7)
    @ApiModelProperty("到期时间")
    private String expireTime;

    @ExcelColumn(value = "代理费", col = 8)
    @ApiModelProperty("代理费")
    private String agencyFees;

    @ExcelColumn(value = "履约保证金", col = 9)
    @ApiModelProperty("履约保证金")
    private String depositFee;

    @ExcelColumn(value = "签约时间", col = 10)
    @ApiModelProperty("签约时间")
    private String signTime;

    @ExcelColumn(value = "是否建仓", col = 11)
    @ApiModelProperty("是否建仓：1：是，2：否")
    private String isOpenPosition;

    @ExcelColumn(value = "代理区域", col = 12)
    @ApiModelProperty("代理区域")
    private String agentArea;

}
