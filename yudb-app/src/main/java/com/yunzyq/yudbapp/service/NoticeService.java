package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.NoticeDetailDto;
import com.yunzyq.yudbapp.dao.dto.NoticePageDto;
import com.yunzyq.yudbapp.dao.vo.NoticeDetailVo;
import com.yunzyq.yudbapp.dao.vo.NoticePageVo;

import javax.servlet.http.HttpServletRequest;

public interface NoticeService {
    ApiRes<PageWrap<NoticePageVo>> findPage(HttpServletRequest httpServletRequest, PageWrap<NoticePageDto>  pageWrap);

    ApiRes<NoticeDetailVo> detail(NoticeDetailDto noticeDetailDto);
}
