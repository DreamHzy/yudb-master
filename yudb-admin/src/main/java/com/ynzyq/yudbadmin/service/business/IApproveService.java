package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xinchen
 * @date 2021/12/24 15:09
 * @description:
 */
public interface IApproveService {

    /**
     * 审批流列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ListApproveProcessVO> listApproveProcess(PageWrap<ListApproveProcessDTO> pageWrap);

    /**
     * 调整前查看
     *
     * @param approveProcessDetailDTO
     * @return
     */
    ApiRes<ApproveProcessDetailVO> approveProcessDetail(ApproveProcessDetailDTO approveProcessDetailDTO);

    /**
     * 审批人/会签人下拉框
     *
     * @return
     */
    ApiRes<SystemUserVO> userSelectBox();

    /**
     * 审核类型下拉框
     *
     * @return
     */
    ApiRes<SystemUserVO> examineTypeSelectBox();

    /**
     * 添加审批流
     *
     * @param addApproveProcessDTO
     * @return
     */
    ApiRes addApproveProcess(AddApproveProcessDTO addApproveProcessDTO);

    /**
     * 审批流调整
     *
     * @param updateApproveProcessDTO
     * @return
     */
    ApiRes updateApproveProcess(UpdateApproveProcessDTO updateApproveProcessDTO);

    /**
     * 门店审核查看列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ShowStoreExamineVO> showStoreExamine(PageWrap<ShowStoreExamineDTO> pageWrap);

    /**
     * 门店审核查看导出
     *
     * @param showStoreExamineDTO
     * @param httpServletResponse
     */
    void exportShowStoreExamine(ShowStoreExamineDTO showStoreExamineDTO, HttpServletResponse httpServletResponse);

    /**
     * 门店审核管理列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ShowStoreExamineVO> listStoreExamine(PageWrap<ShowStoreExamineDTO> pageWrap);

    /**
     * 门店审核管理列表导出
     *
     * @param showStoreExamineDTO
     * @param httpServletResponse
     */
    void exportListStoreExamine(ShowStoreExamineDTO showStoreExamineDTO, HttpServletResponse httpServletResponse);

    /**
     * 详情
     *
     * @param storeExamineDetailDTO
     * @return
     */
    ApiRes<StoreExamineDetailVO> storeExamineDetail(StoreExamineDetailDTO storeExamineDetailDTO);

    /**
     * 拒绝
     *
     * @param examineDTO
     * @return
     */
    ApiRes examineRefuse(ExamineDTO examineDTO);

    /**
     * 同意
     *
     * @param examineDTO
     * @return
     */
    ApiRes examineAgree(ExamineDTO examineDTO);
}
