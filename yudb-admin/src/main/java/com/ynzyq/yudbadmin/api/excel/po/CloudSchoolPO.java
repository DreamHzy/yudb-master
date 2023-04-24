package com.ynzyq.yudbadmin.api.excel.po;

import com.ynzyq.yudbadmin.api.excel.dto.CloudSchoolDTO;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreCloudSchool;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Slf4j
@Data
public class CloudSchoolPO extends MerchantStoreCloudSchool implements Serializable {

    public CloudSchoolPO(Integer storeId, CloudSchoolDTO cloudSchoolDTO) {
        this.setMerchantStoreId(storeId);
        this.setMerchantStoreUid(cloudSchoolDTO.getCode());
        this.setAccount(cloudSchoolDTO.getAccount());
        this.setStatus(StatusEnum.ENABLE.getStatus());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.setEndTime(StringUtils.isBlank(cloudSchoolDTO.getEndTime()) ? null : sdf.parse(cloudSchoolDTO.getEndTime()));
            this.setCreateDate(StringUtils.isBlank(cloudSchoolDTO.getStartTime()) ? null : sdf.parse(cloudSchoolDTO.getStartTime()));
        } catch (Exception e) {
            log.error("日期格式转换异常", e.getMessage());
            this.setEndTime(null);
        }
        this.setCreateTime(new Date());

    }
}
