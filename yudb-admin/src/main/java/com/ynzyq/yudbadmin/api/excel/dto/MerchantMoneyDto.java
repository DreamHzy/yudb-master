package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

@Data
public class MerchantMoneyDto {
    @ExcelColumn(value = "代理权代码", col = 0)
    private String code;


    @ExcelColumn(value = "管理费用", col = 0)
    private String money;


}
