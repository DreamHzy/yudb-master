package com.ynzyq.yudbadmin.util.ylWallet.msg;

public class QueryBalParam extends BaseParam{

    /** 钱包ID */
    private String walletId;
    /** 是否验证支付密码 */
    private String isNeedPwd;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getIsNeedPwd() {
        return isNeedPwd;
    }

    public void setIsNeedPwd(String isNeedPwd) {
        this.isNeedPwd = isNeedPwd;
    }
}
