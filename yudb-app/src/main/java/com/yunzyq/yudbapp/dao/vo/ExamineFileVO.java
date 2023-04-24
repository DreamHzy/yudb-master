package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/1/19 12:31
 * @description:
 */
@Data
public class ExamineFileVO implements Serializable {
    @ApiModelProperty("域名")
    private String imageUrl;

    @ApiModelProperty("图片")
    private List<String> photos;

    @ApiModelProperty("视频")
    private List<String> videos;
}
