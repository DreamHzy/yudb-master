package com.ynzyq.yudbadmin.api.excel.dto;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/23 19:17
 * @description:
 */
@Data
public class LevelCityDTO implements Serializable {
    @ExcelColumn(value = "区域代码", col = 1)
    private String code;

    @ExcelColumn(value = "代理权代码", col = 2)
    private String agentCode;

    @ExcelColumn(value = "线级", col = 3)
    private String level;

}
