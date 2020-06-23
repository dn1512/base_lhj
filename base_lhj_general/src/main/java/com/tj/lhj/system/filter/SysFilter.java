package com.tj.lhj.system.filter;

import com.tj.lhj.common.ResultDto;
import com.tj.lhj.system.account.enti.AccountToken;
import com.tj.lhj.system.account.service.SySAccountTokenService;
import com.tj.lhj.utils.JSONUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter extends AuthenticatingFilter {

    public static final String LOGIN_TYPE = LoginType.OAUTH2.toString();

    @Autowired SySAccountTokenService sysAccountTokenService;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        //获取请求token
        String token = getRequestToken(httpServletRequest);

        if(null == token || "null".equals(token)){
            return null;
        }

        return new LoginTypeToken(LOGIN_TYPE, token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     * 检查token
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(null == token || "null".equals(token)){
            tokenIsNull(response,"invalid token");
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 登录失败
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            ResultDto r = ResultDto.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());
            String json = JSONUtils.beanToJson(r);
            httpResponse.getWriter().print(json);
        } catch (Exception ee) {

        }

        return false;
    }


    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(null == token || "null".equals(token)){
            token = httpRequest.getParameter("token");
        }

        return token;
    }

    private void tokenIsNull(ServletResponse response,String msg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        ResultDto r = ResultDto.error(HttpStatus.SC_UNAUTHORIZED, msg);
        String json = JSONUtils.beanToJson(r);
        httpResponse.getWriter().print(json);
    }
}
