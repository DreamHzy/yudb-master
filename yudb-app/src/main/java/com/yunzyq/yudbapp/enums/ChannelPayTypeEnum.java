package com.yunzyq.yudbapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WJ
 */
@Getter
@AllArgsConstructor
public enum ChannelPayTypeEnum {
    WECHAT(1, "微信"),
    ALIPAY(2, "支付宝"),
    AGGREGATE_PAYMENT(3, "聚合支付"),
    WECHAT_MINI_PROGRAM(4, "微信小程序");
    /**
     * 支付类型
     */
    private Integer type;

    /**
     * 文本说明
     */
    private String desc;
}
