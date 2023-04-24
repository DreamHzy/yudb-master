package com.ynzyq.yudbadmin.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.ynzyq.yudbadmin.api.excel.enums.StatusTwoEnum;
import com.ynzyq.yudbadmin.dao.business.dao.*;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.ManagementOrderExpireTimeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class NewManagementOrderTask {

    @Resource
    MerchantStoreMapper merchantStoreMapper;

    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;

    //    @Scheduled(cron = "0 0 1 1 * ?")
    @Scheduled(cron = "0 0 0 1/1 * ?")
//    @Scheduled(cron = "*/60 * * * * ?")//每五分 测试用`
    public void newManagementOrderJob() {
        log.info("管理费生成任务开始");
        // 查询该收款月份的门店
//        int month = DateUtil.month(new Date()) + 1;
        List<ManagementOrderExpireTimeVo> merchantStores = merchantStoreMapper.queryStoreByCollectionMonth();
        if (merchantStores.size() > 0) {
            List<PaymentOrderMaster> noExamineList = Lists.newArrayList();
//            DateUtil.endOfMonth(DateUtil.parse(DateUtil.today(), "yyyy-MM-dd"));
//            DateTime dateTime = DateUtil.parse(DateUtil.format(DateUtil.endOfMonth(new Date()), "yyyy-MM-dd"), "yyyy-MM-dd");
            merchantStores.forEach(merchantStore -> {
                int count = paymentOrderMasterMapper.queryExistOrderCount(Integer.parseInt(merchantStore.getMerchantStoreId()));
                if (count > 0) {
                    log.info("门店【" + merchantStore.getMerchantStoreId() + "】授权号【" + merchantStore.getMerchantStoreUid() + "】已生成管理费，无需重复生成");
                    return;
                }
//                Date newTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
//                Date serviceStartTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1天后", merchantStore.getServiceExpireTime());
//                Date serviceEndTime = newTime;
                PaymentOrderMaster paymentOrderMaster = new PaymentOrderMaster();
                String orderNo = "MDGLF" + "-" + merchantStore.getMerchantStoreUid() + "-" + DateUtil.format(new Date(), "yyyyMMdd") + "-" + DateUtil.format(merchantStore.getServiceExpireTime(), "yyyyMMdd");
                paymentOrderMaster.setOrderNo(orderNo);
                paymentOrderMaster.setMerchantId(Integer.valueOf(merchantStore.getMerchantId()));
                paymentOrderMaster.setMerchantName(merchantStore.getMerchantName());
                paymentOrderMaster.setMerchantStoreId(Integer.valueOf(merchantStore.getMerchantStoreId()));
                paymentOrderMaster.setMerchantStoreName(merchantStore.getMerchantStoreName());
                paymentOrderMaster.setMerchantStoreUid(merchantStore.getMerchantStoreUid());
                paymentOrderMaster.setMerchantStoreMobile(merchantStore.getMerchantStoreMobile());
                paymentOrderMaster.setRegionalManagerId(Integer.valueOf(merchantStore.getRegionalManagerId()));
                paymentOrderMaster.setRegionalManagerName(merchantStore.getRegionalManagerName());
                paymentOrderMaster.setRegionalManagerMobile(merchantStore.getRegionalManagerMobile());
                paymentOrderMaster.setPaymentTypeId(1);
                paymentOrderMaster.setPaymentTypeName("管理费");
                paymentOrderMaster.setRemark("管理费");
                paymentOrderMaster.setType(1);
                paymentOrderMaster.setSend(2);
                paymentOrderMaster.setExpireTime(merchantStore.getServiceExpireTime());
                paymentOrderMaster.setStatus(1);
                paymentOrderMaster.setDeleted(false);
                paymentOrderMaster.setCreateTime(new Date());
                paymentOrderMaster.setExamineNum(2);
                paymentOrderMaster.setMoney(merchantStore.getMoney());
                paymentOrderMaster.setPayMoney(BigDecimal.ZERO);
                paymentOrderMaster.setPayType(1);
                paymentOrderMaster.setCity(merchantStore.getCity());
                paymentOrderMaster.setProvince(merchantStore.getProvince());
                paymentOrderMaster.setArea(merchantStore.getArea());
                paymentOrderMaster.setAdjustmentCount(0);
                paymentOrderMaster.setAddress(merchantStore.getAddress());
                paymentOrderMaster.setPaymentStandardMoney(paymentOrderMaster.getMoney());
                paymentOrderMaster.setCycle(DateUtil.format(new Date(), "yyyy-MM-dd") + "~" + DateUtil.format(merchantStore.getServiceExpireTime(), "yyyy-MM-dd"));
                paymentOrderMaster.setIsPush(0);
                paymentOrderMaster.setServiceStartTime(merchantStore.getServiceExpireTime());
                paymentOrderMaster.setServiceEndTime(com.ynzyq.yudbadmin.util.DateUtil.endTime("1年后", merchantStore.getServiceExpireTime()));
                paymentOrderMaster.setIsPush(0);
                paymentOrderMaster.setIsChange(2);
                // 在营、迁址、暂停经营直接推送区域经理，无需审核
                paymentOrderMaster.setExamine(1);
                paymentOrderMaster.setIsPublish(1);
                noExamineList.add(paymentOrderMaster);
            });
            // 添加非审核订单
            if (noExamineList.size() > 0) {
                paymentOrderMasterMapper.insertList(noExamineList);
            }
        }
        log.info("管理费生成任务结束");
    }

}

