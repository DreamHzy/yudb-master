package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.MonitorUserAddDto;
import com.ynzyq.yudbadmin.dao.business.dto.MonitorUserStatusDto;
import com.ynzyq.yudbadmin.dao.business.vo.MonitorUserPageVo;

import javax.servlet.http.HttpServletRequest;

public interface MonitorUserService {
    ApiRes<MonitorUserPageVo> findPage(PageWrap pageWrap);

    ApiRes add(MonitorUserAddDto monitorUserAddDto, HttpServletRequest httpServletRequest);

    ApiRes updatStatus(MonitorUserStatusDto monitorUserStatusDto);
}
