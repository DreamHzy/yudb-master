//package com.ynzyq.yudbadmin.task;
//
//import com.ynzyq.yudbadmin.dao.business.dao.*;
//import com.ynzyq.yudbadmin.dao.business.model.MonitorUser;
//import com.ynzyq.yudbadmin.dao.business.model.PaymentOrderMaster;
//import com.ynzyq.yudbadmin.dao.business.model.PaymentType;
//import com.ynzyq.yudbadmin.dao.business.vo.MerchantStoreCloudSchoolVo;
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
//
//@Component
//@Slf4j
//public class MerchantStoreCloudSchoolTask {
//    @Resource
//    MerchantStoreMapper merchantStoreMapper;
//    @Resource
//    PaymentOrderMasterMapper paymentOrderMasterMapper;
//    @Resource
//    PaymentTypeMapper paymentTypeMapper;
//    @Resource
//    MonitorUserMapper monitorUserMapper;
//
//    @Scheduled(cron = "0 0 1 * * ?")
////    @Scheduled(cron = "*/10 * * * * ?")//每五分 测试用`
//    public void merchantLinkOrderJob() {
//        log.info("云学堂费用生成任务开始");
//        List<PaymentOrderMaster> merchantStoresNew = new ArrayList<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//
//        PaymentType paymentType = paymentTypeMapper.queryPayTypeList(3 + "");
//        if (paymentType != null) {
//            //查询管理费低于2万以下的，且云学堂还有30天到期的
//            List<MerchantStoreCloudSchoolVo> merchantStoreList = merchantStoreMapper.merchantStoreCloudSchoolVo();
//            if (merchantStoreList.size() > 0) {
//                List<PaymentOrderMaster> alerdOrder = paymentOrderMasterMapper.queryByPayTypeIdAndStatus(3, 1);
//                merchantStoreList.stream().forEach(
//                        merchantStores -> {
//                            if (paymentType.getMoney().compareTo(BigDecimal.ZERO) == 0) {
//                                return;
//                            }
//                            merchantStores.setStatus(0);
//                            alerdOrder.stream().forEach(
//                                    paymentOrderMaster1 -> {
//                                        if (merchantStores.getId().equals(paymentOrderMaster1.getOtherId() + "")) {
//                                            merchantStores.setStatus(1);
//                                        }
//                                    }
//                            );
//                            //并且需要排除已经生成了商户通订单待支付的用户
//                            if (merchantStores.getStatus() == 0) {
//                                Date newTime1 = DateUtil.endTime("1年后", merchantStores.getEndTime());
//
//                                PaymentOrderMaster paymentOrderMaster = new PaymentOrderMaster();
//                                paymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));
//                                paymentOrderMaster.setOtherId(Integer.valueOf(merchantStores.getId()));
//                                paymentOrderMaster.setMerchantId(Integer.valueOf(merchantStores.getMerchantId()));
//                                paymentOrderMaster.setMerchantName(merchantStores.getMerchantName());
//                                paymentOrderMaster.setMerchantStoreId(Integer.valueOf(merchantStores.getMerchantStoreId()));
//                                paymentOrderMaster.setMerchantStoreName(merchantStores.getMerchantStoreName());
//                                paymentOrderMaster.setMerchantStoreUid(merchantStores.getMerchantStoreUid());
//                                paymentOrderMaster.setMerchantStoreMobile(merchantStores.getMerchantStoreMobile());
//                                paymentOrderMaster.setRegionalManagerId(Integer.valueOf(merchantStores.getRegionalManagerId()));
//                                paymentOrderMaster.setRegionalManagerName(merchantStores.getRegionalManagerName());
//                                paymentOrderMaster.setRegionalManagerMobile(merchantStores.getRegionalManagerMobile());
//                                paymentOrderMaster.setPaymentTypeId(3);
//                                paymentOrderMaster.setPaymentTypeName("云学堂");
//                                paymentOrderMaster.setRemark("云学堂");
//                                paymentOrderMaster.setType(1);
//                                paymentOrderMaster.setSend(2);
//                                paymentOrderMaster.setExpireTime(merchantStores.getEndTime());
//                                paymentOrderMaster.setStatus(1);
//                                paymentOrderMaster.setExamine(1);
//                                paymentOrderMaster.setDeleted(false);
//                                paymentOrderMaster.setCreateTime(new Date());
//                                paymentOrderMaster.setExamineNum(2);
//                                paymentOrderMaster.setMoney(paymentType.getMoney());
//                                paymentOrderMaster.setPayType(1);
//
//                                paymentOrderMaster.setAddress(merchantStores.getAddress());
//                                paymentOrderMaster.setCity(merchantStores.getCity());
//                                paymentOrderMaster.setProvince(merchantStores.getProvince());
//                                paymentOrderMaster.setArea(merchantStores.getArea());
//                                paymentOrderMaster.setAdjustmentCount(0);
//                                paymentOrderMaster.setPaymentStandardMoney(paymentOrderMaster.getMoney());
//                                paymentOrderMaster.setCycle(sdf.format(merchantStores.getEndTime()) + "~" + sdf.format(newTime1));
//
//
//                                merchantStoresNew.add(paymentOrderMaster);
//                            }
//                        }
//                );
//                if (merchantStoresNew.size() > 0) {
//                    paymentOrderMasterMapper.insertList(merchantStoresNew);
//                }
//
//            }
//        }
//        List<MonitorUser> monitorUserList = monitorUserMapper.queryByStatus(1);
//        monitorUserList.stream().forEach(
//                monitorUser -> {
//                    SmsUtil.notice(monitorUser.getPhone(), "云学堂", merchantStoresNew.size());
//                }
//        );
//        log.info("云学堂费用生成任务结束");
//    }
//}
