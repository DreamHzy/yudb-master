package com.yunzyq.yudbapp.po;

import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderMaster;
import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderPay;
import com.yunzyq.yudbapp.dao.vo.PayChennleVo;
import com.yunzyq.yudbapp.dos.PayChannelDO;
import com.yunzyq.yudbapp.enums.OrderPayStatusEnum;
import com.yunzyq.yudbapp.util.PlatformCodeUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WJ
 */
@Data
public class AgentOrderPayPO extends AgentAreaPaymentOrderPay implements Serializable {
    public AgentOrderPayPO(PayChannelDO payChannelDO, AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster) {
        this.setPaymentOrderMasterId(agentAreaPaymentOrderMaster.getId());
        this.setOrderNo(PlatformCodeUtil.orderNo("13GT"));
        this.setPayChannelId(payChannelDO.getId());
        this.setPayChannelName(payChannelDO.getChannelName());
        this.setTotalMoney(agentAreaPaymentOrderMaster.getMoney());
        this.setPayType(payChannelDO.getPayType());
        this.setPayChannelRoute(payChannelDO.getChannelRoute());
        this.setStatus(OrderPayStatusEnum.ORDER_NOW.getStatus());
        this.setChannelApiUrl(payChannelDO.getChannelApiUrl());
        this.setChannelNotifyUrl(payChannelDO.getChannelNotifyUrl());
        this.setRemark("授权号:" + agentAreaPaymentOrderMaster.getUid() + "," + agentAreaPaymentOrderMaster.getPaymentTypeName());
        this.setCreateTime(new Date());
    }

    public AgentOrderPayPO(Long orderPayId, PayChennleVo payChennleVo) {
        this.setId(orderPayId);
        this.setStatus(payChennleVo.getStatus());
        this.setMsg(payChennleVo.getMsg());
    }
}
