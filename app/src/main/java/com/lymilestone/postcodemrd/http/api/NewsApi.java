package com.lymilestone.postcodemrd.http.api;

import com.lymilestone.postcodemrd.modle.response.news.NewsDetail;
import com.lymilestone.postcodemrd.modle.response.news.NewsSummary;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by CodeManLY on 2017/7/24 0024.
 */

public interface NewsApi {

    String HOST = "http://c.m.163.com/";

    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NewsDetail>> getNewDetail(
            @Path("postId") String postId);

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);
}
