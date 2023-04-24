package com.yunzyq.yudbapp.dao.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AgentOrderPayDTO implements Serializable {
    /**
     * 缴费单id
     */
    @NotBlank(message = "缴费单id不能为空")
    private String id;

    /**
     * openId
     */
    private String openId;
}
