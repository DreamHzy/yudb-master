package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewDetailDto;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewDto;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.PopupDto;
import com.ynzyq.yudbadmin.dao.business.vo.PayReviewDetailVo;
import com.ynzyq.yudbadmin.dao.business.vo.PayReviewPageVo;
import com.ynzyq.yudbadmin.dao.business.vo.PopupVo;
import com.ynzyq.yudbadmin.service.business.AgentPayReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "(新)代理缴费审核")
@Slf4j
@RequestMapping("/agentPayReview")
@RestController
public class AgentPayReviewController {

    @Resource
    AgentPayReviewService agentPayReviewService;

    @RequiresPermissions("business:agentPayReview:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<PageWrap<PayReviewPageVo>> findPage(@RequestBody PageWrap<PayReviewPageDto> pageWrap) {
        return agentPayReviewService.findPage(pageWrap);
    }


    /**
     * 一审/二审弹窗内容
     */
    @PostMapping("/popup")
    @ApiOperation("一审/二审弹窗内容")
    public ApiRes<PopupVo> popup(@RequestBody PopupDto popupDto) {
        return agentPayReviewService.popup(popupDto);
    }


    /**
     * 一审
     */
    @Log("一审")
    @RequiresPermissions("business:agentPayReview:one")
    @PostMapping("/one")
    @ApiOperation("一审(business:agentPayReview:one)")
    public ApiRes one(@RequestBody PayReviewDto payReviewDto) {
        return agentPayReviewService.one(payReviewDto);
    }


    /**
     * 二审
     */
    @Log("二审")
    @RequiresPermissions("business:agentPayReview:two")
    @PostMapping("/two")
    @ApiOperation("二审(business:agentPayReview:two)")
    public ApiRes two(@RequestBody PayReviewDto payReviewDto) {
        return agentPayReviewService.two(payReviewDto);
    }


    /**
     * 详情
     */
    @RequiresPermissions("business:agentPayReview:detail")
    @PostMapping("/detail")
    @ApiOperation("详情(business:agentPayReview:detail)")
    public ApiRes<PayReviewDetailVo> detail(@RequestBody PayReviewDetailDto payReviewDetailDto) {
        return agentPayReviewService.detail(payReviewDetailDto);
    }

}
