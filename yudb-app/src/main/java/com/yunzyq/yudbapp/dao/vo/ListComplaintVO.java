package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/1/25 11:59
 * @description:
 */
@Data
public class ListComplaintVO implements Serializable {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("投诉时间")
    private String createTime;

    @ApiModelProperty("编号")
    private String complaintNo;

    @ApiModelProperty("状态：1：未处理，2：已处理")
    private String status;
}
