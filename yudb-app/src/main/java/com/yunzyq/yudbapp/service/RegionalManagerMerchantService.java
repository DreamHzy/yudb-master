package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.vo.HistoryOrderVo;
import com.yunzyq.yudbapp.dao.vo.PayTypeListVo;
import com.yunzyq.yudbapp.dao.vo.StorePage2Vo;
import com.yunzyq.yudbapp.dao.vo.StroePageVo;

import javax.servlet.http.HttpServletRequest;

public interface RegionalManagerMerchantService {
    ApiRes<PageWrap<StroePageVo>> findPage(PageWrap<StroePageDto> pageWrap, HttpServletRequest httpServletRequest);

    ApiRes<StorePage2Vo> findPage2(PageWrap<StroePage2Dto> pageWrap, HttpServletRequest httpServletRequest);

    ApiRes<PayTypeListVo> payTypeList();

    ApiRes submitForReview(SubmitForReviewDto submitForReviewDto,HttpServletRequest httpServletRequest);

    ApiRes<PageWrap<HistoryOrderVo>> historyOrder(HttpServletRequest httpServletRequest, PageWrap<HistoryOrderDto> pageWrap);

    ApiRes confirmRenewal(ConfirmRenewalDto confirmRenewalDto, HttpServletRequest httpServletRequest);

    ApiRes renewalWithdrawal(RenewalWithdrawalDto renewalWithdrawalDto, HttpServletRequest httpServletRequest);

    ApiRes signAContract(SignAContractDto signAContractDto, HttpServletRequest httpServletRequest);

    ApiRes withdrawalOfContract(WithdrawalOfContractDto withdrawalOfContractDto, HttpServletRequest httpServletRequest);

    ApiRes rescind(RescindDto rescindDto, HttpServletRequest httpServletRequest);

    ApiRes terminationWithdrawal(TerminationWithdrawalDto terminationWithdrawalDto, HttpServletRequest httpServletRequest);

    ApiRes againConfirmRenewal(AgainConfirmRenewalDto againConfirmRenewalDto, HttpServletRequest httpServletRequest);
}
