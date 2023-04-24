package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/6/16 17:56
 * @description:
 */
@Data
public class CalcTwoDTO implements Serializable {
    @ApiModelProperty("订单id数组")
    private List<String> orderIdList;
}
