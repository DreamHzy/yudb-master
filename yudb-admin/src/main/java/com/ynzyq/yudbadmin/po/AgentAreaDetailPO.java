package com.ynzyq.yudbadmin.po;

import com.ynzyq.yudbadmin.dao.business.dto.AgentAreaDTO;
import com.ynzyq.yudbadmin.dao.business.dto.EditManagementExpenseDTO;
import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentArea;
import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentAreaDetail;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class AgentAreaDetailPO extends MerchantAgentAreaDetail implements Serializable {

    public AgentAreaDetailPO(Integer areaId, AgentAreaDTO agentAreaDTO) {
        this.setUid(agentAreaDTO.getUid());
        this.setAgentAreaId(areaId);
        this.setProvince(agentAreaDTO.getProvince());
        this.setCity(agentAreaDTO.getCity());
        this.setArea(agentAreaDTO.getArea());
        this.setStatus(StatusEnum.ENABLE.getStatus());
        this.setCreateTime(new Date());
    }
}
