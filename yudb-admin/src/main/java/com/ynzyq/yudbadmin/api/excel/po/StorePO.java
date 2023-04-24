package com.ynzyq.yudbadmin.api.excel.po;

import com.ynzyq.yudbadmin.api.excel.dto.StoreDTO;
import com.ynzyq.yudbadmin.api.excel.enums.ContractStatusEnum;
import com.ynzyq.yudbadmin.api.excel.enums.NewStatusTwoEnum;
import com.ynzyq.yudbadmin.api.excel.enums.StatusTwoEnum;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Slf4j
@Data
public class StorePO extends MerchantStore implements Serializable {

    public StorePO(StoreDTO storeDTO, Integer merchantId, String desc) {
        this.setUid(storeDTO.getUid());
        this.setMerchantId(merchantId);
        this.setSignatory(StringUtils.isBlank(storeDTO.getSignatory()) ? null : storeDTO.getSignatory());
        this.setMobile(StringUtils.isBlank(storeDTO.getMobile()) ? null : storeDTO.getMobile());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.setSignTime(StringUtils.isBlank(storeDTO.getSignTime()) ? null : sdf.parse(storeDTO.getSignTime()));
        } catch (Exception e) {
            log.error("日期格式转换异常", e);
            this.setSignTime(null);
        }
        try {
            this.setStartTime(StringUtils.isBlank(storeDTO.getSignTime()) ? null : sdf.parse(storeDTO.getSignTime()));
        } catch (Exception e) {
            log.error("日期格式转换异常", e);
            this.setSignTime(null);
        }
        try {
            this.setExpireTime(StringUtils.isBlank(storeDTO.getSignTime()) ? null : sdf.parse(storeDTO.getExpireTime()));
            this.setServiceExpireTime(StringUtils.isBlank(storeDTO.getServiceTime()) ? null : sdf.parse(storeDTO.getServiceTime()));
        } catch (Exception e) {
            log.error("日期格式转换异常", e);
            this.setSignTime(null);
        }
        try {
            this.setOpenTime(StringUtils.isBlank(storeDTO.getOpenTime()) ? null : sdf.parse(storeDTO.getOpenTime()));
        } catch (Exception e) {
            log.error("日期格式转换异常", e);
        }
        try {
            this.setCloseTime(StringUtils.isBlank(storeDTO.getCloseTime()) ? null : sdf.parse(storeDTO.getCloseTime()));
        } catch (Exception e) {
            log.error("日期格式转换异常", e);
        }
        try {
            this.setRelocationTime(StringUtils.isBlank(storeDTO.getRelocationTime()) ? null : sdf.parse(storeDTO.getRelocationTime()));
        } catch (Exception e) {
            log.error("日期格式转换异常", e);
        }
        try {
            this.setPauseTime(StringUtils.isBlank(storeDTO.getPauseTime()) ? null : sdf.parse(storeDTO.getPauseTime()));
        } catch (Exception e) {
            log.error("日期格式转换异常", e);
        }
        this.setAgentName(storeDTO.getAgentName());
        this.setIsPreferential(StringUtils.equals(storeDTO.getIsAgentStore(), "是") ? 1 : 2);
        this.setProvince(StringUtils.isBlank(storeDTO.getProvince()) ? null : storeDTO.getProvince());
        this.setCity(StringUtils.isBlank(storeDTO.getCity()) ? null : storeDTO.getCity());
        this.setArea(StringUtils.isBlank(storeDTO.getArea()) ? null : storeDTO.getArea());
        this.setAddress(StringUtils.isBlank(storeDTO.getAddress()) ? null : storeDTO.getAddress());
        this.setStatus(StringUtils.isBlank(storeDTO.getStatus()) ? null : StatusTwoEnum.getStatusNum(storeDTO.getStatus()));
        this.setStatusTwo(StringUtils.isBlank(storeDTO.getStatusTwo()) ? null : NewStatusTwoEnum.getStatusNum(storeDTO.getStatusTwo()));
        this.setContractStatus(ContractStatusEnum.getStatusNum(storeDTO.getContractStatus()));
        try {
            this.setFranchiseFee(StringUtils.isBlank(storeDTO.getFranchiseFee()) ? BigDecimal.ZERO : new BigDecimal(storeDTO.getFranchiseFee()));
        } catch (Exception e) {
            log.error("类型转换错误", e);
            this.setFranchiseFee(BigDecimal.ZERO);
        }
        this.setManagementExpense(StringUtils.isBlank(storeDTO.getManagementExpense()) ? BigDecimal.ZERO : new BigDecimal(storeDTO.getManagementExpense()));
        this.setNeedManagementExpense(StringUtils.isBlank(storeDTO.getManagementExpense()) ? BigDecimal.ZERO : new BigDecimal(storeDTO.getManagementExpense()));
        this.setAlreadyManagementExpense(StringUtils.isBlank(storeDTO.getManagementExpense()) ? BigDecimal.ZERO : new BigDecimal(storeDTO.getManagementExpense()));
        this.setBondMoney(StringUtils.isBlank(storeDTO.getBondMoney()) ? BigDecimal.ZERO : new BigDecimal(storeDTO.getBondMoney()));
        this.setMerchantMoney(BigDecimal.ZERO);
        this.setMerchantLink(2);
        this.setCreateUser(1);
        this.setCreateTime(new Date());
    }

    public StorePO(StoreDTO storeDTO, Integer id) {
        this.setId(id);
        this.setAddress(storeDTO.getAddress());
        this.setStatus(StatusTwoEnum.getStatusNum(storeDTO.getStatus()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.setSignTime(StringUtils.isBlank(storeDTO.getSignTime()) ? null : sdf.parse(storeDTO.getSignTime()));
            this.setStartTime(StringUtils.isBlank(storeDTO.getSignTime()) ? null : sdf.parse(storeDTO.getSignTime()));
            this.setExpireTime(StringUtils.isBlank(storeDTO.getExpireTime()) ? null : sdf.parse(storeDTO.getExpireTime()));
            this.setOpenTime(StringUtils.isBlank(storeDTO.getOpenTime()) ? null : sdf.parse(storeDTO.getOpenTime()));
            this.setCloseTime(StringUtils.isBlank(storeDTO.getCloseTime()) ? null : sdf.parse(storeDTO.getCloseTime()));
            this.setRelocationTime(StringUtils.isBlank(storeDTO.getRelocationTime()) ? null : sdf.parse(storeDTO.getRelocationTime()));
            this.setPauseTime(StringUtils.isBlank(storeDTO.getPauseTime()) ? null : sdf.parse(storeDTO.getPauseTime()));
            this.setServiceExpireTime(StringUtils.isBlank(storeDTO.getServiceTime()) ? null : sdf.parse(storeDTO.getServiceTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.setContractStatus(ContractStatusEnum.getStatusNum(storeDTO.getContractStatus()));
        this.setStatusTwo(NewStatusTwoEnum.getStatusNum(storeDTO.getStatusTwo()));
        this.setFranchiseFee(StringUtils.isBlank(storeDTO.getFranchiseFee()) ? BigDecimal.ZERO : new BigDecimal(storeDTO.getFranchiseFee()));
        this.setBondMoney(StringUtils.isBlank(storeDTO.getBondMoney()) ? BigDecimal.ZERO : new BigDecimal(storeDTO.getBondMoney()));
        this.setAgentName(storeDTO.getAgentName());
        this.setIsPreferential(StringUtils.equals(storeDTO.getIsAgentStore(), "是") ? 1 : 2);
        this.setManagementExpense(StringUtils.isBlank(storeDTO.getManagementExpense()) ? BigDecimal.ZERO : new BigDecimal(storeDTO.getManagementExpense()));
    }
}
