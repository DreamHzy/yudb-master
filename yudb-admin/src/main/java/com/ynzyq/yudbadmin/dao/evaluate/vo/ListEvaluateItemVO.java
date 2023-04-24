package com.ynzyq.yudbadmin.dao.evaluate.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/12/1 11:56
 * @description:
 */
@Data
public class ListEvaluateItemVO implements Serializable {
    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("内容")
    private String item;

    @ApiModelProperty("是否必填：1：是，2：否")
    private String isRequired;

    @ApiModelProperty("级别：1：问题，2：选项")
    private String level;

    @ApiModelProperty("评估选项")
    List<ListEvaluateItemVO> sonListEvaluateItemVO;

}
