package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryTransResultParam extends BaseParam {

    /**
     * 电子账户 ID
     */
    private String walletId;


    /**
     * 商户订单号
     */
    private String mctOrderNo;


}
