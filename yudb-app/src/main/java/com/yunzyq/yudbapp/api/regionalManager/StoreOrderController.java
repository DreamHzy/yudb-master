package com.yunzyq.yudbapp.api.regionalManager;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.ListOrderDTO;
import com.yunzyq.yudbapp.dao.dto.OrderDetailDTO;
import com.yunzyq.yudbapp.dao.vo.ListOrderVO;
import com.yunzyq.yudbapp.dao.vo.OrderDetailVO;
import com.yunzyq.yudbapp.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author xinchen
 * @date 2022/4/15 18:28
 * @description:
 */
@Api(tags = "门店订单")
@Slf4j
@RequestMapping("/order")
@RestController
public class StoreOrderController {

    @Resource
    IOrderService iOrderService;

    /**
     * 订单列表
     *
     * @param pageWrap
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("订单列表")
    @PostMapping("/listOrder")
    public ApiRes<ListOrderVO> listOrder(@RequestBody @Valid PageWrap<ListOrderDTO> pageWrap, HttpServletRequest httpServletRequest) {
        return iOrderService.listOrder(pageWrap, httpServletRequest);
    }

    /**
     * 订单详情
     *
     * @param orderDetailDTO
     * @param httpServletRequest
     * @return
     */
    @ApiOperation("订单详情")
    @PostMapping("/orderDetail")
    public ApiRes<OrderDetailVO> orderDetail(@RequestBody @Valid OrderDetailDTO orderDetailDTO, HttpServletRequest httpServletRequest) {
        return iOrderService.orderDetail(orderDetailDTO, httpServletRequest);
    }
}
