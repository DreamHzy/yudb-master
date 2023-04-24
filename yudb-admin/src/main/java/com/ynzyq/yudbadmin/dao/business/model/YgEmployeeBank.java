package com.ynzyq.yudbadmin.dao.business.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class YgEmployeeBank {

    @ExcelIgnore
    private Integer id;

    @ExcelProperty(value = "员工编号")
    @ApiModelProperty("员工编号")
    private String employeeId;

    @ExcelProperty(value = "员工姓名")
    @ApiModelProperty("员工姓名")
    private String employeeName;

    @ExcelProperty(value = "银行账户")
    @ApiModelProperty("银行账户")
    private String bankAccount;

    @ExcelProperty(value = "账户名称")
    @ApiModelProperty("账户名称")
    private String accountName;

    @ExcelProperty(value = "银行网点")
    @ApiModelProperty("银行网点")
    private String bankBranch;

    @ExcelProperty(value = "开户银行")
    @ApiModelProperty("开户银行")
    private String depositaryBank;

    @ExcelProperty(value = "开户行地址")
    @ApiModelProperty("开户行地址")
    private String accountOpeningAddress;

    @ExcelProperty(value = "银联号")
    @ApiModelProperty("银联号")
    private String unionpayNumber;

    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remark;

    @ExcelIgnore
    private Date createTime;

}