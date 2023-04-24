package com.ynzyq.yudbadmin.dao.evaluate.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeVO implements Serializable {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("员工编号")
    private String employeeId;

    @ApiModelProperty("员工姓名")
    private String employeeName;

    @ApiModelProperty("银行账户")
    private Integer bankAccount;

    @ApiModelProperty("账户名称")
    private String accountName;

    @ApiModelProperty("银行网点")
    private String bankBranch;

    @ApiModelProperty("开户银行")
    private String depositaryBank;

    @ApiModelProperty("开户行地址")
    private String accountOpeningAddress;

    @ApiModelProperty("银联号")
    private Integer unionpayNumber;

    @ApiModelProperty("备注")
    private String remark;

}
