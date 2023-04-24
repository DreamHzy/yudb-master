package com.ynzyq.yudbadmin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@Slf4j
@SpringBootApplication
@MapperScan("com.ynzyq.yudbadmin.dao")
@EnableAsync
@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableScheduling//定时任务
public class YudbAdminApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(YudbAdminApplication.class);
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

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }

}
