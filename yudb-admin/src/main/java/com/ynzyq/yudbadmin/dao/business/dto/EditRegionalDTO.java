package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class EditRegionalDTO implements Serializable {
    @ApiModelProperty("代理权id")
    @NotNull(message = "代理权id不能为空")
    private List<Integer> id;

    @ApiModelProperty("区域经理id")
    @NotNull(message = "区域经理id不能为空")
    private Integer regionalId;
}
