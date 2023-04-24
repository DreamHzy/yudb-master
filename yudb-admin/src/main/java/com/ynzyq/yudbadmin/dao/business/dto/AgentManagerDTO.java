package com.ynzyq.yudbadmin.dao.business.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/11/19 14:05
 * @description:
 */
@Data
public class AgentManagerDTO implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 所属代理id
     */
    private Integer agentId;
    /**
     * 所属代理
     */
    private String agentName;
    /**
     * 区域经理id
     */
    private Integer managerId;

    /**
     * 区域经理
     */
    private String managerName;

    /**
     * 是否映射
     */
    private String isMapping;

    /**
     * 所属代理权id
     */
    private Integer agentAreaId;
}
