package com.ynzyq.yudbadmin.task;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreStatisticsMapper;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreStatistics;
import com.ynzyq.yudbadmin.dao.business.vo.MerchantVO;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import com.ynzyq.yudbadmin.po.MerchantStoreStatisticsPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xinchen
 * @date 2021/12/8 16:31
 * @description:
 */
@Component
@Slf4j
public class MerchantStoreStatisticsTask {

    @Resource
    MerchantStoreStatisticsMapper merchantStoreStatisticsMapper;

    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "*/20 * * * * ?")//每五分 测试用`
    public void statisticsJob() {
        log.info("门店数据统计定时任务开始。。。");
        // 判断昨日数据是否已统计过
        String yesterday = DateUtil.format(DateUtil.yesterday(), "yyyy-MM-dd");
        Example countExample = new Example(MerchantStoreStatistics.class);
        countExample.createCriteria()
                .andEqualTo("statisticsDate", yesterday)
                .andEqualTo("status", StatusEnum.ENABLE.getStatus());
        int count = merchantStoreStatisticsMapper.selectCountByExample(countExample);
        if (count == 0) {
            // 概览：门店数量和经营门店数，代理权数在代理权分析中统计
            StoreCountDTO storeCountDTO = merchantStoreStatisticsMapper.storeCount();
            // 门店数据分析：各不同状态的门店数量
            StoreStatusCountDTO storeStatusCountDTO = merchantStoreStatisticsMapper.storeStatusCount();
            // 区域：门店数量
            List<RegionStoreCountDTO> regionStoreCountDTOs = merchantStoreStatisticsMapper.regionStoreCount();
            // 线级：门店数量
            List<LevelStoreCountDTO> levelStoreCountDTOs = merchantStoreStatisticsMapper.levelStoreCount();
            // 账单数据分析：门店和代理账单
            StoreBillDTO storeBillDTO = merchantStoreStatisticsMapper.storeBill();
            // 客户情况：客户数量
            CustomerDTO customerDTO = merchantStoreStatisticsMapper.customer(yesterday + " 00:00:00", yesterday + " 23:59:59");
            int loginCount = merchantStoreStatisticsMapper.loginCount(yesterday + " 00:00:00", yesterday + " 23:59:59");
            customerDTO.setLoginCustomerCount(loginCount);
            // 计算需要剔除的客户数量(门店和代理数都为0)
            List<Integer> removeList = Lists.newArrayList();
            List<MerchantVO> merchantVOS = merchantStoreStatisticsMapper.merchantData();
            merchantVOS.forEach(merchantVO -> {
                if (merchantVO.getAgentCount().equals(0) && merchantVO.getStoreCount().equals(0)) {
                    removeList.add(merchantVO.getId());
                }
            });
            customerDTO.setCustomerCount(customerDTO.getCustomerCount() - removeList.size());
            // 代理权分析：代理权数(概览)，代理权有效无效
            AgencyDTO agencyDTO = merchantStoreStatisticsMapper.agency();

            // 添加统计数据记录
            MerchantStoreStatisticsPO merchantStoreStatistics = new MerchantStoreStatisticsPO(storeCountDTO, storeStatusCountDTO, regionStoreCountDTOs, levelStoreCountDTOs, storeBillDTO, customerDTO, agencyDTO);
            merchantStoreStatisticsMapper.insertSelective(merchantStoreStatistics);
        }
        log.info("门店数据统计定时任务结束。。。");
    }

}

