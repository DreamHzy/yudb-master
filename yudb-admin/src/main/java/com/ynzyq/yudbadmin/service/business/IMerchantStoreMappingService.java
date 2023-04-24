package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.LevelVO;
import com.ynzyq.yudbadmin.dao.business.vo.OrderGoodsSelectBoxVO;
import com.ynzyq.yudbadmin.dao.business.vo.StoreMappingVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xinchen
 * @date 2021/11/18 11:11
 * @description:
 */
public interface IMerchantStoreMappingService {
    /**
     * 门店映射
     *
     * @param pageWrap
     * @return
     */
    ApiRes<StoreMappingVO> listStoreMapping(PageWrap<StoreMappingDTO> pageWrap);

    /**
     * 代理商下拉框
     *
     * @return
     */
    ApiRes agentSelectBox();

    /**
     * 区域经理下拉框
     *
     * @return
     */
    ApiRes managerSelectBox();

    /**
     * 代理商划拨
     *
     * @param agentTransferAreaDTO
     * @return
     */
    ApiRes agentTransferArea(AgentTransferAreaDTO agentTransferAreaDTO);

    /**
     * 区域经理划拨
     *
     * @param managerTransferAreaDTO
     * @return
     */
    ApiRes managerTransferArea(ManagerTransferAreaDTO managerTransferAreaDTO);

    /**
     * 导出
     *
     * @param response
     * @param storeMappingDTO
     */
    void exportStoreMapping(StoreMappingDTO storeMappingDTO, HttpServletResponse response);

    /**
     * 修改线级城市
     *
     * @param levelDTO
     * @return
     */
    ApiRes updateLevel(LevelDTO levelDTO);

    /**
     * 线级城市下拉框
     *
     * @return
     */
    ApiRes<LevelVO> levelSelectBox();

    /**
     * 修改是否映射
     *
     * @param updateIsMappingDTO
     * @return
     */
    ApiRes updateIsMapping(UpdateIsMappingDTO updateIsMappingDTO);

    /**
     * 批量修改代仓/限单配置/供应商匹配规则
     *
     * @param updateWarehouseDTO
     * @return
     */
    ApiRes updateOrderGoods(UpdateWarehouseDTO updateWarehouseDTO);

    /**
     * 代仓/限单配置/供应商匹配规则下拉框
     *
     * @return
     * @param orderGoodsSelectBoxDTO
     */
    ApiRes<OrderGoodsSelectBoxVO> orderGoodsSelectBox(OrderGoodsSelectBoxDTO orderGoodsSelectBoxDTO);

}
