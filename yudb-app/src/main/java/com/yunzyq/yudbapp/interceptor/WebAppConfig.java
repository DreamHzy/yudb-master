package com.yunzyq.yudbapp.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public TokenInterceptor getLoginInterceptor() {
        return new TokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/common/**")
                .excludePathPatterns("/merchantOrder/paymentOnBehalf")
                .excludePathPatterns("/regionalManagerOrder/agentPaymentCode")
                .excludePathPatterns("/merchantOrder/auth")
                .excludePathPatterns("/merchantOrderV2/paymentOnAgentBehalf")
                .excludePathPatterns("/regionalManagerOrderV2/isShowQrcode")
                .excludePathPatterns("/doc.html")      // - 放行swagger
                .excludePathPatterns("/webjars/**")      // - 放行swagger
                .excludePathPatterns("/swagger-resources/**")      // - 放行swagger
                .excludePathPatterns("/v2/api-docs/**")      // - 放行swagger
        ;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//设置允许跨域的路径
                .allowedOrigins("*")//设置允许跨域请求的域名
                .allowCredentials(true)//是否允许证书 不再默认开启
                .allowedMethods("GET", "POST", "PUT", "DELETE")//设置允许的方法
                .maxAge(3600);//跨域允许时间
    }
}
