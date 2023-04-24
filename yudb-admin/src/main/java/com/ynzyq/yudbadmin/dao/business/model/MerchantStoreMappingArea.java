package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class MerchantStoreMappingArea {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String uid;

    private String province;

    private String city;

    private String area;

    private String region;

    private String agentUid;

    private Integer agentId;

    private String agentName;

    private Integer managerId;

    private String managerName;

    private Integer status;

    private Date createTime;

    private String level;

    private Integer isMapping;

    private Integer warehouseId;

    private Integer limitConfigId;

    private Integer ruleId;
}