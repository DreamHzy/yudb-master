package com.ynzyq.yudbadmin.dao.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/8 17:17
 * @description:
 */
@Data
public class AgencyDTO implements Serializable {

    /**
     * 代理权数
     */
    private Integer agentAreaCount;

    /**
     * 有效
     */
    private Integer agentValid;

    /**
     * 无效
     */
    private Integer agentInvalid;
}
