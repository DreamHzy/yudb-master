package com.ynzyq.yudbadmin.api.business;


import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDetailDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPopupDto;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.StoreRenewalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "(新)门店续约意向审核")
@Slf4j
@RequestMapping("/storeRenewal")
@RestController
public class StoreRenewalCotroller {


    @Resource
    StoreRenewalService storeRenewalService;


    /**
     * 列表
     */
    @RequiresPermissions("business:storeRenewal:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<PageWrap<StoreRenewalPageVo>> findPage(@RequestBody PageWrap<StoreReviewPageDto> pageWrap) {
        return storeRenewalService.findPage(pageWrap);
    }



    /**
     * 详情
     */
    @RequiresPermissions("business:storeRenewal:detail")
    @PostMapping("/detail")
    @ApiOperation("详情(business:storeRenewal:detail)")
    public ApiRes<StoreRenewalDetailVo> detail(@RequestBody StoreReviewDetailDto storeReviewDetailDto) {
        return storeRenewalService.detail(storeReviewDetailDto);
    }



    /**
     * 一审
     */
    @RequiresPermissions("business:storeRenewal:one")
    @PostMapping("/one")
    @ApiOperation("一审(business:storeRenewal:one)")
    public ApiRes one(@RequestBody StoreReviewDto storeReviewDto) {
        return storeRenewalService.one(storeReviewDto);
    }


    /**
     * 二审弹窗内容
     */
    @PostMapping("/popup")
    @ApiOperation("二审弹窗内容")
    public ApiRes<StoreReviewPopupVo> popup(@RequestBody StoreReviewPopupDto storeReviewPopupDto) {
        return storeRenewalService.popup(storeReviewPopupDto);
    }


    /**
     * 二审
     */
    @RequiresPermissions("business:storeRenewal:two")
    @PostMapping("/two")
    @ApiOperation("二审(business:storeRenewal:two)")
    public ApiRes two(@RequestBody StoreReviewDto storeReviewDto) {
        return storeRenewalService.two(storeReviewDto);
    }

}
