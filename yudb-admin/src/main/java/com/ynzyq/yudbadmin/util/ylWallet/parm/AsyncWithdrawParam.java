package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class AsyncWithdrawParam extends BaseParam {


    /**
     * 商户订单号
     */
    private String mctOrderNo;


    /**
     * 电子账户 ID
     */
    private String walletId;


    /**
     * 提现金额
     */
    private String amount;


    /**
     * 提现类型
     * T0：快捷提现
     */
    private String withdrawType;


    /**
     * 提现银行账号
     * 已绑定的银行账号。
     * 不填则提现到绑定的默认银行卡
     */
    private String bankAcctNo;


    /**
     * 备注
     */
    private String remark;

    private String noticeUrl;
}
