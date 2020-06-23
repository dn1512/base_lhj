package com.tj.lhj.system.filter.config;

import com.tj.lhj.system.filter.SysFilter;
import com.tj.lhj.system.filter.SysRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * Shiro配置
 *

 * @date 2019-12-02 下午3:08:42
 */
@Configuration
public class ShiroConfig {

    @Value("${server.session.timeout}")
    private Integer serverSessionTimeout;

    @Bean("sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionValidationInterval(8000L);
        sessionManager.setGlobalSessionTimeout(serverSessionTimeout * 1000L);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager;
    }

    @Bean("securityManager")
    public org.apache.shiro.mgt.SecurityManager securityManager(SysRealm realm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login.html");

        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("sysFilter", new SysFilter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/druid/**", "anon");
        filterMap.put("/api/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/sys/captcha.jpg", "anon");
        filterMap.put("/**/*.css", "anon");
        filterMap.put("/**/*.scss", "anon");
        filterMap.put("/**/*.js", "anon");
        filterMap.put("/**/*.png", "anon");
        filterMap.put("/**/*.jpg", "anon");
        filterMap.put("/**/*.eot", "anon");
        filterMap.put("/**/*.svg", "anon");
        filterMap.put("/**/*.ttf", "anon");
        filterMap.put("/**/*.woff", "anon");
        filterMap.put("/**/*.woff2", "anon");
        filterMap.put("/**/*.map", "anon");
        filterMap.put("/**/*.html", "anon");
//        filterMap.put("/static/**/*", "anon");
        filterMap.put("/plugins/**", "anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/wx/cp/portal", "anon");
        filterMap.put("/**/wx/auth", "anon");
        filterMap.put("/wx/auth/redirect", "anon");
        filterMap.put("/api/applet/authenticator", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/**", "sysFilter");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
