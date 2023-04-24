package com.yunzyq.yudbapp.api.merchant;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.service.MerchantOrderService;
import com.yunzyq.yudbapp.util.WxUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "加盟商缴费平台相关接口")
@Slf4j
@RequestMapping("/merchantOrder")
@RestController
public class MerchantOrderController {


    @Value("${AppID}")
    private String AppID;
    @Value("${AppSecret}")
    private String AppSecret;

    @Resource
    MerchantOrderService merchantOrderService;

    /**
     * * 门店列表
     */
    @ApiOperation("门店列表")
    @PostMapping("/storeList")
    public ApiRes<SoreListVo> storeList(HttpServletRequest httpServletRequest) {
        return merchantOrderService.storeList(httpServletRequest);
    }

    /**
     * 缴费平台列表
     */
    @ApiOperation("缴费平台列表")
    @PostMapping("/merchantPlatformPageVo")
    public ApiRes<MerchantPlatformPageVo> merchantPlatformPageVo(@RequestBody PageWrap<StroeIdDto> pageWrap, HttpServletRequest httpServletRequest) {
        return merchantOrderService.merchantPlatformPageVo(pageWrap, httpServletRequest);
    }

    /**
     * 去支付(返回的支付链接访问打开)
     */
    @ApiOperation("去支付(小程序支付)")
    @PostMapping("/pay")
    public ApiRes<MerchantOrderPayVo> pay(@RequestBody MerchantOrderPayDto merchantOrderPayDto, HttpServletRequest httpServletRequest) {
        return merchantOrderService.pay(merchantOrderPayDto, httpServletRequest);
    }

    @ApiOperation("好友代付(返回的是支付二维码)")
    @PostMapping("/paymentOnBehalf")
    public ApiRes<PaymentOnBehalfVo> paymentOnBehalf(@RequestBody MerchantOrderPayDto merchantOrderPayDto, HttpServletRequest httpServletRequest) {
        return merchantOrderService.paymentOnBehalf(merchantOrderPayDto, httpServletRequest);
    }


    /**
     * 微信授权接口
     *
     * @param jsCode
     * @return
     */
    @ApiOperation("微信授权接口")
    @GetMapping("/auth")
    public ApiRes auth(String jsCode) {
        return WxUtil.getopenId(jsCode, AppID, AppSecret);
    }


    /**
     * 缴费记录
     */
    @ApiOperation("缴费记录")
    @PostMapping("/paymentRecord")
    public ApiRes<PageWrap<PaymentRecordMerchantVo>> paymentRecord(HttpServletRequest httpServletRequest, @RequestBody PageWrap pageWrap) {
        return merchantOrderService.paymentRecord(httpServletRequest, pageWrap);
    }


    /**
     * 缴费详情
     */
    @ApiOperation("缴费详情")
    @PostMapping("/paymentRecordDetail")
    public ApiRes<MerchantPaymentRecordDetailVo> paymentRecordDetail(@RequestBody PlatformPageDetatilDto platformPageDetatilDto) {
        return merchantOrderService.paymentRecordDetail(platformPageDetatilDto);
    }

}
