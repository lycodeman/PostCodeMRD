package com.lymilestone.postcodemrd.http.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Author: Othershe
 * Time: 2016/8/17 11:49
 */
public interface GirlApis {
    public static final String HOST = "http://www.dbmeinv.com/dbgroup/";

    @GET("{id}")
    Observable<String> getGirlDetailData(@Path("id") String id);

    @GET("show.htm")
    Observable<String> getGirlItemData(@Query("cid") String cid, @Query("pager_offset") int pager_offset);
}
