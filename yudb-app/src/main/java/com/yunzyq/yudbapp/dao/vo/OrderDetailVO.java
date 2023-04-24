package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/2/25 15:56
 * @description:
 */
@Data
public class OrderDetailVO implements Serializable {
    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("所属代理")
    private String merchantName;

    @ApiModelProperty("订单类型：1：首配，2：二配，3：线下下单")
    private String type;

    @ApiModelProperty("下单类型：1：线上下单，2：线下下单")
    private String placeOrderType;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("订单状态")
    private String status;

    @ApiModelProperty("订单金额")
    private String orderMoney;

    @ApiModelProperty("返利金额")
    private String rebateMoney;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("状态说明")
    private String remark;

    @ApiModelProperty("支付方式")
    private String payWay;

    @ApiModelProperty("收货人")
    private String receiver;

    @ApiModelProperty("收货电话")
    private String receiverMobile;

    @ApiModelProperty("收货地址")
    private String deliveryAddress;

    @ApiModelProperty("门店uid")
    private String storeUid;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("商品金额")
    private String goodsMoney;

    @ApiModelProperty("运费")
    private String freight;

    @ApiModelProperty("是否加急配送：1：是，2：否")
    private String isDoubleFreight;

    @ApiModelProperty("支付金额")
    private String payMoney;

    @ApiModelProperty("付款截止时间")
    private String expireTime;

    @ApiModelProperty("操作人")
    private String userName;

    @ApiModelProperty("是否有售后：等于0没有，大于0有售后")
    private String hasAfterSold;

    @ApiModelProperty("配送方式：1：物流，2：到仓自提，3：快递到付")
    private String distributionType;

    @ApiModelProperty("代仓地址")
    private String address;

    @ApiModelProperty("代仓联系人")
    private String contactPerson;

    @ApiModelProperty("自提日期")
    private String pickUpTime;

    @ApiModelProperty("姓名")
    private String contactUserName;

    @ApiModelProperty("电话")
    private String contactMobile;

    @ApiModelProperty("身份证后6位")
    private String idCard;

    @ApiModelProperty("审核状态")
    private Integer auditStatus;

    @ApiModelProperty("是否配置")
    private Integer isConfig;

    @ApiModelProperty("退款金额")
    private Integer refundMoney;

    @ApiModelProperty("是否显示收货地址：1：是，2：否")
    private String showDeliveryAddress;

    @ApiModelProperty("运费省份")
    private String deliveryProvince;

    @ApiModelProperty("运费市区")
    private String deliveryCity;

    @ApiModelProperty("运费区县")
    private String deliveryArea;

    @ApiModelProperty("商品信息")
    private List<OrderGoods> orderDetailGoodsList;
}
