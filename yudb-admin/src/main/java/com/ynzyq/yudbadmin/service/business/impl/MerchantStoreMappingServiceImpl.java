package com.ynzyq.yudbadmin.service.business.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.*;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.po.MerchantStoreChangeRecordPO;
import com.ynzyq.yudbadmin.po.MerchantStoreRegionalManagerPO;
import com.ynzyq.yudbadmin.po.PaymentOrderChangeRecordPO;
import com.ynzyq.yudbadmin.service.business.IMerchantStoreMappingService;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author xinchen
 * @date 2021/11/18 11:12
 * @description:
 */
@Slf4j
@Service
public class MerchantStoreMappingServiceImpl implements IMerchantStoreMappingService {

    @Resource
    MerchantStoreMappingMapper merchantStoreMappingMapper;

    @Resource
    MerchantMapper merchantMapper;

    @Resource
    PaymentOrderChangeRecordMapper paymentOrderChangeRecordMapper;

    @Resource
    MerchantStoreChangeRecordMapper merchantStoreChangeRecordMapper;

    @Resource
    RegionalManagerMapper regionalManagerMapper;

    @Resource
    MerchantStoreRegionalManagerMapper merchantStoreRegionalManagerMapper;

    @Resource
    MerchantStoreMappingAreaMapper merchantStoreMappingAreaMapper;

    @Override
    public ApiRes<StoreMappingVO> listStoreMapping(PageWrap<StoreMappingDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<StoreMappingVO> storeMappingVOS = merchantStoreMappingMapper.listStoreMapping(pageWrap.getModel());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(storeMappingVOS)));
    }

    @Override
    public ApiRes agentSelectBox() {
        List<MerchantSelectBoxVO> agentSelectBox = merchantStoreMappingMapper.agentSelectBox();
        if (agentSelectBox.size() == 0) {
            agentSelectBox = new ArrayList<>();
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", agentSelectBox);
    }

    @Override
    public ApiRes managerSelectBox() {
        List<MerchantSelectBoxVO> managerSelectBox = merchantStoreMappingMapper.managerSelectBox();
        if (managerSelectBox.size() == 0) {
            managerSelectBox = new ArrayList<>();
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", managerSelectBox);
    }

    @Override
    public ApiRes agentTransferArea(AgentTransferAreaDTO agentTransferAreaDTO) {
        String agentId = agentTransferAreaDTO.getAgentId();
        Merchant merchant = merchantMapper.selectByPrimaryKey(agentId);
        if (merchant == null) {
            return ApiRes.failResponse("代理商不存在");
        }
        String merchantUid;
        try {
            merchantUid = merchantStoreMappingAreaMapper.getMerchantUid(merchant.getId());
            if (StringUtils.isBlank(merchantUid)) {
                return ApiRes.failResponse("无法获取到代理权代码，请先在代理权映射补全数据");
            }
        } catch (Exception e) {
            log.error("该异常是有多条数据导致，此处只做异常捕获，获取方式将在后面的循环中获取", e);
            merchantUid = null;
        }
        // 查询所有代理商并放在map里面
        List<MerchantSelectBoxVO> agentSelectBox = merchantStoreMappingMapper.agentSelectBox();
        Map<String, String> map = new HashMap<>(agentSelectBox.size());
        agentSelectBox.forEach(merchantSelectBoxVO -> {
            map.put(merchantSelectBoxVO.getName(), merchantSelectBoxVO.getId());
        });
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        List<MerchantStoreChangeRecord> merchantStoreChangeRecordList = new ArrayList<>(agentTransferAreaDTO.getId().size());
        for (String id : agentTransferAreaDTO.getId()) {
            MerchantStoreMappingArea merchantStoreMappingArea = merchantStoreMappingAreaMapper.selectByPrimaryKey(id);
            if (merchantStoreMappingArea == null) {
                return ApiRes.failResponse("编号为【" + id + "】的区域映射不存在");
            }
            if (StringUtils.isBlank(merchantUid)) {
                merchantUid = merchantStoreMappingAreaMapper.getMerchantUid2(merchant.getId(), merchantStoreMappingArea.getProvince(), merchantStoreMappingArea.getCity(), merchantStoreMappingArea.getArea());
                if (StringUtils.isBlank(merchantUid)) {
                    return ApiRes.failResponse("无法获取到编号为【" + id + "】的代理权代码，请核对代理权代码的省市区地址和门店映射表中的省市区地址是否一致");
                }
            }
            // 判断代理名称是否在map里面，是则直接取id
            String oldAgentName = merchantStoreMappingArea.getAgentName();
            Integer oldAgentId = null;
            if (map.keySet().contains(oldAgentName)) {
                oldAgentId = Integer.parseInt(map.get(oldAgentName));
            }

            // 更新映射表数据
            MerchantStoreMappingArea updateMerchantStoreMappingArea = new MerchantStoreMappingArea();
            updateMerchantStoreMappingArea.setId(Integer.parseInt(id));
            updateMerchantStoreMappingArea.setAgentUid(merchantUid);
            updateMerchantStoreMappingArea.setAgentId(merchant.getId());
            updateMerchantStoreMappingArea.setAgentName(merchant.getName());
            merchantStoreMappingAreaMapper.updateByPrimaryKeySelective(updateMerchantStoreMappingArea);

            // 查询所有
            List<Integer> storeIdList = merchantStoreMappingMapper.getStoreId(merchantStoreMappingArea.getProvince(), merchantStoreMappingArea.getCity(), merchantStoreMappingArea.getArea());
            for (Integer storeId : storeIdList) {
                // 修改门店所属代理
                merchantStoreMappingMapper.updateStoreAgent(merchant.getId(), merchant.getName(), storeId);

                // 添加修改记录
                MerchantStoreChangeRecordPO merchantStoreChangeRecordPO = new MerchantStoreChangeRecordPO(id, storeId, oldAgentId, oldAgentName, merchant, 1, loginUserInfo);
                merchantStoreChangeRecordList.add(merchantStoreChangeRecordPO);
            }
        }
        // 批量添加更新记录
        if (merchantStoreChangeRecordList.size() > 0) {
            merchantStoreChangeRecordMapper.insertList(merchantStoreChangeRecordList);
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes managerTransferArea(ManagerTransferAreaDTO managerTransferAreaDTO) {
        String managerId = managerTransferAreaDTO.getManagerId();
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(managerId);
        if (regionalManager == null) {
            return ApiRes.failResponse("区域经理不存在");
        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        List<MerchantStoreChangeRecord> merchantStoreChangeRecordList = new ArrayList<>(managerTransferAreaDTO.getId().size());
        List<MerchantStoreRegionalManager> merchantStoreRegionalManagerList = new ArrayList<>();
        List<PaymentOrderChangeRecord> paymentOrderChangeRecordList = new ArrayList<>();
        for (String id : managerTransferAreaDTO.getId()) {
            MerchantStoreMappingArea merchantStoreMappingArea = merchantStoreMappingAreaMapper.selectByPrimaryKey(id);
            if (merchantStoreMappingArea == null) {
                return ApiRes.failResponse("编号为【" + id + "】的区域映射不存在");
            }
            // 更新映射表数据
            MerchantStoreMappingArea updateMerchantStoreMappingArea = new MerchantStoreMappingArea();
            updateMerchantStoreMappingArea.setId(Integer.parseInt(id));
            updateMerchantStoreMappingArea.setManagerId(regionalManager.getId());
            updateMerchantStoreMappingArea.setManagerName(regionalManager.getName());
            merchantStoreMappingAreaMapper.updateByPrimaryKeySelective(updateMerchantStoreMappingArea);
            if (merchantStoreMappingArea.getIsMapping().equals(1)) {
                // 获取该区域下所有的加盟店
                List<MerchantStoreRegionalManagerDTO> merchantStoreRegionalManagerDTOList = merchantStoreMappingMapper.getManagerId(merchantStoreMappingArea.getProvince(), merchantStoreMappingArea.getCity(), merchantStoreMappingArea.getArea());
                Integer oldManagerId = null;
                String oldManagerName = null;
                // 判断店铺是否已关联区域经理
                if (merchantStoreRegionalManagerDTOList.size() > 0) {
                    for (MerchantStoreRegionalManagerDTO merchantStoreRegionalManagerDTO : merchantStoreRegionalManagerDTOList) {
                        // 将原来的关联更新成不可用
                        merchantStoreMappingMapper.updateStoreManager(merchantStoreRegionalManagerDTO.getStoreId());
                        // 获取原来的区域经理数据
                        if (merchantStoreRegionalManagerDTO.getManagerId() != null) {
                            oldManagerId = merchantStoreRegionalManagerDTO.getManagerId();
                            oldManagerName = merchantStoreRegionalManagerDTO.getName();
                        }
                        // 添加新的关联数据
                        MerchantStoreRegionalManagerPO merchantStoreRegionalManagerPO = new MerchantStoreRegionalManagerPO(managerId, merchantStoreRegionalManagerDTO.getStoreId(), loginUserInfo.getId());
                        merchantStoreRegionalManagerList.add(merchantStoreRegionalManagerPO);

                        // 添加修改记录
                        MerchantStoreChangeRecordPO merchantStoreChangeRecordPO = new MerchantStoreChangeRecordPO(id, merchantStoreRegionalManagerDTO.getStoreId(), oldManagerId, oldManagerName, regionalManager, 2, loginUserInfo);
                        merchantStoreChangeRecordList.add(merchantStoreChangeRecordPO);

                        // 修改门店订单区域经理数据
                        List<PaymentOrderDTO> orderDTOList = merchantStoreMappingMapper.listStoreOrder(merchantStoreRegionalManagerDTO.getStoreId());
                        if (orderDTOList.size() > 0) {
                            for (PaymentOrderDTO paymentOrderDTO : orderDTOList) {
                                PaymentOrderChangeRecordPO paymentOrderChangeRecordPO = new PaymentOrderChangeRecordPO(id, paymentOrderDTO, oldManagerId, oldManagerName, regionalManager, 1, loginUserInfo);
                                paymentOrderChangeRecordList.add(paymentOrderChangeRecordPO);
                                merchantStoreMappingMapper.updateOrderManager(regionalManager.getId(), regionalManager.getName(), regionalManager.getMobile(), paymentOrderDTO.getId());
                            }
                        }
                    }
                }
            }
        }
        // 批量添加关联数据
        if (merchantStoreRegionalManagerList.size() > 0) {
            merchantStoreRegionalManagerMapper.insertList(merchantStoreRegionalManagerList);
        }
        // 批量添加更新记录
        if (merchantStoreChangeRecordList.size() > 0) {
            merchantStoreChangeRecordMapper.insertList(merchantStoreChangeRecordList);
        }
        // 批量添加订单更新记录
        if (paymentOrderChangeRecordList.size() > 0) {
            paymentOrderChangeRecordMapper.insertList(paymentOrderChangeRecordList);
        }
        return ApiRes.successResponse();
    }

    @Override
    public void exportStoreMapping(StoreMappingDTO storeMappingDTO, HttpServletResponse response) {
        List<StoreMappingVO> storeMappingVOS = merchantStoreMappingMapper.listStoreMapping(storeMappingDTO);
//        ExcelUtils.writeExcel(response, storeMappingVOS, StoreMappingVO.class, "门店映射.xlsx");
        EasyExcel.write(ExcelUtil.getOutPutStream("门店映射", response), StoreMappingVO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(storeMappingVOS);
    }

    @Override
    public ApiRes updateLevel(LevelDTO levelDTO) {
        for (String id : levelDTO.getIds()) {
            MerchantStoreMappingArea merchantStoreMappingArea = merchantStoreMappingAreaMapper.selectByPrimaryKey(id);
            if (merchantStoreMappingArea == null) {
                return ApiRes.failResponse("区域映射不存在");
            }
            MerchantStoreMappingArea updateMerchantStoreMappingArea = new MerchantStoreMappingArea();
            updateMerchantStoreMappingArea.setId(merchantStoreMappingArea.getId());
            updateMerchantStoreMappingArea.setLevel(levelDTO.getLevel());
            merchantStoreMappingAreaMapper.updateByPrimaryKeySelective(updateMerchantStoreMappingArea);
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<LevelVO> levelSelectBox() {
        List<LevelVO> levelVOS = merchantStoreMappingAreaMapper.levelSelectBox();
        if (levelVOS.size() == 0) {
            levelVOS = Lists.newArrayList();
        }
        return ApiRes.successResponseData(levelVOS);
    }

    @Override
    public ApiRes updateIsMapping(UpdateIsMappingDTO updateIsMappingDTO) {
        for (String id : updateIsMappingDTO.getIds()) {
            MerchantStoreMappingArea merchantStoreMappingArea = merchantStoreMappingAreaMapper.selectByPrimaryKey(id);
            if (merchantStoreMappingArea == null) {
                return ApiRes.failResponse("区域映射不存在");
            }
            MerchantStoreMappingArea updateMerchantStoreMappingArea = new MerchantStoreMappingArea();
            updateMerchantStoreMappingArea.setId(merchantStoreMappingArea.getId());
            updateMerchantStoreMappingArea.setIsMapping(Integer.parseInt(updateIsMappingDTO.getIsMapping()));
            merchantStoreMappingAreaMapper.updateByPrimaryKeySelective(updateMerchantStoreMappingArea);
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes updateOrderGoods(UpdateWarehouseDTO updateWarehouseDTO) {
        MerchantStoreMappingArea updateMerchantStoreMappingArea = new MerchantStoreMappingArea();
        if (StringUtils.equals("1", updateWarehouseDTO.getType())) {
            updateMerchantStoreMappingArea.setWarehouseId(Integer.parseInt(updateWarehouseDTO.getValueId()));
        } else if (StringUtils.equals("2", updateWarehouseDTO.getType())) {
            updateMerchantStoreMappingArea.setLimitConfigId(Integer.parseInt(updateWarehouseDTO.getValueId()));
        } else if (StringUtils.equals("3", updateWarehouseDTO.getType())) {
            updateMerchantStoreMappingArea.setRuleId(Integer.parseInt(updateWarehouseDTO.getValueId()));
        } else {
            return ApiRes.failResponse("操作类型错误");
        }
        if (merchantStoreMappingAreaMapper.updateOrderGoods(updateMerchantStoreMappingArea, updateWarehouseDTO.getIds()) == 0) {
            return ApiRes.failResponse("修改失败");
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes<OrderGoodsSelectBoxVO> orderGoodsSelectBox(OrderGoodsSelectBoxDTO orderGoodsSelectBoxDTO) {
        List<OrderGoodsSelectBoxVO> list;
        if (StringUtils.equals("1", orderGoodsSelectBoxDTO.getType())) {
            list = merchantStoreMappingAreaMapper.warehouseSelectBox();
        } else if (StringUtils.equals("2", orderGoodsSelectBoxDTO.getType())) {
            list = merchantStoreMappingAreaMapper.limitConfigSelectBox();
        } else if (StringUtils.equals("3", orderGoodsSelectBoxDTO.getType())) {
            list = merchantStoreMappingAreaMapper.supplierRuleSelectBox();
        } else {
            return ApiRes.failResponse("类型错误");
        }
        return ApiRes.successResponseData(list);
    }
}
