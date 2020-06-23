package com.tj.lhj.common;

import java.util.List;
import java.util.Map;

public class ResultTableDataDto{
  private long curPage;
  private long pageNums;
  private long totalRows;
  private boolean success;
  private List<Map<String,Object>> rows;

  public long getCurPage() {
    return curPage;
  }

  public void setCurPage(long curPage) {
    this.curPage = curPage;
  }

  public List<Map<String, Object>> getRows() {
    return rows;
  }

  public void setRows(List<Map<String, Object>> rows) {
    this.rows = rows;
  }

  public long getPageNums() {
    return pageNums;
  }

  public void setPageNums(long pageNums) {
    this.pageNums = pageNums;
  }

  public long getTotalRows() {
    return totalRows;
  }

  public void setTotalRows(long totalRows) {
    this.totalRows = totalRows;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}