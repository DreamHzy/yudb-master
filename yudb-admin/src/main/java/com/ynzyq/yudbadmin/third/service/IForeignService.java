package com.ynzyq.yudbadmin.third.service;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.third.dto.*;
import com.ynzyq.yudbadmin.third.vo.QuerySettleStatusVO;

/**
 * @author xinchen
 * @date 2021/12/2 17:40
 * @description:
 */
public interface IForeignService {

    /**
     * 修改门店状态
     *
     * @param modifyStoreStatusDTO
     * @return
     */
    ApiRes modifyStoreStatus(ModifyStoreStatusDTO modifyStoreStatusDTO);

    /**
     * 用户信息同步金蝶接口
     *
     * @param userInfoDTO
     * @return
     */
    String userInfo(UserInfoDTO userInfoDTO);

    /**
     * 修改门店状态2
     *
     * @param modifyStoreStatusTwoDTO
     * @return
     */
    ApiRes modifyStoreStatusV2(ModifyStoreStatusTwoDTO modifyStoreStatusTwoDTO);

    /**
     * 传输订单
     *
     * @param transferOrderDTO
     * @return
     */
    ApiRes transferOrder(TransferOrderDTO transferOrderDTO);

    /**
     * 查询订单结算状态
     *
     * @param querySettleStatusDTO
     * @return
     */
    ApiRes<QuerySettleStatusVO> querySettleStatus(QuerySettleStatusDTO querySettleStatusDTO);
}
