package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/23 19:17
 * @description:
 */
@Data
public class AccountDTO implements Serializable {
    @ExcelColumn(value = "订单号", col = 0)
    private String orderNo;

}
