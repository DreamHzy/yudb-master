package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/27 1:06
 * @description:
 */
@Data
public class ExamineFileDetail implements Serializable {

    @ApiModelProperty("文件id")
    private String id;

    @ApiModelProperty("文件路径")
    private String url;
}
