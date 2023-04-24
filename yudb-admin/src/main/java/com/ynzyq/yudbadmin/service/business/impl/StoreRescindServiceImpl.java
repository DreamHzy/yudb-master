package com.ynzyq.yudbadmin.service.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynzyq.yudbadmin.core.model.*;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreExamineDeailMapper;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreExamineFileMapper;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreExamineMapper;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreMapper;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDetailDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPopupDto;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStore;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreExamine;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreExamineDeail;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStoreExamineFile;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.StoreRescindService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StoreRescindServiceImpl implements StoreRescindService {
    @Value("${imageUrl}")
    private String imageurl;
    @Resource
    MerchantStoreExamineMapper merchantStoreExamineMapper;
    @Resource
    MerchantStoreExamineDeailMapper merchantStoreExamineDeailMapper;
    @Resource
    MerchantStoreExamineFileMapper merchantStoreExamineFileMapper;
    @Resource
    MerchantStoreMapper merchantStoreMapper;

    @Override
    public ApiRes<PageWrap<StoreRescindPageVo>> findPage(PageWrap<StoreReviewPageDto> pageWrap) {
        StoreReviewPageDto storeReviewPageDto = pageWrap.getModel();
        if (storeReviewPageDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String condition = storeReviewPageDto.getCondition();
        if (StringUtils.isEmpty(condition)) {
            storeReviewPageDto.setCondition(null);
        }
        if (StringUtils.isEmpty(storeReviewPageDto.getEndTime())) {
            storeReviewPageDto.setEndTime(null);
            storeReviewPageDto.setStartTime(null);
        } else {
            String startTime = storeReviewPageDto.getStartTime();
            String endTime = storeReviewPageDto.getEndTime();
            startTime = startTime + " 00:00:00";
            endTime = endTime + " 23:59:59";
            storeReviewPageDto.setEndTime(endTime);
            storeReviewPageDto.setStartTime(startTime);
        }
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<StoreRescindPageVo> storeRescindPageVos = merchantStoreExamineMapper.storeRescindPageVos(storeReviewPageDto);
        storeRescindPageVos.stream().forEach(
                storeReviewPageVo -> {
                    switch (storeReviewPageVo.getExamineOneStatus()) {
                        case "1":
                            storeReviewPageVo.setExamineOneStatus("待审核");
                            storeReviewPageVo.setExamineTwoStatus("");
                            break;
                        case "2":
                            storeReviewPageVo.setExamineOneStatus("审核通过");
                            break;
                        case "3":
                            storeReviewPageVo.setExamineOneStatus("拒绝");
                            break;
                        default:
                            storeReviewPageVo.setExamineOneStatus("");
                    }
                    if (!StringUtils.isEmpty(storeReviewPageVo.getExamineTwoStatus())) {
                        switch (storeReviewPageVo.getExamineTwoStatus()) {
                            case "1":
                                storeReviewPageVo.setExamineTwoStatus("待审核");
                                break;
                            case "2":
                                storeReviewPageVo.setExamineTwoStatus("审核通过");
                                break;
                            default:
                                storeReviewPageVo.setExamineTwoStatus("拒绝");
                        }
                    } else {
                        storeReviewPageVo.setExamineTwoStatus("");

                    }

                }
        );
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", PageData.from(new PageInfo<>(storeRescindPageVos)));
    }


    @Override
    public ApiRes<StoreRescindDetailVo> detail(StoreReviewDetailDto storeReviewDetailDto) {
        if (storeReviewDetailDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = storeReviewDetailDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }

        MerchantStoreExamine merchantStoreExamine = merchantStoreExamineMapper.selectByPrimaryKey(id);
        if (merchantStoreExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "订单不存在", "");
        }
        //审核明细
        List<MerchantStoreExamineDeail> merchantStoreExamineDeails = merchantStoreExamineDeailMapper.queryByExamineId(merchantStoreExamine.getId());
        //文件明细
        List<MerchantStoreExamineFile> paymentOrderExamineFileList = merchantStoreExamineFileMapper.queryByExamineId(merchantStoreExamine.getId());


        StoreRescindDetailVo storeRescindDetailVo = new StoreRescindDetailVo();


        storeRescindDetailVo.setUid(merchantStoreExamine.getMerchantStoreUid());

        List<ExamineDetailVo> examineDetailVoList = new ArrayList<>();
        merchantStoreExamineDeails.stream().forEach(
                paymentOrderExamineDeail -> {
                    ExamineDetailVo examineDetailVo = new ExamineDetailVo();
                    if (paymentOrderExamineDeail.getExamine() == 1) {
                        examineDetailVo.setName("一审");
                    } else {
                        examineDetailVo.setName("二审");
                    }

                    switch (paymentOrderExamineDeail.getStatus()) {
                        case 1:
                            examineDetailVo.setExamineStatus("待审核");
                            break;
                        case 2:
                            examineDetailVo.setExamineStatus("审核通过");
                            break;
                        default:
                            examineDetailVo.setExamineStatus("拒绝");
                    }
                    examineDetailVo.setExamineMsg(paymentOrderExamineDeail.getRemark());
                    examineDetailVo.setExamineName(paymentOrderExamineDeail.getCreateName());
                    examineDetailVoList.add(examineDetailVo);
                }
        );
        storeRescindDetailVo.setExamineDetailVoList(examineDetailVoList);


        List<ExamineFileTwoVo> examineFileVoList = new ArrayList<>();

//        List<String> words = new ArrayList<>();
//        List<String> image = new ArrayList<>();
//        paymentOrderExamineFileList.stream().forEach(
//                paymentOrderExamineFile -> {
//                    if (paymentOrderExamineFile.getType() == 4) {
//                        image.add(imageurl + paymentOrderExamineFile.getUrl());
//                    }
//                    if (paymentOrderExamineFile.getType() == 2) {
//                        words.add(imageurl + paymentOrderExamineFile.getUrl());
//
//                    }
//                }
//        );

        List<FileVo> words = new ArrayList<>();
        List<FileVo> image = new ArrayList<>();
        paymentOrderExamineFileList.stream().forEach(
                paymentOrderExamineFile -> {
                    FileVo fileVo = new FileVo();
                    if (paymentOrderExamineFile.getType() == 4) {
                        fileVo.setUrl(imageurl + paymentOrderExamineFile.getUrl());
                        fileVo.setName(paymentOrderExamineFile.getName());
                        image.add(fileVo);
                    }
                    if (paymentOrderExamineFile.getType() == 2) {
                        fileVo.setUrl(imageurl + paymentOrderExamineFile.getUrl());
                        fileVo.setName(paymentOrderExamineFile.getName());
                        words.add(fileVo);
                    }
                }
        );


        ExamineFileTwoVo examineFileVoImage = new ExamineFileTwoVo();
        examineFileVoImage.setName("图片");
        examineFileVoImage.setFileS(image);
        examineFileVoList.add(examineFileVoImage);
        ExamineFileTwoVo examineFileVoWords = new ExamineFileTwoVo();
        examineFileVoWords.setName("文档");
        examineFileVoWords.setFileS(words);
        examineFileVoList.add(examineFileVoWords);
        storeRescindDetailVo.setExamineFileVoList(examineFileVoList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", storeRescindDetailVo);
    }

    @Override
    public ApiRes one(StoreReviewDto storeReviewDto) {

        if (storeReviewDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = storeReviewDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String status = storeReviewDto.getStatus();
        if (!"2".equals(status) && !"3".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");

        }

        String msg = storeReviewDto.getMsg();
        MerchantStoreExamine merchantStoreExamine = merchantStoreExamineMapper.selectByPrimaryKey(id);
        if (merchantStoreExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核订单不存在", "");
        }
        if (merchantStoreExamine.getExamine() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }
        //查询一审内容
        MerchantStoreExamineDeail merchantStoreExamineDeail = merchantStoreExamineDeailMapper.queryByOne(merchantStoreExamine);
        if (merchantStoreExamineDeail == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }
        if (merchantStoreExamineDeail.getStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核状态错误", "");
        }

        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(merchantStoreExamine.getMerchantStoreId());
        if (merchantStore == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");

        }
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        //组装一审数据
        merchantStoreExamineDeail.setStatus(Integer.valueOf(status));
        merchantStoreExamineDeail.setRemark(msg);
        merchantStoreExamineDeail.setExamineTime(new Date());
        merchantStoreExamineDeail.setUpdateTime(new Date());
        merchantStoreExamineDeail.setCreateUser(loginUserInfo.getId());
        merchantStoreExamineDeail.setCreateName(loginUserInfo.getRealname());

        //如果是审核通过的生成二审订单明细
        if ("2".equals(status)) {//生成二审订单
            MerchantStoreExamineDeail merchantStoreExamineDeailTwo = new MerchantStoreExamineDeail();
            merchantStoreExamineDeailTwo.setMerchantStoreExamineId(merchantStoreExamine.getId());
            merchantStoreExamineDeailTwo.setExamine(2);
            merchantStoreExamineDeailTwo.setStatus(1);
            merchantStoreExamineDeailTwo.setCreateTime(new Date());
            merchantStoreExamineDeailMapper.insertSelective(merchantStoreExamineDeailTwo);
            //将主订单修改为二审
            merchantStoreExamine.setExamine(2);
        } else {//修改主订单的状态
            //将主订单修改为审核完成
            merchantStoreExamine.setRefuse(msg);
            merchantStoreExamine.setExamine(0);
            merchantStoreExamine.setStatus(3);
            merchantStoreExamine.setCompleteTime(new Date());
            merchantStore.setContractStatus(1);
            merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        }
        merchantStoreExamineDeailMapper.updateByPrimaryKeySelective(merchantStoreExamineDeail);
        merchantStoreExamineMapper.updateByPrimaryKeySelective(merchantStoreExamine);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "审核成功", "");
    }

    @Override
    public ApiRes<StoreReviewPopupVo> popup(StoreReviewPopupDto storeReviewPopupDto) {
        if (storeReviewPopupDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = storeReviewPopupDto.getId();
        MerchantStoreExamine merchantStoreExamine = merchantStoreExamineMapper.selectByPrimaryKey(id);
        if (merchantStoreExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核订单不存在", "");
        }
        StoreReviewPopupVo storeReviewPopupVo = new StoreReviewPopupVo();
        //查询一审内容
        MerchantStoreExamineDeail merchantStoreExamineDeail = merchantStoreExamineDeailMapper.queryByOne(merchantStoreExamine);
        storeReviewPopupVo.setExamineOneMsg(merchantStoreExamineDeail.getRemark());
        storeReviewPopupVo.setExamineOneName(merchantStoreExamineDeail.getCreateName());
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", storeReviewPopupVo);
    }

    @Override
    public ApiRes two(StoreReviewDto storeReviewDto) {
        if (storeReviewDto == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String id = storeReviewDto.getId();
        if (StringUtils.isEmpty(id)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");
        }
        String status = storeReviewDto.getStatus();
        if (!"2".equals(status) && !"3".equals(status)) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "参数错误", "");

        }
        String msg = storeReviewDto.getMsg();
        MerchantStoreExamine merchantStoreExamine = merchantStoreExamineMapper.selectByPrimaryKey(id);
        if (merchantStoreExamine == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核订单不存在", "");
        }
        if (merchantStoreExamine.getExamine() != 2) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }
        MerchantStoreExamineDeail merchantStoreExamineDeail = merchantStoreExamineDeailMapper.queryByTwo(merchantStoreExamine);
        if (merchantStoreExamineDeail == null) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "流程错误", "");
        }
        if (merchantStoreExamineDeail.getStatus() != 1) {
            return ApiRes.response(CommonConstant.FAIL_CODE, "审核状态错误", "");
        }

        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        merchantStoreExamineDeail.setStatus(Integer.valueOf(status));
        merchantStoreExamineDeail.setRemark(msg);
        merchantStoreExamineDeail.setExamineTime(new Date());
        merchantStoreExamineDeail.setUpdateTime(new Date());
        merchantStoreExamineDeail.setCreateUser(loginUserInfo.getId());
        merchantStoreExamineDeail.setCreateName(loginUserInfo.getRealname());
        MerchantStore merchantStore = merchantStoreMapper.selectByPrimaryKey(merchantStoreExamine.getMerchantStoreId());
        if ("2".equals(status)) {//通过该后需要对门店的续约时间进行操作/管理费等金额进行操作
            if (merchantStore == null) {
                return ApiRes.response(CommonConstant.FAIL_CODE, "门店信息错误", "");
            }
            merchantStore.setContractStatus(6);
            merchantStoreExamine.setStatus(2);
            merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        } else {
            merchantStoreExamine.setRefuse(msg);
            merchantStoreExamine.setStatus(3);
            merchantStore.setContractStatus(1);
            merchantStoreMapper.updateByPrimaryKeySelective(merchantStore);
        }
        //将主订单改为审核完成
        merchantStoreExamine.setExamine(0);
        merchantStoreExamine.setCompleteTime(new Date());
        //将缴费订单修改为未审核状态
        merchantStoreExamineDeailMapper.updateByPrimaryKeySelective(merchantStoreExamineDeail);
        merchantStoreExamineMapper.updateByPrimaryKeySelective(merchantStoreExamine);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "审核成功", "");
    }
}
