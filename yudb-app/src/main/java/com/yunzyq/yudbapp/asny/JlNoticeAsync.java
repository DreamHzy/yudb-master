package com.yunzyq.yudbapp.asny;

import com.yunzyq.yudbapp.dao.*;
import com.yunzyq.yudbapp.dao.model.*;
import com.yunzyq.yudbapp.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JlNoticeAsync {

    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;
    @Resource
    PaymentOrderPayMapper paymentOrderPayMapper;
    @Resource
    MerchantStoreMapper merchantStoreMapper;
    @Resource
    MerchantStoreCloudSchoolMapper merchantStoreCloudSchoolMapper;
    @Resource
    AgentAreaPaymentOrderPayMapper agentAreaPaymentOrderPayMapper;
    @Resource
    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;
    @Resource
    MerchantAgentAreaMapper merchantAgentAreaMapper;

    @Async
    public void callBack(PaymentOrderPay paymentOrderPay) {
        //查询订单是否存在
        PaymentOrderPay paymentOrderPayNew = paymentOrderPayMapper.queryByOrderNo(paymentOrderPay);
        if (paymentOrderPayNew != null && paymentOrderPayNew.getStatus() == 2) {//只有订单存在切是未支付的状态才进行下一步操作
            paymentOrderPayNew.setTransactionId(paymentOrderPay.getTransactionId());
            paymentOrderPayNew.setChannelOrderNo(paymentOrderPay.getChannelOrderNo());
            PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.queryByOrderNO(paymentOrderPayNew.getPaymentOrderMasterId() + "");
            if (paymentOrderMaster != null) {
                MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(paymentOrderMaster.getMerchantStoreId());
                if (merchantStore.getServiceExpireTime() == null) {
                    return;
                }
                //如果是管理费要对门店管理字段进行操作
                if (paymentOrderMaster.getPaymentTypeId() == 1) {
                    merchantStore.setAlreadyManagementExpense(paymentOrderMaster.getMoney());
                    Date newTime = DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
                    merchantStore.setServiceExpireTime(newTime);
                    merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
                    //如果管理费大缴纳标准2w
                    if (merchantStore.getManagementExpense().compareTo(new BigDecimal(20000)) > -1) {//说明用户的管理费金额包含云学堂的
                        //查询这个用户总共有多少个云学堂账户
                        List<MerchantStoreCloudSchool> merchantStoreCloudSchoolList = merchantStoreCloudSchoolMapper.queryByMerChantStoreId(merchantStore.getId());
                        if (merchantStoreCloudSchoolList.size() > 0) {
                            merchantStoreCloudSchoolList.stream().forEach(merchantStoreCloudSchool -> {
                                Date newTime1 = DateUtil.endTime("1年后", merchantStoreCloudSchool.getEndTime());
                                merchantStoreCloudSchool.setEndTime(newTime1);
                                merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
                            });
                        }
                    }
                }
                paymentOrderMaster.setPayTime(new Date());
                paymentOrderMaster.setFees(paymentOrderPayNew.getFees());
                paymentOrderMaster.setPayMoney(paymentOrderMaster.getPayMoney().add(paymentOrderPayNew.getTotalMoney()));
                paymentOrderPayNew.setStatus(3);
                paymentOrderPayNew.setUpdateTime(new Date());
                paymentOrderPayNew.setPayTime(new Date());
                if (paymentOrderMaster.getMoney().equals(paymentOrderMaster.getPayMoney())) {
                    paymentOrderMaster.setStatus(2);
                }
                //对支付订单进行操作
                paymentOrderMasterMapper.updateByPrimaryKeySelective(paymentOrderMaster);
                paymentOrderPayMapper.updateByPrimaryKeySelective(paymentOrderPayNew);
            }
        }
    }

    public void agentJlCallBack(AgentAreaPaymentOrderPay paymentOrderPay) {
        //查询订单是否存在
        AgentAreaPaymentOrderPay paymentOrderPayNew = agentAreaPaymentOrderPayMapper.queryByOrderNo(paymentOrderPay);
        if (paymentOrderPayNew != null && paymentOrderPayNew.getStatus() == 2) {//只有订单存在切是未支付的状态才进行下一步操作
            paymentOrderPayNew.setTransactionId(paymentOrderPay.getTransactionId());
            paymentOrderPayNew.setChannelOrderNo(paymentOrderPay.getChannelOrderNo());
            AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = agentAreaPaymentOrderMasterMapper.queryByOrderNO(paymentOrderPayNew.getPaymentOrderMasterId() + "");
            if (agentAreaPaymentOrderMaster != null) {
                MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.queryByUid(agentAreaPaymentOrderMaster.getUid());
                Date newTime = DateUtil.endTime("1年后", merchantAgentArea.getServiceExpireTime());
                merchantAgentArea.setAlreadyManagementExpense(paymentOrderPayNew.getTotalMoney());
                merchantAgentArea.setServiceExpireTime(newTime);
                agentAreaPaymentOrderMaster.setPayTime(new Date());
                agentAreaPaymentOrderMaster.setFees(paymentOrderPayNew.getFees());
                agentAreaPaymentOrderMaster.setPayMoney(agentAreaPaymentOrderMaster.getPayMoney().add(paymentOrderPayNew.getTotalMoney()));
                paymentOrderPayNew.setStatus(3);
                paymentOrderPayNew.setUpdateTime(new Date());
                paymentOrderPayNew.setPayTime(new Date());
                if (agentAreaPaymentOrderMaster.getMoney().equals(agentAreaPaymentOrderMaster.getPayMoney())) {
                    agentAreaPaymentOrderMaster.setStatus(2);
                }
                //对支付订单进行操作
                agentAreaPaymentOrderMasterMapper.updateByPrimaryKeySelective(agentAreaPaymentOrderMaster);
                agentAreaPaymentOrderPayMapper.updateByPrimaryKeySelective(paymentOrderPayNew);
                merchantAgentAreaMapper.updateByPrimaryKeySelective(merchantAgentArea);
            }


        }
    }
}
