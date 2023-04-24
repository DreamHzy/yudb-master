package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class IndexMerchantVo {
    @ApiModelProperty("轮播图列表")
    private List<String> banners;

    @ApiModelProperty("我的通知")
    private List<IndexNoticeListVo> noticeListVo;

    @ApiModelProperty("我的代办数")
    private String count;

    @ApiModelProperty("代办区内容")
    private IndexOrderVo indexOrderVo;

}
