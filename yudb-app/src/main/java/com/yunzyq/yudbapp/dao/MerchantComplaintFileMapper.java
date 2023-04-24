package com.yunzyq.yudbapp.dao;

import com.yunzyq.yudbapp.dao.model.MerchantComplaintFile;
import java.util.List;

import com.yunzyq.yudbapp.dao.vo.ComplaintFileVO;
import com.yunzyq.yudbapp.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

public interface MerchantComplaintFileMapper extends MyBaseMapper<MerchantComplaintFile> {
    @Select("SELECT URL FROM merchant_complaint_file WHERE COMPLAINT_ID = #{complaintId}")
    List<ComplaintFileVO> listFile(Integer complaintId);
}