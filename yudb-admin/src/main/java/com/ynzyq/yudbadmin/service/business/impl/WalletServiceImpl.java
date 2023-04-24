package com.ynzyq.yudbadmin.service.business.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dao.MerchantStoreMapper;
import com.ynzyq.yudbadmin.dao.business.dao.WalletMapper;
import com.ynzyq.yudbadmin.dao.business.dao.WalletOrderMapper;
import com.ynzyq.yudbadmin.dao.business.dto.ListSettleStatisticsDTO;
import com.ynzyq.yudbadmin.dao.business.dto.SettleStatisticsDetailDTO;
import com.ynzyq.yudbadmin.dao.business.model.MerchantStore;
import com.ynzyq.yudbadmin.dao.business.model.WalletOrder;
import com.ynzyq.yudbadmin.dao.business.vo.ListSettleStatisticsVO;
import com.ynzyq.yudbadmin.dao.business.vo.SettleStatisticsDetailVO;
import com.ynzyq.yudbadmin.enums.SettleStatusEnum;
import com.ynzyq.yudbadmin.enums.StatisticsSettleStatusEnum;
import com.ynzyq.yudbadmin.service.business.IWalletService;
import com.ynzyq.yudbadmin.util.ExcelUtil;
import com.ynzyq.yudbadmin.util.PlatformCodeUtil;
import com.ynzyq.yudbadmin.util.ylWallet.WalletUtil;
import com.ynzyq.yudbadmin.util.ylWallet.parm.BatchTransferListParam;
import com.ynzyq.yudbadmin.util.ylWallet.parm.BatchTransferParam;
import com.ynzyq.yudbadmin.util.ylWallet.parm.TransferParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/6/16 11:30
 * @description:
 */
@Slf4j
@Service
public class WalletServiceImpl implements IWalletService {

    @Resource
    WalletMapper walletMapper;

    @Resource
    MerchantStoreMapper merchantStoreMapper;

    @Resource
    WalletOrderMapper walletOrderMapper;

    @Override
    public ApiRes<ListSettleStatisticsVO> listSettleStatistics(PageWrap<ListSettleStatisticsDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<ListSettleStatisticsVO> listSettleStatisticsVOS = walletMapper.listSettleStatistics(pageWrap.getModel());
        for (ListSettleStatisticsVO listSettleStatisticsVO : listSettleStatisticsVOS) {
            listSettleStatisticsVO.setSettleStatusDesc(StatisticsSettleStatusEnum.getName(listSettleStatisticsVO.getSettleStatus()));
            listSettleStatisticsVO.setNotSettleMoney(new BigDecimal(listSettleStatisticsVO.getOrderMoney()).subtract(new BigDecimal(listSettleStatisticsVO.getSettleMoney())).toString());
        }
        return ApiRes.successResponseData(PageData.from(new PageInfo<>(listSettleStatisticsVOS)));
    }

    @Override
    public void exportSettleStatistics(ListSettleStatisticsDTO listSettleStatisticsDTO, HttpServletResponse response) {
        List<ListSettleStatisticsVO> listSettleStatisticsVOS = walletMapper.listSettleStatistics(listSettleStatisticsDTO);
        for (ListSettleStatisticsVO listSettleStatisticsVO : listSettleStatisticsVOS) {
            listSettleStatisticsVO.setSettleStatusDesc(StatisticsSettleStatusEnum.getName(listSettleStatisticsVO.getSettleStatus()));
            listSettleStatisticsVO.setNotSettleMoney(new BigDecimal(listSettleStatisticsVO.getOrderMoney()).subtract(new BigDecimal(listSettleStatisticsVO.getSettleMoney())).toString());
        }
        EasyExcel.write(ExcelUtil.getOutPutStream("结算列表", response), ListSettleStatisticsVO.class).registerWriteHandler(ExcelUtil.getHorizontalCellStyleStrategy()).sheet().doWrite(listSettleStatisticsVOS);
    }

    @Override
    public ApiRes<SettleStatisticsDetailVO> settleStatisticsDetail(SettleStatisticsDetailDTO settleStatisticsDetailDTO) {
        if (StringUtils.isBlank(settleStatisticsDetailDTO.getStatisticsId())) {
            return ApiRes.failResponse("结算id不能为空");
        }
        List<SettleStatisticsDetailVO> settleStatisticsDetailVOS = walletMapper.listSettleOrder(settleStatisticsDetailDTO);
        for (SettleStatisticsDetailVO settleStatisticsDetailVO : settleStatisticsDetailVOS) {
            settleStatisticsDetailVO.setSettleStatusDesc(SettleStatusEnum.getName(settleStatisticsDetailVO.getSettleStatus()));
        }
        return ApiRes.successResponseData(settleStatisticsDetailVOS);
    }

    @Override
    public ApiRes settle(String password, String plugRandKey, List<WalletOrder> walletOrderList) {
        List<BatchTransferListParam> list = Lists.newArrayList();
        BatchTransferListParam batchTransferListParam;
        for (WalletOrder walletOrder : walletOrderList) {
            if (walletOrder.getSettleStatus().equals(1)) {
                return ApiRes.failResponse("订单【" + walletOrder.getOrderNo() + "】已结算完成，无需重复结算，请刷新页面后重新选择");
            } else if (walletOrder.getSettleStatus().equals(3)) {
                return ApiRes.failResponse("订单【" + walletOrder.getOrderNo() + "】正在结算中，无需重复结算，请刷新页面后重新选择");
            }
            MerchantStore merchantStore = merchantStoreMapper.queryByUid(walletOrder.getStoreUid());
            if (merchantStore == null) {
                return ApiRes.failResponse("门店【" + merchantStore.getUid() + "】不存在");
            } else if (merchantStore.getIsOpenWallet().equals(2) || StringUtils.isBlank(merchantStore.getWalletId())) {
                return ApiRes.failResponse("门店【" + merchantStore.getUid() + "】未开通钱包");
            }

            batchTransferListParam = new BatchTransferListParam();
            batchTransferListParam.setSn(walletOrder.getOrderNo());
            batchTransferListParam.setIntoWalletId(merchantStore.getWalletId());
            batchTransferListParam.setIntoWalletName(merchantStore.getWalletName());
            batchTransferListParam.setAmount(String.valueOf(walletOrder.getOrderMoney().multiply(new BigDecimal("100")).intValue()));
            batchTransferListParam.setBizType("010005");
            list.add(batchTransferListParam);
        }
        BatchTransferParam batchTransferParam = new BatchTransferParam();
        String mctOrderNo = PlatformCodeUtil.orderNo("D");
        batchTransferParam.setMctOrderNo(mctOrderNo);
        batchTransferParam.setFromWalletId(WalletUtil.ZONG_DAIAN_WALLET_ID);
        batchTransferParam.setTotalCount(String.valueOf(walletOrderList.size()));
        BigDecimal money = walletOrderList.stream().map(WalletOrder::getOrderMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
        batchTransferParam.setTotalAmt(String.valueOf(money.multiply(new BigDecimal("100")).intValue()));
        batchTransferParam.setList(list);
        try {
            log.info("订单【{}】批量转账请求参数：{}", batchTransferParam.getMctOrderNo(), JSON.toJSONString(batchTransferParam));
            JSONObject transfer = WalletUtil.batchTransfer(batchTransferParam, WalletUtil.PWD, password, plugRandKey);
            log.info("订单【{}】批量转账响应参数：{}", batchTransferParam.getMctOrderNo(), transfer);
            String code = transfer.getString("code");
            if (!StringUtils.equals(code, "00000")) {
                return ApiRes.failResponse("订单【" + batchTransferParam.getMctOrderNo() + "】请求转账失败，原因：" + transfer.getString("msg"));
            }
            String response = transfer.getString("response");
            JSONObject responseJsonObject = JSONObject.parseObject(response);
            String rspCode = responseJsonObject.getString("rspCode");
            if (!StringUtils.equals(rspCode, "00000")) {
                return ApiRes.failResponse("订单【" + batchTransferParam.getMctOrderNo() + "】请求转账失败，原因：" + responseJsonObject.getString("rspResult"));
            }
        } catch (IOException e) {
            log.error("请求转账异常", e);
            return ApiRes.failResponse("订单【" + batchTransferParam.getMctOrderNo() + "】请求转账异常");
        }

        // 订单更新结算中
        for (WalletOrder walletOrder : walletOrderList) {
            WalletOrder updateWalletOrder = new WalletOrder();
            updateWalletOrder.setId(walletOrder.getId());
            updateWalletOrder.setSettleStatus(Integer.parseInt(SettleStatusEnum.SETTLEMENT.getStatus()));
            updateWalletOrder.setUpdateTime(new Date());
            updateWalletOrder.setMctOrderNo(mctOrderNo);
            walletOrderMapper.updateByPrimaryKeySelective(updateWalletOrder);
        }
        return ApiRes.successResponse();
    }
}
