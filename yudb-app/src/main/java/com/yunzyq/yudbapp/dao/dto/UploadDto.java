package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDto {

    private MultipartFile file;

    @ApiModelProperty("3、视频 4、照片")
    private String type;
}
