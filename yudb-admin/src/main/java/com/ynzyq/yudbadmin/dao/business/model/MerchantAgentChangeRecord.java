package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class MerchantAgentChangeRecord {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer merchantAgentAreaId;

    private Integer oldManagerId;

    private String oldManagerName;

    private Integer newManagerId;

    private String newManagerName;

    private Integer userId;

    private String userName;

    private Date createTime;

}