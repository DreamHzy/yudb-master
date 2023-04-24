package com.ynzyq.yudbadmin.util.ylWallet.msg;

public class BaseResult {

    /** 服务应答码，参考统一应答码标准 ，取自应答报文体 */
    private String rspCode;

    /** 服务应答结果描述 ，取自应答报文体 */
    private String rspResult;

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
}
