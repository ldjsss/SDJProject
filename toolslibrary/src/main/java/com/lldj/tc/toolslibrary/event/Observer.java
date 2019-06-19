package com.lldj.tc.toolslibrary.event;

public interface Observer<T> {
    void onUpdate(Observable<T> observable, T data);
}

