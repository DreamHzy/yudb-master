package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/29 10:06
 * @description:
 */
@Data
public class CloudSchoolAccountVO implements Serializable {
    @ApiModelProperty("云学堂账号id")
    private String id;

    @ApiModelProperty("云学堂账号")
    private String account;
}
