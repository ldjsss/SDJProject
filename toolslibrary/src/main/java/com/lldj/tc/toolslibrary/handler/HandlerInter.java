package com.lldj.tc.toolslibrary.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 *
 */

public class HandlerInter extends Handler{

    private HandleMsgListener listener;
    private String Tag = HandlerInter.class.getSimpleName();

    //使用单例模式创建HandlerInter
    private HandlerInter(){
        Log.e(Tag,"GlobalHandler创建");
    }

    private static class Holder{
        private static final HandlerInter HANDLER = new HandlerInter();
    }

    public static HandlerInter getInstance(){
        return Holder.HANDLER;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (getHandleMsgListener() != null){
            getHandleMsgListener().handleMsg(msg);
        }else {
            Log.e(Tag,"请传入HandleMsgListener对象");
        }
    }

    public interface HandleMsgListener{
        void handleMsg(Message msg);
    }

    public void setHandleMsgListener(HandleMsgListener listener){
        this.listener = listener;
    }

    public HandleMsgListener getHandleMsgListener(){
        return listener;
    }

}
