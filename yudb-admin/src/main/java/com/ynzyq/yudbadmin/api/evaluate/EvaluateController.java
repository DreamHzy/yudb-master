package com.ynzyq.yudbadmin.api.evaluate;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.evaluate.dto.*;
import com.ynzyq.yudbadmin.dao.evaluate.vo.EmployeeVO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.EvaluationVO;
import com.ynzyq.yudbadmin.dao.evaluate.vo.ListEvaluateItemVO;
import com.ynzyq.yudbadmin.service.evaluate.IEvaluateService;
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
 * @date 2021/12/1 11:45
 * @description:
 */
@Api(tags = "评估事项")
@Slf4j
@RestController
public class EvaluateController {

    @Resource
    IEvaluateService iEvaluateService;

    /**
     * 评估申请表事项内容
     *
     * @return
     */
    @PostMapping("/evaluate/listEvaluateItem")
    @ApiOperation("评估申请表事项内容")
    public ApiRes<ListEvaluateItemVO> listEvaluateItem() {
        return iEvaluateService.listEvaluateItem();
    }

    /**
     * 评估申请表提交
     *
     * @param evaluationDTO
     * @return
     */
    @PostMapping("/evaluate/addEvaluate")
    @ApiOperation("评估申请表提交")
    public ApiRes addEvaluate(@RequestBody EvaluationDTO evaluationDTO) {
        return iEvaluateService.addEvaluate(evaluationDTO);
    }

    /**
     * 意向申请列表
     *
     * @param pageWrap
     * @return
     */
    @PostMapping("/evaluate/listEvaluate")
    @ApiOperation("意向申请列表")
    public ApiRes<EvaluationVO> listEvaluate(@RequestBody PageWrap<ListEvaluateDTO> pageWrap) {
        return iEvaluateService.listEvaluate(pageWrap);
    }

    /**
     * 导出
     *
     * @param
     * @param listEvaluateDTO
     * @return
     */
    @GetMapping("/evaluate/exportEvaluate")
    @ApiOperation("导出")
    public void exportEvaluate(ListEvaluateDTO listEvaluateDTO, HttpServletResponse response) {
        iEvaluateService.exportEvaluate(listEvaluateDTO, response);
    }

    /**
     * 员工信息收集
     *
     * @param
     * @param employeeDTO
     * @return
     */
    @GetMapping("/evaluate/employeeInformation")
    @ApiOperation("员工信息收集导出")
    public void employeeInformation(EmployeeDTO employeeDTO, HttpServletResponse response) {
        iEvaluateService.employeeInformationService(employeeDTO, response);
    }

    /**
     * 员工信息收集查询
     *
     * @param
     * @param employeeDTO
     * @return
     */
    @PostMapping("/evaluate/employeeInformationQuery")
    @ApiOperation("员工信息收集查询")
    public ApiRes<EmployeeVO> employeeInformationQuery(@RequestBody PageWrap<EmployeeKeysDTO> employeeDTO) {
       return iEvaluateService.employeeInformationQueryService(employeeDTO);
    }

    /**
     * 员工信息收集保存
     *
     * @param
     * @param employeeDTO
     * @return
     */
    @PostMapping("/evaluate/employeeInformationStorage")
    @ApiOperation("员工信息收集保存")
    public ApiRes employeeInformationStorage(@RequestBody @Valid EmployeeStorageDTO employeeDTO) {
        return iEvaluateService.EmployeeInformationStorageService(employeeDTO);
    }

}