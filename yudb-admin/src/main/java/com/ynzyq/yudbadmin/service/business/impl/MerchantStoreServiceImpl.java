package com.ynzyq.yudbadmin.service.business.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ynzyq.yudbadmin.api.excel.enums.ContractStatusEnum;
import com.ynzyq.yudbadmin.api.excel.enums.NewStatusTwoEnum;
import com.ynzyq.yudbadmin.api.excel.enums.StatusTwoEnum;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.*;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.enums.IsPreferentialEnum;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import com.ynzyq.yudbadmin.enums.StoreTypeEnum;
import com.ynzyq.yudbadmin.po.PaymentOrderChangeRecordPO;
import com.ynzyq.yudbadmin.redis.RedisUtils;
import com.ynzyq.yudbadmin.service.business.MerchantStoreService;
import com.ynzyq.yudbadmin.third.dto.UserInfoDTO;
import com.ynzyq.yudbadmin.third.service.IForeignService;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import com.ynzyq.yudbadmin.util.PhoneFormatCheckUtils;
import com.ynzyq.yudbadmin.util.PlatformCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MerchantStoreServiceImpl implements MerchantStoreService {


    @Resource
    MerchantStoreMapper merchantStoreMapper;
    @Resource
    RegionalManagerMapper regionalManagerMapper;
    @Resource
    MerchantStoreRegionalManagerMapper merchantStoreRegionalManagerMapper;
    @Resource
    MerchantStoreStatusTimeMapper merchantStoreStatusTimeMapper;
    @Resource
    MerchantStoreCloudSchoolMapper merchantStoreCloudSchoolMapper;
    @Resource
    MerchantMapper merchantMapper;
    @Resource
    MerchantStoreExamineFileMapper merchantStoreExamineFileMapper;
    @Resource
    MerchantStoreExamineMapper merchantStoreExamineMapper;
    @Resource
    MerchantStoreExamineDeailMapper merchantStoreExamineDeailMapper;
    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;
    @Resource
    PaymentOrderExamineMapper paymentOrderExamineMapper;
    @Resource
    PaymentOrderExamineDeailMapper paymentOrderExamineDeailMapper;
    @Resource
    PaymentOrderExamineFileMapper paymentOrderExamineFileMapper;
    @Resource
    RedisUtils redisUtils;
    @Resource
    PaymentTypeMapper paymentTypeMapper;

    @Resource
    DictMapper dictMapper;

    @Resource
    MerchantStoreMappingMapper merchantStoreMappingMapper;

    @Resource
    PaymentOrderChangeRecordMapper paymentOrderChangeRecordMapper;

    @Resource
    ApproveProcessMapper approveProcessMapper;

    @Resource
    IForeignService iForeignService;

    @Override
    public ApiRes<PageWrap<StroePageVo>> findPage(PageWrap<StroePageDto> pageWrap) {
        StroePageDto stroePageDto = pageWrap.getModel();

        String condition = stroePageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            stroePageDto.setCondition(null);
        }
        String startexpireTime = stroePageDto.getStartExpireTime();
        if (StringUtils.isEmpty(startexpireTime)) {
            stroePageDto.setStartExpireTime(null);
            stroePageDto.setEndExpireTime(null);
        } else {
            stroePageDto.setStartExpireTime(stroePageDto.getStartExpireTime() + " 00:00:00");
            stroePageDto.setEndExpireTime(stroePageDto.getEndExpireTime() + " 23:59:59");
        }
        String stareSignTime = stroePageDto.getStartSignTime();
        if (StringUtils.isEmpty(stareSignTime)) {
            stroePageDto.setStartSignTime(null);
            stroePageDto.setEndSignTime(null);
        } else {
            stroePageDto.setStartSignTime(stroePageDto.getStartSignTime() + " 00:00:00");
            stroePageDto.setEndSignTime(stroePageDto.getEndSignTime() + " 23:59:59");
        }
        stroePageDto.setMerchantId(StringUtils.isBlank(stroePageDto.getMerchantId()) ? null : stroePageDto.getMerchantId());
        stroePageDto.setAreaId(StringUtils.isBlank(stroePageDto.getAreaId()) ? null : stroePageDto.getAreaId());
        stroePageDto.setMonth(StringUtils.isBlank(stroePageDto.getMonth()) ? null : stroePageDto.getMonth());
        stroePageDto.setStatus(StringUtils.isBlank(stroePageDto.getStatus()) ? null : stroePageDto.getStatus());
        stroePageDto.setDelayedOpen(StringUtils.isBlank(stroePageDto.getDelayedOpen()) ? null : stroePageDto.getDelayedOpen());
        stroePageDto.setRegion(StringUtils.isBlank(stroePageDto.getRegion()) ? null : stroePageDto.getRegion());
        stroePageDto.setProvince(StringUtils.isBlank(stroePageDto.getProvince()) ? null : stroePageDto.getProvince());
        stroePageDto.setCity(StringUtils.isBlank(stroePageDto.getCity()) ? null : stroePageDto.getCity());
        stroePageDto.setArea(StringUtils.isBlank(stroePageDto.getArea()) ? null : stroePageDto.getArea());
        stroePageDto.setStartServiceExpireTime(StringUtils.isBlank(stroePageDto.getStartServiceExpireTime()) ? null : stroePageDto.getStartServiceExpireTime());
        stroePageDto.setEndServiceExpireTime(StringUtils.isBlank(stroePageDto.getEndServiceExpireTime()) ? null : stroePageDto.getEndServiceExpireTime());
        stroePageDto.setIsApply(StringUtils.isBlank(stroePageDto.getIsApply()) ? null : stroePageDto.getIsApply());

        List<MappingAreaDTO> mappingAreaDTOS = merchantStoreMapper.listMappingArea();
        Map<String, MappingAreaDTO> areaMap = Maps.newHashMap();
        mappingAreaDTOS.forEach(mappingAreaDTO -> {
            areaMap.put(mappingAreaDTO.getProvince() + mappingAreaDTO.getCity() + mappingAreaDTO.getArea(), mappingAreaDTO);
        });
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<StroePageVo> stroePageVoList;
        if (StringUtils.isBlank(stroePageDto.getAreaId())) {
            stroePageVoList = merchantStoreMapper.findPage(stroePageDto);
        } else {
            stroePageVoList = merchantStoreMapper.findPageByAreaId(stroePageDto);
        }
        stroePageVoList.stream().forEach(
                stroePageVo -> {
                    stroePageVo.setStatus(StatusTwoEnum.getStatusDesc(Integer.parseInt(stroePageVo.getStatus())));
                    stroePageVo.setContractStatusId(stroePageVo.getContractStatus());
                    stroePageVo.setContractStatus(ContractStatusEnum.getStatusDesc(Integer.parseInt(stroePageVo.getContractStatus())));
                    stroePageVo.setStatusTwoDesc(NewStatusTwoEnum.getStatusDesc(Integer.parseInt(stroePageVo.getStatusTwo())));
                    if (areaMap.keySet().contains(stroePageVo.getProvince() + stroePageVo.getCity() + stroePageVo.getArea())) {
                        stroePageVo.setRegion(areaMap.get(stroePageVo.getProvince() + stroePageVo.getCity() + stroePageVo.getArea()).getRegion());
                        stroePageVo.setLevel(areaMap.get(stroePageVo.getProvince() + stroePageVo.getCity() + stroePageVo.getArea()).getLevel());
                    }
                }
        );
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(stroePageVoList)));
    }

    @Override
    public ApiRes<List<RegionalManageListVo>> regionalManageListVo() {
        List<RegionalManageListVo> regionalManageListVoList = regionalManagerMapper.queryName();
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", regionalManageListVoList);
    }

    @Override
    public ApiRes<List<OneLevelStatusVO>> stroeStatusVo() {
        List<OneLevelStatusVO> oneLevelStatusVOList = dictMapper.getOneLevelStatus();
        if (oneLevelStatusVOList.size() == 0) {
            oneLevelStatusVOList = new ArrayList<>();
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", oneLevelStatusVOList);
    }

    @Override
    public ApiRes<List<OneLevelStatusVO>> storeStatusTwoVo() {
        List<OneLevelStatusVO> oneLevelStatusVOList = dictMapper.getTwoLevelStatus();
        if (oneLevelStatusVOList.size() == 0) {
            oneLevelStatusVOList = new ArrayList<>();
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", oneLevelStatusVOList);
    }

    @Override
    public ApiRes updateRegionalManage(UpdateRegionalManageDto updateRegionalManageDto) {

        if (updateRegionalManageDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String regionalManageId = updateRegionalManageDto.getRegionalManageId();
        if (StringUtils.isEmpty(regionalManageId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        List<PaymentOrderChangeRecord> paymentOrderChangeRecordList = new ArrayList<>();
        for (String storeId : updateRegionalManageDto.getStoreIds()) {
            // 获取原区域经理信息
            Integer oldManagerId = null;
            String oldManagerName = null;
            MerchantStoreRegionalManagerDTO oldManager = merchantStoreMapper.getOldManager(Integer.parseInt(storeId));
            if (oldManager != null) {
                oldManagerId = oldManager.getManagerId();
                oldManagerName = oldManager.getName();
            }
            RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(regionalManageId);
            if (regionalManager == null) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
            }
            MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(storeId);
            if (merchantStore == null) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");
            }
            LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

            MerchantStoreRegionalManager merchantStoreRegionalManager = new MerchantStoreRegionalManager();
            merchantStoreRegionalManager.setMerchantStoreId(merchantStore.getId());
            merchantStoreRegionalManager.setRegionalManagerId(regionalManager.getId());
            merchantStoreRegionalManager.setStatus(1);
            merchantStoreRegionalManager.setCreateUser(loginUserInfo.getId());
            merchantStoreRegionalManager.setCreateTime(new Date());
            merchantStoreRegionalManagerMapper.updateStatusByStore(storeId);
            merchantStoreRegionalManagerMapper.insertSelective(merchantStoreRegionalManager);
            // 获取区域映射
            AgentManagerDTO agentManager = merchantStoreMapper.getAgentManager(merchantStore.getProvince(), merchantStore.getCity(), merchantStore.getArea());

            List<PaymentOrderDTO> orderDTOList = merchantStoreMappingMapper.listStoreOrder(merchantStore.getId());
            if (orderDTOList.size() > 0) {
                for (PaymentOrderDTO paymentOrderDTO : orderDTOList) {
                    PaymentOrderChangeRecordPO paymentOrderChangeRecordPO = new PaymentOrderChangeRecordPO(agentManager.getId().toString(), paymentOrderDTO, oldManagerId, oldManagerName, regionalManager, 1, loginUserInfo);
                    paymentOrderChangeRecordList.add(paymentOrderChangeRecordPO);
                    merchantStoreMappingMapper.updateOrderManager(regionalManager.getId(), regionalManager.getName(), regionalManager.getMobile(), paymentOrderDTO.getId());
                }
            }
        }
        // 批量添加订单更新记录
        if (paymentOrderChangeRecordList.size() > 0) {
            paymentOrderChangeRecordMapper.insertList(paymentOrderChangeRecordList);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }

    @Override
    public ApiRes updateStoreStatus(UpdateStoreStatusDto updateStoreStatusDto) {

        if (updateStoreStatusDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String statusId = updateStoreStatusDto.getStatusId();
        if (StringUtils.isEmpty(statusId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String storeId = updateStoreStatusDto.getStoreId();
        if (StringUtils.isEmpty(storeId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(storeId);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        MerchantStore updateMerchantStore = new MerchantStore();
        updateMerchantStore.setId(merchantStore.getId());
        updateMerchantStore.setStatus(Integer.valueOf(statusId));

        MerchantStoreStatusTime merchantStoreStatusTime = new MerchantStoreStatusTime();
        merchantStoreStatusTime.setStatus(1);
        merchantStoreStatusTime.setMerchantStoreId(Integer.valueOf(storeId));
        merchantStoreStatusTime.setStoreStatus(Integer.valueOf(statusId));
        merchantStoreStatusTime.setCreateTime(new Date());
        merchantStoreStatusTime.setCreateUser(loginUserInfo.getId());
        // 由未开业变更为在营、迁址、暂停经营时，在变更时系统生成对应的管理费账单，不推送城区经理，在开业后的 收款月份1号同步推送
//        if (StatusTwoEnum.UNOPENED.getStatus().equals(merchantStore.getStatus())) {
//            if (merchantStore.getOpenTime() == null) {
//                return ApiRes.failResponse("请先设置开业时间");
//            }
//            if (StatusTwoEnum.IN_CAMP.getStatus().equals(Integer.valueOf(statusId))
//                    || StatusTwoEnum.RELOCATION.getStatus().equals(Integer.valueOf(statusId))
//                    || StatusTwoEnum.SUSPEND_BUSINESS.getStatus().equals(Integer.valueOf(statusId))) {
//                if (merchantStore.getManagementExpense().compareTo(BigDecimal.ZERO) > 0) {
//                    // 新建管理费账单
//                    ApiRes apiRes = createOrder(merchantStore, Integer.valueOf(statusId));
//                    if (apiRes.getCode() != CommonConstant.SUCCESS_CODE) {
//                        return apiRes;
//                    }
//                }
//            }
//        }
        merchantStoreStatusTimeMapper.insertSelective(merchantStoreStatusTime);
        merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }

    private ApiRes createOrder(MerchantStore merchantStore, Integer statusId) {
        if (merchantStore.getOpenTime() == null || merchantStore.getServiceExpireTime() == null) {
            log.info("门店【" + merchantStore.getId() + "】授权号【" + merchantStore.getUid() + "】开业时间或服务到期时间为空，无法计算金额");
            return ApiRes.failResponse("请先设置开业时间和服务到期，否则无法计算管理费金额");
        }
        int count = paymentOrderMasterMapper.queryExistOrderCount(merchantStore.getId());
        if (count > 0) {
            log.info("门店【" + merchantStore.getId() + "】授权号【" + merchantStore.getUid() + "】已生成管理费，无需重复生成");
            return ApiRes.successResponse();
        }
        RegionalManager regionalManager = merchantStoreMapper.getRegionalManagerByStoreId(merchantStore.getId());
        if (regionalManager == null) {
            return ApiRes.failResponse("请先设置区域经理");
        }
        Merchant merchant = merchantMapper.selectByPrimaryKey(merchantStore.getMerchantId());
        // 计算开业时间 与 签约时间天数差
        long betweenDay = DateUtil.between(merchantStore.getOpenTime(), merchantStore.getSignTime(), DateUnit.DAY);
        // 计算本月月份值
        int month = DateUtil.month(new Date()) + 1;
        // 延期开业，补缴订单
        Date newTime;
        if (betweenDay > 365) {
            // 开业时间月份
            int openTimeMonth = DateUtil.month(merchantStore.getOpenTime()) + 1;
            // 服务到期时间月份
            int serviceExpireTimeMonth = DateUtil.month(merchantStore.getServiceExpireTime()) + 1;
            int diffMonth;
            if (openTimeMonth <= serviceExpireTimeMonth) {
                diffMonth = serviceExpireTimeMonth - openTimeMonth + 1;
            } else {
                diffMonth = 13 - openTimeMonth + serviceExpireTimeMonth;
            }
//            String openTime = DateUtil.format(merchantStore.getOpenTime(), "yyyy-MM");
//            String thisMonth = DateUtil.format(new Date(), "yyyy-MM");
            int day = DateUtil.dayOfMonth(merchantStore.getOpenTime());
//            if (StringUtils.equals(openTime, thisMonth) && day < 15) {
            if (day > 15) {
                diffMonth = diffMonth - 1;
            }
            BigDecimal money = new BigDecimal("20000").divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(diffMonth));

            newTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
            PaymentOrderMaster replenishOrderMaster = new PaymentOrderMaster();
            Date serviceStartTime;
            Date serviceEndTime;
            if (StatusTwoEnum.IN_CAMP.getStatus().equals(Integer.valueOf(statusId))) {
                serviceStartTime = DateUtil.parse(DateUtil.format(merchantStore.getOpenTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
                serviceEndTime = merchantStore.getServiceExpireTime();
            } else {
                serviceStartTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1天后", merchantStore.getServiceExpireTime());
                serviceEndTime = newTime;
            }

            String orderNo = "MDGLF" + "-" + merchantStore.getUid() + "-" + DateUtil.format(serviceStartTime, "yyyyMMdd") + "-" + DateUtil.format(serviceEndTime, "yyyyMMdd");
            replenishOrderMaster.setOrderNo(orderNo);
            replenishOrderMaster.setMerchantId(Integer.valueOf(merchantStore.getMerchantId()));
            replenishOrderMaster.setMerchantName(merchant.getName());
            replenishOrderMaster.setMerchantStoreId(merchantStore.getId());
            replenishOrderMaster.setMerchantStoreName(merchantStore.getName());
            replenishOrderMaster.setMerchantStoreUid(merchantStore.getUid());
            replenishOrderMaster.setMerchantStoreMobile(merchantStore.getMobile());
            replenishOrderMaster.setRegionalManagerId(regionalManager.getId());
            replenishOrderMaster.setRegionalManagerName(regionalManager.getName());
            replenishOrderMaster.setRegionalManagerMobile(regionalManager.getMobile());
            replenishOrderMaster.setPaymentTypeId(1);
            replenishOrderMaster.setPaymentTypeName("管理费");
            replenishOrderMaster.setRemark("管理费");
            replenishOrderMaster.setType(1);
            replenishOrderMaster.setSend(2);
            if (StatusTwoEnum.IN_CAMP.getStatus().equals(Integer.valueOf(statusId))) {
                replenishOrderMaster.setIsChange(1);
                replenishOrderMaster.setExpireTime(DateUtil.parse(DateUtil.format(DateUtil.endOfMonth(DateUtil.offset(new Date(), DateField.MONTH, 1)), "yyyy-MM-dd"), "yyyy-MM-dd"));
            } else {
                replenishOrderMaster.setIsChange(2);
                replenishOrderMaster.setExpireTime(DateUtil.parse(DateUtil.format(DateUtil.endOfMonth(new Date()), "yyyy-MM-dd"), "yyyy-MM-dd"));
            }
            replenishOrderMaster.setStatus(1);
            replenishOrderMaster.setDeleted(false);
            replenishOrderMaster.setCreateTime(new Date());
            replenishOrderMaster.setExamineNum(2);
            replenishOrderMaster.setMoney(money);
            replenishOrderMaster.setPayType(1);
            replenishOrderMaster.setCity(merchantStore.getCity());
            replenishOrderMaster.setProvince(merchantStore.getProvince());
            replenishOrderMaster.setArea(merchantStore.getArea());
            replenishOrderMaster.setAdjustmentCount(0);
            replenishOrderMaster.setAddress(merchantStore.getAddress());
            replenishOrderMaster.setPaymentStandardMoney(replenishOrderMaster.getMoney());
            replenishOrderMaster.setCycle(DateUtil.format(serviceStartTime, "yyyy-MM-dd") + "~" + DateUtil.format(serviceEndTime, "yyyy-MM-dd"));
            replenishOrderMaster.setIsPush(1);
            replenishOrderMaster.setIsPublish(1);
            replenishOrderMaster.setExamine(1);
            replenishOrderMaster.setServiceStartTime(serviceStartTime);
            replenishOrderMaster.setServiceEndTime(serviceEndTime);
            paymentOrderMasterMapper.insertSelective(replenishOrderMaster);
            if (month >= merchantStore.getCollectionMonth()) {
                // 明年的管理费
//                Date nextTime = newTime;
                Date nextServiceStartTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1天后", merchantStore.getServiceExpireTime());
                Date nextServiceEndTime = newTime;
//                if (newTime == null) {
//                    nextTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
//                    nextServiceStartTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1天后", merchantStore.getServiceExpireTime());
//                } else {
//                    nextTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1年后", newTime);
//                    nextServiceStartTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1天后", newTime);
//                }
//                nextServiceEndTime = nextTime;
                String nextOrderNo = "MDGLF" + "-" + merchantStore.getUid() + "-" + DateUtil.format(nextServiceStartTime, "yyyyMMdd") + "-" + DateUtil.format(nextServiceEndTime, "yyyyMMdd");
                PaymentOrderMaster nextReplenishOrderMaster = new PaymentOrderMaster();
                nextReplenishOrderMaster.setOrderNo(nextOrderNo);
                nextReplenishOrderMaster.setMerchantId(Integer.valueOf(merchantStore.getMerchantId()));
                nextReplenishOrderMaster.setMerchantName(merchant.getName());
                nextReplenishOrderMaster.setMerchantStoreId(merchantStore.getId());
                nextReplenishOrderMaster.setMerchantStoreName(merchantStore.getName());
                nextReplenishOrderMaster.setMerchantStoreUid(merchantStore.getUid());
                nextReplenishOrderMaster.setMerchantStoreMobile(merchantStore.getMobile());
                nextReplenishOrderMaster.setRegionalManagerId(regionalManager.getId());
                nextReplenishOrderMaster.setRegionalManagerName(regionalManager.getName());
                nextReplenishOrderMaster.setRegionalManagerMobile(regionalManager.getMobile());
                nextReplenishOrderMaster.setPaymentTypeId(1);
                nextReplenishOrderMaster.setPaymentTypeName("管理费");
                nextReplenishOrderMaster.setRemark("管理费");
                nextReplenishOrderMaster.setType(1);
                nextReplenishOrderMaster.setSend(2);
//        nextReplenishOrderMaster.setExpireTime(DateUtil.parse(DateUtil.format(DateUtil.endOfMonth(DateUtil.offset(nextTime, DateField.MONTH, -1)), "yyyy-MM-dd"), "yyyy-MM-dd"));
                if (StatusTwoEnum.IN_CAMP.getStatus().equals(Integer.valueOf(statusId))) {
                    nextReplenishOrderMaster.setExpireTime(DateUtil.parse(DateUtil.format(DateUtil.endOfMonth(DateUtil.offset(new Date(), DateField.MONTH, 1)), "yyyy-MM-dd"), "yyyy-MM-dd"));
                } else {
                    nextReplenishOrderMaster.setExpireTime(DateUtil.parse(DateUtil.format(DateUtil.endOfMonth(new Date()), "yyyy-MM-dd"), "yyyy-MM-dd"));
                }
                nextReplenishOrderMaster.setStatus(1);
                nextReplenishOrderMaster.setDeleted(false);
                nextReplenishOrderMaster.setCreateTime(new Date());
                nextReplenishOrderMaster.setExamineNum(2);
                nextReplenishOrderMaster.setMoney(merchantStore.getManagementExpense());
                nextReplenishOrderMaster.setPayType(1);
                nextReplenishOrderMaster.setCity(merchantStore.getCity());
                nextReplenishOrderMaster.setProvince(merchantStore.getProvince());
                nextReplenishOrderMaster.setArea(merchantStore.getArea());
                nextReplenishOrderMaster.setAdjustmentCount(0);
                nextReplenishOrderMaster.setAddress(merchantStore.getAddress());
                nextReplenishOrderMaster.setPaymentStandardMoney(nextReplenishOrderMaster.getMoney());
                nextReplenishOrderMaster.setCycle(DateUtil.format(nextServiceStartTime, "yyyy-MM-dd") + "~" + DateUtil.format(nextServiceEndTime, "yyyy-MM-dd"));
                nextReplenishOrderMaster.setIsPush(1);
//        if (merchantSt ore.getCollectionMonth().equals(month)) {
                nextReplenishOrderMaster.setIsPublish(1);
//        } else {
//            nextReplenishOrderMaster.setIsPublish(2);
//        }
                nextReplenishOrderMaster.setExamine(1);
                nextReplenishOrderMaster.setServiceStartTime(nextServiceStartTime);
                nextReplenishOrderMaster.setServiceEndTime(nextServiceEndTime);
                paymentOrderMasterMapper.insertSelective(nextReplenishOrderMaster);
                return ApiRes.successResponse();
            }
        } else {
            // 明年的管理费
//            Date nextTime;
//            Date serviceStartTime;
//            Date serviceEndTime;
//            if (newTime == null) {
            Date nextTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1年后", merchantStore.getServiceExpireTime());
            Date serviceStartTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1天后", merchantStore.getServiceExpireTime());
//            } else {
//                nextTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1年后", newTime);
//                serviceStartTime = com.ynzyq.yudbadmin.util.DateUtil.endTime("1天后", newTime);
//            }
            Date serviceEndTime = nextTime;
            String orderNo = "MDGLF" + "-" + merchantStore.getUid() + "-" + DateUtil.format(serviceStartTime, "yyyyMMdd") + "-" + DateUtil.format(serviceEndTime, "yyyyMMdd");
            PaymentOrderMaster replenishOrderMaster = new PaymentOrderMaster();
            replenishOrderMaster.setOrderNo(orderNo);
            replenishOrderMaster.setMerchantId(Integer.valueOf(merchantStore.getMerchantId()));
            replenishOrderMaster.setMerchantName(merchant.getName());
            replenishOrderMaster.setMerchantStoreId(merchantStore.getId());
            replenishOrderMaster.setMerchantStoreName(merchantStore.getName());
            replenishOrderMaster.setMerchantStoreUid(merchantStore.getUid());
            replenishOrderMaster.setMerchantStoreMobile(merchantStore.getMobile());
            replenishOrderMaster.setRegionalManagerId(regionalManager.getId());
            replenishOrderMaster.setRegionalManagerName(regionalManager.getName());
            replenishOrderMaster.setRegionalManagerMobile(regionalManager.getMobile());
            replenishOrderMaster.setPaymentTypeId(1);
            replenishOrderMaster.setPaymentTypeName("管理费");
            replenishOrderMaster.setRemark("管理费");
            replenishOrderMaster.setType(1);
            replenishOrderMaster.setSend(2);
            replenishOrderMaster.setExpireTime(DateUtil.parse(DateUtil.format(DateUtil.endOfMonth(new Date()), "yyyy-MM-dd"), "yyyy-MM-dd"));
            replenishOrderMaster.setStatus(1);
            replenishOrderMaster.setDeleted(false);
            replenishOrderMaster.setCreateTime(new Date());
            replenishOrderMaster.setExamineNum(2);
            replenishOrderMaster.setMoney(merchantStore.getManagementExpense());
            replenishOrderMaster.setPayType(1);
            replenishOrderMaster.setCity(merchantStore.getCity());
            replenishOrderMaster.setProvince(merchantStore.getProvince());
            replenishOrderMaster.setArea(merchantStore.getArea());
            replenishOrderMaster.setAdjustmentCount(0);
            replenishOrderMaster.setAddress(merchantStore.getAddress());
            replenishOrderMaster.setPaymentStandardMoney(replenishOrderMaster.getMoney());
            replenishOrderMaster.setCycle(DateUtil.format(serviceStartTime, "yyyy-MM-dd") + "~" + DateUtil.format(serviceEndTime, "yyyy-MM-dd"));
            replenishOrderMaster.setIsPush(1);
            replenishOrderMaster.setIsPublish(1);
            replenishOrderMaster.setExamine(1);
            replenishOrderMaster.setServiceStartTime(serviceStartTime);
            replenishOrderMaster.setServiceEndTime(serviceEndTime);
            paymentOrderMasterMapper.insertSelective(replenishOrderMaster);
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes updateStoreStatusTwo(UpdateStatusTwoDTO updateStatusTwoDTO) {
        if (updateStatusTwoDTO == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String statusTwo = updateStatusTwoDTO.getStatusTwo();
        if (StringUtils.isEmpty(statusTwo)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String storeId = updateStatusTwoDTO.getStoreId();
        if (StringUtils.isEmpty(storeId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(storeId);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");
        }
        // 修改门店二级状态
        MerchantStore updateMerchantStore = new MerchantStore();
        updateMerchantStore.setId(Integer.parseInt(updateStatusTwoDTO.getStoreId()));
        updateMerchantStore.setStatusTwo(Integer.parseInt(updateStatusTwoDTO.getStatusTwo()));
        if (merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore) == 0) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "修改失敗", "");
        }
        // 添加状态变更记录
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        MerchantStoreStatusTime merchantStoreStatusTime = new MerchantStoreStatusTime();
        merchantStoreStatusTime.setStatus(1);
        merchantStoreStatusTime.setMerchantStoreId(Integer.valueOf(storeId));
        merchantStoreStatusTime.setStoreStatusTwo(Integer.valueOf(statusTwo));
        merchantStoreStatusTime.setCreateTime(new Date());
        merchantStoreStatusTime.setCreateUser(loginUserInfo.getId());
        merchantStoreStatusTimeMapper.insertSelective(merchantStoreStatusTime);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }

    @Override
    public ApiRes detail(DetailDto dto) {
        if (dto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = dto.getId();
        MerchantStoreDetailVo merchantStoreDetailVo = merchantStoreMapper.singleMerchantStore(Integer.parseInt(id));
        if (merchantStoreDetailVo == null) {
            merchantStoreDetailVo = merchantStoreMapper.queryDetail2(id);
            if (merchantStoreDetailVo == null) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");
            }
            List<ChargingStandardVo> chargingStandardVoList = new ArrayList<>();
            List<MerchantStoreOrderVo> merchantStoreOrderVos = new ArrayList<>();
            merchantStoreDetailVo.setChargingStandardVoList(chargingStandardVoList);
            merchantStoreDetailVo.setMerchantStoreOrderVos(merchantStoreOrderVos);
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", merchantStoreDetailVo);
        }
        merchantStoreDetailVo.setProvince(StringUtils.isBlank(merchantStoreDetailVo.getProvince()) ? "" : merchantStoreDetailVo.getProvince());
        merchantStoreDetailVo.setCity(StringUtils.isBlank(merchantStoreDetailVo.getCity()) ? "" : merchantStoreDetailVo.getCity());
        merchantStoreDetailVo.setArea(StringUtils.isBlank(merchantStoreDetailVo.getArea()) ? "" : merchantStoreDetailVo.getArea());
        merchantStoreDetailVo.setStatus(StatusTwoEnum.getStatusDesc(Integer.parseInt(merchantStoreDetailVo.getStatus())));
        merchantStoreDetailVo.setContractStatus(ContractStatusEnum.getStatusDesc(Integer.parseInt(merchantStoreDetailVo.getContractStatus())));
        merchantStoreDetailVo.setStatusTwoDesc(NewStatusTwoEnum.getStatusDesc(Integer.parseInt(merchantStoreDetailVo.getStatusTwo())));

        merchantStoreDetailVo.setSeatCount(StringUtils.isBlank(merchantStoreDetailVo.getSeatCount()) ? "" : merchantStoreDetailVo.getSeatCount());
        merchantStoreDetailVo.setHllCode(StringUtils.isBlank(merchantStoreDetailVo.getHllCode()) ? "" : merchantStoreDetailVo.getHllCode());
        merchantStoreDetailVo.setMtId(StringUtils.isBlank(merchantStoreDetailVo.getMtId()) ? "" : merchantStoreDetailVo.getMtId());
        merchantStoreDetailVo.setElmId(StringUtils.isBlank(merchantStoreDetailVo.getElmId()) ? "" : merchantStoreDetailVo.getElmId());
        merchantStoreDetailVo.setDzdpId(StringUtils.isBlank(merchantStoreDetailVo.getDzdpId()) ? "" : merchantStoreDetailVo.getDzdpId());
        merchantStoreDetailVo.setRemark(StringUtils.isBlank(merchantStoreDetailVo.getRemark()) ? "" : merchantStoreDetailVo.getRemark());

        List<ChargingStandardVo> chargingStandardVoList = new ArrayList<>();
        //管理费
        ChargingStandardVo chargingStandardVoManagementExpense = chargingStandardVo("管理费", merchantStoreDetailVo.getManagementExpense(), "", "");
        chargingStandardVoList.add(chargingStandardVoManagementExpense);
        //商户通
        ChargingStandardVo chargingStandardVoMerchantLink = chargingStandardVo("商户通", "1500/年/账户", merchantStoreDetailVo.getMerchantLink(), "");
        chargingStandardVoList.add(chargingStandardVoMerchantLink);
        //云学堂
        List<CloudSchool> cloudSchools = merchantStoreCloudSchoolMapper.queryDay(id);
        ChargingStandardVo chargingStandardVocloudSchools = chargingStandardVo("云学堂", merchantStoreDetailVo.getCloudSchoolMoney() + "/年/账户", "", "");
        if (cloudSchools.size() > 0) {
            chargingStandardVocloudSchools.setOpen("是");
            chargingStandardVocloudSchools.setNum(cloudSchools.size() + "");
        } else {
            chargingStandardVocloudSchools.setOpen("否");
            chargingStandardVocloudSchools.setNum("0");
        }
        chargingStandardVoList.add(chargingStandardVocloudSchools);
        merchantStoreDetailVo.setChargingStandardVoList(chargingStandardVoList);
        List<MerchantStoreOrderVo> merchantStoreOrderVoList = new ArrayList<>();
        //查询缴费记录
        List<PaymentOrderMaster> paymentOrderMasterList = paymentOrderMasterMapper.queryOrderInfoByStoreId(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        paymentOrderMasterList.stream().forEach(
                paymentOrderMaster -> {
                    MerchantStoreOrderVo merchantStoreOrderVo = new MerchantStoreOrderVo();
                    merchantStoreOrderVo.setPayTypeName(paymentOrderMaster.getPaymentTypeName());
                    merchantStoreOrderVo.setCycle(paymentOrderMaster.getCycle());
                    merchantStoreOrderVo.setMoney(paymentOrderMaster.getPaymentStandardMoney() + "");
                    if (paymentOrderMaster.getAdjustmentCount() > 0) {
                        merchantStoreOrderVo.setAdjustment("是");
                    } else {
                        merchantStoreOrderVo.setAdjustment("否");
                    }
                    merchantStoreOrderVo.setAdjustmentMsg(paymentOrderMaster.getAdjustmentMsg());
                    merchantStoreOrderVo.setNeedMoney(paymentOrderMaster.getMoney() + "");
                    merchantStoreOrderVo.setAlerdMoney(paymentOrderMaster.getMoney() + "");
                    merchantStoreOrderVo.setPayTime(sdf.format(paymentOrderMaster.getPayTime()));
                    merchantStoreOrderVoList.add(merchantStoreOrderVo);
                }
        );
        merchantStoreDetailVo.setMerchantStoreOrderVos(merchantStoreOrderVoList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", merchantStoreDetailVo);
    }

    public ChargingStandardVo chargingStandardVo(String name, String money, String open, String num) {
        ChargingStandardVo chargingStandardVo = new ChargingStandardVo();
        chargingStandardVo.setPayType(name);
        chargingStandardVo.setMoney(money);
        chargingStandardVo.setOpen(open);
        chargingStandardVo.setNum(num);
        return chargingStandardVo;
    }


    @Override
    public ApiRes<CloudSchoolQueryVo> cloudSchoolQuery(DetailDto dto) {

        if (dto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = dto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");
        }
        List<CloudSchool> cloudSchools = merchantStoreCloudSchoolMapper.queryDay(id);
        CloudSchoolQueryVo cloudSchoolQueryVo = new CloudSchoolQueryVo();
        cloudSchoolQueryVo.setUid(merchantStore.getUid());
        cloudSchoolQueryVo.setCloudSchools(cloudSchools);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", cloudSchoolQueryVo);
    }

    @Override
    public ApiRes deleteCloudSchool(DetailDto dto) {
        if (dto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        MerchantStoreCloudSchool merchantStoreCloudSchool = merchantStoreCloudSchoolMapper.selectByPrimaryKey(dto.getId());
        if (merchantStoreCloudSchool == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "删除新不存在", "");
        }
        merchantStoreCloudSchool.setStatus(2);
        merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "删除成功", "");

    }

    @Override
    public ApiRes addCloudSchool(AddCloudSchoolDto dto) {
        if (dto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String uid = dto.getUid();

        MerchantStore merchantStore = merchantStoreMapper.queryByUid(uid);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息不存在", "");
        }
        String startTime = dto.getStartTime();
        String endTime = dto.getEndTime();
        MerchantStoreCloudSchool merchantStoreCloudSchool = new MerchantStoreCloudSchool();

        merchantStoreCloudSchool.setMerchantStoreId(merchantStore.getId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            merchantStoreCloudSchool.setCreateDate(sdf.parse(startTime));
            merchantStoreCloudSchool.setEndTime(sdf.parse(endTime));
            merchantStoreCloudSchool.setCreateTime(new Date());
            merchantStoreCloudSchool.setStatus(1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        merchantStoreCloudSchoolMapper.insertSelective(merchantStoreCloudSchool);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "添加成功", "");
    }

    @Override
    public ApiRes<List<MerchantListVo>> merchantList(MerchantListDto merchantListDto) {
        List<MerchantListVo> merchantListVoList = merchantMapper.queryByMerchantListVoList(merchantListDto);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", merchantListVoList);
    }

    @Override
    public ApiRes addMerchantStore(AddMerchantStoreDto addMerchantStoreDto) {
        if (addMerchantStoreDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String uid = addMerchantStoreDto.getUid();
        //先查询该授权号是否有用户存在
        MerchantStore merchantStore = merchantStoreMapper.queryByUid(uid);
        if (merchantStore != null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "授权号已经存在", "");
        }
        merchantStore = new MerchantStore();
        if (StringUtils.isNotBlank(addMerchantStoreDto.getType())) {
            merchantStore.setType(addMerchantStoreDto.getType());
        }
        merchantStore.setUid(uid);
        String merchantId = addMerchantStoreDto.getMerchantId();
        if (StringUtils.isEmpty(merchantId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择加盟商", "");
        }
        merchantStore.setMerchantId(Integer.valueOf(merchantId));
        String province = addMerchantStoreDto.getProvince();
        if (StringUtils.isNotBlank(province)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择省份", "");
            merchantStore.setProvince(province);
        }

        String city = addMerchantStoreDto.getCity();
        if (StringUtils.isNotBlank(city)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择城市", "");
            merchantStore.setCity(city);
        }
        String address = addMerchantStoreDto.getAddress();
        if (StringUtils.isNotBlank(address)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择地址", "");
            merchantStore.setAddress(address);
        }
        String area = addMerchantStoreDto.getArea();
        if (StringUtils.isNotBlank(address)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择区", "");
            merchantStore.setArea(area);
        }
        String status = addMerchantStoreDto.getStatus();
        if (StringUtils.isEmpty(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择状态", "");
        }
        String statusTwo = addMerchantStoreDto.getStatusTwo();
        if (StringUtils.isEmpty(statusTwo)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择二级状态", "");
        }
        merchantStore.setStatus(Integer.valueOf(status));
        merchantStore.setStatusTwo(Integer.valueOf(statusTwo));
        AgentManagerDTO agentManager = null;
        if (StringUtils.isNotBlank(province) && StringUtils.isNotBlank(city) && StringUtils.isNotBlank(area)) {
            agentManager = merchantStoreMapper.getAgentManager(province, city, area);
            if (agentManager == null) {
                return ApiRes.failResponse("无法自动获取该省市区的区域经理和代理，请联系管理员");
            }
        }
//        String regionalManagerId = agentManager.getManagerId();
//        if (StringUtils.isEmpty(regionalManagerId)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择区域经理", "");
//        }
        String signatory = addMerchantStoreDto.getSignatory();
        if (StringUtils.isEmpty(signatory)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请填写签约人", "");
        }
        merchantStore.setSignatory(signatory);
        String idNumber = addMerchantStoreDto.getIdNumber();
        if (StringUtils.isEmpty(idNumber)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请填写证件号", "");
        }
        merchantStore.setIdNumber(idNumber);
        String mobile = addMerchantStoreDto.getMobile();
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(mobile)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请填写正确的签约人手机号", "");
        }
        merchantStore.setMobile(mobile);
        String signTime = addMerchantStoreDto.getSignTime();
        if (StringUtils.isEmpty(signTime)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择签约时间", "");
        }
        String expireTime = addMerchantStoreDto.getExpireTime();
        if (StringUtils.isEmpty(expireTime)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择到期时间", "");
        }
        String serviceExpireTime = addMerchantStoreDto.getServiceExpireTime();
        if (StringUtils.isEmpty(serviceExpireTime)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择服务到期时间", "");
        }
        if (StringUtils.isNotBlank(addMerchantStoreDto.getSeatCount())) {
            merchantStore.setSeatCount(Integer.parseInt(addMerchantStoreDto.getSeatCount()));
        }
        if (StringUtils.isNotBlank(addMerchantStoreDto.getHllCode())) {
            merchantStore.setHllCode(addMerchantStoreDto.getHllCode());
        }
        if (StringUtils.isNotBlank(addMerchantStoreDto.getMtId())) {
            merchantStore.setMtId(addMerchantStoreDto.getMtId());
        }
        if (StringUtils.isNotBlank(addMerchantStoreDto.getElmId())) {
            merchantStore.setElmId(addMerchantStoreDto.getElmId());
        }
        if (StringUtils.isNotBlank(addMerchantStoreDto.getDzdpId())) {
            merchantStore.setDzdpId(addMerchantStoreDto.getDzdpId());
        }
        if (StringUtils.isNotBlank(addMerchantStoreDto.getRemark())) {
            merchantStore.setRemark(addMerchantStoreDto.getRemark());
        }
        try {
            merchantStore.setSignTime(sdf.parse(signTime));
            merchantStore.setStartTime(sdf.parse(signTime));
            merchantStore.setExpireTime(sdf.parse(expireTime));
            merchantStore.setServiceExpireTime(sdf.parse(serviceExpireTime));
            if (StringUtils.isNotBlank(addMerchantStoreDto.getOpenTime())) {
                merchantStore.setOpenTime(sdf.parse(addMerchantStoreDto.getOpenTime()));
            }
            if (StringUtils.isNotBlank(addMerchantStoreDto.getCloseTime())) {
                merchantStore.setCloseTime(sdf.parse(addMerchantStoreDto.getCloseTime()));
            }
            if (StringUtils.isNotBlank(addMerchantStoreDto.getRelocationTime())) {
                merchantStore.setRelocationTime(sdf.parse(addMerchantStoreDto.getRelocationTime()));
            }
            if (StringUtils.isNotBlank(addMerchantStoreDto.getPauseTime())) {
                merchantStore.setPauseTime(sdf.parse(addMerchantStoreDto.getPauseTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String managementExpense = addMerchantStoreDto.getManagementExpense();
        if (StringUtils.isEmpty(managementExpense)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入管理费额度", "");
        }
        merchantStore.setContractStatus(1);
        merchantStore.setMerchantLink(2);
        merchantStore.setManagementExpense(new BigDecimal(managementExpense));
        merchantStore.setNeedManagementExpense(new BigDecimal(addMerchantStoreDto.getAlreadyManagementExpense()));

        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        merchantStore.setCreateUser(loginUserInfo.getId());
        merchantStore.setCreateTime(new Date());
        if (agentManager != null) {
            merchantStore.setAgentId(agentManager.getAgentId());
            merchantStore.setAgentName(agentManager.getAgentName());
            merchantStore.setAgentAreaId(agentManager.getAgentAreaId());
        }
        merchantStore.setFranchiseFee(new BigDecimal(addMerchantStoreDto.getFranchiseFee()));
        merchantStore.setBondMoney(new BigDecimal(addMerchantStoreDto.getBondMoney()));
        // 以下是页面没有添加的参数，给用默认值
//        merchantStore.setFranchiseFee(BigDecimal.ZERO);
//        merchantStore.setBondMoney(BigDecimal.ZERO);
        merchantStore.setMerchantMoney(BigDecimal.ZERO);
        merchantStore.setIsPreferential(2);
        merchantStoreMapper.insertSelective(merchantStore);

        if (agentManager != null) {
            MerchantStoreRegionalManager merchantStoreRegionalManager = new MerchantStoreRegionalManager();
            merchantStoreRegionalManager.setMerchantStoreId(merchantStore.getId());
            merchantStoreRegionalManager.setRegionalManagerId(agentManager.getManagerId());
            merchantStoreRegionalManager.setStatus(1);
            merchantStoreRegionalManager.setCreateUser(loginUserInfo.getId());
            merchantStoreRegionalManager.setCreateTime(new Date());
            merchantStoreRegionalManagerMapper.insertSelective(merchantStoreRegionalManager);
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO("1", merchantStore.getUid(), "1");
        String msg = iForeignService.userInfo(userInfoDTO);
        if (!StringUtils.equals("success", msg)) {
            ApiRes.failResponse("鱼店宝添加门店成功，门店数据同步金蝶失败，原因：" + msg);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "添加成功", "");
    }

    @Override
    public ApiRes editMerchantStore(EditMerchantStoreDto editMerchantStoreDto) {

        if (editMerchantStoreDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String id = editMerchantStoreDto.getId();
        //先查询该授权号是否有用户存在
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息不存在", "");
        }
        if (StringUtils.isNotBlank(editMerchantStoreDto.getType())) {
            merchantStore.setType(editMerchantStoreDto.getType());
        }
        String province = editMerchantStoreDto.getProvince();
        if (StringUtils.isNotBlank(province)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择省份", "");
            merchantStore.setProvince(province);
        }
        String city = editMerchantStoreDto.getCity();
        if (StringUtils.isNotBlank(city)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择城市", "");
            merchantStore.setCity(city);
        }
        String address = editMerchantStoreDto.getAddress();
        if (StringUtils.isNotBlank(address)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择地址", "");
            merchantStore.setAddress(address);
        }
        String area = editMerchantStoreDto.getArea();
        log.info("area={}", area);
        if (StringUtils.isNotBlank(area)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择区县", "");
            merchantStore.setArea(area);
        }
        String statusId = editMerchantStoreDto.getStatus();
        if (StringUtils.isEmpty(statusId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择状态", "");
        }
        String statusTwo = editMerchantStoreDto.getStatusTwo();
        if (StringUtils.isEmpty(statusTwo)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择二级状态", "");
        }
//        String regionalManagerId = editMerchantStoreDto.getRegionalManagerId();
//        if (StringUtils.isEmpty(regionalManagerId)) {
//            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择区域经理", "");
//        }
        AgentManagerDTO agentManager = null;
        if (StringUtils.isNotBlank(province) && StringUtils.isNotBlank(city) && StringUtils.isNotBlank(area)) {
            agentManager = merchantStoreMapper.getAgentManager(province, city, area);
            if (agentManager == null) {
                return ApiRes.failResponse("无法自动获取该省市区的区域经理和代理，请联系管理员");
            }
        }
        String signatory = editMerchantStoreDto.getSignatory();
        if (StringUtils.isEmpty(signatory)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请填写签约人", "");
        }
        merchantStore.setSignatory(signatory);

        String mobile = editMerchantStoreDto.getMobile();
        if (!PhoneFormatCheckUtils.isChinaPhoneLegal(mobile)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请填写正确的签约人手机号", "");
        }
        merchantStore.setMobile(mobile);

        String idNumber = editMerchantStoreDto.getIdNumber();
        if (StringUtils.isEmpty(idNumber)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请填写证件号", "");
        }
        merchantStore.setIdNumber(idNumber);

        String signTime = editMerchantStoreDto.getSignTime();
        if (StringUtils.isEmpty(signTime)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择签约时间", "");
        }
        String expireTime = editMerchantStoreDto.getExpireTime();
        if (StringUtils.isEmpty(expireTime)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择到期时间", "");
        }
        String serviceExpireTime = editMerchantStoreDto.getServiceExpireTime();
        if (StringUtils.isEmpty(serviceExpireTime)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请选择到期时间", "");
        }
        if (StringUtils.isNotBlank(editMerchantStoreDto.getSeatCount())) {
            merchantStore.setSeatCount(Integer.parseInt(editMerchantStoreDto.getSeatCount()));
        }
        if (StringUtils.isNotBlank(editMerchantStoreDto.getHllCode())) {
            merchantStore.setHllCode(editMerchantStoreDto.getHllCode());
        }
        if (StringUtils.isNotBlank(editMerchantStoreDto.getMtId())) {
            merchantStore.setMtId(editMerchantStoreDto.getMtId());
        }
        if (StringUtils.isNotBlank(editMerchantStoreDto.getElmId())) {
            merchantStore.setElmId(editMerchantStoreDto.getElmId());
        }
        if (StringUtils.isNotBlank(editMerchantStoreDto.getDzdpId())) {
            merchantStore.setDzdpId(editMerchantStoreDto.getDzdpId());
        }
        if (StringUtils.isNotBlank(editMerchantStoreDto.getRemark())) {
            merchantStore.setRemark(editMerchantStoreDto.getRemark());
        }
        try {
            merchantStore.setSignTime(sdf.parse(signTime));
            merchantStore.setStartTime(sdf.parse(signTime));
            merchantStore.setExpireTime(sdf.parse(expireTime));
            merchantStore.setServiceExpireTime(sdf.parse(serviceExpireTime));
            if (StringUtils.isNotBlank(editMerchantStoreDto.getOpenTime())) {
                merchantStore.setOpenTime(sdf.parse(editMerchantStoreDto.getOpenTime()));
                long betweenDay = cn.hutool.core.date.DateUtil.between(cn.hutool.core.date.DateUtil.parse(editMerchantStoreDto.getOpenTime(), "yyyy-MM-dd"), cn.hutool.core.date.DateUtil.parse(signTime, "yyyy-MM-dd"), DateUnit.DAY);
                if (betweenDay > 365) {
                    merchantStore.setDelayedOpen("延期开业");
                } else {
                    merchantStore.setDelayedOpen("非延期开业");
                }
            }
            if (StringUtils.isNotBlank(editMerchantStoreDto.getCloseTime())) {
                merchantStore.setCloseTime(sdf.parse(editMerchantStoreDto.getCloseTime()));
            }
            if (StringUtils.isNotBlank(editMerchantStoreDto.getRelocationTime())) {
                merchantStore.setRelocationTime(sdf.parse(editMerchantStoreDto.getRelocationTime()));
            }
            if (StringUtils.isNotBlank(editMerchantStoreDto.getPauseTime())) {
                merchantStore.setPauseTime(sdf.parse(editMerchantStoreDto.getPauseTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String managementExpense = editMerchantStoreDto.getManagementExpense();
        if (StringUtils.isEmpty(managementExpense)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入管理费额度", "");
        }

        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        if (agentManager != null) {
            if (agentManager.getIsMapping().equals("1")) {
                merchantStore.setAgentId(agentManager.getAgentId());
                merchantStore.setAgentName(agentManager.getAgentName());
                merchantStore.setAgentAreaId(agentManager.getAgentAreaId());
                merchantStoreRegionalManagerMapper.updateStatusByStore(merchantStore.getId() + "");

                MerchantStoreRegionalManager merchantStoreRegionalManager = new MerchantStoreRegionalManager();
                merchantStoreRegionalManager.setMerchantStoreId(merchantStore.getId());
                merchantStoreRegionalManager.setRegionalManagerId(agentManager.getManagerId());
                merchantStoreRegionalManager.setStatus(1);
                merchantStoreRegionalManager.setCreateUser(loginUserInfo.getId());
                merchantStoreRegionalManager.setCreateTime(new Date());
                merchantStoreRegionalManagerMapper.insertSelective(merchantStoreRegionalManager);
            }
        }
        merchantStore.setManagementExpense(new BigDecimal(managementExpense));
        // 由未开业变更为在营、迁址、暂停经营时，在变更时系统生成对应的管理费账单，不推送城区经理，在开业后的 收款月份1号同步推送
//        if (StatusTwoEnum.UNOPENED.getStatus().equals(merchantStore.getStatus())) {
//            if (StatusTwoEnum.IN_CAMP.getStatus().equals(Integer.valueOf(statusId))
//                    || StatusTwoEnum.RELOCATION.getStatus().equals(Integer.valueOf(statusId))
//                    || StatusTwoEnum.SUSPEND_BUSINESS.getStatus().equals(Integer.valueOf(statusId))) {
//                // 新建管理费账单
//                ApiRes apiRes = createOrder(merchantStore, Integer.valueOf(statusId));
//                if (apiRes.getCode() != CommonConstant.SUCCESS_CODE) {
//                    return apiRes;
//                }
//            }
//        }
        merchantStore.setStatus(Integer.valueOf(statusId));
        merchantStore.setStatusTwo(Integer.valueOf(statusTwo));
        merchantStore.setNeedManagementExpense(new BigDecimal(editMerchantStoreDto.getAlreadyManagementExpense()));
        merchantStore.setFranchiseFee(new BigDecimal(editMerchantStoreDto.getFranchiseFee()));
        merchantStore.setBondMoney(new BigDecimal(editMerchantStoreDto.getBondMoney()));
        merchantStore.setUpdateUser(loginUserInfo.getId());
        merchantStore.setUpdateTime(new Date());
        merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);

        UserInfoDTO userInfoDTO = new UserInfoDTO("1", merchantStore.getUid(), "2");
        String msg = iForeignService.userInfo(userInfoDTO);
        if (!StringUtils.equals("success", msg)) {
            ApiRes.failResponse("鱼店宝修改门店成功，门店数据同步金蝶失败，原因：" + msg);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }

    @Override
    public ApiRes<EditMerchantStoreQueryVo> editMerchantStoreQuery(DetailDto dto) {
        if (dto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = dto.getId();
        EditMerchantStoreQueryVo editMerchantStoreQueryVo = merchantStoreMapper.editMerchantStoreQuery(id);
        if (editMerchantStoreQueryVo == null) {
            MerchantStoreDetailVo merchantStoreDetailVo = merchantStoreMapper.queryDetail2(id);
            if (merchantStoreDetailVo == null) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");
            }
            editMerchantStoreQueryVo = new EditMerchantStoreQueryVo();
            BeanUtils.copyProperties(merchantStoreDetailVo, editMerchantStoreQueryVo);
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", editMerchantStoreQueryVo);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", editMerchantStoreQueryVo);
    }

    @Override
    public ApiRes renewal(RenewalDto renewalDto) {
        if (renewalDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = renewalDto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");
        }
        String managementExpenseNew = renewalDto.getManagementExpense();
        if (StringUtils.isEmpty(managementExpenseNew)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "请输入管理费", "");

        }

        //先查询是否缴纳了新的需要管理费，如果缴纳了则可以进行续约
        BigDecimal managementExpense = merchantStore.getManagementExpense();
        BigDecimal alreadyManagementExpense = merchantStore.getAlreadyManagementExpense();
        if (managementExpense.compareTo(alreadyManagementExpense) > 0) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "管理费还未缴纳完整，缴纳完整后在要进行续约", "");
        }
        //查询是否有订单在审核，有审核则不能再次提交
        MerchantStoreExamine merchantStoreExamineOld = merchantStoreExamineMapper.queryByStoreId(id);
        if (merchantStoreExamineOld != null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "已有订单在审核，审核完毕后在提交", "");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        MerchantStoreExamine merchantStoreExamine = new MerchantStoreExamine();
        merchantStoreExamine.setMerchantStoreId(Integer.valueOf(id));
        merchantStoreExamine.setManagementExpense(new BigDecimal(managementExpenseNew));
        merchantStoreExamine.setExamineType(1);
        merchantStoreExamine.setMerchantStoreUid(merchantStore.getUid());
        merchantStoreExamine.setExamine(1);
        merchantStoreExamine.setOldTime(merchantStore.getExpireTime());
        Date newTime = endTime("1年后", merchantStore.getExpireTime());
        merchantStoreExamine.setNewTime(newTime);
        merchantStoreExamine.setStatus(1);
        merchantStoreExamine.setCreateTime(new Date());
        merchantStoreExamine.setCreateUser(loginUserInfo.getId());
        merchantStoreExamine.setCreateName(loginUserInfo.getRealname());

        merchantStoreExamineMapper.insertSelective(merchantStoreExamine);

        MerchantStoreExamineDeail merchantStoreExamineDeail = new MerchantStoreExamineDeail();
        merchantStoreExamineDeail.setExamine(1);
        merchantStoreExamineDeail.setStatus(1);
        merchantStoreExamineDeail.setMerchantStoreExamineId(merchantStoreExamine.getId());
        merchantStoreExamineDeail.setCreateTime(new Date());

        List<MerchantStoreExamineFile> examineFileList = new ArrayList<>();
        List<String> images = renewalDto.getImages();
        List<String> words = renewalDto.getWords();
        images.stream().forEach(
                s -> {
                    MerchantStoreExamineFile merchantStoreExamineFile = new MerchantStoreExamineFile();
                    merchantStoreExamineFile.setType(4);
                    merchantStoreExamineFile.setMerchantStoreExamineId(merchantStoreExamine.getId());
                    merchantStoreExamineFile.setStatus(1);
                    merchantStoreExamineFile.setUrl(s);
                    merchantStoreExamineFile.setCreateTime(new Date());
                    examineFileList.add(merchantStoreExamineFile);
                }
        );
        words.stream().forEach(
                s -> {
                    MerchantStoreExamineFile merchantStoreExamineFile = new MerchantStoreExamineFile();
                    merchantStoreExamineFile.setType(2);
                    merchantStoreExamineFile.setMerchantStoreExamineId(merchantStoreExamine.getId());
                    merchantStoreExamineFile.setStatus(1);
                    merchantStoreExamineFile.setUrl(s);
                    merchantStoreExamineFile.setCreateTime(new Date());
                    examineFileList.add(merchantStoreExamineFile);
                }
        );
        merchantStoreExamineDeailMapper.insertSelective(merchantStoreExamineDeail);
        merchantStoreExamineFileMapper.insertList(examineFileList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "提交审核中", "");
    }

    @Override
    public ApiRes updateCloudSchool(UpdateCloudSchoolDto dto) {

        if (dto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        MerchantStoreCloudSchool merchantStoreCloudSchool = merchantStoreCloudSchoolMapper.selectByPrimaryKey(dto.getId());
        if (merchantStoreCloudSchool == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "该账户不存在", "");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            merchantStoreCloudSchool.setEndTime(sdf.parse(dto.getEndTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        merchantStoreCloudSchoolMapper.updateByPrimaryKeySelective(merchantStoreCloudSchool);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }

    @Override
    public ApiRes<QueryMerchantLinkInfoVo> queryMerchantLinkInfo(DetailDto dto) {
        if (dto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(dto.getId());
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        QueryMerchantLinkInfoVo queryMerchantLinkInfoVo = new QueryMerchantLinkInfoVo();

        queryMerchantLinkInfoVo.setMerchantLinkTime(merchantStore.getMerchantLinkTime());
        queryMerchantLinkInfoVo.setMerchantLinkEndTime(merchantStore.getMerchantLinkEndTime());
        queryMerchantLinkInfoVo.setStatus(merchantStore.getMerchantLink() + "");

        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", queryMerchantLinkInfoVo);
    }

    @Override
    public ApiRes<QueryMerchantLinkInfoVo> editMerchantLinkInfo(EditMerchantLinkInfoDto editMerchantLinkInfoDto) {
        if (editMerchantLinkInfoDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(editMerchantLinkInfoDto.getId());
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        merchantStore.setMerchantLink(Integer.valueOf(editMerchantLinkInfoDto.getStatus()));
        try {
            merchantStore.setMerchantLinkTime(sdf.parse(editMerchantLinkInfoDto.getMerchantLinkTime()));
            merchantStore.setMerchantLinkEndTime(sdf.parse(editMerchantLinkInfoDto.getMerchantLinkEndTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }

    @Override
    public ApiRes submitForReview(SubmitForReviewDto submitForReviewDto, HttpServletRequest httpServletRequest) {

        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();

        if (submitForReviewDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String payTypeId = submitForReviewDto.getPayTypeId();
        if (StringUtils.isEmpty(payTypeId)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String money = submitForReviewDto.getMoney();
        if (StringUtils.isEmpty(money)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        if (new BigDecimal("0.1").compareTo(new BigDecimal(money)) == 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "最小金额为0.1", "");
        }

        String time = submitForReviewDto.getTime();
        if (StringUtils.isEmpty(time)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = submitForReviewDto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        Merchant merchant = merchantMapper.selectByPrimaryKey(merchantStore.getMerchantId());
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商信息错误", "");
        }
        if (StringUtils.equals(payTypeId, "3") && submitForReviewDto.getAccountIds().size() == 0) {
            return ApiRes.failResponse("云学堂账号不能为空");
        }
//        else if (StringUtils.equals(payTypeId, "2") && merchantStore.getManagementExpense().compareTo(new BigDecimal("20000")) >= 0) {
//            return ApiRes.failResponse("该门店管理费金额已大于等于2W，无法开商户通缴费订单");
//        }
        MerchantStoreRegionalManager merchantStoreRegionalManager = merchantStoreRegionalManagerMapper.queryByMerchantStoreId(merchantStore.getId());
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(merchantStoreRegionalManager.getRegionalManagerId());
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
        }

        PaymentType paymentType = paymentTypeMapper.selectByPrimaryKey(payTypeId);
        if (paymentType == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费类型错误", "");
        }
        if (paymentType.getId() == 1 || paymentType.getId() == 2 || paymentType.getId() == 3) {
            //先判断是否有正在缴费的单子，有则续约缴费完才能申请
            PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.queryByStoreIdAndPayTypeId(merchantStore.getId(), paymentType.getId());
            if (paymentOrderMaster != null) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "请先让加盟商缴纳前面相同类型费用在进行缴费申请", "");
            }
        }
        String remark = submitForReviewDto.getRemark();
        // 云学堂需要遍历插入，其他的则只需添加一次
        if (StringUtils.equals(payTypeId, "3") && submitForReviewDto.getAccountIds().size() > 0) {
            for (String accountId : submitForReviewDto.getAccountIds()) {
                addOrder(submitForReviewDto, loginUserInfo, money, time, merchantStore, merchant, regionalManager, paymentType, remark, Integer.parseInt(accountId));
            }
        } else {
            addOrder(submitForReviewDto, loginUserInfo, money, time, merchantStore, merchant, regionalManager, paymentType, remark, null);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "缴费提交成功", "");

    }

    private void addOrder(SubmitForReviewDto submitForReviewDto, LoginUserInfo loginUserInfo, String money, String time, MerchantStore merchantStore, Merchant merchant, RegionalManager regionalManager, PaymentType paymentType, String remark, Integer otherId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //缴费主订单审核
        PaymentOrderMaster paymentOrderMaster = new PaymentOrderMaster();

        paymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));

        paymentOrderMaster.setMerchantId(merchant.getId());
        paymentOrderMaster.setMerchantName(merchant.getName());
        paymentOrderMaster.setMerchantStoreId(merchantStore.getId());
        paymentOrderMaster.setMerchantStoreName(merchantStore.getName());
        paymentOrderMaster.setMerchantStoreUid(merchantStore.getUid());
        paymentOrderMaster.setMerchantStoreMobile(merchantStore.getMobile());
        paymentOrderMaster.setRegionalManagerId(regionalManager.getId());
        paymentOrderMaster.setRegionalManagerName(regionalManager.getName());
        paymentOrderMaster.setRegionalManagerMobile(regionalManager.getMobile());
        paymentOrderMaster.setPaymentTypeId(paymentType.getId());
        paymentOrderMaster.setPaymentTypeName(paymentType.getName());
        paymentOrderMaster.setType(3);
        paymentOrderMaster.setStatus(1);
        paymentOrderMaster.setExamine(2);
        paymentOrderMaster.setProvince(merchantStore.getProvince());
        paymentOrderMaster.setCity(merchantStore.getCity());
        paymentOrderMaster.setArea(merchantStore.getArea());
        paymentOrderMaster.setSend(2);
        paymentOrderMaster.setDeleted(false);
        paymentOrderMaster.setExamineNum(2);
        paymentOrderMaster.setMoney(new BigDecimal(money));
        try {
            paymentOrderMaster.setExpireTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paymentOrderMaster.setSend(2);
        paymentOrderMaster.setExamine(2);
        paymentOrderMaster.setRemark(remark);
        paymentOrderMaster.setCreateTime(new Date());
        paymentOrderMaster.setCreateUser(loginUserInfo.getId());
        paymentOrderMaster.setDeleted(false);
        paymentOrderMaster.setOtherId(otherId);
        paymentOrderMasterMapper.insertSelective(paymentOrderMaster);


        //缴费审核订单
        PaymentOrderExamine paymentOrderExamine = new PaymentOrderExamine();
        paymentOrderExamine.setPaymentTypeId(paymentType.getId());
        paymentOrderExamine.setPaymentOrderMasterId(paymentOrderMaster.getId());
        paymentOrderExamine.setMerchantStoreUid(merchantStore.getUid());
        paymentOrderExamine.setMerchantStoreId(merchantStore.getId());
        paymentOrderExamine.setPaymentTypeName(paymentType.getName());
        paymentOrderExamine.setNewMoney(paymentOrderMaster.getMoney());
        paymentOrderExamine.setExamineType(3);
        paymentOrderExamine.setExamine(1);
        paymentOrderExamine.setStatus(1);
        paymentOrderExamine.setApplyName(loginUserInfo.getRealname());
        paymentOrderExamine.setMsg("新订单审核:" + paymentType.getName() + money + "元");
        paymentOrderExamine.setRemark(remark);
        paymentOrderExamine.setDeleted(false);
        paymentOrderExamine.setApplyId(loginUserInfo.getId());
        paymentOrderExamine.setCreateTime(new Date());
        paymentOrderExamine.setStep(1);
        paymentOrderExamineMapper.insertSelective(paymentOrderExamine);
        //缴费审核订单明细
        PaymentOrderExamineDeail paymentOrderExamineDeail = new PaymentOrderExamineDeail();

        paymentOrderExamineDeail.setPaymentOrderExamineId(paymentOrderExamine.getId());
        paymentOrderExamineDeail.setExamine(1);
        paymentOrderExamineDeail.setStatus(1);
        paymentOrderExamineDeail.setCreateTime(new Date());
        paymentOrderExamineDeail.setDeleted(false);
        paymentOrderExamineDeail.setStep(1);
        paymentOrderExamineDeail.setType(1);
        // 查询该阶段的审核人
        List<String> approveNames = approveProcessMapper.getApproveName(1, paymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), paymentOrderExamineDeail.getType());
        String approveName = approveNames.stream().collect(Collectors.joining(","));
        paymentOrderExamineDeail.setApproveName(approveName);

        paymentOrderExamineDeailMapper.insertSelective(paymentOrderExamineDeail);

        //文件信息存储
        List<PaymentOrderExamineFile> paymentOrderExamineFileList = new ArrayList<>();
        List<String> photos = submitForReviewDto.getPhotos();
        photos.stream().forEach(
                s -> {
                    log.info("photos={}", s.length());
                    PaymentOrderExamineFile paymentOrderExamineFile = new PaymentOrderExamineFile();
                    paymentOrderExamineFile.setPaymentOrderExamineId(paymentOrderExamine.getId());
                    paymentOrderExamineFile.setType(4);
                    paymentOrderExamineFile.setUrl(s);
                    paymentOrderExamineFile.setDeleted(false);
                    paymentOrderExamineFile.setStatus(1);
                    paymentOrderExamineFile.setCreateTime(new Date());
                    paymentOrderExamineFileList.add(paymentOrderExamineFile);
                }
        );

        List<String> video = submitForReviewDto.getVideo();
        video.stream().forEach(
                s -> {
                    log.info("video={}", s.length());
                    PaymentOrderExamineFile paymentOrderExamineFile = new PaymentOrderExamineFile();
                    paymentOrderExamineFile.setPaymentOrderExamineId(paymentOrderExamine.getId());
                    paymentOrderExamineFile.setType(3);
                    paymentOrderExamineFile.setUrl(s);
                    paymentOrderExamineFile.setDeleted(false);
                    paymentOrderExamineFile.setStatus(1);
                    paymentOrderExamineFile.setCreateTime(new Date());
                    paymentOrderExamineFileList.add(paymentOrderExamineFile);
                }
        );

        if (paymentOrderExamineFileList.size() > 0) {
            paymentOrderExamineFileMapper.insertList(paymentOrderExamineFileList);
        }
    }

    @Override
    public ApiRes<PayTypeListVo> payTypeList() {
        List<PayTypeListVo> list = paymentTypeMapper.queryPayTypeListNoId();
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "参数错误", list);


    }

    @Override
    public ApiRes excelSubmitForReview(MultipartFile file, HttpServletRequest httpServletRequest) {
        List<OrderExcelDto> list = ExcelUtils.readExcel("", OrderExcelDto.class, file);
        List<OrderExcelDto> listYes = new ArrayList<>();
        List<OrderExcelDto> listNo = new ArrayList<>();
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        // 未导入数据
        List<String> notExportList = Lists.newArrayList();
        List<String> notExportList2 = Lists.newArrayList();
        if (list.size() > 0) {
            //查收授权号是否都存在
            List<String> merchantStores = merchantStoreMapper.queryUids();
            List<String> payTypeNames = paymentTypeMapper.queryPayTypeName();
            List<ExcelSubmitForReviewVo> excelSubmitForReviewVoList = merchantStoreMapper.ExcelSubmitForReviewVo();
            List<PaymentType> payTypeNameList = paymentTypeMapper.queryList();
            list.stream().forEach(
                    orderExcelDto -> {
                        if (orderExcelDto.getUid().contains(".")) {
                            int index = orderExcelDto.getUid().indexOf(".");
                            orderExcelDto.setUid(StringUtils.substring(orderExcelDto.getUid(), 0, index));
                        }
                        if (merchantStores.contains(orderExcelDto.getUid()) && payTypeNames.contains(orderExcelDto.getPayTypeName())) {
                            listYes.add(orderExcelDto);
                        } else {
                            listNo.add(orderExcelDto);
                        }
                    }
            );

            // 获取数据库中已有的云学堂账号数据，以键值对形式存进map里
            List<ListCloudSchoolDTO> listCloudSchoolDTOS = merchantStoreMapper.listCloudSchool();
            Map<String, Integer> cloudSchoolMap = Maps.newHashMap();
            listCloudSchoolDTOS.forEach(listCloudSchoolDTO -> {
                cloudSchoolMap.put(listCloudSchoolDTO.getMerchantStoreUid() + listCloudSchoolDTO.getAccount(), listCloudSchoolDTO.getId());
            });

            // 获取数据库中已有的门店数据，以键值对形式存进map里
            List<MerchantStore> merchantStoreList = merchantStoreMapper.listMerchantStore();
            Map<String, BigDecimal> storeMap = Maps.newHashMap();
            Map<String, String> storeMap1 = Maps.newHashMap();
            merchantStoreList.forEach(merchantStore -> {
                storeMap.put(merchantStore.getUid(), merchantStore.getManagementExpense());
                storeMap1.put(merchantStore.getUid(), merchantStore.getStatus() + "");
            });
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            listYes.stream().forEach(
                    orderExcelDto -> {
                        excelSubmitForReviewVoList.stream().forEach(
                                excelSubmitForReviewVo -> {
                                    if (orderExcelDto.getUid().equals(excelSubmitForReviewVo.getMerchantStoreUid())) {
                                        payTypeNameList.stream().forEach(
                                                paymentType -> {
                                                    if (paymentType.getName().equals(orderExcelDto.getPayTypeName())) {
//                                                        // 如果缴费类型是云学堂且账号为空，则不导入数据
//                                                        Integer otherId = null;
//                                                        if (StringUtils.equals("云学堂", orderExcelDto.getPayTypeName())) {
//                                                            if (StringUtils.isBlank(orderExcelDto.getAccount())) {
//                                                                notExportList.add(orderExcelDto.getUid());
//                                                                return;
//                                                            } else {
//                                                                if (cloudSchoolMap.keySet().contains(orderExcelDto.getUid() + orderExcelDto.getAccount())) {
//                                                                    otherId = cloudSchoolMap.get(orderExcelDto.getUid() + orderExcelDto.getAccount());
//                                                                } else {
//                                                                    notExportList.add(orderExcelDto.getUid());
//                                                                    return;
//                                                                }
//                                                            }
//                                                        }
//                                                        // 如果缴费类型是商户通且管理费金额大于20000，则不导入数据
//                                                        if (StringUtils.equals("商户通", orderExcelDto.getPayTypeName())) {
//                                                            if (storeMap.keySet().contains(orderExcelDto.getUid())) {
//                                                                BigDecimal managementExpense = storeMap.get(orderExcelDto.getUid());
//                                                                if (new BigDecimal("20000").compareTo(managementExpense) <= 0) {
//                                                                    notExportList2.add(orderExcelDto.getUid());
//                                                                    return;
//                                                                }
//                                                            }
//                                                            // 如果门店状态不是在营的就不能导入订单
//                                                            if (storeMap1.keySet().contains(orderExcelDto.getUid())) {
//                                                                String status = storeMap1.get(orderExcelDto.getUid());
//                                                                if (!"2".equals(status) && !"11".equals(status)) {
//                                                                    notExportList2.add(orderExcelDto.getUid());
//                                                                    return;
//                                                                }
//                                                            }
//                                                        }
                                                        //缴费主订单审核
                                                        PaymentOrderMaster paymentOrderMaster = new PaymentOrderMaster();
                                                        paymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("YDB"));
                                                        paymentOrderMaster.setMerchantId(Integer.valueOf(excelSubmitForReviewVo.getMerchantId()));
                                                        paymentOrderMaster.setMerchantName(excelSubmitForReviewVo.getMaechantName());
                                                        paymentOrderMaster.setMerchantStoreId(Integer.valueOf(excelSubmitForReviewVo.getMerchantStoreId()));
                                                        paymentOrderMaster.setMerchantStoreName(excelSubmitForReviewVo.getMerchantStoreName());
                                                        paymentOrderMaster.setMerchantStoreUid(excelSubmitForReviewVo.getMerchantStoreUid());
                                                        paymentOrderMaster.setMerchantStoreMobile(excelSubmitForReviewVo.getMerchantStoreMobile());
                                                        paymentOrderMaster.setRegionalManagerId(Integer.valueOf(excelSubmitForReviewVo.getRegionalManagerId()));
                                                        paymentOrderMaster.setRegionalManagerName(excelSubmitForReviewVo.getRegionalManagerName());
                                                        paymentOrderMaster.setRegionalManagerMobile(excelSubmitForReviewVo.getRegionalManagerMobile());
                                                        paymentOrderMaster.setPaymentTypeId(paymentType.getId());
                                                        paymentOrderMaster.setPaymentTypeName(paymentType.getName());
                                                        paymentOrderMaster.setType(3);
                                                        paymentOrderMaster.setStatus(1);

                                                        paymentOrderMaster.setAddress(excelSubmitForReviewVo.getAddress());
                                                        paymentOrderMaster.setArea(excelSubmitForReviewVo.getArea());
                                                        paymentOrderMaster.setCity(excelSubmitForReviewVo.getCity());
                                                        paymentOrderMaster.setProvince(excelSubmitForReviewVo.getProvince());

                                                        paymentOrderMaster.setExamine(1);
                                                        paymentOrderMaster.setSend(2);
                                                        paymentOrderMaster.setDeleted(false);
                                                        paymentOrderMaster.setExamineNum(2);
                                                        paymentOrderMaster.setMoney(new BigDecimal(orderExcelDto.getMoney()));
                                                        try {
                                                            paymentOrderMaster.setExpireTime(sdf.parse(orderExcelDto.getExpireTime()));
                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }
                                                        paymentOrderMaster.setSend(2);
                                                        paymentOrderMaster.setRemark(orderExcelDto.getRemark());
                                                        paymentOrderMaster.setCreateTime(new Date());
                                                        paymentOrderMaster.setCreateUser(loginUserInfo.getId());
                                                        paymentOrderMaster.setDeleted(false);
                                                        paymentOrderMaster.setOtherId(null);
                                                        paymentOrderMaster.setIsPush(1);
//                                                        paymentOrderMaster.setIsPublish(2);
                                                        paymentOrderMaster.setIsPublish(1);
                                                        paymentOrderMasterMapper.insertSelective(paymentOrderMaster);


                                                        //缴费审核订单
//                                                        PaymentOrderExamine paymentOrderExamine = new PaymentOrderExamine();
//                                                        paymentOrderExamine.setPaymentTypeId(paymentType.getId());
//                                                        paymentOrderExamine.setPaymentOrderMasterId(paymentOrderMaster.getId());
//                                                        paymentOrderExamine.setMerchantStoreUid(excelSubmitForReviewVo.getMerchantStoreUid());
//                                                        paymentOrderExamine.setMerchantStoreId(Integer.valueOf(excelSubmitForReviewVo.getMerchantStoreId()));
//                                                        paymentOrderExamine.setPaymentTypeName(paymentType.getName());
//                                                        paymentOrderExamine.setNewMoney(paymentOrderMaster.getMoney());
//                                                        paymentOrderExamine.setExamineType(3);
//                                                        paymentOrderExamine.setExamine(1);
//                                                        paymentOrderExamine.setStatus(1);
//                                                        paymentOrderExamine.setStep(1);
//                                                        paymentOrderExamine.setApplyName(loginUserInfo.getRealname());
//                                                        paymentOrderExamine.setMsg("新订单审核:" + paymentType.getName() + orderExcelDto.getMoney() + "元");
//                                                        paymentOrderExamine.setRemark(paymentOrderMaster.getRemark());
//                                                        paymentOrderExamine.setDeleted(false);
//                                                        paymentOrderExamine.setApplyId(loginUserInfo.getId());
//                                                        paymentOrderExamine.setCreateTime(new Date());
//                                                        paymentOrderExamineMapper.insertSelective(paymentOrderExamine);
//
//                                                        //缴费审核订单明细
//                                                        PaymentOrderExamineDeail paymentOrderExamineDeail = new PaymentOrderExamineDeail();
//                                                        paymentOrderExamineDeail.setPaymentOrderExamineId(paymentOrderExamine.getId());
//                                                        paymentOrderExamineDeail.setExamine(1);
//                                                        paymentOrderExamineDeail.setStatus(1);
//                                                        paymentOrderExamineDeail.setCreateTime(new Date());
//                                                        paymentOrderExamineDeail.setDeleted(false);
//                                                        paymentOrderExamineDeail.setStep(1);
//                                                        paymentOrderExamineDeail.setType(1);
//                                                        // 查询该阶段的审核人
//                                                        List<String> approveNames = approveProcessMapper.getApproveName(1, paymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), paymentOrderExamineDeail.getType());
//                                                        String approveName = approveNames.stream().collect(Collectors.joining(","));
//                                                        paymentOrderExamineDeail.setApproveName(approveName);
//                                                        paymentOrderExamineDeailMapper.insertSelective(paymentOrderExamineDeail);
                                                    }
                                                }
                                        );
                                    }
                                }
                        );
                    }
            );
            if (notExportList.size() > 0 || notExportList2.size() > 0) {
                String msg = "导入说明：";
                if (notExportList.size() > 0) {
                    String uid = notExportList.stream().collect(Collectors.joining(","));
                    msg = msg + "授权号为(" + uid + ")的云学堂账号为空，";
                }
                if (notExportList2.size() > 0) {
                    String uid = notExportList2.stream().collect(Collectors.joining(","));
                    msg = msg + "授权号为(" + uid + ")的管理费金额大于等于2w未导入或者未开业";
                }
                log.info("msg：{}", msg);
                return ApiRes.response(CommonConstant.SUCCESS_CODE, msg, msg);
            }
            return ApiRes.response(CommonConstant.SUCCESS_CODE, "请求成功", "");
        }
        return ApiRes.response(CommonConstant.FAIL_CODE, "上传数据为空", "");
    }

    @Override
    public ApiRes merchantSelectBox() {
        List<MerchantSelectBoxVO> merchantSelectBoxVOS = merchantStoreMapper.listMerchantSelectBox();
        if (merchantSelectBoxVOS.size() == 0) {
            merchantSelectBoxVOS = new ArrayList<>();
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", merchantSelectBoxVOS);
    }

    @Override
    public ApiRes updateMerchantDTO(UpdateMerchantDTO updateMerchantDTO) {
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(updateMerchantDTO.getStoreId());
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        Merchant merchant = merchantMapper.selectByPrimaryKey(updateMerchantDTO.getMerchantId());
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "代理商不存在", "");
        }
        MerchantStore updateMerchantStore = new MerchantStore();
        updateMerchantStore.setId(merchantStore.getId());
        updateMerchantStore.setAgentId(merchant.getId());
        updateMerchantStore.setAgentName(merchant.getName());
        if (merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore) == 0) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "修改失败", "");
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "修改成功", "");
    }

    @Override
    public void exportStore(StroePageDto stroePageDto, HttpServletResponse response) {
        String condition = stroePageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            stroePageDto.setCondition(null);
        }
        String startexpireTime = stroePageDto.getStartExpireTime();
        if (StringUtils.isEmpty(startexpireTime)) {
            stroePageDto.setStartExpireTime(null);
            stroePageDto.setEndExpireTime(null);
        } else {
            stroePageDto.setStartExpireTime(stroePageDto.getStartExpireTime() + " 00:00:00");
            stroePageDto.setEndExpireTime(stroePageDto.getEndExpireTime() + " 23:59:59");
        }
        String stareSignTime = stroePageDto.getStartSignTime();
        if (StringUtils.isEmpty(stareSignTime)) {
            stroePageDto.setStartSignTime(null);
            stroePageDto.setEndSignTime(null);
        } else {
            stroePageDto.setStartSignTime(stroePageDto.getStartSignTime() + " 00:00:00");
            stroePageDto.setEndSignTime(stroePageDto.getEndSignTime() + " 23:59:59");
        }
        stroePageDto.setMerchantId(StringUtils.isBlank(stroePageDto.getMerchantId()) ? null : stroePageDto.getMerchantId());
        stroePageDto.setAreaId(StringUtils.isBlank(stroePageDto.getAreaId()) ? null : stroePageDto.getAreaId());
        stroePageDto.setMonth(StringUtils.isBlank(stroePageDto.getMonth()) ? null : stroePageDto.getMonth());
        stroePageDto.setStatus(StringUtils.isBlank(stroePageDto.getStatus()) ? null : stroePageDto.getStatus());
        stroePageDto.setDelayedOpen(StringUtils.isBlank(stroePageDto.getDelayedOpen()) ? null : stroePageDto.getDelayedOpen());
        stroePageDto.setRegion(StringUtils.isBlank(stroePageDto.getRegion()) ? null : stroePageDto.getRegion());
        stroePageDto.setProvince(StringUtils.isBlank(stroePageDto.getProvince()) ? null : stroePageDto.getProvince());
        stroePageDto.setCity(StringUtils.isBlank(stroePageDto.getCity()) ? null : stroePageDto.getCity());
        stroePageDto.setArea(StringUtils.isBlank(stroePageDto.getArea()) ? null : stroePageDto.getArea());
        stroePageDto.setStartServiceExpireTime(StringUtils.isBlank(stroePageDto.getStartServiceExpireTime()) ? null : stroePageDto.getStartServiceExpireTime());
        stroePageDto.setEndServiceExpireTime(StringUtils.isBlank(stroePageDto.getEndServiceExpireTime()) ? null : stroePageDto.getEndServiceExpireTime());
        stroePageDto.setIsApply(StringUtils.isBlank(stroePageDto.getIsApply()) ? null : stroePageDto.getIsApply());
        List<MappingAreaDTO> mappingAreaDTOS = merchantStoreMapper.listMappingArea();
        Map<String, MappingAreaDTO> areaMap = Maps.newHashMap();
        mappingAreaDTOS.forEach(mappingAreaDTO -> {
            areaMap.put(mappingAreaDTO.getProvince() + mappingAreaDTO.getCity() + mappingAreaDTO.getArea(), mappingAreaDTO);
        });
        List<StroePageVo> stroePageVoList;
        if (StringUtils.isBlank(stroePageDto.getAreaId())) {
            stroePageVoList = merchantStoreMapper.findPage(stroePageDto);
        } else {
            stroePageVoList = merchantStoreMapper.findPageByAreaId(stroePageDto);
        }
        stroePageVoList.forEach(
                stroePageVo -> {
                    stroePageVo.setType(StoreTypeEnum.getName(stroePageVo.getType()));
                    stroePageVo.setStatus(StatusTwoEnum.getStatusDesc(Integer.parseInt(stroePageVo.getStatus())));
                    stroePageVo.setContractStatus(ContractStatusEnum.getStatusDesc(Integer.parseInt(stroePageVo.getContractStatus())));
                    stroePageVo.setStatusTwoDesc(NewStatusTwoEnum.getStatusDesc(Integer.parseInt(stroePageVo.getStatusTwo())));
                    if ("1".equals(stroePageVo.getIsPreferential())) {
                        stroePageVo.setIsPreferential("是");
                    } else {
                        stroePageVo.setIsPreferential("否");
                    }
                    stroePageVo.setShowDeliveryAddress(StringUtils.equals("1", stroePageVo.getShowDeliveryAddress()) ? "配送到门店" : "配送到代理");
                    if (areaMap.keySet().contains(stroePageVo.getProvince() + stroePageVo.getCity() + stroePageVo.getArea())) {
                        stroePageVo.setRegion(areaMap.get(stroePageVo.getProvince() + stroePageVo.getCity() + stroePageVo.getArea()).getRegion());
                        stroePageVo.setLevel(areaMap.get(stroePageVo.getProvince() + stroePageVo.getCity() + stroePageVo.getArea()).getLevel());
                    }
                    stroePageVo.setIsApply(StringUtils.equals("1", stroePageVo.getIsApply()) ? "开" : "关");
                }
        );
//        ExcelUtils.writeExcel(response, stroePageVoList, StroePageVo.class, "门店.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("门店", response), StroePageVo.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(stroePageVoList);
    }

    @Override
    public ApiRes updateTime(UpdateTimeDTO updateTimeDTO) {
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(updateTimeDTO.getStoreId());
        if (merchantStore == null) {
            return ApiRes.failResponse("门店不存在");
        }
        if (StringUtils.isBlank(updateTimeDTO.getTime())) {
            if (merchantStoreMapper.updateTime(Integer.parseInt(updateTimeDTO.getType()), merchantStore.getId()) == 0) {
                return ApiRes.failResponse("修改失败");
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            MerchantStore updateMerchantStore = new MerchantStore();
            updateMerchantStore.setId(merchantStore.getId());
            try {
                if (StringUtils.equals("1", updateTimeDTO.getType())) {
                    updateMerchantStore.setOpenTime(sdf.parse(updateTimeDTO.getTime()));
                    long betweenDay = cn.hutool.core.date.DateUtil.between(cn.hutool.core.date.DateUtil.parse(updateTimeDTO.getTime(), "yyyy-MM-dd"), cn.hutool.core.date.DateUtil.parse(cn.hutool.core.date.DateUtil.format(merchantStore.getSignTime(), "yyyy-MM-dd"), "yyyy-MM-dd"), DateUnit.DAY);
                    if (betweenDay > 365) {
                        updateMerchantStore.setDelayedOpen("延期开业");
                    } else {
                        updateMerchantStore.setDelayedOpen("非延期开业");
                    }
                } else if (StringUtils.equals("2", updateTimeDTO.getType())) {
                    updateMerchantStore.setCloseTime(sdf.parse(updateTimeDTO.getTime()));
                } else if (StringUtils.equals("3", updateTimeDTO.getType())) {
                    updateMerchantStore.setRelocationTime(sdf.parse(updateTimeDTO.getTime()));
                } else if (StringUtils.equals("4", updateTimeDTO.getType())) {
                    updateMerchantStore.setPauseTime(sdf.parse(updateTimeDTO.getTime()));
                } else if (StringUtils.equals("5", updateTimeDTO.getType())) {
                    updateMerchantStore.setServiceExpireTime(sdf.parse(updateTimeDTO.getTime()));
                    int month = DateUtil.month(updateMerchantStore.getServiceExpireTime());
                    updateMerchantStore.setCollectionMonth(month + 1);
                    // 当前登录人信息
                    LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
                    log.info("【{}】修改【{}】服务到期时间【{}】为【{}】", loginUserInfo.getRealname(), merchantStore.getId(), merchantStore.getServiceExpireTime(), updateMerchantStore.getServiceExpireTime());
                } else if (StringUtils.equals("6", updateTimeDTO.getType())) {
                    Date time = sdf.parse(updateTimeDTO.getTime());
                    updateMerchantStore.setExpireTime(time);
                    // 合同状态
                    if (time.before(new Date())) {
                        updateMerchantStore.setContractStatus(ContractStatusEnum.AGREEMENT_OVERDUE.getStatus());
                    } else if (time.after(new Date())) {
                        updateMerchantStore.setContractStatus(ContractStatusEnum.IN_PROGRESS.getStatus());
                    }
                } else if (StringUtils.equals("7", updateTimeDTO.getType())) {
                    updateMerchantStore.setOpenAgainTime(sdf.parse(updateTimeDTO.getTime()));
                } else if (StringUtils.equals("8", updateTimeDTO.getType())) {
                    updateMerchantStore.setEstimateTime(sdf.parse(updateTimeDTO.getTime()));
                } else {
                    return ApiRes.failResponse("修改类型错误");
                }
            } catch (ParseException e) {
                log.error("时间转换异常", e);
                return ApiRes.failResponse("时间错误");
            }
            if (merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore) == 0) {
                return ApiRes.failResponse("修改失败");
            }
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<CloudSchoolAccountVO> cloudSchoolAccount(CloudSchoolAccountDTO cloudSchoolAccountDTO) {
        Example example = new Example(MerchantStoreCloudSchool.class);
        example.createCriteria().andEqualTo("merchantStoreId", cloudSchoolAccountDTO.getStoreId())
                .andEqualTo("status", StatusEnum.ENABLE.getStatus());
        List<MerchantStoreCloudSchool> storeCloudSchools = merchantStoreCloudSchoolMapper.selectByExample(example);
        List<CloudSchoolAccountVO> list = Lists.newArrayList();
        storeCloudSchools.forEach(merchantStoreCloudSchool -> {
            CloudSchoolAccountVO cloudSchoolAccountVO = new CloudSchoolAccountVO();
            cloudSchoolAccountVO.setId(merchantStoreCloudSchool.getId().toString());
            cloudSchoolAccountVO.setAccount(merchantStoreCloudSchool.getAccount());
            list.add(cloudSchoolAccountVO);
        });
        return ApiRes.successResponseData(list);
    }

    @Override
    public ApiRes updateIsPreferential(DetailDto detailDto) {
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(detailDto.getId());
        if (merchantStore == null) {
            return ApiRes.failResponse("门店不存在");
        }
        Integer isPreferential;
        // 状态取反
        if (IsPreferentialEnum.YES.getIsPreferential().equals(merchantStore.getIsPreferential())) {
            isPreferential = IsPreferentialEnum.NO.getIsPreferential();
        } else {
            isPreferential = IsPreferentialEnum.YES.getIsPreferential();
        }
        MerchantStore updateMerchantStore = new MerchantStore();
        updateMerchantStore.setId(merchantStore.getId());
        updateMerchantStore.setIsPreferential(isPreferential);
        if (merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes updateContractStatus(UpdateContractStatusDTO updateContractStatusDTO) {
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(updateContractStatusDTO.getId());
        if (merchantStore == null) {
            return ApiRes.failResponse("门店不存在");
        }
        MerchantStore updateMerchantStore = new MerchantStore();
        updateMerchantStore.setId(Integer.parseInt(updateContractStatusDTO.getId()));
        updateMerchantStore.setContractStatus(Integer.parseInt(updateContractStatusDTO.getContractStatus()));
        if (merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<OneLevelStatusVO> contractStatusSelectBox() {
        List<OneLevelStatusVO> contractStatusVOList = dictMapper.getContractStatus();
        if (contractStatusVOList.size() == 0) {
            contractStatusVOList = new ArrayList<>();
        }
        return ApiRes.successResponseData(contractStatusVOList);
    }

    @Override
    public ApiRes updateMoney(UpdateMoneyDTO updateMoneyDTO) {
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(updateMoneyDTO.getId());
        if (merchantStore == null) {
            return ApiRes.failResponse("门店不存在");
        }
        MerchantStore updateMerchantStore = new MerchantStore();
        updateMerchantStore.setId(Integer.parseInt(updateMoneyDTO.getId()));
        if (StringUtils.equals("1", updateMoneyDTO.getType())) {
            updateMerchantStore.setFranchiseFee(new BigDecimal(updateMoneyDTO.getMoney()));
        } else if (StringUtils.equals("2", updateMoneyDTO.getType())) {
            updateMerchantStore.setManagementExpense(new BigDecimal(updateMoneyDTO.getMoney()));
        } else if (StringUtils.equals("3", updateMoneyDTO.getType())) {
            updateMerchantStore.setBondMoney(new BigDecimal(updateMoneyDTO.getMoney()));
        } else {
            return ApiRes.failResponse("修改类型不正确");
        }
        if (merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        return ApiRes.successResponse();
    }

    @Override
    public void exportStoreDetail(StroePageDto stroePageDto, HttpServletResponse response) {
        String condition = stroePageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            stroePageDto.setCondition(null);
        }
        String startexpireTime = stroePageDto.getStartExpireTime();
        if (StringUtils.isEmpty(startexpireTime)) {
            stroePageDto.setStartExpireTime(null);
            stroePageDto.setEndExpireTime(null);
        } else {
            stroePageDto.setStartExpireTime(stroePageDto.getStartExpireTime() + " 00:00:00");
            stroePageDto.setEndExpireTime(stroePageDto.getEndExpireTime() + " 23:59:59");
        }
        String stareSignTime = stroePageDto.getStartSignTime();
        if (StringUtils.isEmpty(stareSignTime)) {
            stroePageDto.setStartSignTime(null);
            stroePageDto.setEndSignTime(null);
        } else {
            stroePageDto.setStartSignTime(stroePageDto.getStartSignTime() + " 00:00:00");
            stroePageDto.setEndSignTime(stroePageDto.getEndSignTime() + " 23:59:59");
        }
        stroePageDto.setMerchantId(StringUtils.isBlank(stroePageDto.getMerchantId()) ? null : stroePageDto.getMerchantId());
        stroePageDto.setAreaId(StringUtils.isBlank(stroePageDto.getAreaId()) ? null : stroePageDto.getAreaId());
        stroePageDto.setMonth(StringUtils.isBlank(stroePageDto.getMonth()) ? null : stroePageDto.getMonth());
        stroePageDto.setStatus(StringUtils.isBlank(stroePageDto.getStatus()) ? null : stroePageDto.getStatus());
        stroePageDto.setDelayedOpen(StringUtils.isBlank(stroePageDto.getDelayedOpen()) ? null : stroePageDto.getDelayedOpen());
        stroePageDto.setRegion(StringUtils.isBlank(stroePageDto.getRegion()) ? null : stroePageDto.getRegion());
        stroePageDto.setProvince(StringUtils.isBlank(stroePageDto.getProvince()) ? null : stroePageDto.getProvince());
        stroePageDto.setCity(StringUtils.isBlank(stroePageDto.getCity()) ? null : stroePageDto.getCity());
        stroePageDto.setArea(StringUtils.isBlank(stroePageDto.getArea()) ? null : stroePageDto.getArea());
        stroePageDto.setStartServiceExpireTime(StringUtils.isBlank(stroePageDto.getStartServiceExpireTime()) ? null : stroePageDto.getStartServiceExpireTime());
        stroePageDto.setEndServiceExpireTime(StringUtils.isBlank(stroePageDto.getEndServiceExpireTime()) ? null : stroePageDto.getEndServiceExpireTime());
        stroePageDto.setIsApply(StringUtils.isBlank(stroePageDto.getIsApply()) ? null : stroePageDto.getIsApply());
        List<ExportMerchantStoreDetailVO> exportMerchantStoreDetailVOS = merchantStoreMapper.exportStoreDetail(stroePageDto);
        List<MappingAreaDTO> mappingAreaDTOS = merchantStoreMapper.listMappingArea();
        Map<String, MappingAreaDTO> areaMap = Maps.newHashMap();
        mappingAreaDTOS.forEach(mappingAreaDTO -> {
            areaMap.put(mappingAreaDTO.getProvince() + mappingAreaDTO.getCity() + mappingAreaDTO.getArea(), mappingAreaDTO);
        });
        exportMerchantStoreDetailVOS.forEach(
                merchantStoreDetailVo -> {
                    merchantStoreDetailVo.setStatus(StatusTwoEnum.getStatusDesc(Integer.parseInt(merchantStoreDetailVo.getStatus())));
                    merchantStoreDetailVo.setContractStatus(ContractStatusEnum.getStatusDesc(Integer.parseInt(merchantStoreDetailVo.getContractStatus())));
                    merchantStoreDetailVo.setStatusTwo(NewStatusTwoEnum.getStatusDesc(Integer.parseInt(merchantStoreDetailVo.getStatusTwo())));
                    if ("1".equals(merchantStoreDetailVo.getType())) {
                        merchantStoreDetailVo.setType("街边店");
                    } else if ("2".equals(merchantStoreDetailVo.getType())) {
                        merchantStoreDetailVo.setType("商场店");
                    } else if ("3".equals(merchantStoreDetailVo.getType())) {
                        merchantStoreDetailVo.setType("档口店");
                    }
                    if ("1".equals(merchantStoreDetailVo.getIsPreferential())) {
                        merchantStoreDetailVo.setIsPreferential("是");
                    } else {
                        merchantStoreDetailVo.setIsPreferential("否");
                    }
                    if (Integer.parseInt(merchantStoreDetailVo.getOpenNum()) > 0) {
                        merchantStoreDetailVo.setIsOpen("是");
                    } else {
                        merchantStoreDetailVo.setIsOpen("否");
                    }
                    merchantStoreDetailVo.setShowDeliveryAddress(StringUtils.equals("1", merchantStoreDetailVo.getShowDeliveryAddress()) ? "是" : "否");
                    if (areaMap.keySet().contains(merchantStoreDetailVo.getProvince() + merchantStoreDetailVo.getCity() + merchantStoreDetailVo.getArea())) {
                        merchantStoreDetailVo.setRegion(areaMap.get(merchantStoreDetailVo.getProvince() + merchantStoreDetailVo.getCity() + merchantStoreDetailVo.getArea()).getRegion());
                        merchantStoreDetailVo.setLevel(areaMap.get(merchantStoreDetailVo.getProvince() + merchantStoreDetailVo.getCity() + merchantStoreDetailVo.getArea()).getLevel());
                    }
                    merchantStoreDetailVo.setIsApply(StringUtils.equals("1", merchantStoreDetailVo.getIsApply()) ? "开" : "关");
                }
        );
//        ExcelUtils.writeExcel(response, exportMerchantStoreDetailVOS, ExportMerchantStoreDetailVO.class, "门店详情.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("门店详情", response), ExportMerchantStoreDetailVO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(exportMerchantStoreDetailVOS);
    }

    @Override
    public ApiRes<OneLevelStatusVO> storeTypeSelectBox() {
        List<OneLevelStatusVO> contractStatusVOList = dictMapper.getStoreType();
        if (contractStatusVOList.size() == 0) {
            contractStatusVOList = new ArrayList<>();
        }
        return ApiRes.successResponseData(contractStatusVOList);
    }

    @Override
    public ApiRes updateExtraField(UpdateExtraFieldDTO updateExtraFieldDTO) {
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(updateExtraFieldDTO.getStoreId());
        if (merchantStore == null) {
            return ApiRes.failResponse("门店不存在");
        }
        if (StringUtils.isBlank(updateExtraFieldDTO.getValue())) {
            if (merchantStoreMapper.updateField(Integer.parseInt(updateExtraFieldDTO.getType()), merchantStore.getId()) == 0) {
                return ApiRes.failResponse("修改失败");
            }
        } else {
            MerchantStore updateMerchantStore = new MerchantStore();
            updateMerchantStore.setId(merchantStore.getId());
            if (StringUtils.equals("1", updateExtraFieldDTO.getType())) {
                updateMerchantStore.setSeatCount(Integer.parseInt(updateExtraFieldDTO.getValue()));
            } else if (StringUtils.equals("2", updateExtraFieldDTO.getType())) {
                updateMerchantStore.setHllCode(updateExtraFieldDTO.getValue());
            } else if (StringUtils.equals("3", updateExtraFieldDTO.getType())) {
                updateMerchantStore.setMtId(updateExtraFieldDTO.getValue());
            } else if (StringUtils.equals("4", updateExtraFieldDTO.getType())) {
                updateMerchantStore.setElmId(updateExtraFieldDTO.getValue());
            } else if (StringUtils.equals("5", updateExtraFieldDTO.getType())) {
                updateMerchantStore.setDzdpId(updateExtraFieldDTO.getValue());
            } else if (StringUtils.equals("6", updateExtraFieldDTO.getType())) {
                updateMerchantStore.setRemark(updateExtraFieldDTO.getValue());
            } else {
                return ApiRes.failResponse("修改类型错误");
            }
            if (merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore) == 0) {
                return ApiRes.failResponse("修改失败");
            }
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes inputExcelUpdateExtraField(MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
        List<InputExcelUpdateExtraFieldDTO> list = ExcelUtils.readExcel("", InputExcelUpdateExtraFieldDTO.class, multipartFile);
//        List<MerchantStore> merchantStoreList = merchantStoreMapper.listMerchantStore();
//        Map<String, Integer> storeMap = Maps.newHashMap();
//        merchantStoreList.forEach(merchantStore -> {
//            storeMap.put(merchantStore.getUid(), merchantStore.getId());
//        });
        List<MerchantStore> updateList = Lists.newArrayList();
        list.forEach(inputExcelUpdateExtraFieldDTO -> {
            MerchantStore merchantStore = new MerchantStore();
//            merchantStore.setUid(inputExcelUpdateExtraFieldDTO.getUid());
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getIdNumber())) {
                merchantStore.setIdNumber(inputExcelUpdateExtraFieldDTO.getIdNumber());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getSeatCount())) {
                if (inputExcelUpdateExtraFieldDTO.getSeatCount().contains(".")) {
                    int index = inputExcelUpdateExtraFieldDTO.getSeatCount().indexOf(".");
                    inputExcelUpdateExtraFieldDTO.setSeatCount(StringUtils.substring(inputExcelUpdateExtraFieldDTO.getSeatCount(), 0, index));
                }
                merchantStore.setSeatCount(Integer.parseInt(inputExcelUpdateExtraFieldDTO.getSeatCount()));
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getHllCode())) {
                if (inputExcelUpdateExtraFieldDTO.getHllCode().contains(".")) {
                    int index = inputExcelUpdateExtraFieldDTO.getHllCode().indexOf(".");
                    inputExcelUpdateExtraFieldDTO.setHllCode(StringUtils.substring(inputExcelUpdateExtraFieldDTO.getHllCode(), 0, index));
                }
                merchantStore.setHllCode(inputExcelUpdateExtraFieldDTO.getHllCode());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getMtId())) {
                if (inputExcelUpdateExtraFieldDTO.getMtId().contains(".")) {
                    int index = inputExcelUpdateExtraFieldDTO.getMtId().indexOf(".");
                    inputExcelUpdateExtraFieldDTO.setMtId(StringUtils.substring(inputExcelUpdateExtraFieldDTO.getMtId(), 0, index));
                }
                merchantStore.setMtId(inputExcelUpdateExtraFieldDTO.getMtId());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getElmId())) {
                if (inputExcelUpdateExtraFieldDTO.getElmId().contains(".")) {
                    int index = inputExcelUpdateExtraFieldDTO.getElmId().indexOf(".");
                    inputExcelUpdateExtraFieldDTO.setElmId(StringUtils.substring(inputExcelUpdateExtraFieldDTO.getElmId(), 0, index));
                }
                merchantStore.setElmId(inputExcelUpdateExtraFieldDTO.getElmId());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getDzdpId())) {
                if (inputExcelUpdateExtraFieldDTO.getDzdpId().contains(".")) {
                    int index = inputExcelUpdateExtraFieldDTO.getDzdpId().indexOf(".");
                    inputExcelUpdateExtraFieldDTO.setDzdpId(StringUtils.substring(inputExcelUpdateExtraFieldDTO.getDzdpId(), 0, index));
                }
                merchantStore.setDzdpId(inputExcelUpdateExtraFieldDTO.getDzdpId());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getReceiver())) {
                merchantStore.setReceiver(inputExcelUpdateExtraFieldDTO.getReceiver());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getDeliveryAddress())) {
                merchantStore.setDeliveryAddress(inputExcelUpdateExtraFieldDTO.getDeliveryAddress());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getReceiverMobile())) {
                if (inputExcelUpdateExtraFieldDTO.getReceiverMobile().contains(".")) {
                    int index = inputExcelUpdateExtraFieldDTO.getReceiverMobile().indexOf(".");
                    inputExcelUpdateExtraFieldDTO.setReceiverMobile(StringUtils.substring(inputExcelUpdateExtraFieldDTO.getReceiverMobile(), 0, index));
                }
                merchantStore.setReceiverMobile(inputExcelUpdateExtraFieldDTO.getReceiverMobile());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getDeliveryProvince())) {
                merchantStore.setDeliveryProvince(inputExcelUpdateExtraFieldDTO.getDeliveryProvince());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getDeliveryCity())) {
                merchantStore.setDeliveryCity(inputExcelUpdateExtraFieldDTO.getDeliveryCity());
            }
            if (StringUtils.isNotBlank(inputExcelUpdateExtraFieldDTO.getDeliveryArea())) {
                merchantStore.setDeliveryArea(inputExcelUpdateExtraFieldDTO.getDeliveryArea());
            }
            Example example = new Example(MerchantStore.class);
            example.createCriteria().andEqualTo("uid", inputExcelUpdateExtraFieldDTO.getUid());
            merchantStoreMapper.updateByExampleSelective(merchantStore, example);
//            updateList.add(merchantStore);
        });
//        if (updateList.size() > 0) {
//            merchantStoreMapper.updateExtraField(updateList);
//        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes updateDeliveryAddress(UpdateDeliveryAddressDTO updateDeliveryAddressDTO) {
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(updateDeliveryAddressDTO.getStoreId());
        if (merchantStore == null) {
            return ApiRes.failResponse("门店不存在");
        }
        MerchantStore updateMerchantStore = new MerchantStore();
        updateMerchantStore.setId(merchantStore.getId());
        updateMerchantStore.setShowDeliveryAddress(Integer.parseInt(updateDeliveryAddressDTO.getShowDeliveryAddress()));
        updateMerchantStore.setDeliveryAddress(updateDeliveryAddressDTO.getDeliveryAddress());
        updateMerchantStore.setReceiver(updateDeliveryAddressDTO.getReceiver());
        updateMerchantStore.setReceiverMobile(updateDeliveryAddressDTO.getReceiverMobile());
        updateMerchantStore.setDeliveryProvince(updateDeliveryAddressDTO.getDeliveryProvince());
        updateMerchantStore.setDeliveryCity(updateDeliveryAddressDTO.getDeliveryCity());
        updateMerchantStore.setDeliveryArea(updateDeliveryAddressDTO.getDeliveryArea());
        merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore);
        return ApiRes.successResponse();
    }

    /**
     * 根据传入的字符串得到相应的日期
     * taskTime:1天后 1月后 1年后
     *
     * @param taskTime
     * @return
     */
    public static Date endTime(String taskTime, Date paramTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(paramTime);
        if (taskTime.contains("天后")) {
            String[] str = taskTime.split("天");
            Integer index = Integer.parseInt(str[0]);
            calendar.add(Calendar.DATE, index);
        }
        if (taskTime.contains("月后")) {
            String[] str = taskTime.split("月");
            Integer index = Integer.parseInt(str[0]);
            calendar.add(Calendar.MONTH, index);
        }
        if (taskTime.contains("年后")) {
            String[] str = taskTime.split("年");
            Integer index = Integer.parseInt(str[0]);
            calendar.add(Calendar.YEAR, index);
        }
        Date resultDate = calendar.getTime();
        return resultDate;
    }

    /**
     * 所属代理权
     *
     * @param belongAgencyDTO
     * @return
     */
    @Override
    public ApiRes<BelongAgencyVO> belongAgencyService(BelongAgencyDTO belongAgencyDTO) {
        List<BelongAgencyVO> agencyDTOList = merchantStoreMapper.belongAgencyMapper(belongAgencyDTO);
        agencyDTOList.forEach(belongAgencyVO -> {
            belongAgencyVO.setMerchantName(belongAgencyVO.getUid() + "~" + belongAgencyVO.getMerchantName());
        });
        return ApiRes.successResponseData(agencyDTOList);
    }

    /**
     * 所属代理权修改
     */
    @Override
    public ApiRes updateBelongAgencyService(UpdateBelongAgencyDTO updateBelongAgencyDTO) {
        if (updateBelongAgencyDTO == null) {
            ApiRes.failResponse("请输入数据");
        }
        if (!merchantStoreMapper.updateBelongAgencyMapper(updateBelongAgencyDTO)) {
            return ApiRes.failResponse("所属代理权修改失败");
        }
        return ApiRes.successResponseData("");
    }

    /**
     * 返回配送到代理的数据
     */
    @Override
    public ApiRes<ReturnDataVO> returnData(ReturnDataDTO returnDataDTO) {
        if (returnDataDTO.getId() == 0 || returnDataDTO.getId() == null) {
            return ApiRes.failResponse("门店id不存在");
        }
        ReturnDataVO returnDataVO2 = merchantStoreMapper.selectActing(returnDataDTO);
        if (returnDataVO2 == null) {
            returnDataVO2 = new ReturnDataVO();
            returnDataVO2.setPhone("");
            returnDataVO2.setReceiver("");
            returnDataVO2.setReceiptProvince("");
            returnDataVO2.setReceiptCity("");
            returnDataVO2.setReceiptArea("");
            returnDataVO2.setAddress("");
            return ApiRes.successResponseData(returnDataVO2);
        }
        return ApiRes.successResponseData(returnDataVO2);
    }

    /**
     * 返回配送到门店的数据
     *
     * @param returnDataDTO
     * @return
     */
    @Override
    public ApiRes<DeliveryVO> deliveryToStoreService(ReturnDataDTO returnDataDTO) {
        DeliveryVO deliveryVO = merchantStoreMapper.deliveryToStoreServiceMapper(returnDataDTO);
        if (deliveryVO != null) {
            deliveryVO.setProvinceCityDistrict(deliveryVO.getProvince() + deliveryVO.getCity() + deliveryVO.getArea());
        } else {
            deliveryVO = new DeliveryVO();
            deliveryVO.setReceiver("");
            deliveryVO.setReceiverMobile("");
            deliveryVO.setProvince("");
            deliveryVO.setCity("");
            deliveryVO.setArea("");
            deliveryVO.setProvinceCityDistrict("");
            deliveryVO.setDeliveryAddress("");
        }
        return ApiRes.successResponseData(deliveryVO);
    }

    @Override
    public ApiRes updateApplyAdjust(UpdateApplyAdjustDTO updateApplyAdjustDTO) {
        if (merchantStoreMapper.updateApplyAdjust(updateApplyAdjustDTO) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        return ApiRes.successResponse();
    }

}
