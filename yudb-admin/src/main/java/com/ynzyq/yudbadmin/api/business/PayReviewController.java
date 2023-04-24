package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.PayReviewDetailVo;
import com.ynzyq.yudbadmin.dao.business.vo.PayReviewPageVo;
import com.ynzyq.yudbadmin.dao.business.vo.PopupVo;
import com.ynzyq.yudbadmin.service.business.PayReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "(新)缴费审核")
@Slf4j
@RequestMapping("/payReview")
@RestController
public class PayReviewController extends BaseController {

    @Resource
    PayReviewService payReviewService;

    /**
     * 列表
     */
    @RequiresPermissions("business:payReview:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<PageWrap<PayReviewPageVo>> findPage(@RequestBody PageWrap<PayReviewPageDto> pageWrap) {
        return payReviewService.findPage(pageWrap);
    }


    /**
     * 一审/二审弹窗内容
     */
    @PostMapping("/popup")
    @ApiOperation("一审/二审弹窗内容")
    public ApiRes<PopupVo> popup(@RequestBody PopupDto popupDto) {
        return payReviewService.popup(popupDto);
    }


    /**
     * 一审
     */
    @Log("一审")
    @RequiresPermissions("business:payReview:one")
    @PostMapping("/one")
    @ApiOperation("一审(business:payReview:one)")
    public ApiRes one(@RequestBody PayReviewDto payReviewDto) {
        return payReviewService.one(payReviewDto);
    }


    /**
     * 二审
     */
    @Log("二审")
    @RequiresPermissions("business:payReview:two")
    @PostMapping("/two")
    @ApiOperation("二审(business:payReview:two)")
    public ApiRes two(@RequestBody PayReviewDto payReviewDto) {
        return payReviewService.two(payReviewDto);
    }


    /**
     * 详情
     */
    @RequiresPermissions("business:payReview:detail")
    @PostMapping("/detail")
    @ApiOperation("详情(business:payReview:detail)")
    public ApiRes<PayReviewDetailVo> detail(@RequestBody PayReviewDetailDto payReviewDetailDto) {
        return payReviewService.detail(payReviewDetailDto);
    }

    /**
     * 导出
     *
     * @param payReviewPageDto
     * @return
     */
    @GetMapping("/exportPayReview")
    @ApiOperation("导出")
    public void exportPayReview(PayReviewPageDto payReviewPageDto, HttpServletResponse response) {
        payReviewService.exportPayReview(payReviewPageDto, response);
    }

}
