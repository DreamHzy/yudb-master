package com.ynzyq.yudbadmin.dao.business.vo;

import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class MerchantStoreDetailVo {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("加盟商名称")
    private String merchantName;

    @ApiModelProperty("加盟商电话")
    private String merchantMobile;


    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区县")
    private String area;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("门店状态")
    private String status;

    @ApiModelProperty("二级状态")
    private String statusTwo;

    @ApiModelProperty("区域经理")
    private String regionalManagerName;

    @ApiModelProperty("所属部门")
    private String departmentName;

    @ApiModelProperty("签约人")
    private String signatory;

    @ApiModelProperty("签约人电话")
    private String mobile;

    @ApiModelProperty("签约证件号")
    private String idNumber;


    @ApiModelProperty("签约时间")
    private String signTime;

    @ApiModelProperty("合同到期时间")
    private String expireTime;

    @ApiModelProperty("服务到期时间")
    private String serviceExpireTime;

    private String merchantLink;
    private String merchantMoney;

    private String managementExpense;

    private String alreadyManagementExpense;

    private String cloudSchoolMoney;

    @ApiModelProperty("状态标识")
    private String statusNum;

    @ApiModelProperty("区域经理标识")
    private String regionalManagerId;

    @ApiModelProperty("合同状态")
    private String contractStatus;

    @ApiModelProperty("二级状态说明")
    private String statusTwoDesc;

    @ApiModelProperty("加盟费")
    private String franchiseFee;

    @ApiModelProperty("保证金")
    private String bondMoney;

    @ApiModelProperty("开业时间")
    private String openTime;

    @ApiModelProperty("闭店时间")
    private String closeTime;

    @ApiModelProperty("迁址时间")
    private String relocationTime;

    @ApiModelProperty("暂停营业时间")
    private String pauseTime;

    @ApiModelProperty("所属代理id")
    private String agentId;

    @ApiModelProperty("所属代理")
    private String agentName;

    @ApiModelProperty("是否适用代理优惠：1：是，2：否")
    private String isPreferential;

    @ApiModelProperty("代理商id")
    private String merchantId;

    @ApiModelProperty("收费标准")
    private List<ChargingStandardVo> chargingStandardVoList;

    @ApiModelProperty("缴费记录")
    List<MerchantStoreOrderVo> merchantStoreOrderVos;

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
