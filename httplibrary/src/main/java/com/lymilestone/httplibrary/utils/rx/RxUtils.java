package com.lymilestone.httplibrary.utils.rx;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public class RxUtils {

    public static <T> ObservableTransformer<T, T> dialogOTransformer(final Activity activity, final String msg, final boolean cancelable) {
        return new ObservableTransformer<T, T>() {
            private ProgressDialog progressDialog;

            @Override
            public ObservableSource<T> apply(final Observable<T> upstream) {

                return upstream.doOnSubscribe(new Consumer<Disposable>() {//订阅前调用
                    @Override
                    public void accept(@NonNull final Disposable disposable) throws Exception {
                        progressDialog = ProgressDialog.show(activity, null, msg, true, cancelable);
                        if (cancelable) {
                            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    disposable.dispose();
                                }
                            });
                        }
                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {//终止时调用
                        if (progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }
                    }
                });
            }
        };
    }

    public static <T> FlowableTransformer<T, T> dialogFTransformer(final Activity activity, final String msg, final boolean cancelable) {
        return new FlowableTransformer<T, T>() {
            private ProgressDialog progressDialog;
            @Override
            public Publisher<T> apply(@NonNull Flowable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull final Subscription subscription) throws Exception {
                        //订阅前调用
                        progressDialog = ProgressDialog.show(activity, null, msg, true, cancelable);
                        if (cancelable) {
                            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    subscription.cancel();
                                }
                            });
                        }
                    }
                }).doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {//终止时调用
                        if (progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }
                    }
                });
            }
        };
    }

    /*默认线程转换*/
    public static <T> ObservableTransformer<T, T> defaultTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
//    public static <T> FlowableTransformer<GankHttpResponse<T>, T> handleResult() {   //compose判断结果
//        return new FlowableTransformer<GankHttpResponse<T>, T>() {
//            @Override
//            public Flowable<T> apply(Flowable<GankHttpResponse<T>> httpResponseFlowable) {
//                return httpResponseFlowable.flatMap(new Function<GankHttpResponse<T>, Flowable<T>>() {
//                    @Override
//                    public Flowable<T> apply(GankHttpResponse<T> tGankHttpResponse) {
//                        if(!tGankHttpResponse.getError()) {
//                            return createData(tGankHttpResponse.getResults());
//                        } else {
//                            return Flowable.error(new ApiException("服务器返回error"));
//                        }
//                    }
//                });
//            }
//        };
//    }

//    public static <T> ObservableTransformer<HttpResponseResult<T>, T> transformer() {
//        return new ObservableTransformer<HttpResponseResult<T>, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<HttpResponseResult<T>> upstream) {
//                return upstream
//                        .flatMap(ResultTransformer.<T>flatMap())
//                        .compose(SchedulerTransformer.<T>transformer());
//            }
//        };
//    }
//
//    private static <T> Function<HttpResponseResult<T>, ObservableSource<T>> flatMap() {
//        return new Function<HttpResponseResult<T>, ObservableSource<T>>() {
//            @Override
//            public ObservableSource<T> apply(@NonNull final HttpResponseResult<T> tHttpResponseResult) throws Exception {
//                return new Observable<T>() {
//                    @Override
//                    protected void subscribeActual(Observer<? super T> observer) {
//                        if (tHttpResponseResult.isSuccess()) {
//                            observer.onNext(tHttpResponseResult.getResult());
//                            observer.onComplete();
//                        } else {
//                            observer.onError(new HttpResponseException(tHttpResponseResult.getMsg(), tHttpResponseResult.getState()));
//                        }
//                    }
//                };
//            }
//        };
//    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createObservable(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createFlowable(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
