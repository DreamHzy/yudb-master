package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/3/13 20:04
 * @description:
 */
@Data
public class FutureBacklogDetailDTO implements Serializable {
    @ApiModelProperty("收款月份")
    @NotBlank(message = "收款月份不能为空")
    private String collectionMonth;
}
