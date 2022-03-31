package org.sang.pojo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageVo<T> implements Serializable {
    // 当前页
    private int currPage = 1;
    // 每页条数
    private int pageSize = 10;
    // 起始行
    private int startIndex = 0;
    // 结束行
    private int endIndex = 9;
    // 总页数
    private int totalPage;
    // 总行数
    private int totalRows;
    // 数据集合
    List<T> result = new ArrayList<>();

    public PageVo(int currPage, int pageSize) {
        this.setCurrPage(currPage);
        this.setPageSize(pageSize);
        this.setStartIndex((this.currPage-1)*this.pageSize);
        this.setEndIndex(this.currPage*this.pageSize);
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        if(currPage<=0){
            currPage = 1;
        }
        this.currPage = currPage;

    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize<10){
            pageSize = 10;
        }else if(pageSize>500){
            pageSize = 500;
        }
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public List<T> getResult() {
        return result;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}
