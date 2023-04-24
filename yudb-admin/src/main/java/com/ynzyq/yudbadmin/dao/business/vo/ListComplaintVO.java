package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/1/25 11:59
 * @description:
 */
@Data
public class ListComplaintVO implements Serializable {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("投诉时间")
    private String createTime;

    @ApiModelProperty("加盟商")
    private String merchantName;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("区域")
    private String region;

    @ApiModelProperty("授权号")
    private String storeUid;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("状态：1：待处理，2：已处理")
    private String status;

    @ApiModelProperty("投诉内容")
    private String content;

    @ApiModelProperty("处理内容")
    private String result;

    @ApiModelProperty("相关凭证")
    private List<ComplaintFileVO> files;

}
