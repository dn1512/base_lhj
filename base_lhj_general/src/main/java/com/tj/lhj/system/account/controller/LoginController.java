package com.tj.lhj.system.account.controller;

import com.tj.lhj.common.ResultDto;
import com.tj.lhj.system.account.enti.AccountEntity;
import com.tj.lhj.system.account.service.SySAccountTokenService;
import com.tj.lhj.system.account.service.SysAccountService;
import com.tj.lhj.utils.ConstantsUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Component
@RequestMapping("/sys")
public class LoginController {

    @Autowired SysAccountService sysAccountService;

    @Autowired SySAccountTokenService sysAccountTokenService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public ResultDto login(String accountName, String accountPassword)throws IOException {
        AccountEntity ae = sysAccountService.getAccountByName(accountName);
        if (null != ae){
            String password = DigestUtils.md5Hex(accountPassword);
            if (password.equals(ae.getAccountPassword())){
                if(ae.getStatus().equals(ConstantsUtil.ACCOUNT_STATUS_NORMAL)){
                    ResultDto r = ResultDto.success("登录成功！");
                    r.put("accountId",ae.getId());
                    r.put("token",sysAccountTokenService.createAccountToken(ae.getId()));
                    sysAccountTokenService.saveAccountToken(ae.getId(),(String) r.get("token"));
                    return r;
                }else{
                    return ResultDto.error(500,"账号已被回收,无法使用此账号登录！");
                }
            }else{
                return ResultDto.error(500,"密码错误，请查证后登录！");
            }
        }else{
            return ResultDto.error(500,"系统内无此账号，请查证后登录！");
        }
    }
}
