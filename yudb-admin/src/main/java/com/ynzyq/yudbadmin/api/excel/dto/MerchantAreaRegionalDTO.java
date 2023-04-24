package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class MerchantAreaRegionalDTO implements Serializable {
    @ExcelColumn(value = "省份", col = 0)
    private String province;
    @ExcelColumn(value = "地级市", col = 0)
    private String city;
    @ExcelColumn(value = "区县", col = 0)
    private String area;
    @ExcelColumn(value = "组合", col = 0)
    private String address;
    @ExcelColumn(value = "代理商", col = 0)
    private String name;
    @ExcelColumn(value = "代理区域代码", col = 0)
    private String code;
    @ExcelColumn(value = "代理权代码", col = 0)
    private String areaCode;
    @ExcelColumn(value = "区域经理", col = 0)
    private String managerName;
}
