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
public enum LevelCityEnum {

    NEW_FIRST_TIER("新一线"),
    FIRST_TIER("一线城市"),
    SECOND_TIER("二线城市"),
    THIRD_TIER("三线城市"),
    FOUR_TIER("四线城市"),
    FIVE_TIER("五线城市"),
    COUNTY("县城"),
    HKMT("港澳台地区"),
    ;

    /**
     * 大区名称
     */
    private String level;
}
