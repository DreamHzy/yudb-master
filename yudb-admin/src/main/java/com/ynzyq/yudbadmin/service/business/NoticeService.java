package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.NoticePageVo;
import com.ynzyq.yudbadmin.dao.business.vo.NoticeSeeVo;

public interface NoticeService {
    ApiRes<PageWrap<NoticePageVo>> findPage(PageWrap<NoticePageDto> pageWrap);

    ApiRes top(NoticeTopDto noticeTopDto);

    ApiRes status(NoticeStatusDto noticeStatusDto);

    ApiRes noticeType(NoticeTypeDto noticeTypeDto);

    ApiRes<NoticeSeeVo> see(NoticeSeeDto noticeSeeDto);

    ApiRes add(NoticeAddDto noticeSeeDto);

    ApiRes edit(NoticeEditDto noticeEditDto);
}
