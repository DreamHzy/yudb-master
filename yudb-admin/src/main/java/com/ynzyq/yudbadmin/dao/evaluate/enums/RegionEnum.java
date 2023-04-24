package com.ynzyq.yudbadmin.dao.evaluate.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xinchen
 * @date 2021/12/1 18:17
 * @description:
 */
@Getter
@AllArgsConstructor
public enum RegionEnum {

    EAST_CHINA("1","华东"),
    NORTH_CHINA("2","华北"),
    CENTRAL_CHINA("3","华中"),
    WEST_CHINA("4","华西"),
    SOUTH_CHINA("5","华南"),
    ;
    /**
     * 大区代表值
     */
    private String number;

    /**
     * 大区名称
     */
    private String desc;

    public static String getRegionDesc(String number) {
        for (RegionEnum regionEnum : RegionEnum.values()) {
            if (regionEnum.getNumber().equals(number)) {
                return regionEnum.getDesc();
            }
        }
        return "";
    }
}
