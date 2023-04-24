package com.ynzyq.yudbadmin.dao.business.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayReviewPageVo {

    @ExcelIgnore
    private String id;

    @ExcelProperty(value = "申请人")
    @ApiModelProperty(value = "申请人")
    private String applicant;


    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @ExcelProperty(value = "授权码")
    @ApiModelProperty(value = "授权码")
    private String uid;


    @ExcelProperty(value = "费用类型")
    @ApiModelProperty(value = "费用类型")
    private String payTypeName;

    @ExcelProperty(value = "金额")
    @ApiModelProperty(value = "金额")
    private String money;

    @ExcelProperty(value = "审核类型")
    @ApiModelProperty(value = "审核类型")
    private String examineType;

    @ExcelProperty(value = "一审状态")
    @ApiModelProperty(value = "一审状态")
    private String examineOneStatus;

    @ExcelProperty(value = "二审状态")
    @ApiModelProperty(value = "二审状态")
    private String examineTwoStatus;


    @ExcelProperty(value = "具体内容")
    @ApiModelProperty(value = "具体内容")
    private String reviewMsg;


    @ExcelProperty(value = "申请时间")
    @ApiModelProperty(value = "申请时间")
    private String createTime;

    @ExcelProperty(value = "缴费截至时间")
    @ApiModelProperty(value = "缴费截至时间")
    private String expireTime;

    @ExcelProperty(value = "一级状态")
    @ApiModelProperty(value = "一级状态")
    private String status;

}
