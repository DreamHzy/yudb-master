package com.ynzyq.yudbadmin.dao.business.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantStoreStatistics {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private Date statisticsDate;

    private Integer storeCount;

    private Integer agentAreaCount;

    private Integer manageCount;

    private Integer businessCount;

    private Integer notOpenCount;

    private Integer relocationCount;

    private Integer closeCount;

    private Integer pauseCount;

    private Integer northChinaCount;

    private Integer eastChinaCount;

    private Integer southChinaCount;

    private Integer westChinaCount;

    private Integer centerChinaCount;

    private Integer newFirstTierCount;

    private Integer firstTierCount;

    private Integer secondTierCount;

    private Integer thirdTierCount;

    private Integer fourTierCount;

    private Integer fiveTierCount;

    private Integer countyCount;

    private Integer hkmtCount;

    private Integer customerCount;

    private Integer addCustomerCount;

    private Integer loginCustomerCount;

    private Integer agentValid;

    private Integer agentInvalid;

    private Integer accountReceivableCount;

    private BigDecimal accountReceivableMoney;

    private Integer actualReceivableCount;

    private BigDecimal actualReceivableMoney;

    private Integer notReceivableCount;

    private BigDecimal notReceivableMoney;

    private Integer sendCount;

    private BigDecimal sendMoney;

    private Integer notSendCount;

    private BigDecimal notSendMoney;

    private Integer status;

    private Date createTime;
}