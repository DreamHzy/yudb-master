package com.ynzyq.yudbadmin.util.ylWallet.parm;


import lombok.Data;

@Data
public class SendSmsAuthCodeParam extends BaseParam{

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 短信模板编码
     */
    private String smsTmpltCode;


    /**
     * 短信业务类型
     * 短信业务类型。开户时可以不填，若填 4 则会验证该
     * 手机号是否已经开户。
     * 1：绑定第三方用户
     * 2：重置密码
     * 3：变更手机号
     * 4：开户
     * 5：银行卡签约
     * 6：交易
     */
    private String smsBizType;


}
