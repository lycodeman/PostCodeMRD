package com.lymilestone.postcodemrd.dagger.modlue;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.httplibrary.utils.network.NetWorkUtils;
import com.lymilestone.httplibrary.utils.sp.SPUtils;
import com.lymilestone.postcodemrd.BuildConfig;
import com.lymilestone.postcodemrd.constants.Constants;
import com.lymilestone.postcodemrd.dagger.qualifier.GilrUrl;
import com.lymilestone.postcodemrd.dagger.qualifier.JokeUrl;
import com.lymilestone.postcodemrd.dagger.qualifier.NewsUrl;
import com.lymilestone.postcodemrd.http.api.GirlApis;
import com.lymilestone.postcodemrd.http.api.JokeApis;
import com.lymilestone.postcodemrd.http.api.NewsApi;
import com.lymilestone.postcodemrd.http.intercepter.LoggerInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by codeest on 2017/2/26.
 */

@Module
public class HttpModule {

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    /*-----------------------------joke 注意是成对的出现---------------------------------*/
    @Singleton
    @Provides
    @JokeUrl
    Retrofit provideJokeRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, JokeApis.HOST);
    }

    @Singleton
    @Provides
    @GilrUrl
    Retrofit provideGilsRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, GirlApis.HOST);
    }

    @Singleton
    @Provides
    @NewsUrl
    Retrofit provideNewsRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, NewsApi.HOST);
    }


    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        LoggerInterceptor loggingInterceptor = new LoggerInterceptor("==DEBUG  TEST==", true);
        builder.addInterceptor(loggingInterceptor);
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtils.isNetworkConnected(AppManager.appContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetWorkUtils.isNetworkConnected(AppManager.appContext())) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
//        Interceptor apikey = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                request = request.newBuilder()
//                        .addHeader("apikey",Constants.KEY_API)
//                        .build();
//                return chain.proceed(request);
//            }
//        }
//        设置统一的请求头部参数
//        builder.addInterceptor(apikey);
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }
    /*-----------------------------joke 注意是成对的出现---------------------------------*/
    @Singleton
    @Provides
    JokeApis provideJokeApis(@JokeUrl Retrofit retrofit) {
        return retrofit.create(JokeApis.class);
    }

    //
    @Singleton
    @Provides
    GirlApis provideGirlApis(@GilrUrl Retrofit retrofit) {
        return retrofit.create(GirlApis.class);
    }

    @Singleton
    @Provides
    NewsApi provideNewsApis(@NewsUrl Retrofit retrofit) {
        return retrofit.create(NewsApi.class);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        return builder
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
