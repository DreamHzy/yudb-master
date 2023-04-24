package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ConfirmRenewalDto {
    private String id;

    @ApiModelProperty("金额")
    private String money;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("照片")
    private List<String> photos;

    @ApiModelProperty("文件")
    private List<FileDto> words;
}
