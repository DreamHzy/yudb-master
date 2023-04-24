package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.RegionalManagerAddDto;
import com.ynzyq.yudbadmin.dao.business.dto.RegionalManagerEditDto;
import com.ynzyq.yudbadmin.dao.business.dto.RegionalManagerPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.RegionalManagerStatusDto;
import com.ynzyq.yudbadmin.dao.business.vo.DepartmentListVo;
import com.ynzyq.yudbadmin.dao.business.vo.RegionalManagerPageVo;

public interface RegionalManagerService {
    ApiRes<PageWrap<RegionalManagerPageVo>> findPage(PageWrap<RegionalManagerPageDto> pageWrap);

    ApiRes updateStatus(RegionalManagerStatusDto regionalManagerStatusDto);

    ApiRes<DepartmentListVo> departmentList();

    ApiRes add(RegionalManagerAddDto regionalManagerAddDto);

    ApiRes edit(RegionalManagerEditDto regionalManagerEditDto);
}
