package com.yunzyq.yudbapp.dao.dto;

import com.yunzyq.yudbapp.dao.model.AgentAreaPaymentOrderMaster;
import com.yunzyq.yudbapp.dos.PayChannelDO;
import lombok.Data;

import java.io.Serializable;

@Data
public class AgentOrderDataDTO implements Serializable {
    /**
     * 缴费主订单对象
     */
    private AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster;

    /**
     * 通道对象
     */
    private PayChannelDO payChannelDO;
}
