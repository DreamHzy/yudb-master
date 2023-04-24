package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

import java.util.List;

@Data
public class AddProfitSharingRuleParam extends BaseParam {


    /**
     * 平台电子账户 ID  （必传）
     */
    private String merWalletId;


    /**
     * 平台电子账户名称 （必传）
     */
    private String merName;


    /**
     * 业务类型 （必传）
     */
    private String bizType;


    /**
     * 是否指令分账0：非指令分账1：指令分账 （必传）
     */
    private String isInstr;


    /**
     * 分账资金清算周期
     * （T+N 日）
     * 范围 1~30 的整数，默认为 1。
     * 指令分账时填写无效。
     * 非指令分账传入 1 则表示 T+1 日进行清算
     */
    private String settCyc;


    /**
     * 分账起始金额
     * 单位（分）默认为 0。
     * 指令分账时填写无效。
     * 非指令分账当结算金额小于分账起始金额时，会自动全
     * 部分账给平台电子账户
     */
    private String startAmt;


    /**
     * 分账方式
     * 01：按比例分账
     * 02：按固定金额分账
     */
    private String settRuleType;


    /**
     * 分账清算规则
     */
    private List<SettRuleListParam> settRuleList;


    private List<FileListParam> fileList;


}
