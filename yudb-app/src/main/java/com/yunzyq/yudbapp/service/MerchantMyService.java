package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.MerchantDaibDto;
import com.yunzyq.yudbapp.dao.dto.UpdatePwdDto;
import com.yunzyq.yudbapp.dao.vo.MerchantDaibVo;
import com.yunzyq.yudbapp.dao.vo.StoreListVo;

import javax.servlet.http.HttpServletRequest;

public interface MerchantMyService {
    ApiRes my(HttpServletRequest httpServletRequest);

    ApiRes<PageWrap<MerchantDaibVo>> daib(HttpServletRequest httpServletRequest, PageWrap<MerchantDaibDto> pageWrap);

    ApiRes<PageWrap<StoreListVo>> storeList(HttpServletRequest httpServletRequest, PageWrap pageWrap);

    ApiRes updatePwd(HttpServletRequest httpServletRequest, UpdatePwdDto updatePwdDto);

    /**
     * 我的代理权
     *
     * @param httpServletRequest
     * @return
     */
    ApiRes myAgentArea(HttpServletRequest httpServletRequest);
}
