package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class ResetBtypeAcctPwdParam extends BaseParam {

    /**
     * 电子账户 ID
     */
    private String walletId;

    /**
     * 新密码的密文
     */
    private String encryptNewPwd;

    /**
     * 加密类型
     */
    private String encryptType;

    /**
     * 随机因子
     */
    private String plugRandomKey;

    /**
     * 法人代表姓名
     */
    private String legalName;

    /**
     * 法人代表证件编号
     */
    private String legalIdCard;

    /**
     * 法人代表手机号码
     */
    private String legalPhoneNum;

    /**
     * 法人代表短信验证码
     */
    private String legalSmsAuthCode;


}
