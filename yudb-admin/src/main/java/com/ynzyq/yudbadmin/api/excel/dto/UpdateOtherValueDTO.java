package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/18 17:01
 * @description:
 */
@Data
public class UpdateOtherValueDTO implements Serializable {
    @ExcelColumn(value = "授权号", col = 1)
    private String uid;

    @ExcelColumn(value = "签约人", col = 2)
    private String signatory;

    @ExcelColumn(value = "重开业时间", col = 2)
    private String openAgainTime;

    @ExcelColumn(value = "哗啦啦授权号", col = 2)
    private String hllCode;

    @ExcelColumn(value = "美团ID", col = 2)
    private String mtId;

    @ExcelColumn(value = "饿了么ID", col = 2)
    private String elmId;

    @ExcelColumn(value = "大众点评ID", col = 2)
    private String dzdpId;

    @ExcelColumn(value = "店铺类型", col = 2)
    private String type;

    @ExcelColumn(value = "身份证号/营业执照号", col = 2)
    private String idNumber;
}
