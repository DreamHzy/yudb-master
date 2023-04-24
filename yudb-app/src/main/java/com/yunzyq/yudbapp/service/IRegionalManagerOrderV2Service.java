package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.vo.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WJ
 */
public interface IRegionalManagerOrderV2Service {
    /**
     * 缴费平台代理缴费列表
     *
     * @param managerId
     * @param platformPageDTO
     * @return
     */
    ApiRes<PageWrap<RegionalManagerAgentPlatformPageVO>> agentPlatformPageV2(String managerId, PlatformPageDTO platformPageDTO);

    /**
     * 查看审批单
     *
     * @param auditRecordDTO
     * @return
     */
    ApiRes<ExamineFileVO> examineFile(AuditRecordDTO auditRecordDTO);

    /**
     * 审核记录
     *
     * @param auditRecordDTO
     * @return
     */
    ApiRes<AuditRecordVO> auditRecord(AuditRecordDTO auditRecordDTO);

    /**
     * 代理申请调整
     *
     * @param managerId
     * @param pageDetailDto
     * @return
     */
    ApiRes agentApplyForAdjustment(String managerId, ApplyForAdjustmentDto pageDetailDto);

    /**
     * 撤回
     *
     * @param httpServletRequest
     * @param pageDetatilDto
     * @return
     */
    ApiRes agentWithdrawal(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto);

    /**
     * 生成支付码
     *
     * @param ip
     * @param pageDetailDto
     * @return
     */
    ApiRes agentPaymentCode(String ip, PlatformPageDetatilDto pageDetailDto);

    /**
     * 推送缴费订单
     *
     * @param platformPageDetatilDto
     * @return
     */
    ApiRes agentSend(PlatformPageDetatilDto platformPageDetatilDto);

    /**
     * 推送撤回
     *
     * @param platformPageDetatilDto
     * @return
     */
    ApiRes agentSendBack(PlatformPageDetatilDto platformPageDetatilDto);

    /**
     * 我的待办
     *
     * @param managerId
     * @param agentMyBacklogDTO
     * @return
     */
    ApiRes<RegionalManagerPlatformPageVo> regionalMyBacklog(String managerId, AgentMyBacklogDTO agentMyBacklogDTO);

    /**
     * 未来待办
     *
     * @param managerId
     * @return
     */
    ApiRes<FutureBacklogVO> futureBacklog(Integer managerId);

    /**
     * 未来待办详情列表
     *
     * @param managerId
     * @param futureBacklogDetailDTO
     * @return
     */
    ApiRes<FutureBacklogDetailVO> futureBacklogDetail(Integer managerId, FutureBacklogDetailDTO futureBacklogDetailDTO);

    /**
     * 代理缴费记录
     *
     * @param managerId 商户号
     * @param page      页码
     * @param capacity  每页显示行数
     * @return
     */
    ApiRes<PageWrap<PaymentRecordMerchantVo>> agentPaymentRecord(String managerId, int page, int capacity);

    /**
     * 是否显示二维码
     *
     * @return
     */
    ApiRes isShowQrcode();
}
