package com.ynzyq.yudbadmin.api.excel.po;

import com.ynzyq.yudbadmin.api.excel.dto.AgentDTO;
import com.ynzyq.yudbadmin.api.excel.dto.MerchantAreaDTO;
import com.ynzyq.yudbadmin.api.excel.dto.MerchantDTO;
import com.ynzyq.yudbadmin.dao.business.model.Merchant;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class MerchantPO extends Merchant implements Serializable {
    public MerchantPO(AgentDTO agentDTO) {
//        this.setUid(agentDTO.getCode());
        this.setName(agentDTO.getName());
        this.setMobile(StringUtils.equals("#N/A", agentDTO.getMobile()) ? null : agentDTO.getMobile());
//        this.setProvince(agentDTO.getProvince());
//        this.setCity(agentDTO.getCity());
//        this.setArea(agentDTO.getArea());
        this.setPassword("tc6BZwktKlwSWeQCzh+TqQ==");
        this.setStatus(StatusEnum.ENABLE.getStatus());
        this.setIsAgent(1);
        this.setCreateUser(1);
        this.setCreateTime(new Date());
    }

    public MerchantPO(AgentDTO agentDTO, Merchant merchant) {
        this.setId(merchant.getId());
        this.setMobile(agentDTO.getMobile());
        this.setIsAgent(1);
    }

    public MerchantPO(MerchantDTO merchantDTO) {
        this.setName(merchantDTO.getName());
        this.setMobile(merchantDTO.getMobile());
        this.setPassword("tc6BZwktKlwSWeQCzh+TqQ==");
        this.setStatus(StatusEnum.ENABLE.getStatus());
        this.setIsAgent(2);
        this.setCreateUser(1);
        this.setCreateTime(new Date());
    }

    public MerchantPO(MerchantAreaDTO merchantAreaDTO) {
        this.setName(merchantAreaDTO.getAgentName());
//        this.setMobile(merchantDTO.getMobile());
        this.setPassword("tc6BZwktKlwSWeQCzh+TqQ==");
        this.setStatus(StatusEnum.ENABLE.getStatus());
        this.setIsAgent(1);
        this.setCreateUser(1);
        this.setCreateTime(new Date());
    }
}
