package com.ynzyq.yudbadmin.api.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xinchen
 * @date 2021/11/22 20:34
 * @description:
 */
@Getter
@AllArgsConstructor
public enum ContractStatusEnum {
    IN_PROGRESS(1, "进行中"),
    RENEWING(2, "续约中"),
    TO_BE_SIGNED(3, "待签署"),
    SIGNING_A_CONTRACT(4, "签署合同"),
    CANCELING_THE_CONTRACT(5, "解约中"),
    TERMINATE_THE_CONTRACT(6, "解约"),
    CONTRACT_RENEWAL(7, "续约中(解约状态下)"),
    AGREEMENT_EXPIRED(8, "协议失效"),
    AGREEMENT_OVERDUE(9, "协议逾期"),
    ;
    private Integer status;
    private String desc;

    public static Integer getStatusNum(String desc) {
        if (StringUtils.isBlank(desc)) {
            return null;
        }
        for (ContractStatusEnum contractStatusEnum : ContractStatusEnum.values()) {
            if (StringUtils.equals(desc, contractStatusEnum.getDesc())) {
                return contractStatusEnum.getStatus();
            }
        }
        return 0;
    }

    public static String getStatusDesc(Integer status) {
        for (ContractStatusEnum contractStatusEnum : ContractStatusEnum.values()) {
            if (contractStatusEnum.getStatus().equals(status)) {
                return contractStatusEnum.getDesc();
            }
        }
        return "";
    }
}
