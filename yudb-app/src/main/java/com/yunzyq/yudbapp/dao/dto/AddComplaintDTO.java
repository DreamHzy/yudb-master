package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/1/25 15:49
 * @description:
 */
@Data
public class AddComplaintDTO implements Serializable {
    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("投诉内容")
    private String content;

    @ApiModelProperty("相关凭证")
    private List<String> files;
}
