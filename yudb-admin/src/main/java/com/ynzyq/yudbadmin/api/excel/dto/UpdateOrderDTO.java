package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/18 20:02
 * @description:
 */
@Data
public class UpdateOrderDTO implements Serializable {
    @ExcelColumn(value = "授权号", col = 1)
    private String uid;

    @ExcelColumn(value = "缴费截止日期", col = 1)
    private String expireTime;

    @ExcelColumn(value = "剩余应缴金额", col = 1)
    private String money;

    @ExcelColumn(value = "截止日期", col = 1)
    private String expireTime2;
}
