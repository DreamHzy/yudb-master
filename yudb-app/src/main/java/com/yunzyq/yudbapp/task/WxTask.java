package com.yunzyq.yudbapp.task;

import com.alibaba.fastjson.JSONObject;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@Slf4j
public class WxTask {

    @Resource
    RedisUtils redisUtils;

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @Value("${AppID}")
    private String AppID;
    @Value("${AppSecret}")
    private String AppSecret;

    /**
     * 获取请求权限
     */
//    @Scheduled(cron = "*/30 * * * * ?")//每五分 测试用`
//    @PostConstruct
    @Scheduled(cron = "0 0 0/1 * * ?")//每一小时执行一次
    public void userCouponjob() {
        if ("dev".equals(springProfilesActive)) {
            log.info("获取请求权限任务开始DEV={}");
//            log.info("获取请求权限任务开始={}");
//            String auth = getAccessToken();
//            JSONObject jsonObject = JSONObject.parseObject(auth);
//            log.info("获取请求权限任务开始={}", auth);
//            String access_token = jsonObject.getString("access_token");
//            Long expires_in = jsonObject.getLong("expires_in");
//            redisUtils.set("access_token", access_token, expires_in);
        } else {
            log.info("获取请求权限任务开始={}");
            String auth = getAccessToken();
            JSONObject jsonObject = JSONObject.parseObject(auth);
            log.info("获取请求权限任务开始={}", auth);
            String access_token = jsonObject.getString("access_token");
            Long expires_in = jsonObject.getLong("expires_in");
            redisUtils.set("access_token", access_token, expires_in);
        }
    }

    public String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" +
                "client_credential&appid=" + AppID + "&secret=" + AppSecret;
        log.info("usr={}", url);
        return HttpUtil.sendByGetUrl(url);
    }

}
