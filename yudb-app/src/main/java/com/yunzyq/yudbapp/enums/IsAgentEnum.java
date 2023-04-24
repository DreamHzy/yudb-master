package com.yunzyq.yudbapp.enums;

import lombok.Getter;

/**
 * @author WJ
 */
@Getter
public enum IsAgentEnum {
    NO("0", "否"),
    YES("1", "是");
    /**
     * 是否为代理：0：否，1：是
     */
    private String isAgent;

    /**
     * 文本说明
     */
    private String desc;

    IsAgentEnum(String isAgent, String desc) {
        this.isAgent = isAgent;
        this.desc = desc;
    }
}
