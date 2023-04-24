package com.ynzyq.yudbadmin.util.ylWallet.msg;

public class TransferParam extends BaseParam{

    /** 商户订单号 */
    private String mctOrderNo;
    /** 转出钱包ID */
    private String fromWalletId;
    /** 转出钱包名称 */
    private String fromWalletName;
    /** 转账金额 */
    private long transferAmt;
    /** 转入钱包ID */
    private String intoWalletId;
    /** 转入钱包名称 */
    private String intoWalletName;
    /** 业务类型代码：比如：010008表示工资 */
    private String bizType;
    /** 备注 */
    private String remark;
    /** 摘要 */
    private String abst;

    public String getMctOrderNo() {
        return mctOrderNo;
    }

    public void setMctOrderNo(String mctOrderNo) {
        this.mctOrderNo = mctOrderNo;
    }

    public String getFromWalletId() {
        return fromWalletId;
    }

    public void setFromWalletId(String fromWalletId) {
        this.fromWalletId = fromWalletId;
    }

    public String getFromWalletName() {
        return fromWalletName;
    }

    public void setFromWalletName(String fromWalletName) {
        this.fromWalletName = fromWalletName;
    }

    public long getTransferAmt() {
        return transferAmt;
    }

    public void setTransferAmt(long transferAmt) {
        this.transferAmt = transferAmt;
    }

    public String getIntoWalletId() {
        return intoWalletId;
    }

    public void setIntoWalletId(String intoWalletId) {
        this.intoWalletId = intoWalletId;
    }

    public String getIntoWalletName() {
        return intoWalletName;
    }

    public void setIntoWalletName(String intoWalletName) {
        this.intoWalletName = intoWalletName;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAbst() {
        return abst;
    }

    public void setAbst(String abst) {
        this.abst = abst;
    }
}
