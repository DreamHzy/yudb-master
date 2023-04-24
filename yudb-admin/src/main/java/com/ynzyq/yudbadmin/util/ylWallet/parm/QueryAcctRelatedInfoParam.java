package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryAcctRelatedInfoParam extends BaseParam {

    /**
     * 电子账户 ID
     */
    private String walletId;

    /**
     * 手机号
     */
    private String mobileNo;

}
