package com.tj.lhj.system.org.controller;

import com.tj.lhj.common.KVDTO;
import com.tj.lhj.common.ResultDto;
import com.tj.lhj.common.ResultTableDataDto;
import com.tj.lhj.common.TablePageInfosDTO;
import com.tj.lhj.system.org.enti.SysOrgEntity;
import com.tj.lhj.system.org.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname SysOrgController
 * @Discription TODO
 * @date 2020/4/21 14:44
 * @Created by liutengjun
 */
@RestController
@RequestMapping("/sys/org")
public class SysOrgController {

  @Autowired SysOrgService sysOrgService;

  @RequestMapping("/treeList")
  public ResultTableDataDto treeList(TablePageInfosDTO tablePageInfosDTO){
    return sysOrgService.treeList(tablePageInfosDTO);
  }

  @RequestMapping("/save")
  public ResultDto save(@RequestBody SysOrgEntity sysOrgEntity){
    return sysOrgService.save(sysOrgEntity);
  }

  @RequestMapping("/delete")
  public ResultDto delete(@RequestBody Integer orgId){
    return sysOrgService.delete(orgId);
  }

  @RequestMapping("/selectAllOrg")
  public List<KVDTO> selectAllOrg() {
    return sysOrgService.selectAllOrg();
  }

}
