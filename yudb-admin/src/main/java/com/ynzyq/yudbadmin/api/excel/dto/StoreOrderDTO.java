package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/9 19:20
 * @description:
 */
@Data
public class StoreOrderDTO implements Serializable {
    @ExcelColumn(value = "授权号",col = 1)
    private String uid;
    @ExcelColumn(value = "区域经理",col = 1)
    private String name;
    @ExcelColumn(value = "省",col = 1)
    private String province;
    @ExcelColumn(value = "市",col = 1)
    private String city;
    @ExcelColumn(value = "区",col = 1)
    private String area;
    @ExcelColumn(value = "地址",col = 1)
    private String address;
    @ExcelColumn(value = "管理费",col = 1)
    private String managementExpense;
    private String startTime;
    private String expireTime;
}
