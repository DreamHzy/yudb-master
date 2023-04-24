package com.ynzyq.yudbadmin.dao.business.vo;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EditMerchantStoreQueryVo {

    private String id;

    @ApiModelProperty("门店类型")
    private String type;

    @ApiModelProperty("加盟商名称")
    private String merchantName;

    @ApiModelProperty("加盟商标识")
    private String merchantId;

    @ApiModelProperty("授权号")
    private String uid;


    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("二级状态")
    private String statusTwo;

    @ApiModelProperty("区域经理")
    private String regionalManagerName;

    @ApiModelProperty("区域经理id")
    private String regionalManagerId;

    @ApiModelProperty("签约人")
    private String signatory;

    @ApiModelProperty("签约人电话")
    private String mobile;

    @ApiModelProperty("签约人证件号")
    private String idNumber;


    @ApiModelProperty("签约时间")
    private String signTime;

    @ApiModelProperty("合同到期时间")
    private String expireTime;

    @ApiModelProperty("服务到期时间")
    private String serviceExpireTime;

    @ApiModelProperty("是否开通商户通")
    private String merchantLink;
    @ApiModelProperty("商开通时间")
    private String merchantLinkTime;

    @ApiModelProperty(value = "管理费缴纳金额")
    private String managementExpense;

    @ApiModelProperty(value = "本年度管理费缴纳金额")
    private String alreadyManagementExpense;

    @ApiModelProperty(value = "开业时间")
    private String openTime;

    @ApiModelProperty(value = "闭店时间")
    private String closeTime;

    @ApiModelProperty(value = "迁址时间")
    private String relocationTime;

    @ApiModelProperty(value = "暂停营业时间")
    private String pauseTime;

    @ApiModelProperty(value = "加盟费")
    private String franchiseFee;

    @ApiModelProperty(value = "保证金")
    private String bondMoney;

    @ApiModelProperty("餐位数")
    private String seatCount;

    @ApiModelProperty("哗啦啦授权码")
    private String hllCode;

    @ApiModelProperty("美团ID")
    private String mtId;

    @ApiModelProperty("饿了么ID")
    private String elmId;

    @ApiModelProperty("大众点评ID")
    private String dzdpId;

    @ApiModelProperty("备注")
    private String remark;

}
