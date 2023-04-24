package com.ynzyq.yudbadmin.api.business;

import com.alibaba.fastjson.JSON;
import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.CommonConstant;
import com.ynzyq.yudbadmin.core.model.LoginUserInfo;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.IAgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author wj
 * @Date 2021/10/26
 */
@Api(tags = "代理权管理")
@Slf4j
@RequestMapping("/agent")
@RestController
public class AgentController {

    private static final String LOGGER_PREFIX = "【代理管理】";

    @Resource
    IAgentService iAgentService;

    /**
     * 代理管理列表
     *
     * @param pageWrap
     * @return
     */
    @RequiresPermissions("business:agent:list")
    @ApiOperation("列表(business:agent:list)")
    @PostMapping("/listAgent")
    public ApiRes<ListAgentVO> listAgent(@RequestBody PageWrap<ListAgentDTO> pageWrap) {
        log.info("{}代理管理列表请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(pageWrap));
        return iAgentService.listAgent(pageWrap);
    }

    /**
     * 修改订货配送信息
     */
    @Log("修改订货配送信息")
//    @RequiresPermissions("business:agent:modifyData")
    @ApiOperation("修改订货配送信息(business:agent:modifyData)")
    @PostMapping("/modifyData")
    public ApiRes modifyData(@RequestBody ModifyDataDTO modifyDataDTO) {
        return iAgentService.modifyDataService(modifyDataDTO);
    }

    /**
     * 代理详情
     *
     * @param
     * @return
     */
    @Log("代理详情")
    @RequiresPermissions("business:agent:detail")
    @ApiOperation("详情(business:agent:detail)")
    @PostMapping("/singleAgentDetail")
    public ApiRes<SingleAgentDetailVO> singleAgentDetail(@RequestBody SingleAgentDetailDTO singleAgentDetailDTO) {
        log.info("{}代理详情请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(singleAgentDetailDTO));
        return iAgentService.singleAgentDetail(singleAgentDetailDTO);
    }

    /**
     * 编辑区域经理
     *
     * @param editRegionalDTO
     * @return
     */
    @RequiresPermissions("business:agent:editRegional")
    @ApiOperation("编辑区域经理(business:agent:editRegional)")
    @PostMapping("/editRegional")
    public ApiRes<SingleAgentDetailVO> editRegional(@RequestBody @Valid EditRegionalDTO editRegionalDTO) {
        log.info("{}编辑区域经理请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(editRegionalDTO));
        return iAgentService.editRegional(editRegionalDTO);
    }

    /**
     * 调整管理费标准
     *
     * @param editManagementExpenseDTO
     * @return
     */
    @RequiresPermissions("business:agent:editExpense")
    @ApiOperation("调整管理费(business:agent:editExpense)")
    @PostMapping("/editManagementExpense")
    public ApiRes<SingleAgentDetailVO> editManagementExpense(@RequestBody @Valid EditManagementExpenseDTO editManagementExpenseDTO) {
        log.info("{}编辑区域经理请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(editManagementExpenseDTO));
        // 当前登录人信息
        LoginUserInfo loginUserInfo = (LoginUserInfo) SecurityUtils.getSubject().getPrincipal();
        return iAgentService.editManagementExpense(editManagementExpenseDTO, loginUserInfo.getId());
    }


    /**
     * EXCEL缴费表模板
     */
    @PostMapping("/excelSubmitForReviewFile")
    @ApiOperation("EXCEL缴费表模板")
    public ApiRes excelSubmitForReview() {
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "查询成功", "https://yudianbao.ynzyq.cn/代理-缴费模板.xlsx");
    }

    /**
     * excel导入缴费表
     */
    @PostMapping("/excelSubmitForReview")
    @RequiresPermissions("business:agent:excelSubmitForReview")
    @ApiOperation("excel导入缴费表")
    public ApiRes excelSubmitForReview(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest httpServletRequest) {
        return iAgentService.excelSubmitForReview(file, httpServletRequest);
    }


    /**
     * 缴费类型列表
     */
    @PostMapping("/payTypeList")
    @ApiOperation("缴费类型列表")
    public ApiRes<PayTypeListVo> payTypeList() {
        return iAgentService.payTypeList();
    }


    /**
     * 单个缴费提交
     */
    @PostMapping("/oneSubmitForReview")
    @RequiresPermissions("business:agent:oneSubmitForReview")
    @ApiOperation("单个缴费提交(business:agent:oneSubmitForReview)")
    public ApiRes submitForReview(@RequestBody SubmitForReviewDto submitForReviewDto, HttpServletRequest httpServletRequest) {
        return iAgentService.submitForReview(submitForReviewDto, httpServletRequest);
    }


    /**
     * 添加代理
     */
    @PostMapping("/addAgent")
    @RequiresPermissions("business:agent:addAgent")
    @ApiOperation("添加代理(business:agent:addAgent)")
    public ApiRes addAgent(@RequestBody AddAgentDto addAgentDto) {
        return iAgentService.addAgent(addAgentDto);
    }

    /**
     * 导出
     *
     * @param listAgentDTO
     * @return
     */
    @GetMapping("/exportAgent")
//    @RequiresPermissions("business:agent:exportAgent")
    @ApiOperation("导出(business:agent:exportAgent)")
    public void exportAgent(ListAgentDTO listAgentDTO, HttpServletResponse response) {
        iAgentService.exportAgent(listAgentDTO, response);
    }

    /**
     * 编辑前查询
     *
     * @param agentIdDTO
     */
    @PostMapping("/singleAgent")
    @ApiOperation("编辑前查询")
    public ApiRes<SingleAgentVO> singleAgent(@RequestBody AgentIdDTO agentIdDTO) {
        return iAgentService.singleAgent(agentIdDTO);
    }

    /**
     * 编辑代理权
     *
     * @param editAgentDTO
     */
    @PostMapping("/editAgent")
    @RequiresPermissions("business:agent:editAgent")
    @ApiOperation("编辑代理权(business:agent:editAgent)")
    public ApiRes editAgent(@RequestBody @Valid EditAgentDTO editAgentDTO) {
        return iAgentService.editAgent(editAgentDTO);
    }

    /**
     * 修改代理权是否生效
     *
     * @param editEffectDTO
     */
    @Log("修改代理权是否生效")
    @PostMapping("/editEffect")
    @ApiOperation("修改代理权是否生效(business:agent:editEffect)")
    public ApiRes editEffect(@RequestBody @Valid EditEffectDTO editEffectDTO) {
        return iAgentService.editEffect(editEffectDTO);
    }

    /**
     * 编辑收款账户
     *
     * @param merchantIdDTO
     * @return
     */
    @ApiOperation("编辑收款账户(business:agent:editReceivingAccount)")
    @PostMapping("/editReceivingAccount")
    public ApiRes editReceivingAccount(@RequestBody MerchantIdDTO merchantIdDTO) {
        return iAgentService.editReceivingAccount(merchantIdDTO);
    }

    /**
     * 代理体系下拉框
     *
     * @return
     */
    @PostMapping("/systemFactorSelectBox")
    @ApiOperation("代理体系下拉框")
    public ApiRes<OneLevelStatusVO> systemFactorSelectBox() {
        return iAgentService.systemFactorSelectBox();
    }

    /**
     * 修改代理体系
     *
     * @param editSystemFactorDTO
     */
    @Log("修改代理体系")
    @PostMapping("/editSystemFactor")
    @ApiOperation("修改代理体系(business:agent:editSystemFactor)")
    public ApiRes editSystemFactor(@RequestBody @Valid EditSystemFactorDTO editSystemFactorDTO) {
        return iAgentService.editSystemFactor(editSystemFactorDTO);
    }
}
