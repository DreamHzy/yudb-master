package com.ynzyq.yudbadmin.third.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ynzyq.yudbadmin.api.excel.enums.NewStatusTwoEnum;
import com.ynzyq.yudbadmin.api.excel.enums.StatusTwoEnum;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreMapper;
import com.ynzyq.yudbadmin.dao.business.dao.WalletOrderMapper;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStore;
import com.ynzyq.yudbadmin.dao.business.model.WalletOrder;
import com.ynzyq.yudbadmin.third.dto.*;
import com.ynzyq.yudbadmin.third.service.IForeignService;
import com.ynzyq.yudbadmin.third.vo.QuerySettleStatusVO;
import com.ynzyq.yudbadmin.util.Encrypt;
import com.ynzyq.yudbadmin.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author xinchen
 * @date 2021/12/2 17:40
 * @description:
 */
@Slf4j
@Service
public class ForeignServiceImpl implements IForeignService {

    @Value("${encryptKey}")
    private String encryptKey;

    @Resource
    MerchantStoreMapper merchantStoreMapper;

    @Value("${jinDie.userInfo}")
    private String userInfoUrl;

    @Resource
    WalletOrderMapper walletOrderMapper;

    @Override
    public ApiRes modifyStoreStatus(ModifyStoreStatusDTO modifyStoreStatusDTO) {
        // 校验参数
        if (StringUtils.isBlank(modifyStoreStatusDTO.getUid())) {
            return ApiRes.failResponse("授权号不能为空");
        } else if (StringUtils.isBlank(modifyStoreStatusDTO.getTimeStamp())) {
            return ApiRes.failResponse("时间戳毫秒数不能为空");
        } else if (StringUtils.isBlank(modifyStoreStatusDTO.getSign())) {
            return ApiRes.failResponse("签名不能为空");
        }
        // 校验签名
        String SignStr = modifyStoreStatusDTO.getUid() + "&" + modifyStoreStatusDTO.getTimeStamp();
        String sign = null;
        try {
            sign = Encrypt.AESEncryptByKey(SignStr, encryptKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.equals(sign, modifyStoreStatusDTO.getSign())) {
            return ApiRes.failResponse("签名不正确");
        }
        // 获取门店信息
        Example example = new Example(MerchantStore.class);
        example.createCriteria().andEqualTo("uid", modifyStoreStatusDTO.getUid());
        MerchantStore merchantStore = merchantStoreMapper.selectOneByExample(example);
        if (merchantStore == null) {
            return ApiRes.failResponse("门店不存在");
        }
        MerchantStore updateMerchantStore = new MerchantStore();
        updateMerchantStore.setId(merchantStore.getId());
        updateMerchantStore.setStatus(StatusTwoEnum.TERMINATE_THE_CONTRACT.getStatus());
        updateMerchantStore.setStatusTwo(NewStatusTwoEnum.CLOSED_SHOP.getStatus());
        updateMerchantStore.setUpdateTime(new Date());
        if (merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore) == 0) {
            return ApiRes.failResponse("更新失败");
        }
        return ApiRes.successResponse();
    }

    @Async
    @Override
    public String userInfo(UserInfoDTO userInfoDTO) {
        String jsonString = JSON.toJSONString(userInfoDTO);
        log.info("用户信息同步请求参数：{}", jsonString);
        String ret = HttpUtil.sendByPostJson(userInfoUrl, jsonString);
        log.info("用户信息同步响应参数：{}", ret);
        if (!StringUtils.isBlank(ret)) {
            JSONObject jsonObject = JSONObject.parseObject(ret);
            String code = jsonObject.getString("code");
            if (!StringUtils.equals("200", code)) {
                String msg = jsonObject.getString("data");
                return msg;
            }
        }
        return "success";
    }

    @Override
    public ApiRes modifyStoreStatusV2(ModifyStoreStatusTwoDTO modifyStoreStatusTwoDTO) {
        // 校验参数
        if (StringUtils.isBlank(modifyStoreStatusTwoDTO.getUid())) {
            return ApiRes.failResponse("授权号不能为空");
        } else if (StringUtils.isBlank(modifyStoreStatusTwoDTO.getType())) {
            return ApiRes.failResponse("操作类型不能为空");
        } else if (StringUtils.isBlank(modifyStoreStatusTwoDTO.getTimeStamp())) {
            return ApiRes.failResponse("时间戳毫秒数不能为空");
        } else if (StringUtils.isBlank(modifyStoreStatusTwoDTO.getSign())) {
            return ApiRes.failResponse("签名不能为空");
        }
        // 校验签名
        String SignStr = modifyStoreStatusTwoDTO.getUid() + "&" + modifyStoreStatusTwoDTO.getType() + "&" + modifyStoreStatusTwoDTO.getTimeStamp();
        String sign = null;
        try {
            sign = Encrypt.AESEncryptByKey(SignStr, encryptKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.equals(sign, modifyStoreStatusTwoDTO.getSign())) {
            return ApiRes.failResponse("签名不正确");
        }
        // 获取门店信息
        Example example = new Example(MerchantStore.class);
        example.createCriteria().andEqualTo("uid", modifyStoreStatusTwoDTO.getUid());
        MerchantStore merchantStore = merchantStoreMapper.selectOneByExample(example);
        if (merchantStore == null) {
            return ApiRes.failResponse("门店不存在");
        }
        MerchantStore updateMerchantStore = new MerchantStore();
        updateMerchantStore.setId(merchantStore.getId());
        if (StringUtils.equals("1", modifyStoreStatusTwoDTO.getType())) {
            updateMerchantStore.setStatus(StatusTwoEnum.TERMINATE_THE_CONTRACT.getStatus());
            updateMerchantStore.setStatusTwo(NewStatusTwoEnum.CLOSED_SHOP.getStatus());
            updateMerchantStore.setCloseTime(new Date(Long.parseLong(modifyStoreStatusTwoDTO.getTimeStamp())));
        } else if (StringUtils.equals("2", modifyStoreStatusTwoDTO.getType())) {
            updateMerchantStore.setStatus(StatusTwoEnum.RELOCATION.getStatus());
            updateMerchantStore.setStatusTwo(NewStatusTwoEnum.RELOCATION.getStatus());
            updateMerchantStore.setRelocationTime(new Date(Long.parseLong(modifyStoreStatusTwoDTO.getTimeStamp())));
        } else if (StringUtils.equals("3", modifyStoreStatusTwoDTO.getType())) {
            updateMerchantStore.setStatus(StatusTwoEnum.SUSPEND_BUSINESS.getStatus());
            updateMerchantStore.setStatusTwo(NewStatusTwoEnum.SUSPEND_BUSINESS.getStatus());
            updateMerchantStore.setPauseTime(new Date(Long.parseLong(modifyStoreStatusTwoDTO.getTimeStamp())));
        } else {
            return ApiRes.failResponse("操作类型错误");
        }
        updateMerchantStore.setUpdateTime(new Date());
        if (merchantStoreMapper.updateByPrimaryKeySelective(updateMerchantStore) == 0) {
            return ApiRes.failResponse("更新失败");
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes transferOrder(TransferOrderDTO transferOrderDTO) {
        WalletOrder query = new WalletOrder();
        query.setOrderNo(transferOrderDTO.getOrderNo());
        WalletOrder walletOrder = walletOrderMapper.selectOne(query);
        if (walletOrder != null) {
            log.info("订单【{}】已存在，无需重复添加", transferOrderDTO.getOrderNo());
            return ApiRes.failResponse("订单【" + transferOrderDTO.getOrderNo() + "】已存在，无需重复推送");
        }
        String signStr = getSignStr(transferOrderDTO);
        log.info("待验签字符串：{}", signStr);
        String sign = null;
        try {
            sign = Encrypt.AESEncryptByKey(signStr, encryptKey);
            log.info("对方签名【{}】，我方签名【{}】", transferOrderDTO.getSign(), sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.equals(sign, transferOrderDTO.getSign())) {
            return ApiRes.failResponse("签名不正确");
        }
        WalletOrder addWalletOrder = new WalletOrder();
        addWalletOrder.setOrderNo(transferOrderDTO.getOrderNo());
        addWalletOrder.setStoreUid(transferOrderDTO.getStoreUid());
        addWalletOrder.setOrderFee(new BigDecimal(transferOrderDTO.getOrderFee()));
        addWalletOrder.setOrderMoney(new BigDecimal(transferOrderDTO.getOrderMoney()));
        addWalletOrder.setActualMoney(new BigDecimal(transferOrderDTO.getOrderMoney()).subtract(new BigDecimal(transferOrderDTO.getOrderFee())));
        addWalletOrder.setSettleStatus(2);
        addWalletOrder.setOrderDate(DateUtil.parse(transferOrderDTO.getOrderTime(), "yyyy-MM-dd"));
        addWalletOrder.setOrderTime(DateUtil.parse(transferOrderDTO.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
        addWalletOrder.setCreateTime(new Date());
        if (walletOrderMapper.insert(addWalletOrder) == 0) {
            return ApiRes.failResponse("同步失败");
        }
        return ApiRes.successResponse();
    }

    private String getSignStr(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        SortedMap<String, String> sortedMap = new TreeMap<>();
        try {
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String name = field.getName();
                if (StringUtils.equals("sign", name)) {
                    continue;
                }
                sortedMap.put(field.getName(), String.valueOf(field.get(obj)));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            sb.append(key).append("=").append(sortedMap.get(key)).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    @Override
    public ApiRes<QuerySettleStatusVO> querySettleStatus(QuerySettleStatusDTO querySettleStatusDTO) {
        String signStr = getSignStr(querySettleStatusDTO);
        log.info("待验签字符串：{}", signStr);
        String sign = null;
        try {
            sign = Encrypt.AESEncryptByKey(signStr, encryptKey);
            log.info("对方签名【{}】，我方签名【{}】", querySettleStatusDTO.getSign(), sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.equals(sign, querySettleStatusDTO.getSign())) {
            return ApiRes.failResponse("签名不正确");
        }
        WalletOrder query = new WalletOrder();
        query.setOrderNo(querySettleStatusDTO.getOrderNo());
        WalletOrder walletOrder = walletOrderMapper.selectOne(query);
        if (walletOrder == null) {
            return ApiRes.failResponse("该订单不存在，请确认是否已同步");
        }
        QuerySettleStatusVO querySettleStatusVO = new QuerySettleStatusVO();
        querySettleStatusVO.setOrderNo(querySettleStatusDTO.getOrderNo());
        querySettleStatusVO.setSettleStatus(walletOrder.getSettleStatus().toString());
        return ApiRes.successResponseData(querySettleStatusVO);
    }

    public static void main(String[] args) throws Exception {
        String SignStr = "2&1&1234567889";
        System.out.println(Encrypt.AESEncryptByKey(SignStr, "uh8yLn1KIq59FvA3"));
    }
}
