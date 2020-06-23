package com.tj.lhj.system.menu.service;

import com.tj.lhj.common.ResultDto;
import com.tj.lhj.system.menu.enti.SysMenuRoleEntity;
import com.tj.lhj.system.menu.repo.SysMenuRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname SysMenuRoleService
 * @Discription TODO
 * @date 2020/4/24 10:26
 * @Created by liutengjun
 */
@Service
@Slf4j
public class SysMenuRoleService {

  @Autowired SysMenuRoleRepository sysMenuRoleRepository;


  public ResultDto saveAll(List<SysMenuRoleEntity> menuRoleEntities) {
    try {
      if(menuRoleEntities.size() > 0){
        this.deleteByRoleId(menuRoleEntities.get(0).getRoleId());
      }
      List<SysMenuRoleEntity> list = sysMenuRoleRepository.saveAll(menuRoleEntities);
      return ResultDto.success("保存成功！");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }

  public ResultDto deleteByRoleId(Integer roleId){
    try{
      List<SysMenuRoleEntity> list = sysMenuRoleRepository.findDetailByRoleId(roleId);
      int rowNum = list.size();
      if(rowNum > 0){
        sysMenuRoleRepository.deleteAll(list);
        return ResultDto.success().obj(rowNum);
      }
      return ResultDto.error("未查询到数据");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error("删除失败！");
    }
  }
}
