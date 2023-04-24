package com.ynzyq.yudbadmin.util.ylWallet.msg;

public class TransferResult extends BaseResult{

    /** 交易订单号 */
    private String transOrderNo;
    /** 总余额 */
    private Long totalAmt;
    /** 可用余额 */
    private Long avlblAmt;

    public String getTransOrderNo() {
        return transOrderNo;
    }

    public void setTransOrderNo(String transOrderNo) {
        this.transOrderNo = transOrderNo;
    }

    public Long getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Long totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Long getAvlblAmt() {
        return avlblAmt;
    }

    public void setAvlblAmt(Long avlblAmt) {
        this.avlblAmt = avlblAmt;
    }

}
