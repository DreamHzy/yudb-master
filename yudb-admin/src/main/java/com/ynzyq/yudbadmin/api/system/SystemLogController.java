package com.ynzyq.yudbadmin.api.system;

import com.ynzyq.yudbadmin.api.BaseController;
import com.ynzyq.yudbadmin.core.model.ApiResponse;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemLogDTO;
import com.ynzyq.yudbadmin.dao.system.vo.SystemLogListVO;
import com.ynzyq.yudbadmin.service.system.SystemLogService;
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

@Api(tags = "日志接口")
@Slf4j
@RequestMapping("/system/log")
@RestController
public class SystemLogController extends BaseController {


    @Resource
    SystemLogService systemUserService;

    /**
     * @author Caesar Liu
     * @date 2021/03/28 09:30
     */
    @RequiresPermissions("system:log:query")
    @PostMapping("/page")
    @ApiOperation("分页查询")
    @ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = SystemLogListVO.class)
    )
    public ApiResponse findPage(@RequestBody PageWrap<QuerySystemLogDTO> pageWrap) {
        return ApiResponse.success(systemUserService.findPage(pageWrap));
    }
}
