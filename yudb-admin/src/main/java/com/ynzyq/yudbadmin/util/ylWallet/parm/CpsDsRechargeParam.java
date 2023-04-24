package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class CpsDsRechargeParam extends BaseParam {


    /**
     * 商户订单号
     */
    private String mctOrderNo;

    /**
     * 电子账户 ID
     */
    private String walletId;

    /**
     * 充值金额
     */
    private String amount;


    /**
     * 银行卡号
     */
    private String bankAcctNo;


//    /**
//     * 密码密文激活账户时必填。填写方式见附录
//     */
//    private String encryptPwd;
//
//    /**
//     * 加密类型激活账户时必填。填写方式见附录
//     */
//    private String encryptType;
//
//    /**
//     * 随机因子激活账户时必填。填写方式见附录
//     */
//    private String plugRandomKey;
}
