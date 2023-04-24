package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/3/13 20:33
 * @description:
 */
@Data
public class FutureBacklogDetailVO implements Serializable {
    @ApiModelProperty("收款月份")
    private String collectionMonth;

    @ApiModelProperty("待完成数量")
    private String count;

    @ApiModelProperty("待办列表")
    private List<RegionalManagerPlatformPageVo> regionalManagerPlatformPageVos;
}
