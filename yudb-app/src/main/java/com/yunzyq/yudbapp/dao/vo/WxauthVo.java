package com.yunzyq.yudbapp.dao.vo;

public class WxauthVo {

    private String appid;

    private String secret;

    private String js_code;

    private String grant_type;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setJs_code(String js_code) {
        this.js_code = js_code;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getJs_code() {
        return js_code;
    }

    public String getGrant_type() {
        return grant_type;
    }
}
