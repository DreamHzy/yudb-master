package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class MerchantAreaDTO implements Serializable {
    @ExcelColumn(value = "代理权代码", col = 0)
    private String code;
    @ExcelColumn(value = "代理区域", col = 0)
    private String area;
    @ExcelColumn(value = "所属代理商", col = 0)
    private String agentName;
    @ExcelColumn(value = "代理费", col = 0)
    private String agencyFees;
    @ExcelColumn(value = "管理费", col = 0)
    private String managementFee;
    @ExcelColumn(value = "履约保证金", col = 0)
    private String marginFee;
    @ExcelColumn(value = "签约时间", col = 0)
    private String signingTime;
    @ExcelColumn(value = "履约协议到期时间", col = 0)
    private String expireDate;
    @ExcelColumn(value = "是否建仓", col = 0)
    private String isPosition;
    @ExcelColumn(value = "区域经理", col = 0)
    private String managerName;
}
