package com.lldj.tc.toolslibrary.util;

import androidx.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Description: 计时器升级版
 */

public class RxTimerUtilPro {
    private static Disposable mDisposable;

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static Disposable timer(long milliseconds, final IRxNext next) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            Log.e("====开始执行定时器======", "====开始执行定时器======");
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {}

                    @Override
                    public void onComplete() {
                        if (next != null) { next.onComplete(); }
                    }
                });

        return mDisposable;
    }


    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static Disposable interval(long milliseconds, final IRxNext next) {
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {}

                    @Override
                    public void onComplete() {
                        if (next != null) {
                            next.onComplete();
                        }
                    }
                });

        return mDisposable;
    }


    /**
     * 取消订阅
     */
    public static void cancel(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            Clog.e("====定时器取消======", "====定时器取消======");
        }
    }

    public interface IRxNext {
        void doNext(long number);
        void onComplete();
    }

}
