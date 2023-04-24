package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class BankAcctSignConfirmParam extends BaseParam {


    /**
     * 电子账户 ID
     */
    private String walletId;

    /**
     * 原签约申请流水号
     */
    private String oriReqSn;

    /**
     * 短信验证码 银行发送的签约验证码
     */
    private String verifyCode;

}
