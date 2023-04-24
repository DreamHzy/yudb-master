package com.ynzyq.yudbadmin.dao.business.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;


@Data
public class OrderExcelDto {
    @ExcelColumn(value = "授权号", col = 0)
    private String uid;

    @ExcelColumn(value = "缴费类型(管理费/商户通/云学堂/支援费/罚款收费)", col = 2)
    private String payTypeName;

    @ExcelColumn(value = "金额", col = 2)
    private String money;

    @ExcelColumn(value = "缴费截止时间(年月日)", col = 3)
    private String expireTime;

    @ExcelColumn(value = "费用说明", col = 4)
    private String remark;

    @ExcelColumn(value = "云学堂账户", col = 5)
    private String account;


}
