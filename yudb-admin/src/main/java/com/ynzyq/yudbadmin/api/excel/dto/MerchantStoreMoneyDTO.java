package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/24 11:17
 * @description:
 */
@Data
public class MerchantStoreMoneyDTO implements Serializable {
    @ExcelColumn(value = "授权号", col = 0)
    private String uid;

    @ExcelColumn(value = "减免后金额", col = 0)
    private String money;

    @ExcelColumn(value = "账单备注", col = 0)
    private String remark;
}
