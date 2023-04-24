package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.dao.dto.AddComplaintDTO;
import com.yunzyq.yudbapp.dao.dto.ComplaintDetailDTO;
import com.yunzyq.yudbapp.dao.vo.ComplaintDetailVO;
import com.yunzyq.yudbapp.dao.vo.ListComplaintVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xinchen
 * @date 2022/1/25 13:48
 * @description:
 */
public interface IComplaintService {
    ApiRes<ListComplaintVO> listComplaint(HttpServletRequest httpServletRequest);

    ApiRes<ComplaintDetailVO> complaintDetail(ComplaintDetailDTO complaintDetailDTO);

    ApiRes uidSelectBox(HttpServletRequest httpServletRequest);

    ApiRes addComplaint(AddComplaintDTO addComplaintDTO,HttpServletRequest httpServletRequest);
}
