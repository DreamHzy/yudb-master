package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.dao.dto.YGshangchengDto;
import com.yunzyq.yudbapp.dao.vo.IndexRegionalManagerVo;

import javax.servlet.http.HttpServletRequest;

public interface MerchantService {
    ApiRes<IndexRegionalManagerVo> index(HttpServletRequest httpServletRequest);

    ApiRes YGshangcheng(HttpServletRequest httpServletRequest, YGshangchengDto yGshangchengDto);
}
