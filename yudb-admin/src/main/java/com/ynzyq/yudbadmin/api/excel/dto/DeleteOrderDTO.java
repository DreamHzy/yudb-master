package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/30 14:29
 * @description:
 */
@Data
public class DeleteOrderDTO implements Serializable {
    @ExcelColumn(value = "授权号", col = 1)
    private String uid;
}
