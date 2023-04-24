package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.dao.business.dto.SmsDto;

import javax.servlet.http.HttpServletRequest;

public interface CommonService {
    ApiRes sms(SmsDto dto, HttpServletRequest httpServletRequest);
}
