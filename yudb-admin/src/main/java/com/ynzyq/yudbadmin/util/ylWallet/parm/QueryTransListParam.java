package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryTransListParam extends BaseParam {


    /**
     * 单页面大小。最大限制 50 (必须)
     */
    private String pageSize;
    /**
     * 页数。从 1 开始 (必须)
     */
    private String pageNo;
    /**
     * 电子账户 ID (必须)
     */
    private String walletId;

    /**
     * 开始日期交易日期 yyyyMMdd(必须)
     */
    private String startDate;

    /**
     * 结束日期交易日期 yyyyMMdd(必须)
     */
    private String endDate;

    /**
     * 起始清算日期 清算日期 yyyyMMdd
     */
    private String startSettDate;

    /**
     * 结束清算日期 清算日期 yyyyMMdd
     */
    private String endSettDate;

    /**
     * 交易类型
     */
    private String transType;

    /**
     * transTypes
     */
    private String transTypes;
    /**
     * 处理状态
     * 0：已接收
     * 1：成功
     * 2：失败
     * 3：已冲正
     * 4：已撤销
     * 5：处理中
     */
    private String procStatus;

    /**
     * 商户订单号
     */
    private String mctOrderNo;

    /**
     * 交易订单号
     */
    private String transOrderNo;


    /**
     * 资金计划项目 ID
     */
    private String cptlPlnPrjctId;


    /**
     * 是否输入密码
     * 0：不需要密码
     * 1：需要密码
     */
    private String isNeedPwd;

}
