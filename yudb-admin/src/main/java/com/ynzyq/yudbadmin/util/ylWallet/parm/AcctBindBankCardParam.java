package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class AcctBindBankCardParam extends BaseParam {


    /**
     * 电子账户 ID
     */
    private String walletId;


    /**
     * 操作方式
     * 1：绑定
     * 2：绑定并设置为默认卡
     * 3：解绑
     * 4：设置默认卡
     */
    private String oprtType;


    /**
     * 银行账户类型
     * 绑定时必填。
     * 0：对公银行账户
     * 1：对私银行账户（默认）
     * 仅 B 端非个体工商户可绑定对公银行账户
     */
    private String bankAcctType;

    /**
     * 银行卡
     */
    private String bankAcctNo;


    /**
     * 手机号
     * 绑定时必填。
     * 需要绑定的卡在银行的预留手机号
     */
    private String mobileNo;


    /**
     * 证件编号
     * 绑定时必填。
     * 对公银行账户填写开户人证件编号。
     * 对私银行账户只能填写开户人身份证
     */
    private String idCard;


    /**
     * 银行账户户名
     * 绑定时必填。
     * 对公银行账户需要与企业营业执照名称一致，。
     * 对私银行账户，C 端需与开户人名一致，B 端个体工商
     * 户需与法人名称一致。
     */
    private String bankAcctName;


    /**
     * 开户行号
     * 绑定时必填。
     * 3 位或 8 位的开户行号。
     */
    private String bankNo;


    /**
     * 电子联行号
     * B 端账户绑定对公银行卡时必填。
     */
    private String elecBankNo;



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
