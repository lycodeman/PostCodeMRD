package com.lymilestone.postcodemrd.ui.presenter.joke;

import android.app.Activity;

import com.lymilestone.httplibrary.base.mvp.RxPresenter;
import com.lymilestone.postcodemrd.constants.Constants;
import com.lymilestone.postcodemrd.http.observer.CommonObserver;
import com.lymilestone.postcodemrd.http.transform.RxTransform;
import com.lymilestone.postcodemrd.modle.DataManager;
import com.lymilestone.postcodemrd.modle.response.joke.BaseJoke;
import com.lymilestone.postcodemrd.modle.response.joke.JokeContent;
import com.lymilestone.postcodemrd.ui.contract.joke.JokeContentContract;

import javax.inject.Inject;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public class JokeContentPresenter extends RxPresenter<JokeContentContract.View> implements JokeContentContract.Presenter {

    private DataManager dataManager;
    private Activity activity;

    @Inject
    public JokeContentPresenter(DataManager dataManager, Activity activity) {
        this.dataManager = dataManager;
        this.activity = activity;
    }


    @Override
    public void getTimesJoke(String sort, String time, int id, int pageSize) {
        addSubscribe(dataManager.fetchJokeForTime(Constants.JOKE_KEY,id,pageSize,time,sort)
        .compose(RxTransform.jokeDefaultTransformer())
        .subscribeWith(new CommonObserver<BaseJoke<JokeContent>>(mView) {
            @Override
            public void onSuccess(BaseJoke<JokeContent> jokeContentBaseJoke) {
                mView.showTimesJoke(jokeContentBaseJoke.getResult());
            }
        }));
    }

    @Override
    public void getNewsJoke(int id, int pageSize) {
        addSubscribe(dataManager.fetchJokeForNews(Constants.JOKE_KEY,id,pageSize)
                .compose(RxTransform.jokeDefaultTransformer())
                .subscribeWith(new CommonObserver<BaseJoke<JokeContent>>(mView) {
                    @Override
                    public void onSuccess(BaseJoke<JokeContent> jokeContentBaseJoke) {
                        mView.showNewsJoke(jokeContentBaseJoke.getResult());

                    }
                }));
    }

}
