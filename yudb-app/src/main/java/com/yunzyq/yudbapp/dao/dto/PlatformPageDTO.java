package com.yunzyq.yudbapp.dao.dto;

import com.yunzyq.yudbapp.core.PageWrap;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author WJ
 */
@Data
public class PlatformPageDTO extends PageWrap implements Serializable {

    @NotNull(message = "查询类型不能为空")
    @Max(value = 3, message = "查询类型值只能0~3")
    @Min(value = 0, message = "查询类型值只能0~3")
    private Integer type;

    /**
     * 查询条件(授权号/加盟商/缴费类型)
     */
    private String conditions;

    public void setConditions(String conditions) {
        if (StringUtils.isNotBlank(conditions)) {
            this.conditions = conditions;
        }
    }

}
