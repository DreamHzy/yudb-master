package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/1/25 11:52
 * @description:
 */
@Data
public class ListComplaintDTO implements Serializable {
    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("加盟商")
    private String merchantName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("区域")
    private String region;

    public void setStartTime(String startTime) {
        if (StringUtils.isNotBlank(startTime)) {
            this.startTime = startTime + " 00:00:00";
        }
    }

    public void setEndTime(String endTime) {
        if (StringUtils.isNotBlank(endTime)) {
            this.endTime = endTime + " 23:59:59";
        }
    }

    public void setMerchantName(String merchantName) {
        if (StringUtils.isNotBlank(merchantName)) {
            this.merchantName = merchantName;
        }
    }

    public void setMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            this.mobile = mobile;
        }
    }

    public void setStatus(String status) {
        if (StringUtils.isNotBlank(status)) {
            this.status = status;
        }
    }

    public void setProvince(String province) {
        if (StringUtils.isNotBlank(province)) {
            this.province = province;
        }
    }

    public void setCity(String city) {
        if (StringUtils.isNotBlank(city)) {
            this.city = city;
        }
    }

    public void setArea(String area) {
        if (StringUtils.isNotBlank(area)) {
            this.area = area;
        }
    }

    public void setRegion(String region) {
        if (StringUtils.isNotBlank(region)) {
            this.region = region;
        }
    }
}
