package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryFeeParam extends BaseParam{

    /**
     * 电子账户 ID
     */
    private String walletId;

    /**
     * 交易金额
     * 计费金额。
     * 单位（分）
     */
    private String transAmt;

    /**
     * 交易类型
     */
    private String transType;
}
