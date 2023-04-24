package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/13 19:38
 * @description:
 */
@Data
public class LevelVO implements Serializable {
    @ApiModelProperty("线级城市")
    private String level;
}
