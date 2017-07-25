package com.lymilestone.postcodemrd.base.application;

import com.lymilestone.httplibrary.base.application.BaseApplication;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.httplibrary.utils.sp.SPUtils;
import com.lymilestone.postcodemrd.dagger.component.AppComponent;
import com.lymilestone.postcodemrd.dagger.component.DaggerAppComponent;
import com.lymilestone.postcodemrd.dagger.modlue.AppModule;
import com.lymilestone.postcodemrd.dagger.modlue.HttpModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class App extends BaseApplication {

    private static App instans;
    public static AppComponent appComponent;

    @Override
    protected void initConfigs() {
        instans=this;
        //初始化appmanager
        AppManager.getInstance().init(this);
        //初始化Logger日志
        Logger.addLogAdapter(new AndroidLogAdapter());
        //初始化sp存储,并设置存储文件名
        SPUtils.getInstanse(this).setFileName(getPackageName());
        //对logger封装设置，允许打印种类
//        LUtils.creat().
//                enableLogD(true).//logd
//                enableLogE(true).//loge
//                enableLogI(true).//logi
//                enableLogJ(true).//logjson
//                enableLogV(true).//logv
//                enableLogW(true).//logw
//                enableLogX(true);//logxml
    }

    public static AppComponent getAppComponent(){
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instans))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

}
