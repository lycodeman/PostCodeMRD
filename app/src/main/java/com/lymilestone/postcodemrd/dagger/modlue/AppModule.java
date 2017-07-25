package com.lymilestone.postcodemrd.dagger.modlue;

import android.content.Context;

import com.lymilestone.httplibrary.utils.sp.SPUtils;
import com.lymilestone.postcodemrd.base.application.App;
import com.lymilestone.postcodemrd.http.HttpHelper;
import com.lymilestone.postcodemrd.http.RetrofitHelper;
import com.lymilestone.postcodemrd.modle.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

@Module
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }


    @Singleton
    @Provides
    SPUtils provideSP(Context context) {
        return SPUtils.getInstanse(context);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper) {
        return new DataManager(httpHelper);
    }
}
