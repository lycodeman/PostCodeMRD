package com.lymilestone.postcodemrd.ui.presenter.joke;

import android.app.Activity;

import com.lymilestone.httplibrary.base.mvp.RxPresenter;
import com.lymilestone.postcodemrd.constants.Constants;
import com.lymilestone.postcodemrd.http.observer.CommonObserver;
import com.lymilestone.postcodemrd.http.transform.RxTransform;
import com.lymilestone.postcodemrd.modle.DataManager;
import com.lymilestone.postcodemrd.modle.response.joke.BaseJoke;
import com.lymilestone.postcodemrd.modle.response.joke.JokeImgs;
import com.lymilestone.postcodemrd.ui.contract.joke.JokeImgContract;

import javax.inject.Inject;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public class JokeImgPresenter extends RxPresenter<JokeImgContract.View> implements JokeImgContract.Presenter {

    private DataManager dataManager;
    private Activity activity;

    @Inject
    public JokeImgPresenter(DataManager dataManager, Activity activity) {
        this.dataManager = dataManager;
        this.activity = activity;
    }

    @Override
    public void getTimeImg(String sort, String time, int id, int pageSize) {
        addSubscribe(dataManager.fetchJokeImgsTime(Constants.JOKE_KEY,id,pageSize,time,sort)
                .compose(RxTransform.jokeDefaultTransformer())
                .subscribeWith(new CommonObserver<BaseJoke<JokeImgs>>(mView) {
                    @Override
                    public void onSuccess(BaseJoke<JokeImgs> jokeContentBaseJoke) {
                        mView.showTimeImg(jokeContentBaseJoke.getResult());
                    }
                }));
    }

    @Override
    public void getNewsImg(int id, int pageSize) {
        addSubscribe(dataManager.fetchJokeImgNews(Constants.JOKE_KEY,id,pageSize)
                .compose(RxTransform.jokeDefaultTransformer())
                .subscribeWith(new CommonObserver<BaseJoke<JokeImgs>>(mView) {
                    @Override
                    public void onSuccess(BaseJoke<JokeImgs> jokeContentBaseJoke) {
                        mView.showNewsImg(jokeContentBaseJoke.getResult());
                    }
                }));
    }

}
