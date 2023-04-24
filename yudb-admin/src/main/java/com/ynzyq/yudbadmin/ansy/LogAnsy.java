package com.ynzyq.yudbadmin.ansy;


import com.alibaba.fastjson.JSON;
import com.ynzyq.yudbadmin.dao.system.SystemLogMapper;
import com.ynzyq.yudbadmin.dao.system.model.SystemLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LogAnsy {


    @Resource
    SystemLogMapper systemLogMapper;

    @Async
    public void save(SystemLog sysLog) {
        systemLogMapper.insertSelective(sysLog);

    }

    /**
     * 返回数据
     *
     * @param retVal
     * @return
     */
    private String postHandle(Object retVal) {
        if (null == retVal) {
            return "";
        }
        return JSON.toJSONString(retVal);
    }
}
