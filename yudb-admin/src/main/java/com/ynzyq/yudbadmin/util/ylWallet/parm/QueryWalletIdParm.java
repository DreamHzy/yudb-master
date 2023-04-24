package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryWalletIdParm {
    /**
     * 与获取凭据接口上送的外部用户 ID 相同
     */
    private String extUserId;
}
