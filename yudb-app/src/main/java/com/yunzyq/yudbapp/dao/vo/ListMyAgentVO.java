package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/25
 */
@Data
public class ListMyAgentVO implements Serializable {

    /**
     * 加盟商代理区域id
     */
    @ApiModelProperty("加盟商代理区域id")
    private String id;

    /**
     * 授权号
     */
    @ApiModelProperty("授权号")
    private String uid;

    /**
     * 加盟商
     */
    @ApiModelProperty("加盟商")
    private String merchantName;

    /**
     * 省
     */
    @ApiModelProperty("省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty("市")
    private String city;

    /**
     * 区
     */
    @ApiModelProperty("区")
    private String area;

    /**
     * 合同到期时间
     */
    @ApiModelProperty("合同到期时间")
    private String expireTime;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String MOBILE;
}
