package com.ynzyq.yudbadmin.third.api;

import com.alibaba.fastjson.JSON;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.third.dto.ModifyStoreStatusDTO;
import com.ynzyq.yudbadmin.third.dto.ModifyStoreStatusTwoDTO;
import com.ynzyq.yudbadmin.third.dto.QuerySettleStatusDTO;
import com.ynzyq.yudbadmin.third.dto.TransferOrderDTO;
import com.ynzyq.yudbadmin.third.service.IForeignService;
import com.ynzyq.yudbadmin.third.vo.QuerySettleStatusVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author xinchen
 * @date 2021/12/2 17:30
 * @description: 对外提供三方请求接口类
 */
@Slf4j
@RestController
public class ForeignServiceApi {

    @Resource
    IForeignService iForeignService;

    /**
     * 更新门店状态
     *
     * @param modifyStoreStatusDTO
     * @return
     */
    @PostMapping("/yudb/modifyStoreStatus")
    public ApiRes modifyStoreStatus(@RequestBody ModifyStoreStatusDTO modifyStoreStatusDTO) {
        log.info("【金蝶修改门店状态】请求参数：{}", JSON.toJSONString(modifyStoreStatusDTO));
        return iForeignService.modifyStoreStatus(modifyStoreStatusDTO);
    }

    /**
     * 更新
     *
     * @param modifyStoreStatusTwoDTO
     * @return
     */
    @PostMapping("/yudb/modifyStoreStatus/V2")
    public ApiRes modifyStoreStatusV2(@RequestBody ModifyStoreStatusTwoDTO modifyStoreStatusTwoDTO) {
        log.info("【金蝶修改门店状态2】请求参数：{}", JSON.toJSONString(modifyStoreStatusTwoDTO));
        return iForeignService.modifyStoreStatusV2(modifyStoreStatusTwoDTO);
    }

    /**
     * 同步订单
     *
     * @param transferOrderDTO
     * @return
     */
    @PostMapping("/yudb/transferOrder")
    public ApiRes transferOrder(@RequestBody @Valid TransferOrderDTO transferOrderDTO) {
        log.info("【同步订单】请求参数：{}", JSON.toJSONString(transferOrderDTO));
        return iForeignService.transferOrder(transferOrderDTO);
    }

    /**
     * 查询订单结算状态
     *
     * @param querySettleStatusDTO
     * @return
     */
    @PostMapping("/yudb/querySettleStatus")
    public ApiRes<QuerySettleStatusVO> querySettleStatus(@RequestBody @Valid QuerySettleStatusDTO querySettleStatusDTO) {
        log.info("【查询订单结算状态】请求参数：{}", JSON.toJSONString(querySettleStatusDTO));
        return iForeignService.querySettleStatus(querySettleStatusDTO);
    }
}
