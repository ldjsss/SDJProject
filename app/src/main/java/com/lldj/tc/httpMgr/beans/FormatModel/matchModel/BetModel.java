package com.lldj.tc.httpMgr.beans.FormatModel.matchModel;

import org.json.JSONException;
import org.json.JSONObject;

public class BetModel {
    private int amount = 0;
    private int oddsid = 0;
    private float willget = 0;
    private String bet_money = "";
    private int bet_site = 0;
    private String bet_win_money = "";
    private long bet_min;
    private long bet_max;
    private String name;

    private int code = -1;
    private String odds_value = "";

    public String getBet_money() {
        return bet_money;
    }

    public void setBet_money(String bet_money) {
        this.bet_money = bet_money;
    }

    public int getBet_site() {
        return bet_site;
    }

    public void setBet_site(int bet_site) {
        this.bet_site = bet_site;
    }

    public String getBet_win_money() {
        return bet_win_money;
    }

    public void setBet_win_money(String bet_win_money) {
        this.bet_win_money = bet_win_money;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOdds_value() {
        return odds_value;
    }

    public void setOdds_value(String odds_value) {
        this.odds_value = odds_value;
    }

    public float getWillget() {
        return willget;
    }

    public void setWillget(float willget) {
        this.willget = willget;
    }

    public int getAmount() { return amount; }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOddsid() {
        return oddsid;
    }

    public void setOddsid(int oddsid) {
        this.oddsid = oddsid;
    }

    public long getBet_min() {
        return bet_min;
    }

    public void setBet_min(long bet_min) {
        this.bet_min = bet_min;
    }

    public long getBet_max() {
        return bet_max;
    }

    public void setBet_max(long bet_max) {
        this.bet_max = bet_max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BetModel(int amount, int oddsid, float willget, long bet_max, long bet_min, String name) {
       this.amount = amount;
       this.oddsid = oddsid;
       this.willget = willget;
       this.bet_max = bet_max;
       this.bet_min = bet_min;
       this.name = name;
    }

    @Override
    public String toString() {
        return "BetModel{" +
                "amount=" + amount +
                ", oddsid=" + oddsid +
                ", willget=" + willget +
                ", bet_money='" + bet_money + '\'' +
                ", bet_site=" + bet_site +
                ", bet_win_money='" + bet_win_money + '\'' +
                ", code=" + code +
                ", odds_value='" + odds_value + '\'' +
                '}';
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("amount", getAmount());
            jsonObj.put("oddsid", getOddsid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObj;
    }
}
