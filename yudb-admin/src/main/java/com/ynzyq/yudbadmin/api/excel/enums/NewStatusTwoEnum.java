package com.ynzyq.yudbadmin.api.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WJ
 */

@Getter
@AllArgsConstructor
public enum NewStatusTwoEnum {
    CLOSED_SHOP(1, "闭店"),
    FROZEN_CUSTOMERS(2, "冰冻客户"),
    UNDER_EVALUATION(3, "测评中"),
    DEPOSIT_TO_BE_PAID_BACK(4, "定金-待回款"),
    HAS_BEEN_SIGNED_DEAD_ORDER(5, "定金合同已签-死单"),
    NOT_YET_DOCKED(6, "定金合同-暂不对接"),
    DEPOSIT_CHARGEBACK(7, "定金-退单"),
    NOT_DOCKED(8, "合同未回 未对接"),
    TO_BE_DETERMINED(9, "加盟合同已签-待定"),
    HAS_BEEN_SIGNED_CHARGEBACK(10, "加盟合同已签-退单"),
    COMPLETED(11, "竣工"),
    RELOCATION(12, "迁址"),
    AREA_PROTECTION(13, "区域保护"),
    THE_FULL_PAYMENT_HAS_BEEN_SIGNED(14, "全款已签约"),
    DESIGN_COMPLETED(15, "设计完成"),
    DESIGNING(16, "设计中"),
    UNDER_CONSTRUCTION(17, "施工中"),
    DUPLICATE_AUTHORIZATION_CODE(18, "授权码重复"),
    DUPLICATE_AUTHORIZATION_CODE_1088(19, "授权码重复1088"),
    DUPLICATE_AUTHORIZATION_CODE_2112(20, "授权码重复2112"),
    DUPLICATE_AUTHORIZATION_CODE_839(21, "授权码重复839"),
    UNOPENED(22, "未开业"),
    NOT_SEATED(23, "未落位"),
    SITE_SELECTION(24, "选址中"),
    CONSTRUCTION_PARTY_HAS_BEEN_DISPATCHED(25, "已派施工方"),
    SIGNED_CONTRACT_SELF_SELECTED_LOCATION(26, "已签约、自行选址"),
    IN_CAMP(27, "在营"),
    DO_NOT_CHOOSE_LOCATION(28, "暂不选址"),
    SUSPEND_BUSINESS(29, "暂停营业"),
    LONG_LINE(30, "长线"),
    DATA_COLLECTION(31, "资料收集中"),
    CHOOSE_YOUR_OWN_LOCATION(32, "自行选址"),
    THE_LEASE_CONTRACT_WAS_NOT_SIGNED(33, "租赁合同未签"),
    ;
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
        for (NewStatusTwoEnum statusTwoEnum : NewStatusTwoEnum.values()) {
            if (StringUtils.equals(desc, statusTwoEnum.getDesc())) {
                return statusTwoEnum.getStatus();
            }
        }
        return 0;
    }

    public static String getStatusDesc(Integer status) {
        for (NewStatusTwoEnum newStatusTwoEnum : NewStatusTwoEnum.values()) {
            if (newStatusTwoEnum.getStatus().equals(status)) {
                return newStatusTwoEnum.getDesc();
            }
        }
        return null;
    }
}
