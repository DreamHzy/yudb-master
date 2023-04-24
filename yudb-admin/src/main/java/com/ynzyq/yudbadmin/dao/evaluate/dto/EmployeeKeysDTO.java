package com.ynzyq.yudbadmin.dao.evaluate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeKeysDTO implements Serializable {

    @ApiModelProperty("关键字")
    private String keyWords;

}
