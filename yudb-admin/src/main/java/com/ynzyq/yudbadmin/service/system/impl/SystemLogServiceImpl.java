package com.ynzyq.yudbadmin.service.system.impl;

import com.ynzyq.yudbadmin.core.model.PageData;
import com.ynzyq.yudbadmin.core.model.PageWrap;
import com.ynzyq.yudbadmin.dao.system.SystemLogMapper;
import com.ynzyq.yudbadmin.dao.system.dto.QuerySystemLogDTO;
import com.ynzyq.yudbadmin.dao.system.vo.SystemLogListVO;
import com.ynzyq.yudbadmin.service.system.SystemLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Resource
    SystemLogMapper systemLogMapper;

    @Override
    public Object findPage(PageWrap<QuerySystemLogDTO> pageWrap) {
        PageHelper.startPage(pageWrap.getPage(), pageWrap.getCapacity());
        List<SystemLogListVO> userList = systemLogMapper.selectList(pageWrap.getModel());
        return PageData.from(new PageInfo<>(userList));
    }
}
