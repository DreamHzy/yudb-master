package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

import java.util.List;

@Data
public class AddProfitSharingMerRelParam extends BaseParam {


    /**
     * 平台电子账户 ID
     */
    private String merWalletId;


    /**
     * 平台电子账户名称
     */
    private String merName;


    /**
     * 业务类型030002
     */
    private String bizType;


    /**
     * 循环域
     */
    private List<AddProfitSharingMerRelListParam> list;

}
