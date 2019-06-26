package com.lldj.tc.httpMgr.beans;

public class BetBean {
    private int amount;
    private int oddsid;
    private float willget;
    private String bet_money;
    private int bet_site;
    private String bet_win_money;

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

    public int getOdds_id() {
        return odds_id;
    }

    public void setOdds_id(int odds_id) {
        this.odds_id = odds_id;
    }

    public String getOdds_value() {
        return odds_value;
    }

    public void setOdds_value(String odds_value) {
        this.odds_value = odds_value;
    }

    private int code;
    private int odds_id;
    private String odds_value;


    public float getWillget() {
        return willget;
    }

    public void setWillget(float willget) {
        this.willget = willget;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getOddsid() {
        return oddsid;
    }

    public void setOddsid(int oddsid) {
        this.oddsid = oddsid;
    }

    public BetBean(int amount, int oddsid, float willget) {
       this.amount = amount;
       this.oddsid = oddsid;
       this.willget = willget;
    }

    @Override
    public String toString() {
        return "BetBean{" +
                "amount=" + amount +
                ", oddsid=" + oddsid +
                ", willget=" + willget +
                ", bet_money='" + bet_money + '\'' +
                ", bet_site=" + bet_site +
                ", bet_win_money='" + bet_win_money + '\'' +
                ", code=" + code +
                ", odds_id=" + odds_id +
                ", odds_value='" + odds_value + '\'' +
                '}';
    }
}
