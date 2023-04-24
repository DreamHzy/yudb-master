package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class DeptDTO implements Serializable {
    @ExcelColumn(value = "一级部门", col = 0)
    private String firstDept;
    @ExcelColumn(value = "二级部门", col = 0)
    private String secondDept;
    @ExcelColumn(value = "三级部门", col = 0)
    private String thirdDept;
}
