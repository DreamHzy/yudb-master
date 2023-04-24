package com.ynzyq.yudbadmin.dao.business.dao;

import com.ynzyq.yudbadmin.dao.business.model.MerchantComplaintFile;

import com.ynzyq.yudbadmin.dao.business.vo.ComplaintFileVO;
import com.ynzyq.yudbadmin.util.MyBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MerchantComplaintFileMapper extends MyBaseMapper<MerchantComplaintFile> {
    @Select("SELECT URL FROM merchant_complaint_file WHERE COMPLAINT_ID = #{complaintId}")
    List<ComplaintFileVO> listFile(Integer complaintId);
}