package com.ynzyq.yudbadmin.po;

import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreRegionalManager;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xinchen
 * @date 2021/11/18 17:36
 * @description:
 */
public class MerchantStoreRegionalManagerPO extends MerchantStoreRegionalManager implements Serializable {
    public MerchantStoreRegionalManagerPO(String managerId, Integer storeId, Integer userId) {
        this.setRegionalManagerId(Integer.parseInt(managerId));
        this.setMerchantStoreId(storeId);
        this.setStatus(1);
        this.setCreateUser(userId);
        this.setCreateTime(new Date());
    }
}
