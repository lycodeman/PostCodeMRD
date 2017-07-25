package com.lymilestone.postcodemrd.modle;


import com.lymilestone.httplibrary.utils.sp.SPUtils;
import com.lymilestone.postcodemrd.http.HttpHelper;
import com.lymilestone.postcodemrd.modle.response.joke.BaseJoke;
import com.lymilestone.postcodemrd.modle.response.joke.JokeContent;
import com.lymilestone.postcodemrd.modle.response.joke.JokeImgs;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;
import com.lymilestone.postcodemrd.modle.response.joke.Jokes;
import com.lymilestone.postcodemrd.modle.response.news.NewsDetail;
import com.lymilestone.postcodemrd.modle.response.news.NewsSummary;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author: Est <codeest.dev@gmail.com>
 * @date: 2017/4/21
 * @desciption:
 */

public class DataManager implements HttpHelper {

    HttpHelper mHttpHelper;
//    SPUtils mSpUtils;

    public DataManager(HttpHelper httpHelper) {
        mHttpHelper = httpHelper;
//        mSpUtils = spUtils;
    }

    /*----------------------------joke-------------------------------*/
    @Override
    public Observable<BaseJoke<JokeContent>> fetchJokeForTime(String key, int page, int pagesize, String time, String sort) {
        return mHttpHelper.fetchJokeForTime(key, page, pagesize, time, sort);
    }

    @Override
    public Observable<BaseJoke<JokeContent>> fetchJokeForNews(String key, int page, int pagesize) {
        return mHttpHelper.fetchJokeForNews(key, page, pagesize);
    }

    @Override
    public Observable<BaseJoke<JokeImgs>> fetchJokeImgsTime(String key, int page, int pagesize, String time, String sort) {
        return mHttpHelper.fetchJokeImgsTime(key, page, pagesize, time, sort);
    }

    @Override
    public Observable<BaseJoke<JokeImgs>> fetchJokeImgNews(String key, int page, int pagesize) {
        return mHttpHelper.fetchJokeImgNews(key, page, pagesize);
    }

    @Override
    public Observable<BaseJoke<List<JokePic>>> fetchRandJokePic(String key, String type) {
        return mHttpHelper.fetchRandJokePic(key, type);
    }

    @Override
    public Observable<BaseJoke<List<Jokes>>> fetchRandJokes(String key) {
        return mHttpHelper.fetchRandJokes(key);
    }

    /*----------------------------girl-------------------------------*/
    @Override
    public Observable<String> getGirlDetailData(String id) {
        return mHttpHelper.getGirlDetailData(id);
    }

    @Override
    public Observable<String> getGirlItemData(String cid, int pager_offset) {
        return mHttpHelper.getGirlItemData(cid, pager_offset);
    }

    /*----------------------------新闻-------------------------------*/
    @Override
    public Observable<Map<String, NewsDetail>> getNewDetail(String postId) {
        return mHttpHelper.getNewDetail(postId);
    }

    @Override
    public Observable<Map<String, List<NewsSummary>>> getNewsList(String type, String id, int startPage) {
        return mHttpHelper.getNewsList(type, id, startPage);
    }
}
