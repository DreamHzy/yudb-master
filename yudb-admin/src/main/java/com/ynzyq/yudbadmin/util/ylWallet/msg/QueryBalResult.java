package com.ynzyq.yudbadmin.util.ylWallet.msg;

public class QueryBalResult extends BaseResult{

    /** 钱包id */
    private String walletId;

    /** 余额 (单位：分)*/
    private Long bal;

    /** 可用余额 (单位：分)*/
    private Long avlblAmt;

    /** 冻结余额 (单位：分)*/
    private Long frznAmt;

    /** 授信额度 (单位：分)*/
    private Long creditBal;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public Long getBal() {
        return bal;
    }

    public void setBal(Long bal) {
        this.bal = bal;
    }

    public Long getAvlblAmt() {
        return avlblAmt;
    }

    public void setAvlblAmt(Long avlblAmt) {
        this.avlblAmt = avlblAmt;
    }

    public Long getFrznAmt() {
        return frznAmt;
    }

    public void setFrznAmt(Long frznAmt) {
        this.frznAmt = frznAmt;
    }

    public Long getCreditBal() {
        return creditBal;
    }

    public void setCreditBal(Long creditBal) {
        this.creditBal = creditBal;
    }
}
