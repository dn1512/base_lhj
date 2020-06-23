package com.tj.lhj.system.account.controller;

import com.tj.lhj.common.*;
import com.tj.lhj.system.account.enti.AccountEntity;
import com.tj.lhj.system.account.service.SysAccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Classname SysAccountController
 * @Discription TODO
 * @date 2019/12/4 14:58
 * @Created by liutengjun
 */
@RestController
@RequestMapping("/sys/account")
public class SysAccountController {

  @Autowired
  SysAccountService sysAccountService;

  @RequestMapping("/list")
  @ResponseBody
  public ResultTableDataDto list(TablePageInfosDTO tablePageInfosDTO) {
    return sysAccountService.list(tablePageInfosDTO);
  }

  @RequestMapping("/updatePswd")
  @ResponseBody
  public ResultDto updatePswd(String pswd, String newPswd) {
    AccountEntity ae = ShiroUtils.getAccountEntity();
    if (null == pswd && "null".equals(pswd)) {
      return ResultDto.error("密码为空");
    }
    if (null == newPswd && "null".equals(newPswd)) {
      return ResultDto.error("新密码不能为空");
    }

    if (ae.getAccountPassword().equals(DigestUtils.md5Hex(pswd))) {
      ae.setAccountPassword(DigestUtils.md5Hex(newPswd));
      sysAccountService.save(ae);
      return ResultDto.success("修改成功");
    }
    return ResultDto.error("原密码错误，不能修改！");
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public ResultDto save(@RequestBody AccountEntity account){
    return sysAccountService.saveAccount(account);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public ResultDto delete(@RequestBody Integer id){
    return sysAccountService.delete(id);
  }
}
