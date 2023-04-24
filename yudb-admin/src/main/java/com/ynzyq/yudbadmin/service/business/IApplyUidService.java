package com.ynzyq.yudbadmin.service.business;

import cn.hutool.http.server.HttpServerRequest;
import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.AddApplyUidDTO;
import com.ynzyq.yudbadmin.dao.business.dto.ListApplyRecordDTO;
import com.ynzyq.yudbadmin.dao.business.dto.NextStepDTO;
import com.ynzyq.yudbadmin.dao.business.vo.ListApplyRecordVO;
import com.ynzyq.yudbadmin.dao.business.vo.NextStepVO;

/**
 * @author xinchen
 * @date 2022/4/6 14:28
 * @description:
 */
public interface IApplyUidService {

    ApiRes<ListApplyRecordVO> listApplyRecord(PageWrap<ListApplyRecordDTO> pageWrap, HttpServerRequest httpServerRequest);

    ApiRes<NextStepVO> nextStep(NextStepDTO nextStepDTO);

    ApiRes addApplyUid(AddApplyUidDTO addApplyUidDTO, HttpServerRequest httpServerRequest);
}
