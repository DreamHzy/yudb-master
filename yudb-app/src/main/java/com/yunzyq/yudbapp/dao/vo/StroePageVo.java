package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StroePageVo {


    private String id;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("加盟商名称")
    private String merchantName;

    @ApiModelProperty("区域")
    private String area;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("电话")
    private String mobile;

    @ApiModelProperty("1代理 2不是代理")
    private String isAgent;

    @ApiModelProperty("合同到期时间")
    private String expireTime;

    @ApiModelProperty("合同状态 1进行中 2续约中3 待签署 4 签署中 5解约中 6解约 7续约中")
    private String contractStatus;

    @ApiModelProperty("一级状态")
    private String status;

    @ApiModelProperty("一级状态文本说明")
    private String statusDesc;

    @ApiModelProperty("需缴纳管理费")
    private BigDecimal managementExpense;

    @ApiModelProperty("已缴纳管理费")
    private BigDecimal alreadyManagementExpense;


}
