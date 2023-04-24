package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class BatchTransferListParam {


    //明细流水号
    private String sn;

    //转入电子账户 ID
    private String intoWalletId;



    //转入电子账户名称
    private String intoWalletName;


    private String amount;


    private String bizType;


}
