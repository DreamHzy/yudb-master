package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.FinanceListVo;
import com.ynzyq.yudbadmin.dao.business.vo.OneLevelStatusVO;
import com.ynzyq.yudbadmin.dao.business.vo.PayTypeListVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FinanceService {
    ApiRes<FinanceListVo> findPage(PageWrap<FinancePageDto> pageWrap);

    ApiRes export(FinancePageDto financePageDto, HttpServletResponse httpServletResponse);

    ApiRes<PayTypeListVo> payTypeList();

    /**
     * 付款类型下拉框
     *
     * @return
     */
    ApiRes<OneLevelStatusVO> payTypeSelectBox();

    ApiRes settle(MultipartFile file);

    /**
     * 提报/批量提报
     *
     * @param reportDTO
     * @return
     */
    ApiRes report(ReportDTO reportDTO);

    ApiRes<FinanceListVo> listFinanceOrder(PageWrap<ListFinanceOrderDTO> pageWrap);

    ApiRes cancelOrder(CancelOrderDTO cancelOrderDTO, HttpServletResponse httpServletResponse);

    ApiRes updateExpireTime(UpdateExpireTimeDTO updateExpireTimeDTO, HttpServletResponse httpServletResponse);

    ApiRes updatePublishStatus(UpdatePublishStatusDTO updatePublishStatusDTO);
}
