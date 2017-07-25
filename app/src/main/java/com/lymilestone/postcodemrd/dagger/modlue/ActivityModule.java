package com.lymilestone.postcodemrd.dagger.modlue;

import android.app.Activity;

import com.lymilestone.postcodemrd.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
