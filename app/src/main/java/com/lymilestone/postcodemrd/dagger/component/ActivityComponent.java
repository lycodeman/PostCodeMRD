package com.lymilestone.postcodemrd.dagger.component;

import android.app.Activity;

import com.lymilestone.postcodemrd.modle.response.news.NewsDetail;
import com.lymilestone.postcodemrd.ui.activity.MainActivity;
import com.lymilestone.postcodemrd.dagger.modlue.ActivityModule;
import com.lymilestone.postcodemrd.dagger.scope.ActivityScope;
import com.lymilestone.postcodemrd.ui.activity.news.NewsDetailActivity;

import dagger.Component;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);
    void inject(NewsDetailActivity newsDetailActivity);
}
