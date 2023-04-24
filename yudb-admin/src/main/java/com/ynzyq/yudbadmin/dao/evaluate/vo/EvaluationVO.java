package com.ynzyq.yudbadmin.dao.evaluate.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @String 2021/12/1 15:07
 * @description:
 */
@Data
public class EvaluationVO implements Serializable {
    @ApiModelProperty("大区")
    @ExcelProperty(value = "大区")
    private String region;

    @ExcelProperty(value = "申请者")
    @ApiModelProperty("申请者")
    private String applicant;

    @ExcelProperty(value = "证件照地址")
    @ApiModelProperty("证件照地址")
    private String idCard;

    @ExcelProperty(value = "出生日期")
    @ApiModelProperty("出生日期")
    private String birth;

    @ExcelProperty(value = "籍贯")
    @ApiModelProperty("籍贯")
    private String hometown;

    @ExcelProperty(value = "联系电话")
    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ExcelProperty(value = "婚姻状况")
    @ApiModelProperty("婚姻状况")
    private String marriage;

    @ExcelProperty(value = "邮箱")
    @ApiModelProperty("邮箱")
    private String mail;

    @ExcelProperty(value = "紧急联络人")
    @ApiModelProperty("紧急联络人")
    private String emergencyContact;

    @ExcelProperty(value = "与紧急联络人关系")
    @ApiModelProperty("与紧急联络人关系")
    private String relation;

    @ExcelProperty(value = "紧急联系人电话")
    @ApiModelProperty("紧急联系人电话")
    private String emergencyContactPhone;

    @ExcelProperty(value = "省")
    @ApiModelProperty("省")
    private String province;

    @ExcelProperty(value = "市")
    @ApiModelProperty("市")
    private String city;

    @ExcelProperty(value = "区")
    @ApiModelProperty("区")
    private String area;

    @ExcelProperty(value = "详细地址")
    @ApiModelProperty("详细地址")
    private String address;

    @ExcelProperty(value = "是否已有店铺")
    @ApiModelProperty("是否已有店铺")
    private String isExistStore;

    @ExcelProperty(value = "计划开店区域")
    @ApiModelProperty("计划开店区域")
    private String openStoreArea;

    @ExcelProperty(value = "预计开店时间")
    @ApiModelProperty("预计开店时间")
    private String openStoreTime;

    @ExcelProperty(value = "是否有创业经历")
    @ApiModelProperty("是否有创业经历")
    private String isEntrepreneurshipExperience;

    @ExcelProperty(value = "预计投资金额")
    @ApiModelProperty("预计投资金额")
    private String investmentAmount;

    @ExcelProperty(value = "你开业的目的")
    @ApiModelProperty("你开业的目的")
    private String purposeOfOpen;

    @ExcelProperty(value = "开店资金来源")
    @ApiModelProperty("开店资金来源")
    private String sourcesOfFunds;

    @ExcelProperty(value = "了解鱼你在一起品牌的渠道")
    @ApiModelProperty("了解鱼你在一起品牌的渠道")
    private String channel;

    @ExcelProperty(value = "是否有合伙人")
    @ApiModelProperty("是否有合伙人")
    private String isPartner;

    @ExcelProperty(value = "鱼你在一起的运作管理")
    @ApiModelProperty("鱼你在一起的运作管理")
    private String operationManagement;

    @ExcelProperty(value = "计划开店时间")
    @ApiModelProperty("计划开店时间")
    private String planOpenStoreTime;

    @ExcelProperty(value = "特许管理标准的认可")
    @ApiModelProperty("特许管理标准的认可")
    private String recognized;

    @ExcelProperty(value = "您期望的回本周期")
    @ApiModelProperty("您期望的回本周期")
    private String paybackCycle;

    @ExcelProperty(value = "您是否曾到鱼你在一起门店消费过")
    @ApiModelProperty("您是否曾到鱼你在一起门店消费过")
    private String isConsumption;

    @ExcelProperty(value = "您认为鱼你在一起与同行业最大的差异在哪里")
    @ApiModelProperty("您认为鱼你在一起与同行业最大的差异在哪里")
    private String difference;

    @ExcelProperty(value = "其他内容")
    @ApiModelProperty("其他内容")
    private String differenceRemark;

    @ExcelProperty(value = "请问您为何想合作鱼你在一起品牌连锁店")
    @ApiModelProperty("请问您为何想合作鱼你在一起品牌连锁店")
    private String cooperate;

    @ExcelProperty(value = "自述内容")
    @ApiModelProperty("自述内容")
    private String cooperateRemark;
}
