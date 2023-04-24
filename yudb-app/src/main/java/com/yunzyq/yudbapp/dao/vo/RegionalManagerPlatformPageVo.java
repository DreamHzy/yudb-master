package com.yunzyq.yudbapp.dao.vo;

import com.yunzyq.yudbapp.dao.dto.ExamineDetailDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RegionalManagerPlatformPageVo {

    private String id;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("加盟商名称")
    private String merchantName;

    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty(value = "费用类型")
    private String payTypeName;

    @ApiModelProperty(value = "金额")
    private String money;

    @ApiModelProperty(value = "已缴金额")
    private String payMoney;

    @ApiModelProperty(value = "剩余应缴金额")
    private String remain;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "订单状态1、待支付 2、支付成功 3、取消 4、审核拒绝")
    private String status;

    @ApiModelProperty(value = "是否推送")
    private String send;


    @ApiModelProperty(value = "付款截止时间")
    private String expireTime;

    @ApiModelProperty(value = "用户是否显示")
    private String merchantDisplay;

    @ApiModelProperty(value = "是否显示1显示 2不显示(如果为1的话显示 审核状态调整内容、拒绝原因)")
    private String display;

    @ApiModelProperty(value = "审核状态1、未审核 2、审核中 3：已撤回，4：审核完成")
    private String examineStatus;

    @ApiModelProperty(value = "调整内容")
    private String adjustment;

    @ApiModelProperty(value = "拒绝原因(只有  审核状态为拒绝的情况下会显示)")
    private String refuse;

    private String type;

    /**
     * 缴纳截止时间
     */
    @ApiModelProperty(value = "缴纳截止时间")
    private String naturalYearEnd;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "角色类型")
    private String roleTypeName;

    @ApiModelProperty(value = "审核人")
    private String approveName;

    @ApiModelProperty(value = "调整次数")
    private String adjustmentCount;

    @ApiModelProperty(value = "申请时间")
    private String createTime;

    @ApiModelProperty("具体内容")
    private String msg;

    @ApiModelProperty("备注")
    private String remark;

    private String deleted;

    @ApiModelProperty("调整类型：1、金额调整 2、延期支付 3、新订单审核 4、取消费用，5：线下支付")
    private String examineType;

    @ApiModelProperty("逾期天数")
    private String overdueDay;

    @ApiModelProperty("审核信息")
    private List<ExamineDetailDTO> examineDetailList;

}
