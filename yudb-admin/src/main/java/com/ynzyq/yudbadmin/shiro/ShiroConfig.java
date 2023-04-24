package com.ynzyq.yudbadmin.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author Caesar Liu
 * @date 2021-03-30 23:10
 */
@Configuration
public class ShiroConfig {

    @Resource
    private ShiroCredentialsMatcher shiroCredentialsMatcher;

    private static String cookieId = "TOKEN";


    @Bean
    public ShiroRealm getShiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(shiroCredentialsMatcher);
        return shiroRealm;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    //解决自定义的Cookie名称与默认名称冲突
    @Bean
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName(cookieId);
        simpleCookie.setPath("/");
        return simpleCookie;
    }


    @Bean
    public SessionManager sessionManager() {
        LoginSessionManager loginSessionManager = new LoginSessionManager();
        loginSessionManager.setSessionIdCookie(simpleCookie());
        return loginSessionManager;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setRealm(this.getShiroRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new LinkedHashMap<>();
        // 路径拦截配置
        map.put("/system/login", "anon");
        map.put("/system/smsLogin", "anon");
        map.put("/common/**", "anon");
        map.put("/Test/**", "anon");
        map.put("/system/smsUpdatePwd", "anon");
        map.put("/evaluate/employeeInformationStorage", "anon");
        map.put("/site/sitereadExcel", "anon");
        map.put("/finance/export", "anon");
//        map.put("/agent/exportAgent", "anon");
//        map.put("/store/exportStore", "anon");
        map.put("/merchant/exportMerchant", "anon");
        map.put("/evaluate/addEvaluate", "anon");
        map.put("/evaluate/exportEvaluate", "anon");
        map.put("/agent/excelSubmitForReview", "anon");
        // - 放行swagger
        map.put("/doc.html", "anon");
        map.put("/webjars/**", "anon");
        map.put("/agentFinance/export", "anon");
        map.put("/swagger-resources/**", "anon");
        map.put("/v2/api-docs/**", "anon");
        map.put("/yudb/modifyStoreStatus", "anon");
        map.put("/yudb/modifyStoreStatus/V2", "anon");
        map.put("/yudb/transferOrder", "anon");
        map.put("/yudb/querySettleStatus", "anon");
        map.put("/storeMapping/exportStoreMapping", "anon");
//        map.put("/excel/**", "anon");
        // - 其他接口统一拦截
        map.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        // 添加认证过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new ShiroAuthFilter());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
