package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CloudSchoolQueryVo {

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("云学堂列表")
    List<CloudSchool> cloudSchools;
}
