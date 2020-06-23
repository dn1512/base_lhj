package com.tj.lhj.system.role.service;

import com.tj.lhj.common.*;
import com.tj.lhj.system.org.enti.SysOrgEntity;
import com.tj.lhj.system.org.repo.SysOrgRepository;
import com.tj.lhj.system.role.enti.SysRoleEntity;
import com.tj.lhj.system.role.repo.SysRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Classname SysRoleService
 * @Discription TODO
 * @date 2020/4/22 16:46
 * @Created by liutengjun
 */
@Service
@Slf4j
public class SysRoleService {

  @Autowired SysRoleRepository sysRoleRepository;

  @Autowired SysOrgRepository sysOrgRepository;

  public ResultDto save(SysRoleEntity sysRoleEntity) {
    try{
      sysRoleEntity.setUserIdCreate(ShiroUtils.getAccountEntity().getId());
      sysRoleEntity.setGmtCreate(new Timestamp(System.currentTimeMillis()));
      sysRoleRepository.save(sysRoleEntity);
      return  ResultDto.success("保存成功！");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }

  public ResultTableDataDto list(TablePageInfosDTO tablePageInfosDTO) {
    ResultTableDataDto r = new ResultTableDataDto();
    PageRequest pageRequest = PageUtils.getPageRequest(tablePageInfosDTO);
    Page<SysRoleEntity> sysRoleEntityPage = sysRoleRepository.findAll(pageRequest);
    List<SysOrgEntity> orgs = sysOrgRepository.findAll();
    Map<Integer,String> orgNames = new HashMap<>();
    for(SysOrgEntity org:orgs){
      orgNames.put(org.getOrgId(),org.getName());
    }
    List<SysRoleEntity> list = sysRoleEntityPage.getContent();
    for(SysRoleEntity role:list){
      role.setOrgName(orgNames.get(role.getOrgId()));
    }
    r.setSuccess(true);
    r.setTotalRows(sysRoleEntityPage.getTotalElements());
    r.setCurPage(tablePageInfosDTO.getPage());
    r.setPageNums(sysRoleEntityPage.getTotalPages());
    r.setRows(list.stream().map(BeanUtils::getObjectField).collect(Collectors.toList()));
    return r;
  }

  public ResultDto delete(Integer roleId) {
    try{
      sysRoleRepository.deleteById(roleId);
      return ResultDto.success("删除成功！");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }
}
