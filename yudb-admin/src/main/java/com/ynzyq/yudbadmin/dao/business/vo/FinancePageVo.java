package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class FinancePageVo {

    @ExcelIgnore
    private String id;

    @ApiModelProperty(value = "区域")
    @ExcelProperty(value = "区域")
    private String region;

    @ApiModelProperty(value = "授权号")
    @ExcelProperty(value = "授权号")
    private String merchantStoreUid;

    @ApiModelProperty(value = "加盟商姓名")
    @ExcelProperty(value = "加盟商姓名")
    private String merchantName;

    @ApiModelProperty(value = "支付渠道订单号")
    @ExcelProperty(value = "支付渠道订单号")
    private String channelOrderNo;

    @ApiModelProperty(value = "系统订单号")
    @ExcelProperty(value = "系统订单号")
    private String orderNo;

    @ApiModelProperty(value = "类型")
    @ExcelProperty(value = "类型")
    private String paymentTypeName;

    @ApiModelProperty(value = "金额")
    @ExcelProperty(value = "金额")
    private String money;

    @ApiModelProperty(value = "已缴费金额")
    @ExcelProperty(value = "已缴费金额")
    private String payMoney;

    @ApiModelProperty(value = "剩余应缴金额")
    @ExcelProperty(value = "剩余应缴金额")
    private String remain;

    @ApiModelProperty(value = "手续费")
    @ExcelProperty(value = "手续费")
    private String fees;

    @ApiModelProperty(value = "一级状态")
    @ExcelProperty(value = "一级状态")
    private String status;

    @ApiModelProperty(value = "归属区域经理")
    @ExcelProperty(value = "归属区域经理")
    private String regionalManagerName;

    @ApiModelProperty(value = "缴费截止日期")
    @ExcelProperty(value = "缴费截止日期")
    private String expireTime;

    @ApiModelProperty(value = "支付时间")
    @ExcelProperty(value = "支付时间")
    private String payTime;

    @ExcelProperty(value = "详情")
    @ApiModelProperty(value = "详情")
    private String remark;

    @ExcelProperty(value = "省份")
    @ApiModelProperty(value = "省份")
    private String province;

    @ExcelProperty(value = "城市")
    @ApiModelProperty(value = "城市")
    private String city;

    @ExcelProperty(value = "区域")
    @ApiModelProperty(value = "区域")
    private String area;


    @ExcelProperty(value = "详细地址")
    @ApiModelProperty(value = "详细地址")
    private String address;

    @ExcelProperty(value = "订单支付类型")
    @ApiModelProperty(value = "订单支付类型")
    private String payType;

    @ExcelProperty(value = "调整内容")
    @ApiModelProperty(value = "调整内容")
    private String adjustmentMsg;

    @ExcelProperty(value = "是否推送")
    @ApiModelProperty(value = "是否推送 1推送给加盟商 2未推送给加盟商")
    private String send;

    @ExcelProperty(value = "订单生成时间")
    @ApiModelProperty(value = "订单生成时间")
    private String createTime;

    @ExcelProperty(value = "是否清算")
    @ApiModelProperty(value = "是否清算：0：未清算，1：已清算")
    private String isSettle;

    @ExcelProperty(value = "是否提报")
    @ApiModelProperty(value = "是否提报：1：是，2：否")
    private String isReport;

    @ExcelIgnore
    @ApiModelProperty(value = "一级状态")
    private String primaryStatus;

    @ExcelProperty(value = "一级状态")
    @ApiModelProperty(value = "一级状态文本说明")
    private String primaryStatusDesc;

    @ExcelProperty(value = "收款月份")
    @ApiModelProperty(value = "收款月份")
    private String collectionMonth;

    @ExcelProperty(value = "签约时间")
    @ApiModelProperty(value = "签约时间")
    private String signTime;

    @ExcelProperty(value = "服务到期时间")
    @ApiModelProperty(value = "服务到期时间")
    private String serviceExpireTime;

    @ExcelProperty(value = "开业时间")
    @ApiModelProperty(value = "开业时间")
    private String openTime;

    @ExcelProperty(value = "是否发布")
    @ApiModelProperty("是否发布：1：已发布，2：未发布")
    private String examine;

    @ExcelProperty(value = "本账单服务开始时间")
    @ApiModelProperty(value = "本账单服务开始时间")
    private String serviceStartTime;

    @ExcelProperty(value = "本账单服务结束时间")
    @ApiModelProperty(value = "本账单服务结束时间")
    private String serviceEndTime;

    @ExcelProperty(value = "逾期天数")
    @ApiModelProperty(value = "逾期天数")
    private String overdueDay;
}
