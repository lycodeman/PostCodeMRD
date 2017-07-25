package com.lymilestone.postcodemrd.dagger.component;

import com.lymilestone.httplibrary.utils.sp.SPUtils;
import com.lymilestone.postcodemrd.base.application.App;
import com.lymilestone.postcodemrd.dagger.modlue.AppModule;
import com.lymilestone.postcodemrd.dagger.modlue.HttpModule;
import com.lymilestone.postcodemrd.http.RetrofitHelper;
import com.lymilestone.postcodemrd.modle.DataManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

@Singleton
@Component(modules = {HttpModule.class, AppModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类

//    SPUtils spHelper();  //提供http的帮助类

    DataManager dataManager();  //提供http的帮助类,内含RetrofitHelper，是所有数据的入口

}
