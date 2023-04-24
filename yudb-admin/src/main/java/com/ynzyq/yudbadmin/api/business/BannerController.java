package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.BannerAddDto;
import com.ynzyq.yudbadmin.dao.business.dto.BannerStatus;
import com.ynzyq.yudbadmin.dao.business.vo.BannerPageVo;
import com.ynzyq.yudbadmin.service.business.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "(新)轮播图管理接口")
@Slf4j
@RequestMapping("/banner")
@RestController
public class BannerController extends BaseController {

    @Resource
    BannerService bannerService;


    /**
     * 轮播图列表
     */
    @RequiresPermissions("business:banner:query")
    @PostMapping("/page")
    @ApiOperation("轮播图列表")
    public ApiRes<PageWrap<BannerPageVo>> findPage(@RequestBody PageWrap pageWrap) {
        return bannerService.findPage(pageWrap);
    }


    /**
     * 新建轮播位
     */
    @RequiresPermissions("business:banner:add")
    @PostMapping("/add")
    @ApiOperation("新建轮播位(business:banner:add)")
    public ApiRes add(@RequestBody BannerAddDto bannerAddDto) {
        return bannerService.add(bannerAddDto);
    }


    /**
     * 发布状态
     */
    @RequiresPermissions("business:banner:updatStatus")
    @PostMapping("/updatStatus")
    @ApiOperation("发布状态")
    public ApiRes updatStatus(@RequestBody BannerStatus bannerStatus) {
        return bannerService.updatStatus(bannerStatus);
    }

}
