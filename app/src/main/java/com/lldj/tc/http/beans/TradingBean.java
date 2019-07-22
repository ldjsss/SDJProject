package com.lldj.tc.http.beans;

import com.lldj.tc.http.beans.FormatModel.ResultsModel;

import java.util.List;

public class TradingBean{
    private int code = -9999;
    private String message = "";
    private TradeRecordList result;

    public int getCode() { return code; }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) {
        this.message = message;
    }

    public TradeRecordList getResult() {
        return result;
    }
    public void setResults(TradeRecordList result) {
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

    public class TradeRecordList{
        private int page_num;
        private int page_size;
        private int pages;
        private int total;
        private List<TradeRecordDTO> records;

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

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<TradeRecordDTO> getRecords() {
            return records;
        }

        public void setRecords(List<TradeRecordDTO> records) {
            this.records = records;
        }

        @Override
        public String toString() {
            return "matchModel{" +
                    "page_num=" + page_num +
                    ", page_size=" + page_size +
                    ", pages=" + pages +
                    ", total=" + total +
                    ", records=" + records +
                    '}';
        }
    }

    public class TradeRecordDTO{
        private int cash_status;
        private int id;
        private String money;
        private int recharge_status;
        private long time;
        private int type;

        public int getCash_status() {
            return cash_status;
        }

        public void setCash_status(int cash_status) {
            this.cash_status = cash_status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getRecharge_status() {
            return recharge_status;
        }

        public void setRecharge_status(int recharge_status) {
            this.recharge_status = recharge_status;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "TradeRecordDTO{" +
                    "cash_status=" + cash_status +
                    ", id=" + id +
                    ", money='" + money + '\'' +
                    ", recharge_status=" + recharge_status +
                    ", time=" + time +
                    ", type=" + type +
                    '}';
        }
    }
}


