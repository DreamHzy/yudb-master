package com.yunzyq.yudbapp.api.regionalManager;

import com.alibaba.fastjson.JSON;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.vo.ListAgentPaymentTypeVO;
import com.yunzyq.yudbapp.dao.dto.HistoryOrderDto;
import com.yunzyq.yudbapp.dao.dto.ListMyAgentDTO;
import com.yunzyq.yudbapp.dao.dto.MakeOutBillDTO;
import com.yunzyq.yudbapp.dao.vo.HistoryOrderVo;
import com.yunzyq.yudbapp.dao.vo.ListMyAgentVO;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.IMyAgentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author wj
 * @Date 2021/10/25
 */
@Api(tags = "区域经理我的代理")
@Slf4j
@RequestMapping("/myAgent")
@RestController
public class MyAgentController {

    private static final String LOGGER_PREFIX = "【区域经理-我的代理】";

    @Resource
    RedisUtils redisUtils;

    @Resource
    IMyAgentService iMyAgentService;

    /**
     * 我的代理列表
     *
     * @param listMyAgentDTO     查询条件
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("我的代理列表")
    @PostMapping("/listMyAgent")
    public ApiRes<ListMyAgentVO> listMyAgent(@RequestBody ListMyAgentDTO listMyAgentDTO, HttpServletRequest httpServletRequest) {
        log.info("{}代理列表请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(listMyAgentDTO));
        String managerId = redisUtils.token(httpServletRequest);
        return iMyAgentService.listMyAgent(managerId, listMyAgentDTO);
    }

    /**
     * 缴费类型下拉框
     *
     * @return
     */
    @ApiOperation("缴费类型下拉框")
    @PostMapping("/listAgentPaymentType")
    public ApiRes<ListAgentPaymentTypeVO> listMyAgent() {
        return iMyAgentService.listAgentPaymentType();
    }

    /**
     * 开缴费单
     *
     * @param makeOutBillDTO
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/makeOutBill")
    @ApiOperation("开缴费单")
    public ApiRes makeOutBill(@RequestBody @Valid MakeOutBillDTO makeOutBillDTO, HttpServletRequest httpServletRequest) {
        log.info("{}开缴费单请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(makeOutBillDTO));
        String managerId = redisUtils.token(httpServletRequest);
        return iMyAgentService.makeOutBill(managerId, makeOutBillDTO);
    }

    /**
     * 历史开单
     *
     * @param pageWrap
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/historyBill")
    @ApiOperation("历史开单")
    public ApiRes<PageWrap<HistoryOrderVo>> historyBill(@RequestBody PageWrap<HistoryOrderDto> pageWrap, HttpServletRequest httpServletRequest) {
        log.info("{}历史开单请求参数：{}", LOGGER_PREFIX, JSON.toJSONString(pageWrap));
        String managerId = redisUtils.token(httpServletRequest);
        return iMyAgentService.historyBill(managerId, pageWrap);
    }

}
