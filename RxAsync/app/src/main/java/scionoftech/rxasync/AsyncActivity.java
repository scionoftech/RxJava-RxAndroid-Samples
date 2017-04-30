package scionoftech.rxasync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;


public class AsyncActivity extends AppCompatActivity {

    private RxAsync rxAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxAsync = new RxAsync();

        rxAsync.DoWork(new RxAsync.RXHANDLER() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    Log.d("output", jsonObject.getJSONArray("result").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String Error) {

                Toast.makeText(AsyncActivity.this, "Somthing went wrong", Toast.LENGTH_LONG).show();

            }

            @Override
            public Observable<JSONObject> getobservable() {
                return Observable.defer(new Callable<ObservableSource<? extends JSONObject>>() {
                    @Override
                    public ObservableSource<? extends JSONObject> call() throws Exception {
                        return Observable.just(GetData1());
                    }
                });
            }
        });
    }

    public JSONObject GetData1() {

        JSONArray jsonObject = new JSONArray();
        for (int i = 0; i < 100000; i++) {
            jsonObject.put(i);
        }
        JSONObject object = new JSONObject();
        try {
            object.put("result", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }


    @Override
    protected void onDestroy() {
        rxAsync.Stop();
        super.onDestroy();
    }
}
