package com.ynzyq.yudbadmin.dao.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/8 17:17
 * @description:
 */
@Data
public class LevelStoreCountDTO implements Serializable {
    /**
     * 门店数
     */
    private Integer count;

    /**
     * 线级城市
     */
    private String level;
}
