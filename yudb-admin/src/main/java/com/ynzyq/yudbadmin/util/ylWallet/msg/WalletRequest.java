package com.ynzyq.yudbadmin.util.ylWallet.msg;

public class WalletRequest {

    /** 发起方标志（必填） */
    private String issrId;

    /**
     * 报文编号（类型）
     * <li>业务网关或企业网关1.0.1接入，该字段必填</li>
     * <li>企业网关1.0.2接入，该字段可不填</li>
     */
    private String msgType;

    /** 发起方请求流水（唯一标识一个报文，保证一天内唯一） */
    private String reqSn;

    /** 请求方发起日期时间（yyyy-mm-ddTHH:MM:SS） */
    private String sndDate;

    /** 报文体字符串（json格式） */
    private String msgBody;

    /** 钱包机构编号（非必填） */
    private String walletBranchCode;

    public String getIssrId() {
        return issrId;
    }

    public void setIssrId(String issrId) {
        this.issrId = issrId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getReqSn() {
        return reqSn;
    }

    public void setReqSn(String reqSn) {
        this.reqSn = reqSn;
    }

    public String getSndDate() {
        return sndDate;
    }

    public void setSndDate(String sndDate) {
        this.sndDate = sndDate;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getWalletBranchCode() {
        return walletBranchCode;
    }

    public void setWalletBranchCode(String walletBranchCode) {
        this.walletBranchCode = walletBranchCode;
    }
}
