package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class AgentAreaPaymentOrderExamine {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Integer paymentOrderMasterId;

    private Integer paymentTypeId;

    private String paymentTypeName;

    private Integer examineType;

    private Integer examine;

    private Integer status;

    private BigDecimal oldMoney;

    private BigDecimal newMoney;

    private Date oldTime;

    private Date newTime;

    private String msg;

    private String remark;

    private String refuse;

    private Integer applyId;

    private String applyName;

    private Date createTime;

    private Date completeTime;

    private boolean deleted;

    private String uid;

    private Integer step;
}