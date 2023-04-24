package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StroePageVo {

    @ExcelIgnore
    private String id;

    @ExcelProperty(value = "门店类型")
    @ApiModelProperty("门店类型")
    private String type;

    @ExcelProperty(value = "授权号")
    @ApiModelProperty("授权号")
    private String uid;

    @ExcelProperty(value = "加盟商名称")
    @ApiModelProperty("加盟商名称")
    private String merchantName;

    @ExcelProperty(value = "加盟商电话")
    @ApiModelProperty("加盟商电话")
    private String merchantMobile;

    @ExcelProperty(value = "地址")
    @ApiModelProperty("地址")
    private String address;

    @ExcelProperty(value = "一级状态")
    @ApiModelProperty("一级状态")
    private String status;

    @ExcelIgnore
    @ApiModelProperty("状态标识")
    private String statusNum;

    @ExcelProperty(value = "签约时间")
    @ApiModelProperty("签约时间")
    private String signTime;

    @ExcelProperty(value = "合同到期时间")
    @ApiModelProperty("合同到期时间")
    private String expireTime;

    @ExcelProperty(value = "区域经理")
    @ApiModelProperty("区域经理")
    private String regionalManagerName;

    @ExcelIgnore
    @ApiModelProperty("区域经理标识")
    private String regionalManagerId;

    @ExcelProperty(value = "部门")
    @ApiModelProperty("部门")
    private String departmentName;

    @ExcelProperty(value = "合同状态")
    @ApiModelProperty("合同状态")
    private String contractStatus;

    @ExcelIgnore
    @ApiModelProperty("合同状态")
    private String contractStatusId;

    @ExcelIgnore
    @ApiModelProperty("二级状态")
    private String statusTwo;

    @ExcelProperty(value = "二级状态")
    @ApiModelProperty("二级状态说明")
    private String statusTwoDesc;

    @ExcelProperty(value = "加盟费")
    @ApiModelProperty("加盟费")
    private String franchiseFee;

    @ExcelProperty(value = "保证金")
    @ApiModelProperty("保证金")
    private String bondMoney;

    @ExcelProperty(value = "管理费")
    @ApiModelProperty("管理费")
    private String managementExpense;

    @ExcelProperty(value = "开业时间")
    @ApiModelProperty("开业时间")
    private String openTime;

    @ExcelProperty(value = "重开业时间")
    @ApiModelProperty("重开业时间")
    private String openAgainTime;

    @ExcelProperty(value = "闭店时间")
    @ApiModelProperty("闭店时间")
    private String closeTime;

    @ExcelProperty(value = "迁址时间")
    @ApiModelProperty("迁址时间")
    private String relocationTime;

    @ExcelProperty(value = "暂停营业时间")
    @ApiModelProperty("暂停营业时间")
    private String pauseTime;

    @ExcelProperty(value = "预计开业时间")
    @ApiModelProperty("预计开业时间")
    private String estimateTime;

    @ExcelIgnore
    @ApiModelProperty("所属代理id")
    private String agentId;

    @ExcelProperty(value = "所属代理")
    @ApiModelProperty("所属代理")
    private String agentName;


    @ExcelProperty(value = "是否适用代理优惠")
    @ApiModelProperty("是否适用代理优惠：1：是，2：否")
    private String isPreferential;

    @ExcelIgnore
    @ApiModelProperty("代理商id")
    private String merchantId;

    @ExcelProperty(value = "省")
    @ApiModelProperty("省")
    private String province;

    @ExcelProperty(value = "市")
    @ApiModelProperty("市")
    private String city;

    @ExcelProperty(value = "区")
    @ApiModelProperty("区")
    private String area;

    @ExcelProperty(value = "服务到期时间")
    @ApiModelProperty("服务到期时间")
    private String serviceExpireTime;

    @ExcelProperty(value = "区域")
    @ApiModelProperty("区域")
    private String region;

    @ExcelProperty(value = "线级城市")
    @ApiModelProperty("线级城市")
    private String level;

    @ExcelProperty(value = "餐位数")
    @ApiModelProperty("餐位数")
    private String seatCount;

    @ExcelProperty(value = "哗啦啦授权码")
    @ApiModelProperty("哗啦啦授权码")
    private String hllCode;

    @ExcelProperty(value = "美团ID")
    @ApiModelProperty("美团ID")
    private String mtId;

    @ExcelProperty(value = "饿了么ID")
    @ApiModelProperty("饿了么ID")
    private String elmId;

    @ExcelProperty(value = "大众点评ID")
    @ApiModelProperty("大众点评ID")
    private String dzdpId;

    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remark;

    @ExcelProperty(value = "收款月份")
    @ApiModelProperty("收款月份")
    private String collectionMonth;

    @ExcelProperty(value = "延期开业")
    @ApiModelProperty("延期开业")
    private String delayedOpen;

    @ExcelProperty(value = "创建人")
    @ApiModelProperty("创建人")
    private String createUser;

    @ExcelProperty(value = "创建时间")
    @ApiModelProperty("创建时间")
    private String createTime;

    @ExcelProperty(value = "送货地址")
    @ApiModelProperty("送货地址")
    private String deliveryAddress;

    @ExcelProperty(value = "配送限制")
    @ApiModelProperty("配送限制")
    private String distributionLimit;

    @ExcelProperty(value = "收货人")
    @ApiModelProperty("收货人")
    private String receiver;

    @ExcelProperty(value = "收货电话")
    @ApiModelProperty("收货电话")
    private String receiverMobile;

    @ExcelProperty(value = "是否显示收货地址")
    @ApiModelProperty("是否显示收货地址：1：是，2：否")
    private String showDeliveryAddress;

    @ExcelProperty(value = "运费省份")
    @ApiModelProperty("运费省份")
    private String deliveryProvince;

    @ExcelProperty(value = "运费市区")
    @ApiModelProperty("运费市区")
    private String deliveryCity;

    @ExcelProperty(value = "运费区县")
    @ApiModelProperty("运费区县")
    private String deliveryArea;

    @ExcelProperty(value = "所属代理权")
    @ApiModelProperty("所属代理权")
    private String belongAgency;

    @ExcelProperty(value = "申请调货")
    @ApiModelProperty("是否申请调货：1：开，2：关")
    private String isApply;
}
