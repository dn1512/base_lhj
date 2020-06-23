package com.tj.lhj.system.filter;

import com.tj.lhj.common.ShiroUtils;
import com.tj.lhj.system.account.enti.AccountEntity;
import com.tj.lhj.system.account.enti.AccountToken;
import com.tj.lhj.system.account.service.SySAccountTokenService;
import com.tj.lhj.system.account.service.SysAccountService;
import com.tj.lhj.utils.ConstantsUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

/**
 * 认证
 *
 * @date 2019-12-02 下午3:49:27
 */
@Component
public class SysRealm extends AuthorizingRealm {

    @Autowired SySAccountTokenService sysAccountTokenService;

    @Autowired SysAccountService sysAccountService;

    /**
     * token过期时间，在《application.yml》中的配置
     */
    @Value("${server.session.timeout}")
    private Integer serverSessionTimeout;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof LoginTypeToken;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Integer accountId = ShiroUtils.getAccountId();
//        //用户角色
//        Set<String> rolesSet = sysAccountService.listUserRoles(userId);
//        //用户权限
//        Set<String> permsSet = sysAccountService.listUserPerms(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setRoles(rolesSet);
//        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        LoginTypeToken loginTypeToken = (LoginTypeToken) token;
        if (loginTypeToken.getLoginType().equals(LoginType.APPLET.toString())) {
            return null;
        }
        String accessToken = (String) token.getPrincipal();

        //根据accessToken，查询用户信息
        AccountToken tokenEntity = sysAccountTokenService.getByToken(accessToken);
        //token失效
        if (tokenEntity == null || tokenEntity.getGmtModified().getTime() + serverSessionTimeout < System.currentTimeMillis()) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }else{
            sysAccountTokenService.upDateTokenModified(tokenEntity.getId());
        }

        //查询用户信息
        AccountEntity ae = sysAccountService.getById(tokenEntity.getAccountId());
        //账号锁定
        if (ae.getStatus().equals(ConstantsUtil.ACCOUNT_STATUS_RECYCL)) {
            throw new LockedAccountException("账号已被回收,请联系管理员!");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(ae, accessToken, getName());
        return info;
    }

}
