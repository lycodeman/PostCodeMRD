package com.lymilestone.postcodemrd.dagger.modlue;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.lymilestone.postcodemrd.dagger.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
