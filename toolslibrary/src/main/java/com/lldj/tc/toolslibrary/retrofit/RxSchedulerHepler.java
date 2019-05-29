package com.lldj.tc.toolslibrary.retrofit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * description: <p>
 * user: wangclia<p>
 * Creat Time: 2018/12/19 15:16<p>
 * Modify Time: 2018/12/19 15:16<p>
 * 对线程切换进行包装
 * 每次订阅都要执行
 * .subscribeOn(Schedulers.io())
 * .observeOn(AndroidSchedulers.mainThread())
 *
 * apiService.getData()
 * .compose(RxSchedulerHepler.<TestBean>io_main())
 */


public class RxSchedulerHepler {
    public static <T> ObservableTransformer<T,T> io_main(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return  upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
