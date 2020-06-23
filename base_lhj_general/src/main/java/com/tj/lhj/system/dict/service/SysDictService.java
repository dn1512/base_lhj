package com.tj.lhj.system.dict.service;

import com.tj.lhj.common.*;
import com.tj.lhj.system.dict.enti.SysDictEntity;
import com.tj.lhj.system.dict.repo.SysDictRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname SysDictService
 * @Discription TODO
 * @date 2020/2/6 11:58
 * @Created by liutengjun
 */
@Service
@Slf4j
public class SysDictService {
  @Autowired
  private SysDictRepository sysDictRepository;

  public List<SysDictEntity> getDictByFlag(String dictFlag) {
    return sysDictRepository.findByDictFlag(dictFlag);
  }

  public ResultTableDataDto list(TablePageInfosDTO tablePageInfosDTO) {
    ResultTableDataDto r = new ResultTableDataDto();
    PageRequest pageRequest = PageUtils.getPageRequest(tablePageInfosDTO);
    Page<SysDictEntity> page = sysDictRepository.findAllList(pageRequest);
    r.setSuccess(true);
    r.setTotalRows(page.getTotalElements());
    r.setCurPage(tablePageInfosDTO.getPage());
    if(page.getTotalElements() == 0 || null == page){
      return r;
    }
    List<SysDictEntity> list = page.getContent();
    r.setRows(list.stream().map(BeanUtils::getObjectField).collect(Collectors.toList()));
    return r;
  }

  public ResultDto saveAll(List<SysDictEntity> dicts) {
    try {
      if(dicts.size() > 0){
        this.deleteByDictFlag(dicts.get(0).getDictFlag());
      }
      List<SysDictEntity> list = sysDictRepository.saveAll(dicts);
      return ResultDto.success("保存成功！");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }

  public ResultDto detail(String dictFlag) {
    try{
      List<SysDictEntity> list = sysDictRepository.findDetailByDictFlag(dictFlag);
      return ResultDto.success().obj(list);
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error();
    }
  }

  public ResultDto deleteByDictFlag(String dictFlag) {
    try{
      List<SysDictEntity> list = sysDictRepository.findDetailByDictFlag(dictFlag);
//      List<Integer> ids = list.stream().map(SysDictEntity :: getId).collect(Collectors.toList());
      int rowNum = list.size();
      if(rowNum > 0){
        sysDictRepository.deleteAll(list);
        return ResultDto.success().obj(rowNum);
      }
      return ResultDto.error("未查询到数据");
    }catch (Exception e){
      e.printStackTrace();
      return ResultDto.error("删除失败！");
    }
  }
}
