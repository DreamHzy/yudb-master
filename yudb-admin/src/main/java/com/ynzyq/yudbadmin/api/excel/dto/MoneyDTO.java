package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/9 15:38
 * @description:
 */
@Data
public class MoneyDTO implements Serializable {
    @ExcelColumn(value = "授权号", col = 1)
    private String uid;

    @ExcelColumn(value = "加盟费", col = 2)
    private String franchiseFee;

    @ExcelColumn(value = "管理费", col = 3)
    private String managementExpense;

    @ExcelColumn(value = "保证金", col = 4)
    private String bondMoney;
}
