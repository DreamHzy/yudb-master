package com.yunzyq.yudbapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.CommonConstant;
import com.yunzyq.yudbapp.core.PageData;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.AppOrderMapper;
import com.yunzyq.yudbapp.dao.dto.ListOrderDTO;
import com.yunzyq.yudbapp.dao.dto.OrderDetailDTO;
import com.yunzyq.yudbapp.dao.vo.*;
import com.yunzyq.yudbapp.enums.OrderStatusEnum;
import com.yunzyq.yudbapp.enums.OrderTypeEnum;
import com.yunzyq.yudbapp.enums.PayWayEnum;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author xinchen
 * @date 2022/2/25 14:43
 * @description:
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Value("${imageUrl}")
    private String imageUrl;

    @Resource
    RedisUtils redisUtils;

    @Resource
    AppOrderMapper appOrderMapper;

    @Override
    public ApiRes<ListOrderVO> listOrder(PageWrap<ListOrderDTO> pageWrap, HttpServletRequest httpServletRequest) {
        String managerId = redisUtils.token(httpServletRequest);
        ListOrderDTO listOrderDTO = pageWrap.getModel();
        listOrderDTO.setManagerId(Integer.parseInt(managerId));
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<ListOrderVO> listOrderVOS = appOrderMapper.listOrder(listOrderDTO);
        listOrderVOS.forEach(listOrderVO -> {
            List<GoodsImage> goodsImages = appOrderMapper.goodsImages(Integer.parseInt(listOrderVO.getId()));
            if (!CollectionUtils.isEmpty(goodsImages) && goodsImages.size() > 0) {
                goodsImages.stream().forEach(goodsImage -> {
                    if (StringUtils.isNotBlank(goodsImage.getUrl())) {
                        goodsImage.setUrl(imageUrl + goodsImage.getUrl());
                    }
                });
            }
            listOrderVO.setImages(goodsImages);
            // 配送审核：订单类型=二配 && 订单状态=待付款 && 审核状态=待审核
            // 待审核：订单状态=进行中(支付完成) && 审核状态=已审核 && 配置状态=未配置(商品未推送至金蝶)
            if ((OrderTypeEnum.SECOND_PAIR.getType().equals(listOrderVO.getType()) && OrderStatusEnum.PENDING_PAY.getStatus().equals(Integer.parseInt(listOrderVO.getStatus())) && listOrderVO.getAuditStatus().equals(0))
                    || (OrderStatusEnum.IN_PROGRESS.getStatus().equals(Integer.parseInt(listOrderVO.getStatus())) && listOrderVO.getAuditStatus().equals(1) && listOrderVO.getIsConfig().equals(2))) {
                listOrderVO.setStatus("待审核");
            } else {
                listOrderVO.setStatus(OrderStatusEnum.getStatusDesc(Integer.parseInt(listOrderVO.getStatus())));
            }
        });
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", PageData.from(new PageInfo<>(listOrderVOS)));
    }

    @Override
    public ApiRes<OrderDetailVO> orderDetail(OrderDetailDTO orderDetailDTO, HttpServletRequest httpServletRequest) {
        String merchantId = redisUtils.token(httpServletRequest);
        OrderDetailVO orderDetailVO = appOrderMapper.orderDetail(Integer.parseInt(orderDetailDTO.getId()));
        // 配送审核：订单类型=二配 && 订单状态=待付款 && 审核状态=待审核
        // 待审核：订单状态=进行中(支付完成) && 审核状态=已审核 && 配置状态=未配置(商品未推送至金蝶)
        if ((OrderTypeEnum.SECOND_PAIR.getType().equals(Integer.parseInt(orderDetailVO.getType())) && OrderStatusEnum.PENDING_PAY.getStatus().equals(Integer.parseInt(orderDetailVO.getStatus())) && orderDetailVO.getAuditStatus().equals(0))
                || (OrderStatusEnum.IN_PROGRESS.getStatus().equals(Integer.parseInt(orderDetailVO.getStatus())) && orderDetailVO.getAuditStatus().equals(1) && orderDetailVO.getIsConfig().equals(2))) {
            orderDetailVO.setStatus("待审核");
        } else {
            orderDetailVO.setStatus(OrderStatusEnum.getStatusDesc(Integer.parseInt(orderDetailVO.getStatus())));
        }
        List<Integer> payWayList = appOrderMapper.getPayWay(Integer.parseInt(orderDetailDTO.getId()));
        StringJoiner sj = new StringJoiner(",");
        payWayList.forEach(payWay -> {
            sj.add(PayWayEnum.getPayWayDesc(payWay));
        });
        orderDetailVO.setPayWay(sj.toString());
        List<CategoryInfo> categoryInfos = appOrderMapper.orderGoodsCategoryInfo(Integer.parseInt(orderDetailDTO.getId()));
        List<OrderGoods> orderGoodsList = Lists.newArrayList();
        categoryInfos.forEach(categoryInfo -> {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setCategory(categoryInfo.getName());
            BigDecimal money = appOrderMapper.orderDetailGoodsMoney(Integer.parseInt(orderDetailDTO.getId()), Integer.parseInt(categoryInfo.getId()));
            orderGoods.setMoney(money.toString());
            List<OrderDetailGoods> orderDetailGoodsList = appOrderMapper.orderDetailGoods(Integer.parseInt(orderDetailDTO.getId()), Integer.parseInt(merchantId), Integer.parseInt(categoryInfo.getId()));
            orderDetailGoodsList.forEach(orderDetailGoods -> {
                if (StringUtils.isNotBlank(orderDetailGoods.getImage())) {
                    orderDetailGoods.setImage(imageUrl + orderDetailGoods.getImage());
                }
            });
            orderGoods.setOrderGoodsInfos(orderDetailGoodsList);
            orderGoodsList.add(orderGoods);
        });
        orderDetailVO.setOrderDetailGoodsList(orderGoodsList);
        return ApiRes.response(CommonConstant.SUCCESS_CODE, "成功", orderDetailVO);
    }

}
