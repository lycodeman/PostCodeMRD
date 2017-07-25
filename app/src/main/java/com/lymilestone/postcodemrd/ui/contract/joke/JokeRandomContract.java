package com.lymilestone.postcodemrd.ui.contract.joke;

import com.lymilestone.httplibrary.base.mvp.BasePresenter;
import com.lymilestone.httplibrary.base.mvp.BaseView;
import com.lymilestone.postcodemrd.modle.response.joke.JokeContent;
import com.lymilestone.postcodemrd.modle.response.joke.JokeImgs;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;
import com.lymilestone.postcodemrd.modle.response.joke.Jokes;

import java.util.List;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

public interface JokeRandomContract {

    interface View extends BaseView{
        void showRandomJokePic(List<JokePic> jokePics);
        void showRandomJokes(List<Jokes> jokes);
    }

    interface Presenter extends BasePresenter<View>{

        void getRandomJokePic(String type);
        void getRandomJokes();
    }
}
