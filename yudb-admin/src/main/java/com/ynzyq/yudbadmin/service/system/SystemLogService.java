package com.ynzyq.yudbadmin.service.system;

import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemLogDTO;

public interface SystemLogService {
    Object findPage(PageWrap<QuerySystemLogDTO> pageWrap);
}
