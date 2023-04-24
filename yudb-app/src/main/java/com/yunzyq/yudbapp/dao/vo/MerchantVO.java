package com.yunzyq.yudbapp.dao.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/3 17:07
 * @description:
 */
@Data
public class MerchantVO implements Serializable {
    /**
     * 门店数量
     */
    private Integer storeCount;

    /**
     * 代理权数量
     */
    private Integer agentCount;
}
