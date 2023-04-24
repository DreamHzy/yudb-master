package com.yunzyq.yudbapp.dao.dto;

import lombok.Data;

/**
 * @author mzb
 */
@Data
public class PayDto {

    private String url;
    private String shopNo;
    private String orderNo;
    private Integer money;
    private String authCode;
    private String ip;
    private String callbackUrl;
    private String orderName;
    private String sign;
    private String openId;

}
