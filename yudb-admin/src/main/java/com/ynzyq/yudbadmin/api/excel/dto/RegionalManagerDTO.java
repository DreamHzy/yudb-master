package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class RegionalManagerDTO implements Serializable {
    @ExcelColumn(value = "区域经理姓名", col = 0)
    private String name;
    @ExcelColumn(value = "手机号", col = 0)
    private String mobile;
    @ExcelColumn(value = "员工编码", col = 0)
    private String code;
}
