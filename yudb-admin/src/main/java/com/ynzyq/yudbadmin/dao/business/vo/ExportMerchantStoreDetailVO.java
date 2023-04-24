package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ynzyq.yudbadmin.annotation.ExcelColumn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ExportMerchantStoreDetailVO {
    @ExcelProperty(value = "授权号")
    private String uid;

    @ExcelProperty(value = "店铺类型")
    private String type;

    @ExcelProperty(value = "省份")
    private String province;

    @ExcelProperty(value = "城市")
    private String city;

    @ExcelProperty(value = "区域")
    private String area;

    @ExcelProperty(value = "签约人")
    private String signatory;

    @ExcelProperty(value = "门店联系方式")
    private String mobile;

    @ExcelProperty(value = "身份证号/营业执照号")
    private String idNumber;

    @ExcelProperty(value = "一级状态")
    private String status;

    @ExcelProperty(value = "二级状态")
    private String statusTwo;

    @ExcelProperty(value = "加盟费")
    private String franchiseFee;

    @ExcelProperty(value = "保证金")
    private String bondMoney;

    @ExcelProperty(value = "管理费缴费标准")
    private String managementExpense;

    @ExcelProperty(value = "商户通缴费标准")
    private String chargingStandard;

    @ExcelProperty(value = "云学堂缴费标准")
    private String cloudSchool;

    @ExcelProperty(value = "云学堂账户是否开通")
    private String isOpen;

    @ExcelProperty(value = "云学堂账户开通数量")
    private String openNum;

    @ExcelProperty(value = "开店时间")
    private String openTime;

    @ExcelProperty(value = "重开业时间")
    private String openAgainTime;

    @ExcelProperty(value = "闭店时间")
    private String closeTime;

    @ExcelProperty(value = "迁址时间")
    private String relocationTime;

    @ExcelProperty(value = "暂停营业时间")
    private String pauseTime;

    @ExcelProperty(value = "所属代理")
    private String agentName;

    @ExcelProperty(value = "是否适用代理优惠")
    private String isPreferential;

    @ExcelProperty(value = "加盟商姓名")
    private String merchantName;

    @ExcelProperty(value = "加盟商手机号")
    private String merchantMobile;

    @ExcelProperty(value = "地址")
    private String address;

    @ExcelProperty(value = "合同状态")
    private String contractStatus;

    @ExcelProperty(value = "区域经理")
    private String regionalManagerName;

    @ExcelProperty(value = "所属部门")
    private String departmentName;

    @ExcelProperty(value = "签约时间")
    private String signTime;

    @ExcelProperty(value = "合同到期时间")
    private String expireTime;

    @ExcelProperty(value = "服务到期时间")
    private String serviceExpireTime;

    @ExcelProperty(value = "预计开业时间")
    @ApiModelProperty("预计开业时间")
    private String estimateTime;

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

    @ExcelProperty(value = "申请调货")
    @ApiModelProperty("是否申请调货：1：开，2：关")
    private String isApply;

    @ExcelProperty(value = "所属代理权")
    @ApiModelProperty("所属代理权")
    private String belongAgency;
}
