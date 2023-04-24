package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class OpenAcctParm extends BaseParam {

    /**
     * 外部用户id
     */
    private String extUserId;

    /**
     * 用户姓名
     */
    private String userName;


    /**
     * 手机号
     */
    private String mobileNo;


    /**
     * 证件编号
     */
    private String idCard;


    /**
     * 2：运营商三要素
     */
    private String authType;

    /**
     * 0：不激活，1：激活。默认为激活
     * 只有激活的账户才可正常进行交易。如果本接口不激活
     * 账户，那么需要额外调用激活账户接口
     */
    private String isActive;

    /**
     * 密码密文激活账户时必填。填写方式见附录
     */
    private String encryptPwd;

    /**
     * 加密类型激活账户时必填。填写方式见附录
     */
    private String encryptType;

    /**
     * 随机因子激活账户时必填。填写方式见附录
     */
    private String plugRandomKey;
}
