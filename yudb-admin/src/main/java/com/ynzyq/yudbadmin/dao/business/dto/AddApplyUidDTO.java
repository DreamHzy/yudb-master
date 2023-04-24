package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/4/6 15:26
 * @description:
 */
@Data
public class AddApplyUidDTO implements Serializable {
    @ApiModelProperty("签约人")
    @NotBlank(message = "签约人不能为空")
    private String signatory;

    @ApiModelProperty("联系方式")
    @NotBlank(message = "联系方式不能为空")
    private String mobile;

    @ApiModelProperty("身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String idCard;

    @ApiModelProperty("签约时间")
    @NotBlank(message = "签约时间不能为空")
    private String signTime;

    @ApiModelProperty("合同到期时间")
    @NotBlank(message = "合同到期时间不能为空")
    private String expireTime;

    @ApiModelProperty("管理费")
    @NotBlank(message = "管理费不能为空")
    private String manageMoney;

    @ApiModelProperty("履约保证金")
    @NotBlank(message = "履约保证金不能为空")
    private String bondMoney;

    @ApiModelProperty("加盟费")
    @NotBlank(message = "加盟费不能为空")
    private String franchiseMoney;

    @ApiModelProperty("是否为代理：1：是，2：否")
    @NotBlank(message = "是否为代理不能为空")
    private String isAgent;

    @ApiModelProperty("是否历史合作客户：1：是，2：否")
    @NotBlank(message = "是否历史合作客户不能为空")
    private String isCooperate;
}
