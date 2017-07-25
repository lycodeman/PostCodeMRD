package com.lymilestone.postcodemrd.dagger.component;

import android.app.Activity;

import com.lymilestone.postcodemrd.dagger.modlue.FragmentModule;
import com.lymilestone.postcodemrd.dagger.scope.FragmentScope;
import com.lymilestone.postcodemrd.ui.contract.girls.GirlsDetailCondtract;
import com.lymilestone.postcodemrd.ui.fragment.girls.GilsFragment;
import com.lymilestone.postcodemrd.ui.fragment.girls.GilsItemFragment;
import com.lymilestone.postcodemrd.ui.fragment.girls.GirlsDetailFragment;
import com.lymilestone.postcodemrd.ui.fragment.joke.JokeContentFragment;
import com.lymilestone.postcodemrd.ui.fragment.joke.JokeImgFragment;
import com.lymilestone.postcodemrd.ui.fragment.joke.JokeRandomFragment;
import com.lymilestone.postcodemrd.ui.fragment.news.NewsDTFragment;
import com.lymilestone.postcodemrd.ui.fragment.news.NewsFragment;

import dagger.Component;

/**
 * Created by CodeManLY on 2017/7/20 0020.
 */

@FragmentScope
@Component(dependencies = AppComponent.class , modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(JokeContentFragment contentFragment);
    void inject(JokeRandomFragment randomFragment);
    void inject(JokeImgFragment imgFragment);

    void inject(GilsItemFragment gilsItemFragment);
    void inject(GirlsDetailFragment girlsDetailFragment);

    void inject(NewsDTFragment newsFragment);
}
