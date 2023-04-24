package com.yunzyq.yudbapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.core.PageData;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.*;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.model.*;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.enums.StatusTwoEnum;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.RegionalManagerMerchantService;
import com.yunzyq.yudbapp.util.DateUtil;
import com.yunzyq.yudbapp.util.PlatformCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegionalManagerMerchantServiceImpl implements RegionalManagerMerchantService {

    @Resource
    MerchantStoreMapper merchantStoreMapper;
    @Resource
    RedisUtils redisUtils;
    @Resource
    PaymentTypeMapper paymentTypeMapper;
    @Resource
    MerchantMapper merchantMapper;
    @Resource
    RegionalManagerMapper regionalManagerMapper;
    @Resource
    PaymentOrderMasterMapper paymentOrderMasterMapper;
    @Resource
    PaymentOrderExamineMapper paymentOrderExamineMapper;
    @Resource
    PaymentOrderExamineDeailMapper paymentOrderExamineDeailMapper;
    @Resource
    PaymentOrderExamineFileMapper paymentOrderExamineFileMapper;
    @Resource
    MerchantStoreExamineMapper merchantStoreExamineMapper;
    @Resource
    MerchantStoreExamineDeailMapper merchantStoreExamineDeailMapper;
    @Resource
    MerchantStoreExamineFileMapper merchantStoreExamineFileMapper;

    @Override
    public ApiRes<PageWrap<StroePageVo>> findPage(PageWrap<StroePageDto> pageWrap, HttpServletRequest httpServletRequest) {
        StroePageDto stroePageDto = pageWrap.getModel();
        if (stroePageDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String condition = stroePageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            stroePageDto.setCondition(null);
        }
        String vlaue = redisUtils.token(httpServletRequest);

        stroePageDto.setId(vlaue);
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<StroePageVo> list = merchantStoreMapper.findPage(stroePageDto);
        list.forEach(stroePageVo -> {
            stroePageVo.setStatusDesc(StatusTwoEnum.getStatusDesc(Integer.parseInt(stroePageVo.getStatus())));
        });
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes<StorePage2Vo> findPage2(PageWrap<StroePage2Dto> pageWrap, HttpServletRequest httpServletRequest) {
        StroePage2Dto stroePage2Dto = pageWrap.getModel();
        if (stroePage2Dto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String condition = stroePage2Dto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            stroePage2Dto.setCondition(null);
        }
        String vlaue = redisUtils.token(httpServletRequest);
        stroePage2Dto.setId(vlaue);
        List<StorePage2Statistics> storePage2StatisticsList = merchantStoreMapper.storePage2Statistics(stroePage2Dto);
        storePage2StatisticsList.forEach(storePage2Statistics -> {
            storePage2Statistics.setStatusDesc(StatusTwoEnum.getStatusDesc(Integer.parseInt(storePage2Statistics.getStatus())));
        });
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<StorePage2> list = merchantStoreMapper.findPage2(stroePage2Dto);
        list.forEach(stroePageVo -> {
            stroePageVo.setStatusDesc(StatusTwoEnum.getStatusDesc(Integer.parseInt(stroePageVo.getStatus())));
        });
        StorePage2Vo storePage2Vo = new StorePage2Vo();
        storePage2Vo.setStorePage2StatisticsList(storePage2StatisticsList);
        storePage2Vo.setListPageData(PageData.from(new PageInfo<>(list)));
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", storePage2Vo);
    }

    @Override
    public ApiRes<PayTypeListVo> payTypeList() {
        List<PayTypeListVo> list = paymentTypeMapper.queryPayTypeList();
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "参数错误", list);

    }

    @Override
    public ApiRes submitForReview(SubmitForReviewDto submitForReviewDto, HttpServletRequest httpServletRequest) {
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
        String vlaue = redisUtils.token(httpServletRequest);
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
        }

        PaymentType paymentType = paymentTypeMapper.selectByPrimaryKey(payTypeId);
        if (paymentType == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "缴费类型错误", "");
        }
        if (paymentType.getId() == 1 || paymentType.getId() == 2 || paymentType.getId() == 3) {//先判断是否有正在缴费的单子，有则续约缴费完才能申请
            PaymentOrderMaster paymentOrderMaster = paymentOrderMasterMapper.queryByStoreIdAndPayTypeId(merchantStore.getId(), paymentType.getId());
            if (paymentOrderMaster != null) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "请先让加盟商缴纳前面相同类型费用在进行缴费申请", "");
            }
        }
        String remark = submitForReviewDto.getRemark();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //缴费主订单审核
        PaymentOrderMaster paymentOrderMaster = new PaymentOrderMaster();
        paymentOrderMaster.setArea(merchantStore.getArea());
        paymentOrderMaster.setProvince(merchantStore.getProvince());
        paymentOrderMaster.setCity(merchantStore.getCity());
        paymentOrderMaster.setOrderNo(PlatformCodeUtil.orderNo("13GT"));
        paymentOrderMaster.setPaymentStandardMoney(new BigDecimal(money));
        paymentOrderMaster.setMerchantId(merchant.getId());
        paymentOrderMaster.setAddress(merchantStore.getAddress());
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
        paymentOrderMaster.setType(2);
        paymentOrderMaster.setStatus(1);
        paymentOrderMaster.setExamine(2);
        paymentOrderMaster.setSend(2);
        paymentOrderMaster.setDeleted(false);
        paymentOrderMaster.setAdjustmentCount(0);
        paymentOrderMaster.setExamineNum(2);
        paymentOrderMaster.setMoney(new BigDecimal(money));
        try {
            paymentOrderMaster.setExpireTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paymentOrderMaster.setSend(2);
        paymentOrderMaster.setIsPublish(1);
        paymentOrderMaster.setExamine(2);
        paymentOrderMaster.setRemark(remark);
        paymentOrderMaster.setCreateTime(new Date());
        paymentOrderMaster.setCreateUser(Integer.valueOf(vlaue));
        paymentOrderMaster.setDeleted(false);

        paymentOrderMaster.setAdjustmentCount(0);
        paymentOrderMaster.setCity(merchantStore.getCity());
        paymentOrderMaster.setProvince(merchantStore.getProvince());
        paymentOrderMaster.setArea(merchantStore.getArea());
        paymentOrderMaster.setPaymentStandardMoney(new BigDecimal(money));
        paymentOrderMaster.setCycle(sdf.format(paymentOrderMaster.getCreateTime()) + "~" + time);
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
        paymentOrderExamine.setApplyName(regionalManager.getName());
        paymentOrderExamine.setMsg("新订单审核:" + paymentType.getName() + money + "元");
        paymentOrderExamine.setRemark(remark);
        paymentOrderExamine.setDeleted(false);
        paymentOrderExamine.setApplyId(regionalManager.getId());
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
        List<String> approveNames = paymentOrderExamineDeailMapper.getApproveName(1, paymentOrderExamine.getExamineType(), paymentOrderExamineDeail.getStep(), paymentOrderExamineDeail.getType());
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
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "缴费提交成功", "");

    }

    @Override
    public ApiRes<PageWrap<HistoryOrderVo>> historyOrder(HttpServletRequest httpServletRequest, PageWrap<HistoryOrderDto> pageWrap) {
        HistoryOrderDto historyOrderDto = pageWrap.getModel();
        if (historyOrderDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        if (StringUtils.isEmpty(historyOrderDto.getId())) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(historyOrderDto.getId());
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");
        }
        String vlaue = redisUtils.token(httpServletRequest);
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
        }
        historyOrderDto.setRegionalManagerId(vlaue);
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<HistoryOrderVo> list = paymentOrderMasterMapper.historyOrder(historyOrderDto);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(list)));
    }

    @Override
    public ApiRes confirmRenewal(ConfirmRenewalDto confirmRenewalDto, HttpServletRequest httpServletRequest) {

        if (confirmRenewalDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String money = confirmRenewalDto.getMoney();
        if (StringUtils.isEmpty(money)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = confirmRenewalDto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        if (merchantStore.getContractStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "合同状态不对", "");
        }
        Merchant merchant = merchantMapper.selectByPrimaryKey(merchantStore.getMerchantId());
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商信息错误", "");
        }
        String vlaue = redisUtils.token(httpServletRequest);
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
        }
        MerchantStoreExamine merchantStoreExamine = new MerchantStoreExamine();
        merchantStoreExamine.setMerchantStoreId(merchantStore.getId());
        merchantStoreExamine.setMerchantStoreUid(merchantStore.getUid());
        merchantStoreExamine.setExamineType(2);
        merchantStoreExamine.setExamine(1);
        merchantStoreExamine.setRemark(confirmRenewalDto.getRemark());
        merchantStoreExamine.setOldMoney(merchantStore.getManagementExpense());
        merchantStoreExamine.setNewMoney(new BigDecimal(money));
        merchantStoreExamine.setStatus(1);
        merchantStoreExamine.setCreateUser(regionalManager.getId());
        merchantStoreExamine.setCreateName(regionalManager.getName());
        merchantStoreExamine.setDeleted(false);
        merchantStoreExamine.setCreateTime(new Date());

        merchantStoreExamineMapper.insertSelective(merchantStoreExamine);

        MerchantStoreExamineDeail merchantStoreExamineDeail = new MerchantStoreExamineDeail();
        merchantStoreExamineDeail.setMerchantStoreExamineId(merchantStoreExamine.getId());
        merchantStoreExamineDeail.setExamine(1);
        merchantStoreExamineDeail.setStatus(1);
        merchantStoreExamineDeail.setCreateTime(new Date());
        merchantStoreExamineDeail.setDeleted(false);
        merchantStoreExamineDeailMapper.insertSelective(merchantStoreExamineDeail);

        merchantStore.setContractStatus(2);

        merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);

        //文件信息存储
        List<MerchantStoreExamineFile> merchantStoreExamineFiles = new ArrayList<>();
        List<String> photos = confirmRenewalDto.getPhotos();
        photos.stream().forEach(
                s -> {
                    log.info("photos={}", s.length());
                    MerchantStoreExamineFile merchantStoreExamineFile = new MerchantStoreExamineFile();
                    merchantStoreExamineFile.setMerchantStoreExamineId(merchantStoreExamine.getId());
                    merchantStoreExamineFile.setType(4);
                    merchantStoreExamineFile.setUrl(s);
                    merchantStoreExamineFile.setDeleted(false);
                    merchantStoreExamineFile.setStatus(1);
                    merchantStoreExamineFile.setCreateTime(new Date());
                    merchantStoreExamineFiles.add(merchantStoreExamineFile);
                }
        );
        List<FileDto> fileDtos = confirmRenewalDto.getWords();
        fileDtos.stream().forEach(
                fileDto -> {
                    log.info("video={}", fileDtos.size());
                    MerchantStoreExamineFile merchantStoreExamineFile = new MerchantStoreExamineFile();
                    merchantStoreExamineFile.setMerchantStoreExamineId(merchantStoreExamine.getId());
                    merchantStoreExamineFile.setType(3);
                    merchantStoreExamineFile.setUrl(fileDto.getUrl());
                    merchantStoreExamineFile.setName(fileDto.getName());
                    merchantStoreExamineFile.setDeleted(false);
                    merchantStoreExamineFile.setStatus(1);
                    merchantStoreExamineFile.setCreateTime(new Date());
                    merchantStoreExamineFiles.add(merchantStoreExamineFile);
                }
        );
        if (merchantStoreExamineFiles.size() > 0) {
            merchantStoreExamineFileMapper.insertList(merchantStoreExamineFiles);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "确认续约成功", "");
    }

    @Override
    public ApiRes renewalWithdrawal(RenewalWithdrawalDto renewalWithdrawalDto, HttpServletRequest httpServletRequest) {

        if (renewalWithdrawalDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = renewalWithdrawalDto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        if (merchantStore.getContractStatus() != 2 && merchantStore.getContractStatus() != 7) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "状态错误", "");
        }
        if (merchantStore.getContractStatus() == 2) {
            merchantStore.setContractStatus(1);
        } else {
            merchantStore.setContractStatus(6);
        }
        MerchantStoreExamine merchantStoreExamine = merchantStoreExamineMapper.queryByStroeIdAndExamineType(merchantStore.getId(), 2);
        if (merchantStoreExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "系统错误", "");
        }
        if (merchantStoreExamine.getStatus() == 0) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核状态错误", "");
        }
        merchantStoreExamine.setDeleted(true);
        //修改审核主订单状态
        merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        merchantStoreExamineMapper.updateByPrimaryKeySelective(merchantStoreExamine);
        //删除审核详情/审核文件
        merchantStoreExamineDeailMapper.updateDeletedByPaymentOrderExamineId(merchantStoreExamine.getId());
        merchantStoreExamineFileMapper.updateDeletedByPaymentOrderExamineId(merchantStoreExamine.getId());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "撤销成功", "");
    }

    @Override
    public ApiRes signAContract(SignAContractDto signAContractDto, HttpServletRequest httpServletRequest) {
        if (signAContractDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = signAContractDto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        if (merchantStore.getContractStatus() != 3) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "合同状态不对", "");
        }
        if (merchantStore.getManagementExpense().compareTo(merchantStore.getAlreadyManagementExpense()) == 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "还未缴纳足够的管理费用", "");
        }
        Merchant merchant = merchantMapper.selectByPrimaryKey(merchantStore.getMerchantId());
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商信息错误", "");
        }
        String vlaue = redisUtils.token(httpServletRequest);
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
        }
        merchantStore.setContractStatus(4);

        MerchantStoreExamine merchantStoreExamine = new MerchantStoreExamine();
        merchantStoreExamine.setMerchantStoreId(merchantStore.getId());
        merchantStoreExamine.setMerchantStoreUid(merchantStore.getUid());
        merchantStoreExamine.setExamineType(1);
        merchantStoreExamine.setExamine(1);
        merchantStoreExamine.setRemark(signAContractDto.getRemark());
        merchantStoreExamine.setOldTime(merchantStore.getExpireTime());
        Date newTime = DateUtil.endTime("1年后", merchantStore.getExpireTime());
        merchantStoreExamine.setNewTime(newTime);
        merchantStoreExamine.setManagementExpense(merchantStore.getManagementExpense());
        merchantStoreExamine.setStatus(1);
        merchantStoreExamine.setCreateUser(regionalManager.getId());
        merchantStoreExamine.setCreateName(regionalManager.getName());
        merchantStoreExamine.setDeleted(false);
        merchantStoreExamine.setCreateTime(new Date());
        merchantStoreExamineMapper.insertSelective(merchantStoreExamine);


        MerchantStoreExamineDeail merchantStoreExamineDeail = new MerchantStoreExamineDeail();
        merchantStoreExamineDeail.setExamine(1);
        merchantStoreExamineDeail.setStatus(1);
        merchantStoreExamineDeail.setMerchantStoreExamineId(merchantStoreExamine.getId());
        merchantStoreExamineDeail.setCreateTime(new Date());

        merchantStoreExamineDeailMapper.insertSelective(merchantStoreExamineDeail);

        List<MerchantStoreExamineFile> examineFileList = new ArrayList<>();
        List<String> images = signAContractDto.getPhotos();
        List<FileDto> fileDtos = signAContractDto.getWords();
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
        fileDtos.stream().forEach(
                fileDto -> {
                    MerchantStoreExamineFile merchantStoreExamineFile = new MerchantStoreExamineFile();
                    merchantStoreExamineFile.setType(2);
                    merchantStoreExamineFile.setMerchantStoreExamineId(merchantStoreExamine.getId());
                    merchantStoreExamineFile.setStatus(1);
                    merchantStoreExamineFile.setUrl(fileDto.getUrl());
                    merchantStoreExamineFile.setName(fileDto.getName());
                    merchantStoreExamineFile.setCreateTime(new Date());
                    examineFileList.add(merchantStoreExamineFile);
                }
        );
        if (examineFileList.size() > 0) {
            merchantStoreExamineFileMapper.insertList(examineFileList);

        }
        merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "提交成功", "");
    }

    @Override
    public ApiRes withdrawalOfContract(WithdrawalOfContractDto withdrawalOfContractDto, HttpServletRequest httpServletRequest) {
        if (withdrawalOfContractDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = withdrawalOfContractDto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        if (merchantStore.getContractStatus() != 4) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "状态错误", "");
        }
        merchantStore.setContractStatus(3);
        MerchantStoreExamine merchantStoreExamine = merchantStoreExamineMapper.queryByStroeIdAndExamineType(merchantStore.getId(), 1);
        if (merchantStoreExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "系统错误", "");
        }
        if (merchantStoreExamine.getStatus() == 0) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核状态错误", "");
        }
        merchantStoreExamine.setDeleted(true);
        merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        //修改审核主订单状态
        merchantStoreExamineMapper.updateByPrimaryKeySelective(merchantStoreExamine);
        //删除审核详情/审核文件
        merchantStoreExamineDeailMapper.updateDeletedByPaymentOrderExamineId(merchantStoreExamine.getId());
        merchantStoreExamineFileMapper.updateDeletedByPaymentOrderExamineId(merchantStoreExamine.getId());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "撤销成功", "");
    }

    @Override
    public ApiRes rescind(RescindDto rescindDto, HttpServletRequest httpServletRequest) {


        if (rescindDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = rescindDto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        if (merchantStore.getContractStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "合同状态不对", "");
        }
        Merchant merchant = merchantMapper.selectByPrimaryKey(merchantStore.getMerchantId());
        if (merchant == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "加盟商信息错误", "");
        }
        String vlaue = redisUtils.token(httpServletRequest);
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
        }
        merchantStore.setContractStatus(5);
        MerchantStoreExamine merchantStoreExamine = new MerchantStoreExamine();
        merchantStoreExamine.setMerchantStoreId(merchantStore.getId());
        merchantStoreExamine.setMerchantStoreUid(merchantStore.getUid());
        merchantStoreExamine.setExamineType(3);
        merchantStoreExamine.setExamine(1);
        merchantStoreExamine.setRemark(rescindDto.getRemark());
        merchantStoreExamine.setOldTime(merchantStore.getExpireTime());
        merchantStoreExamine.setStatus(1);
        merchantStoreExamine.setCreateUser(regionalManager.getId());
        merchantStoreExamine.setCreateName(regionalManager.getName());
        merchantStoreExamine.setDeleted(false);
        merchantStoreExamine.setCreateTime(new Date());
        merchantStoreExamineMapper.insertSelective(merchantStoreExamine);

        MerchantStoreExamineDeail merchantStoreExamineDeail = new MerchantStoreExamineDeail();
        merchantStoreExamineDeail.setExamine(1);
        merchantStoreExamineDeail.setStatus(1);
        merchantStoreExamineDeail.setMerchantStoreExamineId(merchantStoreExamine.getId());
        merchantStoreExamineDeail.setCreateTime(new Date());
        merchantStoreExamineDeailMapper.insertSelective(merchantStoreExamineDeail);

        List<MerchantStoreExamineFile> examineFileList = new ArrayList<>();
        List<String> images = rescindDto.getPhotos();
        List<FileDto> words = rescindDto.getWords();
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
                word -> {
                    MerchantStoreExamineFile merchantStoreExamineFile = new MerchantStoreExamineFile();
                    merchantStoreExamineFile.setType(2);
                    merchantStoreExamineFile.setMerchantStoreExamineId(merchantStoreExamine.getId());
                    merchantStoreExamineFile.setStatus(1);
                    merchantStoreExamineFile.setUrl(word.getUrl());
                    merchantStoreExamineFile.setName(word.getName());
                    merchantStoreExamineFile.setCreateTime(new Date());
                    examineFileList.add(merchantStoreExamineFile);
                }
        );
        if (examineFileList.size() > 0) {
            merchantStoreExamineFileMapper.insertList(examineFileList);
        }
        merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "提交成功", "");

    }

    @Override
    public ApiRes terminationWithdrawal(TerminationWithdrawalDto terminationWithdrawalDto, HttpServletRequest httpServletRequest) {
        if (terminationWithdrawalDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = terminationWithdrawalDto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        if (merchantStore.getContractStatus() != 5) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "状态错误", "");
        }
        merchantStore.setContractStatus(1);
        MerchantStoreExamine merchantStoreExamine = merchantStoreExamineMapper.queryByStroeIdAndExamineType(merchantStore.getId(), 3);
        if (merchantStoreExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "系统错误", "");
        }
        if (merchantStoreExamine.getStatus() == 0) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核状态错误", "");
        }
        merchantStoreExamine.setDeleted(true);
        merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        //修改审核主订单状态
        merchantStoreExamineMapper.updateByPrimaryKeySelective(merchantStoreExamine);
        //删除审核详情/审核文件
        merchantStoreExamineDeailMapper.updateDeletedByPaymentOrderExamineId(merchantStoreExamine.getId());
        merchantStoreExamineFileMapper.updateDeletedByPaymentOrderExamineId(merchantStoreExamine.getId());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "撤销成功", "");
    }

    @Override
    public ApiRes againConfirmRenewal(AgainConfirmRenewalDto againConfirmRenewalDto, HttpServletRequest httpServletRequest) {

        if (againConfirmRenewalDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String money = againConfirmRenewalDto.getMoney();
        if (StringUtils.isEmpty(money)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = againConfirmRenewalDto.getId();
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(id);
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店不存在", "");
        }
        String vlaue = redisUtils.token(httpServletRequest);
        RegionalManager regionalManager = regionalManagerMapper.selectByPrimaryKey(vlaue);
        if (regionalManager == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "区域经理信息错误", "");
        }
        MerchantStoreExamine merchantStoreExamine = new MerchantStoreExamine();
        merchantStoreExamine.setMerchantStoreId(merchantStore.getId());
        merchantStoreExamine.setMerchantStoreUid(merchantStore.getUid());
        merchantStoreExamine.setExamineType(2);
        merchantStoreExamine.setExamine(1);
        merchantStoreExamine.setRemark(againConfirmRenewalDto.getRemark());
        merchantStoreExamine.setOldMoney(merchantStore.getManagementExpense());
        merchantStoreExamine.setNewMoney(new BigDecimal(money));
        merchantStoreExamine.setStatus(1);
        merchantStoreExamine.setCreateUser(regionalManager.getId());
        merchantStoreExamine.setCreateName(regionalManager.getName());
        merchantStoreExamine.setDeleted(false);
        merchantStoreExamine.setCreateTime(new Date());
        merchantStoreExamineMapper.insertSelective(merchantStoreExamine);

        MerchantStoreExamineDeail merchantStoreExamineDeail = new MerchantStoreExamineDeail();
        merchantStoreExamineDeail.setMerchantStoreExamineId(merchantStoreExamine.getId());
        merchantStoreExamineDeail.setExamine(1);
        merchantStoreExamineDeail.setStatus(1);
        merchantStoreExamineDeail.setCreateTime(new Date());
        merchantStoreExamineDeail.setDeleted(false);
        merchantStoreExamineDeailMapper.insertSelective(merchantStoreExamineDeail);
        merchantStore.setContractStatus(7);
        merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        //文件信息存储
        List<MerchantStoreExamineFile> merchantStoreExamineFiles = new ArrayList<>();

        List<String> photos = againConfirmRenewalDto.getPhotos();
        photos.stream().forEach(
                s -> {
                    log.info("photos={}", s.length());
                    MerchantStoreExamineFile merchantStoreExamineFile = new MerchantStoreExamineFile();
                    merchantStoreExamineFile.setMerchantStoreExamineId(merchantStoreExamine.getId());
                    merchantStoreExamineFile.setType(4);
                    merchantStoreExamineFile.setUrl(s);
                    merchantStoreExamineFile.setDeleted(false);
                    merchantStoreExamineFile.setStatus(1);
                    merchantStoreExamineFile.setCreateTime(new Date());
                    merchantStoreExamineFiles.add(merchantStoreExamineFile);
                }
        );

        List<FileDto> words = againConfirmRenewalDto.getWords();
        words.stream().forEach(
                word -> {
                    log.info("words={}", words.size());
                    MerchantStoreExamineFile merchantStoreExamineFile = new MerchantStoreExamineFile();
                    merchantStoreExamineFile.setMerchantStoreExamineId(merchantStoreExamine.getId());
                    merchantStoreExamineFile.setType(3);
                    merchantStoreExamineFile.setUrl(word.getUrl());
                    merchantStoreExamineFile.setName(word.getName());
                    merchantStoreExamineFile.setDeleted(false);
                    merchantStoreExamineFile.setStatus(1);
                    merchantStoreExamineFile.setCreateTime(new Date());
                    merchantStoreExamineFiles.add(merchantStoreExamineFile);
                }
        );
        if (merchantStoreExamineFiles.size() > 0) {
            merchantStoreExamineFileMapper.insertList(merchantStoreExamineFiles);
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "确认续约成功", "");
    }
}
