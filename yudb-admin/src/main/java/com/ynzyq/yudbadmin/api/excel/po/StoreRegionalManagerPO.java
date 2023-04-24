package com.ynzyq.yudbadmin.api.excel.po;

import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreRegionalManager;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class StoreRegionalManagerPO extends MerchantStoreRegionalManager implements Serializable {

    public StoreRegionalManagerPO(Integer storeId, Integer managerId) {
        this.setRegionalManagerId(managerId);
        this.setMerchantStoreId(storeId);
        this.setStatus(StatusEnum.ENABLE.getStatus());
        this.setCreateUser(1);
        this.setCreateTime(new Date());
    }
}
