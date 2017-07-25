package com.lymilestone.postcodemrd.http;

import com.lymilestone.postcodemrd.http.api.GirlApis;
import com.lymilestone.postcodemrd.http.api.JokeApis;
import com.lymilestone.postcodemrd.http.api.NewsApi;
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
 * Created by codeest on 2016/8/3.
 */
public class RetrofitHelper implements HttpHelper{

    private JokeApis jokeApis;
    private GirlApis girlApis;
    private NewsApi newsApi;

    @Inject
    public RetrofitHelper(JokeApis jokeApis,GirlApis girlApis,NewsApi newsApi) {
        this.jokeApis = jokeApis;
        this.girlApis=girlApis;
        this.newsApi=newsApi;
    }

    /*--------------------------------JokeApis---------------------------------*/
    @Override
    public Observable<BaseJoke<JokeContent>> fetchJokeForTime(String key, int page, int pagesize, String time, String sort) {
        return jokeApis.fetchJokeForTime(key, page, pagesize, time, sort);
    }

    @Override
    public Observable<BaseJoke<JokeContent>> fetchJokeForNews(String key, int page, int pagesize) {
        return jokeApis.fetchJokeForNews(key, page, pagesize);
    }

    @Override
    public Observable<BaseJoke<JokeImgs>> fetchJokeImgsTime(String key, int page, int pagesize, String time, String sort) {
        return jokeApis.fetchJokeImgsTime(key, page, pagesize, time, sort);
    }

    @Override
    public Observable<BaseJoke<JokeImgs>> fetchJokeImgNews(String key, int page, int pagesize) {
        return jokeApis.fetchJokeImgNews(key, page, pagesize);
    }

    @Override
    public Observable<BaseJoke<List<JokePic>>> fetchRandJokePic(String key, String type) {
        return jokeApis.fetchRandJokePic(key, type);
    }

    @Override
    public Observable<BaseJoke<List<Jokes>>> fetchRandJokes(String key) {
        return jokeApis.fetchRandJokes(key);
    }

    /*--------------------------------GirlApis---------------------------------*/
    @Override
    public Observable<String> getGirlDetailData(String id) {
        return girlApis.getGirlDetailData(id);
    }

    @Override
    public Observable<String> getGirlItemData(String cid, int pager_offset) {
        return girlApis.getGirlItemData(cid, pager_offset);
    }

    @Override
    public Observable<Map<String, NewsDetail>> getNewDetail(String postId) {
        return newsApi.getNewDetail(postId);
    }

    @Override
    public Observable<Map<String, List<NewsSummary>>> getNewsList(String type, String id, int startPage) {
        return newsApi.getNewsList(type, id, startPage);
    }
}
