package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/8 14:42
 * @description:
 */
@Data
public class ListStatementVO implements Serializable {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("日期")
    private String statisticsDate;
}
