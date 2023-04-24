package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/1/25 15:25
 * @description:
 */
@Data
public class ComplaintDetailDTO implements Serializable {
    @ApiModelProperty("id")
    private String id;
}
