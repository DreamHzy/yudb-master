package com.ynzyq.yudbadmin.service.evaluate;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.evaluate.dto.*;
import com.ynzyq.yudbadmin.dao.evaluate.vo.EmployeeVO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.EvaluationVO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.ListEvaluateItemVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xinchen
 * @date 2021/12/1 11:50
 * @description:
 */
public interface IEvaluateService {
    /**
     * 评估申请表事项内容
     *
     * @return
     */
    ApiRes<ListEvaluateItemVO> listEvaluateItem();

    /**
     * 添加评估申请表
     *
     * @param evaluationDTO
     * @return
     */
    ApiRes addEvaluate(EvaluationDTO evaluationDTO);

    /**
     * 意向申请列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<EvaluationVO> listEvaluate(PageWrap<ListEvaluateDTO> pageWrap);

    /**
     * 导出
     *
     * @param listEvaluateDTO
     * @param response
     */
    void exportEvaluate(ListEvaluateDTO listEvaluateDTO, HttpServletResponse response);

    void employeeInformationService(EmployeeDTO employeeDTO, HttpServletResponse response);

    ApiRes<EmployeeVO> employeeInformationQueryService(PageWrap<EmployeeKeysDTO> employeeDTO);

    ApiRes EmployeeInformationStorageService(EmployeeStorageDTO employeeDTO);
}
