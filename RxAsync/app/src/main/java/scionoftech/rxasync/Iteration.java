package scionoftech.rxasync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class Iteration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iteration);

        // Emits each item of the array, one at a time
        getobservable()
                //.take(3)  //to take first 3 items from list
                //.takeLast(3) //get last 3 items from list
                // .repeat(3) //repeats taken before
                // .distinct() //return unique items
                // .skip(3) //skips first 3 items
                /*.filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return false;
                    }
                })*/ //to filter based on condition

                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("My Action", String.valueOf(integer)); // Prints the number received
                    }
                });

    }

    public Flowable<Integer> getobservable(){


        return Flowable.fromArray(new Integer[]{1, 2, 3, 4, 5, 6});
    }
}
