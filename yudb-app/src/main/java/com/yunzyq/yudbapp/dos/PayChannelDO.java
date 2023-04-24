package com.yunzyq.yudbapp.dos;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author WJ
 */
@Data
public class PayChannelDO implements Serializable {
    /**
     * 通道id
     */
    private Integer id;

    /**
     * 通道标识
     */
    private String channelNo;

    /**
     * 通道秘钥
     */
    private String channelKey;

    /**
     * 通道名称
     */
    private String channelName;

    /**
     * 支付类型
     */
    private Integer payType;

    /**
     * 支付路由
     */
    private String channelRoute;

    /**
     * 请求地址
     */
    private String channelApiUrl;

    /**
     * 回调地址
     */
    private String channelNotifyUrl;

    private BigDecimal rate;

}
