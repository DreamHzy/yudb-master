package com.ynzyq.yudbadmin.dao.business.dto;

import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreStatistics;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2021/12/8 19:58
 * @description:
 */
@Data
public class CustomerDTO implements Serializable {

    /**
     * 当前客户数
     */
    private Integer customerCount;

    /**
     * 今日新增客户数
     */
    private Integer addCustomerCount;

    /**
     * 今日登录客户数
     */
    private Integer loginCustomerCount;
}
