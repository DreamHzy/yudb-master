package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.vo.SystemUserVO;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.IApproveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xinchen
 * @date 2021/12/24 11:47
 * @description:
 */
@Api(tags = "审核管理")
@Slf4j
@RestController
public class ApproveController {

    @Resource
    IApproveService iApproveService;

    /**
     * 审批流列表
     *
     * @param pageWrap
     * @return
     */
    @ApiOperation("审批流列表")
    @PostMapping("/approve/listApproveProcess")
    public ApiRes<ListApproveProcessVO> listApproveProcess(@RequestBody PageWrap<ListApproveProcessDTO> pageWrap) {
        return iApproveService.listApproveProcess(pageWrap);
    }

    /**
     * 调整前查看
     *
     * @param approveProcessDetailDTO
     * @return
     */
    @ApiOperation("调整前查看")
    @PostMapping("/approve/approveProcessDetail")
    public ApiRes<ApproveProcessDetailVO> approveProcessDetail(@RequestBody ApproveProcessDetailDTO approveProcessDetailDTO) {
        return iApproveService.approveProcessDetail(approveProcessDetailDTO);
    }

    /**
     * 审批人/会签人下拉框
     *
     * @return
     */
    @ApiOperation("审批人/会签人下拉框")
    @PostMapping("/approve/userSelectBox")
    public ApiRes<SystemUserVO> userSelectBox() {
        return iApproveService.userSelectBox();
    }

    /**
     * 审核类型下拉框
     *
     * @return
     */
    @ApiOperation("审核类型下拉框")
    @PostMapping("/approve/examineTypeSelectBox")
    public ApiRes<SystemUserVO> examineTypeSelectBox() {
        return iApproveService.examineTypeSelectBox();
    }

    /**
     * 添加审批流
     *
     * @param addApproveProcessDTO
     * @return
     */
    @ApiOperation("添加审批流")
    @PostMapping("/approve/addApproveProcess")
    public ApiRes addApproveProcess(@RequestBody AddApproveProcessDTO addApproveProcessDTO) {
        return iApproveService.addApproveProcess(addApproveProcessDTO);
    }

    /**
     * 修改审批流
     *
     * @param updateApproveProcessDTO
     * @return
     */
    @ApiOperation("修改审批流")
    @PostMapping("/approve/updateApproveProcess")
    public ApiRes updateApproveProcess(@RequestBody UpdateApproveProcessDTO updateApproveProcessDTO) {
        return iApproveService.updateApproveProcess(updateApproveProcessDTO);
    }

    /**
     * 门店审核查看列表
     *
     * @param pageWrap
     * @return
     */
    @ApiOperation("门店审核查看列表")
    @PostMapping("/approve/showStoreExamine")
    public ApiRes<ShowStoreExamineVO> showStoreExamine(@RequestBody PageWrap<ShowStoreExamineDTO> pageWrap) {
        return iApproveService.showStoreExamine(pageWrap);
    }

    /**
     * 门店审核查看导出
     *
     * @param showStoreExamineDTO
     * @param httpServletResponse
     * @return
     */
    @ApiOperation("门店审核查看导出")
    @GetMapping("/approve/exportShowStoreExamine")
    public void exportShowStoreExamine(ShowStoreExamineDTO showStoreExamineDTO, HttpServletResponse httpServletResponse) {
        iApproveService.exportShowStoreExamine(showStoreExamineDTO, httpServletResponse);
    }

    /**
     * 门店审核管理列表
     *
     * @param pageWrap
     * @return
     */
    @ApiOperation("门店审核管理列表")
    @PostMapping("/approve/listStoreExamine")
    public ApiRes<ShowStoreExamineVO> listStoreExamine(@RequestBody PageWrap<ShowStoreExamineDTO> pageWrap) {
        return iApproveService.listStoreExamine(pageWrap);
    }

    /**
     * 门店审核管理列表导出
     *
     * @param showStoreExamineDTO
     * @param httpServletResponse
     */
    @ApiOperation("门店审核管理列表导出")
    @GetMapping("/approve/exportListStoreExamine")
    public void exportListStoreExamine(ShowStoreExamineDTO showStoreExamineDTO, HttpServletResponse httpServletResponse) {
        iApproveService.exportListStoreExamine(showStoreExamineDTO, httpServletResponse);
    }

    /**
     * 详情
     *
     * @param storeExamineDetailDTO
     * @return
     */
    @ApiOperation("详情")
    @PostMapping("/approve/storeExamineDetail")
    public ApiRes<StoreExamineDetailVO> storeExamineDetail(@RequestBody StoreExamineDetailDTO storeExamineDetailDTO) {
        return iApproveService.storeExamineDetail(storeExamineDetailDTO);
    }

    /**
     * 拒绝
     *
     * @param examineDTO
     * @return
     */
    @ApiOperation("拒绝")
    @PostMapping("/approve/examineRefuse")
    public ApiRes examineRefuse(@RequestBody ExamineDTO examineDTO) {
        return iApproveService.examineRefuse(examineDTO);
    }

    /**
     * 同意
     *
     * @param examineDTO
     * @return
     */
    @ApiOperation("同意")
    @PostMapping("/approve/examineAgree")
    public ApiRes examineAgree(@RequestBody ExamineDTO examineDTO) {
        return iApproveService.examineAgree(examineDTO);
    }
}
