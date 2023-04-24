package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.DepartmentListVo;
import com.ynzyq.yudbadmin.dao.business.vo.NoticePageVo;
import com.ynzyq.yudbadmin.dao.business.vo.RegionalManagerPageVo;
import com.ynzyq.yudbadmin.service.business.RegionalManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "(新)区域经理管理")
@Slf4j
@RequestMapping("/regionalManager")
@RestController
public class RegionalManagerController extends BaseController {

    @Resource
    RegionalManagerService regionalManagerService;

    /**
     * 列表
     */
    @RequiresPermissions("business:regionalManager:query")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<PageWrap<RegionalManagerPageVo>> findPage(@RequestBody PageWrap<RegionalManagerPageDto> pageWrap) {
        return regionalManagerService.findPage(pageWrap);
    }


    @RequiresPermissions("business:regionalManager:updateStatus")
    @PostMapping("/updateStatus")
    @ApiOperation("状态")
    public ApiRes updateStatus(@RequestBody RegionalManagerStatusDto regionalManagerStatusDto) {
        return regionalManagerService.updateStatus(regionalManagerStatusDto);
    }

    /**
     * 部门列表
     */
    @PostMapping("/departmentList")
    @ApiOperation("部门列表")
    public ApiRes<DepartmentListVo> departmentList() {
        return regionalManagerService.departmentList();
    }

    /**
     * 新建
     */
    @Log("新建部门")
    @PostMapping("/add")
    @RequiresPermissions("business:regionalManager:add")
    @ApiOperation("新建(business:regionalManager:add)")
    public ApiRes add(@RequestBody RegionalManagerAddDto regionalManagerAddDto) {
        return regionalManagerService.add(regionalManagerAddDto);
    }


    /**
     * 编辑
     */
    @Log("编辑部门")
    @PostMapping("/edit")
    @RequiresPermissions("business:regionalManager:edit")
    @ApiOperation("编辑(business:regionalManager:edit)")
    public ApiRes edit(@RequestBody RegionalManagerEditDto regionalManagerEditDto) {
        return regionalManagerService.edit(regionalManagerEditDto);
    }
}
