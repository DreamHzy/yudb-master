package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.dao.dto.*;
import com.yunzyq.yudbapp.dao.vo.LoginVo;
import com.yunzyq.yudbapp.dao.vo.MerchantOrderPayVo;
import com.yunzyq.yudbapp.dao.vo.PayShareInfoVO;

import javax.servlet.http.HttpServletRequest;

public interface CommonService {
    ApiRes sms(SmsDto dto, HttpServletRequest httpServletRequest);

    ApiRes<LoginVo> login(LoginDTO dto, HttpServletRequest httpServletRequest);

    ApiRes<LoginVo> smsLogin(SmsLoginDto dto, HttpServletRequest httpServletRequest);

    void loginRecord(HttpServletRequest httpServletRequest);

    ApiRes resetSmsPwd(ResetSmsPwdDto dto, HttpServletRequest httpServletRequest);

    ApiRes authQuery(HttpServletRequest httpServletRequest);

    ApiRes sendAuth(HttpServletRequest httpServletRequest);

    ApiRes<PayShareInfoVO> payShareInfo(PayShareInfoDTO payShareInfoDTO);

    ApiRes<MerchantOrderPayVo> goPay(GoPayDTO goPayDTO, HttpServletRequest request);

    ApiRes<MerchantOrderPayVo> goPaySeparately(GoPaySeparatelyDTO goPaySeparatelyDTO, HttpServletRequest request);

}
