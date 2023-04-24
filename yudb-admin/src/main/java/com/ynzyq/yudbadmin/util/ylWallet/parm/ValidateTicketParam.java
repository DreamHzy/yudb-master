package com.ynzyq.yudbadmin.util.ylWallet.parm;

import lombok.Data;

@Data
public class ValidateTicketParam {
    /**
     * 好易联移动客户端跳转免登录凭据：只能使用一次
     */
    private String ticket;
}
