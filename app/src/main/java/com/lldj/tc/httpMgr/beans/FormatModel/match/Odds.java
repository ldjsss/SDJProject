package com.lldj.tc.httpMgr.beans.FormatModel.match;

public class Odds {
    private int group_id;
    private int id;
    private int team_id;
    private int status;
    private int bet_min;
    private int bet_max;
    private long last_update;
    private int match_id;

    private String value;
    private String win;
    private String name;
    private String group_name;
    private String group_short_name;
    private String odds;
    private String tag;
    private String match_stage;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBet_min() {
        return bet_min;
    }

    public void setBet_min(int bet_min) {
        this.bet_min = bet_min;
    }

    public int getBet_max() {
        return bet_max;
    }

    public void setBet_max(int bet_max) {
        this.bet_max = bet_max;
    }

    public long getLast_update() {
        return last_update;
    }

    public void setLast_update(long last_update) {
        this.last_update = last_update;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMatch_stage() {
        return match_stage;
    }

    public void setMatch_stage(String match_stage) {
        this.match_stage = match_stage;
    }


}
