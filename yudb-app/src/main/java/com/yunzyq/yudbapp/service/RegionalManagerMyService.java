package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.RegionalManagerDaibDto;
import com.yunzyq.yudbapp.dao.dto.UpdatePwdDto;
import com.yunzyq.yudbapp.dao.vo.RegionalManagerDaibVo;

import javax.servlet.http.HttpServletRequest;

public interface RegionalManagerMyService {
    ApiRes my(HttpServletRequest httpServletRequest);

    ApiRes<PageWrap<RegionalManagerDaibVo>> daib(HttpServletRequest httpServletRequest, PageWrap<RegionalManagerDaibDto> pageWrap);

    ApiRes updatePwd(HttpServletRequest httpServletRequest, UpdatePwdDto updatePwdDto);
}
