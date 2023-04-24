package com.ynzyq.yudbadmin.util.ylWallet.parm;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class H5Parm {


    /**
     * 11、H5 企业注册开户
     * 3、 重置支付密码(C)
     * 2、 修改支付密码(B/C)
     */
    private String jumpType;


    /**
     * 外部用户 ID (必传，用于查询开户情况)
     */
    private String extUserId;
    /**
     * 点击页面“返回”按钮时回到商户系统的回调地址
     */
    private String callbackUrl;
    /**
     * 为企业用户提供审核结果推送（详见电子账户消息推送
     */
    @ApiModelProperty("为企业用户提供审核结果推送（详见电子账户消息推送")
    private String notifyUrl;

    /**
     * 电子账户 ID jumpType=3 2时必传
     */
    private String walletId;
}
