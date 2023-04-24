package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/2/25 10:50
 * @description:
 */
@Data
public class ListOrderDTO implements Serializable {
    @ApiModelProperty("订单状态：传空字符串表示查全部，1：待付款，2：进行中，3：配送中，4：已完成，5：待审核，10：已取消")
    @NotBlank(message = "订单状态不能为空")
    private String status;

    @ApiModelProperty("授权号")
    private String keyword;

    private Integer managerId;

    public void setStatus(String status) {
        if (StringUtils.isNotBlank(status)) {
            this.status = status;
        }
    }

    public void setKeyword(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            this.keyword = keyword;
        }
    }
}
