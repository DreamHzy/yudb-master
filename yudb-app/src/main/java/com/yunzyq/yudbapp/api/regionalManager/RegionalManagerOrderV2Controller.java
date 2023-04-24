package com.yunzyq.yudbapp.api.regionalManager;


import com.alibaba.fastjson.JSON;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.IRegionalManagerOrderV2Service;
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

@Api(tags = "V2区域经理缴费平台代理相关接口")
@Slf4j
@RequestMapping("/regionalManagerOrderV2")
@RestController
public class RegionalManagerOrderV2Controller {

    private static final String LOGGER_PREFIX = "【区域经理缴费平台】";

    @Resource
    IRegionalManagerOrderV2Service IRegionalManagerOrderV2Service;

    @Resource
    RedisUtils redisUtils;

    /**
     * 代理缴费列表
     *
     * @param httpServletRequest
     * @param platformPageDTO
     * @return
     */
    @PostMapping("/agentPlatformPageV2")
    @ApiOperation("代理缴费列表")
    public ApiRes<PageWrap<RegionalManagerAgentPlatformPageVO>> agentPlatformPageV2(HttpServletRequest httpServletRequest, @RequestBody @Valid PlatformPageDTO platformPageDTO) {
        log.info("{}代理缴费列表请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(platformPageDTO));
        String managerId = redisUtils.token(httpServletRequest);
        return IRegionalManagerOrderV2Service.agentPlatformPageV2(managerId, platformPageDTO);
    }

    /**
     * 查看审批单
     *
     * @param auditRecordDTO
     * @return
     */
    @PostMapping("/examineFile")
    @ApiOperation("查看审批单")
    public ApiRes<ExamineFileVO> examineFile(@RequestBody @Valid AuditRecordDTO auditRecordDTO) {
        return IRegionalManagerOrderV2Service.examineFile(auditRecordDTO);
    }

    /**
     * 审核记录
     *
     * @param auditRecordDTO
     * @return
     */
    @PostMapping("/auditRecord")
    @ApiOperation("审核记录")
    public ApiRes<AuditRecordVO> auditRecord(@RequestBody @Valid AuditRecordDTO auditRecordDTO) {
        return IRegionalManagerOrderV2Service.auditRecord(auditRecordDTO);
    }

    /**
     * 代理申请调整
     *
     * @param httpServletRequest
     * @param applyForAdjustmentDto
     * @return
     */
    @PostMapping("/agentApplyForAdjustment")
    @ApiOperation("代理申请调整")
    public ApiRes agentApplyForAdjustment(HttpServletRequest httpServletRequest, @RequestBody ApplyForAdjustmentDto applyForAdjustmentDto) {
        log.info("{}代理申请调整请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(applyForAdjustmentDto));
        String managerId = redisUtils.token(httpServletRequest);
        return IRegionalManagerOrderV2Service.agentApplyForAdjustment(managerId, applyForAdjustmentDto);
    }


    /**
     * 撤回
     */
    @PostMapping("/agentWithdrawal")
    @ApiOperation("撤回")
    public ApiRes agentWithdrawal(HttpServletRequest httpServletRequest, @RequestBody PlatformPageDetatilDto pageDetatilDto) {
        return IRegionalManagerOrderV2Service.agentWithdrawal(httpServletRequest, pageDetatilDto);
    }

    /**
     * 生成支付码
     */
    @PostMapping("/agentPaymentCode")
    @ApiOperation("生成支付码")
    public ApiRes agentPaymentCode(HttpServletRequest httpServletRequest, @RequestBody PlatformPageDetatilDto pageDetailDto) {
        String ip = IpUtil.getIpAddr(httpServletRequest);
        return IRegionalManagerOrderV2Service.agentPaymentCode(ip, pageDetailDto);
    }


    /**
     * 推送缴费订单
     */
    @ApiOperation("推送缴费订单")
    @PostMapping("/agentSend")
    public ApiRes agentSend(@RequestBody PlatformPageDetatilDto platformPageDetatilDto) {
        return IRegionalManagerOrderV2Service.agentSend(platformPageDetatilDto);
    }

    /**
     * 推送撤回
     */
    @ApiOperation("推送撤回")
    @PostMapping("/agentSendBack")
    public ApiRes agentSendBack(@RequestBody PlatformPageDetatilDto platformPageDetatilDto) {
        return IRegionalManagerOrderV2Service.agentSendBack(platformPageDetatilDto);
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
    public ApiRes<RegionalManagerPlatformPageVo> agentMyBacklog(@RequestBody @Valid AgentMyBacklogDTO agentMyBacklogDTO, HttpServletRequest httpServletRequest) {
        log.info("{}我的待办请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(agentMyBacklogDTO));
        String managerId = redisUtils.token(httpServletRequest);
        return IRegionalManagerOrderV2Service.regionalMyBacklog(managerId, agentMyBacklogDTO);
    }

    /**
     * 未来待办
     *
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("未来待办")
    @PostMapping("/futureBacklog")
    public ApiRes<FutureBacklogVO> futureBacklog(HttpServletRequest httpServletRequest) {
        String managerId = redisUtils.token(httpServletRequest);
        return IRegionalManagerOrderV2Service.futureBacklog(Integer.parseInt(managerId));
    }

    /**
     * 未来待办详情列表
     *
     * @param futureBacklogDetailDTO
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("未来待办详情列表")
    @PostMapping("/futureBacklogDetail")
    public ApiRes<FutureBacklogDetailVO> futureBacklogDetail(@RequestBody @Valid FutureBacklogDetailDTO futureBacklogDetailDTO, HttpServletRequest httpServletRequest) {
        String managerId = redisUtils.token(httpServletRequest);
        return IRegionalManagerOrderV2Service.futureBacklogDetail(Integer.parseInt(managerId), futureBacklogDetailDTO);
    }

    /**
     * 区域经理缴费记录
     */
    @ApiOperation("区域经理缴费记录")
    @PostMapping("/agentPaymentRecord")
    public ApiRes<PageWrap<PaymentRecordMerchantVo>> agentPaymentRecord(@RequestBody PageWrap pageWrap, HttpServletRequest httpServletRequest) {
        log.info("{}代理去支付请求参数：{}", LOGGER_PREFIX, pageWrap);
        String managerId = redisUtils.token(httpServletRequest);
        return IRegionalManagerOrderV2Service.agentPaymentRecord(managerId, pageWrap.getPage(), pageWrap.getCapacity());
    }

    /**
     * 是否显示二维码
     */
    @ApiOperation("是否显示二维码")
    @PostMapping("/isShowQrcode")
    public ApiRes<IsShowQrcodeVO> isShowQrcode() {
        return IRegionalManagerOrderV2Service.isShowQrcode();
    }
}
