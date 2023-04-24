package com.yunzyq.yudbapp.api.regionalManager;

import com.yunzyq.yudbapp.api.BaseController;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.dao.vo.IndexRegionalManagerVo;
import com.yunzyq.yudbapp.service.RegionalManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "区域经理首页")
@Slf4j
@RequestMapping("/regionalManager")
@RestController
public class RegionalManagerController extends BaseController {

    @Resource
    RegionalManagerService regionalManagerService;

    /**
     * 首页
     */
    @ApiOperation("首页")
    @PostMapping("/index")
    public ApiRes<IndexRegionalManagerVo> index(HttpServletRequest httpServletRequest) {
        return regionalManagerService.index(httpServletRequest);
    }
}
