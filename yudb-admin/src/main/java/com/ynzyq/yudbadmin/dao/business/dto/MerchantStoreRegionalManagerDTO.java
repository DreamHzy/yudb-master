package com.ynzyq.yudbadmin.dao.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/18 17:28
 * @description:
 */
@Data
public class MerchantStoreRegionalManagerDTO implements Serializable {
    /**
     * 关联表id
     */
    private Integer storeId;

//    /**
//     * 关联表id
//     */
//    private Integer id;

    /**
     * 区域经理id
     */
    private Integer managerId;

    /**
     * 区域经理名称
     */
    private String name;
}
