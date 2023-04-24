package com.yunzyq.yudbapp.dao.dto;

import com.yunzyq.yudbapp.core.PageWrap;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/27
 */
@Data
public class AgentMyBacklogDTO extends PageWrap implements Serializable {
    @ApiModelProperty("查询类型：1：逾期待办，2：本月待办，3：历史待办")
    @Min(value = 1, message = "查询类型错误")
    @Max(value = 3, message = "查询类型错误")
    @NotNull(message = "查询类型不能为空")
    private Integer type;
}
