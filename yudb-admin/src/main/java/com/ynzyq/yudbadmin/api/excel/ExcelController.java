package com.ynzyq.yudbadmin.api.excel;

import cn.hutool.core.date.DateField;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ynzyq.yudbadmin.api.excel.dto.*;
import com.ynzyq.yudbadmin.api.excel.mapper.ExcelMapper;
import com.ynzyq.yudbadmin.api.excel.po.*;
import com.ynzyq.yudbadmin.dao.business.dao.*;
import com.ynzyq.yudbadmin.dao.business.dto.OrderExcelDto;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import com.ynzyq.yudbadmin.util.DateUtil;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import com.ynzyq.yudbadmin.util.PlatformCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author wj
 * @Date 2021/10/28
 */
@Slf4j
@RequestMapping("/Test")
@RestController
public class ExcelController {

    @Resource
    DepartmentMapper departmentMapper;

    @Resource
    RegionalManagerMapper regionalManagerMapper;

    @Resource
    RegionalManagerDepartmentMapper regionalManagerDepartmentMapper;

    @Resource
    MerchantMapper merchantMapper;

    @Resource
    ExcelMapper excelMapper;

    @Resource
    MerchantStoreMapper merchantStoreMapper;

    @Resource
    MerchantStoreRegionalManagerMapper merchantStoreRegionalManagerMapper;

    @Resource
    MerchantAgentAreaMapper merchantAgentAreaMapper;

    @Resource
    MerchantAgentAreaRegionalManagerMapper merchantAgentAreaRegionalManagerMapper;

    @Resource
    MerchantStoreCloudSchoolMapper merchantStoreCloudSchoolMapper;

    @Resource
    MerchantAgentAreaDetailMapper merchantAgentAreaDetailMapper;

    @Resource
    AgentAreaPaymentOrderMasterMapper agentAreaPaymentOrderMasterMapper;
    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;

    @Resource
    MerchantStoreMappingAreaMapper merchantStoreMappingAreaMapper;

    @Resource
    ApproveProcessMapper approveProcessMapper;

    /**
     * 修改门店其他信息
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/updatePayOrder")
    public String updatePayOrder(MultipartFile file) {
        List<PayOrderDTO> list = ExcelUtils.readExcel("", PayOrderDTO.class, file);
        PaymentOrderMaster query;
        PaymentOrderMaster paymentOrderMaster;
        PaymentOrderMaster updatePaymentOrderMaster;
        for (PayOrderDTO payOrderDTO : list) {
            query = new PaymentOrderMaster();
            query.setOrderNo(payOrderDTO.getOrderNo());
            query.setMerchantStoreUid(payOrderDTO.getUid());
            paymentOrderMaster = paymentOrderMasterMapper.selectOne(query);
            if (paymentOrderMaster != null) {
                updatePaymentOrderMaster = new PaymentOrderMaster();
                updatePaymentOrderMaster.setId(paymentOrderMaster.getId());
                updatePaymentOrderMaster.setMoney(new BigDecimal(payOrderDTO.getMoney()));
                updatePaymentOrderMaster.setRemark(payOrderDTO.getRemark());
                updatePaymentOrderMaster.setUpdateTime(new Date());
                paymentOrderMasterMapper.updateByPrimaryKeySelective(updatePaymentOrderMaster);
            }
        }
        return "success";
    }

    /**
     * 修改门店其他信息
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/yunxuetang")
    public String yunxuetang(MultipartFile file) {
        List<OrderExcelDto> list = ExcelUtils.readExcel("", OrderExcelDto.class, file);
        list.forEach(orderExcelDto -> {
            if (orderExcelDto.getUid().contains(".")) {
                int i = orderExcelDto.getUid().indexOf(".");
                orderExcelDto.setUid(StringUtils.substring(orderExcelDto.getUid(), 0, i));
            }
            log.info("{}", orderExcelDto.getUid());
        });
        return "success";
    }

    /**
     * 修改门店其他信息
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/updateOrder")
    public String updateOrder(MultipartFile file) {
        List<UpdateOrderDTO> list = ExcelUtils.readExcel("", UpdateOrderDTO.class, file);
        list.forEach(updateOrderDTO -> {
            Example example = new Example(PaymentOrderMaster.class);
            example.createCriteria()
                    .andEqualTo("merchantStoreUid", updateOrderDTO.getUid())
//                    .andEqualTo("money", updateOrderDTO.getMoney())
                    .andEqualTo("status", 1)
                    .andEqualTo("expireTime", updateOrderDTO.getExpireTime());
            PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.selectOneByExample(example);
            if (paymentOrderMaster != null) {
                PaymentOrderMaster updatePaymentOrderMaster = new PaymentOrderMaster();
                updatePaymentOrderMaster.setId(paymentOrderMaster.getId());
                updatePaymentOrderMaster.setExpireTime(cn.hutool.core.date.DateUtil.parse(updateOrderDTO.getExpireTime2(), "yyyy-MM-dd"));
                paymentOrderMasterMapper.updateByPrimaryKeySelective(updatePaymentOrderMaster);
            }
        });
        return "success";
    }

    /**
     * 修改门店其他信息
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/updateOtherValue")
    public String updateOtherValue(MultipartFile file) {
        List<UpdateOtherValueDTO> list = ExcelUtils.readExcel("", UpdateOtherValueDTO.class, file);
        List<MerchantStore> stores = merchantStoreMapper.selectAll();
        Map<String, MerchantStore> map = Maps.newHashMap();
        stores.forEach(merchantStore -> {
            map.put(merchantStore.getUid(), merchantStore);
        });
        list.forEach(updateOtherValueDTO -> {
            if (map.keySet().contains(updateOtherValueDTO.getUid())) {
                MerchantStore merchantStore = map.get(updateOtherValueDTO.getUid());
                MerchantStore updateMerchantStore = new MerchantStore();
                updateMerchantStore.setId(merchantStore.getId());
                if (StringUtils.isNotBlank(updateOtherValueDTO.getSignatory())) {
                    updateMerchantStore.setSignatory(updateOtherValueDTO.getSignatory());
                }
                if (StringUtils.isNotBlank(updateOtherValueDTO.getOpenAgainTime())) {
                    updateMerchantStore.setOpenAgainTime(cn.hutool.core.date.DateUtil.parse(updateOtherValueDTO.getOpenAgainTime(), "yyyy-MM-dd"));
                }
                if (StringUtils.isNotBlank(updateOtherValueDTO.getHllCode())) {
                    updateMerchantStore.setHllCode(updateOtherValueDTO.getHllCode());
                }
                if (StringUtils.isNotBlank(updateOtherValueDTO.getMtId())) {
                    updateMerchantStore.setMtId(updateOtherValueDTO.getMtId());
                }
                if (StringUtils.isNotBlank(updateOtherValueDTO.getElmId())) {
                    updateMerchantStore.setElmId(updateOtherValueDTO.getElmId());
                }
                if (StringUtils.isNotBlank(updateOtherValueDTO.getDzdpId())) {
                    updateMerchantStore.setDzdpId(updateOtherValueDTO.getDzdpId());
                }
                if (StringUtils.isNotBlank(updateOtherValueDTO.getType())) {
                    if (StringUtils.equals("街边店", updateOtherValueDTO.getType())) {
                        updateMerchantStore.setType("1");
                    } else if (StringUtils.equals("商场店", updateOtherValueDTO.getType())) {
                        updateMerchantStore.setType("2");
                    } else if (StringUtils.equals("档口店", updateOtherValueDTO.getType())) {
                        updateMerchantStore.setType("3");
                    }
                }
                if (StringUtils.isNotBlank(updateOtherValueDTO.getIdNumber())) {
                    updateMerchantStore.setIdNumber(updateOtherValueDTO.getIdNumber());
                }
                merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore);
            }
        });
        return "success";
    }


    /**
     * 修改收款月份
     *
     * @return
     */
    @RequestMapping("/excel/updateCollectionMonth")
    public String updateCollectionMonth() {
        List<MerchantStore> stores = merchantStoreMapper.selectAll();
        List<MerchantStore> updateList = Lists.newArrayList();
        stores.forEach(merchantStore -> {
            MerchantStore updateMerchantStore = new MerchantStore();
            updateMerchantStore.setId(merchantStore.getId());
            Date serviceExpireTime = merchantStore.getServiceExpireTime();
            if (serviceExpireTime != null) {
                int month = cn.hutool.core.date.DateUtil.month(serviceExpireTime);
//                if (month == 0) {
//                    month = 12;
//                }
                updateMerchantStore.setCollectionMonth(month + 1);
            }
//            Date openTime = merchantStore.getOpenTime();
//            Date signTime = merchantStore.getSignTime();
//            if (openTime == null || signTime == null) {
//                updateMerchantStore.setDelayedOpen("未开业");
//            } else {
//                // 计算开业时间 与 签约时间天数差
//                long betweenDay = cn.hutool.core.date.DateUtil.between(cn.hutool.core.date.DateUtil.parse(cn.hutool.core.date.DateUtil.format(openTime, "yyyy-MM-dd"), "yyyy-MM-dd"), cn.hutool.core.date.DateUtil.parse(cn.hutool.core.date.DateUtil.format(signTime, "yyyy-MM-dd"), "yyyy-MM-dd"), DateUnit.DAY);
//                if (betweenDay > 365) {
//                    updateMerchantStore.setDelayedOpen("延期开业");
//                } else {
//                    updateMerchantStore.setDelayedOpen("非延期开业");
//                }
//            }
            updateList.add(updateMerchantStore);
        });
        excelMapper.updateStoreCollectionMonth(updateList);
        return "success";
    }

    /**
     * 修改缴费截止时间
     *
     * @return
     */
    @RequestMapping("/excel/updateExpireTime")
    public String updateExpireTime(MultipartFile file) {
        List<UpdateExpireTimeDTO> list = ExcelUtils.readExcel("", UpdateExpireTimeDTO.class, file);
        list.forEach(updateExpireTimeDTO -> {
            excelMapper.updateExpireTime(updateExpireTimeDTO.getExpireTime(), Integer.parseInt(updateExpireTimeDTO.getId()));
        });
        return "success";
    }

    /**
     * 阶段审核人
     *
     * @return
     */
    @RequestMapping("/excel/updateApproveName")
    public String updateApproveName() {
        List<PaymentOrderExamine> paymentOrderExamines = excelMapper.listStoreExamine();
        if (paymentOrderExamines.size() > 0) {
            paymentOrderExamines.forEach(paymentOrderExamine -> {
                List<PaymentOrderExamineDeail> paymentOrderExamineDeails = excelMapper.listStoreExamineDetail(paymentOrderExamine.getId());
                if (paymentOrderExamineDeails.size() > 0) {
                    paymentOrderExamineDeails.forEach(paymentOrderExamineDeail -> {
                        List<String> approveNames = approveProcessMapper.getApproveName(1, paymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), paymentOrderExamineDeail.getType());
                        String approveName = approveNames.stream().collect(Collectors.joining(","));
                        if (StringUtils.isNotBlank(approveName)) {
                            excelMapper.updateStoreExamineDetail(approveName, paymentOrderExamineDeail.getId());
                        }
                    });
                }
            });
        }

        List<AgentAreaPaymentOrderExamine> agentAreaPaymentOrderExamines = excelMapper.listAgentExamine1();
        if (agentAreaPaymentOrderExamines.size() > 0) {
            agentAreaPaymentOrderExamines.forEach(agentAreaPaymentOrderExamine -> {
                List<AgentAreaPaymentOrderExamineDeail> agentAreaPaymentOrderExamineDeails = excelMapper.listAgentExamineDetail(agentAreaPaymentOrderExamine.getId());
                if (agentAreaPaymentOrderExamineDeails.size() > 0) {
                    agentAreaPaymentOrderExamineDeails.forEach(agentAreaPaymentOrderExamineDeail -> {
                        List<String> approveNames = approveProcessMapper.getApproveName(2, agentAreaPaymentOrderExamine.getExamineType(), agentAreaPaymentOrderExamineDeail.getStep(), agentAreaPaymentOrderExamineDeail.getType());
                        String approveName = approveNames.stream().collect(Collectors.joining(","));
                        if (StringUtils.isNotBlank(approveName)) {
                            excelMapper.updateAgentExamineDetail(approveName, agentAreaPaymentOrderExamineDeail.getId());
                        }
                    });
                }
            });
        }
        return "success";
    }


//    /**
//     * 删除订单
//     *
//     * @return
//     */
//    @RequestMapping("/excel/deleteOrder")
//    public String deleteOrder(MultipartFile file) {
//        List<DeleteOrderDTO> list = ExcelUtils.readExcel("", DeleteOrderDTO.class, file);
//        list.forEach(deleteOrderDTO -> {
//            // 查询是否有订单
////            List<AgentAreaPaymentOrderMaster> agentAreaPaymentOrderMasters = excelMapper.listAgentOrderByUid(deleteOrderDTO.getUid());
////            if (agentAreaPaymentOrderMasters.size() > 0) {
////                agentAreaPaymentOrderMasters.forEach(agentAreaPaymentOrderMaster -> {
////                    List<AgentAreaPaymentOrderExamine> agentAreaPaymentOrderExamines = excelMapper.listAgentExamine(agentAreaPaymentOrderMaster.getId(), deleteOrderDTO.getUid());
////                    if (agentAreaPaymentOrderExamines.size() > 0) {
////                        agentAreaPaymentOrderExamines.forEach(agentAreaPaymentOrderExamine -> {
////                            excelMapper.deleteAgentExamineDetail(agentAreaPaymentOrderExamine.getId());
////                        });
////                        excelMapper.deleteAgentExamine(agentAreaPaymentOrderMaster.getId());
////                    }
////                });
////                excelMapper.deleteAgentOrder(deleteOrderDTO.getUid());
////            }
//            // 修改服务到期时间
//            MerchantAgentArea merchantAgentArea = merchantAgentAreaMapper.queryByUid(deleteOrderDTO.getUid());
////            String serviceExpireTime = cn.hutool.core.date.DateUtil.format(merchantAgentArea.getServiceExpireTime(), "yyyy-MM-dd");
////            String year = serviceExpireTime.substring(0, 4);
////            String expireTime = cn.hutool.core.date.DateUtil.format(merchantAgentArea.getExpireTime(), "yyyy-MM-dd");
////            if (StringUtils.equals(serviceExpireTime, expireTime) && (Integer.parseInt(year) < 2022)) {
//            MerchantAgentArea updateMerchantAgentArea = new MerchantAgentArea();
//            updateMerchantAgentArea.setId(merchantAgentArea.getId());
//            Date newTime = DateUtil.endTime("1年后", merchantAgentArea.getServiceExpireTime());
//            updateMerchantAgentArea.setServiceExpireTime(newTime);
//            merchantAgentAreaMapper.updateByPrimaryKeySelective(updateMerchantAgentArea);
////            }
//        });
//        return "success";
//    }


    /**
     * 修改客户状态
     *
     * @return
     */
    @RequestMapping("/excel/updateMerchant")
    public String updateMerchant() {
        List<Merchant> merchants = excelMapper.listMerchant();
        merchants.forEach(merchant -> {
            MerchantVO merchantData = excelMapper.getMerchantData(merchant.getId());
            if (merchantData.getAgentCount().equals(0) && merchantData.getStoreCount().equals(0)) {
                Merchant updateMerchant = new Merchant();
                updateMerchant.setId(merchant.getId());
                updateMerchant.setStatus(2);
                merchantMapper.updateByPrimaryKeySelective(updateMerchant);
            }
        });
        return "success";
    }


    /**
     * 修改金额
     *
     * @param response
     * @return
     */
    @RequestMapping("/excel/exportOrder")
    public void exportOrder(HttpServletResponse response) {
        List<StoreOrderDTO> storeOrderDTOS = excelMapper.listStore();
        List<StoreOrderDTO> list = new ArrayList<>();
        storeOrderDTOS.forEach(storeOrderDTO -> {
            String startTime = storeOrderDTO.getStartTime();
            String startYear = startTime.substring(0, 4);
            String endTime = storeOrderDTO.getExpireTime();
            String endYear = endTime.substring(0, 4);
            if (Integer.parseInt(startYear) <= 2020 && Integer.parseInt(endYear) >= 2020) {
                list.add(storeOrderDTO);
            }
        });
        if (list.size() > 0) {
//            ExcelUtils.writeExcel(response, list, StoreOrderDTO.class, "2020.xlsx");
            EasyExcel.write(ExcelUtil.getOutPutStream("2020", response), StoreOrderDTO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(list);
        }
    }


    /**
     * 修改金额
     *
     * @return
     */
    @RequestMapping("/excel/updateMoney")
    public String updateMoney(MultipartFile file) {
        List<MoneyDTO> list = ExcelUtils.readExcel("", MoneyDTO.class, file);
        List<MerchantStore> merchantStoreList = excelMapper.getAllMerchantStore();
        Map<String, Integer> map = Maps.newHashMap();
        merchantStoreList.forEach(merchantStore -> {
            map.put(merchantStore.getUid(), merchantStore.getId());
        });

        List<MerchantStore> updateList = Lists.newArrayList();
        list.forEach(moneyDTO -> {
            // 代理订单
            if (map.keySet().contains(moneyDTO.getUid())) {
                Integer id = map.get(moneyDTO.getUid());
                MerchantStore updateMerchantStore = new MerchantStore();
                updateMerchantStore.setId(id);
                updateMerchantStore.setFranchiseFee(new BigDecimal(moneyDTO.getFranchiseFee()));
                updateMerchantStore.setManagementExpense(new BigDecimal(moneyDTO.getManagementExpense()));
                updateMerchantStore.setBondMoney(new BigDecimal(moneyDTO.getBondMoney()));
                updateList.add(updateMerchantStore);
            }
        });
        if (updateList.size() > 0) {
            excelMapper.updateStore(updateList);
        }
        return "success";
    }

    /**
     * 线级城市
     *
     * @return
     */
    @RequestMapping("/excel/levelCity")
    public String levelCity(MultipartFile file) {
        List<LevelCityDTO> list = ExcelUtils.readExcel("", LevelCityDTO.class, file);
        List<MerchantStoreMappingArea> mappingAreaList = excelMapper.listMappingArea();
        Map<String, Integer> map = Maps.newHashMap();
        mappingAreaList.forEach(merchantStoreMappingArea -> {
            map.put(merchantStoreMappingArea.getUid() + merchantStoreMappingArea.getAgentUid(), merchantStoreMappingArea.getId());
        });

        List<MerchantStoreMappingArea> updateList = Lists.newArrayList();
        list.forEach(levelCityDTO -> {
            // 代理订单
            if (map.keySet().contains(levelCityDTO.getCode() + levelCityDTO.getAgentCode())) {
                Integer id = map.get(levelCityDTO.getCode() + levelCityDTO.getAgentCode());
                MerchantStoreMappingArea updateArea = new MerchantStoreMappingArea();
                updateArea.setId(id);
                updateArea.setLevel(levelCityDTO.getLevel());
                updateList.add(updateArea);
            }
        });
        if (updateList.size() > 0) {
            excelMapper.updateMappingArea(updateList);
        }
        return "success";
    }

    /**
     * 对账清算
     *
     * @return
     */
    @RequestMapping("/excel/account")
    public String account(MultipartFile file) {
        List<AccountDTO> list = ExcelUtils.readExcel2("", AccountDTO.class, file);
        List<OrderDTO> agentOrders = excelMapper.listAgentOrder();
        Set<String> agentOrderSet = new HashSet<>();
        agentOrders.forEach(agentOrder -> {
            agentOrderSet.add(agentOrder.getOrderNo());
        });

        List<OrderDTO> storeOrders = excelMapper.listStoreOrder();
        Set<String> storeOrderSet = new HashSet<>();
        storeOrders.forEach(storeOrder -> {
            storeOrderSet.add(storeOrder.getOrderNo());
        });
        list.forEach(accountDTO -> {
            // 代理订单
            if (agentOrderSet.contains(accountDTO.getOrderNo())) {
                excelMapper.updateAgentOrder(accountDTO.getOrderNo());
            }
            // 门店订单
            if (storeOrderSet.contains(accountDTO.getOrderNo())) {
                excelMapper.updateStoreOrder(accountDTO.getOrderNo());
            }
        });
        return "success";
    }

    /**
     * 修改otherId
     *
     * @return
     */
    @RequestMapping("/test/updateOtherId")
    public String updateOtherId() {
        List<SchoolDTO> orderList = excelMapper.listOrder();
        orderList.forEach(schoolDTO -> {
            List<Integer> ids = excelMapper.listSchool(schoolDTO.getUid());
            for (Integer id : ids) {
                if (!schoolDTO.getOtherId().equals(id)) {
//                if (schoolDTO.getOtherId().equals(id)) {
                    excelMapper.updateOrder(schoolDTO.getOtherId(), id, schoolDTO.getUid());
                }
            }
        });
        return "success";
    }

    /**
     * 省市区下拉框
     *
     * @return
     */
    @RequestMapping("/excel/updateArea")
    public String updateArea(MultipartFile file) {
        List<StoreAreaDTO> list = ExcelUtils.readExcel("", StoreAreaDTO.class, file);
        List<MerchantStore> allMerchantStore = excelMapper.getAllMerchantStore();
        Map<String, Integer> map = new HashMap<>();
        allMerchantStore.forEach(merchantStore -> {
            map.put(merchantStore.getUid(), merchantStore.getId());
        });
        for (StoreAreaDTO storeAreaDTO : list) {
            if ("未开业".equals(storeAreaDTO.getStatus())) {
                continue;
            }
            if (map.keySet().contains(storeAreaDTO.getCode().trim())) {
//                MerchantStore merchantStore = new MerchantStore();
//                merchantStore.setId(map.get(storeAreaDTO.getCode().trim()));
//                merchantStore.setProvince(storeAreaDTO.getProvince());
//                merchantStore.setCity(storeAreaDTO.getCity());
//                merchantStore.setArea(storeAreaDTO.getArea());
//                merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
                excelMapper.updateStoreArea(storeAreaDTO.getProvince(), storeAreaDTO.getCity(), storeAreaDTO.getArea(), map.get(storeAreaDTO.getCode().trim()));
            }
        }
        return "success";
    }

    /**
     * 省市区下拉框
     *
     * @return
     */
    @RequestMapping("/excel/areaSelectBox")
    public String updateArea() {
        List<ProvinceDTO> provinceDTOS = excelMapper.listProvince();
        provinceDTOS.forEach(provinceDTO -> {
            List<CityDTO> cityDTOS = excelMapper.listCity(provinceDTO.getValue());
            provinceDTO.setChildren(cityDTOS);
            cityDTOS.forEach(cityDTO -> {
                List<AreaDTO> areaDTOS = excelMapper.listArea(provinceDTO.getValue(), cityDTO.getValue());
                cityDTO.setChildren(areaDTOS);
            });
        });
        return JSON.toJSONString(provinceDTOS);
    }

    /**
     * 导入部门
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/inputDept")
    public String inputDept(MultipartFile file) {
        Map<String, Object> firstMap = new HashMap(16);
        Map<String, Object> secondMap = new HashMap<>(16);
        Map<String, Object> thirdMap = new HashMap<>(16);
        List<DeptDTO> list = ExcelUtils.readExcel("", DeptDTO.class, file);
        list.forEach(deptDTO -> {
            log.info("deptDTO：{}", deptDTO);
            // 一级部门
            String firstDept = deptDTO.getFirstDept();
            // 二级部门
            String secondDept = deptDTO.getSecondDept();
            // 三级部门
            String thirdDept = deptDTO.getThirdDept();
            DeptPO firstDeptPO = null;
            DeptPO secondDeptPO = null;
            DeptPO thirdDeptPO = null;
            if (!firstMap.keySet().contains(firstDept)) {
                firstDeptPO = new DeptPO(0, firstDept);
                departmentMapper.insert(firstDeptPO);
                firstMap.put(firstDept, firstDeptPO);
            } else {
                // 从map里面取对象
                firstDeptPO = (DeptPO) firstMap.get(firstDept);
            }

            if (!secondMap.keySet().contains(secondDept)) {
                secondDeptPO = new DeptPO(firstDeptPO.getId(), secondDept);
                departmentMapper.insert(secondDeptPO);
                secondMap.put(secondDept, secondDeptPO);
            } else {
                // 从map里面取对象
                secondDeptPO = (DeptPO) secondMap.get(secondDept);
            }
            if (StringUtils.isBlank(thirdDept)) {
                return;
            }
            if (!thirdMap.keySet().contains(thirdDept)) {
                log.info("secondDeptPO：{}", secondDeptPO);
                thirdDeptPO = new DeptPO(secondDeptPO.getId(), thirdDept);
                departmentMapper.insert(thirdDeptPO);
                thirdMap.put(thirdDept, thirdDeptPO);
            }
        });
        return "success";
    }

    /**
     * 导入区域经理
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/inputRegionalManager")
    public String inputRegionalManager(MultipartFile file) {
        List<RegionalManagerDTO> list = ExcelUtils.readExcel("", RegionalManagerDTO.class, file);
        List<RegionalManagerDepartment> regionalManagerDepartments = new ArrayList<>();
        list.forEach(regionalManagerDTO -> {
            // 区域经理数据
            RegionalManagerPO regionalManagerPO = new RegionalManagerPO(regionalManagerDTO);
            regionalManagerMapper.insert(regionalManagerPO);
            // 区域经理部门关联数据
            RegionalManagerDeptPO regionalManagerDeptPO = new RegionalManagerDeptPO(regionalManagerPO.getId());
            regionalManagerDepartments.add(regionalManagerDeptPO);
        });
        regionalManagerDepartmentMapper.insertList(regionalManagerDepartments);
        return "success";
    }

    /**
     * 导入代理商
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/inputAgent")
    public String inputAgent(MultipartFile file) {
        List<AgentDTO> list = ExcelUtils.readExcel("", AgentDTO.class, file);

        List<Merchant> allMerchant = excelMapper.getAllMerchant();
        Map<String, Merchant> merchantMap = new HashMap<>();
        allMerchant.forEach(merchant -> {
            merchantMap.put(merchant.getName(), merchant);
        });

        List<Merchant> merchants = new ArrayList<>();
        list.forEach(agentDTO -> {
            if (merchantMap.keySet().contains(agentDTO.getName())) {
                Merchant merchant = merchantMap.get(agentDTO.getName());
                if (StringUtils.isBlank(merchant.getMobile()) || merchant.getIsAgent().equals(2)) {
                    MerchantPO merchantPO = new MerchantPO(agentDTO, merchant);
                    merchantMapper.updateByPrimaryKeySelective(merchantPO);
                }
            } else {
                // 代理商数据
                MerchantPO merchantPO = new MerchantPO(agentDTO);
                merchants.add(merchantPO);
            }
        });
        if (merchants.size() > 0) {
            merchantMapper.insertList(merchants);
        }
        return "success";
    }

    /**
     * 导入加盟商
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/inputMerchant")
    public String inputMerchant(MultipartFile file) {
        List<MerchantDTO> list = ExcelUtils.readExcel("", MerchantDTO.class, file);
        List<Merchant> merchants = new ArrayList<>();
        // 获取所有代理商名字
//        List<String> merchantNames = excelMapper.getAllMerchantName();
//        List<String> merchantMobiles = excelMapper.getAllMerchantMobile();
//        if (CollectionUtils.isEmpty(merchantNames) || CollectionUtils.isEmpty(merchantMobiles)) {
//            return "请先导入代理商表格";
//        }
        list.forEach(merchantDTO -> {
//            // 先判断名字是否相同
//            if (!merchantNames.contains(merchantDTO.isShowAll())) {
//                // 名字不同判断手机号是否相同
//                if (!merchantMobiles.contains(merchantDTO.getMobile())) {
            // 代理商数据
            MerchantPO merchantPO = new MerchantPO(merchantDTO);
            merchants.add(merchantPO);
//                }
//            } else {
//                // 名字相同判断手机号是否相同
//                if (!merchantMobiles.contains(merchantDTO.getMobile())) {
//                    // 代理商数据
//                    MerchantPO merchantPO = new MerchantPO(merchantDTO);
//                    merchants.add(merchantPO);
//                }
//            }
        });
        merchantMapper.insertList(merchants);
        return "success";
    }

    /**
     * 导入代理商区域
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/inputMerchantArea")
    public String inputMerchantArea(MultipartFile file) {
        List<MerchantAreaDTO> list = ExcelUtils.readExcel("", MerchantAreaDTO.class, file);
        List<Merchant> allMerchant = excelMapper.getAllMerchant();
        Map<String, Merchant> merchantMap = new HashMap<>();
        allMerchant.forEach(merchant -> {
            merchantMap.put(merchant.getName(), merchant);
        });

        List<RegionalManager> allRegional = excelMapper.getAllRegional();
        Map<String, Integer> map = new HashMap<>();
        allRegional.forEach(regionalManager -> {
            map.put(regionalManager.getName(), regionalManager.getId());
        });

        List<MerchantAgentAreaRegionalManager> areaRegionalManagers = new ArrayList<>();
        Integer regionalId = null;
        for (MerchantAreaDTO merchantAreaDTO : list) {
            Merchant merchant;

            if (merchantMap.keySet().contains(merchantAreaDTO.getAgentName())) {
                merchant = merchantMap.get(merchantAreaDTO.getAgentName());
            } else {
                MerchantPO merchantPO = new MerchantPO(merchantAreaDTO);
                merchantMapper.insert(merchantPO);
                merchant = merchantPO;
                merchantMap.put(merchantPO.getName(), merchant);
            }

            if (map.keySet().contains(merchantAreaDTO.getManagerName())) {
                regionalId = map.get(merchantAreaDTO.getManagerName());
            } else {
                // 区域经理数据
                RegionalManagerDTO regionalManagerDTO = new RegionalManagerDTO();
                regionalManagerDTO.setName(merchantAreaDTO.getManagerName());
                RegionalManagerPO regionalManagerPO = new RegionalManagerPO(regionalManagerDTO);
                regionalManagerMapper.insert(regionalManagerPO);
                // 区域经理部门关联数据
                RegionalManagerDeptPO regionalManagerDeptPO = new RegionalManagerDeptPO(regionalManagerPO.getId());
                regionalManagerDepartmentMapper.insert(regionalManagerDeptPO);
                regionalId = regionalManagerPO.getId();
                map.put(merchantAreaDTO.getManagerName(), regionalId);
            }

            MerchantAreaPO merchantAreaPO = new MerchantAreaPO(merchantAreaDTO, merchant);
            merchantAgentAreaMapper.insert(merchantAreaPO);

            MerchantAreaRegionalPO merchantAreaRegionalPO = new MerchantAreaRegionalPO(merchantAreaPO.getId(), regionalId);
            areaRegionalManagers.add(merchantAreaRegionalPO);
        }
        merchantAgentAreaRegionalManagerMapper.insertList(areaRegionalManagers);
        return "success";
    }

    /**
     * 导入代理商区域和区域经理
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/inputMerchantAreaRegional")
    public String inputMerchantAreaRegional(MultipartFile file) {
        List<MerchantAreaRegionalDTO> list = ExcelUtils.readExcel("", MerchantAreaRegionalDTO.class, file);
        List<RegionalManager> allRegional = excelMapper.getAllRegional();
        Map<String, Integer> map = new HashMap<>();
        allRegional.forEach(regionalManager -> {
            map.put(regionalManager.getName(), regionalManager.getId());
        });

        List<Merchant> allMerchant = excelMapper.getAllMerchant();
        Map<String, Integer> merchantMap = new HashMap<>();
        allMerchant.forEach(merchant -> {
            merchantMap.put(merchant.getName(), merchant.getId());
        });


        List<MerchantAgentArea> allArea = excelMapper.getAllArea();
        Map<String, Integer> areaMap = new HashMap<>();
        allArea.forEach(agentArea -> {
            areaMap.put(agentArea.getMerchantName(), agentArea.getId());
        });

        List<MerchantAgentAreaRegionalManager> allAreaRegional = excelMapper.getAllAreaRegional();
        Map<String, Integer> areaRegionalMap = new HashMap<>();
        allAreaRegional.forEach(areaRegionalManager -> {
            areaRegionalMap.put(areaRegionalManager.getRegionalManagerId().toString() + "_" + areaRegionalManager.getMerchantAgentAreaId().toString(), areaRegionalManager.getRegionalManagerId());
        });

        List<MerchantAgentAreaRegionalManager> agentAreaList = new ArrayList<>();
//        Set<Integer> hashSet = new HashSet<>();
        Integer regionalId = null;
        Integer merchantId;
        Integer areaId;
        List<MerchantAgentAreaDetail> areaDetailList = new ArrayList<>();
        for (MerchantAreaRegionalDTO merchantAreaRegionalDTO : list) {
//            // 区域经理id
//            if (map.keySet().contains(merchantAreaRegionalDTO.getManagerName().trim())) {
//                regionalId = map.get(merchantAreaRegionalDTO.getManagerName());
//            } else {
//                // 区域经理数据
//                RegionalManagerDTO regionalManagerDTO = new RegionalManagerDTO();
//                regionalManagerDTO.setName(merchantAreaRegionalDTO.getManagerName());
//                RegionalManagerPO regionalManagerPO = new RegionalManagerPO(regionalManagerDTO);
//                regionalManagerMapper.insert(regionalManagerPO);
//                // 区域经理部门关联数据
//                RegionalManagerDeptPO regionalManagerDeptPO = new RegionalManagerDeptPO(regionalManagerPO.getId());
//                regionalManagerDepartmentMapper.insert(regionalManagerDeptPO);
//                regionalId = regionalManagerPO.getId();
//                map.put(merchantAreaRegionalDTO.getManagerName(), regionalId);
//            }
            // 代理商id
            if (merchantMap.keySet().contains(merchantAreaRegionalDTO.getName().trim())) {
                merchantId = merchantMap.get(merchantAreaRegionalDTO.getName());
            } else {
                MerchantDTO merchantDTO = new MerchantDTO();
                merchantDTO.setName(merchantAreaRegionalDTO.getName());
                MerchantPO merchantPO = new MerchantPO(merchantDTO);
                merchantMapper.insert(merchantPO);
                merchantId = merchantPO.getId();
                merchantMap.put(merchantAreaRegionalDTO.getName(), merchantId);
            }

            // 代理区域id
//            String key = merchantAreaRegionalDTO.getProvince().trim() + merchantAreaRegionalDTO.getCity().trim() + merchantAreaRegionalDTO.getArea().trim();
            if (areaMap.keySet().contains(merchantAreaRegionalDTO.getName())) {
                areaId = areaMap.get(merchantAreaRegionalDTO.getName());
            } else {
//                areaId = excelMapper.getMerchantAreaId(merchantAreaRegionalDTO.getProvince().trim(), merchantAreaRegionalDTO.getCity().trim(), merchantAreaRegionalDTO.getArea().trim());
//                if (areaId == null) {
//                    continue;
//                }
//                areaMap.put(key, areaId);
                MerchantAreaDTO merchantAreaDTO = new MerchantAreaDTO();
                merchantAreaDTO.setCode(merchantAreaRegionalDTO.getAreaCode());
                merchantAreaDTO.setAgencyFees(BigDecimal.ZERO.toString());
                merchantAreaDTO.setManagementFee(BigDecimal.ZERO.toString());
                merchantAreaDTO.setMarginFee(BigDecimal.ZERO.toString());
                merchantAreaDTO.setIsPosition("是");
                Merchant merchant = new Merchant();
                merchant.setId(merchantId);
                merchant.setName(merchantAreaRegionalDTO.getName());
                MerchantAreaPO merchantAreaPO = new MerchantAreaPO(merchantAreaDTO, merchant);
                merchantAgentAreaMapper.insert(merchantAreaPO);
                areaId = merchantAreaPO.getId();
                areaMap.put(merchantAreaRegionalDTO.getName(), areaId);
            }
            if (map.keySet().contains(merchantAreaRegionalDTO.getManagerName().trim())) {
                regionalId = map.get(merchantAreaRegionalDTO.getManagerName());
                String key = regionalId.toString() + "_" + areaId.toString();
                if (!areaRegionalMap.keySet().contains(key)) {
                    MerchantAreaRegionalPO merchantAreaRegionalPO = new MerchantAreaRegionalPO(areaId, regionalId);
//                    merchantAgentAreaRegionalManagerMapper.insert(merchantAreaRegionalPO);
                    agentAreaList.add(merchantAreaRegionalPO);
                    areaRegionalMap.put(key, regionalId);
                }
            }


            MerchantAgentAreaDetail merchantAgentAreaDetail = new MerchantAgentAreaDetail();
            merchantAgentAreaDetail.setUid(merchantAreaRegionalDTO.getCode());
            merchantAgentAreaDetail.setAgentAreaId(areaId);
            merchantAgentAreaDetail.setProvince(merchantAreaRegionalDTO.getProvince());
            merchantAgentAreaDetail.setCity(merchantAreaRegionalDTO.getCity());
            merchantAgentAreaDetail.setArea(merchantAreaRegionalDTO.getArea());
            merchantAgentAreaDetail.setStatus(StatusEnum.ENABLE.getStatus());
            merchantAgentAreaDetail.setCreateTime(new Date());
            areaDetailList.add(merchantAgentAreaDetail);

//            if (!areaMap.keySet().contains(areaId)) {
//                MerchantAreaRegionalPO merchantAreaPO = new MerchantAreaRegionalPO(areaId, regionalId);
//                agentAreaList.add(merchantAreaPO);
//                hashSet.add(areaId);
//            }
        }
        merchantAgentAreaDetailMapper.insertList(areaDetailList);
        if (agentAreaList.size() > 0) {
            merchantAgentAreaRegionalManagerMapper.insertList(agentAreaList);
        }

        return "success";
    }

    /**
     * 导入加盟店
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/inputStore")
    public String inputStore(MultipartFile file) {
        List<StoreDTO> list = ExcelUtils.readExcel("", StoreDTO.class, file);

//        List<MerchantStoreRegionalManager> merchantStoreRegionalManagers = new ArrayList<>();
        // 获取所有代理商的id和名字
        List<Merchant> merchants = excelMapper.getAllMerchant();
        Map<String, Integer> merchantMap = new HashMap<>();
        merchants.forEach(merchant -> {
            merchantMap.put(merchant.getName() + merchant.getMobile(), merchant.getId());
        });

        // 获取所有区域经理的id和名字
        List<RegionalManager> regionalManagers = excelMapper.getAllRegional();
        Map<String, Integer> managerMap = new HashMap<>();
        regionalManagers.forEach(regionalManager -> {
            managerMap.put(regionalManager.getName(), regionalManager.getId());
        });

        List<MerchantStore> stores = excelMapper.getAllMerchantStore();
        Map<String, Integer> storeMap = new HashMap<>();
        stores.forEach(store -> {
            storeMap.put(store.getUid(), store.getId());
        });

        list.forEach(storeDTO -> {
            // 加盟商不存在，添加
            Integer merchantId = null;
            if (StringUtils.isNotBlank(storeDTO.getMerchantName())) {
                if (!merchantMap.keySet().contains(storeDTO.getMerchantName() + storeDTO.getMobile())) {
                    MerchantDTO merchantDTO = new MerchantDTO();
                    merchantDTO.setName(storeDTO.getMerchantName());
                    merchantDTO.setMobile(storeDTO.getMobile());
                    MerchantPO merchantPO = new MerchantPO(merchantDTO);
//                merchants.add(merchantPO);
                    merchantMapper.insert(merchantPO);
                    merchantId = merchantPO.getId();
                    merchantMap.put(storeDTO.getMerchantName() + storeDTO.getMobile(), merchantPO.getId());
                } else {
                    merchantId = merchantMap.get(storeDTO.getMerchantName() + storeDTO.getMobile());
                }
            }
            Integer managerId = null;
            if (StringUtils.isNotBlank(storeDTO.getRegionalManager())) {
                // 区域经理不存在，添加
                if (!managerMap.keySet().contains(storeDTO.getRegionalManager())) {
                    RegionalManagerDTO regionalManagerDTO = new RegionalManagerDTO();
                    regionalManagerDTO.setMobile(storeDTO.getRegionalManager());
                    // 区域经理数据
                    RegionalManagerPO regionalManagerPO = new RegionalManagerPO(regionalManagerDTO);
                    regionalManagerMapper.insert(regionalManagerPO);
                    // 区域经理部门关联数据
                    RegionalManagerDeptPO regionalManagerDeptPO = new RegionalManagerDeptPO(regionalManagerPO.getId());
//                    regionalManagerDepartments.add(regionalManagerDeptPO);
                    regionalManagerDepartmentMapper.insert(regionalManagerDeptPO);
                    managerId = regionalManagerDeptPO.getId();
                } else {
                    managerId = managerMap.get(storeDTO.getRegionalManager());
                }
            }
            // 加盟店
            if (storeMap.keySet().contains(storeDTO.getUid())) {
                StorePO storePO = new StorePO(storeDTO, storeMap.get(storeDTO.getUid()));
                merchantStoreMapper.updateByPrimaryKeySelective(storePO);
            } else {
                StorePO storePO = new StorePO(storeDTO, merchantId, "");
                merchantStoreMapper.insert(storePO);

                if (StringUtils.isNotBlank(storeDTO.getRegionalManager())) {
                    StoreRegionalManagerPO storeRegionalManagerPO = new StoreRegionalManagerPO(storePO.getId(), managerId);
//                if (storeRegionalManagerPO.getRegionalManagerId() == null) {
//                    return;
//                }
//                merchantStoreRegionalManagers.add(storeRegionalManagerPO);
                    merchantStoreRegionalManagerMapper.insert(storeRegionalManagerPO);
                }
            }
        });
//        merchantStoreRegionalManagerMapper.insertList(merchantStoreRegionalManagers);
        return "success";
    }

    /**
     * 导入云学堂
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/inputCloudSchool")
    public String inputCloudSchool(MultipartFile file) {
        List<CloudSchoolDTO> list = ExcelUtils.readExcel("", CloudSchoolDTO.class, file);
        List<MerchantStore> allMerchantStore = excelMapper.getAllMerchantStore();
        Map<String, Integer> storeMap = new HashMap<>();
        allMerchantStore.forEach(merchantStore -> {
            storeMap.put(merchantStore.getUid(), merchantStore.getId());
        });

        List<MerchantStoreCloudSchool> storeCloudSchools = excelMapper.getAllCloudSchool();
        Map<String, Integer> cloudSchoolMap = new HashMap<>();
        storeCloudSchools.forEach(merchantStoreCloudSchool -> {
            cloudSchoolMap.put(merchantStoreCloudSchool.getMerchantStoreUid() + "_" + merchantStoreCloudSchool.getAccount(), merchantStoreCloudSchool.getMerchantStoreId());
        });

        Integer storeId = null;
        List<MerchantStoreCloudSchool> cloudSchoolList = new ArrayList<>();
        for (CloudSchoolDTO cloudSchoolDTO : list) {
            // 判断数据是否已存在
            String key = cloudSchoolDTO.getCode().trim() + "_" + cloudSchoolDTO.getAccount().trim();
            if (cloudSchoolMap.keySet().contains(key)) {
                storeId = storeMap.get(cloudSchoolDTO.getCode().trim());
                excelMapper.updateCloudSchool(storeId, cloudSchoolDTO.getEndTime(), cloudSchoolDTO.getStartTime(), cloudSchoolDTO.getCode(), cloudSchoolDTO.getAccount());
            } else {
                // 获取到加盟店的id
                if (storeMap.keySet().contains(cloudSchoolDTO.getCode())) {
                    storeId = storeMap.get(cloudSchoolDTO.getCode().trim());
                }
                CloudSchoolPO cloudSchoolPO = new CloudSchoolPO(storeId, cloudSchoolDTO);
                cloudSchoolList.add(cloudSchoolPO);
            }
        }
        merchantStoreCloudSchoolMapper.insertList(cloudSchoolList);
        return "success";
    }


    /**
     * 导入代理费用
     */
    @RequestMapping("/excel/merchantMoney")
    public String merchantMoney(@RequestParam(value = "uploadFile", required = false) MultipartFile file) {
        List<AgentAreaPaymentOrderMaster> merchantStoresNew = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<MerchantMoneyDto> list = ExcelUtils.readExcel("", MerchantMoneyDto.class, file);
        //先查询代理权的信息
        List<AgentOrderExpireTimeVo> agentOrderExpireTimeVoList = merchantAgentAreaMapper.queryCodes(list);
        if (agentOrderExpireTimeVoList.size() > 0) {
            //并且排除已经有生成待支付管理费的代理
            List<AgentOrderJoinVo> managementOrderJoinVoList = agentAreaPaymentOrderMasterMapper.queryManagementOrderJoinVoList(agentOrderExpireTimeVoList);
            agentOrderExpireTimeVoList.stream().forEach(
                    agentOrderExpireTimeVo -> {
                        if (agentOrderExpireTimeVo.getMoney().compareTo(BigDecimal.ZERO) == 0) {
                            return;
                        }
                        if (StringUtils.isBlank(agentOrderExpireTimeVo.getRegionalManagerId())) {
                            return;
                        }
                        agentOrderExpireTimeVo.setStatus(0);
                        managementOrderJoinVoList.stream().forEach(
                                managementOrderJoinVo -> {
                                    if (agentOrderExpireTimeVo.getUid().equals(managementOrderJoinVo.getId())) {//如果是相同的门店对比金额
                                        agentOrderExpireTimeVo.setStatus(1);
                                    }
                                }
                        );
                        if (agentOrderExpireTimeVo.getStatus() == 0) {
                            Date newTime1 = DateUtil.endTime("1年后", agentOrderExpireTimeVo.getServiceExpireTime());
                            AgentAreaPaymentOrderMaster agentAreaPaymentOrderMaster = new AgentAreaPaymentOrderMaster();
                            agentAreaPaymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));
                            agentAreaPaymentOrderMaster.setMerchantId(Integer.valueOf(agentOrderExpireTimeVo.getMerchantId()));
                            agentAreaPaymentOrderMaster.setMerchantName(agentOrderExpireTimeVo.getMerchantName());
                            agentAreaPaymentOrderMaster.setUid(agentOrderExpireTimeVo.getUid());
                            agentAreaPaymentOrderMaster.setRegionalManagerId(Integer.valueOf(agentOrderExpireTimeVo.getRegionalManagerId()));
                            agentAreaPaymentOrderMaster.setRegionalManagerName(agentOrderExpireTimeVo.getRegionalManagerName());
                            agentAreaPaymentOrderMaster.setRegionalManagerMobile(agentOrderExpireTimeVo.getRegionalManagerMobile());
                            agentAreaPaymentOrderMaster.setPaymentTypeId(1);
                            agentAreaPaymentOrderMaster.setPaymentTypeName("管理费");
                            agentAreaPaymentOrderMaster.setRemark("管理费");
                            agentAreaPaymentOrderMaster.setType(1);
                            agentAreaPaymentOrderMaster.setSend(2);
                            agentAreaPaymentOrderMaster.setExpireTime(agentOrderExpireTimeVo.getServiceExpireTime());
                            agentAreaPaymentOrderMaster.setStatus(1);
                            agentAreaPaymentOrderMaster.setExamine(1);
                            agentAreaPaymentOrderMaster.setDeleted(false);
                            agentAreaPaymentOrderMaster.setCreateTime(new Date());
                            agentAreaPaymentOrderMaster.setExamineNum(2);
                            agentAreaPaymentOrderMaster.setMoney(agentOrderExpireTimeVo.getMoney());
                            agentAreaPaymentOrderMaster.setCity(agentOrderExpireTimeVo.getCity());
                            agentAreaPaymentOrderMaster.setProvince(agentOrderExpireTimeVo.getProvince());
                            agentAreaPaymentOrderMaster.setArea(agentOrderExpireTimeVo.getArea());
                            agentAreaPaymentOrderMaster.setAdjustmentCount(0);
                            agentAreaPaymentOrderMaster.setPaymentStandardMoney(agentOrderExpireTimeVo.getMoney());
                            agentAreaPaymentOrderMaster.setCycle(sdf.format(agentOrderExpireTimeVo.getServiceExpireTime()) + "~" + sdf.format(newTime1));
//
                            merchantStoresNew.add(agentAreaPaymentOrderMaster);
                        }
                    }
            );
            if (merchantStoresNew.size() > 0) {
                agentAreaPaymentOrderMasterMapper.insertList(merchantStoresNew);
            }
        }
        return "success";
    }


    /**
     * 导入门店费用
     */
    @RequestMapping("/excel/merchantStoreMoney")
    public String merchantStoreMoney(MultipartFile file) {
        List<MerchantStoreMoneyDTO> list = ExcelUtils.readExcel("", MerchantStoreMoneyDTO.class, file);
        List<PaymentOrderMaster> noExamineList = new ArrayList<>();
        for (MerchantStoreMoneyDTO merchantStoreMoneyDTO : list) {
            ManagementOrderExpireTimeVo merchantStore = merchantStoreMapper.queryStoreInfoByUid(merchantStoreMoneyDTO.getUid());

            int count = paymentOrderMasterMapper.queryExistOrderCount(Integer.parseInt(merchantStore.getMerchantStoreId()));
            if (count > 0) {
                log.info("门店【" + merchantStore.getMerchantStoreId() + "】授权号【" + merchantStore.getMerchantStoreUid() + "】已生成管理费，无需重复生成");
                continue;
            }
            PaymentOrderMaster paymentOrderMaster = new PaymentOrderMaster();
            String orderNo = "MDGLF" + "-" + merchantStore.getMerchantStoreUid() + "-" + cn.hutool.core.date.DateUtil.format(new Date(), "yyyyMMdd") + "-" + cn.hutool.core.date.DateUtil.format(merchantStore.getServiceExpireTime(), "yyyyMMdd");
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
            paymentOrderMaster.setRemark(merchantStoreMoneyDTO.getRemark());
            paymentOrderMaster.setType(1);
            paymentOrderMaster.setSend(2);
            paymentOrderMaster.setExpireTime(merchantStore.getServiceExpireTime());
            paymentOrderMaster.setStatus(1);
            paymentOrderMaster.setDeleted(false);
            paymentOrderMaster.setCreateTime(new Date());
            paymentOrderMaster.setExamineNum(2);
            paymentOrderMaster.setMoney(new BigDecimal(merchantStoreMoneyDTO.getMoney()));
            paymentOrderMaster.setPayType(1);
            paymentOrderMaster.setCity(merchantStore.getCity());
            paymentOrderMaster.setProvince(merchantStore.getProvince());
            paymentOrderMaster.setArea(merchantStore.getArea());
            paymentOrderMaster.setAdjustmentCount(0);
            paymentOrderMaster.setAddress(merchantStore.getAddress());
            paymentOrderMaster.setPaymentStandardMoney(paymentOrderMaster.getMoney());
            paymentOrderMaster.setCycle(cn.hutool.core.date.DateUtil.format(new Date(), "yyyy-MM-dd") + "~" + cn.hutool.core.date.DateUtil.format(merchantStore.getServiceExpireTime(), "yyyy-MM-dd"));
            paymentOrderMaster.setIsPush(0);
            paymentOrderMaster.setServiceStartTime(merchantStore.getServiceExpireTime());
            paymentOrderMaster.setServiceEndTime(com.ynzyq.yudbadmin.util.DateUtil.endTime("1年后", merchantStore.getServiceExpireTime()));
            paymentOrderMaster.setIsPush(0);
            paymentOrderMaster.setIsChange(2);
            // 在营、迁址、暂停经营直接推送区域经理，无需审核
            paymentOrderMaster.setExamine(1);
            paymentOrderMaster.setIsPublish(2);
            noExamineList.add(paymentOrderMaster);
        }
        // 添加非审核订单
        if (noExamineList.size() > 0) {
            paymentOrderMasterMapper.insertList(noExamineList);
        }
        return "success";
    }


    /**
     * 导入云学堂账户
     */
    @RequestMapping("/excel/MerchantStoreCloudSchoolTask")
    public String MerchantStoreCloudSchoolTask(@RequestParam(value = "uploadFile", required = false) MultipartFile file) {

        List<PaymentOrderMaster> merchantStoresNew = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<MerchantMoneyDto> list = ExcelUtils.readExcel("", MerchantMoneyDto.class, file);
        List<MerchantStore> merchantStoreList = merchantStoreMapper.queryStoreCodes(list);
        if (merchantStoreList.size() > 0) {
            merchantStoreList.stream().forEach(
                    merchantStore -> {
                        //查询云学堂数据
                        List<MerchantStoreCloudSchool> merchantStoreCloudSchoolList = merchantStoreCloudSchoolMapper.queryMerchantStoreId(merchantStore.getUid());
                        //查询代理
                        Merchant merchant = merchantMapper.selectByPrimaryKey(merchantStore.getMerchantId());
                        //查询区域经理
                        RegionalManager regionalManager = regionalManagerDepartmentMapper.queryStoreId(merchantStore.getId());


                        merchantStoreCloudSchoolList.stream().forEach(
                                merchantStoreCloudSchool -> {
                                    //并且需要排除已经生成了商户通订单待支付的用户
                                    Date newTime1 = DateUtil.endTime("1年后", merchantStoreCloudSchool.getEndTime());
                                    PaymentOrderMaster paymentOrderMaster = new PaymentOrderMaster();
                                    paymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));
                                    paymentOrderMaster.setOtherId(Integer.valueOf(merchantStore.getId()));
                                    paymentOrderMaster.setMerchantId(Integer.valueOf(merchantStore.getMerchantId()));
                                    paymentOrderMaster.setMerchantName(merchant.getName());
                                    paymentOrderMaster.setMerchantStoreId(Integer.valueOf(merchantStore.getId()));
                                    paymentOrderMaster.setMerchantStoreName(merchantStore.getName());
                                    paymentOrderMaster.setMerchantStoreUid(merchantStore.getUid());
                                    paymentOrderMaster.setMerchantStoreMobile(merchantStore.getMobile());
                                    if (regionalManager != null) {
                                        paymentOrderMaster.setRegionalManagerId(Integer.valueOf(regionalManager.getId()));
                                        paymentOrderMaster.setRegionalManagerName(regionalManager.getName());
                                        paymentOrderMaster.setRegionalManagerMobile(regionalManager.getMobile());
                                    }


                                    paymentOrderMaster.setPaymentTypeId(3);
                                    paymentOrderMaster.setPaymentTypeName("云学堂");
                                    paymentOrderMaster.setRemark("云学堂");
                                    paymentOrderMaster.setType(1);
                                    paymentOrderMaster.setSend(2);
                                    paymentOrderMaster.setExpireTime(merchantStoreCloudSchool.getEndTime());
                                    paymentOrderMaster.setStatus(1);
                                    paymentOrderMaster.setExamine(1);
                                    paymentOrderMaster.setDeleted(false);
                                    paymentOrderMaster.setCreateTime(new Date());
                                    paymentOrderMaster.setExamineNum(2);
                                    paymentOrderMaster.setMoney(new BigDecimal(100));
                                    paymentOrderMaster.setAddress(merchantStore.getAddress());
                                    paymentOrderMaster.setCity(merchantStore.getCity());
                                    paymentOrderMaster.setProvince(merchantStore.getProvince());
                                    paymentOrderMaster.setArea(merchantStore.getArea());
                                    paymentOrderMaster.setAdjustmentCount(0);
                                    paymentOrderMaster.setPaymentStandardMoney(paymentOrderMaster.getMoney());
                                    paymentOrderMaster.setCycle(sdf.format(merchantStoreCloudSchool.getEndTime()) + "~" + sdf.format(newTime1));
                                    merchantStoresNew.add(paymentOrderMaster);
                                }
                        );


                    }
            );
            if (merchantStoresNew.size() > 0) {
                paymentOrderMasterMapper.insertList(merchantStoresNew);
            }

        }
        return "success";
    }

    /**
     * 导入代理商区域映射
     *
     * @param file
     * @return
     */
    @RequestMapping("/excel/inputMerchantStoreArea")
    public String inputMerchantStoreArea(MultipartFile file) {
        List<MerchantStoreAreaDTO> list = ExcelUtils.readExcel("", MerchantStoreAreaDTO.class, file);
        List<RegionalManager> allRegional = excelMapper.getAllRegional();
        Map<String, Integer> map = new HashMap<>();
        allRegional.forEach(regionalManager -> {
            map.put(regionalManager.getName(), regionalManager.getId());
        });

        List<Merchant> allMerchant = excelMapper.getAllMerchant();
        Map<String, Integer> merchantMap = new HashMap<>();
        allMerchant.forEach(merchant -> {
            merchantMap.put(merchant.getName(), merchant.getId());
        });

        List<MerchantStoreMappingArea> agentAreaList = new ArrayList<>();
        Integer regionalId;
        Integer merchantId;
        for (MerchantStoreAreaDTO merchantStoreAreaDTO : list) {
            MerchantStoreMappingArea merchantStoreMappingArea = new MerchantStoreMappingArea();
            merchantStoreMappingArea.setUid(merchantStoreAreaDTO.getCode());
            merchantStoreMappingArea.setProvince(merchantStoreAreaDTO.getProvince());
            merchantStoreMappingArea.setCity(merchantStoreAreaDTO.getCity());
            merchantStoreMappingArea.setArea(merchantStoreAreaDTO.getArea());
            merchantStoreMappingArea.setRegion(merchantStoreAreaDTO.getAddress());
            merchantStoreMappingArea.setStatus(1);
            merchantStoreMappingArea.setCreateTime(new Date());
            // 代理商id
            if (merchantMap.keySet().contains(merchantStoreAreaDTO.getName().trim())) {
                merchantId = merchantMap.get(merchantStoreAreaDTO.getName());
                merchantStoreMappingArea.setAgentId(merchantId);
                merchantStoreMappingArea.setAgentName(merchantStoreAreaDTO.getName().trim());
            }

            // 代理区域id
            if (map.keySet().contains(merchantStoreAreaDTO.getManagerName().trim())) {
                regionalId = map.get(merchantStoreAreaDTO.getManagerName().trim());
                merchantStoreMappingArea.setManagerId(regionalId);
                merchantStoreMappingArea.setManagerName(merchantStoreAreaDTO.getManagerName().trim());
            }
            agentAreaList.add(merchantStoreMappingArea);
        }
        if (agentAreaList.size() > 0) {
            merchantStoreMappingAreaMapper.insertList(agentAreaList);
        }
        return "success";
    }
}



