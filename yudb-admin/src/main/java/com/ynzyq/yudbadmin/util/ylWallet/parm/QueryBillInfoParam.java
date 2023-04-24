package com.ynzyq.yudbadmin.util.ylWallet.parm;


import lombok.Data;

@Data
public class QueryBillInfoParam extends BaseParam {


    /**
     * 订单号 非必须
     */
    private String mctOrderNo;


    /**
     * 电子钱包必须
     */
    private String walletId;

    /**
     *开始日期YYYY-MM-DD。查询区间最长为 3 个月。  必须
     */
    private String startDate;


    /**
     * 结束日期 YYYY-MM-DD。查询区间最长为 3 个月。  必须
     */
    private String endDate;


    /**
     * 收支类型
     * C：收入
     * D：支出
     */
    private String loanMark;


    /**
     * 是否分页  必须
     * 0：分页
     * 1：不分页（返回所有查询结果
     */
    private String pageType;


    /**
     * 页数分页时必填
     */
    private String pageNumber;

    /**
     * 每页显示数分页时必填
     */
    private String pageSize;


}
