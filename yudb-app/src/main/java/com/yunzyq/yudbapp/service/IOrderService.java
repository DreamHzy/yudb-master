package com.yunzyq.yudbapp.service;


import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.dto.ListOrderDTO;
import com.yunzyq.yudbapp.dao.dto.OrderDetailDTO;
import com.yunzyq.yudbapp.dao.vo.ListOrderVO;
import com.yunzyq.yudbapp.dao.vo.OrderDetailVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xinchen
 * @date 2022/2/25 14:41
 * @description:
 */
public interface IOrderService {
    /**
     * 订单列表
     *
     * @param pageWrap
     * @param httpServletRequest
     * @return
     */
    ApiRes<ListOrderVO> listOrder(PageWrap<ListOrderDTO> pageWrap, HttpServletRequest httpServletRequest);

    /**
     * 订单详情
     *
     * @param orderDetailDTO
     * @param httpServletRequest
     * @return
     */
    ApiRes<OrderDetailVO> orderDetail(OrderDetailDTO orderDetailDTO, HttpServletRequest httpServletRequest);
}
