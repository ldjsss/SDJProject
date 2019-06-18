package com.lldj.tc.httpMgr.beans.FormatModel;

import com.lldj.tc.httpMgr.beans.FormatModel.match.Team;
import com.lldj.tc.httpMgr.beans.FormatModel.match.Odds;

import java.util.Arrays;


public class Results{

    private String access_token;
    private String expires_in;
    private String openid;
    private String mobile;
    private String money;
    private String username;

    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }
    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMoney() {
        return money;
    }
    public void setMoney(String money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }




    ////////////赛事相关
    private int early;
    private int id;
    private int live;
    private int today;
    private String logo;
    private String name;
    private String short_name;


    public int getEarly() {
        return early;
    }
    public void setEarly(int early) {
        this.early = early;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getLive() { return live; }
    public void setLive(int live) {
        this.live = live;
    }

    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }
    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public int getToday() {
        return today;
    }
    public void setToday(int today) {
        this.today = today;
    }


    /////////////

    private int game_id;
    private int tournament_id;
    private int status;
    private String game_name;
    private String match_name;
    private String match_short_name;
    private String start_time;
    private String end_time;
    private String round;
    private String tournament_name;
    private String tournament_short_name;
    private int play_count;
    private Team [] team;
    private Odds [] odds;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getTournament_id() {
        return tournament_id;
    }

    public void setTournament_id(int tournament_id) {
        this.tournament_id = tournament_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getTournament_name() {
        return tournament_name;
    }

    public void setTournament_name(String tournament_name) {
        this.tournament_name = tournament_name;
    }

    public String getTournament_short_name() {
        return tournament_short_name;
    }

    public void setTournament_short_name(String tournament_short_name) {
        this.tournament_short_name = tournament_short_name;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public Team[] getTeam() {
        return team;
    }

    public void setTeam(Team[] team) {
        this.team = team;
    }

    public Odds[] getOdds() {
        return odds;
    }

    public void setOdds(Odds[] odds) {
        this.odds = odds;
    }


    @Override
    public String toString() {
        return "Results{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", openid='" + openid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", money='" + money + '\'' +
                ", username='" + username + '\'' +
                ", early=" + early +
                ", id=" + id +
                ", live=" + live +
                ", today=" + today +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", game_id=" + game_id +
                ", tournament_id=" + tournament_id +
                ", status=" + status +
                ", game_name='" + game_name + '\'' +
                ", match_name='" + match_name + '\'' +
                ", match_short_name='" + match_short_name + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", round='" + round + '\'' +
                ", tournament_name='" + tournament_name + '\'' +
                ", tournament_short_name='" + tournament_short_name + '\'' +
                ", play_count=" + play_count +
                ", team=" + Arrays.toString(team) +
                ", odds=" + Arrays.toString(odds) +
                '}';
    }
}