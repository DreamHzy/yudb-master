package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/20 10:54
 * @description:
 */
@Data
public class SystemFactorSelectBoxVO implements Serializable {
    @ApiModelProperty("代理体系")
    private String systemFactor;
}
