package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryProfitSharingMerRelParam extends BaseParam {

    /**
     * 平台钱包 ID  必填
     */
    private String platformWalletId;

    /**
     * 关联分账方钱包 ID  可不填
     */
    private String relMerWalletId;

}
