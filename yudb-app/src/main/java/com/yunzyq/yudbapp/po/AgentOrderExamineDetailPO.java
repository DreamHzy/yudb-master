package com.yunzyq.yudbapp.po;

import com.yunzyq.yudbapp.dao.dto.ApplyForAdjustmentDto;
import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderExamineDeail;
import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderMaster;
import com.yunzyq.yudbapp.enums.AuditFlowEnum;
import com.yunzyq.yudbapp.enums.AuditStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AgentOrderExamineDetailPO extends AgentAreaPaymentOrderExamineDeail implements Serializable {

    public AgentOrderExamineDetailPO(Integer examineId) {
        this.setPaymentOrderExamineId(examineId);
        this.setExamine(AuditFlowEnum.FIRST_INSTANCE.getFlow());
        this.setStatus(AuditStatusEnum.PENDING_REVIEW.getStatus());
        this.setCreateTime(new Date());
        this.setStep(1);
        this.setType(1);
        this.setStatus(1);
    }
}
