package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class MerchantDTO implements Serializable {
    @ExcelColumn(value = "加盟商姓名", col = 0)
    private String name;
    @ExcelColumn(value = "联系电话", col = 0)
    private String mobile;
}
