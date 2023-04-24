package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/1/18 17:19
 * @description:
 */
@Data
public class ExamineDetailDTO implements Serializable {
    @ApiModelProperty("审核人")
    private String createName;
    @ApiModelProperty("审核时间")
    private String examineTime;
    @ApiModelProperty("状态：1、待审核 2、通过 3、拒绝")
    private String status;
    @ApiModelProperty("审核意见")
    private String remark;
}
