package com.ynzyq.yudbadmin.po;

import com.ynzyq.yudbadmin.core.model.LoginUserInfo;
import com.ynzyq.yudbadmin.dao.business.model.Merchant;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreChangeRecord;
import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xinchen
 * @date 2021/11/18 16:08
 * @description:
 */
public class MerchantStoreChangeRecordPO extends MerchantStoreChangeRecord implements Serializable {

    public MerchantStoreChangeRecordPO(String id, Integer storeId, Integer oldAgentId, String oldAgentName, Merchant merchant, Integer type, LoginUserInfo loginUserInfo) {
        this.setMappingAreaId(Integer.parseInt(id));
        this.setStoreId(storeId);
        this.setOldAgentId(oldAgentId);
        this.setOldAgentName(oldAgentName);
        this.setNewAgentId(merchant.getId());
        this.setNewAgentName(merchant.getName());
        this.setType(type);
        this.setUserId(loginUserInfo.getId());
        this.setUserName(loginUserInfo.getRealname());
        this.setCreateTime(new Date());
    }

    public MerchantStoreChangeRecordPO(String id, Integer storeId, Integer oldManagerId, String oldManagerName, RegionalManager regionalManager, Integer type, LoginUserInfo loginUserInfo) {
        this.setMappingAreaId(Integer.parseInt(id));
        this.setStoreId(storeId);
        this.setOldManagerId(oldManagerId);
        this.setOldManagerName(oldManagerName);
        this.setNewManagerId(regionalManager.getId());
        this.setNewManagerName(regionalManager.getName());
        this.setType(type);
        this.setUserId(loginUserInfo.getId());
        this.setUserName(loginUserInfo.getRealname());
        this.setCreateTime(new Date());
    }
}
