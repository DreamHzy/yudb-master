package com.ynzyq.yudbadmin.api.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dao.WalletOrderMapper;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.model.WalletOrder;
import com.ynzyq.yudbadmin.dao.business.vo.ListSettleStatisticsVO;
import com.ynzyq.yudbadmin.dao.business.vo.SettleStatisticsDetailVO;
import com.ynzyq.yudbadmin.service.business.IWalletService;
import com.ynzyq.yudbadmin.util.ylWallet.WalletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/6/16 10:21
 * @description:
 */
@Api(tags = "钱包管理")
@Slf4j
@RequestMapping("/wallet")
@RestController
public class WalletController {

    @Resource
    IWalletService iWalletService;

    @Resource
    WalletOrderMapper walletOrderMapper;

    /**
     * 获取随机因子
     *
     * @return
     */
    @ApiOperation("获取随机因子")
    @PostMapping("/getPlugRandKey")
    public ApiRes getPlugRandKey() {
        try {
            String plugRandKey = WalletUtil.getPlugRandKey();
            log.info("plugRandKey------------------------>", plugRandKey);
            return ApiRes.successResponseData(plugRandKey);
        } catch (IOException e) {
            log.error("获取随机因子异常", e);
        }
        return ApiRes.failResponse("获取失败");
    }

    /**
     * 结算列表
     *
     * @param pageWrap
     * @return
     */
    @ApiOperation("结算列表")
    @PostMapping("listSettleStatistics")
    public ApiRes<ListSettleStatisticsVO> listSettleStatistics(@RequestBody PageWrap<ListSettleStatisticsDTO> pageWrap) {
        return iWalletService.listSettleStatistics(pageWrap);
    }

    /**
     * 结算列表导出
     *
     * @param listSettleStatisticsDTO
     * @return
     */
    @GetMapping("/exportSettleStatistics")
    @ApiOperation("结算列表导出(business:wallet:exportSettleStatistics)")
    public void exportSettleStatistics(ListSettleStatisticsDTO listSettleStatisticsDTO, HttpServletResponse response) {
        iWalletService.exportSettleStatistics(listSettleStatisticsDTO, response);
    }

    /**
     * 结算详情
     *
     * @param settleStatisticsDetailDTO
     * @return
     */
    @ApiOperation("结算详情")
    @PostMapping("settleStatisticsDetail")
    public ApiRes<SettleStatisticsDetailVO> settleStatisticsDetail(@RequestBody SettleStatisticsDetailDTO settleStatisticsDetailDTO) {
        return iWalletService.settleStatisticsDetail(settleStatisticsDetailDTO);
    }

    /**
     * 结算列表结算
     *
     * @param settleOneDTO
     * @return
     */
    @ApiOperation("结算列表结算")
    @PostMapping("settleOne")
    public ApiRes settleOne(@RequestBody SettleOneDTO settleOneDTO) {
        if (settleOneDTO.getStatisticsIdList().size() == 0) {
            return ApiRes.failResponse("结算id不能为空");
        } else if (StringUtils.isBlank(settleOneDTO.getPassword())) {
            return ApiRes.failResponse("密码不能为空");
        } else if (StringUtils.isBlank(settleOneDTO.getPlugRandKey())) {
            return ApiRes.failResponse("随机因子不能为空");
        }
        Example example = new Example(WalletOrder.class);
        example.createCriteria()
                .andIn("statisticsId", settleOneDTO.getStatisticsIdList())
                .andNotEqualTo("settleStatus", 1);
        List<WalletOrder> walletOrderList = walletOrderMapper.selectByExample(example);
        if (walletOrderList.size() == 0) {
            return ApiRes.failResponse("没有需要结算的订单，请刷新列表");
        }
        return iWalletService.settle(settleOneDTO.getPassword(), settleOneDTO.getPlugRandKey(), walletOrderList);
    }

    /**
     * 结算详情里面结算
     *
     * @param settleTwoDTO
     * @return
     */
    @ApiOperation("结算详情里面结算")
    @PostMapping("settleTwo")
    public ApiRes settleTwo(@RequestBody SettleTwoDTO settleTwoDTO) {
        if (settleTwoDTO.getOrderIdList() == null || settleTwoDTO.getOrderIdList().size() == 0) {
            return ApiRes.failResponse("请选择结算订单");
        } else if (StringUtils.isBlank(settleTwoDTO.getPassword())) {
            return ApiRes.failResponse("密码不能为空");
        } else if (StringUtils.isBlank(settleTwoDTO.getPlugRandKey())) {
            return ApiRes.failResponse("随机因子不能为空");
        }
        Example example = new Example(WalletOrder.class);
        example.createCriteria()
                .andIn("id", settleTwoDTO.getOrderIdList())
                .andNotEqualTo("settleStatus", 1);
        List<WalletOrder> walletOrderList = walletOrderMapper.selectByExample(example);
        if (walletOrderList.size() == 0) {
            return ApiRes.failResponse("没有需要结算的订单，请刷新列表");
        }
        return iWalletService.settle(settleTwoDTO.getPassword(), settleTwoDTO.getPlugRandKey(), walletOrderList);
    }

    /**
     * 结算列表金额计算
     *
     * @param calcOneDTO
     * @return
     */
    @ApiOperation("结算列表金额计算")
    @PostMapping("calcOne")
    public ApiRes calcOne(@RequestBody CalcOneDTO calcOneDTO) {
        if (calcOneDTO.getStatisticsIdList().size() == 0) {
            return ApiRes.failResponse("结算id不能为空");
        }
        Example example = new Example(WalletOrder.class);
        example.createCriteria()
                .andIn("statisticsId", calcOneDTO.getStatisticsIdList())
                .andNotEqualTo("settleStatus", 1);
        List<WalletOrder> walletOrderList = walletOrderMapper.selectByExample(example);
        if (walletOrderList.size() == 0) {
            return ApiRes.failResponse("没有需要结算的订单，请刷新列表");
        }
        BigDecimal money = walletOrderList.stream().map(WalletOrder::getOrderMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        return ApiRes.successResponseData(money);
    }

    /**
     * 结算详情里面金额计算
     *
     * @param calcTwoDTO
     * @return
     */
    @ApiOperation("结算详情里面金额计算")
    @PostMapping("calcTwo")
    public ApiRes calcTwo(@RequestBody CalcTwoDTO calcTwoDTO) {
        if (calcTwoDTO.getOrderIdList() == null || calcTwoDTO.getOrderIdList().size() == 0) {
            return ApiRes.failResponse("请选择结算订单");
        }
        Example example = new Example(WalletOrder.class);
        example.createCriteria()
                .andIn("id", calcTwoDTO.getOrderIdList())
                .andNotEqualTo("settleStatus", 1);
        List<WalletOrder> walletOrderList = walletOrderMapper.selectByExample(example);
        if (walletOrderList.size() == 0) {
            return ApiRes.failResponse("没有需要结算的订单，请刷新列表");
        }
        BigDecimal money = walletOrderList.stream().map(WalletOrder::getOrderMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        return ApiRes.successResponseData(money);
    }
}
