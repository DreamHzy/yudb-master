package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sir
 */
@Data
public class EditAgentDTO extends AddAgentDto implements Serializable {
    @ApiModelProperty("代理权id")
    @NotBlank(message = "请求参数不能为空")
    private String id;
}
