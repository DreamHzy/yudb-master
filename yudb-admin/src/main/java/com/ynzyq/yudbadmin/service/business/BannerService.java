package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.BannerAddDto;
import com.ynzyq.yudbadmin.dao.business.dto.BannerStatus;
import com.ynzyq.yudbadmin.dao.business.model.Banner;
import com.ynzyq.yudbadmin.dao.business.vo.BannerPageVo;

public interface BannerService {
    ApiRes<PageWrap<BannerPageVo>> findPage(PageWrap pageWrap);

    ApiRes add(BannerAddDto bannerAddDto);

    ApiRes updatStatus(BannerStatus bannerStatus);
}
