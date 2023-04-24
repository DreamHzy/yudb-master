package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class BankAcctSignApplyParam extends BaseParam {

    /**
     * 电子账户 ID
     */
    private String walletId;

    /**
     * 银行账户编号
     */
    private String accountNo;


    /**
     * 银行账户名称
     */
    private String accountName;

    /**
     * 银行账户类型
     */
    private String accountType;


    /**
     * 信用卡有效期
     * 信用卡必填。
     * 例如：01/19 表示 2019 年 01 月
     */
    private String validityDate;

    /**
     * 开户证件类型
     * 0：身份证
     * 1：户口簿
     * 2：护照
     * 3：军官证
     * 4：士兵证
     * 5: 港澳居民来往内地通行证
     * 6：台湾同胞来往内地通行证
     * 7：临时身份证
     * 8：外国人居留证
     * 9：警官证
     * X：其他证件
     * 10：营业执照
     * 11：组织机构代码证
     * 12：税务登记证
     * 13：统一社会信用代码证
     */
    private String idType;

    /**
     * 证件号
     * 开户时的证件号码
     */
    private String idNo;

    /**
     * 手机号
     * 开户时预留的 11 位手机号，用于接收银行短信验证码 。
     */
    private String tel;
}
