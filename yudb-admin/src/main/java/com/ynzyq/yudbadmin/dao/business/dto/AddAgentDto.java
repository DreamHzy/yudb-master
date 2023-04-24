package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AddAgentDto {

    @ApiModelProperty("加盟商标识")
    @NotBlank(message = "加盟商标识不能为空")
    private String merchantId;

    @ApiModelProperty("授权号")
    @NotBlank(message = "授权号不能为空")
    private String uid;

    @ApiModelProperty("省市区数组")
//    @NotEmpty(message = "省市区数组不能为空")
    private List<AgentAreaDTO> areaList;

    @ApiModelProperty("区域经理标识")
    @NotBlank(message = "区域经理标识不能为空")
    private String regionalManagerId;


    @ApiModelProperty("签约时间")
    @NotBlank(message = "签约时间不能为空")
    private String signTime;

    @ApiModelProperty("合同到期时间")
    @NotBlank(message = "合同到期时间不能为空")
    private String expireTime;

    @ApiModelProperty("服务到期时间")
    @NotBlank(message = "服务到期时间不能为空")
    private String serviceExpireTime;

    @ApiModelProperty(value = "代理-管理费缴纳标准")
    @NotBlank(message = "代理-管理费缴纳标准不能为空")
    private String managementExpense;

    @ApiModelProperty(value = "代理-管理费缴需缴纳")
    @NotBlank(message = "代理-管理费缴需缴纳不能为空")
    private String alreadyManagementExpense;

    @ApiModelProperty("代理费")
    @NotBlank(message = "代理费不能为空")
    private String agencyFees;

    @ApiModelProperty("履约保证金")
    @NotBlank(message = "履约保证金不能为空")
    private String depositFee;

    @ApiModelProperty("是否建仓：1：是，2：否")
    @NotBlank(message = "是否建仓不能为空")
    private String isOpenPosition;
}
