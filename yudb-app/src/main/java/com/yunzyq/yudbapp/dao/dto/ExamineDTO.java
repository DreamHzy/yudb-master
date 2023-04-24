package com.yunzyq.yudbapp.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/1/18 17:19
 * @description:
 */
@Data
public class ExamineDTO implements Serializable {
    @ApiModelProperty("审核记录id")
    private String id;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("审核类型：1：金额调整，2：延期支付，3：新订单审核，4：取消缴费，5：线下支付")
    private String examineType;
    private List<ExamineDetailDTO> ExamineDetailList;
}
