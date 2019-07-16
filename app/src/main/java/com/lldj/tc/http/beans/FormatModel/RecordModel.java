package com.lldj.tc.http.beans.FormatModel;

import com.lldj.tc.http.beans.FormatModel.matchModel.BetModel;

import java.util.List;

public class RecordModel {
    private int page_num;
    private int page_size;
    private int total;
    private int pages;
    private List<BetModel> records;

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<BetModel> getRecords() {
        return records;
    }

    public void setRecords(List<BetModel> records) {
        this.records = records;
    }
}
