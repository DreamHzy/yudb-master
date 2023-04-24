package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class PageQueryHzfOriTransParam extends BaseParam {

    private String pageSize;

    private String pageNo;

    private String platformWalletId;

    private String startSettDate;

    private String endSettDate;

    private String refNo;
}
