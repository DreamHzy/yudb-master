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
public enum StoreRegionEnum {

    EAST_CHINA("华东"),
    NORTH_CHINA("华北"),
    CENTRAL_CHINA("华中"),
    SOUTH_CHINA("华南"),
    WEST_CHINA("华西");

    /**
     * 大区名称
     */
    private String region;
}
