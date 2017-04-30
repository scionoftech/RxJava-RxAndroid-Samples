package scionoftech.rxasync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static android.content.ContentValues.TAG;

public class MapandReduce extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapand_reduce);

        //apply condition to each item emitted by an Observable, sequentially, and emit the final value
        getObservable()
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer t1, Integer t2) {
                        return t1 + t2;
                    }
                })
                .subscribe(getObserver());

        //transform the items emitted by an Observable by applying a condition to each item
        getObservable()
                .map(new Function<Integer, Integer>() {

                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer*3;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, " onSuccess : value : " + integer);
                    }
                });

    }


    private Observable<Integer> getObservable() {
        return Observable.just(1, 2, 3, 4);
    }

    private MaybeObserver<Integer> getObserver() {
        return new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(Integer value) {
                Log.d(TAG, " onSuccess : value : " + value);
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {

                Log.d(TAG, " onComplete");
            }
        };
    }
}
