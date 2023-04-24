package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.AgentMyBacklogDTO;
import com.yunzyq.yudbapp.dao.dto.AgentOrderPayDTO;
import com.yunzyq.yudbapp.dao.dto.AgentOrderPaySeparatelyDTO;
import com.yunzyq.yudbapp.dao.dto.PlatformPageDetatilDto;
import com.yunzyq.yudbapp.dao.vo.*;

/**
 * @Author wj
 * @Date 2021/10/25
 */
public interface IMerchantOrderV2Service {

    /**
     * 代理列表
     *
     * @param merchantId 商户号
     * @param page       页码
     * @param capacity   每页显示行数
     * @return
     */
    ApiRes<MerchantPlatformAgentVO> merchantPlatformAgentPageVo(String merchantId, int page, int capacity);

    /**
     * 代理好友代付
     *
     * @param agentOrderPayDto 请求参数
     * @param ip               IP地址
     * @return
     */
    ApiRes<PaymentOnBehalfVo> paymentOnAgentBehalf(AgentOrderPayDTO agentOrderPayDto, String ip);

    /**
     * 代理去支付
     *
     * @param agentOrderPayDto 请求参数
     * @param ip               ip地址
     * @return
     */
    ApiRes<MerchantOrderPayVo> agentPay(AgentOrderPayDTO agentOrderPayDto, String ip);

    /**
     * 拆单支付支付
     *
     * @param agentOrderPaySeparatelyDTO 请求参数
     * @param ip               ip地址
     * @return
     */
    ApiRes<MerchantOrderPayVo> agentPaySeparately(AgentOrderPaySeparatelyDTO agentOrderPaySeparatelyDTO, String ip);

    /**
     * 代理缴费记录
     *
     * @param merchantId 商户号
     * @param page       页码
     * @param capacity   每页显示行数
     * @return
     */
    ApiRes<PageWrap<PaymentRecordMerchantVo>> agentPaymentRecord(String merchantId, int page, int capacity);

    /**
     * 我的待办列表
     *
     * @param merchantId
     * @param agentMyBacklogDTO
     * @return
     */
    ApiRes<PageWrap<MerchantPlatformPageVo>> agentMyBacklog(String merchantId, AgentMyBacklogDTO agentMyBacklogDTO);

    /**
     * 订单详情
     *
     * @param platformPageDetatilDto
     * @return
     */
    ApiRes<MerchantPaymentRecordDetailVo> paymentRecordDetail(PlatformPageDetatilDto platformPageDetatilDto);
}
