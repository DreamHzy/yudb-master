package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.*;
import com.ynzyq.yudbadmin.dao.business.vo.*;

/**
 * @author xinchen
 * @date 2021/12/24 15:09
 * @description:
 */
public interface IAgentApproveService {

    /**
     * 门店审核查看列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ShowStoreExamineVO> showStoreExamine(PageWrap<ShowStoreExamineDTO> pageWrap);

    /**
     * 门店审核管理列表
     *
     * @param pageWrap
     * @return
     */
    ApiRes<ShowStoreExamineVO> listStoreExamine(PageWrap<ShowStoreExamineDTO> pageWrap);

    /**
     * 详情
     *
     * @param storeExamineDetailDTO
     * @return
     */
    ApiRes<StoreExamineDetailVO> storeExamineDetail(StoreExamineDetailDTO storeExamineDetailDTO);

    /**
     * 拒绝
     *
     * @param examineDTO
     * @return
     */
    ApiRes examineRefuse(ExamineDTO examineDTO);

    /**
     * 同意
     *
     * @param examineDTO
     * @return
     */
    ApiRes examineAgree(ExamineDTO examineDTO);
}
