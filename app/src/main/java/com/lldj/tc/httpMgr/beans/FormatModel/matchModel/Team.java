package com.lldj.tc.httpMgr.beans.FormatModel.matchModel;

public class Team {
    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getTeam_logo() {
        return team_logo;
    }

    public void setTeam_logo(String team_logo) {
        this.team_logo = team_logo;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_short_name() {
        return team_short_name;
    }

    public void setTeam_short_name(String team_short_name) {
        this.team_short_name = team_short_name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    private int team_id;
    private int pos;
    private int match_id;
    private String team_logo;
    private String team_name;
    private String team_short_name;
    private String start_time;
    private Score score;

    @Override
    public String toString() {
        return "Team{" +
                "team_id=" + team_id +
                ", pos=" + pos +
                ", match_id=" + match_id +
                ", team_logo='" + team_logo + '\'' +
                ", team_name='" + team_name + '\'' +
                ", team_short_name='" + team_short_name + '\'' +
                ", start_time='" + start_time + '\'' +
                ", score=" + score +
                '}';
    }
}