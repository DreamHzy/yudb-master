package com.yunzyq.yudbapp.api.merchant;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.dao.dto.AddComplaintDTO;
import com.yunzyq.yudbapp.dao.dto.ComplaintDetailDTO;
import com.yunzyq.yudbapp.dao.vo.ComplaintDetailVO;
import com.yunzyq.yudbapp.dao.vo.ListComplaintVO;
import com.yunzyq.yudbapp.service.IComplaintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xinchen
 * @date 2022/1/25 11:50
 * @description:
 */
@Api(tags = "投诉记录")
@Slf4j
@RestController
public class ComplaintController {

    @Resource
    IComplaintService iComplaintService;

    /**
     * 投诉记录
     *
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("投诉记录")
    @PostMapping("/merchant/listComplaint")
    public ApiRes<ListComplaintVO> listComplaint(HttpServletRequest httpServletRequest) {
        return iComplaintService.listComplaint(httpServletRequest);
    }

    /**
     * 投诉详情
     *
     * @param complaintDetailDTO
     * @return
     */
    @ApiOperation("投诉详情")
    @PostMapping("/merchant/complaintDetail")
    public ApiRes<ComplaintDetailVO> complaintDetail(@RequestBody ComplaintDetailDTO complaintDetailDTO) {
        return iComplaintService.complaintDetail(complaintDetailDTO);
    }

    /**
     * 授权号下拉框
     *
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("授权号下拉框")
    @PostMapping("/merchant/uidSelectBox")
    public ApiRes uidSelectBox(HttpServletRequest httpServletRequest) {
        return iComplaintService.uidSelectBox(httpServletRequest);
    }

    /**
     * 添加投诉
     *
     * @param addComplaintDTO
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("添加投诉")
    @PostMapping("/merchant/addComplaint")
    public ApiRes addComplaint(@RequestBody AddComplaintDTO addComplaintDTO, HttpServletRequest httpServletRequest) {
        return iComplaintService.addComplaint(addComplaintDTO, httpServletRequest);
    }
}
