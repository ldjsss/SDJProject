package com.lldj.tc.httpMgr.beans.FormatModel;

public class BetModel {
    private int amount;
    private int oddsid;
    private float willget;

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

    public BetModel(int amount, int oddsid, float willget) {
       this.amount = amount;
       this.oddsid = oddsid;
       this.willget = willget;
    }

    @Override
    public String toString() {
        return "BetModel{" +
                "amount=" + amount +
                ", oddsid=" + oddsid +
                ", willget=" + willget +
                '}';
    }
}
