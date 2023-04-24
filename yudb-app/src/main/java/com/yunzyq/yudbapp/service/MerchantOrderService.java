package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.vo.*;

import javax.servlet.http.HttpServletRequest;

public interface MerchantOrderService {
    ApiRes<SoreListVo> storeList(HttpServletRequest httpServletRequest);

    ApiRes<MerchantPlatformPageVo> merchantPlatformPageVo(PageWrap<StroeIdDto> pageWrap, HttpServletRequest httpServletRequest);

    ApiRes<MerchantOrderPayVo> pay(MerchantOrderPayDto merchantOrderPayDto, HttpServletRequest httpServletRequest);

    ApiRes<MerchantOrderPayVo> paySeparately(MerchantOrderPaySeparatelyDto merchantOrderPaySeparatelyDto, HttpServletRequest httpServletRequest);

    ApiRes<PaymentOnBehalfVo> paymentOnBehalf(MerchantOrderPayDto merchantOrderPayDto, HttpServletRequest httpServletRequest);

    ApiRes<PageWrap<PaymentRecordMerchantVo>> paymentRecord(HttpServletRequest httpServletRequest, PageWrap pageWrap);

    ApiRes paymentRecordDetail(PlatformPageDetatilDto platformPageDetatilDto);
}
