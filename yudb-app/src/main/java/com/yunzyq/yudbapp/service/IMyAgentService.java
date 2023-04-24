package com.yunzyq.yudbapp.service;

import com.yunzyq.yudbapp.core.ApiRes;
import com.yunzyq.yudbapp.core.PageWrap;
import com.yunzyq.yudbapp.dao.vo.ListAgentPaymentTypeVO;
import com.yunzyq.yudbapp.dao.dto.HistoryOrderDto;
import com.yunzyq.yudbapp.dao.dto.ListMyAgentDTO;
import com.yunzyq.yudbapp.dao.dto.MakeOutBillDTO;
import com.yunzyq.yudbapp.dao.vo.HistoryOrderVo;
import com.yunzyq.yudbapp.dao.vo.ListMyAgentVO;

/**
 * @Author wj
 * @Date 2021/10/25
 */
public interface IMyAgentService {
    /**
     * 我的代理列表
     *
     * @param managerId
     * @param listMyAgentDTO
     * @return
     */
    ApiRes<ListMyAgentVO> listMyAgent(String managerId, ListMyAgentDTO listMyAgentDTO);

    /**
     * 缴费类型下拉框
     *
     * @return
     */
    ApiRes<ListAgentPaymentTypeVO> listAgentPaymentType();

    /**
     * 开缴费单
     *
     * @param managerId
     * @param makeOutBillDTO
     * @return
     */
    ApiRes makeOutBill(String managerId, MakeOutBillDTO makeOutBillDTO);

    /**
     * 历史开单
     *
     * @param managerId
     * @param pageWrap
     * @return
     */
    ApiRes<PageWrap<HistoryOrderVo>> historyBill(String managerId, PageWrap<HistoryOrderDto> pageWrap);
}
