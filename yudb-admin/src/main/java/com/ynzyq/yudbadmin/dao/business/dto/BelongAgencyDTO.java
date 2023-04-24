package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/4/29 17:19
 * @description:
 */
@Data
public class BelongAgencyDTO implements Serializable {
    @ApiModelProperty("所属代理商id")
    private String agentId;
}
