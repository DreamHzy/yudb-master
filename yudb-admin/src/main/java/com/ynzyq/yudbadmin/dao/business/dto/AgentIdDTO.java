package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/24 15:30
 * @description:
 */
@Data
public class AgentIdDTO implements Serializable {
    @ApiModelProperty("代理权id")
    @NotBlank(message = "代理权id不能为空")
    private String id;
}
