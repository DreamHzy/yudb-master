package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.StoreReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "(新)门店合同审核")
@Slf4j
@RequestMapping("/storeReview")
@RestController
public class StoreReviewController {


    @Resource
    StoreReviewService storeReviewService;

    /**
     * 列表
     */
    @RequiresPermissions("business:storeReview:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<PageWrap<StoreReviewPageVo>> findPage(@RequestBody PageWrap<StoreReviewPageDto> pageWrap) {
        return storeReviewService.findPage(pageWrap);
    }

    /**
     * 详情
     */
    @RequiresPermissions("business:storeReview:detail")
    @PostMapping("/detail")
    @ApiOperation("详情(business:storeReview:detail)")
    public ApiRes<StoreReviewDetailVo> detail(@RequestBody StoreReviewDetailDto storeReviewDetailDto) {
        return storeReviewService.detail(storeReviewDetailDto);
    }


    /**
     * 一审
     */
    @RequiresPermissions("business:storeReview:one")
    @PostMapping("/one")
    @ApiOperation("一审(business:storeReview:one)")
    public ApiRes one(@RequestBody StoreReviewDto storeReviewDto) {
        return storeReviewService.one(storeReviewDto);
    }


    /**
     * 二审弹窗内容
     */
    @PostMapping("/popup")
    @ApiOperation("二审弹窗内容")
    public ApiRes<StoreReviewPopupVo> popup(@RequestBody StoreReviewPopupDto storeReviewPopupDto) {
        return storeReviewService.popup(storeReviewPopupDto);
    }


    /**
     * 二审
     */
    @RequiresPermissions("business:storeReview:two")
    @PostMapping("/two")
    @ApiOperation("二审(business:storeReview:two)")
    public ApiRes two(@RequestBody StoreReviewDto storeReviewDto) {
        return storeReviewService.two(storeReviewDto);
    }

}
