package com.yunzyq.yudbapp.dao.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MerchantStore {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String uid;

    private String name;

    private Integer merchantId;

    private String signatory;

    private String mobile;

    private Date signTime;

    private Date openTime;

    private Date expireTime;

    private Date serviceExpireTime;

    private String province;

    private String city;

    private String address;

    private Integer status;

    private BigDecimal managementExpense;

    private BigDecimal alreadyManagementExpense;

    private BigDecimal needManagementExpense;

    private Integer merchantLink;

    private Date merchantLinkTime;

    private Date merchantLinkEndTime;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    private String area;

    private String idNumber;

    private Integer contractStatus;
}