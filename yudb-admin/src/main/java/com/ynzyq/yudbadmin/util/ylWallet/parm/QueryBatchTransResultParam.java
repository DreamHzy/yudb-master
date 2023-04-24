package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class QueryBatchTransResultParam extends BaseParam{


    //原商户订单号
    private String oriMctOrderNo;

    //批量转账3
    private String operType;

    //如果只查批次中某条明细，请输入明细流水号
    private String sn;
}
