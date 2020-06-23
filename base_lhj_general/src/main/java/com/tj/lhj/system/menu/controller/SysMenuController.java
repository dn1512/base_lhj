package com.tj.lhj.system.menu.controller;

import com.alibaba.fastjson.JSON;
import com.tj.lhj.common.ResultDto;
import com.tj.lhj.common.ResultTableDataDto;
import com.tj.lhj.common.ShiroUtils;
import com.tj.lhj.common.TablePageInfosDTO;
import com.tj.lhj.system.menu.enti.SysMenuEntity;
import com.tj.lhj.system.menu.enti.SysMenuRoleEntity;
import com.tj.lhj.system.menu.service.SysMenuRoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tj.lhj.system.menu.service.SysMenuService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Classname SysMenuController
 * @Discription TODO
 * @date 2019/12/4 9:42
 * @Created by liutengjun
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired SysMenuService sysMenuService;

    @Autowired SysMenuRoleService sysMenuRoleService;

    /**
     * 用户菜单
     */
    @RequestMapping("/account")
    public ResultDto account(){
        Integer id = ShiroUtils.getAccountEntity().getId();
        
        if(id == 1){
            return sysMenuService.getAllMenus();
        }else{
            return sysMenuService.getAccountMenus(id);
        }
    }

    @RequestMapping("/list")
    public ResultTableDataDto list(TablePageInfosDTO tablePageInfosDTO){
        return sysMenuService.list(tablePageInfosDTO);
    }

    @RequestMapping(value = "/listByRoleId",method = RequestMethod.POST)
    public ResultDto listByRoleId(@RequestBody Integer roleId){
        if(null == roleId){
          return ResultDto.error("请选择角色！");
        }else{
          return sysMenuService.listByRoleId(roleId);
        }
    }

  @RequestMapping("/treeList")
  public ResultTableDataDto treeList(TablePageInfosDTO tablePageInfosDTO){
    return sysMenuService.treeList(tablePageInfosDTO);
  }

  @RequestMapping("/treeListAll")
  public ResultTableDataDto treeListAll(){
    return sysMenuService.treeListAll();
  }

  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public ResultDto save(@RequestBody SysMenuEntity menu){
//    SysMenuEntity menu = JSON.parseObject(request.getParameter("menu"),SysMenuEntity.class);
    return sysMenuService.save(menu);
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public ResultDto delete(@RequestBody Integer id){
    return sysMenuService.delete(id);
  }

  @RequestMapping(value = "/saveMenuRole", method = RequestMethod.PUT)
  @ResponseBody
  public ResultDto saveMenuRole(@RequestBody String params){
    List<SysMenuRoleEntity> menuRoleEntities = JSON.parseArray(params,SysMenuRoleEntity.class);
    return sysMenuRoleService.saveAll(menuRoleEntities);
  }
}
