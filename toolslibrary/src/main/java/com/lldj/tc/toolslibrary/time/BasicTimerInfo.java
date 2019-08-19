package com.lldj.tc.toolslibrary.time;

final class BasicTimerInfo {
    int init;           //初始计数值
    int count;          //间隔计数，即基于基础定时的倍率
    boolean oneshot;    //是否为单次定时
    Object cb;          //定时回调
}
