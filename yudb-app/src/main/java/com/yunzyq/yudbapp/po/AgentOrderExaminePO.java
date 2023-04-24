package com.yunzyq.yudbapp.po;

import com.yunzyq.yudbapp.dao.dto.ApplyForAdjustmentDto;
import com.yunzyq.yudbapp.dao.model.*;
import com.yunzyq.yudbapp.enums.AdjustmentTypeEnum;
import com.yunzyq.yudbapp.enums.AuditFlowEnum;
import com.yunzyq.yudbapp.enums.AuditStatusEnum;
import com.yunzyq.yudbapp.enums.IsDeleteEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author WJ
 */
@Data
public class AgentOrderExaminePO extends AgentAreaPaymentOrderExamine implements Serializable {

    public AgentOrderExaminePO(AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster, ApplyForAdjustmentDto applyForAdjustmentDto, Integer id, String name) {
        this.setPaymentOrderMasterId(agentAreaPaymentOrderMaster.getId());
        this.setUid(agentAreaPaymentOrderMaster.getUid());
        this.setPaymentTypeId(Integer.valueOf(applyForAdjustmentDto.getPayTypeId()));
        this.setPaymentTypeName(agentAreaPaymentOrderMaster.getPaymentTypeName());
        this.setExamine(AuditFlowEnum.FIRST_INSTANCE.getFlow());
        this.setStatus(AuditStatusEnum.PENDING_REVIEW.getStatus());
        this.setExamineType(Integer.valueOf(applyForAdjustmentDto.getPayTypeId()));
        if (AdjustmentTypeEnum.AMOUNT_ADJUSTMENT.getType().equals(applyForAdjustmentDto.getPayTypeId())) {
            this.setOldMoney(agentAreaPaymentOrderMaster.getMoney());
            this.setNewMoney(new BigDecimal(applyForAdjustmentDto.getMoney()));
            this.setMsg("支付金额" + agentAreaPaymentOrderMaster.getMoney() + "调整到" + applyForAdjustmentDto.getMoney());
        }
        if (AdjustmentTypeEnum.DEFERRED_PAYMENT.getType().equals(applyForAdjustmentDto.getPayTypeId())) {
            this.setNewMoney(agentAreaPaymentOrderMaster.getMoney());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.setOldTime(agentAreaPaymentOrderMaster.getExpireTime());
            try {
                this.setNewTime(sdf.parse(applyForAdjustmentDto.getTime()));
                this.setMsg("支付时间" + sdf.format(agentAreaPaymentOrderMaster.getExpireTime()) + "延期到" + applyForAdjustmentDto.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (AdjustmentTypeEnum.CANCEL_PAYMENT.getType().equals(applyForAdjustmentDto.getPayTypeId())) {
            this.setNewMoney(agentAreaPaymentOrderMaster.getMoney());
            this.setMsg("缴费取消");
        }
        if (AdjustmentTypeEnum.OFFLINE_PAYMENTS.getType().equals(applyForAdjustmentDto.getPayTypeId())) {
            this.setMsg("线下缴费");
        }
        this.setCreateTime(new Date());
        this.setApplyId(id);
        this.setApplyName(name);
        this.setRemark(applyForAdjustmentDto.getMsg());
        this.setStep(1);
    }

    public AgentOrderExaminePO(String uid, AgentAreaPaymentType agentAreaPaymentType, AgentOrderMasterPO agentOrderMasterPO, RegionalManager regionalManager) {
        this.setUid(uid);
        this.setPaymentTypeId(agentAreaPaymentType.getId());
        this.setPaymentOrderMasterId(agentOrderMasterPO.getId());
        this.setPaymentTypeName(agentAreaPaymentType.getName());
        this.setNewMoney(agentOrderMasterPO.getMoney());
        this.setExamineType(3);
        this.setExamine(1);
        this.setStatus(1);
        this.setApplyName(regionalManager.getName());
        this.setMsg("新订单审核:" + agentAreaPaymentType.getName() + agentOrderMasterPO.getMoney() + "元");
        this.setRemark(agentOrderMasterPO.getRemark());
        this.setDeleted(IsDeleteEnum.NOT_DELETE.getIsDelete());
        this.setApplyId(regionalManager.getId());
        this.setCreateTime(new Date());
        this.setStep(1);
    }
}
