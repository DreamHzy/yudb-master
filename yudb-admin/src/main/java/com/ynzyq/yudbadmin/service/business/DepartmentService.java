package com.ynzyq.yudbadmin.service.business;

import com.ynzyq.yudbadmin.core.model.ApiRes;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.business.dto.DepartmentAdd;
import com.ynzyq.yudbadmin.dao.business.dto.DepartmentPageDto;
import com.ynzyq.yudbadmin.dao.business.dto.DepartmentUpdateDto;
import com.ynzyq.yudbadmin.dao.business.vo.DepartmentPageVo;

public interface DepartmentService {
    ApiRes<DepartmentPageVo> findPage();

    ApiRes add(DepartmentAdd departmentAdd);

    ApiRes update(DepartmentUpdateDto departmentUpdateDto);
}
