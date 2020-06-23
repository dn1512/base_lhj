package com.tj.lhj.system.org.service;

import com.tj.lhj.common.*;
import com.tj.lhj.system.org.enti.SysOrgEntity;
import com.tj.lhj.system.org.repo.SysOrgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname SysOrgService
 * @Discription TODO
 * @date 2020/4/21 14:45
 * @Created by liutengjun
 */
@Service
@Slf4j
public class SysOrgService {

  @Autowired SysOrgRepository sysOrgRepository;

  public ResultTableDataDto treeList(TablePageInfosDTO tablePageInfosDTO) {
    ResultTableDataDto r = new ResultTableDataDto();
    List<SysOrgEntity> orgs = new ArrayList<>();
    if(null == tablePageInfosDTO.getNodeid()){
      orgs = sysOrgRepository.treeListAll();
    }else{
      orgs = sysOrgRepository.findByParentId(Integer.valueOf(tablePageInfosDTO.getNodeid()));
    }
    List<SysOrgEntity> resultList = new ArrayList<SysOrgEntity>();
    addChildMenu(orgs,resultList);
    r.setRows(resultList.stream().map(BeanUtils::getObjectField).collect(Collectors.toList()));
    return r;
  }

  private void addChildMenu(List<SysOrgEntity> orgs,List<SysOrgEntity> resultList) {
    List<SysOrgEntity> children = new ArrayList<SysOrgEntity>();
    for (SysOrgEntity item : orgs) {
      resultList.add(item);
      children = sysOrgRepository.findByParentId(item.getOrgId());
      if(children.size() > 0){
        addChildMenu(children,resultList);
      }
    }
  }

  public ResultDto save(SysOrgEntity sysOrgEntity) {
    try{
      if(null != sysOrgEntity.getOrgId()){
        List<SysOrgEntity> orgs = sysOrgRepository.findByParentId(sysOrgEntity.getOrgId());
        if(orgs.size() > 0){
          sysOrgEntity.setLeaf(false);
        }
        sysOrgEntity.setGmtModified(new Timestamp(System.currentTimeMillis()));
      }else{
        sysOrgEntity.setLeaf(true);
        SysOrgEntity org = sysOrgRepository.findById(sysOrgEntity.getParentId()).get();
        org.setLeaf(false);
        sysOrgRepository.save(org);
        sysOrgEntity.setGmtCreate(new Timestamp(System.currentTimeMillis()));
      }
      sysOrgRepository.save(sysOrgEntity);
      return ResultDto.success("保存成功！");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }

  public ResultDto delete(Integer orgId) {
    try{
      sysOrgRepository.deleteById(orgId);
      return ResultDto.success("删除成功！");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }

  public List<KVDTO> selectAllOrg() {
    List<KVDTO> kvs = new ArrayList<>();
    List<SysOrgEntity> orgs = sysOrgRepository.findAllKy();
    for(SysOrgEntity org:orgs){
      KVDTO kv = new KVDTO();
      kv.setKey(org.getOrgId());
      kv.setValue(org.getName());
      kvs.add(kv);
    }
    return kvs;
  }
}
