package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewDetailDto;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewDto;
import com.ynzyq.yudbadmin.dao.business.dto.PayReviewPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.PopupDto;
import com.ynzyq.yudbadmin.dao.business.vo.PayReviewDetailVo;
import com.ynzyq.yudbadmin.dao.business.vo.PayReviewPageVo;
import com.ynzyq.yudbadmin.dao.business.vo.PopupVo;

public interface AgentPayReviewService {
    ApiRes<PageWrap<PayReviewPageVo>> findPage(PageWrap<PayReviewPageDto> pageWrap);

    ApiRes<PopupVo> popup(PopupDto popupDto);

    ApiRes one(PayReviewDto payReviewDto);

    ApiRes two(PayReviewDto payReviewDto);

    ApiRes<PayReviewDetailVo> detail(PayReviewDetailDto payReviewDetailDto);
}
