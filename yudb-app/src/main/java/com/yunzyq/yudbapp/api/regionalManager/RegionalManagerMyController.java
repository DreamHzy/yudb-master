package com.yunzyq.yudbapp.api.regionalManager;


import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.RegionalManagerDaibDto;
import com.yunzyq.yudbapp.dao.dto.SubmitForReviewDto;
import com.yunzyq.yudbapp.dao.dto.UpdatePwdDto;
import com.yunzyq.yudbapp.dao.vo.RegionalManageMyVo;
import com.yunzyq.yudbapp.dao.vo.RegionalManagerDaibVo;
import com.yunzyq.yudbapp.service.RegionalManagerMyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "区域经理我的页面")
@Slf4j
@RequestMapping("/regionalManagerMy")
@RestController
public class RegionalManagerMyController {

    @Resource
    RegionalManagerMyService regionalManagerMyService;

    /**
     * 我的
     */
    @PostMapping("/my")
    @ApiOperation("我的")
    public ApiRes<RegionalManageMyVo> my(HttpServletRequest httpServletRequest) {
        return regionalManagerMyService.my(httpServletRequest);
    }


    /**
     * 代办列表
     */
    @PostMapping("/daib")
    @ApiOperation("代办列表")
    public ApiRes<PageWrap<RegionalManagerDaibVo>> daib(HttpServletRequest httpServletRequest, @RequestBody PageWrap<RegionalManagerDaibDto> pageWrap) {
        return regionalManagerMyService.daib(httpServletRequest, pageWrap);
    }


    /**
     * 修改密码
     */
    @PostMapping("/updatePwd")
    @ApiOperation("修改密码")
    public ApiRes updatePwd(@RequestBody UpdatePwdDto updatePwdDto, HttpServletRequest httpServletRequest) {
        return regionalManagerMyService.updatePwd(httpServletRequest,updatePwdDto);
    }

}
