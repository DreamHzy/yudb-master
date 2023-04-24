package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.ListOverdueOrderDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ManageStatementDTO;
import com.ynzyq.yudbadmin.dao.business.dto.StatementIdDTO;
import com.ynzyq.yudbadmin.dao.business.vo.ListOverdueOrderVO;
import com.ynzyq.yudbadmin.dao.business.vo.ListStatementVO;
import com.ynzyq.yudbadmin.dao.business.vo.ManageStatementVO;
import com.ynzyq.yudbadmin.dao.business.vo.StatementDetailVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xinchen
 * @date 2021/12/8 14:39
 * @description:
 */
public interface IStatementService {

    /**
     * 日报表列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ListStatementVO> listStatement(PageWrap pageWrap);

    /**
     * 详情
     *
     * @param statementIdDTO
     * @return
     */
    ApiRes<StatementDetailVO> statementDetail(StatementIdDTO statementIdDTO);

    /**
     * 下载
     *
     * @param statementIdDTO
     * @param response
     */
    void download(StatementIdDTO statementIdDTO, HttpServletResponse response);

    /**
     * 管理列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ManageStatementVO> listManageStatement(PageWrap<ManageStatementDTO> pageWrap);

    /**
     * 导出
     *
     * @param manageStatementDTO
     * @param response
     */
    void exportManageStatement(ManageStatementDTO manageStatementDTO, HttpServletResponse response);

    /**
     * 逾期账单
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ListOverdueOrderVO> listOverdueOrder(PageWrap<ListOverdueOrderDTO> pageWrap);

    /**
     * 导出
     *
     * @param listOverdueOrderDTO
     * @param response
     */
    void exportOverdueOrder(ListOverdueOrderDTO listOverdueOrderDTO, HttpServletResponse response);
}
