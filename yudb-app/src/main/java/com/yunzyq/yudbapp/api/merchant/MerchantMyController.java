package com.yunzyq.yudbapp.api.merchant;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.MerchantDaibDto;
import com.yunzyq.yudbapp.dao.dto.RegionalManagerDaibDto;
import com.yunzyq.yudbapp.dao.dto.UpdatePwdDto;
import com.yunzyq.yudbapp.dao.vo.MerchantDaibVo;
import com.yunzyq.yudbapp.dao.vo.MerchantMyVo;
import com.yunzyq.yudbapp.dao.vo.RegionalManagerDaibVo;
import com.yunzyq.yudbapp.dao.vo.StoreListVo;
import com.yunzyq.yudbapp.service.MerchantMyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "加盟商我的页面")
@Slf4j
@RequestMapping("/merchantMy")
@RestController
public class MerchantMyController {

    @Resource
    MerchantMyService merchantMyService;

    /**
     * 我的
     */
    @PostMapping("/my")
    @ApiOperation("我的")
    public ApiRes<MerchantMyVo> my(HttpServletRequest httpServletRequest) {
        return merchantMyService.my(httpServletRequest);
    }


    /**
     * 代办列表
     */
    @PostMapping("/daib")
    @ApiOperation("代办列表")
    public ApiRes<PageWrap<MerchantDaibVo>> daib(HttpServletRequest httpServletRequest, @RequestBody PageWrap<MerchantDaibDto> pageWrap) {
        return merchantMyService.daib(httpServletRequest, pageWrap);
    }

    /**
     * 我的门店列表信息
     */
    @PostMapping("/storeList")
    @ApiOperation("我的门店列表信息")
    public ApiRes<PageWrap<StoreListVo>> storeList(HttpServletRequest httpServletRequest, @RequestBody PageWrap pageWrap) {
        return merchantMyService.storeList(httpServletRequest, pageWrap);
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePwd")
    @ApiOperation("修改密码")
    public ApiRes updatePwd(@RequestBody UpdatePwdDto updatePwdDto, HttpServletRequest httpServletRequest) {
        return merchantMyService.updatePwd(httpServletRequest,updatePwdDto);
    }

    /**
     * 我的代理权
     */
    @PostMapping("/myAgentArea")
    @ApiOperation("我的代理权")
    public ApiRes myAgentArea(HttpServletRequest httpServletRequest) {
        return merchantMyService.myAgentArea(httpServletRequest);
    }

}
