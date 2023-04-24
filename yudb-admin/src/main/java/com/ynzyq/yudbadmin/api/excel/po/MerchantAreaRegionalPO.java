package com.ynzyq.yudbadmin.api.excel.po;

import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentAreaRegionalManager;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MerchantAreaRegionalPO extends MerchantAgentAreaRegionalManager implements Serializable {
    public MerchantAreaRegionalPO(Integer areaId, Integer regionalId) {
        this.setMerchantAgentAreaId(areaId);
        this.setRegionalManagerId(regionalId);
        this.setStatus(1);
        this.setCreateUser(1);
        this.setCreateTime(new Date());
    }
}
