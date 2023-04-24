package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class CloudSchoolDTO implements Serializable {
    @ExcelColumn(value = "云学堂账号", col = 0)
    private String account;
    @ExcelColumn(value = "授权号", col = 0)
    private String code;
    @ExcelColumn(value = "开始时间", col = 0)
    private String startTime;
    @ExcelColumn(value = "到期时间", col = 0)
    private String endTime;
}
