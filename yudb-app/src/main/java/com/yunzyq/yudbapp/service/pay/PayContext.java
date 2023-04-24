package com.yunzyq.yudbapp.service.pay;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 动态调用核心类
 */
@Service
public class PayContext {

    @Resource
    Map<String, PayService> payServiceMap;

    public PayService pay(String type) {
        return payServiceMap.get(type);
    }



    public PayService callBack(String type) {
        return payServiceMap.get(type);
    }
}
