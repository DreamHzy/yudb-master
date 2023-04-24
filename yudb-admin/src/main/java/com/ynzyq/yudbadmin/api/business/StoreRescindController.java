package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDetailDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPopupDto;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.StoreRescindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "(新)门店解约审核")
@Slf4j
@RequestMapping("/storeRescind")
@RestController
public class StoreRescindController {


    @Resource
    StoreRescindService storeRescindService;

    /**
     * 列表
     */
    @RequiresPermissions("business:storeRescind:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<PageWrap<StoreRescindPageVo>> findPage(@RequestBody PageWrap<StoreReviewPageDto> pageWrap) {
        return storeRescindService.findPage(pageWrap);
    }

    /**
     * 详情
     */
    @RequiresPermissions("business:storeRescind:detail")
    @PostMapping("/detail")
    @ApiOperation("详情(business:storeRescind:detail)")
    public ApiRes<StoreRescindDetailVo> detail(@RequestBody StoreReviewDetailDto storeReviewDetailDto) {
        return storeRescindService.detail(storeReviewDetailDto);
    }

    /**
     * 一审
     */
    @RequiresPermissions("business:storeRescind:one")
    @PostMapping("/one")
    @ApiOperation("一审(business:storeRescind:one)")
    public ApiRes one(@RequestBody StoreReviewDto storeReviewDto) {
        return storeRescindService.one(storeReviewDto);
    }


    /**
     * 二审弹窗内容
     */
    @PostMapping("/popup")
    @ApiOperation("二审弹窗内容")
    public ApiRes<StoreReviewPopupVo> popup(@RequestBody StoreReviewPopupDto storeReviewPopupDto) {
        return storeRescindService.popup(storeReviewPopupDto);
    }


    /**
     * 二审
     */
    @RequiresPermissions("business:storeRescind:two")
    @PostMapping("/two")
    @ApiOperation("二审(business:storeRescind:two)")
    public ApiRes two(@RequestBody StoreReviewDto storeReviewDto) {
        return storeRescindService.two(storeReviewDto);
    }

}
