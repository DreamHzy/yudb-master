package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.FinancePageDto;
import com.ynzyq.yudbadmin.dao.business.dto.ReportDTO;
import com.ynzyq.yudbadmin.dao.business.vo.AgentFinanceListVo;
import com.ynzyq.yudbadmin.dao.business.vo.PayTypeListVo;

import javax.servlet.http.HttpServletResponse;

public interface AgentFinanceService {
    ApiRes<AgentFinanceListVo> findPage(PageWrap<FinancePageDto> pageWrap);

    ApiRes<PayTypeListVo> payTypeList();

    ApiRes export(FinancePageDto financePageDto, HttpServletResponse httpServletResponse);

    /**
     * 提报/批量提报
     *
     * @param reportDTO
     * @return
     */
    ApiRes report(ReportDTO reportDTO);
}
