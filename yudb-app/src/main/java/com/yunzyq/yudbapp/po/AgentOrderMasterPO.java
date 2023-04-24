package com.yunzyq.yudbapp.po;

import com.yunzyq.yudbapp.dao.dto.MakeOutBillDTO;
import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderMaster;
import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentType;
import com.yunzyq.yudbapp.dao.model.MerchantAgentArea;
import com.yunzyq.yudbapp.dao.model.RegionalManager;
import com.yunzyq.yudbapp.enums.AuditStatusEnum;
import com.yunzyq.yudbapp.enums.IsSendEnum;
import com.yunzyq.yudbapp.enums.OrderMasterStatusEnum;
import com.yunzyq.yudbapp.enums.OrderMasterTypeEnum;
import com.yunzyq.yudbapp.util.DateUtil;
import com.yunzyq.yudbapp.util.PlatformCodeUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/25
 */
@Slf4j
@Data
public class AgentOrderMasterPO extends AgentAreaPaymentOrderMaster implements Serializable {
    public AgentOrderMasterPO(String managerId, MerchantAgentArea merchantAgentArea, MakeOutBillDTO makeOutBillDTO, RegionalManager regionalManager, AgentAreaPaymentType agentAreaPaymentType) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newTime1 = DateUtil.endTime("1年后", merchantAgentArea.getExpireTime());
        this.setUid(merchantAgentArea.getUid());
        this.setOrderNo(PlatformCodeUtil.orderNo("13GT"));
        this.setPaymentStandardMoney(new BigDecimal(makeOutBillDTO.getMoney()));
        this.setMerchantId(merchantAgentArea.getMerchantId());
        this.setMerchantName(merchantAgentArea.getMerchantName());
        this.setMerchantMobile(merchantAgentArea.getMobile());
        this.setRegionalManagerId(regionalManager.getId());
        this.setRegionalManagerName(regionalManager.getName());
        this.setRegionalManagerMobile(regionalManager.getMobile());
        this.setPaymentTypeId(agentAreaPaymentType.getId());
        this.setCycle(sdf.format(merchantAgentArea.getExpireTime()) + "~" + sdf.format(newTime1));
        this.setPaymentTypeName(agentAreaPaymentType.getName());
        this.setType(OrderMasterTypeEnum.REGIONAL_MANAGER_GENERATION.getType());
        this.setStatus(OrderMasterStatusEnum.TO_BE_PAID.getStatus());
        this.setExamine(AuditStatusEnum.PENDING_REVIEW.getStatus());
        this.setSend(IsSendEnum.NOT_PUSHED.getIsSend());
        this.setDeleted(Boolean.FALSE);
        this.setAdjustmentCount(0);
        this.setExamineNum(2);
        this.setMoney(new BigDecimal(makeOutBillDTO.getMoney()));
        try {
            this.setExpireTime(DateUtils.parseDate(makeOutBillDTO.getTime(), "yyyy-MM-dd"));
        } catch (ParseException e) {
            log.error("日期格式转换异常", e);
            this.setExpireTime(new Date());
        }
        this.setSend(2);
        this.setExamine(2);
        this.setRemark(makeOutBillDTO.getRemark());
        this.setCreateTime(new Date());
        this.setCreateUser(Integer.valueOf(managerId));
        this.setDeleted(false);
        this.setProvince(merchantAgentArea.getProvince());
        this.setCity(merchantAgentArea.getCity());
        this.setArea(merchantAgentArea.getArea());
    }

    public AgentOrderMasterPO(Integer id, IsSendEnum isSendEnum) {
        this.setId(id);
        this.setSend(isSendEnum.getIsSend());
        this.setSendTime(new Date());
    }
}
