package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

import java.util.List;

@Data
public class BatchTransferParam extends BaseParam {

    //客户端唯一标记该笔交易
    private String mctOrderNo;

    //转出电子账户 ID
    private String fromWalletId;

    //总笔数
    private String totalCount;
    //总金额
    private String totalAmt;

    //推送地址，不强制
    private String notifyUrl;


    private List<BatchTransferListParam> list;

}
