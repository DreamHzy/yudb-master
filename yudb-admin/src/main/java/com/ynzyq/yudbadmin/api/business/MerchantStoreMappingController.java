package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.annotation.Log;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.LevelVO;
import com.ynzyq.yudbadmin.dao.business.vo.OrderGoodsSelectBoxVO;
import com.ynzyq.yudbadmin.dao.business.vo.StoreMappingVO;
import com.ynzyq.yudbadmin.service.business.IMerchantStoreMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author xinchen
 * @date 2021/11/18 11:11
 * @description:
 */
@Api(tags = "门店映射表")
@Slf4j
@RestController
public class MerchantStoreMappingController {

    @Resource
    IMerchantStoreMappingService iMerchantStoreMappingService;

    /**
     * 门店基础映射
     *
     * @param pageWrap
     * @return
     */
    @PostMapping("/storeMapping/listStoreMapping")
    @ApiOperation("门店基础映射")
    public ApiRes<StoreMappingVO> listStoreMapping(@RequestBody PageWrap<StoreMappingDTO> pageWrap) {
        return iMerchantStoreMappingService.listStoreMapping(pageWrap);
    }

    /**
     * 代理商下拉框
     *
     * @return
     */
    @PostMapping("/storeMapping/agentSelectBox")
    @ApiOperation("代理商下拉框")
    public ApiRes agentSelectBox() {
        return iMerchantStoreMappingService.agentSelectBox();
    }

    /**
     * 区域经理下拉框
     *
     * @return
     */
    @PostMapping("/storeMapping/managerSelectBox")
    @ApiOperation("区域经理下拉框")
    public ApiRes managerSelectBox() {
        return iMerchantStoreMappingService.managerSelectBox();
    }

    /**
     * 代理商划拨
     *
     * @param agentTransferAreaDTO
     * @return
     */
    @PostMapping("/storeMapping/agentTransferArea")
    @RequiresPermissions("store:mapping:agentTransferArea")
    @ApiOperation("代理商划拨区域(store:mapping:agentTransferArea)")
    public ApiRes agentTransferArea(@RequestBody @Valid AgentTransferAreaDTO agentTransferAreaDTO) {
        return iMerchantStoreMappingService.agentTransferArea(agentTransferAreaDTO);
    }

    /**
     * 区域经理划拨
     *
     * @param managerTransferAreaDTO
     * @return
     */
    @PostMapping("/storeMapping/managerTransferArea")
    @RequiresPermissions("store:mapping:managerTransferArea")
    @ApiOperation("区域经理划拨(store:mapping:managerTransferArea)")
    public ApiRes managerTransferArea(@RequestBody @Valid ManagerTransferAreaDTO managerTransferAreaDTO) {
        return iMerchantStoreMappingService.managerTransferArea(managerTransferAreaDTO);
    }

    /**
     * 导出
     *
     * @param storeMappingDTO
     * @return
     */
    @GetMapping("/storeMapping/exportStoreMapping")
    @ApiOperation("导出")
    public void exportStoreMapping(StoreMappingDTO storeMappingDTO, HttpServletResponse response) {
        iMerchantStoreMappingService.exportStoreMapping(storeMappingDTO, response);
    }

    /**
     * 修改线级城市
     *
     * @param levelDTO
     * @return
     */
    @PostMapping("/storeMapping/updateLevel")
    @ApiOperation("修改线级城市")
    public ApiRes updateLevel(@RequestBody @Valid LevelDTO levelDTO) {
        return iMerchantStoreMappingService.updateLevel(levelDTO);
    }

    /**
     * 线级城市下拉框
     *
     * @return
     */
    @PostMapping("/storeMapping/levelSelectBox")
    @ApiOperation("线级城市下拉框")
    public ApiRes<LevelVO> levelSelectBox() {
        return iMerchantStoreMappingService.levelSelectBox();
    }

    /**
     * 修改是否映射
     *
     * @param updateIsMappingDTO
     * @return
     */
    @RequiresPermissions("store:mapping:updateIsMapping")
    @PostMapping("/storeMapping/updateIsMapping")
    @ApiOperation("修改是否映射(store:mapping:updateIsMapping)")
    public ApiRes updateIsMapping(@RequestBody @Valid UpdateIsMappingDTO updateIsMappingDTO) {
        return iMerchantStoreMappingService.updateIsMapping(updateIsMappingDTO);
    }

    /**
     * 批量修改代仓/限单配置/供应商匹配规则
     *
     * @param updateWarehouseDTO
     * @return
     */
    @Log("批量修改代仓/限单配置/供应商匹配规则")
    @PostMapping("/storeMapping/updateOrderGoods")
    @RequiresPermissions("store:mapping:updateOrderGoods")
    @ApiOperation("批量修改代仓/限单配置/供应商匹配规则(store:mapping:updateOrderGoods)")
    public ApiRes updateOrderGoods(@RequestBody @Valid UpdateWarehouseDTO updateWarehouseDTO) {
        return iMerchantStoreMappingService.updateOrderGoods(updateWarehouseDTO);
    }

    /**
     * 代仓/限单配置/供应商匹配规则下拉框
     *
     * @param orderGoodsSelectBoxDTO
     * @return
     */
    @PostMapping("/storeMapping/orderGoodsSelectBox")
    @ApiOperation("代仓/限单配置/供应商匹配规则下拉框")
    public ApiRes<OrderGoodsSelectBoxVO> orderGoodsSelectBox(@RequestBody @Valid OrderGoodsSelectBoxDTO orderGoodsSelectBoxDTO) {
        return iMerchantStoreMappingService.orderGoodsSelectBox(orderGoodsSelectBoxDTO);
    }
}
