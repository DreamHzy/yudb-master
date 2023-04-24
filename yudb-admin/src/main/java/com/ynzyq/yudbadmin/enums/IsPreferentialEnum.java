package com.ynzyq.yudbadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xinchen
 * @date 2021/12/6 10:31
 * @description:
 */
@Getter
@AllArgsConstructor
public enum IsPreferentialEnum {
    YES(1, "是"),
    NO(2, "否"),
    ;
    /**
     * 是否适用代理优惠
     */
    private Integer isPreferential;

    /**
     * 文本说明
     */
    private String desc;
}
