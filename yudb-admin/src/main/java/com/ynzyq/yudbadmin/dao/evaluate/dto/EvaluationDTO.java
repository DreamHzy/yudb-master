package com.ynzyq.yudbadmin.dao.evaluate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xinchen
 * @String 2021/12/1 15:07
 * @description:
 */
@Data
public class EvaluationDTO implements Serializable {
    @ApiModelProperty("大区")
    @NotBlank(message = "大区不能为空")
    private String region;

    @ApiModelProperty("申请者")
    private String applicant;

    @ApiModelProperty("证件照地址")
    private String idCard;

    @ApiModelProperty("出生日期")
    private String birth;

    @ApiModelProperty("籍贯")
    private String hometown;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("婚姻状况")
    private String marriage;

    @ApiModelProperty("邮箱")
    private String mail;

    @ApiModelProperty("紧急联络人")
    private String emergencyContact;

    @ApiModelProperty("与紧急联络人关系")
    private String relation;

    @ApiModelProperty("紧急联系人电话")
    private String emergencyContactPhone;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("是否已有店铺")
    private String isExistStore;

    @ApiModelProperty("计划开店区域")
    private String openStoreArea;

    @ApiModelProperty("预计开店时间")
    private String openStoreTime;

    @ApiModelProperty("是否有创业经历")
    private String isEntrepreneurshipExperience;

    @ApiModelProperty("预计投资金额")
    private String investmentAmount;

    @ApiModelProperty("你开业的目的")
    private String purposeOfOpen;

    @ApiModelProperty("开店资金来源")
    private String sourcesOfFunds;

    @ApiModelProperty("了解鱼你在一起品牌的渠道")
    private String channel;

    @ApiModelProperty("是否有合伙人")
    private String isPartner;

    @ApiModelProperty("鱼你在一起的运作管理")
    private String operationManagement;

    @ApiModelProperty("计划开店时间")
    private String planOpenStoreTime;

    @ApiModelProperty("特许管理标准的认可")
    private String recognized;

    @ApiModelProperty("您期望的回本周期")
    private String paybackCycle;

    @ApiModelProperty("您是否曾到鱼你在一起门店消费过")
    private String isConsumption;

    @ApiModelProperty("您认为鱼你在一起与同行业最大的差异在哪里")
    private List<String> difference;

    @ApiModelProperty("其他内容")
    private String differenceRemark;

    @ApiModelProperty("请问您为何想合作鱼你在一起品牌连锁店")
    private List<String> cooperate;

    @ApiModelProperty("自述内容")
    private String cooperateRemark;
//
//    @ApiModelProperty("填表日期")
//    private String createDate;
}
