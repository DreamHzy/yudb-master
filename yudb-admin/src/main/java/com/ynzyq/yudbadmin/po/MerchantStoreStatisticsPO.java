package com.ynzyq.yudbadmin.po;

import cn.hutool.core.date.DateUtil;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreStatistics;
import com.ynzyq.yudbadmin.dao.evaluate.enums.LevelCityEnum;
import com.ynzyq.yudbadmin.dao.evaluate.enums.StoreRegionEnum;
import com.ynzyq.yudbadmin.enums.StatusEnum;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/12/8 21:39
 * @description:
 */
public class MerchantStoreStatisticsPO extends MerchantStoreStatistics implements Serializable {
    public MerchantStoreStatisticsPO(StoreCountDTO storeCountDTO, StoreStatusCountDTO storeStatusCountDTO, List<RegionStoreCountDTO> regionStoreCountDTOS, List<LevelStoreCountDTO> levelStoreCountDTOs, StoreBillDTO storeBillDTO, CustomerDTO customerDTO, AgencyDTO agencyDTO) {
        this.setStatisticsDate(DateUtil.yesterday());
        this.setStoreCount(storeCountDTO.getStoreCount());
        this.setAgentAreaCount(agencyDTO.getAgentAreaCount());
        this.setManageCount(storeCountDTO.getManageCount());
        this.setBusinessCount(storeStatusCountDTO.getBusinessCount());
        this.setNotOpenCount(storeStatusCountDTO.getNotOpenCount());
        this.setRelocationCount(storeStatusCountDTO.getRelocationCount());
        this.setCloseCount(storeStatusCountDTO.getCloseCount());
        this.setPauseCount(storeStatusCountDTO.getPauseCount());
        if (regionStoreCountDTOS.size() > 0) {
            regionStoreCountDTOS.forEach(regionStoreCountDTO -> {
                if (StoreRegionEnum.NORTH_CHINA.getRegion().equals(regionStoreCountDTO.getRegion())) {
                    this.setNorthChinaCount(regionStoreCountDTO.getCount());
                } else if (StoreRegionEnum.EAST_CHINA.getRegion().equals(regionStoreCountDTO.getRegion())) {
                    this.setEastChinaCount(regionStoreCountDTO.getCount());
                } else if (StoreRegionEnum.SOUTH_CHINA.getRegion().equals(regionStoreCountDTO.getRegion())) {
                    this.setSouthChinaCount(regionStoreCountDTO.getCount());
                } else if (StoreRegionEnum.WEST_CHINA.getRegion().equals(regionStoreCountDTO.getRegion())) {
                    this.setWestChinaCount(regionStoreCountDTO.getCount());
                } else if (StoreRegionEnum.CENTRAL_CHINA.getRegion().equals(regionStoreCountDTO.getRegion())) {
                    this.setCenterChinaCount(regionStoreCountDTO.getCount());
                }
            });
        }
        if (levelStoreCountDTOs.size() > 0) {
            levelStoreCountDTOs.forEach(levelStoreCountDTO -> {
                if (LevelCityEnum.NEW_FIRST_TIER.getLevel().equals(levelStoreCountDTO.getLevel())) {
                    this.setNewFirstTierCount(levelStoreCountDTO.getCount());
                } else if (LevelCityEnum.FIRST_TIER.getLevel().equals(levelStoreCountDTO.getLevel())) {
                    this.setFirstTierCount(levelStoreCountDTO.getCount());
                } else if (LevelCityEnum.SECOND_TIER.getLevel().equals(levelStoreCountDTO.getLevel())) {
                    this.setSecondTierCount(levelStoreCountDTO.getCount());
                } else if (LevelCityEnum.THIRD_TIER.getLevel().equals(levelStoreCountDTO.getLevel())) {
                    this.setThirdTierCount(levelStoreCountDTO.getCount());
                } else if (LevelCityEnum.FOUR_TIER.getLevel().equals(levelStoreCountDTO.getLevel())) {
                    this.setFourTierCount(levelStoreCountDTO.getCount());
                } else if (LevelCityEnum.FIVE_TIER.getLevel().equals(levelStoreCountDTO.getLevel())) {
                    this.setFiveTierCount(levelStoreCountDTO.getCount());
                } else if (LevelCityEnum.COUNTY.getLevel().equals(levelStoreCountDTO.getLevel())) {
                    this.setCountyCount(levelStoreCountDTO.getCount());
                } else if (LevelCityEnum.HKMT.getLevel().equals(levelStoreCountDTO.getLevel())) {
                    this.setHkmtCount(levelStoreCountDTO.getCount());
                }
            });
        }
        this.setCustomerCount(customerDTO.getCustomerCount());
        this.setAddCustomerCount(customerDTO.getAddCustomerCount());
        this.setLoginCustomerCount(customerDTO.getLoginCustomerCount());
        this.setAgentValid(agencyDTO.getAgentValid());
        this.setAgentInvalid(agencyDTO.getAgentInvalid());
        this.setAccountReceivableCount(storeBillDTO.getAccountReceivableCount());
        this.setAccountReceivableMoney(storeBillDTO.getAccountReceivableMoney());
        this.setActualReceivableCount(storeBillDTO.getActualReceivableCount());
        this.setActualReceivableMoney(storeBillDTO.getActualReceivableMoney());
        this.setNotReceivableCount(storeBillDTO.getAccountReceivableCount() - storeBillDTO.getActualReceivableCount());
        this.setNotReceivableMoney(storeBillDTO.getAccountReceivableMoney().subtract(storeBillDTO.getActualReceivableMoney()));
        this.setSendCount(storeBillDTO.getSendCount());
        this.setSendMoney(storeBillDTO.getSendMoney());
        this.setNotSendCount(storeBillDTO.getNotSendCount());
        this.setNotSendMoney(storeBillDTO.getNotSendMoney());
        this.setStatus(StatusEnum.ENABLE.getStatus());
        this.setCreateTime(new Date());
    }
}
