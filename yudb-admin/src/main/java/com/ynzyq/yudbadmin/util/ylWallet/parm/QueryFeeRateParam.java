package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryFeeRateParam extends BaseParam {

    /**
     *
     */
    private String walletId;


    private String transType;
}
