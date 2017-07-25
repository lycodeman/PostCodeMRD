package com.lymilestone.postcodemrd.http.transform;

import com.lymilestone.postcodemrd.http.exception.ApiException;
import com.lymilestone.postcodemrd.modle.response.joke.BaseJoke;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public class RxTransform {

    public static <T> ObservableTransformer<T, T> jokeDefaultTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<T, T>() {
                            @Override
                            public T apply(@NonNull T t) throws Exception {
                                if (((BaseJoke)t).getError_code()!=0)
                                    throw new ApiException(((BaseJoke)t).getReason());
                                return t;
                            }
                        });
            }
        };
    }

}
