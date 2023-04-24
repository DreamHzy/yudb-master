package com.ynzyq.yudbadmin.api.excel.po;

import com.ynzyq.yudbadmin.api.excel.dto.RegionalManagerDTO;
import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Data
public class RegionalManagerPO extends RegionalManager implements Serializable {
    public RegionalManagerPO(RegionalManagerDTO regionalManagerDTO) {
        this.setUid(regionalManagerDTO.getCode());
        this.setName(regionalManagerDTO.getName());
        this.setMobile(regionalManagerDTO.getMobile());
        this.setPassword("tc6BZwktKlwSWeQCzh+TqQ==");
        this.setStatus(1);
        this.setCreateUser(1);
        this.setCreateTime(new Date());
    }
}
