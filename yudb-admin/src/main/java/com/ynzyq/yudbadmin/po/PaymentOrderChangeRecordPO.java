package com.ynzyq.yudbadmin.po;

import com.ynzyq.yudbadmin.core.model.LoginUserInfo;
import com.ynzyq.yudbadmin.dao.business.dto.PaymentOrderDTO;
import com.ynzyq.yudbadmin.dao.business.model.PaymentOrderChangeRecord;
import com.ynzyq.yudbadmin.dao.business.model.RegionalManager;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xinchen
 * @date 2021/11/18 22:39
 * @description:
 */
@Data
public class PaymentOrderChangeRecordPO extends PaymentOrderChangeRecord implements Serializable {
    public PaymentOrderChangeRecordPO(String id, PaymentOrderDTO paymentOrderDTO, Integer oldManagerId, String oldManagerName, RegionalManager regionalManager, Integer type, LoginUserInfo loginUserInfo) {
        this.setMappingAreaId(Integer.parseInt(id));
        this.setOrderId(paymentOrderDTO.getId());
        this.setOrderStatus(paymentOrderDTO.getStatus());
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
