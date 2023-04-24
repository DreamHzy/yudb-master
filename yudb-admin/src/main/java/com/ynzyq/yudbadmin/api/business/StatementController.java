package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.ListOverdueOrderDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ManageStatementDTO;
import com.ynzyq.yudbadmin.dao.business.dto.StatementIdDTO;
import com.ynzyq.yudbadmin.dao.business.vo.ListOverdueOrderVO;
import com.ynzyq.yudbadmin.dao.business.vo.ListStatementVO;
import com.ynzyq.yudbadmin.dao.business.vo.ManageStatementVO;
import com.ynzyq.yudbadmin.dao.business.vo.StatementDetailVO;
import com.ynzyq.yudbadmin.service.business.IStatementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author xinchen
 * @date 2021/12/8 14:31
 * @description:
 */
@Api(tags = "报表模块")
@Slf4j
@RestController
public class StatementController {

    @Resource
    IStatementService iStatementService;

    /**
     * 日报表列表
     *
     * @param pageWrap
     * @return
     */
    @PostMapping("/statement/listStatement")
    @ApiOperation("日报表列表")
    public ApiRes<ListStatementVO> listStatement(@RequestBody PageWrap pageWrap) {
        return iStatementService.listStatement(pageWrap);
    }

    /**
     * 详情
     *
     * @param statementIdDTO
     * @return
     */
    @PostMapping("/statement/statementDetail")
    @ApiOperation("详情")
    public ApiRes<StatementDetailVO> statementDetail(@RequestBody @Valid StatementIdDTO statementIdDTO) {
        return iStatementService.statementDetail(statementIdDTO);
    }

    /**
     * 下载
     *
     * @param statementIdDTO
     * @param response
     * @return
     */
    @GetMapping("/statement/download")
    @ApiOperation("下载")
    public void download(StatementIdDTO statementIdDTO, HttpServletResponse response) {
        iStatementService.download(statementIdDTO, response);
    }

    /**
     * 管理列表
     *
     * @param pageWrap
     * @return
     */
    @PostMapping("/statement/listManageStatement")
    @ApiOperation("管理列表")
    public ApiRes<ManageStatementVO> listManageStatement(@RequestBody PageWrap<ManageStatementDTO> pageWrap) {
        return iStatementService.listManageStatement(pageWrap);
    }

    /**
     * 管理列表下载
     *
     * @param manageStatementDTO
     * @param response
     * @return
     */
    @GetMapping("/statement/exportManageStatement")
    @ApiOperation("管理列表下载")
    public void exportManageStatement(ManageStatementDTO manageStatementDTO, HttpServletResponse response) {
        iStatementService.exportManageStatement(manageStatementDTO, response);
    }

    /**
     * 逾期账单
     *
     * @param pageWrap
     * @return
     */
    @PostMapping("/statement/listOverdueOrder")
    @ApiOperation("逾期账单")
    public ApiRes<ListOverdueOrderVO> listOverdueOrder(@RequestBody PageWrap<ListOverdueOrderDTO> pageWrap) {
        return iStatementService.listOverdueOrder(pageWrap);
    }

    /**
     * 逾期账单下载
     *
     * @param listOverdueOrderDTO
     * @param response
     * @return
     */
    @GetMapping("/statement/exportOverdueOrder")
    @ApiOperation("逾期账单下载")
    public void exportOverdueOrder(ListOverdueOrderDTO listOverdueOrderDTO, HttpServletResponse response) {
        iStatementService.exportOverdueOrder(listOverdueOrderDTO, response);
    }
}
