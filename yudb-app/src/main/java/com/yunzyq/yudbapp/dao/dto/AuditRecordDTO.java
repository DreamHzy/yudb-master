package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/1/18 17:15
 * @description:
 */
@Data
public class AuditRecordDTO implements Serializable {
    @ApiModelProperty("id")
    @NotBlank(message = "id不能为空")
    private String id;
}
