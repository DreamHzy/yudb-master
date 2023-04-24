package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/20 20:35
 * @description:
 */
@Data
public class StoreAreaDTO implements Serializable {
    @ExcelColumn(value = "授权号", col = 0)
    private String code;
    @ExcelColumn(value = "一级状态", col = 0)
    private String status;
    @ExcelColumn(value = "省1", col = 1)
    private String province;
    @ExcelColumn(value = "市1", col = 2)
    private String city;
    @ExcelColumn(value = "区1", col = 2)
    private String area;
}
