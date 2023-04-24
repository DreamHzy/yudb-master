package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/29 10:00
 * @description:
 */
@Data
public class ListCloudSchoolDTO implements Serializable {
    @ApiModelProperty("云学堂账号id")
    private Integer id;

    @ApiModelProperty("门店uid")
    private String merchantStoreUid;

    @ApiModelProperty("云学堂账号")
    private String account;
}
