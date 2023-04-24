//package com.ynzyq.yudbadmin.task;
//
//import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreMapper;
//import com.ynzyq.yudbadmin.dao.business.dao.PaymentOrderMasterMapper;
//import com.ynzyq.yudbadmin.dao.business.dao.PaymentTypeMapper;
//import com.ynzyq.yudbadmin.dao.business.model.PaymentOrderMaster;
//import com.ynzyq.yudbadmin.dao.business.model.PaymentType;
//import com.ynzyq.yudbadmin.dao.business.vo.MerchantLinkOrderVo;
//import com.ynzyq.yudbadmin.util.PlatformCodeUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Component
//@Slf4j
//public class MerchantLinkOrderTask {
//    @Resource
//    MerchantStoreMapper merchantStoreMapper;
//    @Resource
//    PaymentOrderMasterMapper paymentOrderMasterMapper;
//    @Resource
//    PaymentTypeMapper paymentTypeMapper;
//
//    @Scheduled(cron = "0 0 1 * * ?")
////    @Scheduled(cron = "*/10 * * * * ?")//每五分 测试用`
//    public void merchantLinkOrderJob() {
//        PaymentType paymentType = paymentTypeMapper.queryPayTypeList(2 + "");
//        if (paymentType != null) {
//            log.info("商户通费用生成任务开始");
//            //查询管理费低于2万以下的，且商户通还有30天到期的
//            List<MerchantLinkOrderVo> merchantStoreList = merchantStoreMapper.merchantLinkOrderVo();
//            if (merchantStoreList.size() > 0) {
//                List<PaymentOrderMaster> merchantStoresNew = new ArrayList<>();
//                //查询已经产生了商户通费用，且未支付的账号
//                List<PaymentOrderMaster> alerdOrder = paymentOrderMasterMapper.queryByPayTypeIdAndStatus(2, 1);
//                merchantStoreList.stream().forEach(
//                        merchantStores -> {
//                            merchantStores.setStatus(0);
//                            alerdOrder.stream().forEach(
//                                    paymentOrderMaster1 -> {
//                                        if (merchantStores.getMerchantStoreId().equals(paymentOrderMaster1.getMerchantStoreId() + "")) {
//                                            merchantStores.setStatus(1);
//                                        }
//                                    }
//                            );
//                            if (merchantStores.getStatus() == 0) {
//                                PaymentOrderMaster paymentOrderMaster = new PaymentOrderMaster();
//                                paymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));
//                                paymentOrderMaster.setMerchantId(Integer.valueOf(merchantStores.getMerchantId()));
//                                paymentOrderMaster.setMerchantName(merchantStores.getMerchantName());
//                                paymentOrderMaster.setMerchantStoreId(Integer.valueOf(merchantStores.getMerchantStoreId()));
//                                paymentOrderMaster.setMerchantStoreName(merchantStores.getMerchantStoreName());
//                                paymentOrderMaster.setMerchantStoreUid(merchantStores.getMerchantStoreUid());
//                                paymentOrderMaster.setMerchantStoreMobile(merchantStores.getMerchantStoreMobile());
//                                paymentOrderMaster.setRegionalManagerId(Integer.valueOf(merchantStores.getRegionalManagerId()));
//                                paymentOrderMaster.setRegionalManagerName(merchantStores.getRegionalManagerName());
//                                paymentOrderMaster.setRegionalManagerMobile(merchantStores.getRegionalManagerMobile());
//                                paymentOrderMaster.setPaymentTypeId(2);
//                                paymentOrderMaster.setPaymentTypeName("商户通");
//                                paymentOrderMaster.setType(1);
//                                paymentOrderMaster.setRemark("商户通");
//                                paymentOrderMaster.setSend(2);
//                                paymentOrderMaster.setExpireTime(merchantStores.getMerchantLinkEndTime());
//                                paymentOrderMaster.setStatus(1);
//                                paymentOrderMaster.setExamine(1);
//                                paymentOrderMaster.setDeleted(false);
//                                paymentOrderMaster.setCreateTime(new Date());
//                                paymentOrderMaster.setExamineNum(2);
//                                paymentOrderMaster.setMoney(paymentType.getMoney());
//                                merchantStoresNew.add(paymentOrderMaster);
//                            }
//                        }
//                );
//                if (merchantStoresNew.size() > 0) {
//                    paymentOrderMasterMapper.insertList(merchantStoresNew);
//                }
//            }
//
//            log.info("商户通费用生成任务结束");
//        }
//
//    }
//}
