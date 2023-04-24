package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantStoreExamine {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer merchantStoreId;

    private Integer examineType;

    private Integer examine;

    private Date oldTime;

    private Date newTime;


    private BigDecimal oldMoney;

    private BigDecimal newMoney;


    private Integer status;


    private Integer createUser;

    private String createName;

    private Date createTime;

    private Date updateTime;

    private String merchantStoreUid;

    private BigDecimal managementExpense;

    private String refuse;

    private String remark;

    private Date completeTime;

    private Boolean deleted;


}