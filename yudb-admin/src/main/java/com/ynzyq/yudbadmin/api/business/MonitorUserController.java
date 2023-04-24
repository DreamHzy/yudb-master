package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.BannerStatus;
import com.ynzyq.yudbadmin.dao.business.dto.MonitorUserAddDto;
import com.ynzyq.yudbadmin.dao.business.dto.MonitorUserStatusDto;
import com.ynzyq.yudbadmin.dao.business.vo.BannerPageVo;
import com.ynzyq.yudbadmin.dao.business.vo.MonitorUserPageVo;
import com.ynzyq.yudbadmin.service.business.MonitorUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "(新)报警通知手机号管理")
@Slf4j
@RequestMapping("/monitorUser")
@RestController
public class MonitorUserController {

    @Resource
    MonitorUserService monitorUserService;

    /**
     * 列表
     */
    @RequiresPermissions("business:monitorUser:page")
    @PostMapping("/page")
    @ApiOperation("列表")
    public ApiRes<MonitorUserPageVo> findPage(@RequestBody PageWrap pageWrap) {
        return monitorUserService.findPage(pageWrap);
    }

    /**
     * 新建
     */
    @RequiresPermissions("business:monitorUser:add")
    @PostMapping("/add")
    @ApiOperation("新建")
    public ApiRes add(@RequestBody MonitorUserAddDto monitorUserAddDto, HttpServletRequest httpServletRequest) {
        return monitorUserService.add(monitorUserAddDto, httpServletRequest);
    }

    /**
     * 状态
     */
    @RequiresPermissions("business:monitorUser:updatStatus")
    @PostMapping("/updatStatus")
    @ApiOperation("状态")
    public ApiRes updatStatus(@RequestBody MonitorUserStatusDto monitorUserStatusDto) {
        return monitorUserService.updatStatus(monitorUserStatusDto);
    }
}
