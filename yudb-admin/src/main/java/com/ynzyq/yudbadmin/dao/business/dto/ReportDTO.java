package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/1/7 10:26
 * @description:
 */
@Data
public class ReportDTO implements Serializable {
    @ApiModelProperty("缴费订单id数组")
    @NotEmpty(message = "缴费订单id数组不能为空")
    private List<String> ids;

    @ApiModelProperty("是否提报：1：是，2：否")
    private String isReport;
}
