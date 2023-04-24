package com.yunzyq.yudbapp.api.merchant;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.dao.dto.YGshangchengDto;
import com.yunzyq.yudbapp.dao.vo.IndexRegionalManagerVo;
import com.yunzyq.yudbapp.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author WJ
 */
@Api(tags = "加盟商首页")
@Slf4j
@RequestMapping("/merchant")
@RestController
public class MerchantController {


    @Resource
    MerchantService merchantService;

    /**
     * 首页
     */
    @ApiOperation("首页")
    @PostMapping("/index")
    public ApiRes<IndexRegionalManagerVo> index(HttpServletRequest httpServletRequest) {
        return merchantService.index(httpServletRequest);
    }


    @ApiOperation("阳光商城模拟登陆接口")
    @PostMapping("/YGshangcheng")
    public ApiRes YGshangcheng(@RequestBody YGshangchengDto yGshangchengDto, HttpServletRequest httpServletRequest) {
        return merchantService.YGshangcheng(httpServletRequest, yGshangchengDto);

    }
}
