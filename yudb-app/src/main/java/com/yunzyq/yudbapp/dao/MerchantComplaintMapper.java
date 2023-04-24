package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.MerchantComplaint;

import com.yunzyq.yudbapp.dao.vo.ListComplaintVO;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantComplaintMapper extends MyBaseMapper<MerchantComplaint> {
    @Select("SELECT ID,COMPLAINT_NO,`STATUS`,CREATE_TIME FROM merchant_complaint WHERE MERCHANT_ID = #{merchantId} ORDER BY ID DESC")
    List<ListComplaintVO> listComplaint(Integer merchantId);

    @Select("SELECT UID FROM merchant_store WHERE MERCHANT_ID= #{merchantId}")
    List<String> listUid(Integer merchantId);
}