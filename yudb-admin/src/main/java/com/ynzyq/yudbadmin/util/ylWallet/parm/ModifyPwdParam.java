package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class ModifyPwdParam extends BaseParam {


    /**
     * 电子账户 ID
     */
    private String walletId;

    /**
     * 原支付密码
     */
    private String encryptOrigPwd;

    /**
     * 新支付密码
     */
    private String encryptNewPwd;

    /**
     * 加密类型
     */
    private String encryptType;

    /**
     * plugRandomKey
     */
    private String plugRandomKey;

}
