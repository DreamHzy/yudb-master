package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WJ
 */
@Data
public class ListAgentVO implements Serializable {

    @ExcelIgnore
    @ApiModelProperty("id")
    private String id;

    @ExcelProperty(value = "代理商名称" )
    @ApiModelProperty("代理商名称")
    private String merchantName;

    @ExcelProperty(value = "客户电话" )
    @ApiModelProperty("客户电话")
    private String mobile;

    @ExcelProperty(value = "代理权代码" )
    @ApiModelProperty("代理权代码")
    private String uid;

    @ExcelProperty(value = "是否代理" )
    @ApiModelProperty("是否代理 1代理 2不是代理")
    private String isAgent;

    @ExcelProperty(value = "区域经理" )
    @ApiModelProperty("区域经理")
    private String regionalName;

    @ExcelProperty(value = "管理费标准" )
    @ApiModelProperty("管理费标准")
    private String managementExpense;

    @ApiModelProperty("区域经理ID")
    private String regionalNameId;

    @ExcelProperty(value = "开始时间" )
    @ApiModelProperty("开始时间")
    private String startTime;

    @ExcelProperty(value = "到期时间" )
    @ApiModelProperty("到期时间")
    private String expireTime;

    @ExcelProperty(value = "代理费" )
    @ApiModelProperty("代理费")
    private String  agencyFees;

    @ExcelProperty(value = "履约保证金" )
    @ApiModelProperty("履约保证金")
    private String  depositFee;

    @ExcelProperty(value = "签约时间" )
    @ApiModelProperty("签约时间")
    private String  signTime;

    @ExcelProperty(value = "是否建仓" )
    @ApiModelProperty("是否建仓：1：是，2：否")
    private String isOpenPosition;

    @ExcelProperty(value = "代理区域" )
    @ApiModelProperty("代理区域")
    private String agentArea;

    @ExcelProperty(value = "服务到期时间" )
    @ApiModelProperty("服务到期时间")
    private String serviceExpireTime;

    @ExcelProperty(value = "代理权是否生效" )
    @ApiModelProperty("代理权是否生效：1：是，2：否")
    private String isEffect;

    @ExcelProperty(value = "收款账户名称" )
    @ApiModelProperty("收款账户名称")
    private String accountName;

    @ExcelProperty(value = "收款账户号" )
    @ApiModelProperty("收款账户号")
    private String publicAccount;

    @ExcelProperty(value = "开户支行" )
    @ApiModelProperty("开户支行")
    private String bank;

//    @ExcelProperty(value = "收货人" )
//    @ApiModelProperty("收货人")
//    private String receiver;
//
//    @ExcelProperty(value = "收货电话" )
//    @ApiModelProperty("收货电话")
//    private String phone;
//
//    @ExcelProperty(value = "收货省" )
//    @ApiModelProperty("收货省")
//    private String receiptProvince;
//
//    @ExcelProperty(value = "收货市" )
//    @ApiModelProperty("收货市")
//    private String receiptCity;
//
//    @ExcelProperty(value = "收货区" )
//    @ApiModelProperty("收货区")
//    private String receiptArea;
//
//    @ExcelProperty(value = "收货地址" )
//    @ApiModelProperty("收货地址")
//    private String address;

    @ExcelIgnore
    @ApiModelProperty("代理体系id：1：1.0，2：2.0，3：3.0")
    private String systemFactor;

    @ExcelProperty(value = "代理体系文本" )
    @ApiModelProperty("代理体系文本")
    private String systemFactorDesc;

}
