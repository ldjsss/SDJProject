package com.lldj.tc.toolslibrary.retrofit;

import android.content.Context;

import com.lldj.tc.toolslibrary.util.Clog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * description: <p>
 * user: wangclia<p>
 * Creat Time: 2018/12/19 16:47<p>
 * Modify Time: 2018/12/19 16:47<p>
 */


public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public BaseObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseEntity<T> value) {
        if (value.getCode() == 200) {
            T t = value.getData();
            onSuccess(t);
        } else {
            onFail(value.getCode(), value.getMsg());
        }
    }

    @Override
    public void onError(Throwable e) {
        onFail(-1, "网络不给力");
        Clog.e(TAG, "error:" + e.toString());
    }

    @Override
    public void onComplete() {
        Clog.d(TAG, "onComplete");
    }


    protected abstract void onSuccess(T t);

    protected abstract void onFail(int code, String msg);

}
