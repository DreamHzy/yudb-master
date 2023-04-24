package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/27 12:49
 * @description:
 */
@Data
public class SystemUserVO implements Serializable {
    @ApiModelProperty("审批人/会签人id")
    private String userId;

    @ApiModelProperty("审批人/会签人姓名")
    private String userName;
}
