package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class ActiveAcctParam extends BaseParam {


    /**
     * 电子账户 ID
     */
    private String walletId;


    /**
     * 密码密文
     */
    private String encryptPwd;


    /**
     * 加密类型
     */
    private String encryptType;


    /**
     * 随机因子
     */
    private String plugRandomKey;


}
