package com.ynzyq.yudbadmin.service.business.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantComplaintFileMapper;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantComplaintMapper;
import com.ynzyq.yudbadmin.dao.business.dto.DealWithDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ListComplaintDTO;
import com.ynzyq.yudbadmin.dao.business.model.MerchantComplaint;
import com.ynzyq.yudbadmin.dao.business.vo.ComplaintFileVO;
import com.ynzyq.yudbadmin.dao.business.vo.ListComplaintVO;
import com.ynzyq.yudbadmin.service.business.IComplaintService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Value("${imageUrl}")
    private String imageUrl;

    @Override
    public ApiRes<ListComplaintVO> listComplaint(PageWrap<ListComplaintDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<ListComplaintVO> listComplaintVOS = merchantComplaintMapper.listComplaint(pageWrap.getModel());
        if (listComplaintVOS.size() == 0) {
            listComplaintVOS = Lists.newArrayList();
        }
        listComplaintVOS.forEach(listComplaintVO -> {
            List<ComplaintFileVO> files = merchantComplaintFileMapper.listFile(Integer.parseInt(listComplaintVO.getId()));
            files.forEach(complaintFileVO -> {
                complaintFileVO.setUrl(imageUrl + complaintFileVO.getUrl());
            });
            listComplaintVO.setFiles(files);
        });
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(listComplaintVOS)));
    }

    @Override
    public ApiRes dealWith(DealWithDTO dealWithDTO) {
        MerchantComplaint merchantComplaint = merchantComplaintMapper.selectByPrimaryKey(dealWithDTO.getId());
        if (merchantComplaint == null) {
            return ApiRes.failResponse("投诉内容不存在");
        }
        MerchantComplaint updateMerchantComplaint = new MerchantComplaint();
        updateMerchantComplaint.setId(merchantComplaint.getId());
        updateMerchantComplaint.setStatus(2);
        updateMerchantComplaint.setResult(dealWithDTO.getContent());
        updateMerchantComplaint.setHandleTime(new Date());
        if (merchantComplaintMapper.updateByPrimaryKeySelective(updateMerchantComplaint) == 0) {
            return ApiRes.failResponse("处理失败");
        }
        return ApiRes.successResponse();
    }
}
