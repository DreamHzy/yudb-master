package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/14 15:35
 * @description:
 */
@Data
public class UpdateStatusDTO implements Serializable {
    @ApiModelProperty("id")
    private String id;
}
