package com.ynzyq.yudbadmin.api.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WJ
 */

@Getter
@AllArgsConstructor
public enum StatusTwoEnum {
    SIGNED(1, "已签约"),
    IN_CAMP(2, "在营"),
    UNOPENED(3, "未开业"),
    SITE_SELECTION(4, "退单"),
    COMPOUND_RULER(5, "复尺"),
    DESIGN(6, "设计"),
    CONSTRUCTION(7, "施工"),
    COMPLETED(8, "竣工"),
    TERMINATE_THE_CONTRACT(9, "闭店解约"),
    RELOCATION(10, "迁址"),
    SUSPEND_BUSINESS(11, "暂停营业"),
    CHARGEBACK(12, "退单"),
    AUTHORIZATION_CODE_IS_ABNORMAL(13, "授权码异常");
    /**
     * 状态
     */
    private Integer status;

    /**
     * 文本说明
     */
    private String desc;

    public static Integer getStatusNum(String desc) {
        if (StringUtils.isBlank(desc)) {
            return null;
        }
        for (StatusTwoEnum statusTwoEnum : StatusTwoEnum.values()) {
            if (StringUtils.equals(desc.trim(), statusTwoEnum.getDesc())) {
                return statusTwoEnum.getStatus();
            }
        }
        return null;
    }

    public static String getStatusDesc(Integer status) {
        for (StatusTwoEnum statusTwoEnum : StatusTwoEnum.values()) {
            if (statusTwoEnum.getStatus().equals(status)) {
                return statusTwoEnum.getDesc();
            }
        }
        return null;
    }
}
