package com.lymilestone.postcodemrd.ui.presenter.girls;

import android.app.Activity;
import android.graphics.Bitmap;

import com.lymilestone.httplibrary.base.mvp.RxPresenter;
import com.lymilestone.httplibrary.utils.assist.StringUtil;
import com.lymilestone.httplibrary.utils.rx.RxUtils;
import com.lymilestone.postcodemrd.http.observer.CommonObserver;
import com.lymilestone.postcodemrd.modle.DataManager;
import com.lymilestone.postcodemrd.ui.contract.girls.GirlsDetailCondtract;
import com.lymilestone.postcodemrd.utils.ImageLoader;
import com.lymilestone.postcodemrd.utils.JsoupUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by CodeManLY on 2017/7/23 0023.
 */

public class GirlsDetailPresenter extends RxPresenter<GirlsDetailCondtract.View> implements GirlsDetailCondtract.Presenter {

    private DataManager dataManager;
    private Activity activity;

    @Inject
    public GirlsDetailPresenter(DataManager dataManager, Activity activity) {
        this.dataManager = dataManager;
        this.activity = activity;
    }

    @Override
    public void fetchGilsDetaileData(String id) {
        addSubscribe(dataManager.getGirlDetailData(id)
                .compose(RxUtils.defaultTransformer())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<List<Bitmap>>>() {
                    @Override
                    public ObservableSource<List<Bitmap>> apply(@NonNull String s) throws Exception {
                        List<String> strings = JsoupUtil.parseGirlDetail(s);
                        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
                        for (String s1 : strings) {
                            Bitmap bitmap = ImageLoader.load(activity, s1);//在子线程中开启
                            bitmaps.add(bitmap);
                        }
                        return RxUtils.createObservable(bitmaps);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonObserver<List<Bitmap>>(mView) {
                    @Override
                    public void onSuccess(List<Bitmap> bitmaps) {
                        mView.showGilsDetaile(bitmaps);
                    }
                }));
    }

}
