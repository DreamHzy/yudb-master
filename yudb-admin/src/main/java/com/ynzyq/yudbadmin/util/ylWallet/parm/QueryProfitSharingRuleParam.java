package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryProfitSharingRuleParam extends BaseParam {


    /**（可不传）
     * 页数
     * 不填默认为 1
     */
    private String pageNo;

    /**（可不传）
     * 不填默认为 10，最大不超过 50
     * 每页条数
     */
    private String pageSize;

    /**（可不传）
     * 分账规则 id
     * 该记录的唯一标识
     */
    private String id;

    /**
     * 平台电子账户 ID
     */
    private String platformWalletId;


    /**
     * （可不传）
     * 状态
     * 01：新建（待提交）
     * 02：提交（待初审）
     * 03：生效
     * 04：失效
     */
    private String status;
}
