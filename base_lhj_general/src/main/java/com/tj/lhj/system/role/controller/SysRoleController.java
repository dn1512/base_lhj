package com.tj.lhj.system.role.controller;

import com.tj.lhj.common.ResultDto;
import com.tj.lhj.common.ResultTableDataDto;
import com.tj.lhj.common.TablePageInfosDTO;
import com.tj.lhj.system.org.enti.SysOrgEntity;
import com.tj.lhj.system.role.enti.SysRoleEntity;
import com.tj.lhj.system.role.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SysRoleController
 * @Discription TODO
 * @date 2020/4/22 11:21
 * @Created by liutengjun
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

  @Autowired SysRoleService sysRoleService;

  @RequestMapping("/list")
  public ResultTableDataDto list(TablePageInfosDTO tablePageInfosDTO){
    return sysRoleService.list(tablePageInfosDTO);
  }

  @RequestMapping("/save")
  public ResultDto save(@RequestBody SysRoleEntity sysRoleEntity){
    return sysRoleService.save(sysRoleEntity);
  }

  @RequestMapping("/delete")
  public ResultDto delete(@RequestBody Integer roleId){
    return sysRoleService.delete(roleId);
  }
}
