package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xinchen
 * @date 2022/4/6 14:31
 * @description:
 */
@Data
public class ListApplyRecordVO {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("签约人")
    private String signatory;

    @ApiModelProperty("联系方式")
    private String mobile;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("签约时间")
    private String signTime;

    @ApiModelProperty("合同到期时间")
    private String expireTime;

    @ApiModelProperty("管理费")
    private String manageMoney;

    @ApiModelProperty("履约保证金")
    private String bondMoney;

    @ApiModelProperty("加盟费")
    private String franchiseMoney;

    @ApiModelProperty("是否为代理：1：是，2：否")
    private String isAgent;

    @ApiModelProperty("是否历史合作客户：1：是，2：否")
    private String isCooperate;

    @ApiModelProperty("创建时间")
    private String createTime;
}
