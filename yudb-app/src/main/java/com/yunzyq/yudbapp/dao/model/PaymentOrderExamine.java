package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentOrderExamine {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer paymentOrderMasterId;

    private String merchantStoreUid;

    private Integer merchantStoreId;

    private Integer paymentTypeId;

    private String paymentTypeName;


    private Integer examineType;

    private Integer examine;

    private BigDecimal oldMoney;

    private BigDecimal newMoney;

    private Date oldTime;

    private Date newTime;

    private String msg;

    private Integer applyId;

    private String applyName;

    private Date createTime;

    private Date completeTime;

    private String remark;

    private Integer status;

    private String refuse;

    private Boolean deleted;

    private Integer step;
}