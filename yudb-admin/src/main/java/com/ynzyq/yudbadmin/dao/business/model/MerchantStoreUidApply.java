package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantStoreUidApply {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String uid;

    private String signatory;

    private String mobile;

    private String idCard;

    private Date signTime;

    private Date expireTime;

    private BigDecimal manageMoney;

    private BigDecimal bondMoney;

    private BigDecimal franchiseMoney;

    private Integer isAgent;

    private Integer isCooperate;

    private Date createTime;

    private Integer createUser;
}