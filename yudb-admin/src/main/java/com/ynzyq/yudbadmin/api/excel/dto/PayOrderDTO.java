package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/6 9:49
 * @description:
 */
@Data
public class PayOrderDTO implements Serializable {
    @ExcelColumn("授权号")
    private String uid;

    @ExcelColumn("鱼店宝订单号")
    private String orderNo;

    @ExcelColumn("减免后金额（新政策）")
    private String money;

    @ExcelColumn("备注")
    private String remark;
}
