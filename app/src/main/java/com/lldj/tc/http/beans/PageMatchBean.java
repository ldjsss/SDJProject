package com.lldj.tc.http.beans;

import com.lldj.tc.http.beans.FormatModel.ResultsModel;

import java.util.List;

public class PageMatchBean{
    private int code = -9999;
    private String message = "";
    private matchModel result;

    public int getCode() { return code; }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }

    public matchModel getResult() {
        return result;
    }
    public void setResults(matchModel result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public class matchModel{
        private int page_num;
        private int page_size;
        private int total;
        private int pages;
        private int type;
        private List<ResultsModel> datas;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<ResultsModel> getDatas() {
            return datas;
        }

        public void setDatas(List<ResultsModel> datas) {
            this.datas = datas;
        }

        @Override
        public String toString() {
            return "matchModel{" +
                    "page_num=" + page_num +
                    ", page_size=" + page_size +
                    ", total=" + total +
                    ", pages=" + pages +
                    ", type=" + type +
                    ", datas=" + datas +
                    '}';
        }
    }
}


