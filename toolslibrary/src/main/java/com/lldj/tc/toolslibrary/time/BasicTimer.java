package com.lldj.tc.toolslibrary.time;

public final class BasicTimer {//对外接口

    static private BasicTimerManager mManager = BasicTimerManager.getInstance();
    public interface BasicTimerCallback{
        void onTimer();
    }

    //注意：不可使用对象调用startOneshot()
    public static void startOneshot(int millisec, BasicTimerCallback cb){
        mManager.startOneshot(new BasicTimer(cb), millisec);
    }

    public BasicTimer(BasicTimerCallback cb){
        mManager.addTimer(this, cb);
    }

    public void start(int millisec){
        mManager.start(this, millisec);
    }

    public void stop(){
        mManager.stop(this);
    }

    //调用cancel()后，定时器将不再可用
    public void cancel(){
        mManager.remove(this);
    }

    public boolean isRunning(){
        return mManager.isRunning(this);
    };

}
