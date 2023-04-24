//package com.ynzyq.yudbadmin.task;
//
//import com.google.common.collect.Lists;
//import com.ynzyq.yudbadmin.dao.business.dao.WalletMapper;
//import com.ynzyq.yudbadmin.dao.business.dao.WalletOrderMapper;
//import com.ynzyq.yudbadmin.dao.business.dao.WalletOrderStatisticsMapper;
//import com.ynzyq.yudbadmin.dao.business.dto.WalletOrderStatisticsDTO;
//import com.ynzyq.yudbadmin.dao.business.model.WalletOrder;
//import com.ynzyq.yudbadmin.dao.business.model.WalletOrderStatistics;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import tk.mybatis.mapper.entity.Example;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author xinchen
// * @date 2022/6/17 13:45
// * @description:
// */
//@Slf4j
//@Component
//public class WalletOrderStatisticsTask {
//
//    @Resource
//    WalletMapper walletMapper;
//
//    @Resource
//    WalletOrderStatisticsMapper walletOrderStatisticsMapper;
//
//    @Resource
//    WalletOrderMapper walletOrderMapper;
//
//        @Scheduled(cron = "0 0 0 1/1 * ?")
////    @Scheduled(cron = "0 0/10 * * * ?")
//    public void walletOrderStatisticsJob() {
//        log.info("钱包订单每日统计定时任务开始。。。");
//        List<WalletOrderStatisticsDTO> walletOrderStatisticsDTOS = walletMapper.walletOrderStatistics();
//        if (walletOrderStatisticsDTOS.size() > 0) {
////            List<WalletOrderStatistics> addList = Lists.newArrayList();
//            WalletOrderStatistics walletOrderStatistics;
//            Example example;
//            WalletOrder updateWalletOrder;
//            for (WalletOrderStatisticsDTO walletOrderStatisticsDTO : walletOrderStatisticsDTOS) {
//                WalletOrderStatistics query = new WalletOrderStatistics();
//                query.setStatisticsDate(walletOrderStatisticsDTO.getOrderDate());
//                query.setStoreUid(walletOrderStatisticsDTO.getStoreUid());
//                if (walletOrderStatisticsMapper.selectCount(query) > 0) {
//                    continue;
//                }
//                walletOrderStatistics = new WalletOrderStatistics();
//                walletOrderStatistics.setStatisticsDate(walletOrderStatisticsDTO.getOrderDate());
//                walletOrderStatistics.setStoreUid(walletOrderStatisticsDTO.getStoreUid());
//                walletOrderStatistics.setSettleStatus(2);
//                walletOrderStatistics.setWalletId(walletOrderStatisticsDTO.getWalletId());
//                walletOrderStatistics.setOrderMoney(walletOrderStatisticsDTO.getOrderMoney());
//                walletOrderStatistics.setOrderFee(walletOrderStatisticsDTO.getOrderFee());
//                walletOrderStatistics.setSettleMoney(BigDecimal.ZERO);
//                walletOrderStatistics.setCreateTime(new Date());
////                addList.add(walletOrderStatistics);
//                walletOrderStatisticsMapper.insertSelective(walletOrderStatistics);
//
//                example = new Example(WalletOrder.class);
//                example.createCriteria()
//                        .andEqualTo("orderDate", walletOrderStatisticsDTO.getOrderDate())
//                        .andEqualTo("storeUid", walletOrderStatisticsDTO.getStoreUid())
//                        .andNotEqualTo("settleStatus", 1);
//                updateWalletOrder = new WalletOrder();
//                updateWalletOrder.setStatisticsId(walletOrderStatistics.getId());
//                updateWalletOrder.setUpdateTime(new Date());
//                walletOrderMapper.updateByExampleSelective(updateWalletOrder, example);
//            }
////            if (addList.size() > 0) {
////                walletOrderStatisticsMapper.insertList(addList);
////            }
//        }
//        log.info("钱包订单每日统计查询定时任务开始。。。");
//    }
//}
