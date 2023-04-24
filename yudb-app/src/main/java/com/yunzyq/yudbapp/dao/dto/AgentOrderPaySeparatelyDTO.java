package com.yunzyq.yudbapp.dao.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AgentOrderPaySeparatelyDTO implements Serializable {
    /**
     * 缴费单id
     */
    @NotBlank(message = "缴费单id不能为空")
    private String id;

    /**
     * 缴费金额
     */
    private String money;

    /**
     * openId
     */
    private String openId;
}
