package com.yunzyq.yudbapp.service.impl;


import com.google.common.collect.Lists;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.dao.MerchantComplaintFileMapper;
import com.yunzyq.yudbapp.dao.MerchantComplaintMapper;
import com.yunzyq.yudbapp.dao.MerchantMapper;
import com.yunzyq.yudbapp.dao.dto.AddComplaintDTO;
import com.yunzyq.yudbapp.dao.dto.ComplaintDetailDTO;
import com.yunzyq.yudbapp.dao.model.Merchant;
import com.yunzyq.yudbapp.dao.model.MerchantComplaint;
import com.yunzyq.yudbapp.dao.model.MerchantComplaintFile;
import com.yunzyq.yudbapp.dao.vo.ComplaintDetailVO;
import com.yunzyq.yudbapp.dao.vo.ComplaintFileVO;
import com.yunzyq.yudbapp.dao.vo.DealWithVO;
import com.yunzyq.yudbapp.dao.vo.ListComplaintVO;
import com.yunzyq.yudbapp.exception.BaseException;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.IComplaintService;
import com.yunzyq.yudbapp.util.PlatformCodeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/1/25 13:49
 * @description:
 */
@Service
public class ComplaintServiceImpl implements IComplaintService {
    @Resource
    MerchantComplaintMapper merchantComplaintMapper;

    @Resource
    MerchantComplaintFileMapper merchantComplaintFileMapper;

    @Resource
    RedisUtils redisUtils;

    @Resource
    MerchantMapper merchantMapper;

    @Value("${imageUrl}")
    private String imageUrl;

    @Override
    public ApiRes<ListComplaintVO> listComplaint(HttpServletRequest httpServletRequest) {
        String merchantId = redisUtils.token(httpServletRequest);
        List<ListComplaintVO> listComplaintVOS = merchantComplaintMapper.listComplaint(Integer.parseInt(merchantId));
        if (listComplaintVOS.size() == 0) {
            listComplaintVOS = Lists.newArrayList();
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", listComplaintVOS);
    }

    @Override
    public ApiRes<ComplaintDetailVO> complaintDetail(ComplaintDetailDTO complaintDetailDTO) {
        MerchantComplaint merchantComplaint = merchantComplaintMapper.selectByPrimaryKey(complaintDetailDTO.getId());
        if (merchantComplaint == null) {
            return ApiRes.failed("投诉记录不存在");
        }
        List<ComplaintFileVO> files = merchantComplaintFileMapper.listFile(merchantComplaint.getId());
        files.forEach(complaintFileVO -> {
            complaintFileVO.setUrl(imageUrl + complaintFileVO.getUrl());
        });
        List<DealWithVO> records = Lists.newArrayList();
        DealWithVO dealWithVO = new DealWithVO();
        dealWithVO.setStatus("已发起");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dealWithVO.setHandleTime(sdf.format(merchantComplaint.getCreateTime()));
        dealWithVO.setResult("投诉已受理，您反馈的问题正在核查中");
        records.add(dealWithVO);
        if (merchantComplaint.getStatus().equals(2)) {
            DealWithVO dealWithVO2 = new DealWithVO();
            dealWithVO2.setStatus("已处理");
            dealWithVO2.setHandleTime(sdf.format(merchantComplaint.getHandleTime()));
            dealWithVO2.setResult(merchantComplaint.getResult());
            records.add(dealWithVO2);
        }
        ComplaintDetailVO complaintDetailVO = new ComplaintDetailVO();
        complaintDetailVO.setUid(merchantComplaint.getStoreUid());
        complaintDetailVO.setContent(merchantComplaint.getContent());
        complaintDetailVO.setFiles(files);
        complaintDetailVO.setRecords(records);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", complaintDetailVO);
    }

    @Override
    public ApiRes uidSelectBox(HttpServletRequest httpServletRequest) {
        String merchantId = redisUtils.token(httpServletRequest);
        List<String> uidList = merchantComplaintMapper.listUid(Integer.parseInt(merchantId));
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", uidList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApiRes addComplaint(AddComplaintDTO addComplaintDTO, HttpServletRequest httpServletRequest) {
        String merchantId = redisUtils.token(httpServletRequest);
        Merchant merchant = merchantMapper.selectByPrimaryKey(merchantId);
        if (merchant == null) {
            return ApiRes.failed("加盟商不存在");
        }
        MerchantComplaint merchantComplaint = new MerchantComplaint();
        merchantComplaint.setComplaintNo("YD" + PlatformCodeUtil.getRandomNum(16));
        merchantComplaint.setStoreUid(addComplaintDTO.getUid());
        merchantComplaint.setMerchantId(merchant.getId());
        merchantComplaint.setContent(addComplaintDTO.getContent());
        merchantComplaint.setStatus(1);
        merchantComplaint.setCreateTime(new Date());
        if (merchantComplaintMapper.insertSelective(merchantComplaint) == 0) {
            return ApiRes.failed("失败");
        }
        List<MerchantComplaintFile> files = Lists.newArrayList();
        addComplaintDTO.getFiles().forEach(url -> {
            MerchantComplaintFile merchantComplaintFile = new MerchantComplaintFile();
            merchantComplaintFile.setComplaintId(merchantComplaint.getId());
            merchantComplaintFile.setUrl(url);
            merchantComplaintFile.setCreateTime(new Date());
            files.add(merchantComplaintFile);
        });
        if (files.size() > 0 && merchantComplaintFileMapper.insertList(files) == 0) {
            throw new BaseException(CommonConstant.FAIL_CODE, "图片录入失败");
        }
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", "");
    }

}
