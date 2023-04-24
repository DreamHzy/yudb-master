package com.ynzyq.yudbadmin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author xinchen
 * @date 2022/4/12 10:34
 * @description:
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(1, "超级管理员"),
    UID_APPLY(47, "授权号申请模块总权限"),
    ;

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 文本说明
     */
    private String desc;

    public static Boolean isShowAll(List<Integer> roleIds) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleIds.contains(roleEnum.getId())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
