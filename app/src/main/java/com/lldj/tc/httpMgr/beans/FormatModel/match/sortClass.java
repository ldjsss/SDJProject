package com.lldj.tc.httpMgr.beans.FormatModel.match;


import com.lldj.tc.httpMgr.beans.FormatModel.Results;

import java.util.Comparator;

public class sortClass implements Comparator {
    public int compare(Object arg0, Object arg1) {
        Results user0 = (Results) arg0;
        Results user1 = (Results) arg1;
        int flag = user0.getStart_time().compareTo(user1.getStart_time());
        return flag;
    }
}