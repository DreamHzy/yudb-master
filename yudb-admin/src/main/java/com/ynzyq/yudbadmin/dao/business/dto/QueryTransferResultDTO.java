package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/6/17 11:50
 * @description:
 */
@Data
public class QueryTransferResultDTO implements Serializable {
    @ApiModelProperty("商户订单号")
    private String sn;

    @ApiModelProperty("处理状态：1：待处理，2：处理中，3：成功，4：失败")
    private String status;
}
