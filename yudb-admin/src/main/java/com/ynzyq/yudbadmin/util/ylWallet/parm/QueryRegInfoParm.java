package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryRegInfoParm {

    /**
     * 登记手机号 mobileNo、registerNo 至少上送一个
     */
    private String mobileNo;

    /**
     * 注册号 mobileNo、registerNo 至少上送一个
     */
    private String registerNo;
}
