package com.lymilestone.postcodemrd.ui.presenter.news;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lymilestone.httplibrary.base.mvp.RxPresenter;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.rx.RxUtils;
import com.lymilestone.postcodemrd.http.observer.CommonObserver;
import com.lymilestone.postcodemrd.modle.DataManager;
import com.lymilestone.postcodemrd.modle.response.news.NewsDetail;
import com.lymilestone.postcodemrd.ui.contract.news.NewsDetailsContract;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by CodeManLY on 2017/7/25 0025.
 */

public class NewsDetailPresenter extends RxPresenter<NewsDetailsContract.View> implements NewsDetailsContract.Presenter {

    private DataManager dataManager;
    private Activity activity;

    @Inject
    public NewsDetailPresenter(DataManager dataManager , Activity activity){
        this.dataManager=dataManager;
        this.activity=activity;
    }
    @Override
    public void handleData(String postId) {
        addSubscribe(dataManager.getNewDetail(postId)
                .flatMap(new Function<Map<String, NewsDetail>, ObservableSource<NewsDetail>>() {
                    @Override
                    public ObservableSource<NewsDetail> apply(@NonNull Map<String, NewsDetail> stringNewsDetailMap) throws Exception {
                        return RxUtils.createObservable(stringNewsDetailMap.get(postId));
                    }
                })
                .compose(RxUtils.defaultTransformer())
                .subscribeWith(new CommonObserver<NewsDetail>(mView) {
                    @Override
                    public void onSuccess(NewsDetail newsDetail) {
                        mView.showNewsDetail(newsDetail);
                    }
                }));
//        AppBarLayout.ScrollingViewBehavior
    }

    @Override
    public void fetchImg(ImageView imageView,String url) {
        LUtils.e("====="+url);
        Glide.with(activity).load(url).asBitmap().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                LUtils.e("===========ddddd==========");
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                mView.showImg(resource);//此方法回掉不上
                return false;
            }
        }).centerCrop().into(imageView);
    }
}
