package com.lldj.tc.http.beans.FormatModel;

import com.lldj.tc.http.beans.FormatModel.matchModel.Team;
import com.lldj.tc.http.beans.FormatModel.matchModel.Odds;

import java.util.List;


public class ResultsModel{

    public ResultsModel(int id, String name, String logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
    }

    private String access_token;
    private String expires_in;
    private String openid;
    private String mobile;
    private String money;
    private String username;
    private String recharge_url;

    ////////////赛事相关
    private int early;
    private int id;
    private int live;
    private int today;
    private String logo;
    private String name;
    private String short_name;

    /////////////

    private int game_id;
    private int tournament_id;
    private int status;
    private String game_name;
    private String match_name;
    private String match_short_name;
    private String start_time;
    private String end_time;
    private long start_time_ms;
    private long end_time_ms;
    public long getStart_time_ms() {
        return start_time_ms;
    }

    public void setStart_time_ms(long start_time_ms) {
        this.start_time_ms = start_time_ms;
    }

    public long getEnd_time_ms() {
        return end_time_ms;
    }

    public void setEnd_time_ms(long end_time_ms) {
        this.end_time_ms = end_time_ms;
    }

    private String round;
    private String tournament_name;
    private String tournament_short_name;
    private int play_count;
    private List<Team> team;
    private List<Odds> odds;
    private String live_url;

    private int code;
    private int bet_site;

    private String bet_money;
    private String bet_win_money;
    private String odds_id;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getBet_site() {
        return bet_site;
    }

    public void setBet_site(int bet_site) {
        this.bet_site = bet_site;
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


    public String getOdds_id() {
        return odds_id;
    }

    public void setOdds_id(String odds_id) {
        this.odds_id = odds_id;
    }

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

    public String getRecharge_url() {
        return recharge_url;
    }
    public void setRecharge_url(String recharge_url) {
        this.recharge_url = recharge_url;
    }

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

    public String getLive_url() { return live_url; }
    public void setLive_url(String live_url) {
        this.live_url = live_url;
    }

    public int getToday() {
        return today;
    }
    public void setToday(int today) {
        this.today = today;
    }

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

    public List<Team> getTeam() {
        return team;
    }

    public void setTeam(List<Team> team) {
        this.team = team;
    }

//    public List<Odds> getOdds() {
//        return odds;
//    }
//
//    public void setOdds(List<Odds> odds) {
//        this.odds = odds;
//    }

    public List<Odds> getOdds() {
        return odds;
    }

    public void setOdds(List<Odds> odds) {
        this.odds = odds;
    }

    @Override
    public String toString() {
        return "ResultsModel{" +
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
                ", start_time_ms='" + start_time_ms + '\'' +
                ", end_time_ms='" + end_time_ms + '\'' +
                ", round='" + round + '\'' +
                ", tournament_name='" + tournament_name + '\'' +
                ", tournament_short_name='" + tournament_short_name + '\'' +
                ", play_count=" + play_count +
                ", team=" + team +
                ", odds=" + odds +
                ", live_url='" + live_url + '\'' +
                ", code=" + code +
                ", bet_site=" + bet_site +
                ", bet_money='" + bet_money + '\'' +
                ", bet_win_money='" + bet_win_money + '\'' +
                ", odds_id='" + odds_id + '\'' +
                '}';
    }
}