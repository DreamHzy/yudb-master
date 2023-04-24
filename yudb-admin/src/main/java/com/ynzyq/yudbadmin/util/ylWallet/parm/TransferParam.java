package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class TransferParam extends BaseParam {
    /**
     * 商户订单号
     */
    private String mctOrderNo;
    /**
     * 转出钱包ID
     */
    private String fromWalletId;

    /**
     * 转账金额
     */
    private String transferAmt;

    /**
     * 转入钱包ID
     */
    private String intoWalletId;

    /**
     * 备注
     */
    private String remark;

}
