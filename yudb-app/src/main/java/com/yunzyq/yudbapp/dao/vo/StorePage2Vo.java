package com.yunzyq.yudbapp.dao.vo;

import com.yunzyq.yudbapp.core.PageData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StorePage2Vo {

    @ApiModelProperty("统计信息")
    private List<StorePage2Statistics> storePage2StatisticsList;

    @ApiModelProperty("门店信息")
    private PageData<StorePage2> listPageData;

}
