package com.ynzyq.yudbadmin.dao.business.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author xinchen
 * @date 2022/4/6 12:00
 * @description:
 */
@Data
public class ListApplyRecordDTO implements Serializable {
    @ApiModelProperty("授权号")
    private String uid;

    @ApiModelProperty("联系方式")
    private String mobile;

    @ApiModelProperty("签约人")
    private String signatory;

    private Integer userId;

    public void setUid(String uid) {
        if (StringUtils.isNotBlank(uid)) {
            this.uid = uid;
        }
    }

    public void setMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile)) {
            this.mobile = mobile;
        }
    }

    public void setSignatory(String signatory) {
        if (StringUtils.isNotBlank(signatory)) {
            this.signatory = signatory;
        }
    }
}
