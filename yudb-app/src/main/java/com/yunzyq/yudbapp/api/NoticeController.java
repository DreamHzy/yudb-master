package com.yunzyq.yudbapp.api;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.NoticeDetailDto;
import com.yunzyq.yudbapp.dao.dto.NoticePageDto;
import com.yunzyq.yudbapp.dao.vo.NoticeDetailVo;
import com.yunzyq.yudbapp.dao.vo.NoticePageVo;
import com.yunzyq.yudbapp.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "通知列表相关接口")
@Slf4j
@RequestMapping("/notice")
@RestController
public class NoticeController {

    @Resource
    NoticeService noticeService;

    /**
     * 信息通知列表
     */
    @PostMapping("/page")
    @ApiOperation("信息通知列表")
    public ApiRes<PageWrap<NoticePageVo>> findPage(HttpServletRequest httpServletRequest, @RequestBody PageWrap<NoticePageDto> pageWrap) {
        return noticeService.findPage(httpServletRequest, pageWrap);
    }

    /**
     * 详情
     */
    @PostMapping("/detail")
    @ApiOperation("详情")
    public ApiRes<NoticeDetailVo> detail(@RequestBody NoticeDetailDto noticeDetailDto) {
        return noticeService.detail(noticeDetailDto);
    }
}
