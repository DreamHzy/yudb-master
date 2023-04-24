package com.ynzyq.yudbadmin.util.ylWallet.msg;

public class WalletResponse {

    /** 服务应答码，参考统一应答码标准 ，取自应答报文体 */
    private String rspCode;

    /** 服务应答结果描述 ，取自应答报文体 */
    private String rspResult;

    /** 发起方标志 ，取自请求DTO */
    private String issrId;

    /** 报文编号（类型），取自请求DTO */
    private String msgType;

    /** 发起方请求流水，取自请求DTO */
    private String reqSn;

    /** 请求方发起日期时间（yyyy-mm-ddTHH:MM:SS），取自请求DTO */
    private String sndDate;

    /** 应答的报文体字符串（json格式） */
    private String msgBody;

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspResult() {
        return rspResult;
    }

    public void setRspResult(String rspResult) {
        this.rspResult = rspResult;
    }

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
}
