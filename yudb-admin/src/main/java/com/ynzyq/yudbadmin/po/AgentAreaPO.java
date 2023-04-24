package com.ynzyq.yudbadmin.po;

import com.ynzyq.yudbadmin.dao.business.dto.EditManagementExpenseDTO;
import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentArea;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class AgentAreaPO extends MerchantAgentArea implements Serializable {

    public AgentAreaPO(EditManagementExpenseDTO editManagementExpenseDTO, Integer userId) {
        this.setId(editManagementExpenseDTO.getId());
        this.setManagementExpense(new BigDecimal(editManagementExpenseDTO.getMoney()));
        this.setUpdateUser(userId);
        this.setUpdateTime(new Date());
    }
}
