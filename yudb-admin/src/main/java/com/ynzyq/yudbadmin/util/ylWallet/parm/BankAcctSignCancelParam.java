package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class BankAcctSignCancelParam extends BaseParam {

    /**
     * 电子账户 ID
     */
    private String walletId;

    /**
     * 银行账户编号
     */
    private String accountNo;


    /**
     * 密码密文激活账户时必填。填写方式见附录
     */
    private String encryptPwd;

    /**
     * 加密类型激活账户时必填。填写方式见附录
     */
    private String encryptType;

    /**
     * 随机因子激活账户时必填。填写方式见附录
     */
    private String plugRandomKey;
}
