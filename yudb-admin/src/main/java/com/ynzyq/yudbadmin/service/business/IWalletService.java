package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.ListSettleStatisticsDTO;
import com.ynzyq.yudbadmin.dao.business.dto.SettleStatisticsDetailDTO;
import com.ynzyq.yudbadmin.dao.business.model.WalletOrder;
import com.ynzyq.yudbadmin.dao.business.vo.ListSettleStatisticsVO;
import com.ynzyq.yudbadmin.dao.business.vo.SettleStatisticsDetailVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xinchen
 * @date 2022/6/16 11:30
 * @description:
 */
public interface IWalletService {
    /**
     * 结算列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ListSettleStatisticsVO> listSettleStatistics(PageWrap<ListSettleStatisticsDTO> pageWrap);

    /**
     * 结算列表导出
     *
     * @param listSettleStatisticsDTO
     * @param response
     */
    void exportSettleStatistics(ListSettleStatisticsDTO listSettleStatisticsDTO, HttpServletResponse response);

    /**
     * 结算详情
     *
     * @param settleStatisticsDetailDTO
     * @return
     */
    ApiRes<SettleStatisticsDetailVO> settleStatisticsDetail(SettleStatisticsDetailDTO settleStatisticsDetailDTO);

    /**
     * 结算
     *
     * @param password
     * @param plugRandKey
     * @param walletOrderList
     * @return
     */
    ApiRes settle(String password, String plugRandKey, List<WalletOrder> walletOrderList);
}
