package com.lymilestone.postcodemrd.http;

import com.lymilestone.postcodemrd.modle.response.joke.BaseJoke;
import com.lymilestone.postcodemrd.modle.response.joke.JokeContent;
import com.lymilestone.postcodemrd.modle.response.joke.JokeImgs;
import com.lymilestone.postcodemrd.modle.response.joke.JokePic;
import com.lymilestone.postcodemrd.modle.response.joke.Jokes;
import com.lymilestone.postcodemrd.modle.response.news.NewsDetail;
import com.lymilestone.postcodemrd.modle.response.news.NewsSummary;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author: Est <codeest.dev@gmail.com>
 * @date: 2017/4/21
 * @description:
 */

public interface HttpHelper {


    /*----------------------------joke 笑话--------------------------------------*/
    Observable<BaseJoke<JokeContent>> fetchJokeForTime(String key, int page
            , int pagesize, String time,  String sort);

    Observable<BaseJoke<JokeContent>> fetchJokeForNews(String key, int page
            ,int pagesize);

    Observable<BaseJoke<JokeImgs>> fetchJokeImgsTime(String key, int page
            , int pagesize, String time, String sort);

    Observable<BaseJoke<JokeImgs>> fetchJokeImgNews(String key,int page
            ,int pagesize);

    Observable<BaseJoke<List<JokePic>>> fetchRandJokePic(String key, String type);

    Observable<BaseJoke<List<Jokes>>> fetchRandJokes(String key);

    /*----------------------------joke 笑话--------------------------------------*/

    Observable<String> getGirlDetailData( String id);

    Observable<String> getGirlItemData(String cid,  int pager_offset);

    /*----------------------------新闻--------------------------------------*/
    Observable<Map<String, NewsDetail>> getNewDetail(String postId);

    Observable<Map<String, List<NewsSummary>>> getNewsList(String type, String id, int startPage);


}
