package com.ynzyq.yudbadmin.api.excel.po;

import com.ynzyq.yudbadmin.api.excel.dto.MerchantAreaDTO;
import com.ynzyq.yudbadmin.dao.business.model.Merchant;
import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentArea;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MerchantAreaPO extends MerchantAgentArea implements Serializable {
    public MerchantAreaPO(MerchantAreaDTO merchantAreaDTO, Merchant merchant) {
        this.setUid(merchantAreaDTO.getCode());
        this.setMerchantId(merchant.getId());
        this.setMerchantName(merchant.getName());
//        this.setProvince(merchantAreaDTO.getProvince());
//        this.setCity(merchantAreaDTO.getCity());
//        this.setArea(merchantAreaDTO.getArea());
//        this.setAgentArea(merchantAreaDTO.getArea());
        this.setAgencyFees(new BigDecimal(merchantAreaDTO.getAgencyFees()));
        this.setManagementExpense(new BigDecimal(merchantAreaDTO.getManagementFee()));
        this.setDepositFee(new BigDecimal(merchantAreaDTO.getMarginFee()));
        if (StringUtils.isNotBlank(merchantAreaDTO.getSigningTime()) && StringUtils.isNotBlank(merchantAreaDTO.getExpireDate())) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.setSignTime(sdf.parse(merchantAreaDTO.getSigningTime()));
                this.setStartTime(sdf.parse(merchantAreaDTO.getSigningTime()));
                this.setExpireTime(sdf.parse(merchantAreaDTO.getExpireDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.setIsOpenPosition(StringUtils.equals(merchantAreaDTO.getIsPosition(), "是") ? 1 : 2);
        this.setCreateUser(1);
        this.setCreateTime(new Date());
    }
}
