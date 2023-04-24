package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/12/27 15:42
 * @description:
 */
@Data
public class ExamineDTO implements Serializable {
    @ApiModelProperty("审批单id")
    @NotBlank(message = "审批单id不能为空")
    private String id;

    @ApiModelProperty("内容")
    private String msg;

    @ApiModelProperty("照片地址")
    private List<String> images;
}
