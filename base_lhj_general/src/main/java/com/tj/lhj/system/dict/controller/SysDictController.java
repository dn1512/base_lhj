package com.tj.lhj.system.dict.controller;

import com.alibaba.fastjson.JSON;
import com.tj.lhj.common.ResultDto;
import com.tj.lhj.common.ResultTableDataDto;
import com.tj.lhj.common.TablePageInfosDTO;
import com.tj.lhj.system.dict.enti.SysDictEntity;
import com.tj.lhj.system.dict.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname SysDictController
 * @Discription TODO
 * @date 2020/2/6 11:57
 * @Created by liutengjun
 */
@Component
@RequestMapping("/sys/dict")
public class SysDictController {

  @Autowired
  private SysDictService sysDictService;

  @RequestMapping("list")
  @ResponseBody
  public ResultTableDataDto list(TablePageInfosDTO tablePageInfosDTO) {
    return sysDictService.list(tablePageInfosDTO);
  }

  @RequestMapping("detail")
  @ResponseBody
  public ResultDto detail(@RequestBody String dictFlag) {
    return sysDictService.detail(dictFlag);
  }

  @RequestMapping(value = "/save", method = RequestMethod.PUT)
  @ResponseBody
  public ResultDto save(@RequestBody String params){
    List<SysDictEntity> dicts = JSON.parseArray(params,SysDictEntity.class);
    return sysDictService.saveAll(dicts);
  }

  @RequestMapping("dict")
  @ResponseBody
  public ResultDto getDict(@RequestBody String dictFlag){
    ResultDto r = new ResultDto();
    r.obj(sysDictService.getDictByFlag(dictFlag));
    return r;
  }

  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  @ResponseBody
  public ResultDto delete(@RequestBody String dictFlag){
    return sysDictService.deleteByDictFlag(dictFlag);
  }
}
