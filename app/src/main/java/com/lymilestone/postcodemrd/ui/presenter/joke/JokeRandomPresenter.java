package com.lymilestone.postcodemrd.ui.presenter.joke;

import android.app.Activity;

import com.lymilestone.httplibrary.base.mvp.RxPresenter;
import com.lymilestone.httplibrary.utils.rx.RxUtils;
import com.lymilestone.postcodemrd.constants.Constants;
import com.lymilestone.postcodemrd.http.observer.CommonObserver;
import com.lymilestone.postcodemrd.http.transform.RxTransform;
import com.lymilestone.postcodemrd.modle.DataManager;
import com.lymilestone.postcodemrd.modle.response.joke.BaseJoke;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;
import com.lymilestone.postcodemrd.modle.response.joke.Jokes;
import com.lymilestone.postcodemrd.ui.contract.joke.JokeRandomContract;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public class JokeRandomPresenter extends RxPresenter<JokeRandomContract.View> implements JokeRandomContract.Presenter {

    private DataManager dataManager;
    private Activity activity;

    @Inject
    public JokeRandomPresenter(DataManager dataManager, Activity activity) {
        this.dataManager = dataManager;
        this.activity = activity;
    }

    @Override
    public void getRandomJokePic(String type) {
        addSubscribe(dataManager.fetchRandJokePic(Constants.JOKE_KEY,type)
                .compose(RxTransform.jokeDefaultTransformer())
                .subscribeWith(new CommonObserver<BaseJoke<List<JokePic>>>(mView) {
                    @Override
                    public void onSuccess(BaseJoke<List<JokePic>> jokeContentBaseJoke) {
                        mView.showRandomJokePic(jokeContentBaseJoke.getResult());
                    }
                }));
    }

    @Override
    public void getRandomJokes() {
        addSubscribe(dataManager.fetchRandJokes(Constants.JOKE_KEY)
                .compose(RxTransform.jokeDefaultTransformer())
                .compose(RxUtils.dialogOTransformer(activity,"正在加载",true))
                .subscribeWith(new CommonObserver<BaseJoke<List<Jokes>>>(mView) {
                    @Override
                    public void onSuccess(BaseJoke<List<Jokes>> jokeContentBaseJoke) {
                        mView.showRandomJokes(jokeContentBaseJoke.getResult());
                    }
                }));
    }
}
