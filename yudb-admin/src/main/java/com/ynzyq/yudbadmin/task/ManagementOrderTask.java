//package com.ynzyq.yudbadmin.task;
//
//import com.ynzyq.yudbadmin.dao.business.dao.*;
//import com.ynzyq.yudbadmin.dao.business.model.*;
//import com.ynzyq.yudbadmin.dao.business.vo.ManagementOrderExpireTimeVo;
//import com.ynzyq.yudbadmin.dao.business.vo.ManagementOrderJoinVo;
//import com.ynzyq.yudbadmin.util.DateUtil;
//import com.ynzyq.yudbadmin.util.PlatformCodeUtil;
//import com.ynzyq.yudbadmin.util.sms.SmsUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@Slf4j
//public class ManagementOrderTask {
//
//    @Resource
//    MerchantStoreMapper merchantStoreMapper;
//    @Resource
//    PaymentOrderMasterMapper paymentOrderMasterMapper;
//    @Resource
//    MonitorUserMapper monitorUserMapper;
//
//    @Resource
//    PaymentOrderExamineMapper paymentOrderExamineMapper;
//
//    @Resource
//    PaymentOrderExamineDeailMapper paymentOrderExamineDeailMapper;
//
//    @Resource
//    ApproveProcessMapper approveProcessMapper;
//
//    @Scheduled(cron = "0 0 1 * * ?")
////  @Scheduled(cron = "*/20 * * * * ?")//每五分 测试用`
//    public void managementOrderJob() {
//        log.info("管理费生成任务开始");
//        //先查询合约快要到期的用户30天
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        List<PaymentOrderMaster> merchantStoresNew = new ArrayList<>();
//        List<ManagementOrderExpireTimeVo> merchantStores = merchantStoreMapper.queryByExpireTime();
//        if (merchantStores.size() > 0) {
//            //并且排除已经有生成待支付管理费的门店
//            List<ManagementOrderJoinVo> managementOrderJoinVoList = paymentOrderMasterMapper.queryManagementOrderJoinVoList(merchantStores);
//            merchantStores.stream().forEach(
//                    merchantStore -> {
//                        if (merchantStore.getMoney().compareTo(BigDecimal.ZERO) == 0) {
//                            return;
//                        }
//                        if (merchantStore.getServiceExpireTime() == null) {
//                            return;
//                        }
//                        //  判断签约时间是否小于2019-01-01
//                        if (merchantStore.getSize().equals(1)) {
//                            // 判断是否适用代理优惠，是就不生成订单
//                            if (merchantStore.getIsPreferential().equals(1)) {
//                                return;
//                            }
//                        }
//                        merchantStore.setStatus(0);
//                        managementOrderJoinVoList.stream().forEach(
//                                managementOrderJoinVo -> {
//                                    if (merchantStore.getMerchantStoreId().equals(managementOrderJoinVo.getId())) {//如果是相同的门店对比金额
//                                        merchantStore.setStatus(1);
//                                    }
//                                }
//                        );
//                        if (merchantStore.getStatus() == 0) {
//                            Date newTime1 = DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
//                            PaymentOrderMaster paymentOrderMaster = new PaymentOrderMaster();
//                            paymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));
//                            paymentOrderMaster.setMerchantId(Integer.valueOf(merchantStore.getMerchantId()));
//                            paymentOrderMaster.setMerchantName(merchantStore.getMerchantName());
//                            paymentOrderMaster.setMerchantStoreId(Integer.valueOf(merchantStore.getMerchantStoreId()));
//                            paymentOrderMaster.setMerchantStoreName(merchantStore.getMerchantStoreName());
//                            paymentOrderMaster.setMerchantStoreUid(merchantStore.getMerchantStoreUid());
//                            paymentOrderMaster.setMerchantStoreMobile(merchantStore.getMerchantStoreMobile());
//                            paymentOrderMaster.setRegionalManagerId(Integer.valueOf(merchantStore.getRegionalManagerId()));
//                            paymentOrderMaster.setRegionalManagerName(merchantStore.getRegionalManagerName());
//                            paymentOrderMaster.setRegionalManagerMobile(merchantStore.getRegionalManagerMobile());
//                            paymentOrderMaster.setPaymentTypeId(1);
//                            paymentOrderMaster.setPaymentTypeName("管理费");
//                            paymentOrderMaster.setRemark("管理费");
//                            paymentOrderMaster.setType(1);
//                            paymentOrderMaster.setSend(2);
//                            paymentOrderMaster.setExpireTime(merchantStore.getServiceExpireTime());
//                            paymentOrderMaster.setStatus(1);
//                            paymentOrderMaster.setExamine(2);
//                            paymentOrderMaster.setDeleted(false);
//                            paymentOrderMaster.setCreateTime(new Date());
//                            paymentOrderMaster.setExamineNum(2);
//                            paymentOrderMaster.setMoney(merchantStore.getMoney());
//                            paymentOrderMaster.setPayType(1);
//
//                            paymentOrderMaster.setCity(merchantStore.getCity());
//                            paymentOrderMaster.setProvince(merchantStore.getProvince());
//                            paymentOrderMaster.setArea(merchantStore.getArea());
//                            paymentOrderMaster.setAdjustmentCount(0);
//                            paymentOrderMaster.setAddress(merchantStore.getAddress());
//                            paymentOrderMaster.setPaymentStandardMoney(paymentOrderMaster.getMoney());
//                            paymentOrderMaster.setCycle(sdf.format(merchantStore.getServiceExpireTime()) + "~" + sdf.format(newTime1));
////
//                            merchantStoresNew.add(paymentOrderMaster);
//                        }
//                    }
//            );
//            if (merchantStoresNew.size() > 0) {
//                paymentOrderMasterMapper.insertList(merchantStoresNew);
//
//                merchantStoresNew.forEach(paymentOrderMaster -> {
//                    //缴费审核订单
//                    PaymentOrderExamine paymentOrderExamine = new PaymentOrderExamine();
//                    paymentOrderExamine.setPaymentTypeId(paymentOrderMaster.getPaymentTypeId());
//                    paymentOrderExamine.setPaymentOrderMasterId(paymentOrderMaster.getId());
//                    paymentOrderExamine.setMerchantStoreUid(paymentOrderMaster.getMerchantStoreUid());
//                    paymentOrderExamine.setMerchantStoreId(paymentOrderMaster.getMerchantStoreId());
//                    paymentOrderExamine.setPaymentTypeName(paymentOrderMaster.getPaymentTypeName());
//                    paymentOrderExamine.setNewMoney(paymentOrderMaster.getMoney());
//                    paymentOrderExamine.setExamineType(3);
//                    paymentOrderExamine.setExamine(1);
//                    paymentOrderExamine.setStatus(1);
//                    paymentOrderExamine.setApplyName("系统自动生成");
//                    paymentOrderExamine.setMsg("新订单审核:" + paymentOrderMaster.getPaymentTypeName() + paymentOrderMaster.getMoney() + "元");
//                    paymentOrderExamine.setRemark("系统自动生成");
//                    paymentOrderExamine.setDeleted(false);
//                    paymentOrderExamine.setCreateTime(new Date());
//                    paymentOrderExamine.setStep(1);
//                    paymentOrderExamineMapper.insertSelective(paymentOrderExamine);
//                    //缴费审核订单明细
//                    PaymentOrderExamineDeail paymentOrderExamineDeail = new PaymentOrderExamineDeail();
//                    paymentOrderExamineDeail.setPaymentOrderExamineId(paymentOrderExamine.getId());
//                    paymentOrderExamineDeail.setExamine(1);
//                    paymentOrderExamineDeail.setStatus(1);
//                    paymentOrderExamineDeail.setCreateTime(new Date());
//                    paymentOrderExamineDeail.setDeleted(false);
//                    paymentOrderExamineDeail.setStep(1);
//                    paymentOrderExamineDeail.setType(1);
//                    // 查询该阶段的审核人
//                    List<String> approveNames = approveProcessMapper.getApproveName(1, paymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), paymentOrderExamineDeail.getType());
//                    String approveName = approveNames.stream().collect(Collectors.joining(","));
//                    paymentOrderExamineDeail.setApproveName(approveName);
//                    paymentOrderExamineDeailMapper.insertSelective(paymentOrderExamineDeail);
//                });
//            }
//        }
//        List<MonitorUser> monitorUserList = monitorUserMapper.queryByStatus(1);
//        monitorUserList.stream().forEach(
//                monitorUser -> {
//                    log.info("进入监控短信模块");
//                    SmsUtil.notice(monitorUser.getPhone(), "管理费", merchantStoresNew.size());
//                }
//        );
//        log.info("管理费生成任务结束");
//    }
//}
//
