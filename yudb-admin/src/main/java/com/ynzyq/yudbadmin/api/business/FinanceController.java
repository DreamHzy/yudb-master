package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;
import com.ynzyq.yudbadmin.service.business.FinanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "(新)门店财务管理")
@Slf4j
@RequestMapping("/finance")
@RestController
public class FinanceController extends BaseController {

    @Resource
    FinanceService financeService;


    /**
     * 财务管理列表
     */
    @RequiresPermissions("business:finance:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<FinanceListVo> findPage(@RequestBody PageWrap<FinancePageDto> pageWrap) {
        return financeService.findPage(pageWrap);
    }

    /**
     * //     * 导出
     * //
     */
//    @RequiresPermissions("business:finance:export")
//    @RequestMapping(value = "/export", method = RequestMethod.POST)
//    @ApiOperation("导出(business:finance:export)")
//    public void export(@RequestBody FinancePageDto financePageDto, HttpServletResponse httpServletResponse) {
//        financeService.export(financePageDto, httpServletResponse);
//    }
//    @RequiresPermissions("business:finance:export")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation("导出")
    public void export(FinancePageDto financePageDto, HttpServletResponse httpServletResponse) {
        financeService.export(financePageDto, httpServletResponse);
    }

    /**
     * 缴费类型列表
     */
    @PostMapping("/payTypeList")
    @ApiOperation("缴费类型列表")
    public ApiRes<PayTypeListVo> payTypeList() {
        return financeService.payTypeList();
    }

    /**
     * 付款类型下拉框
     *
     * @return
     */
    @PostMapping("/payTypeSelectBox")
    @ApiOperation("付款类型下拉框")
    public ApiRes<OneLevelStatusVO> payTypeSelectBox() {
        return financeService.payTypeSelectBox();
    }

    /**
     * 清算
     *
     * @param file
     * @return
     */
    @ApiOperation("清算(business:finance:settle)")
    @RequiresPermissions("business:finance:settle")
    @PostMapping("/settle")
    public ApiRes settle(MultipartFile file) {
        return financeService.settle(file);
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
        return financeService.report(reportDTO);
    }


    /**
     * 已发布待缴账单/未发布待缴账单
     *
     * @param pageWrap
     * @return
     */
    @PostMapping("/listFinanceOrder")
    @ApiOperation("已发布待缴账单/未发布待缴账单")
    public ApiRes<FinanceListVo> listFinanceOrder(@RequestBody PageWrap<ListFinanceOrderDTO> pageWrap) {
        return financeService.listFinanceOrder(pageWrap);
    }

    /**
     * 取消缴费
     *
     * @param cancelOrderDTO
     * @param httpServletResponse
     * @return
     */
    @ApiOperation("取消缴费(business:finance:cancel)")
    @RequiresPermissions("business:finance:cancel")
    @PostMapping("/cancelOrder")
    public ApiRes cancelOrder(@RequestBody @Valid CancelOrderDTO cancelOrderDTO, HttpServletResponse httpServletResponse) {
        return financeService.cancelOrder(cancelOrderDTO, httpServletResponse);
    }

    /**
     * 取消缴费
     *
     * @param updateExpireTimeDTO
     * @param httpServletResponse
     * @return
     */
    @Log("修改缴费截止时间")
    @ApiOperation("修改缴费截止时间(business:finance:update)")
    @RequiresPermissions("business:finance:update")
    @PostMapping("/updateExpireTime")
    public ApiRes updateExpireTime(@RequestBody @Valid UpdateExpireTimeDTO updateExpireTimeDTO, HttpServletResponse httpServletResponse) {
        return financeService.updateExpireTime(updateExpireTimeDTO, httpServletResponse);
    }

    /**
     * 修改发布状态
     *
     * @param updatePublishStatusDTO
     * @return
     */
    @Log("修改发布状态")
    @ApiOperation("修改发布状态(business:finance:publish)")
    @RequiresPermissions("business:finance:publish")
    @PostMapping("/updatePublishStatus")
    public ApiRes updatePublishStatus(@RequestBody @Valid UpdatePublishStatusDTO updatePublishStatusDTO) {
        return financeService.updatePublishStatus(updatePublishStatusDTO);
    }

}
