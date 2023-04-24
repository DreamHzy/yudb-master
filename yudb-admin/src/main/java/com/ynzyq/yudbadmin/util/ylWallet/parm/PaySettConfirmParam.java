package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

import java.util.List;

@Data
public class PaySettConfirmParam extends BaseParam {


    private String mctOrderNo;

    private String refNo;

    private String settDate;

    private String platformWalletId;

    private String settAmt;

    private String isFinished;


    private String bizType;

    private String profitSharingRuleId;

    private String notifyUrl;

    private List<ProfitSharingList> profitSharingList;

}
