package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.DealWithDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ListComplaintDTO;
import com.ynzyq.yudbadmin.dao.business.vo.ListComplaintVO;

/**
 * @author xinchen
 * @date 2022/1/25 13:48
 * @description:
 */
public interface IComplaintService {
    ApiRes<ListComplaintVO> listComplaint(PageWrap<ListComplaintDTO> pageWrap);

    ApiRes dealWith(DealWithDTO dealWithDTO);
}
