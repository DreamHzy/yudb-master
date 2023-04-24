package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDetailDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.StoreReviewPopupDto;
import com.ynzyq.yudbadmin.dao.business.vo.StoreRenewalDetailVo;
import com.ynzyq.yudbadmin.dao.business.vo.StoreRenewalPageVo;
import com.ynzyq.yudbadmin.dao.business.vo.StoreReviewPopupVo;

public interface StoreRenewalService {
    ApiRes<PageWrap<StoreRenewalPageVo>> findPage(PageWrap<StoreReviewPageDto> pageWrap);


    ApiRes one(StoreReviewDto storeReviewDto);

    ApiRes<StoreReviewPopupVo> popup(StoreReviewPopupDto storeReviewPopupDto);

    ApiRes two(StoreReviewDto storeReviewDto);

    ApiRes<StoreRenewalDetailVo> detail(StoreReviewDetailDto storeReviewDetailDto);
}
