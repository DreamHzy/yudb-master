package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDetailDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPopupDto;
import com.ynzyq.yudbadmin.dao.business.vo.StoreReviewDetailVo;
import com.ynzyq.yudbadmin.dao.business.vo.StoreReviewPageVo;
import com.ynzyq.yudbadmin.dao.business.vo.StoreReviewPopupVo;

public interface StoreReviewService {
    ApiRes<PageWrap<StoreReviewPageVo>> findPage(PageWrap<StoreReviewPageDto> pageWrap);

    ApiRes<StoreReviewDetailVo> detail(StoreReviewDetailDto storeReviewDetailDto);

    ApiRes one(StoreReviewDto storeReviewDto);

    ApiRes two(StoreReviewDto storeReviewDto);

    ApiRes<StoreReviewPopupVo> popup(StoreReviewPopupDto storeReviewPopupDto);
}
