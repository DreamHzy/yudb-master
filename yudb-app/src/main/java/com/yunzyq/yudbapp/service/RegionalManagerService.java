package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.dao.vo.IndexRegionalManagerVo;

import javax.servlet.http.HttpServletRequest;

public interface RegionalManagerService {
    ApiRes<IndexRegionalManagerVo> index(HttpServletRequest httpServletRequest);
}
