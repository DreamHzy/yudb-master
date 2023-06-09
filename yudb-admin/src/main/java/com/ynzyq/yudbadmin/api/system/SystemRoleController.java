package com.ynzyq.yudbadmin.api.system;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.biz.SystemRoleBiz;
import com.ynzyq.yudbadmin.core.model.ApiResponse;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.dto.CreateRoleMenuDTO;
import com.ynzyq.yudbadmin.dao.system.dto.CreateRolePermissionDTO;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemRoleDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemRole;
import com.ynzyq.yudbadmin.service.system.SystemRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色接口
 * @author Caesar Liu
 * @date 2021/03/27 22:36
 */
@RestController
@RequestMapping("/system/role")
@Api(tags = "系统角色接口")
public class SystemRoleController extends BaseController {

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    private SystemRoleBiz systemRoleBiz;

    /**
     * @author Caesar Liu
     * @date 2021-03-30 15:47
     */
    @Log("配置角色菜单")
    @RequiresPermissions("system:role:createRoleMenu")
    @PostMapping("/createRoleMenu")
    @ApiOperation("配置角色菜单")
    public ApiResponse createRoleMenu (@RequestBody CreateRoleMenuDTO dto) {
        dto.setCreateUser(this.getLoginUser().getId());
        systemRoleBiz.createRoleMenu(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021-03-29 22:36
     */
    @Log("配置角色权限")
    @RequiresPermissions("system:role:createRolePermission")
    @PostMapping("/createRolePermission")
    @ApiOperation("配置角色权限")
    public ApiResponse createRolePermission (@RequestBody CreateRolePermissionDTO dto) {
        dto.setCreateUser(this.getLoginUser().getId());
        systemRoleBiz.createRolePermission(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @Log("新建角色")
    @RequiresPermissions("system:role:create")
    @PostMapping("/create")
    @ApiOperation("新建")
    public ApiResponse create(@RequestBody SystemRole systemRole) {
        systemRole.setCreateUser(this.getLoginUser().getId());
        return ApiResponse.success(systemRoleService.create(systemRole));
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @Log("删除单个角色")
    @RequiresPermissions("system:role:delete")
    @GetMapping("/delete/{id}")
    @ApiOperation("根据ID删除")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemRoleService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @Log("批量删除角色")
    @RequiresPermissions("system:role:delete")
    @GetMapping("/delete/batch")
    @ApiOperation("批量删除")
    public ApiResponse deleteById(@RequestParam String ids) {
        String [] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemRoleService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @Log("修改角色")
    @RequiresPermissions("system:role:update")
    @PostMapping("/updateById")
    @ApiOperation("根据ID修改")
    public ApiResponse updateById(@RequestBody SystemRole systemRole) {
        systemRole.setUpdateUser(this.getLoginUser().getId());
        systemRoleService.updateById(systemRole);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:role:query")
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public ApiResponse findPage (@RequestBody PageWrap<QuerySystemRoleDTO> pageWrap) {
        return ApiResponse.success(systemRoleService.findPage(pageWrap));
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/27 22:36
     */
    @RequiresPermissions("system:role:query")
    @GetMapping("/all")
    @ApiOperation("查询所有")
    public ApiResponse findAll () {
        return ApiResponse.success(systemRoleService.findList(new SystemRole()));
    }
}