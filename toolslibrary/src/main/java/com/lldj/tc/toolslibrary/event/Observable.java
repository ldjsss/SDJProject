package com.lldj.tc.toolslibrary.event;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    List<Observer<T>> mObservers = new ArrayList<Observer<T>>();

    public void register(Observer<T> observer){
        if (observer == null){
            throw new NullPointerException("observer == null");
        }
        synchronized (this){
            if(!mObservers.contains(observer)){
                mObservers.add(observer);
            }
        }


    }

    public synchronized void unregister(Observer<T> observer) {
        if (observer == null){
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (mObservers.contains(observer)) {
                mObservers.remove(observer);
            }
        }
    }

    public synchronized void notifyObserver(T data){
        synchronized (this) {
            for (int i = 0; i < mObservers.size(); i++) {
                mObservers.get(i).onUpdate(this,data);
            }
//            for (Observer<T> observer:mObservers) {
//                observer.onUpdate( this,data);
//            }
        }
    }

}

