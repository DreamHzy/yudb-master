package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/1/11 14:34
 * @description:
 */
@Data
public class UpdateExpireTimeDTO implements Serializable {
    @ExcelColumn(value = "订单id", col = 1)
    private String id;

    @ExcelColumn(value = "缴费截止时间", col = 2)
    private String expireTime;
}
