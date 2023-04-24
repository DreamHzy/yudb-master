package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryBindBankCardParam extends BaseParam {

    /**
     * 电子账户 ID(必传)
     */
    private String walletId;
    /**
     * 银行账户(非必传)
     */
    private String bankAcctNo;
}
