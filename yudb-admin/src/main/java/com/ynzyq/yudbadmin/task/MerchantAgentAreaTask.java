//package com.ynzyq.yudbadmin.task;
//
//import com.ynzyq.yudbadmin.dao.business.dao.*;
//import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderExamine;
//import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderExamineDeail;
//import com.ynzyq.yudbadmin.dao.business.model.AgentAreaPaymentOrderMaster;
//import com.ynzyq.yudbadmin.dao.business.model.MonitorUser;
//import com.ynzyq.yudbadmin.dao.business.vo.AgentOrderExpireTimeVo;
//import com.ynzyq.yudbadmin.dao.business.vo.AgentOrderJoinVo;
//import com.ynzyq.yudbadmin.util.DateUtil;
//import com.ynzyq.yudbadmin.util.PlatformCodeUtil;
//import com.ynzyq.yudbadmin.util.sms.SmsUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
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
//public class MerchantAgentAreaTask {
//
//    @Resource
//    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;
//    @Resource
//    MonitorUserMapper monitorUserMapper;
//
//    @Resource
//    AgentAreaPaymentOrderExamineMapper agentAreaPaymentOrderExamineMapper;
//
//    @Resource
//    AgentAreaPaymentOrderExamineDeailMapper agentAreaPaymentOrderExamineDeailMapper;
//
//    @Resource
//    ApproveProcessMapper approveProcessMapper;
//
//    @Scheduled(cron = "0 0 1 * * ?")
////    @Scheduled(cron = "*/10 * * * * ?")//每五分 测试用
//    public void merchantAgentAreaJob() {
//        log.info("代理商管理费生成任务开始");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        List<AgentAreaPaymentOrderMaster> merchantStoresNew = new ArrayList<>();
//        List<AgentOrderExpireTimeVo> agentOrderExpireTimeVoList = agentAreaPaymentOrderMasterMapper.queryByExpireTime();
//        if (agentOrderExpireTimeVoList.size() > 0) {
//            //并且排除已经有生成待支付管理费的代理
//            List<AgentOrderJoinVo> managementOrderJoinVoList = agentAreaPaymentOrderMasterMapper.queryManagementOrderJoinVoList(agentOrderExpireTimeVoList);
//
//            agentOrderExpireTimeVoList.stream().forEach(
//                    agentOrderExpireTimeVo -> {
//                        if (agentOrderExpireTimeVo.getMoney().compareTo(BigDecimal.ZERO) == 0) {
//                            return;
//                        }
//                        if (StringUtils.isBlank(agentOrderExpireTimeVo.getRegionalManagerId())) {
//                            return;
//                        }
//                        // 代理权是否生效：1：是，2：否
//                        // 代理权不生效时不产生管理费订单
//                        if (agentOrderExpireTimeVo.getIsEffect().equals(2)) {
//                            return;
//                        }
//                        agentOrderExpireTimeVo.setStatus(0);
//                        managementOrderJoinVoList.stream().forEach(
//                                managementOrderJoinVo -> {
//                                    if (agentOrderExpireTimeVo.getUid().equals(managementOrderJoinVo.getId())) {//如果是相同的门店对比金额
//                                        agentOrderExpireTimeVo.setStatus(1);
//                                    }
//                                }
//                        );
//                        if (agentOrderExpireTimeVo.getStatus() == 0) {
//                            Date newTime1 = DateUtil.endTime("1年后", agentOrderExpireTimeVo.getServiceExpireTime());
//                            AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = new AgentAreaPaymentOrderMaster();
//                            agentAreaPaymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));
//                            agentAreaPaymentOrderMaster.setMerchantId(Integer.valueOf(agentOrderExpireTimeVo.getMerchantId()));
//                            agentAreaPaymentOrderMaster.setMerchantName(agentOrderExpireTimeVo.getMerchantName());
//                            agentAreaPaymentOrderMaster.setUid(agentOrderExpireTimeVo.getUid());
//                            agentAreaPaymentOrderMaster.setRegionalManagerId(Integer.valueOf(agentOrderExpireTimeVo.getRegionalManagerId()));
//                            agentAreaPaymentOrderMaster.setRegionalManagerName(agentOrderExpireTimeVo.getRegionalManagerName());
//                            agentAreaPaymentOrderMaster.setRegionalManagerMobile(agentOrderExpireTimeVo.getRegionalManagerMobile());
//                            agentAreaPaymentOrderMaster.setPaymentTypeId(1);
//                            agentAreaPaymentOrderMaster.setPaymentTypeName("管理费");
//                            agentAreaPaymentOrderMaster.setRemark("管理费");
//                            agentAreaPaymentOrderMaster.setType(1);
////                            agentAreaPaymentOrderMaster.setMerchantMobile();
//                            agentAreaPaymentOrderMaster.setSend(2);
//                            agentAreaPaymentOrderMaster.setExpireTime(agentOrderExpireTimeVo.getServiceExpireTime());
//                            agentAreaPaymentOrderMaster.setStatus(1);
//                            agentAreaPaymentOrderMaster.setExamine(2);
//                            agentAreaPaymentOrderMaster.setDeleted(false);
//                            agentAreaPaymentOrderMaster.setCreateTime(new Date());
//                            agentAreaPaymentOrderMaster.setExamineNum(2);
//                            agentAreaPaymentOrderMaster.setMoney(agentOrderExpireTimeVo.getMoney());
//
//
//                            agentAreaPaymentOrderMaster.setCity(agentOrderExpireTimeVo.getCity());
//                            agentAreaPaymentOrderMaster.setProvince(agentOrderExpireTimeVo.getProvince());
//                            agentAreaPaymentOrderMaster.setArea(agentOrderExpireTimeVo.getArea());
//                            agentAreaPaymentOrderMaster.setAdjustmentCount(0);
//                            agentAreaPaymentOrderMaster.setPaymentStandardMoney(agentOrderExpireTimeVo.getMoney());
//                            agentAreaPaymentOrderMaster.setCycle(sdf.format(agentOrderExpireTimeVo.getServiceExpireTime()) + "~" + sdf.format(newTime1));
////
//                            merchantStoresNew.add(agentAreaPaymentOrderMaster);
//                        }
//                    }
//            );
//            if (merchantStoresNew.size() > 0) {
//                agentAreaPaymentOrderMasterMapper.insertList(merchantStoresNew);
//
//                merchantStoresNew.forEach(agentAreaPaymentOrderMaster -> {
//                    //缴费审核订单
//                    AgentAreaPaymentOrderExamine agentAreaPaymentOrderExamine = new AgentAreaPaymentOrderExamine();
//                    agentAreaPaymentOrderExamine.setPaymentTypeId(agentAreaPaymentOrderMaster.getPaymentTypeId());
//                    agentAreaPaymentOrderExamine.setPaymentOrderMasterId(agentAreaPaymentOrderMaster.getId());
//                    agentAreaPaymentOrderExamine.setUid(agentAreaPaymentOrderMaster.getUid());
//                    agentAreaPaymentOrderExamine.setPaymentTypeName(agentAreaPaymentOrderMaster.getPaymentTypeName());
//                    agentAreaPaymentOrderExamine.setNewMoney(agentAreaPaymentOrderMaster.getMoney());
//                    agentAreaPaymentOrderExamine.setExamineType(3);
//                    agentAreaPaymentOrderExamine.setExamine(1);
//                    agentAreaPaymentOrderExamine.setStatus(1);
//                    agentAreaPaymentOrderExamine.setApplyName("系统自动生成");
//                    agentAreaPaymentOrderExamine.setMsg("新订单审核:" + agentAreaPaymentOrderMaster.getPaymentTypeName() + agentAreaPaymentOrderMaster.getMoney() + "元");
//                    agentAreaPaymentOrderExamine.setRemark("系统自动生成");
//                    agentAreaPaymentOrderExamine.setDeleted(false);
//                    agentAreaPaymentOrderExamine.setCreateTime(new Date());
//                    agentAreaPaymentOrderExamine.setStep(1);
//                    agentAreaPaymentOrderExamineMapper.insertSelective(agentAreaPaymentOrderExamine);
//                    //缴费审核订单明细
//                    AgentAreaPaymentOrderExamineDeail agentAreaPaymentOrderExamineDeail = new AgentAreaPaymentOrderExamineDeail();
//                    agentAreaPaymentOrderExamineDeail.setPaymentOrderExamineId(agentAreaPaymentOrderExamine.getId());
//                    agentAreaPaymentOrderExamineDeail.setExamine(1);
//                    agentAreaPaymentOrderExamineDeail.setStatus(1);
//                    agentAreaPaymentOrderExamineDeail.setCreateTime(new Date());
//                    agentAreaPaymentOrderExamineDeail.setDeleted(0);
//                    agentAreaPaymentOrderExamineDeail.setStep(1);
//                    agentAreaPaymentOrderExamineDeail.setType(1);
//                    // 查询该阶段的审核人
//                    List<String> approveNames = approveProcessMapper.getApproveName(2, agentAreaPaymentOrderExamine.getExamineType(), agentAreaPaymentOrderExamineDeail.getStep(), agentAreaPaymentOrderExamineDeail.getType());
//                    String approveName = approveNames.stream().collect(Collectors.joining(","));
//                    agentAreaPaymentOrderExamineDeail.setApproveName(approveName);
//                    agentAreaPaymentOrderExamineDeailMapper.insertSelective(agentAreaPaymentOrderExamineDeail);
//                });
//            }
//            List<MonitorUser> monitorUserList = monitorUserMapper.queryByStatus(1);
//            monitorUserList.stream().forEach(
//                    monitorUser -> {
//                        log.info("进入监控短信模块");
//                        SmsUtil.notice(monitorUser.getPhone(), "管理费", merchantStoresNew.size());
//                    }
//            );
//            log.info("管理费生成任务结束");
//        }
//    }
//}
