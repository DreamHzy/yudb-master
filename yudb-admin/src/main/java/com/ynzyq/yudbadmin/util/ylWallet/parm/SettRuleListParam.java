package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class SettRuleListParam {


    /**
     * 关联分账方
     * 电子账户 ID  (必传)
     */
    private String profitSharingWalletId;

    /**
     * 关联分账方
     * 电子账户名称  （必传）
     */
    private String profitSharingWalletName;


    /**
     * 清算比例
     * 万分之 N。
     * 分账方式为按比例分账时，该字段必填。
     */
    private String settRate;


    /**
     * 单笔金额最小值
     * 单位（分）
     * 最小为 0。
     * 分账方式为按比例分账时，该字段必填
     */
    private String rateMinSettAmt;

    /**
     * 单笔金额最大值
     * 单位（分）
     * 最小为 0 且必须大于或等于单笔金额最小值。
     * 分账方式为按比例分账时，该字段必填
     */
    private String rateMaxSettAmt;


    /**
     * 固定金额
     * 单位：分
     * 分账方式为按固定金额分账时，该字段必填
     */
    private String fixedSettAmt;


}
