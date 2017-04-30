package scionoftech.rxasync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by saikumaryava on 27/02/17.
 */

public class RxAsync {


    public RxAsync() {

    }

    public static abstract class RXHANDLER {
        public abstract void onSuccess(JSONObject jsonObject);


        public abstract void onFailure(String Error);

        public abstract Observable<JSONObject> getobservable();

    }
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public void DoWork(final RXHANDLER handler) {

        compositeDisposable.add(handler.getobservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<JSONObject>() {
                    @Override
                    public void onNext(JSONObject value) {
                        handler.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                        handler.onFailure(e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }


    public void Stop() {
        compositeDisposable.clear(); // do not send event after fragment has been destroyed
    }

}
