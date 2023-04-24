package com.ynzyq.yudbadmin.po;

import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentAreaRegionalManager;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class AgentAreaRegionalPO extends MerchantAgentAreaRegionalManager implements Serializable {

    public AgentAreaRegionalPO(Integer id, Integer regionalId, Integer userId) {
        this.setId(id);
        this.setRegionalManagerId(regionalId);
        this.setUpdateUser(userId);
        this.setUpdateTime(new Date());
    }

    public AgentAreaRegionalPO(Integer agentAreaId) {
        this.setMerchantAgentAreaId(agentAreaId);
    }
}
