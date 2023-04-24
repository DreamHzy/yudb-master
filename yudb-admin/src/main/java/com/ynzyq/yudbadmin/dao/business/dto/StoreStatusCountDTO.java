package com.ynzyq.yudbadmin.dao.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/8 17:17
 * @description:
 */
@Data
public class StoreStatusCountDTO implements Serializable {
    /**
     * 在营
     */
    private Integer businessCount;

    /**
     * 未开业
     */
    private Integer notOpenCount;

    /**
     * 迁址
     */
    private Integer relocationCount;

    /**
     * 闭店解约
     */
    private Integer closeCount;

    /**
     * 暂停营业
     */
    private Integer pauseCount;
}
