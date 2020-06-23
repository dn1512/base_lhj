package com.tj.lhj.system.menu.service;

import com.tj.lhj.common.*;
import com.tj.lhj.system.menu.enti.SysMenuEntity;
import com.tj.lhj.system.menu.enti.SysMenuRoleEntity;
import com.tj.lhj.system.menu.repo.SysMenuRepository;
import com.tj.lhj.system.menu.repo.SysMenuRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname SysMenuService
 * @Discription 系统菜单service
 * @date 2019/12/4 9:43
 * @Created by liutengjun
 */
@Service
@Slf4j
public class SysMenuService {
  @Autowired
  SysMenuRepository sysMenuRepository;
  @Autowired
  SysMenuRoleRepository sysMenuRoleRepository;

  public ResultDto getAllMenus() {
    List<SysMenuEntity> menus = sysMenuRepository.findByParentId(0);
    setChildMenu(menus);
    return ResultDto.success().put("menuList", menus);
  }

  public ResultDto getAccountMenus(Integer id) {
    return ResultDto.success().put("menuList", sysMenuRoleRepository.findByAccountId(id));
  }

  private void setChildMenu(List<SysMenuEntity> menus) {
    List<SysMenuEntity> child = new ArrayList<SysMenuEntity>();
    for (SysMenuEntity item : menus) {
      if (item.getType().equals(0)) {
        child = sysMenuRepository.findByParentId(item.getId());
        setChildMenu(child);
        item.setList(child);
      }
    }
  }

  private void addChildMenu(List<SysMenuEntity> menus,List<SysMenuEntity> resultList) {
    List<SysMenuEntity> children = new ArrayList<SysMenuEntity>();
    for (SysMenuEntity item : menus) {
      resultList.add(item);
      if (item.getType().equals(0)) {
        children = sysMenuRepository.findByParentId(item.getId());
        if(children.size() > 0 ){
          addChildMenu(children,resultList);
        }else{
          item.setLeaf(true);
        }
      }
    }
  }

  public ResultTableDataDto list(TablePageInfosDTO tablePageInfosDTO) {
    ResultTableDataDto r = new ResultTableDataDto();
    PageRequest pageRequest = PageUtils.getPageRequest(tablePageInfosDTO);
    Page<SysMenuEntity> page = sysMenuRepository.findAll(pageRequest);
    r.setSuccess(true);
    r.setTotalRows(page.getTotalElements());
    r.setCurPage(tablePageInfosDTO.getPage());
    r.setPageNums(page.getTotalPages());
    List<SysMenuEntity> list = page.getContent();
    SysMenuEntity menu = new SysMenuEntity();
    r.setRows(list.stream().map(BeanUtils::getObjectField).collect(Collectors.toList()));
    return r;
  }

  public ResultTableDataDto treeList(TablePageInfosDTO tablePageInfosDTO) {
    ResultTableDataDto r = new ResultTableDataDto();
    PageRequest pageRequest = PageUtils.getPageRequest(tablePageInfosDTO);
    if(null == tablePageInfosDTO.getNodeid()){
      Page<SysMenuEntity> page = sysMenuRepository.treeList(pageRequest);
      r.setSuccess(true);
      r.setTotalRows(page.getTotalElements());
      r.setCurPage(tablePageInfosDTO.getPage());
      r.setPageNums(page.getTotalPages());
      List<SysMenuEntity> list = new ArrayList<SysMenuEntity>(page.getContent());
      List<SysMenuEntity> resultList = new ArrayList<SysMenuEntity>();
      addChildMenu(list,resultList);
      r.setRows(resultList.stream().map(BeanUtils::getObjectField).collect(Collectors.toList()));
      return r;
    }else{
      return r;
    }
  }

  public ResultTableDataDto treeListAll() {
    ResultTableDataDto r = new ResultTableDataDto();
    List<SysMenuEntity> list = sysMenuRepository.treeListAll();
    List<SysMenuEntity> resultList = new ArrayList<SysMenuEntity>();
    addChildMenu(list,resultList);
    r.setRows(resultList.stream().map(BeanUtils::getObjectField).collect(Collectors.toList()));
    return r;
  }

  public ResultDto save(SysMenuEntity menu) {
    try{
      menu.setLeaf(menu.getType() != 0);
      sysMenuRepository.save(menu);
      return ResultDto.success("保存成功！");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }

  public ResultDto delete(Integer id) {
    try{
      sysMenuRepository.deleteById(id);
      return ResultDto.success("删除成功！");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }

  public ResultDto listByRoleId(Integer roleId) {
    if(null != roleId){
      List<SysMenuRoleEntity> menus = sysMenuRoleRepository.findDetailByRoleId(roleId);
      return ResultDto.success().obj(menus.stream().map(SysMenuRoleEntity::getMenuId).collect(Collectors.toList()));
    }else{
      return ResultDto.error("请选择角色！");
    }
  }
}
