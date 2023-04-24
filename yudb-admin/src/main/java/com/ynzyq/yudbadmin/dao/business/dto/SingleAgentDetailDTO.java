package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class SingleAgentDetailDTO implements Serializable {

    @ApiModelProperty("代理管理列表id")
    @NotNull(message = "id不能为空")
    private Integer id;
}
