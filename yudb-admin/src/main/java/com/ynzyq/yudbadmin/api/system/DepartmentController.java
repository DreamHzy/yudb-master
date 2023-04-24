package com.ynzyq.yudbadmin.api.system;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.ApiResponse;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.DepartmentAdd;
import com.ynzyq.yudbadmin.dao.business.dto.DepartmentPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.DepartmentUpdateDto;
import com.ynzyq.yudbadmin.dao.business.vo.DepartmentPageVo;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemLogDTO;
import com.ynzyq.yudbadmin.dao.system.vo.SystemLogListVO;
import com.ynzyq.yudbadmin.service.business.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "(新)系统部门管理")
@Slf4j
@RequestMapping("/department")
@RestController
public class DepartmentController {

    @Resource
    DepartmentService departmentService;


    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @RequiresPermissions("system:department:query")
    @PostMapping("/page")
    @ApiOperation("部门列表查询")
    public ApiRes<DepartmentPageVo> findPage() {
        return departmentService.findPage();
    }

    @RequiresPermissions("system:department:add")
    @PostMapping("/add")
    @ApiOperation("新建部门(system:department:add)")
    public ApiRes add(@RequestBody DepartmentAdd departmentAdd) {
        return departmentService.add(departmentAdd);
    }

    @RequiresPermissions("system:department:update")
    @PostMapping("/update")
    @ApiOperation("修改部门(system:department:update)")
    public ApiRes update(@RequestBody DepartmentUpdateDto departmentUpdateDto) {
        return departmentService.update(departmentUpdateDto);
    }


}
