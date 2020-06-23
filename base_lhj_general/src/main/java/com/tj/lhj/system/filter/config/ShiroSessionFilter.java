package com.tj.lhj.system.filter.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.session.Session;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ShiroSessionFilter {
    public List<String> excludes = new ArrayList<String>();

    public void init(FilterConfig filterConfig) throws ServletException {
        if(log.isDebugEnabled()){
            log.debug("shiro session filter init~~~~~~~~~~~~");
        }
        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; i++) {
                excludes.add(url[i]);
            }
        }
    }

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)  throws IOException,ServletException{
        if(log.isDebugEnabled()){
            log.debug("shiro session filter is open");
        }

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if(handleExcludeURL(req, resp)){
            filterChain.doFilter(request, response);
            return;
        }

        String url = ((HttpServletRequest) request).getRequestURL().toString();
        if (url.indexOf("/login.html") == -1
                && url.indexOf("/logout") == -1
                && url.indexOf("/libs") == -1
                && url.indexOf("/sys/menu/user") == -1
                && url.indexOf("/sys/login") == -1
                && url.indexOf("/system/index/main.html") == -1
                && url.indexOf("/index.html") == -1
                && url.indexOf("/plugins") == -1
                && url.indexOf("/images") == -1
                && url.indexOf("/fonts") == -1) {
            Session session = SecurityUtils.getSubject().getSession();
            HttpSession httpSession = (HttpSession) session;
            String user = (String) httpSession.getAttribute("user");
            if (user == null || "".equals(user)) {
                log.debug("filter url : " + url);
                throw new IncorrectCredentialsException("token失效，请重新登录");
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {

        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }
}