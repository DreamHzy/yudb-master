package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author wj
 * @Date 2021/10/26
 */
public interface IAgentService {
    /**
     * 代理管理列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ListAgentVO> listAgent(PageWrap<ListAgentDTO> pageWrap);

    /**
     * 代理详情
     *
     * @param singleAgentDetailDTO
     * @return
     */
    ApiRes<SingleAgentDetailVO> singleAgentDetail(SingleAgentDetailDTO singleAgentDetailDTO);

    /**
     * 编辑区域经理
     *
     * @param editRegionalDTO
     * @return
     */
    ApiRes editRegional(EditRegionalDTO editRegionalDTO);

    /**
     * 调整管理费标准
     *
     * @param editManagementExpenseDTO
     * @param userId
     * @return
     */
    ApiRes editManagementExpense(EditManagementExpenseDTO editManagementExpenseDTO, Integer userId);

    ApiRes excelSubmitForReview(MultipartFile file, HttpServletRequest httpServletRequest);

    ApiRes<PayTypeListVo> payTypeList();

    ApiRes submitForReview(SubmitForReviewDto submitForReviewDto, HttpServletRequest httpServletRequest);

    ApiRes addAgent(AddAgentDto addAgentDto);

    /**
     * 导出代理权信息
     *
     * @param listAgentDTO
     * @param response
     */
    void exportAgent(ListAgentDTO listAgentDTO, HttpServletResponse response);

    /**
     * 编辑前查询
     *
     * @param agentIdDTO
     * @return
     */
    ApiRes<SingleAgentVO> singleAgent(AgentIdDTO agentIdDTO);

    /**
     * 编辑代理权
     *
     * @param editAgentDTO
     * @return
     */
    ApiRes editAgent(EditAgentDTO editAgentDTO);

    /**
     * 修改代理权是否生效
     *
     * @param editEffectDTO
     * @return
     */
    ApiRes editEffect(EditEffectDTO editEffectDTO);

    /**
     * 编辑收款账户
     *
     * @param merchantIdDTO
     * @return
     */
    ApiRes editReceivingAccount(MerchantIdDTO merchantIdDTO);

    /**
     * 修改订货配送信息
     */
    ApiRes modifyDataService(ModifyDataDTO modifyDataDTO);


    /**
     * 代理体系下拉框
     *
     * @return
     */
    ApiRes<OneLevelStatusVO> systemFactorSelectBox();

    /**
     * 修改代理体系
     *
     * @param editSystemFactorDTO
     * @return
     */
    ApiRes editSystemFactor(EditSystemFactorDTO editSystemFactorDTO);
}
