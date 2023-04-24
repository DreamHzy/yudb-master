package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.NoticePageVo;
import com.ynzyq.yudbadmin.dao.business.vo.NoticeSeeVo;
import com.ynzyq.yudbadmin.service.business.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "(新)信息通知")
@Slf4j
@RequestMapping("/notice")
@RestController
public class NoticeController extends BaseController {

    @Resource
    NoticeService noticeService;


    /**
     * 信息通知列表
     */
    @RequiresPermissions("business:notice:query")
    @PostMapping("/page")
    @ApiOperation("信息通知列表")
    public ApiRes<PageWrap<NoticePageVo>> findPage(@RequestBody PageWrap<NoticePageDto> pageWrap) {
        return noticeService.findPage(pageWrap);
    }

    /**
     * 置顶
     */
    @RequiresPermissions("business:notice:top")
    @PostMapping("/top")
    @ApiOperation("信息通知置顶")
    public ApiRes top(@RequestBody NoticeTopDto noticeTopDto) {
        return noticeService.top(noticeTopDto);
    }

    /**
     * 发布状态
     */
    @RequiresPermissions("business:notice:status")
    @PostMapping("/status")
    @ApiOperation("发布状态")
    public ApiRes status(@RequestBody NoticeStatusDto noticeStatusDto) {
        return noticeService.status(noticeStatusDto);
    }

    /**
     * 是否精选
     */
    @RequiresPermissions("business:notice:noticeType")
    @PostMapping("/noticeType")
    @ApiOperation("是否精选")
    public ApiRes noticeType(@RequestBody NoticeTypeDto noticeTypeDto) {
        return noticeService.noticeType(noticeTypeDto);
    }

    /**
     * 查看
     */
    @RequiresPermissions("business:notice:see")
    @PostMapping("/see")
    @ApiOperation("查看(business:notice:see)")
    public ApiRes<NoticeSeeVo> see(@RequestBody NoticeSeeDto noticeSeeDto) {
        return noticeService.see(noticeSeeDto);
    }


    /**
     * 新建
     */
    @RequiresPermissions("business:notice:add")
    @PostMapping("/add")
    @ApiOperation("新建(business:notice:add)")
    public ApiRes add(@RequestBody NoticeAddDto noticeSeeDto) {
        return noticeService.add(noticeSeeDto);
    }


    /**
     * 编辑
     */
    @RequiresPermissions("business:notice:edit")
    @PostMapping("/edit")
    @ApiOperation("编辑(business:notice:edit)")
    public ApiRes add(@RequestBody NoticeEditDto noticeEditDto) {
        return noticeService.edit(noticeEditDto);
    }


}
