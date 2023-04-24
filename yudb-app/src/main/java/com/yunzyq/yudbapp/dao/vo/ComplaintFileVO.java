package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/1/25 14:54
 * @description:
 */
@Data
public class ComplaintFileVO implements Serializable {
    @ApiModelProperty("图片地址")
    private String url;
}
