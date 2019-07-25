package com.lldj.tc.http.beans.FormatModel.matchModel;

import org.json.JSONException;
import org.json.JSONObject;

public class BetModel {
    private int amount = 0;
    private float willget = 0;

    private long bet_min;
    private long bet_max;
    private String name;

    private int code = -1;
    private String odds_value = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOdds_value() { return odds_value; }

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

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public BetModel(int amount, int odds_id, float willget, long bet_max, long bet_min, String name) {
       this.amount = amount;
       this.odds_id = odds_id;
       this.willget = willget;
       this.bet_max = bet_max;
       this.bet_min = bet_min;
       this.name = name;
    }

    public JSONObject getJSONObject() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("amount", getAmount());
            jsonObj.put("odds_id", getOdds_id());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObj;
    }








    //////////////////////
    ///////////////////////
    private int page_num;
    private int status;
    private int game_id;
    private int group_id;
    private int odds_id;
    private int match_id;
    private String game_name;
    private String group_name;
    private String group_short_name;
    private String odds_name;
    private String game_logo;
    private String match_stage;
    private String match_name;
    private String match_short_name;
    private long start_time;
    private int bet_id;
    private int bet_site;
    private String bet_odds;
    private String bet_money;
    private String bet_win_money;
    private String real_win_money;
    private int win;
    private int platform_operation_status;
    private String order_num;
    private long bet_created_time;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getOdds_id() {
        return odds_id;
    }

    public void setOdds_id(int odds_id) {
        this.odds_id = odds_id;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_short_name() {
        return group_short_name;
    }

    public void setGroup_short_name(String group_short_name) {
        this.group_short_name = group_short_name;
    }

    public String getOdds_name() {
        return odds_name;
    }

    public void setOdds_name(String odds_name) {
        this.odds_name = odds_name;
    }

    public String getGame_logo() {
        return game_logo;
    }

    public void setGame_logo(String game_logo) {
        this.game_logo = game_logo;
    }

    public String getMatch_stage() {
        return match_stage;
    }

    public void setMatch_stage(String match_stage) {
        this.match_stage = match_stage;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getMatch_short_name() {
        return match_short_name;
    }

    public void setMatch_short_name(String match_short_name) {
        this.match_short_name = match_short_name;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public int getBet_id() {
        return bet_id;
    }

    public void setBet_id(int bet_id) {
        this.bet_id = bet_id;
    }

    public int getBet_site() {
        return bet_site;
    }

    public void setBet_site(int bet_site) {
        this.bet_site = bet_site;
    }

    public String getBet_odds() {
        return bet_odds;
    }

    public void setBet_odds(String bet_odds) {
        this.bet_odds = bet_odds;
    }

    public String getBet_money() {
        return bet_money;
    }

    public void setBet_money(String bet_money) {
        this.bet_money = bet_money;
    }

    public String getBet_win_money() {
        return bet_win_money;
    }

    public void setBet_win_money(String bet_win_money) {
        this.bet_win_money = bet_win_money;
    }

    public String getReal_win_money() {
        return real_win_money;
    }

    public void setReal_win_money(String real_win_money) {
        this.real_win_money = real_win_money;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getPlatform_operation_status() {
        return platform_operation_status;
    }

    public void setPlatform_operation_status(int platform_operation_status) {
        this.platform_operation_status = platform_operation_status;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public long getBet_created_time() {
        return bet_created_time;
    }

    public void setBet_created_time(long bet_created_time) {
        this.bet_created_time = bet_created_time;
    }

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }
}
