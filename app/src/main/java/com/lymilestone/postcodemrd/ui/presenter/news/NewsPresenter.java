package com.lymilestone.postcodemrd.ui.presenter.news;

import com.lymilestone.httplibrary.base.mvp.RxPresenter;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.rx.RxUtils;
import com.lymilestone.postcodemrd.constants.Constants;
import com.lymilestone.postcodemrd.http.observer.CommonObserver;
import com.lymilestone.postcodemrd.modle.DataManager;
import com.lymilestone.postcodemrd.modle.response.news.NewsSummary;
import com.lymilestone.postcodemrd.ui.contract.news.NewsCondtract;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by CodeManLY on 2017/7/24 0024.
 */

public class NewsPresenter extends RxPresenter<NewsCondtract.View> implements NewsCondtract.Presenter {

    private DataManager dataManager;

    @Inject
    public NewsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void handleNews(String type, String id, int startPage) {
        addSubscribe(dataManager.getNewsList(type, id, startPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .flatMap(new Function<Map<String, List<NewsSummary>>, ObservableSource<List<NewsSummary>>>() {
                            @Override
                            public ObservableSource<List<NewsSummary>> apply(@NonNull Map<String, List<NewsSummary>> stringListMap) throws Exception {
                                if (id.endsWith(Constants.HOUSE_ID)) {
//                             房产实际上针对地区的它的id与返回key不同
                                    return RxUtils.createObservable(stringListMap.get("北京"));
                                }
                                List<NewsSummary> newsSummaries = stringListMap.get(id);
                                return RxUtils.createObservable(newsSummaries);
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new CommonObserver<List<NewsSummary>>(mView) {
                            @Override
                            public void onSuccess(List<NewsSummary> summaryList) {
                                mView.showNews(summaryList);
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                            }
                        })
        );
    }
}
