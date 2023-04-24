package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QuerySettRegDetailParam extends BaseParam{

    private String mctOrderNo;

    private String platformWalletId;

    private String bizType;

    private String settOrderNo;


}
