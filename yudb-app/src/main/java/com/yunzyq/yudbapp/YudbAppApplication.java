package com.yunzyq.yudbapp;

import com.alibaba.fastjson.JSONObject;
import com.yunzyq.yudbapp.redis.RedisUtils;
import com.yunzyq.yudbapp.task.YlTask;
import com.yunzyq.yudbapp.util.WxUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;


@Slf4j
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan("com.yunzyq.yudbapp.dao")
@EnableScheduling//定时任务
public class YudbAppApplication {

    @Resource
    RedisUtils redisUtils;

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(YudbAppApplication.class);
        Environment env = context.getEnvironment();
        log.info("====================================================================");
        log.info("项目版本: {}", env.getProperty("project.version"));
        log.info("启动环境: {}", env.getProperty("project.env"));
        log.info("启动端口: {}", env.getProperty("server.port"));
        log.info("日志等级: {}", env.getProperty("logback.level"));
        log.info("日志Appender: {}", env.getProperty("logback.appender"));
        log.info("Swagger: {}", Boolean.valueOf(env.getProperty("swagger.enabled")) ? "启用" : "禁用");
        log.info("Startup complete ...");
        log.info("====================================================================");
    }


//    //启动执行
//    @Override
//    public void run(String... args) throws Exception {
//        if ("dev".equals(springProfilesActive)) {
//            log.info("---------------------------开发版本启动成功----------------------------------------");
//        } else {
//            log.info("-----------------------------线上版本启动成功---------------------------");
//            log.info("启动执行获取微信授权={}");
//            String auth = WxUtil.getAccessToken();
//            JSONObject jsonObject = JSONObject.parseObject(auth);
//            log.info("获取请求权限jsonObject：{}", auth);
//            String access_token = jsonObject.getString("access_token");
//            Long expires_in = jsonObject.getLong("expires_in");
//            redisUtils.set("access_token", access_token, expires_in);
//            log.info("------------------------------------------启动执行获取微信结束---------------------------------------------");
//
//        }
//    }

}
