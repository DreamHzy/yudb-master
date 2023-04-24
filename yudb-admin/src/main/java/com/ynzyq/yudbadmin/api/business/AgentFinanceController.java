package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.FinancePageDto;
import com.ynzyq.yudbadmin.dao.business.dto.ReportDTO;
import com.ynzyq.yudbadmin.dao.business.vo.AgentFinanceListVo;
import com.ynzyq.yudbadmin.dao.business.vo.FinanceListVo;
import com.ynzyq.yudbadmin.dao.business.vo.FinancePageVo;
import com.ynzyq.yudbadmin.dao.business.vo.PayTypeListVo;
import com.ynzyq.yudbadmin.service.business.AgentFinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "(新)代理财务管理")
@Slf4j
@RequestMapping("/agentFinance")
@RestController
public class AgentFinanceController {

    @Resource
    AgentFinanceService agentFinanceService;


    /**
     * 财务管理列表
     */
    @RequiresPermissions("business:agentFinance:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<AgentFinanceListVo> findPage(@RequestBody PageWrap<FinancePageDto> pageWrap) {
        return agentFinanceService.findPage(pageWrap);
    }


    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation("导出")
    public void export(FinancePageDto financePageDto, HttpServletResponse httpServletResponse) {
        agentFinanceService.export(financePageDto, httpServletResponse);
    }


    /**
     * 缴费类型列表
     */
    @PostMapping("/payTypeList")
    @ApiOperation("缴费类型列表")
    public ApiRes<PayTypeListVo> payTypeList() {
        return agentFinanceService.payTypeList();
    }

    /**
     * 提报/批量提报
     *
     * @param reportDTO
     * @return
     */
    @ApiOperation("提报")
    @PostMapping("/report")
    public ApiRes report(@RequestBody ReportDTO reportDTO) {
        return agentFinanceService.report(reportDTO);
    }

}
