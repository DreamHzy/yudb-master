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
public class SettleOneDTO implements Serializable {
    @ApiModelProperty("结算id数组")
    private List<String> statisticsIdList;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("随机因子")
    private String plugRandKey;
}
