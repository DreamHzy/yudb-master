package com.ynzyq.yudbadmin.dao.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Data
public class AgencyAreaVO implements Serializable {
    @ApiModelProperty("代理权代码")
    private String UID;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区域")
    private String area;

//    @ApiModelProperty("区域经理")
//    private String regionalName;
//
//    @ApiModelProperty("区域经理Id")
//    private String regionalManagerId;
//
//
//    @ApiModelProperty("开始时间")
//    private String startTime;
//
//    @ApiModelProperty("到期时间")
//    private String expireTime;
//
//    @ApiModelProperty("管理费标准")
//    private String managementExpense;

//    public AgencyAreaVO(String regionalName, MerchantAgentArea merchantAgentArea) {
//        this.province = merchantAgentArea.getProvince();
//        this.city = merchantAgentArea.getCity();
//        this.area = merchantAgentArea.getArea();
//        this.regionalName = regionalName;
//        this.regionalManagerId = regionalManagerId;
//        this.startTime = DateUtils.formatDate(merchantAgentArea.getStartTime(), "yyyy-MM-dd");
//        this.expireTime = DateUtils.formatDate(merchantAgentArea.getExpireTime(), "yyyy-MM-dd");
//        this.managementExpense = String.valueOf(merchantAgentArea.getManagementExpense());
//    }
}
