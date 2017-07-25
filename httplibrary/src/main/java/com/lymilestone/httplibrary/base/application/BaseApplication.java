package com.lymilestone.httplibrary.base.application;

import android.app.Application;

import com.lymilestone.httplibrary.BuildConfig;
import com.lymilestone.httplibrary.utils.log.LUtils;
import com.lymilestone.httplibrary.utils.manager.AppManager;
import com.lymilestone.httplibrary.utils.sp.SPUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @Description: Application基类
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 2016-12-19 14:49
 */
public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化appmanager
        AppManager.getInstance().init(this);
        //初始化Logger日志,只在调试的时候打印日志
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        LUtils.creat();
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
        //初始化其他配置
        initConfigs();
    }

    /**
     * 初始化配置
     */
    protected abstract void initConfigs();
}
