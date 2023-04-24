package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/24 17:54
 * @description:
 */
@Data
public class EditEffectDTO extends AgentIdDTO implements Serializable {
    @ApiModelProperty("代理权是否生效：1：是，2：否")
    @NotBlank(message = "代理权是否生效不能为空")
    private String isEffect;
}
