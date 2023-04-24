package com.yunzyq.yudbapp.api.merchant;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.AgentMyBacklogDTO;
import com.yunzyq.yudbapp.dao.dto.AgentOrderPayDTO;
import com.yunzyq.yudbapp.dao.dto.PlatformPageDetatilDto;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.IMerchantOrderV2Service;
import com.yunzyq.yudbapp.util.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author WJ
 */
@Api(tags = "V2加盟商缴费平台相关接口")
@Slf4j
@RequestMapping("/merchantOrderV2")
@RestController
public class MerchantOrderV2Controller {

    private static final String LOGGER_PREFIX = "【加盟商缴费平台】";

    @Resource
    IMerchantOrderV2Service iMerchantOrderV2Service;

    @Resource
    RedisUtils redisUtils;

    /**
     * 缴费平台列表
     */
    @ApiOperation("缴费平台代理列表")
    @PostMapping("/merchantPlatformAgentPageVo")
    public ApiRes<MerchantPlatformAgentVO> merchantPlatformAgentPageVo(@RequestBody PageWrap pageWrap, HttpServletRequest httpServletRequest) {
        log.info("{}缴费平台代理列表请求参数：{}", LOGGER_PREFIX, pageWrap);
        String merchantId = redisUtils.token(httpServletRequest);
        return iMerchantOrderV2Service.merchantPlatformAgentPageVo(merchantId, pageWrap.getPage(), pageWrap.getCapacity());
    }

    /**
     * 代理好友代付
     *
     * @param agentOrderPayDto
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("代理好友代付")
    @PostMapping("/paymentOnAgentBehalf")
    public ApiRes<PaymentOnBehalfVo> paymentOnAgentBehalf(@RequestBody @Valid AgentOrderPayDTO agentOrderPayDto, HttpServletRequest httpServletRequest) {
        log.info("{}代理列表请求参数：{}", LOGGER_PREFIX, agentOrderPayDto);
        String ip = IpUtil.getIpAddr(httpServletRequest);
        return iMerchantOrderV2Service.paymentOnAgentBehalf(agentOrderPayDto, ip);
    }

    /**
     * 代理去支付(返回的支付链接访问打开)
     */
    @ApiOperation("代理去支付(小程序支付)")
    @PostMapping("/agentPay")
    public ApiRes<MerchantOrderPayVo> agentPay(@RequestBody AgentOrderPayDTO agentOrderPayDto, HttpServletRequest httpServletRequest) {
        log.info("{}代理去支付请求参数：{}", LOGGER_PREFIX, agentOrderPayDto);
        String ip = IpUtil.getIpAddr(httpServletRequest);
        return iMerchantOrderV2Service.agentPay(agentOrderPayDto, ip);
    }

    /**
     * 代理缴费记录
     */
    @ApiOperation("代理缴费记录")
    @PostMapping("/agentPaymentRecord")
    public ApiRes<PageWrap<PaymentRecordMerchantVo>> agentPaymentRecord(@RequestBody PageWrap pageWrap, HttpServletRequest httpServletRequest) {
        log.info("{}代理去支付请求参数：{}", LOGGER_PREFIX, pageWrap);
        String merchantId = redisUtils.token(httpServletRequest);
        return iMerchantOrderV2Service.agentPaymentRecord(merchantId, pageWrap.getPage(), pageWrap.getCapacity());
    }

    /**
     * 我的待办列表
     *
     * @param agentMyBacklogDTO
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("我的待办")
    @PostMapping("/agentMyBacklog")
    public ApiRes<PageWrap<MerchantPlatformPageVo>> agentMyBacklog(@RequestBody @Valid AgentMyBacklogDTO agentMyBacklogDTO, HttpServletRequest httpServletRequest) {
        log.info("{}我的待办请求参数：{}", LOGGER_PREFIX, agentMyBacklogDTO);
        String merchantId = redisUtils.token(httpServletRequest);
        return iMerchantOrderV2Service.agentMyBacklog(merchantId, agentMyBacklogDTO);
    }

    /**
     * 缴费详情
     *
     * @param platformPageDetatilDto
     * @return
     */
    @ApiOperation("缴费详情")
    @PostMapping("/agentPaymentRecordDetail")
    public ApiRes<MerchantPaymentRecordDetailVo> agentPaymentRecordDetail(@RequestBody PlatformPageDetatilDto platformPageDetatilDto) {
        return iMerchantOrderV2Service.paymentRecordDetail(platformPageDetatilDto);
    }

}
