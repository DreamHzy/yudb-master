package com.ynzyq.yudbadmin.dao.evaluate.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class EmployeeStorageDTO implements Serializable {

    @NotBlank(message = "员工编号不能为空")
    @ApiModelProperty("员工编号")
    private String employeeId;
    @NotBlank(message = "员工姓名不能为空")
    @ApiModelProperty("员工姓名")
    private String employeeName;
    @NotBlank(message = "银行账户不能为空")
    @ApiModelProperty("银行账户")
    private String bankAccount;
    @NotBlank(message = "账户名称不能为空")
    @ApiModelProperty("账户名称")
    private String accountName;
    @NotBlank(message = "银行网点不能为空")
    @ApiModelProperty("银行网点")
    private String bankBranch;
    @NotBlank(message = "开户银行不能为空")
    @ApiModelProperty("开户银行")
    private String depositaryBank;
    @NotBlank(message = "开户行地址不能为空")
    @ApiModelProperty("开户行地址")
    private String accountOpeningAddress;
    @NotBlank(message = "银联号不能为空")
    @ApiModelProperty("银联号")
    private String unionpayNumber;
    @ApiModelProperty("备注")
    private String remark;

    public EmployeeStorageDTO(String employeeId, String employeeName, String bankAccount, String accountName, String bankBranch, String depositaryBank, String accountOpeningAddress, String unionpayNumber, String remark) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.bankAccount = bankAccount;
        this.accountName = accountName;
        this.bankBranch = bankBranch;
        this.depositaryBank = depositaryBank;
        this.accountOpeningAddress = accountOpeningAddress;
        this.unionpayNumber = unionpayNumber;
        this.remark = remark;
    }
}
