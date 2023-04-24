package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExamineFileTwoVo {

    @ApiModelProperty("文件类型")
    private String name;

    @ApiModelProperty("文件地址列表")
    private List<FileVo> fileS;
}
