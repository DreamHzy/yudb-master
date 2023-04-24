package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.util.Date;

@Data
public class MerchantAgentAreaRegionalManager {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer regionalManagerId;

    private Integer merchantAgentAreaId;

    private Integer status;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

}