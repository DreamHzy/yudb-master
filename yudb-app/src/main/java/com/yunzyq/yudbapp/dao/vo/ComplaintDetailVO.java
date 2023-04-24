package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/1/25 15:25
 * @description:
 */
@Data
public class ComplaintDetailVO implements Serializable {
    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("相关凭证")
    private List<ComplaintFileVO> files;

    @ApiModelProperty("处理流程")
    private List<DealWithVO> records;
}
