package com.ynzyq.yudbadmin.service.business.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.LoginUserInfo;
import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantAgentAreaMapper;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantMapper;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreMapper;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreUidApplyMapper;
import com.ynzyq.yudbadmin.dao.business.dto.AddApplyUidDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ListApplyRecordDTO;
import com.ynzyq.yudbadmin.dao.business.dto.NextStepDTO;
import com.ynzyq.yudbadmin.dao.business.model.Merchant;
import com.ynzyq.yudbadmin.dao.business.model.MerchantAgentArea;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStore;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreUidApply;
import com.ynzyq.yudbadmin.dao.business.vo.ListApplyRecordVO;
import com.ynzyq.yudbadmin.dao.business.vo.NextStepVO;
import com.ynzyq.yudbadmin.enums.RoleEnum;
import com.ynzyq.yudbadmin.enums.StatusEnum;
import com.ynzyq.yudbadmin.service.business.IApplyUidService;
import com.ynzyq.yudbadmin.third.dto.UserInfoDTO;
import com.ynzyq.yudbadmin.third.service.IForeignService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xinchen
 * @date 2022/4/6 14:32
 * @description:
 */
@Slf4j
@Service
public class ApplyUidServiceImpl implements IApplyUidService {

    @Resource
    MerchantStoreUidApplyMapper merchantStoreUidApplyMapper;

    @Resource
    MerchantMapper merchantMapper;

    @Resource
    MerchantAgentAreaMapper merchantAgentAreaMapper;

    @Resource
    MerchantStoreMapper merchantStoreMapper;

    @Resource
    IForeignService iForeignService;

    private ReentrantLock lock = new ReentrantLock();

    @Override
    public ApiRes<ListApplyRecordVO> listApplyRecord(PageWrap<ListApplyRecordDTO> pageWrap, HttpServerRequest httpServerRequest) {
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        ListApplyRecordDTO listApplyRecordDTO = pageWrap.getModel();
        List<Integer> roleIds = merchantStoreUidApplyMapper.listRoleId(loginUserInfo.getId());
        List<ListApplyRecordVO> listApplyRecordVOS;
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        if (CollectionUtils.isNotEmpty(roleIds) && roleIds.size() > 0 && RoleEnum.isShowAll(roleIds)) {
            listApplyRecordVOS = merchantStoreUidApplyMapper.listApplyRecord(listApplyRecordDTO);
        } else {
            listApplyRecordDTO.setUserId(loginUserInfo.getId());
            listApplyRecordVOS = merchantStoreUidApplyMapper.listApplyRecord(listApplyRecordDTO);
        }
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(listApplyRecordVOS)));
    }

    @Override
    public ApiRes<NextStepVO> nextStep(NextStepDTO nextStepDTO) {
        // 查询手机号是否重复
        Merchant queryMerchant = new Merchant();
        queryMerchant.setMobile(nextStepDTO.getMobile());
        int count = merchantMapper.selectCount(queryMerchant);
        if (count > 0) {
            NextStepVO nextStepVO = merchantStoreUidApplyMapper.singleMerchant(nextStepDTO.getMobile());
            return ApiRes.successResponseData(nextStepVO);
        }
        // 是否为代理查询
        if (StringUtils.equals("1", nextStepDTO.getIsAgent())) {
            MerchantAgentArea queryMerchantAgentArea = new MerchantAgentArea();
            queryMerchantAgentArea.setMerchantName(nextStepDTO.getSignatory());
            int num = merchantAgentAreaMapper.selectCount(queryMerchantAgentArea);
            if (num < 0) {
                return ApiRes.failResponse("该签约人目前不是代理，请修改申请信息或联系数据中心-徐凡博");
            }
        }
        return ApiRes.successResponse();
    }

    @Override
    public ApiRes addApplyUid(AddApplyUidDTO addApplyUidDTO, HttpServerRequest httpServerRequest) {
        String uid;
        lock.lock();
        try {
            // 是否为代理查询
            MerchantAgentArea merchantAgentArea = null;
            if (StringUtils.equals("1", addApplyUidDTO.getIsAgent())) {
                MerchantAgentArea queryMerchantAgentArea = new MerchantAgentArea();
                queryMerchantAgentArea.setMerchantName(addApplyUidDTO.getSignatory());
                merchantAgentArea = merchantAgentAreaMapper.selectOne(queryMerchantAgentArea);
                if (merchantAgentArea == null) {
                    return ApiRes.failResponse("该签约人目前不是代理，请修改申请信息或联系数据中心-徐凡博");
                }
            }
            Merchant merchant = merchantStoreUidApplyMapper.getMerchant(addApplyUidDTO.getMobile());
            // 添加加盟商
            if (merchant == null) {
                Merchant addMerchant = new Merchant();
                addMerchant.setName(addApplyUidDTO.getSignatory());
                addMerchant.setMobile(addApplyUidDTO.getMobile());
                addMerchant.setIdNumber(addApplyUidDTO.getIdCard());
                addMerchant.setPassword("tc6BZwktKlwSWeQCzh+TqQ==");
                addMerchant.setStatus(StatusEnum.ENABLE.getStatus());
                addMerchant.setIsAgent(Integer.parseInt(addApplyUidDTO.getIsAgent()));
                addMerchant.setCreateTime(new Date());
                merchantMapper.insertSelective(addMerchant);
                merchant = addMerchant;
            } else {
                Merchant updateMerchant = new Merchant();
                updateMerchant.setId(merchant.getId());
                updateMerchant.setIdNumber(addApplyUidDTO.getIdCard());
                updateMerchant.setUpdateTime(new Date());
                merchantMapper.updateByPrimaryKeySelective(updateMerchant);
            }
            // 添加门店
            MerchantStore addMerchantStore = new MerchantStore();
            String maxUid = merchantStoreUidApplyMapper.getMaxUid();

            if (StringUtils.isBlank(maxUid)) {
                uid = "3850";
            } else {
                uid = String.valueOf(Integer.parseInt(maxUid) + 1);
            }
            addMerchantStore.setUid(uid);
            addMerchantStore.setMerchantId(merchant.getId());
            addMerchantStore.setSignatory(addApplyUidDTO.getSignatory());
            addMerchantStore.setMobile(addApplyUidDTO.getMobile());
            addMerchantStore.setSignTime(DateUtil.parse(addApplyUidDTO.getSignTime(), "yyyy-MM-dd"));
            addMerchantStore.setIdNumber(addApplyUidDTO.getIdCard());
            addMerchantStore.setExpireTime(DateUtil.parse(addApplyUidDTO.getExpireTime(), "yyyy-MM-dd"));
            addMerchantStore.setServiceExpireTime(DateUtil.parse(addApplyUidDTO.getExpireTime(), "yyyy-MM-dd"));
            addMerchantStore.setManagementExpense(new BigDecimal(addApplyUidDTO.getManageMoney()));
            addMerchantStore.setNeedManagementExpense(new BigDecimal(addApplyUidDTO.getManageMoney()));
            addMerchantStore.setBondMoney(new BigDecimal(addApplyUidDTO.getBondMoney()));
            addMerchantStore.setFranchiseFee(new BigDecimal(addApplyUidDTO.getFranchiseMoney()));
            addMerchantStore.setStatus(3);
            addMerchantStore.setStatusTwo(22);
            addMerchantStore.setContractStatus(1);
            if (merchantAgentArea != null) {
                addMerchantStore.setAgentId(merchantAgentArea.getId());
            }
            addMerchantStore.setCreateTime(new Date());
            merchantStoreMapper.insertSelective(addMerchantStore);
            try {
                UserInfoDTO userInfoDTO = new UserInfoDTO("1", uid, "1");
                String msg = iForeignService.userInfo(userInfoDTO);
                log.info("新建门店授权码请求金蝶响应参数：{}", msg);
            } catch (Exception e) {
                log.error("同步金蝶失败", e);
            }

            // 添加操作记录
            MerchantStoreUidApply addMerchantStoreUidApply = new MerchantStoreUidApply();
            addMerchantStoreUidApply.setUid(uid);
            addMerchantStoreUidApply.setSignatory(addApplyUidDTO.getSignatory());
            addMerchantStoreUidApply.setMobile(addApplyUidDTO.getMobile());
            addMerchantStoreUidApply.setIdCard(addApplyUidDTO.getIdCard());
            addMerchantStoreUidApply.setSignTime(DateUtil.parse(addApplyUidDTO.getSignTime(), "yyyy-MM-dd"));
            addMerchantStoreUidApply.setExpireTime(DateUtil.parse(addApplyUidDTO.getExpireTime(), "yyyy-MM-dd"));
            addMerchantStoreUidApply.setManageMoney(new BigDecimal(addApplyUidDTO.getManageMoney()));
            addMerchantStoreUidApply.setBondMoney(new BigDecimal(addApplyUidDTO.getBondMoney()));
            addMerchantStoreUidApply.setFranchiseMoney(new BigDecimal(addApplyUidDTO.getFranchiseMoney()));
            addMerchantStoreUidApply.setIsAgent(Integer.parseInt(addApplyUidDTO.getIsAgent()));
            addMerchantStoreUidApply.setIsCooperate(Integer.parseInt(addApplyUidDTO.getIsCooperate()));
            LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
            addMerchantStoreUidApply.setCreateUser(loginUserInfo.getId());
            merchantStoreUidApplyMapper.insertSelective(addMerchantStoreUidApply);
        } finally {
            lock.unlock();
        }
        return ApiRes.successResponseData(uid);
    }
}
