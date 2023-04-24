package com.yunzyq.yudbapp.dao.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/3/13 17:47
 * @description:
 */
@Data
public class FutureBacklogVO implements Serializable {
    @ApiModelProperty("收款月份")
    private String collectionMonth;

    @ApiModelProperty("待完成数量")
    private String count;

}
