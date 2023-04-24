package com.ynzyq.yudbadmin.api.system;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.biz.SystemUserBiz;
import com.ynzyq.yudbadmin.core.model.ApiResponse;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.dto.CreateUserRoleDTO;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemUserDTO;
import com.ynzyq.yudbadmin.dao.system.dto.ResetSystemUserPwdDTO;
import com.ynzyq.yudbadmin.dao.system.model.SystemUser;
import com.ynzyq.yudbadmin.service.system.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户接口
 *
 * @author Caesar Liu
 * @date 2021/03/28 09:30
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "系统用户接口")
public class SystemUserController extends BaseController {

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemUserBiz systemUserBiz;

    /**
     * @author Caesar Liu
     * @date 2021-03-29 22:36
     */
    @Log("配置用户角色")
    @RequiresPermissions("system:user:createUserRole")
    @PostMapping("/createUserRole")
    @ApiOperation("配置用户角色")
    public ApiResponse createUserRole(@RequestBody CreateUserRoleDTO dto) {
        dto.setCreateUser(this.getLoginUser().getId());
        systemUserBiz.createUserRole(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021-03-31 20:25
     */
    @Log("重置密码")
    @RequiresPermissions("system:user:resetPwd")
    @PostMapping("/resetPwd")
    @ApiOperation("重置密码")
    public ApiResponse resetPwd(@RequestBody ResetSystemUserPwdDTO dto) {
        dto.setOperaUserId(this.getLoginUser().getId());
        systemUserBiz.resetPwd(dto);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @Log("新建用户")
    @RequiresPermissions("system:user:create")
    @PostMapping("/create")
    @ApiOperation("新建")
    public ApiResponse create(@RequestBody SystemUser systemUser) {
        systemUser.setCreateUser(this.getLoginUser().getId());
        systemUserBiz.create(systemUser);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @Log("删除用户")
    @RequiresPermissions("system:user:delete")
    @GetMapping("/delete/{id}")
    @ApiOperation("根据ID删除")
    public ApiResponse deleteById(@PathVariable Integer id) {
        systemUserService.deleteById(id);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @Log("批量删除用户")
    @RequiresPermissions("system:user:delete")
    @GetMapping("/delete/batch")
    @ApiOperation("批量删除")
    public ApiResponse deleteById(@RequestParam String ids) {
        String[] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String id : idArray) {
            idList.add(Integer.valueOf(id));
        }
        systemUserService.deleteByIdInBatch(idList);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @Log("修改用户")
    @RequiresPermissions("system:user:update")
    @PostMapping("/updateById")
    @ApiOperation("根据ID修改")
    public ApiResponse updateById(@RequestBody SystemUser systemUser) {
        systemUser.setUpdateUser(this.getLoginUser().getId());
        systemUserBiz.updateById(systemUser);
        return ApiResponse.success(null);
    }

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @RequiresPermissions("system:user:query")
    @PostMapping("/page")
    @ApiOperation("分页查询")
    public ApiResponse findPage(@RequestBody PageWrap<QuerySystemUserDTO> pageWrap) {
        return ApiResponse.success(systemUserService.findPage(pageWrap));
    }


}