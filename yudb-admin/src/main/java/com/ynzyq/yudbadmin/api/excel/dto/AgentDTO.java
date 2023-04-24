package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class AgentDTO implements Serializable {
    @ExcelColumn(value = "所属代理", col = 0)
    private String name;
    @ExcelColumn(value = "电话", col = 0)
    private String mobile;
//    @ExcelColumn(value = "省", col = 0)
//    private String province;
//    @ExcelColumn(value = "市", col = 0)
//    private String city;
//    @ExcelColumn(value = "区域", col = 0)
//    private String area;
}
