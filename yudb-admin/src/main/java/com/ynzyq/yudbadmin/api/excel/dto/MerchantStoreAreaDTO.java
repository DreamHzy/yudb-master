package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class MerchantStoreAreaDTO implements Serializable {
    @ExcelColumn(value = "区域代码", col = 0)
    private String code;
    @ExcelColumn(value = "省份", col = 1)
    private String province;
    @ExcelColumn(value = "地级市", col = 2)
    private String city;
    @ExcelColumn(value = "区县", col = 3)
    private String area;
    @ExcelColumn(value = "区域", col = 4)
    private String address;
    @ExcelColumn(value = "代理商", col = 5)
    private String name;
    @ExcelColumn(value = "区域经理", col = 6)
    private String managerName;
}
