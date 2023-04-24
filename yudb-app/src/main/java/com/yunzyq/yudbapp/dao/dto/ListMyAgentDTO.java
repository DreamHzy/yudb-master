package com.yunzyq.yudbapp.dao.dto;

import com.yunzyq.yudbapp.core.PageWrap;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author WJ
 */
@Data
public class ListMyAgentDTO extends PageWrap implements Serializable {

    /**
     * 查询条件(加盟商/授权号/电话)
     */
    private String conditions;

    public void setConditions(String conditions) {
        if (StringUtils.isNotBlank(conditions)) {
            this.conditions = conditions;
        }
    }

}
