package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryAcctBalParam extends BaseParam {
    /**
     * 钱包id
     */
    private String walletId;

    /**
     * 0：不需要密码
     */
    private Integer isNeedPwd;
}
