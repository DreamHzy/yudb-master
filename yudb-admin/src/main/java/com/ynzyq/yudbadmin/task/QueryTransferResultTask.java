//package com.ynzyq.yudbadmin.task;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreMapper;
//import com.ynzyq.yudbadmin.dao.business.dao.WalletMapper;
//import com.ynzyq.yudbadmin.dao.business.dao.WalletOrderMapper;
//import com.ynzyq.yudbadmin.dao.business.dao.WalletOrderStatisticsMapper;
//import com.ynzyq.yudbadmin.dao.business.dto.QueryTransferResultDTO;
//import com.ynzyq.yudbadmin.dao.business.dto.SettleStatusStatistics;
//import com.ynzyq.yudbadmin.dao.business.model.MerchantStore;
//import com.ynzyq.yudbadmin.dao.business.model.WalletOrder;
//import com.ynzyq.yudbadmin.dao.business.model.WalletOrderStatistics;
//import com.ynzyq.yudbadmin.util.ylWallet.WalletUtil;
//import com.ynzyq.yudbadmin.util.ylWallet.parm.QueryBatchTransResultParam;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author xinchen
// * @date 2022/6/17 10:36
// * @description:
// */
//@Slf4j
//@Component
//public class QueryTransferResultTask {
//
//    @Resource
//    WalletOrderMapper walletOrderMapper;
//
//    @Resource
//    MerchantStoreMapper merchantStoreMapper;
//
//    @Resource
//    WalletOrderStatisticsMapper walletOrderStatisticsMapper;
//
//    @Resource
//    WalletMapper walletMapper;
//
//    //    @Scheduled(cron = "0 0 1 1 * ?")
//    @Scheduled(cron = "0 0/2 * * * ?")
//    public void queryTransferResultJob() {
//        log.info("钱包订单转账结果查询定时任务开始。。。");
//        WalletOrder query = new WalletOrder();
//        query.setSettleStatus(3);
//        List<WalletOrder> walletOrderList = walletOrderMapper.select(query);
//        QueryBatchTransResultParam queryBatchTransResultParam;
//        MerchantStore merchantStore;
//        JSONObject jsonObject = null;
//        if (walletOrderList.size() > 0) {
//            for (WalletOrder walletOrder : walletOrderList) {
//                merchantStore = merchantStoreMapper.queryByUid(walletOrder.getStoreUid());
//                if (merchantStore != null && StringUtils.isNotBlank(merchantStore.getWalletId())) {
//                    queryBatchTransResultParam = new QueryBatchTransResultParam();
//                    queryBatchTransResultParam.setOriMctOrderNo(walletOrder.getMctOrderNo());
//                    queryBatchTransResultParam.setOperType("3");
//                    queryBatchTransResultParam.setSn(walletOrder.getOrderNo());
//                    try {
//                        log.info("订单【{}】定时任务查询请求参数：{}", walletOrder.getOrderNo(), JSON.toJSONString(queryBatchTransResultParam));
//                        jsonObject = WalletUtil.queryBatchTransResult(queryBatchTransResultParam);
//                        log.info("订单【{}】定时任务查询请求参数：{}", walletOrder.getOrderNo(), JSON.toJSONString(jsonObject));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (jsonObject == null) {
//                        continue;
//                    }
//                    if (StringUtils.equals(jsonObject.getString("code"), "00000")) {
//                        if (StringUtils.equals(JSONObject.parseObject(jsonObject.getString("response")).getString("rspCode"), "00000")) {
//                            String msgBody = JSONObject.parseObject(jsonObject.getString("response")).getString("msgBody");
//                            JSONObject msgBodyJSONObject = JSONObject.parseObject(msgBody);
//                            String rowList = msgBodyJSONObject.getString("list");
//                            // 解析返回参数
//                            List<QueryTransferResultDTO> queryTransferResultDTOS = JSONArray.parseArray(rowList, QueryTransferResultDTO.class);
//                            for (QueryTransferResultDTO queryTransferResultDTO : queryTransferResultDTOS) {
//                                // 判断是否为该笔订单
//                                if (StringUtils.equals(queryTransferResultDTO.getSn(), walletOrder.getOrderNo())) {
//                                    // 更新订单信息
//                                    WalletOrder updateWalletOrder = new WalletOrder();
//                                    updateWalletOrder.setId(walletOrder.getId());
//                                    updateWalletOrder.setUpdateTime(new Date());
//                                    if (StringUtils.equals("3", queryTransferResultDTO.getStatus())) {
//                                        updateWalletOrder.setSettleStatus(1);
//                                    } else if (StringUtils.equals("4", queryTransferResultDTO.getStatus())) {
//                                        updateWalletOrder.setSettleStatus(4);
//                                    } else {
//                                        updateWalletOrder.setSettleStatus(3);
//                                    }
//                                    walletOrderMapper.updateByPrimaryKeySelective(updateWalletOrder);
//
//                                    WalletOrderStatistics walletOrderStatistics = walletOrderStatisticsMapper.selectByPrimaryKey(walletOrder.getStatisticsId());
//                                    if (walletOrderStatistics != null) {
//                                        SettleStatusStatistics settleStatusStatistics = walletMapper.settleStatusStatistics(walletOrder.getStatisticsId());
//                                        WalletOrderStatistics updateWalletOrderStatistics = new WalletOrderStatistics();
//                                        updateWalletOrderStatistics.setId(walletOrderStatistics.getId());
//                                        updateWalletOrderStatistics.setUpdateTime(new Date());
//                                        if (StringUtils.equals("3", queryTransferResultDTO.getStatus())) {
//                                            updateWalletOrderStatistics.setSettleMoney(walletOrderStatistics.getSettleMoney().add(walletOrder.getOrderMoney()));
//                                        }
//                                        if (settleStatusStatistics.getCount().equals(settleStatusStatistics.getSettleCount())) {
//                                            updateWalletOrderStatistics.setSettleStatus(1);
//                                        } else if (settleStatusStatistics.getCount().equals(settleStatusStatistics.getNotSettleCount())) {
//                                            updateWalletOrderStatistics.setSettleStatus(2);
//                                        } else {
//                                            updateWalletOrderStatistics.setSettleStatus(3);
//                                        }
//                                        walletOrderStatisticsMapper.updateByPrimaryKeySelective(updateWalletOrderStatistics);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        log.info("钱包订单转账结果查询定时任务结束。。。");
//    }
//}
