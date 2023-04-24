package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.ApplyForAdjustmentDto;
import com.yunzyq.yudbapp.dao.dto.AuditRecordDTO;
import com.yunzyq.yudbapp.dao.dto.PlatformPageDTO;
import com.yunzyq.yudbapp.dao.dto.PlatformPageDetatilDto;
import com.yunzyq.yudbapp.dao.model.PaymentOrderPay;
import com.yunzyq.yudbapp.dao.vo.*;

import javax.servlet.http.HttpServletRequest;

public interface RegionalManagerOrderService {
    ApiRes<PageWrap<RegionalManagerPlatformPageVo>> findPage(HttpServletRequest httpServletRequest, PlatformPageDTO platformPageDTO);

    ApiRes<ExamineFileVO> examineFile(AuditRecordDTO auditRecordDTO);

    ApiRes<AuditRecordVO> auditRecord(AuditRecordDTO auditRecordDTO);

    ApiRes<PlatformPageDetatilVo> deatil(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto);

    ApiRes<PageWrap<PlatformRecordPageVo>> platformRecordPage(HttpServletRequest httpServletRequest, PageWrap pageWrap);

    ApiRes applyForAdjustment(HttpServletRequest httpServletRequest, ApplyForAdjustmentDto pageDetatilDto);

    ApiRes delete(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto);

    ApiRes withdrawal(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto);

    ApiRes paymentCode(HttpServletRequest httpServletRequest, PlatformPageDetatilDto pageDetatilDto);

    String callBack(PaymentOrderPay paymentOrderPay);

    ApiRes send(PlatformPageDetatilDto platformPageDetatilDto);

    ApiRes sendBack(PlatformPageDetatilDto platformPageDetatilDto);

    ApiRes<PageWrap<RegionalManagerPlatformPageVo>> platformPageNoSh(HttpServletRequest httpServletRequest, PageWrap pageWrap);
}
