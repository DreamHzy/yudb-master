package com.yunzyq.yudbapp.api.regionalManager;


import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.vo.HistoryOrderVo;
import com.yunzyq.yudbapp.dao.vo.PayTypeListVo;
import com.yunzyq.yudbapp.dao.vo.StorePage2Vo;
import com.yunzyq.yudbapp.dao.vo.StroePageVo;
import com.yunzyq.yudbapp.service.RegionalManagerMerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "区域经理-加盟商管理")
@Slf4j
@RequestMapping("/regionalManagerOrder")
@RestController
public class RegionalManagerMerchantController {


    @Resource
    RegionalManagerMerchantService regionalManagerMerchantService;


    /**
     * 列表
     */
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<PageWrap<StroePageVo>> findPage(@RequestBody PageWrap<StroePageDto> pageWrap, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.findPage(pageWrap, httpServletRequest);
    }

    /**
     * 我的门店列表
     */
    @PostMapping("/page2")
    @ApiOperation("列表")
    public ApiRes<StorePage2Vo> findPage2(@RequestBody PageWrap<StroePage2Dto> pageWrap, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.findPage2(pageWrap, httpServletRequest);
    }


    /**
     * 缴费类型列表
     */
    @PostMapping("/payTypeList")
    @ApiOperation("缴费类型列表")
    public ApiRes<PayTypeListVo> payTypeList() {
        return regionalManagerMerchantService.payTypeList();
    }


    /**
     * 缴费提交审核
     */
    @PostMapping("/submitForReview")
    @ApiOperation("缴费提交审核")
    public ApiRes submitForReview(@RequestBody SubmitForReviewDto submitForReviewDto, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.submitForReview(submitForReviewDto, httpServletRequest);
    }


    /**
     * 历史开单
     */
    @PostMapping("/historyOrder")
    @ApiOperation("历史开单")
    public ApiRes<PageWrap<HistoryOrderVo>> historyOrder(HttpServletRequest httpServletRequest, @RequestBody PageWrap<HistoryOrderDto> pageWrap) {
        return regionalManagerMerchantService.historyOrder(httpServletRequest, pageWrap);
    }

    /**
     * 确认续约 confirmRenewal
     */
    @PostMapping("/confirmRenewal")
    @ApiOperation("确认续约")
    public ApiRes confirmRenewal(@RequestBody ConfirmRenewalDto confirmRenewalDto, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.confirmRenewal(confirmRenewalDto, httpServletRequest);
    }

    /**
     * 续约撤回
     */
    @PostMapping("/renewalWithdrawal")
    @ApiOperation("续约撤回")
    public ApiRes renewalWithdrawal(@RequestBody RenewalWithdrawalDto renewalWithdrawalDto, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.renewalWithdrawal(renewalWithdrawalDto, httpServletRequest);
    }

    /**
     * 合同签署
     */
    @PostMapping("/signAContract")
    @ApiOperation("合同签署")
    public ApiRes signAContract(@RequestBody SignAContractDto signAContractDto, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.signAContract(signAContractDto, httpServletRequest);
    }

    /**
     * 合同撤回
     */
    @PostMapping("/withdrawalOfContract")
    @ApiOperation("合同撤回")
    public ApiRes withdrawalOfContract(@RequestBody WithdrawalOfContractDto withdrawalOfContractDto, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.withdrawalOfContract(withdrawalOfContractDto, httpServletRequest);
    }


    /**
     * 解约
     */
    @PostMapping("/rescind")
    @ApiOperation("解约")
    public ApiRes rescind(@RequestBody RescindDto rescindDto, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.rescind(rescindDto, httpServletRequest);
    }

    /**
     * 撤回解约
     */
    @PostMapping("/terminationWithdrawal")
    @ApiOperation("撤回解约")
    public ApiRes terminationWithdrawal(@RequestBody TerminationWithdrawalDto terminationWithdrawalDto, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.terminationWithdrawal(terminationWithdrawalDto, httpServletRequest);
    }


    /**
     * 重新续约 againConfirmRenewal
     */
    @PostMapping("/againConfirmRenewal")
    @ApiOperation("重新续约")
    public ApiRes againConfirmRenewal(@RequestBody AgainConfirmRenewalDto againConfirmRenewalDto, HttpServletRequest httpServletRequest) {
        return regionalManagerMerchantService.againConfirmRenewal(againConfirmRenewalDto, httpServletRequest);
    }

}
