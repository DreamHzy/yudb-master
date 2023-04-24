package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.ListAppMenuVO;
import com.ynzyq.yudbadmin.dao.business.vo.MerchantPageVo;
import com.ynzyq.yudbadmin.service.business.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "(新)客户管理")
@Slf4j
@RequestMapping("/merchant")
@RestController
public class MerchantController extends BaseController {

    @Resource
    MerchantService merchantService;

    /**
     * 加盟商列表
     */
    @RequiresPermissions("business:merchant:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<PageWrap<MerchantPageVo>> findPage(@RequestBody PageWrap<MerchantPageDto> pageWrap) {
        return merchantService.findPage(pageWrap);
    }

    /**
     * 新建
     */
    @Log("新建加盟商")
    @RequiresPermissions("business:merchant:add")
    @PostMapping("/add")
    @ApiOperation("新建(business:merchant:add)")
    public ApiRes add(@RequestBody MerchantAddDto merchantAddDto) {
        return merchantService.add(merchantAddDto);
    }


    /**
     * 编辑
     */
    @Log("编辑加盟商")
    @RequiresPermissions("business:merchant:edit")
    @PostMapping("/edit")
    @ApiOperation("编辑(business:merchant:edit)")
    public ApiRes edit(@RequestBody MerchantEditDto merchantAddDto) {
        return merchantService.edit(merchantAddDto);
    }


    /**
     * 导出
     *
     * @param merchantPageDto
     * @return
     */
    @GetMapping("/exportMerchant")
    @ApiOperation("导出")
    public void exportMerchant(MerchantPageDto merchantPageDto, HttpServletResponse response) {
        merchantService.exportMerchant(merchantPageDto, response);
    }

    /**
     * 启用/禁用
     *
     * @param updateStatusDTO
     * @return
     */
    @Log("启用/禁用")
    @PostMapping("/updateStatus")
    @ApiOperation("启用/禁用")
    public ApiRes updateStatus(@RequestBody @Valid UpdateStatusDTO updateStatusDTO) {
        return merchantService.updateStatus(updateStatusDTO);
    }

    /**
     * app菜单列表
     *
     * @param listAppMenuDTO
     * @return
     */
    @PostMapping("/listAppMenu")
    @ApiOperation("app菜单列表")
    public ApiRes<ListAppMenuVO> listAppMenu(@RequestBody @Valid ListAppMenuDTO listAppMenuDTO) {
        return merchantService.listAppMenu(listAppMenuDTO);
    }

    /**
     * 选择菜单
     *
     * @param chooseAppMenuDTO
     * @return
     */
    @PostMapping("/chooseAppMenu")
    @ApiOperation("选择菜单")
    public ApiRes chooseAppMenu(@RequestBody @Valid ChooseAppMenuDTO chooseAppMenuDTO) {
        return merchantService.chooseAppMenu(chooseAppMenuDTO);
    }
}
