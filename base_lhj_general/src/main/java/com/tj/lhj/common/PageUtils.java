package com.tj.lhj.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @Classname PageUtils
 * @Discription TODO
 * @date 2020/1/10 14:53
 * @Created by liutengjun
 */
public class PageUtils {
  public static PageRequest getPageRequest(TablePageInfosDTO tablePageInfosDTO){
    Sort sort = null;
    if (tablePageInfosDTO.getSord() != null && !"".equals(tablePageInfosDTO.getSord())) {
      sort = Sort.by(strOrder2Direction(tablePageInfosDTO.getSord()), tablePageInfosDTO.getSortOrder());
    }
    PageRequest pageRequest = PageRequest.of(tablePageInfosDTO.getPage() - 1, tablePageInfosDTO.getRows(), sort);
    return pageRequest;
  }

  public static Sort.Direction strOrder2Direction(String order){
    if (null == order || "".equals(order)) {
      return null;
    } else if ("asc".equals(order)) {
      return Sort.Direction.ASC;
    } else {
      return "desc".equals(order) ? Sort.Direction.DESC : null;
    }
  }
}
