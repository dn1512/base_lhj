package com.tj.lhj.common;

import java.util.Map;

public class TablePageInfosDTO {
    private int page;
    private int rows;
    private String nodeid;
    private String sord;
    private String sortOrder;
    private Map<String, Object> bizArgs;
    private Map<String, Object> filters;
    private String keyword;

    public TablePageInfosDTO() {
    }

    public int getPage() {
        return this.page;
    }

    public int getRows() {
        return this.rows;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public String getSord() {
        return this.sord;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public Map<String, Object> getBizArgs() {
        return this.bizArgs;
    }

    public Map<String, Object> getFilters() {
        return this.filters;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setBizArgs(Map<String, Object> bizArgs) {
        this.bizArgs = bizArgs;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TablePageInfosDTO)) {
            return false;
        } else {
            TablePageInfosDTO other = (TablePageInfosDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getPage() != other.getPage()) {
                return false;
            } else if (this.getRows() != other.getRows()) {
                return false;
            } else {
                label76: {
                    Object this$sord = this.getSord();
                    Object other$sord = other.getSord();
                    if (this$sord == null) {
                        if (other$sord == null) {
                            break label76;
                        }
                    } else if (this$sord.equals(other$sord)) {
                        break label76;
                    }

                    return false;
                }

                Object this$sortOrder = this.getSortOrder();
                Object other$sortOrder = other.getSortOrder();
                if (this$sortOrder == null) {
                    if (other$sortOrder != null) {
                        return false;
                    }
                } else if (!this$sortOrder.equals(other$sortOrder)) {
                    return false;
                }

                label62: {
                    Object this$bizArgs = this.getBizArgs();
                    Object other$bizArgs = other.getBizArgs();
                    if (this$bizArgs == null) {
                        if (other$bizArgs == null) {
                            break label62;
                        }
                    } else if (this$bizArgs.equals(other$bizArgs)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    Object this$filters = this.getFilters();
                    Object other$filters = other.getFilters();
                    if (this$filters == null) {
                        if (other$filters == null) {
                            break label55;
                        }
                    } else if (this$filters.equals(other$filters)) {
                        break label55;
                    }

                    return false;
                }

                Object this$keyword = this.getKeyword();
                Object other$keyword = other.getKeyword();
                if (this$keyword == null) {
                    if (other$keyword != null) {
                        return false;
                    }
                } else if (!this$keyword.equals(other$keyword)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof TablePageInfosDTO;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getPage();
        result = result * 59 + this.getRows();
        Object $sord = this.getSord();
        result = result * 59 + ($sord == null ? 43 : $sord.hashCode());
        Object $sortOrder = this.getSortOrder();
        result = result * 59 + ($sortOrder == null ? 43 : $sortOrder.hashCode());
        Object $bizArgs = this.getBizArgs();
        result = result * 59 + ($bizArgs == null ? 43 : $bizArgs.hashCode());
        Object $filters = this.getFilters();
        result = result * 59 + ($filters == null ? 43 : $filters.hashCode());
        Object $keyword = this.getKeyword();
        result = result * 59 + ($keyword == null ? 43 : $keyword.hashCode());
        return result;
    }

    public String toString() {
        return "TablePageInfosDTO(page=" + this.getPage() + ", rows=" + this.getRows() + ", sord=" + this.getSord() + ", sortOrder=" + this.getSortOrder() + ", bizArgs=" + this.getBizArgs() + ", filters=" + this.getFilters() + ", keyword=" + this.getKeyword() + ")";
    }
}
