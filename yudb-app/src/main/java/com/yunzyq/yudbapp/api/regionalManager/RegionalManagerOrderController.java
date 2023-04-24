package com.yunzyq.yudbapp.api.regionalManager;


import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.ApplyForAdjustmentDto;
import com.yunzyq.yudbapp.dao.dto.AuditRecordDTO;
import com.yunzyq.yudbapp.dao.dto.PlatformPageDTO;
import com.yunzyq.yudbapp.dao.dto.PlatformPageDetatilDto;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.service.RegionalManagerOrderService;
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

@Api(tags = "区域经理缴费平台相关接口")
@Slf4j
@RequestMapping("/regionalManagerOrder")
@RestController
public class RegionalManagerOrderController {

    @Resource
    RegionalManagerOrderService regionalManagerOrderService;

    /**
     * 缴费平台列表
     */
    @PostMapping("/platformPage")
    @ApiOperation("缴费平台列表")
    public ApiRes<PageWrap<RegionalManagerPlatformPageVo>> findPage(HttpServletRequest httpServletRequest,
                                                                    @RequestBody @Valid PlatformPageDTO platformPageDTO) {
        return regionalManagerOrderService.findPage(httpServletRequest, platformPageDTO);
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
        return regionalManagerOrderService.examineFile(auditRecordDTO);
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
        return regionalManagerOrderService.auditRecord(auditRecordDTO);
    }

    @PostMapping("/platformPageNoSh")
    @ApiOperation("缴费平台列表")
    public ApiRes<PageWrap<RegionalManagerPlatformPageVo>> platformPageNoSh(HttpServletRequest httpServletRequest,
                                                                            @RequestBody PageWrap pageWrap) {
        return regionalManagerOrderService.platformPageNoSh(httpServletRequest, pageWrap);
    }


    @PostMapping("/platformRecordPage")
    @ApiOperation("缴费记录列表")
    public ApiRes<PageWrap<PlatformRecordPageVo>> platformRecordPage(HttpServletRequest httpServletRequest,
                                                                     @RequestBody PageWrap pageWrap) {
        return regionalManagerOrderService.platformRecordPage(httpServletRequest, pageWrap);
    }


    /**
     * 缴费详情
     */
    @PostMapping("/deatil")
    @ApiOperation("缴费详情")
    public ApiRes<PlatformPageDetatilVo> deatil(HttpServletRequest httpServletRequest,
                                                @RequestBody PlatformPageDetatilDto pageDetatilDto) {
        return regionalManagerOrderService.deatil(httpServletRequest, pageDetatilDto);
    }

    /**
     * 申请调整
     */
    @PostMapping("/applyForAdjustment")
    @ApiOperation("申请调整")
    public ApiRes applyForAdjustment(HttpServletRequest httpServletRequest,
                                     @RequestBody ApplyForAdjustmentDto applyForAdjustmentDto) {
        return regionalManagerOrderService.applyForAdjustment(httpServletRequest, applyForAdjustmentDto);
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation("删除")
    public ApiRes delete(HttpServletRequest httpServletRequest,
                         @RequestBody PlatformPageDetatilDto pageDetatilDto) {
        return regionalManagerOrderService.delete(httpServletRequest, pageDetatilDto);
    }

    /**
     * 撤回
     */
    @PostMapping("/withdrawal")
    @ApiOperation("撤回")
    public ApiRes withdrawal(HttpServletRequest httpServletRequest,
                             @RequestBody PlatformPageDetatilDto pageDetatilDto) {
        return regionalManagerOrderService.withdrawal(httpServletRequest, pageDetatilDto);
    }

    /**
     * 生成支付码
     */
    @PostMapping("/paymentCode")
    @ApiOperation("生成支付码")
    public ApiRes paymentCode(HttpServletRequest httpServletRequest,
                              @RequestBody PlatformPageDetatilDto pageDetatilDto) {
        return regionalManagerOrderService.paymentCode(httpServletRequest, pageDetatilDto);
    }


    /**
     * 推送
     */
    @ApiOperation("推送")
    @PostMapping("/send")
    public ApiRes send(@RequestBody PlatformPageDetatilDto platformPageDetatilDto) {
        return regionalManagerOrderService.send(platformPageDetatilDto);
    }

    /**
     * 推送撤回
     */
    @ApiOperation("推送撤回")
    @PostMapping("/sendBack")
    public ApiRes sendBack(@RequestBody PlatformPageDetatilDto platformPageDetatilDto) {
        return regionalManagerOrderService.sendBack(platformPageDetatilDto);
    }


}
