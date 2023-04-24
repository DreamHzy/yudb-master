package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class ValidSmsAuthCodeParam extends BaseParam {


    /**
     * 需要验证的手机号码
     */
    private String mobileNo;

    /**
     * 短信验证码
     */
    private String smsAuthCode;
}
