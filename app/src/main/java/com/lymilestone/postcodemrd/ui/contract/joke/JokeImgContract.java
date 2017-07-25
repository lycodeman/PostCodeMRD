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

public interface JokeImgContract {

    interface View extends BaseView{
        void showTimeImg(JokeImgs jokeImgs);
        void showNewsImg(JokeImgs jokeImgs);
    }

    interface Presenter extends BasePresenter<View>{

        void getTimeImg(String sort, String time, int page, int pageSize);
        void getNewsImg(int page, int pageSize);
    }
}
